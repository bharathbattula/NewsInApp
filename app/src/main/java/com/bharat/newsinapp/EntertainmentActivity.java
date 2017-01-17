package com.bharat.newsinapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class EntertainmentActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    NewsAdapter adapter;
    ListView listNews;
    ProgressBar progressSpinner;
    LinearLayout no_internet_layput;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    private String REQUEST_URL="https://newsapi.org/v1/articles?source=the-lad-bible&sortBy=top&apiKey=5bfecae129c44fa389435196ec54adf1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entertainment);
        listNews= (ListView) findViewById(R.id.newsList);
        progressSpinner = (ProgressBar) findViewById(R.id.progressSpinner);
        adapter=new NewsAdapter(this,new ArrayList<News>());
        no_internet_layput= (LinearLayout) findViewById(R.id.no_internet_layout);


        drawerLayout= (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView= (NavigationView) findViewById(R.id.navigationView);
        toggle=new ActionBarDrawerToggle(this,drawerLayout,0,0);
        toggle.syncState();
        drawerLayout.addDrawerListener(toggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(this);


        ConnectivityManager connectivityManager=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        boolean isNetworkActive=networkInfo!=null&&networkInfo.isConnectedOrConnecting();





        listNews.setAdapter(adapter);

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
                Uri url=Uri.parse(currentNews.getUrl());
                startActivity(new Intent(Intent.ACTION_VIEW,url));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_layout,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_business:
                startActivity(new Intent(this,MainActivity.class));
                break;
            case R.id.menu_science:
                startActivity(new Intent(this,ScienceActivity.class));
                break;
            case R.id.menu_sports:
                startActivity(new Intent(this,SportsActivity.class));
                break;
            case R.id.menu_technology:
                startActivity(new Intent(this,TechnologyAvtivity.class));
                break;

        }
        if (toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_business:
                Toast.makeText(this, "Business", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,MainActivity.class));
                break;
            case R.id.menu_entertainment:
                Toast.makeText(this, "Entertainment", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_science:
                Toast.makeText(this, "Science", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,ScienceActivity.class));
                break;
            case R.id.menu_sports:
                Toast.makeText(this, "Sports", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,SportsActivity.class));
                break;
            case R.id.menu_technology:
                Toast.makeText(this, "Technology", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,TechnologyAvtivity.class));
                break;

        }
        drawerLayout= (DrawerLayout) findViewById(R.id.drawerLayout);
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }


    private class NewsAsync extends AsyncTask<String,Void,List<News>> {

        @Override
        protected List<News> doInBackground(String... params) {
            if (params.length < 1 || params[0]==null){
                return null;
            }
            List<News> newses=QueryUtils.fetchNewsData(params[0]);
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
