package com.example.android.newsreader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.ArrayList;

public class NewsAdapter extends ArrayAdapter {

    private static final String TITLE_SEPARATOR = String.valueOf((R.string.title_separator));
    private static final String DATE_SEPARATOR = String.valueOf((R.string.date_separator));


    public NewsAdapter(@NonNull MainActivity context, ArrayList<News> articles) {
        super(context, 0, articles);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //Get the News item at this position  the list
        News currentNews = (News) getItem(position);

        String originalTitle = currentNews.getTitle();
        String title = null;
        String author = null;

        if (originalTitle.contains(TITLE_SEPARATOR)) {
            String[] parts = originalTitle.split(TITLE_SEPARATOR);
            title = parts[0];
            author = parts[1];
        }
        //time is stored here in case app is changed to include time in the future. It is not currently used.
        String originalDate = currentNews.getDate();
        String date = null;
        String time;
        String section = null;

        if (originalDate.contains(DATE_SEPARATOR)) {
            String[] parts = originalDate.split(DATE_SEPARATOR);
            date = parts[0];
            time = parts[1];
        }

        //Check if view is being reused, otherwise inflate
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }

        //Find the correct TextViews in item.xml and set the correct text in the TextViews.
        TextView titleTextView = listItemView.findViewById(R.id.title);
        titleTextView.setText(title);

        TextView authorTextView = listItemView.findViewById(R.id.author);
        authorTextView.setText(author);

        TextView sectionTextView = listItemView.findViewById(R.id.section);
        sectionTextView.setText(section);

        TextView dateTextView = listItemView.findViewById(R.id.date);
        dateTextView.setText(date);

        return listItemView;

    }
}
