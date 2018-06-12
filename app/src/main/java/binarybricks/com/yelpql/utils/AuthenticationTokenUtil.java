package binarybricks.com.yelpql.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import binarybricks.com.yelpql.R;
import binarybricks.com.yelpql.network.YelpService;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * Created by pairan on 7/8/17.
 * <p>
 * A simple util to fetch token for network call. If tokens are expired we do a new call and get it.
 * If tokens are not expired, this returns the cached token.
 */

@Deprecated
public class AuthenticationTokenUtil {

    private static final long TIME_OUT_Days = 15552000000L;

    public static Observable<String> fetchAndUpdateAuthenticationToken(@NonNull final Context context) {
        // fetch token from network.

        Long lastCheckedAuthTokenTime = PreferenceUtil.getLastCheckedAuthTokenTime(context);
        long currentTimeMillis = System.currentTimeMillis();

        // token expires in 180 days after fetching.
        if (lastCheckedAuthTokenTime == 0 || lastCheckedAuthTokenTime - currentTimeMillis >= TIME_OUT_Days) {
            YelpService yelpService = new YelpService();
            return yelpService.getAuthenticationTokenFromYelp(context.getString(R.string.client_id), context.getString(R.string.client_secret))
                    .doOnNext(new Consumer<String>() {
                        @Override
                        public void accept(@io.reactivex.annotations.NonNull String s) throws Exception {
                            if (s != null) {
                                PreferenceUtil.setLastCheckedAuthTokenTime(context, System.currentTimeMillis());
                                PreferenceUtil.setAuthToken(context, s);
                            }
                        }
                    })
                    .doOnError(new Consumer<Throwable>() {
                        @Override
                        public void accept(@io.reactivex.annotations.NonNull Throwable throwable) throws Exception {
                            throw new Exception("Error :" + throwable.getMessage());
                        }
                    });
        }

        return Observable.just(PreferenceUtil.getAuthToken(context));
    }
}
