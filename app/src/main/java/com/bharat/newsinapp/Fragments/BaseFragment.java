package com.bharat.newsinapp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.bharat.newsinapp.DetailedActivity;

import com.bharat.newsinapp.Helper.NewsRecyclerAdapter;
import com.bharat.newsinapp.MainActivity;
import com.bharat.newsinapp.R;
import com.bharat.newsinapp.data.NewsContract;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

/**
 * Created by Bharat on 9/3/2017.
 */

public class BaseFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {


    public NewsRecyclerAdapter newsRecyclerAdapter;
    public RecyclerView recyclerView;
    public ProgressBar progressSpinner;
    public LinearLayout no_internet_layput;
    public int mPosition = RecyclerView.NO_POSITION;

    public  Uri CONTENT_QUERY_URI = NewsContract.NewsEntry.CONTENT_URI;

    public final int  NEWS_LOADER_ID=0;

    private String TAG=getClass().getName();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragement_layout,container,false);
        recyclerView= (RecyclerView) view.findViewById(R.id.recyclerView);
        progressSpinner = (ProgressBar)view.findViewById(R.id.progressSpinnerBusiness);
        no_internet_layput= (LinearLayout) view.findViewById(R.id.no_internet_layout_business);
        Log.d(TAG,"onCreateView()");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);


        recyclerView.setAdapter(newsRecyclerAdapter);

        ConnectivityManager connectivityManager=(ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        boolean isNetworkActive=networkInfo!=null&&networkInfo.isConnectedOrConnecting();
/*
        if (isNetworkActive){


        }else {
            progressSpinner.setVisibility(GONE);
            no_internet_layput.setVisibility(VISIBLE);

        }*/


        getLoaderManager().initLoader(NEWS_LOADER_ID,null,this);
        Log.d(TAG,"onActivityCreated()");
    }



    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (NEWS_LOADER_ID){
            case NEWS_LOADER_ID:
                Uri queryUri = CONTENT_QUERY_URI;

                return new CursorLoader(getContext(),
                        queryUri,
                        MainActivity.MAIN_PROJECTION,
                        null,null,null);
            default:
                throw new RuntimeException("Loader not implemented " +id);
        }

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        newsRecyclerAdapter.setNewsData(data);
        if (mPosition == RecyclerView.NO_POSITION) mPosition = 0;
        recyclerView.smoothScrollToPosition(mPosition);
        if (data.getCount() > 0){
            progressSpinner.setVisibility(INVISIBLE);
            recyclerView.setVisibility(VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        invalidateData();
    }
    private void invalidateData(){
        newsRecyclerAdapter.setNewsData(null);
    }


}
