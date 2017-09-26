package com.bharat.newsinapp.sync;

import java.sql.Time;
import java.util.concurrent.TimeUnit;
import android.content.*;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.bharat.newsinapp.data.NewsContract;
import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

/**
 * Created by Bharat on 9/13/2017.
 */

public class NewsSyncUtils {

    private static final int SYNC_INTERVAL_HOURS = 6;
    private static final int SYNC_INTERVAL_SECONDS = (int) TimeUnit.HOURS.toSeconds(SYNC_INTERVAL_HOURS);
    private static final int SYNC_FLEXTIME_SECONDS = SYNC_INTERVAL_SECONDS / 3;

    private static boolean isInitialized;

    private static final String SYNC_TAG = "news-sync";

    static void scheduleFirebaseJobDispatcher(final Context context){
        Log.d(SYNC_TAG,"scheduled service");
        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher jobDispatcher = new FirebaseJobDispatcher(driver);

        /*Creating job which runs periodically*/
        Job job = jobDispatcher.newJobBuilder()
                /*Service that is responsible for backgroud task to sync data*/
                .setService(NewsSyncFirebaseService.class)
                .setTag(SYNC_TAG)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(
                        Trigger.executionWindow(SYNC_INTERVAL_SECONDS,
                                SYNC_INTERVAL_SECONDS + SYNC_FLEXTIME_SECONDS
                        ))
                .setReplaceCurrent(true)
                .build();
        /*Scheduling the above job with Dispatcher*/
        jobDispatcher.schedule(job);
    }

    synchronized public static void initialize(final Context context){
           /*
         * Only perform initialization once per app lifetime. If initialization has already been
         * performed, we have nothing to do in this method.
         */
        Log.d(SYNC_TAG, "Initialized....");
        if (isInitialized) return;

        isInitialized = true;

         /*
         * This method call triggers Sunshine to create its task to synchronize weather data
         * periodically.
         */
        scheduleFirebaseJobDispatcher(context);

        Thread checkingIfCursorEmpty = new Thread(new Runnable() {
            @Override
            public void run() {

                Uri uri = NewsContract.NewsEntry.CONTENT_URI;

                String[] projection = new String[]{NewsContract.NewsEntry._ID};
                Cursor cursor = context.getContentResolver().query(
                        uri,
                        projection,
                        null,null,null);
                Log.d(SYNC_TAG,cursor.toString());
                if (null == cursor && cursor.getCount() == 0){
                    startServiceImmediately(context);
                    Log.d(SYNC_TAG,"Cursor is null ");
                }else {
                    Log.d(SYNC_TAG,"Cursor is not null");
                }
                cursor.close();
            }
        });

        checkingIfCursorEmpty.start();
    }
    public static void startServiceImmediately(final Context context){
        Log.d(SYNC_TAG,"calling NewsSyncService ....");
        Intent intentToStartService = new Intent(context,NewsSyncService.class);
        context.startService(intentToStartService);
    }
}
