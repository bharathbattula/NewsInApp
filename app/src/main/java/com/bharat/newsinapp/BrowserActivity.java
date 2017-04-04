package com.bharat.newsinapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class BrowserActivity extends AppCompatActivity {

    private String url;
    private Toolbar toolbar;
    private WebView webView;
    private ProgressBar progressBar;
    private CoordinatorLayout coordinatorLayout;
    private float m_downX;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        toolbar= (Toolbar) findViewById(R.id.browserToolbar);
        webView= (WebView) findViewById(R.id.webView);
        progressBar= (ProgressBar) findViewById(R.id.progressbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        url=getIntent().getStringExtra("url");
        if (TextUtils.isEmpty(url)){
            finish();
        }

        initWebView();
        webView.loadUrl(url);
    }

    private void initWebView() {
        webView.setWebChromeClient(new MyWebChromeClient(this));
        webView.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
                invalidateOptionsMenu();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
                invalidateOptionsMenu();
            }


            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webView.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                progressBar.setVisibility(View.GONE);
                invalidateOptionsMenu();
            }
        });
        webView.clearCache(true);
        webView.clearHistory();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setHorizontalScrollBarEnabled(true);
        webView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getPointerCount()>1){
                    return true;
                }
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:{
                        m_downX = event.getX();
                    }
                    break;
                    case MotionEvent.ACTION_MOVE:
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:{
                        event.setLocation(m_downX,event.getY());
                    }
                    break;
                }
                return false;
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.browser_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (!webView.canGoBack()){
            menu.getItem(0).setEnabled(false);
            menu.getItem(0).getIcon().setAlpha(130);
        }else {
            menu.getItem(0).setEnabled(true);
            menu.getItem(0).getIcon().setAlpha(255);
        }
        if (!webView.canGoForward()){
            menu.getItem(1).setEnabled(false);
            menu.getItem(1).getIcon().setAlpha(130);
        }else {
            menu.getItem(1).setEnabled(true);
            menu.getItem(1).getIcon().setAlpha(255);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
        }
        if (item.getItemId()==R.id.action_back){
        back();
        }
        if (item.getItemId()==R.id.action_forward){
        forward();
        }
        return super.onOptionsItemSelected(item);
    }

    private void back() {
        if (webView.canGoBack()){
            webView.goBack();
        }
    }

    private void forward() {
        if (webView.canGoForward()){
            webView.goForward();
        }
    }

    private class MyWebChromeClient extends WebChromeClient {
        Context context;

        public MyWebChromeClient(Context context) {
            super();
            this.context = context;
        }
    }


}
