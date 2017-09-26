package com.bharat.newsinapp.sync;

import android.net.Uri;
import android.os.AsyncTask;
import android.content.*;
import android.util.Log;

import com.bharat.newsinapp.R;
import com.bharat.newsinapp.data.NewsContract;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

/**
 * Created by Bharat on 9/13/2017.
 */

public class NewsSyncFirebaseService extends JobService {



    private AsyncTask<Void,Void,Void> mNewsAsyncTask;
    @Override
    public boolean onStartJob(final JobParameters job) {
        mNewsAsyncTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                Log.d("Job Service","started job");
                Context context = getApplicationContext();
                NewsSyncTask.callToSyncData(context,NewsSyncTask.BUSINESS);
                NewsSyncTask.callToSyncData(context,NewsSyncTask.ENTERTAINMENT);
                NewsSyncTask.callToSyncData(context,NewsSyncTask.SCIENCE);
                NewsSyncTask.callToSyncData(context,NewsSyncTask.SPORTS);
                NewsSyncTask.callToSyncData(context,NewsSyncTask.TECHNOLOGY);
                jobFinished(job,false);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                jobFinished(job,false);
            }
        };

        mNewsAsyncTask.execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        if (mNewsAsyncTask != null) {
            mNewsAsyncTask.cancel(true);
        }
        return true;
    }

}
