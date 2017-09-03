package com.bharat.newsinapp.Fragments;


import android.os.Bundle;

import android.util.Log;

import com.bharat.newsinapp.R;

public class BusinessFragment extends BaseFragment{


    private String TAG=getClass().getName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        REQUEST_URL=  getResources().getString(R.string.url_with_apikey)+"&"+getResources().getString(R.string.sort)+"&"+getResources().getString(R.string.business_source);
        Log.d(TAG,"onCreate()");
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
