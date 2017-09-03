package com.bharat.newsinapp.Fragments;

import android.os.Bundle;

import android.util.Log;

import com.bharat.newsinapp.R;


public class TechnologyFragment extends BaseFragment {

    private String TAG=getClass().getName();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        REQUEST_URL=  getResources().getString(R.string.url_with_apikey)+"&"+getResources().getString(R.string.sort)+"&"+getResources().getString(R.string.technology_source);
        Log.d(TAG,"onCreate()");
    }


}
