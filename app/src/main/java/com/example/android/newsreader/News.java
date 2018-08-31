package com.example.android.newsreader;

import android.support.v7.widget.RecyclerView;

public class News {

    //website Url for the article
    private String mUrl;

    //Title of the article
    private String mTitle;


    //Author of the article
    private String mAuthor;

    //Section article comes from
    private String mSection;

    //Date article was published
    private String mDate;

    //Create a News object without author
    public News(String title, String section, String date, String url) {
        mTitle = title;
        mSection = section;
        mDate = date;
        mUrl = url;
    }

    //Create a News object with author
    public News(String title, String author, String section, String date, String url) {
        mTitle = title;
        mAuthor = author;
        mSection = section;
        mDate = date;
        mUrl = url;
    }

    public String getTitle(){
        return mTitle;
    }
    public String getAuthor(){
        return mAuthor;
    }

    public String getSection(){
        return mSection;
    }
    public String getDate(){
        return mDate;
    }
    public String getUrl(){
        return mUrl;
    }
}
