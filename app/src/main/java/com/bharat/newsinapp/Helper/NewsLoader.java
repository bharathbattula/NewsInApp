package com.bharat.newsinapp.Helper;

import android.support.v4.content.AsyncTaskLoader;

import java.util.List;
import android.content.Context;

import com.bharat.newsinapp.Utils.QueryUtils;

/**
 * Created by Bharat on 6/2/2017.
 */

public class NewsLoader extends AsyncTaskLoader<List<News>> {
    private String mUrl;
    private List<News> newsList;

    public NewsLoader(Context context,String url){
        super(context);
        mUrl=url;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if (newsList != null){
            deliverResult(newsList);
        }else {
        forceLoad();
        }
    }

    @Override
    public List<News> loadInBackground() {
        if (mUrl==null){
            return null;
        }
        try {
            List<News> news= QueryUtils.fetchNewsData(mUrl);
            return news;
        }catch (Exception e){
            return null;
        }
    }
}
