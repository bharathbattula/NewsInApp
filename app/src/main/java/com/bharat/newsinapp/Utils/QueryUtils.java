package com.bharat.newsinapp.Utils;

import android.util.Log;

import com.bharat.newsinapp.Fragments.BusinessFragment;
import com.bharat.newsinapp.Helper.News;
import com.bharat.newsinapp.data.NewsContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import android.content.*;
/**
 * Created by Bharat on 1/10/2017.
 */

public final class QueryUtils {
    private static final String LOG_TAG =BusinessFragment.class.getName();

    private QueryUtils(){

    }

    private static final String JSON_ARTICLE_ARRAY = "articles";
    private static final String JSON_TITLE =  "title";
    private static final String JSON_DESCRIPTION = "description";
    private static final String JSON_URL_TO_IMAGE = "urlToImage";
    private static final String JSON_URL_TO_SOURCE = "url";
    private static final String JSON_PUBLISHED_DATE = "publishedAt";

    public static List<News> fetchNewsData(String requestedURL){
        Log.d("Loader","Query utils Fetch News Data method called");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        URL url=createUrl(requestedURL);

        String jsonString=null;
        try {
            jsonString = makeHttpRequest(url);
        }catch (Exception e){
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }
        List<News> news=extractFromResponse(jsonString);

        return news;

    }

    public static String makeHttpRequest(URL url) throws IOException {
    String jsonResponse = null;
        if (url == null){
            return null;
        }
        HttpURLConnection conn=null;
        InputStream inputStream=null;

        try {
            conn= (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.connect();
            if (conn.getResponseCode()==200){
                inputStream=conn.getInputStream();
                jsonResponse=readFromStream(inputStream);
            }else {
                Log.e(LOG_TAG, "Error response code: " + conn.getResponseCode());
            }

        } catch (IOException e) {
//            e.printStackTrace();
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        }finally {
            if (conn!= null) {
                conn.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }

        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output=new StringBuilder();
        if (inputStream!=null){
            InputStreamReader inputReader=new InputStreamReader(inputStream,Charset.forName("UTF-8"));
            BufferedReader bufferedReader=new BufferedReader(inputReader);
            String line=bufferedReader.readLine();
            while(line!=null){
                output.append(line);
                line=bufferedReader.readLine();
            }
        }

        return output.toString();
    }

    public static URL createUrl(String requestedURL) {
            URL url=null;
        try {
            url=new URL(requestedURL);
        } catch (MalformedURLException e) {
//            e.printStackTrace();
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }


    private static ArrayList<News> extractFromResponse(String jsonString) {
        ArrayList<News> news=new ArrayList<>();

       try {
            JSONObject jsonObject=new JSONObject(jsonString);
            JSONArray jsonArray=jsonObject.getJSONArray("articles");
            for (int i=0;i<jsonArray.length();i++){
                JSONObject currentObject=jsonArray.getJSONObject(i);
                
                String title=currentObject.getString("title");
                String description=currentObject.getString("description");
                String urlToImage=currentObject.getString("urlToImage");
                String postingDate=currentObject.getString("publishedAt");
                String url=currentObject.getString("url");
                News newsData=new News(title,description,urlToImage,postingDate,url);
                news.add(newsData);
            }

        } catch (JSONException e) {

            e.printStackTrace();

        }


        return news;
    }

    public static ContentValues[] extractContentValuesFromResponse(String jsonString) throws JSONException {

        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray jsonArray = jsonObject.getJSONArray(JSON_ARTICLE_ARRAY);

        ContentValues[] contentValues = new ContentValues[jsonArray.length()];

        for (int i = 0; i < jsonArray.length() ; i++){

            JSONObject currentObject = jsonArray.getJSONObject(i);
            ContentValues value = new ContentValues();

            String title=currentObject.getString(JSON_TITLE);
            String description=currentObject.getString(JSON_DESCRIPTION);
            String urlToImage=currentObject.getString(JSON_URL_TO_IMAGE);
            String postingDate=currentObject.getString(JSON_PUBLISHED_DATE);
            String url=currentObject.getString(JSON_URL_TO_SOURCE);

            value.put(NewsContract.NewsEntry.COLUMN_TITLE,title);
            value.put(NewsContract.NewsEntry.COLUMN_DESCRIPTION,description);
            value.put(NewsContract.NewsEntry.COLUMN_IMAGE,urlToImage);
            value.put(NewsContract.NewsEntry.COLUMN_URL_TO_SOURCE,url);
            value.put(NewsContract.NewsEntry.COLUMN_DATE,postingDate);

            contentValues[i] = value;
        }
        return contentValues;
    }

}
