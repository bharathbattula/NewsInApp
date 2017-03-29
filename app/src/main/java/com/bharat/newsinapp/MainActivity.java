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

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar= (Toolbar) findViewById(R.id.toolbar);
        tabLayout= (TabLayout) findViewById(R.id.tabs);
        viewPager= (ViewPager) findViewById(R.id.viewpager);

        setUpViewPagerAdapter(viewPager);
        tabLayout.setupWithViewPager(viewPager);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.info_menu){
            startActivity(new Intent(getApplicationContext(),Info.class));
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
