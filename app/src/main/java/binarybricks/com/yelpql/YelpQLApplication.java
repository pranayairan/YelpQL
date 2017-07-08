package binarybricks.com.yelpql;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by pairan on 7/8/17.
 */

public class YelpQLApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
