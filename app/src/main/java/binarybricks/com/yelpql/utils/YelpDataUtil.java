package binarybricks.com.yelpql.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import java.util.Calendar;
import java.util.List;

import binarybricks.com.yelpql.R;
import yelp.BusinessDetails;

/**
 * Created by pairan on 8/11/17.
 */

public class YelpDataUtil {

    public static void showRatingLogo(ImageView ratings, String rating) {
        switch (rating) {

            case "0":
                ratings.setImageResource(R.drawable.stars_regular_0);
                break;
            case "1.0":
            case "1":
                ratings.setImageResource(R.drawable.stars_regular_1);
                break;
            case "1.5":
                ratings.setImageResource(R.drawable.stars_regular_1_half);
                break;
            case "2.0":
            case "2":
                ratings.setImageResource(R.drawable.stars_regular_2);
                break;
            case "2.5":
                ratings.setImageResource(R.drawable.stars_regular_2_half);
                break;
            case "3.0":
            case "3":
                ratings.setImageResource(R.drawable.stars_regular_3);
                break;
            case "3.5":
                ratings.setImageResource(R.drawable.stars_regular_3_half);
                break;
            case "4.0":
            case "4":
                ratings.setImageResource(R.drawable.stars_regular_4);
                break;
            case "4.5":
                ratings.setImageResource(R.drawable.stars_regular_4_half);
                break;
            case "5.0":
            case "5":
                ratings.setImageResource(R.drawable.stars_regular_5);
                break;
        }
    }

    public static String getTodaysHours(List<BusinessDetails.Open> hourList) {
        int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK); // in java monday is 2, sunday is 1
        // in yelp monday is 0 sunday is 6

        if (day == 1) {
            day = 6;
        } else {
            day = day - 2;
        }

        for (BusinessDetails.Open open : hourList) {
            if (open.day() == day) {
                return "Today " + open.start() + " to " + open.end();
            }
        }
        return "Closed Today";
    }

    /**
     * Method to get duration like 1 day ago, 2 days ago etc.
     *
     * @param context
     * @param reviewMilliseconds
     * @return
     */
    public static String getDuration(@NonNull Context context, long reviewMilliseconds) {
        // assuming the worse case, i.e., milliseconds=0, return something meaningful.

        long milliseconds = System.currentTimeMillis() - reviewMilliseconds;

        String result = context.getString(R.string.stringNow);
        String stringAgo = context.getString(R.string.stringAgo);

        long seconds = Math.abs(milliseconds) / 1000;
        if (seconds == 0) {
            return result;
        }

        // there's at least 1 second in this, check if there are minutes
        long minutes = seconds / 60;
        if (minutes == 0) {
            // no minutes, so return the seconds
            return seconds + context.getString(R.string.stringSecondsUnit) + " " + stringAgo;
        }

        // there's at least 1 mn, check if there are hours
        long hours = minutes / 60;
        if (hours == 0) {
            // no hours, so return the minutes
            return minutes + context.getString(R.string.stringMinutesUnit) + " " + stringAgo;
        }

        // there's at least 1 h, check if there are days
        long days = hours / 24;
        if (days == 0) {
            // no day, so return the hours
            return hours + context.getString(R.string.stringHoursUnit) + " " + stringAgo;
        }

        // there's at least 1 day, check if there are months (based on 30d months)
        long months = days / 30;
        if (months == 0) {
            // no month, so return the days
            return days + " " + context.getResources().getQuantityString(R.plurals.stringDaysUnit, (int) days) + " " + stringAgo;
        }

        // there's at least 1 month, check if there are years
        long years = months / 12;
        if (years == 0) {
            // no years, so return the months
            return months + " " + context.getResources().getQuantityString(R.plurals.stringMonthsUnit, (int) months) + " " + stringAgo;
        }

        // return the years
        return years + " " + context.getResources().getQuantityString(R.plurals.stringYearsUnit, (int) years) + " " + stringAgo;
    }
}
