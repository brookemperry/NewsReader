package com.example.android.newsreader;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<News>> {

    //Query URL
    private String mUrl;

    //Construct a new loader
    public NewsLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        //Perform the network request, parse the response & extract the list of articles
        List<News> articles = QueryUtils.fetchNewsData(mUrl);
        return articles;
    }
}
