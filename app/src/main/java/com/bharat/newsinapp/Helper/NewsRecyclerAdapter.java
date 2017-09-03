package com.bharat.newsinapp.Helper;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.*;
import android.widget.ImageView;
import android.widget.TextView;

import com.bharat.newsinapp.R;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Bharat on 9/3/2017.
 */

public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.NewsViewHolder>{

    private  List<News> newsList;
    private Context context;

    private final NewsRecyclerAdapterOnClickHandler mClickHandler;

    public interface NewsRecyclerAdapterOnClickHandler{
        void onClick(News news);
    }

    public NewsRecyclerAdapter(Context context,NewsRecyclerAdapterOnClickHandler clickHandler){
        this.context=context;
        mClickHandler=clickHandler;
    }

    private Context getContext(){
        return context;
    }
    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutItemId = R.layout.news_list;
        boolean shouldAttachToParentImmediately = false;
        LayoutInflater layoutInflater = LayoutInflater.from(context);


        View view = layoutInflater.inflate(layoutItemId,parent,shouldAttachToParentImmediately);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder newsViewHolder, int position) {
        News newsItemData = newsList.get(position);
        newsViewHolder.mTextView.setText(newsItemData.getTitle());
        ImageView imageView = newsViewHolder.mImageView;
        Glide.with(getContext()).load(newsItemData.getUrlToImage()).into(imageView);
    }

    @Override
    public int getItemCount() {
        if (newsList == null) return 0;
        return newsList.size();
    }

    public void setNewsData(List<News> newsData){
        newsList=newsData;
        notifyDataSetChanged();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView mImageView;
        public TextView mTextView;

        public NewsViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.imageView);
            mTextView = (TextView) itemView.findViewById(R.id.title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            News newsItem = newsList.get(adapterPosition);
            mClickHandler.onClick(newsItem);
        }
    }
}
