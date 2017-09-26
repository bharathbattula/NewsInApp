package com.bharat.newsinapp.Fragments;


import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;


import android.util.Log;

import com.bharat.newsinapp.DetailedActivity;
import com.bharat.newsinapp.Helper.NewsRecyclerAdapter;

import com.bharat.newsinapp.data.NewsContract;


public class TechnologyFragment extends BaseFragment implements NewsRecyclerAdapter.NewsRecyclerAdapterOnClickHandler {

    private String TAG=getClass().getName();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CONTENT_QUERY_URI = NewsContract.TechnologyEntry.CONTENT_URI;
        newsRecyclerAdapter = new NewsRecyclerAdapter(getContext(),this);
        Log.d(TAG,"onCreate()");
    }

    @Override
    public void onClick(int newsId) {
        Intent intent=new Intent(getContext(),DetailedActivity.class);
        Uri uri = NewsContract.TechnologyEntry.CONTENT_URI.buildUpon().appendPath(String.valueOf(newsId)).build();
        intent.setData(uri);
        startActivity(intent);
    }
}
