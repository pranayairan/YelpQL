package binarybricks.com.yelpql;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by pairan on 7/8/17.
 */

// you can download schema via this command
//apollo-codegen download-schema https://api.yelp.com/v3/graphql --output schema.json --header "Authorization: Bearer nHwOICuMpcv4zoj4kFkkzlarDPQf0vxgILtYOrjRft6eUgCE8DtzZuQ4oxOQqLyoi1n_qK0Hpp0V5yDI2cHVzC9PjGEfaY2zBrRTJD6SMZ45e9POnWrrm2pTmjBhWXYx"

public class YelpQLApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
