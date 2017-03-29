package com.bharat.newsinapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailedActivity extends AppCompatActivity  {


    private TextView describe,urlToSource,detail_title;
    private String title, description, urlToImage, uriToSource;
    private ImageView imageView;
    private Toolbar toolbar;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        detail_title= (TextView) findViewById(R.id.tv1);
        describe= (TextView) findViewById(R.id.description);
        urlToSource= (TextView) findViewById(R.id.urlToSource);
        imageView= (ImageView) findViewById(R.id.imageInDetailedView);
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbarLayout= (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);


        Intent intent =getIntent();
        title=intent.getStringExtra("title");
        description=intent.getStringExtra("description");
        urlToImage=intent.getStringExtra("urlToImage");
        uriToSource=intent.getStringExtra("uri");
        detail_title.setText(title);
        collapsingToolbarLayout.setTitle(title);
        final Uri uri= Uri.parse(uriToSource);
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        describe.setText(description);
        Glide.with(getApplicationContext()).load(urlToImage).into(imageView);


        urlToSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,uri));
            }
        });




    }

    public static void start(Context c){
        c.startActivity(new Intent(c,DetailedActivity.class));
    }
}
