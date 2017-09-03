package com.bharat.newsinapp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
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
import com.bharat.newsinapp.Helper.News;
import com.bharat.newsinapp.Helper.NewsLoader;
import com.bharat.newsinapp.Helper.NewsRecyclerAdapter;
import com.bharat.newsinapp.R;

import java.util.List;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

/**
 * Created by Bharat on 9/3/2017.
 */

public class BaseFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<News>>,NewsRecyclerAdapter.NewsRecyclerAdapterOnClickHandler {


    private NewsRecyclerAdapter newsRecyclerAdapter;
    private RecyclerView recyclerView;
    private ProgressBar progressSpinner;
    private LinearLayout no_internet_layput;


    public String REQUEST_URL;
    private final int  NEWS_LOADER_ID=0;

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

        newsRecyclerAdapter = new NewsRecyclerAdapter(getContext(),this);

        recyclerView.setAdapter(newsRecyclerAdapter);

        ConnectivityManager connectivityManager=(ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        boolean isNetworkActive=networkInfo!=null&&networkInfo.isConnectedOrConnecting();

        if (isNetworkActive){

            getLoaderManager().initLoader(NEWS_LOADER_ID,null,this);
        }else {
            progressSpinner.setVisibility(GONE);
            no_internet_layput.setVisibility(VISIBLE);

        }



        Log.d(TAG,"onActivityCreated()");
    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        return new NewsLoader(getContext(),REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {
        progressSpinner.setVisibility(INVISIBLE);
        newsRecyclerAdapter.setNewsData(data);
        if (data == null && data.isEmpty()){
            recyclerView.setVisibility(INVISIBLE);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        invalidateData();
    }

    private void invalidateData(){
        newsRecyclerAdapter.setNewsData(null);
    }

    @Override
    public void onClick(News news) {
        String title=news.getTitle();
        String decribe=news.getDescription();
        String imageUrl=news.getUrlToImage();
        String uri=news.getUrl();
        Intent intent=new Intent(getContext(),DetailedActivity.class);
        intent.putExtra("title",title);
        intent.putExtra("description",decribe);
        intent.putExtra("urlToImage",imageUrl);
        intent.putExtra("uri",uri);
        startActivity(intent);
    }
}
