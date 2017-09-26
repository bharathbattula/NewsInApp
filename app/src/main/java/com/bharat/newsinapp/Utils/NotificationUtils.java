package com.bharat.newsinapp.Utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.content.res.*;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;
import android.content.*;

import com.bharat.newsinapp.DetailedActivity;
import com.bharat.newsinapp.MainActivity;
import com.bharat.newsinapp.R;
import com.bharat.newsinapp.data.NewsContract;
import com.bharat.newsinapp.data.NewsPreference;

/**
 * Created by Bharat on 9/19/2017.
 */

public class NotificationUtils {

    public static final String[] NEWS_NOTIFICATION_PROJECTION = new String[]{
            NewsContract.NewsEntry.COLUMN_TITLE,
            NewsContract.NewsEntry.COLUMN_DESCRIPTION,
            NewsContract.NewsEntry.COLUMN_IMAGE
    };

    public static final int INDEX_TITLE = 0;
    public static final int INDEX_DESCRIPTION = 1;
    public static final int INDEX_IMAGE = 2;

    private static final int NOTIFICATION_ID = 5001;

    public static void notifyUser(Context context) {

        Uri newsUri = NewsContract.NewsEntry.CONTENT_URI;

        Cursor cursor = context.getContentResolver().query(newsUri,
                NEWS_NOTIFICATION_PROJECTION,
                null,null,null);
        if (cursor.moveToFirst()){
            String newsTitle = cursor.getString(INDEX_TITLE);
            String newsDescription = cursor.getString(INDEX_DESCRIPTION);
            String imageUrl = cursor.getString(INDEX_IMAGE);

            Resources resources = context.getResources();
            int smallIconDrawble = R.drawable.article_notification;

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                    .setColor(ContextCompat.getColor(context,R.color.colorPrimary))
                    .setContentTitle(newsTitle)
                    .setContentText(newsDescription)
                    .setSmallIcon(smallIconDrawble)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(newsDescription))
                    .setAutoCancel(true);

            Intent intentForDetailActvity = new Intent(context, MainActivity.class);

            TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(context);
            taskStackBuilder.addNextIntentWithParentStack(intentForDetailActvity);
            PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(0 , PendingIntent.FLAG_UPDATE_CURRENT);

            notificationBuilder.setContentIntent(pendingIntent);

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(NOTIFICATION_ID,notificationBuilder.build());
            NewsPreference.saveLastNotificationTime(context,System.currentTimeMillis());

        }
        cursor.close();
    }
}
