package binarybricks.com.yelpql.network;

import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.ApolloQueryCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.rx2.Rx2Apollo;

import java.util.ArrayList;
import java.util.List;

import binarybricks.com.yelpql.network.model.Business;
import binarybricks.com.yelpql.utils.LocationUtils;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import yelp.SearchYelp;

/**
 * Created by pairan on 7/31/17.
 */

public class SearchAPI {

    public static Single<List<Business>> searchYelp(@NonNull String authenticationToken, final double latitude, final double longitude, int offset) {

        final SearchYelp searchYelp = SearchYelp.builder()
                .latitude(latitude)
                .longitude(longitude)
                .radius(10000d)
                .offset(offset)
                .build();

        ApolloClient apolloClient = GraphQLClient.getApolloClient(authenticationToken);
        ApolloQueryCall<SearchYelp.Data> query = apolloClient.query(searchYelp);

        return Rx2Apollo.from(query)
                .subscribeOn(Schedulers.io())
                .map(new Function<Response<SearchYelp.Data>, List<Business>>() {
                    @Override
                    public List<Business> apply(@NonNull Response<SearchYelp.Data> dataResponse) throws Exception {
                        List<Business> businessList = new ArrayList<>();

                        if (dataResponse.data() != null && dataResponse.data().search() != null) {
                            List<SearchYelp.Business> businessYelpList = dataResponse.data().search().business();

                            for (SearchYelp.Business businessYelp : businessYelpList) {
                                Business business = new Business();
                                business.setName(businessYelp.name());
                                business.setId(businessYelp.id());
                                business.setPrice(businessYelp.price());
                                business.setPhotos(businessYelp.photos());
                                if (businessYelp.rating() != null) {
                                    business.setRating(String.valueOf(businessYelp.rating()));
                                } else {
                                    business.setRating("0");
                                }

                                List<String> categoryList = new ArrayList<>();
                                List<SearchYelp.Category> categories = businessYelp.categories();
                                for (SearchYelp.Category category : categories) {
                                    categoryList.add(category.title());
                                }
                                business.setCategories(categoryList);
                                business.setDistanceFromCurrent(LocationUtils.getDistanceFromLocation(latitude, longitude, businessYelp.coordinates().latitude(), businessYelp.coordinates().longitude()));

                                businessList.add(business);
                            }

                            return businessList;

                        } else if (dataResponse.hasErrors()) {
                            throw new Exception("error" + dataResponse.errors().get(0).message());
                        }

                        throw new Exception("error");
                    }
                });
    }
}
