package binarybricks.com.yelpql.network;

import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.ApolloQueryCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.rx2.Rx2Apollo;

import java.util.ArrayList;
import java.util.List;

import binarybricks.com.yelpql.network.model.Business;
import binarybricks.com.yelpql.utils.LocationUtils;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import yelp.BusinessDetails;

/**
 * Created by pairan on 7/31/17.
 */

public class BusinessDetailsAPI {

    public static Observable<Business> getBusinessDetails(@NonNull String authenticationToken, String businessId, final double latitude, final double longitude) {

        BusinessDetails businessDetails = BusinessDetails.builder()
                .businessID(businessId)
                .build();

        ApolloClient apolloClient = GraphQLClient.getApolloClient(authenticationToken);
        ApolloQueryCall<BusinessDetails.Data> query = apolloClient.query(businessDetails);

        return Rx2Apollo.from(query)
                .subscribeOn(Schedulers.io())
                .map(new Function<Response<BusinessDetails.Data>, Business>() {
                    @Override
                    public Business apply(@NonNull Response<BusinessDetails.Data> dataResponse) throws Exception {

                        if (dataResponse.data() != null && dataResponse.data().business() != null) {
                            BusinessDetails.Business businessData = dataResponse.data().business();
                            Business business = new Business();
                            business.setName(businessData.name());
                            business.setId(businessData.id());
                            business.setPrice(businessData.price());
                            business.setPhotos(businessData.photos());
                            business.setPhone(businessData.phone());
                            business.setUrl(businessData.url());
                            if (businessData.rating() != null) {
                                business.setRating(String.valueOf(businessData.rating()));
                            } else {
                                business.setRating("0");
                            }
                            business.setLatitude(businessData.coordinates().latitude());
                            business.setLongitude(businessData.coordinates().longitude());
                            business.setDisplayPhone(businessData.display_phone());
                            business.setReviewCount(businessData.review_count());
                            business.setFormattedAddress(businessData.location().formatted_address());
                            business.setOpenNow(businessData.hours().get(0).is_open_now());
                            business.setHourList(businessData.hours().get(0).open());
                            business.setReviewList(businessData.reviews());

                            List<String> categoryList = new ArrayList<>();
                            List<BusinessDetails.Category> categories = businessData.categories();
                            for (BusinessDetails.Category category : categories) {
                                categoryList.add(category.title());
                            }
                            business.setCategories(categoryList);
                            business.setDistanceFromCurrent(LocationUtils.getDistanceFromLocation(latitude, longitude, businessData.coordinates().latitude(), businessData.coordinates().longitude()));

                            return business;
                        } else if (dataResponse.hasErrors()) {
                            throw new Exception("error" + dataResponse.errors().get(0).message());
                        }

                        throw new Exception("error");
                    }
                });
    }
}
