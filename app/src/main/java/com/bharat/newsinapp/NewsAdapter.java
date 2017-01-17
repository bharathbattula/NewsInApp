package com.bharat.newsinapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.bharat.newsinapp.News;
import com.bumptech.glide.Glide;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


/**
 * Created by Bharat on 1/10/2017.
 */

public class NewsAdapter extends ArrayAdapter<News> {
    public NewsAdapter(Context context, List<News> objects) {
        super(context, 0, objects);
    }
    TextView title,imageUrl,decription,postingDate;
    Bitmap b;
    ImageView imageView;
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=  convertView;
        if (view==null){
            view= LayoutInflater.from(getContext()).inflate(R.layout.news_list,parent,false);
        }
        News currentNews=getItem(position);
//        imageUrl= (TextView) view.findViewById(R.id.imageUrl);
        title= (TextView) view.findViewById(R.id.title);
//        decription= (TextView) view.findViewById(R.id.description);
        postingDate= (TextView) view.findViewById(R.id.postDate);
        imageView= (ImageView) view.findViewById(R.id.imageView);
        String imageUrl=currentNews.getUrlToImage();

        title.setText(currentNews.getTitle());
//        decription.setText(currentNews.getDescription());
        postingDate.setText(currentNews.getDate());

        Glide.with(getContext()).load(imageUrl).into(imageView);

        return view;
    }



}
