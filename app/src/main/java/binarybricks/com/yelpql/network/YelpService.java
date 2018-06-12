package binarybricks.com.yelpql.network;

import android.support.annotation.NonNull;

import binarybricks.com.yelpql.network.model.YelpAuthentication;
import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by pairan on 7/8/17.
 * Network class that is responsible for fetching authentication token from yelp.
 */

public class YelpService {

    private static final String OAUTH_GRANT = "client_credentials";
    private Retrofit retrofit;

    interface YelpAuthenticationService {
        @FormUrlEncoded
        @POST("oauth2/token")
        Observable<YelpAuthentication> getAutenticationTokenFromYelp(@Field("grant_type") String grantType,
                                                                     @Field("client_id") String clientID,
                                                                     @Field("client_secret") String clientSecret);
    }


    public Observable<String> getAuthenticationTokenFromYelp(@NonNull String clientID, @NonNull String clientSecret) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.yelp.com/")
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit.create(YelpAuthenticationService.class).getAutenticationTokenFromYelp(OAUTH_GRANT, clientID, clientSecret)
                .subscribeOn(Schedulers.io())
                .map(new Function<YelpAuthentication, String>() {
                    @Override
                    public String apply(@io.reactivex.annotations.NonNull YelpAuthentication yelpAuthentication) throws Exception {
                        if (yelpAuthentication != null) {
                            return yelpAuthentication.getAccess_token();
                        }

                        throw new Exception("Error :");
                    }
                });
    }
}
