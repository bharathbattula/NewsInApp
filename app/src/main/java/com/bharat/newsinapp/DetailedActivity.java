package com.bharat.newsinapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailedActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {

    private static final int PERCENTAGE_TO_SHOW_IMAGE = 20;
    private View mFab;
    private int mMaxScrollSize;
    private boolean mIsImageHidden;
    private TextView describe,urlToSource;
    private String title;
    private String description;
    private String urlToImage;
    private String uriToSource;

    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        mFab=findViewById(R.id.fabButton);
        describe= (TextView) findViewById(R.id.description);
        urlToSource= (TextView) findViewById(R.id.urlToSource);
        imageView= (ImageView) findViewById(R.id.imageInDetailedView);

        Intent intent =getIntent();
        title=intent.getStringExtra("title");
        description=intent.getStringExtra("description");
        urlToImage=intent.getStringExtra("urlToImage");
        uriToSource=intent.getStringExtra("uri");

        final Uri uri= Uri.parse(uriToSource);
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getActionBar().setTitle(title);
        toolbar.setTitle(title);
        describe.setText(description);
        Glide.with(getApplicationContext()).load(urlToImage).into(imageView);


        AppBarLayout appBarLayout= (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.addOnOffsetChangedListener(this);

        urlToSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,uri));
            }
        });

    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (mMaxScrollSize==0)
            mMaxScrollSize=appBarLayout.getTotalScrollRange();
        int currentScrollPercentage=(Math.abs(verticalOffset))*100 /mMaxScrollSize;

        if (currentScrollPercentage >=PERCENTAGE_TO_SHOW_IMAGE){
            if (!mIsImageHidden){
                mIsImageHidden=true;
                ViewCompat.animate(mFab).scaleY(0).scaleX(0).start();
            }
        }
        if (currentScrollPercentage < PERCENTAGE_TO_SHOW_IMAGE){
            if (mIsImageHidden){
                mIsImageHidden=false;
                ViewCompat.animate(mFab).scaleY(1).scaleX(1).start();
            }
        }
    }
    public static void start(Context c){
        c.startActivity(new Intent(c,DetailedActivity.class));
    }
}
