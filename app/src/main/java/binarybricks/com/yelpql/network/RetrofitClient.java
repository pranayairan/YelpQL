package binarybricks.com.yelpql.network;

import android.content.Context;
import android.support.annotation.NonNull;

import binarybricks.com.yelpql.network.model.YelpAuthentication;
import binarybricks.com.yelpql.utils.PreferenceUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import timber.log.Timber;

/**
 * Created by pairan on 7/8/17.
 * Network class that is responsible for fetching authentication token from yelp.
 */

public class RetrofitClient {

    private static final String OAUTH_GRANT = "client_credentials";
    private Retrofit retrofit;

    interface YelpService {
        @FormUrlEncoded
        @POST("oauth2/token")
        Call<YelpAuthentication> getAutenticationTokenFromYelp(@Field("grant_type") String grantType,
                                                               @Field("client_id") String clientID,
                                                               @Field("client_secret") String clientSecret);
    }


    public void getAuthenticationTokenFromYelp(@NonNull final Context context, @NonNull String clientID, @NonNull String clientSecret) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.yelp.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        Call<YelpAuthentication> getAuthenticationToken = retrofit.create(YelpService.class).getAutenticationTokenFromYelp(OAUTH_GRANT, clientID, clientSecret);

        getAuthenticationToken.enqueue(new Callback<YelpAuthentication>() {
            @Override
            public void onResponse(Call<YelpAuthentication> call, Response<YelpAuthentication> response) {
                if (response.isSuccessful()) {
                    YelpAuthentication yelpAuthentication = response.body();
                    if (yelpAuthentication != null) {
                        PreferenceUtil.setLastCheckedAuthTokenTime(context, System.currentTimeMillis());
                        PreferenceUtil.setAuthToken(context, yelpAuthentication.getAccess_token());
                    }
                }
                Timber.e(response.message());
            }

            @Override
            public void onFailure(Call<YelpAuthentication> call, Throwable t) {
                Timber.e(t.getMessage());
            }
        });
    }
}
