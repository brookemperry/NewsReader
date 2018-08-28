package com.example.android.newsreader;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {

    public static final String LOG_TAG = QueryUtils.class.getSimpleName();

    //Returns new URL object from the given string URL.
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {

        }
        return url;
    }


    // Make an HTTP request to the given URL and return a String as the response.
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }


    //Return an {@link List<News>} object
    private static List<News> extractFeatureFromJson(String newsJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(newsJSON)) {
            return null;
        }
        //Create an empty ArryList that we can add earthquakes to
        List<News> articles = new ArrayList<>();

        //Try to parse the JSON response string. If there is a problem an exception will be thrown
        //Catch the JSON exception so the app doesn't crash


        try {
            JSONObject baseJsonResponse = new JSONObject(newsJSON);
            //Extract JSONObject associated with key called "response"
            JSONObject articleObject = baseJsonResponse.getJSONObject("response");
            //Extract JSONArray associated with key called "results"
            JSONArray articleArray = baseJsonResponse.getJSONArray("results");

            //For each earthquake in the array create a Quake object
            for (int i = 0; i < articleArray.length(); i++) {
                // Get a single earthquake at position i within the list of earthquakes
                JSONObject currentNews = articleArray.getJSONObject(i);
//todo should this be JSONArray?
                JSONObject results = currentNews.getJSONObject("results");


                // Extract out the title, section date and url values
                String title = results.getString("webTitle");
                String section = results.getString("sectionName");
                String date = results.getString("webPublicationDate");
                String url = results.getString("webUrl");

                // Create a new {@link News} object with title, section, date & url
                News article = new News(title, section, date, url);
                //Add the new Quake to the list of Quakes
                articles.add(article);
            }
        } catch (JSONException e) {

        }
        return articles;
    }

    public static List<News> fetchNewsData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        // Extract relevant fields from the JSON response and create an {@link News} object
        List<News> articles = extractFeatureFromJson(jsonResponse);


        return articles;
    }
}

