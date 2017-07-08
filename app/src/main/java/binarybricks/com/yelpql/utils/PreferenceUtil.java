package binarybricks.com.yelpql.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

/**
 * Created by pairan on 7/8/17.
 * <p>
 * Util class that takes care of storing/retrieving shared preference.
 */

public class PreferenceUtil {

    private static final String PREFERENCE = "YelpQLPref";
    private static final String AUTH_TOKEN_TIME = "AUTH_TOKEN_TIME";
    private static final String AUTH_TOKEN = "AUTH_TOKEN";

    public static Long getLastCheckedAuthTokenTime(@NonNull Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        return sharedPref.getLong(AUTH_TOKEN_TIME, 0);
    }

    public static void setLastCheckedAuthTokenTime(@NonNull Context context, @NonNull Long tokenCheckTime) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(AUTH_TOKEN_TIME, tokenCheckTime);
        editor.apply();
    }

    public static String getAuthToken(@NonNull Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        return sharedPref.getString(AUTH_TOKEN, "");
    }

    public static void setAuthToken(@NonNull Context context, @NonNull String authToken) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(AUTH_TOKEN, authToken);
        editor.apply();
    }
}
