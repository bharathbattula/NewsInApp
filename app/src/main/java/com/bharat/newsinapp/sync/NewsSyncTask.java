package com.bharat.newsinapp.sync;

/**
 * Created by Bharat on 9/13/2017.
 */
import android.content.*;
import android.net.Uri;
import android.text.format.DateUtils;
import android.util.Log;

import com.bharat.newsinapp.R;
import com.bharat.newsinapp.Utils.NotificationUtils;
import com.bharat.newsinapp.Utils.QueryUtils;
import com.bharat.newsinapp.data.NewsContract;
import com.bharat.newsinapp.data.NewsPreference;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

public class NewsSyncTask {

    public  static final int BUSINESS = 1001;
    public  static final int ENTERTAINMENT = 1002;
    public  static final int SCIENCE = 1003;
    public  static final int SPORTS = 1004;
    public  static final int TECHNOLOGY = 1005;

    private static final String QUERY_SORT_KEY = "sortBy";
    private static final String QUERY_SOURCE_KEY = "source";
    private static final String QUERY_API_KEY = "apiKey";
    private static Uri CONTENT_URI ;


    synchronized public static void callToSyncData(Context applicationContext,int code){
        final Context context = applicationContext;
        Uri CONTENT_URI;
        switch (code){
            case BUSINESS:
                String sourceBusinessArticle = context.getResources().getString(R.string.business_source);
                CONTENT_URI = NewsContract.NewsEntry.CONTENT_URI;
                NewsSyncTask.syncNews(context,sourceBusinessArticle,CONTENT_URI);
                break;
            case ENTERTAINMENT:
                String entertainmentSource = context.getResources().getString(R.string.entertainment_source);
                CONTENT_URI = NewsContract.EntertainmentEntry.CONTENT_URI;
                NewsSyncTask.syncNews(context,entertainmentSource,CONTENT_URI);
                break;
            case SCIENCE:
                String scienceSource = context.getResources().getString(R.string.science_source);
                CONTENT_URI = NewsContract.ScienceEntry.CONTENT_URI;
                NewsSyncTask.syncNews(context,scienceSource,CONTENT_URI);
                break;
            case SPORTS:
                String sportsSource = context.getResources().getString(R.string.sports_source);
                CONTENT_URI = NewsContract.SportsEntry.CONTENT_URI;
                NewsSyncTask.syncNews(context,sportsSource,CONTENT_URI);
                break;
            case TECHNOLOGY:
                String techSource = context.getResources().getString(R.string.technology_source);
                CONTENT_URI = NewsContract.TechnologyEntry.CONTENT_URI;
                NewsSyncTask.syncNews(context,techSource,CONTENT_URI);
                break;
            default:
                throw new IllegalArgumentException("Code dosn't matched : "+code);
        }
    }

    synchronized static void syncNews(Context context,String sourceOfArticle,Uri uri) {
        try {

            /*Getting URL object from String */
            CONTENT_URI  = uri;
            String urlString = buildUri(context,sourceOfArticle);
            URL url = QueryUtils.createUrl(urlString);

            /*Getting Json Response as srring from host */
            String jsonResponse = QueryUtils.makeHttpRequest(url);

            /*Parsing jsonResonse and getting ContentValues array*/
            ContentValues[] newsContentValues = QueryUtils.extractContentValuesFromResponse(jsonResponse);


            if (newsContentValues != null && newsContentValues.length != 0) {

                ContentResolver contentResolver = context.getContentResolver();

                /*Delete old data from database if newsContentValues is not null*/
                contentResolver.delete(CONTENT_URI,
                        null,
                        null);
                /*Inserting new data from server*/
                contentResolver.bulkInsert(CONTENT_URI,
                        newsContentValues);


                /*Check if Notifications are enabled */
                boolean notificationsEnabled = NewsPreference.areNotificationsEnbled(context);

                if (notificationsEnabled){
                    NotificationUtils.notifyUser(context);
                }

                  /*
                 * If the last notification was shown was more than 1 day ago, we want to send
                 * another notification to the user that the weather has been updated. Remember,
                 * it's important that you shouldn't spam your users with notifications.
                 */
                long timeSinceLastNotification  = NewsPreference.getLastElapsedNotificationTime(context);

                boolean oneDayPassedSinceLastNotification  = false;
                if (timeSinceLastNotification >= DateUtils.DAY_IN_MILLIS){
                    oneDayPassedSinceLastNotification = true;
                }
                /*If more than one day passed and notification enabled notify the user*/
                if (notificationsEnabled && oneDayPassedSinceLastNotification){
                    NotificationUtils.notifyUser(context);
                }
            }
        }catch (IOException io){
            io.getStackTrace();
        }catch (JSONException je){
            je.getStackTrace();
        }catch (Exception e){
            e.getStackTrace();
        }
    }

    public static String buildUri(Context context, String sourceOfArticle){
        String baseUri = context.getString(R.string.base_url);
        String source = sourceOfArticle;
        String sort = context.getString(R.string.sort);
        String apiKey = context.getString(R.string.api_key);
        Uri uri = Uri.parse(baseUri).buildUpon()
                .appendQueryParameter(QUERY_API_KEY,apiKey)
                .appendQueryParameter(QUERY_SOURCE_KEY,source)
                .appendQueryParameter(QUERY_SORT_KEY,sort).build();
        return uri.toString();
    }
}
