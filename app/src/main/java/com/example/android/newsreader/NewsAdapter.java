package com.example.android.newsreader;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private ArrayList<News> mNews;

      public static class NewsViewHolder extends RecyclerView.ViewHolder{

          //| separates the title from the author if present
          private static final String TITLE_SEPERATOR = "|";

          //Title
          public TextView mTitle;

          //Author
          public TextView mAuthor;

          //Snippet
          public TextView mSnippet;

          //Section
          public TextView mSection;

          //date
          public TextView mDate;

        public NewsViewHolder(View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.title);
            mAuthor = itemView.findViewById(R.id.author);
            mSnippet = itemView.findViewById(R.id.snippet);
            mSection = itemView.findViewById(R.id.section);
            mDate = itemView.findViewById(R.id.date);
        }
    }

    public NewsAdapter(ArrayList<News> articles){
          mNews = articles;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent,false);
        NewsViewHolder newsViewHolder = new NewsViewHolder(v);
        return newsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
          News currentNews = mNews.get(position);
          holder.mTitle.setText(currentNews.getTitle());
          holder.mAuthor.setText(currentNews.getAuthor());
          holder.mSnippet.setText(currentNews.getAuthor());
          holder.mSection.setText(currentNews.getSection());
          holder.mDate.setText(currentNews.getDate());

    }

    @Override
    public int getItemCount() {
        return mNews.size();
    }
}
