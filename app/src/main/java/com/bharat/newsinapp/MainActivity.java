package com.bharat.newsinapp;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.bharat.newsinapp.Fragments.BusinessFragment;
import com.bharat.newsinapp.Fragments.EntertainmentFragment;
import com.bharat.newsinapp.Fragments.ScienceFragment;
import com.bharat.newsinapp.Fragments.SportsFragment;
import com.bharat.newsinapp.Fragments.TechnologyFragment;
import com.bharat.newsinapp.Helper.ViewPagerAdapter;
import com.bharat.newsinapp.data.NewsContract;
import com.bharat.newsinapp.sync.NewsSyncService;
import com.bharat.newsinapp.sync.NewsSyncUtils;

import android.content.*;
public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public static final String[] MAIN_PROJECTION={
                NewsContract.NewsEntry._ID,
                NewsContract.NewsEntry.COLUMN_TITLE,
                NewsContract.NewsEntry.COLUMN_DESCRIPTION,
                NewsContract.NewsEntry.COLUMN_IMAGE,
                NewsContract.NewsEntry.COLUMN_URL_TO_SOURCE,
                NewsContract.NewsEntry.COLUMN_DATE
    };
    public static int INDEX_NEWS_ID = 0;
    public static int INDEX_NEWS_TITLE = 1;
    public static int INDEX_NEWS_DESCRIPTION = 2;
    public static int INDEX_NEWS_IMAGE = 3;
    public static int INDEX_NEWS_SOURCE = 4;
    public static int INDEX_NEWS_DATE = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar= (Toolbar) findViewById(R.id.toolbar);
        tabLayout= (TabLayout) findViewById(R.id.tabs);
        viewPager= (ViewPager) findViewById(R.id.viewpager);

        setUpViewPagerAdapter(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        NewsSyncUtils.initialize(this);
    }

    private void insertToDatabase(){
        ContentValues values = new ContentValues();
        values.put(MAIN_PROJECTION[INDEX_NEWS_TITLE],"Hello");
        values.put(MAIN_PROJECTION[INDEX_NEWS_DESCRIPTION],"This is me");
        values.put(MAIN_PROJECTION[INDEX_NEWS_IMAGE],"http://smalldata.io/img/sdl_logo.png");
        values.put(MAIN_PROJECTION[INDEX_NEWS_SOURCE],"http://timesofindia.indiatimes.com/");
        values.put(MAIN_PROJECTION[INDEX_NEWS_DATE],"25/4/2016");
        getContentResolver().insert(NewsContract.NewsEntry.CONTENT_URI,values);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.info_menu){
            startActivity(new Intent(getApplicationContext(),SettingsActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    public void setUpViewPagerAdapter(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragments(new BusinessFragment(),getString(R.string.business_fragment));
        viewPagerAdapter.addFragments(new EntertainmentFragment(),getString(R.string.entertainment_fragment));
        viewPagerAdapter.addFragments(new ScienceFragment(),getString(R.string.science_fragment));
        viewPagerAdapter.addFragments(new SportsFragment(),getString(R.string.sports_fragment));
        viewPagerAdapter.addFragments(new TechnologyFragment(),getString(R.string.technology_fragment));
        viewPager.setAdapter(viewPagerAdapter);
    }
}
