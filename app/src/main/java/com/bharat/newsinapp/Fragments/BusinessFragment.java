package com.bharat.newsinapp.Fragments;


import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;


import android.util.Log;

import com.bharat.newsinapp.DetailedActivity;

import com.bharat.newsinapp.Helper.NewsRecyclerAdapter;

import com.bharat.newsinapp.data.NewsContract;



public class BusinessFragment extends BaseFragment implements NewsRecyclerAdapter.NewsRecyclerAdapterOnClickHandler{


    private String TAG=getClass().getName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CONTENT_QUERY_URI = NewsContract.NewsEntry.CONTENT_URI;
        newsRecyclerAdapter = new NewsRecyclerAdapter(getContext(),this);
        Log.d(TAG,"onCreate()");
    }

    @Override
    public void onClick(int newsId) {
        Intent intent=new Intent(getContext(),DetailedActivity.class);
        Uri uri = NewsContract.NewsEntry.CONTENT_URI.buildUpon().appendPath(String.valueOf(newsId)).build();
        intent.setData(uri);
        startActivity(intent);
    }

   /* private class NewsAsync extends AsyncTask<String,Void,List<News>>{

        @Override
        protected List<News> doInBackground(String... params) {
            if (params.length < 1 || params[0]==null){
                return null;
            }
            List<News> newses= QueryUtils.fetchNewsData(params[0]);
            return newses;
        }
        @Override
        protected void onPostExecute(List<News> newses) {
            progressSpinner.setVisibility(GONE);

            adapter.clear();
            if (newses !=null && !newses.isEmpty()){
                adapter.addAll(newses);
            }
        }


    }*/


}
