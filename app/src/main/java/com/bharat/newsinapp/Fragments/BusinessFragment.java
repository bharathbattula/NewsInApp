package com.bharat.newsinapp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.bharat.newsinapp.DetailedActivity;
import com.bharat.newsinapp.Helper.News;
import com.bharat.newsinapp.Helper.NewsAdapter;
import com.bharat.newsinapp.Utils.QueryUtils;
import com.bharat.newsinapp.R;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class BusinessFragment extends Fragment  {
    NewsAdapter adapter;
    ListView listNews;
    ProgressBar progressSpinner;
    LinearLayout no_internet_layput;


    private String REQUEST_URL;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_main,container,false);
        listNews= (ListView)view.findViewById(R.id.newsList);
        progressSpinner = (ProgressBar)view.findViewById(R.id.progressSpinner);
        no_internet_layput= (LinearLayout) view.findViewById(R.id.no_internet_layout);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


      REQUEST_URL=  getResources().getString(R.string.url_with_apikey)+"&"+getResources().getString(R.string.sort)+"&"+getResources().getString(R.string.business_source);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter=new NewsAdapter(getContext(),new ArrayList<News>());
        listNews.setAdapter(adapter);

        ConnectivityManager connectivityManager=(ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        boolean isNetworkActive=networkInfo!=null&&networkInfo.isConnectedOrConnecting();







        if (isNetworkActive){

            NewsAsync newsAsync=new NewsAsync();
            newsAsync.execute(REQUEST_URL);
        }else {
            progressSpinner.setVisibility(GONE);
            no_internet_layput.setVisibility(VISIBLE);

        }


        listNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News currentNews=adapter.getItem(position);
                String title=currentNews.getTitle();
                String decribe=currentNews.getDescription();
                String imageUrl=currentNews.getUrlToImage();
                String uri=currentNews.getUrl();
                Intent intent=new Intent(getContext(),DetailedActivity.class);
                intent.putExtra("title",title);
                intent.putExtra("description",decribe);
                intent.putExtra("urlToImage",imageUrl);
                intent.putExtra("uri",uri);
                startActivity(intent);
            }
        });

    }

    private class NewsAsync extends AsyncTask<String,Void,List<News>>{

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


    }


}
