package com.bharat.newsinapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bharat.newsinapp.Helper.News;
import com.bharat.newsinapp.data.NewsContract;
import com.bumptech.glide.Glide;

public class DetailedActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {


    private TextView describe,urlToSource,detail_title;
    private String title, description, urlToImage, uriToSource;
    private ImageView imageView;
    private Toolbar toolbar;
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    private Uri mUri;
    private static final int DETAILED_LOADER_ID = 11;

    public static final String[] DETAILED_PROJECTION = {
            NewsContract.NewsEntry.COLUMN_TITLE,
            NewsContract.NewsEntry.COLUMN_DESCRIPTION,
            NewsContract.NewsEntry.COLUMN_IMAGE,
            NewsContract.NewsEntry.COLUMN_URL_TO_SOURCE,
            NewsContract.NewsEntry.COLUMN_DATE
    };
    public static int INDEX_COLUMN_TITLE = 0;
    public static int INDEX_COLUMN_DESCRIPTION = 1;
    public static int INDEX_COLUMN_IMAGE = 2;
    public static int INDEX_COLUMN_SOURCE = 3;
    public static int INDEX_COLUMN_DATE = 4;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        describe= (TextView) findViewById(R.id.description);
        urlToSource= (TextView) findViewById(R.id.urlToSource);
        imageView= (ImageView) findViewById(R.id.imageInDetailedView);
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        mCollapsingToolbarLayout= (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);
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
        mUri = intent.getData();
        /*title=intent.getStringExtra("title");
        description=intent.getStringExtra("description");
        urlToImage=intent.getStringExtra("urlToImage");
        uriToSource=intent.getStringExtra("uri");
        collapsingToolbarLayout.setTitle(title);

        Glide.with(getApplicationContext()).load(urlToImage).into(imageView);
        describe.setText(description);*/
        if (mUri == null) throw new NullPointerException("URI for detailed activity cannot be null ");



        getSupportLoaderManager().initLoader(DETAILED_LOADER_ID,null,this);

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

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id){
            case DETAILED_LOADER_ID:
                return new CursorLoader(this,
                        mUri,
                        DETAILED_PROJECTION,
                        null,
                        null,null);
            default:
                throw new RuntimeException("Loader is not implemented "+id);
        }

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        boolean cursorHasValidData = false;
        if (data != null && data.moveToFirst()){
            cursorHasValidData = true;
        }

        if (!cursorHasValidData){
            return;
        }
        title = data.getString(INDEX_COLUMN_TITLE);
        description = data.getString(INDEX_COLUMN_DESCRIPTION);
        urlToImage = data.getString(INDEX_COLUMN_IMAGE);
        uriToSource = data.getString(INDEX_COLUMN_SOURCE);
        mCollapsingToolbarLayout.setTitle(title);
        Glide.with(getApplicationContext()).load(urlToImage).into(imageView);
        describe.setText(description);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
