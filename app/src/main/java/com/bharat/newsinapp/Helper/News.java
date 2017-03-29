package com.bharat.newsinapp.Helper;

/**
 * Created by Bharat on 1/10/2017.
 */

public class News {
private String title;
    private String description;
    private String url;
    private String urlToImage;
    private String date;

    public News(String title,String description,String urlToImage,String date,String url){
        this.title=title;
        this.description=description;
        this.date=date;
        this.url=url;
        this.urlToImage=urlToImage;
    }
    public String getDescription(){
        return description;
    }
    public String getTitle(){
        return title;
    }
    public String getUrl(){
        return url;
    }
    public String getUrlToImage(){
        return urlToImage;
    }
    public String getDate()
    {
        return date;
    }
}
