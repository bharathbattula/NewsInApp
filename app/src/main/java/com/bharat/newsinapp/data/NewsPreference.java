package com.bharat.newsinapp.data;

import android.content.Context;

import com.bharat.newsinapp.R;
import android.content.*;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.content.SharedPreferencesCompat;

/**
 * Created by Bharat on 9/19/2017.
 */

public class NewsPreference {



    /*This is to check Notifications are enabled or disabled */
    public static boolean areNotificationsEnbled(Context context) {
        String preferenceNotificationEnabled  = context.getString(R.string.pref_notification_key);

        boolean notificationDefaultPreference = context.getResources().getBoolean(R.bool.show_notification_by_defalt);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);

        boolean isNotificationEnabled = sp.getBoolean(preferenceNotificationEnabled,notificationDefaultPreference);
        return isNotificationEnabled;
    }

    public static long getLastElapsedNotificationTime(Context context) {
        long lastNotificationTimeInMillis = getLastNotificationTimeInMillis(context);
        long timeSinceLastNotification = System.currentTimeMillis() - lastNotificationTimeInMillis;
        return timeSinceLastNotification;
    }

    public static long getLastNotificationTimeInMillis(Context context){

        String lastNotificationKey  = context.getString(R.string.pref_last_notification);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        long lastNotificationTimeInMillis = sp.getLong(lastNotificationKey,0);

        return lastNotificationTimeInMillis;
    }

    public static void saveLastNotificationTime(Context context, long timeOfNotification){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(context.getString(R.string.pref_last_notification),
                timeOfNotification);
        editor.apply();
    }
}
