package com.bharat.newsinapp.Helper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bharat.newsinapp.R;
import com.bumptech.glide.Glide;
import java.util.List;
import java.text.*;
import java.util.*;


/**
 * Created by Bharat on 1/10/2017.
 */

public class NewsAdapter extends ArrayAdapter<News> {
    public NewsAdapter(Context context, List<News> objects) {
        super(context, 0, objects);
    }
    TextView title;
    ImageView imageView;
    String time;
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=  convertView;
        if (view==null){
            view= LayoutInflater.from(getContext()).inflate(R.layout.news_list,parent,false);
        }
        News currentNews=getItem(position);
        title= (TextView) view.findViewById(R.id.title);
        imageView= (ImageView) view.findViewById(R.id.imageView);
        String imageUrl=currentNews.getUrlToImage();
        title.setText(currentNews.getTitle());

        Glide.with(getContext()).load(imageUrl).into(imageView);
        time=currentNews.getDate();


        return view;
    }




}
