package com.bharat.newsinapp;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
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

        describe= (TextView) findViewById(R.id.description);
        urlToSource= (TextView) findViewById(R.id.urlToSource);
        imageView= (ImageView) findViewById(R.id.imageInDetailedView);
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbarLayout= (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        Intent intent =getIntent();
        title=intent.getStringExtra("title");
        description=intent.getStringExtra("description");
        urlToImage=intent.getStringExtra("urlToImage");
        uriToSource=intent.getStringExtra("uri");
        collapsingToolbarLayout.setTitle(title);

        Glide.with(getApplicationContext()).load(urlToImage).into(imageView);
        describe.setText(description);







        urlToSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                  startActivity(new Intent(Intent.ACTION_VIEW,uri));
            Intent intent=new Intent(getApplicationContext(),BrowserActivity.class);
                intent.putExtra("url",uriToSource);
                startActivity(intent);
            }
        });




    }

    public static void start(Context c){
        c.startActivity(new Intent(c,DetailedActivity.class));
    }
}
