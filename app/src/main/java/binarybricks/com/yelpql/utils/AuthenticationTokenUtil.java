package binarybricks.com.yelpql.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import binarybricks.com.yelpql.R;
import binarybricks.com.yelpql.network.RetrofitClient;

/**
 * Created by pairan on 7/8/17.
 * <p>
 * A simple util to fetch token for network call. If tokens are expired we do a new call and get it.
 * If tokens are not expired, this returns the cached token.
 */

public class AuthenticationTokenUtil {

    public static String fetchAndUpdateAuthenticationToken(@NonNull Context context) {
        // fetch token from network.

        Long lastCheckedAuthTokenTime = PreferenceUtil.getLastCheckedAuthTokenTime(context);
        long currentTimeMillis = System.currentTimeMillis();

        // token expires in 180 days after fetching.
        if (lastCheckedAuthTokenTime == 0 || lastCheckedAuthTokenTime - currentTimeMillis >= 15552000000L) {
            RetrofitClient retrofitClient = new RetrofitClient();
            retrofitClient.getAuthenticationTokenFromYelp(context, context.getString(R.string.client_id), context.getString(R.string.client_secret));
        } else {
            return PreferenceUtil.getAuthToken(context);
        }

        return "";
    }
}
