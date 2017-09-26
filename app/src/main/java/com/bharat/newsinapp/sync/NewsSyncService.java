package com.bharat.newsinapp.sync;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import com.bharat.newsinapp.R;
import com.bharat.newsinapp.data.NewsContract;

/**
 * Created by Bharat on 9/13/2017.
 */

public class NewsSyncService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param //name Used to name the worker thread, important only for debugging.
     */
    public NewsSyncService() {
        super("NewsSyncService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d("NEWSSYNCSERVICE","In the onHandleIntent()");

        NewsSyncTask.callToSyncData(this,NewsSyncTask.BUSINESS);
        NewsSyncTask.callToSyncData(this,NewsSyncTask.ENTERTAINMENT);
        NewsSyncTask.callToSyncData(this,NewsSyncTask.SCIENCE);
        NewsSyncTask.callToSyncData(this,NewsSyncTask.SPORTS);
        NewsSyncTask.callToSyncData(this,NewsSyncTask.TECHNOLOGY);
    }
}
