package com.nganhthien.mikemovie.utils;

import android.os.AsyncTask;

import com.nganhthien.mikemovie.data.model.Genre;
import com.nganhthien.mikemovie.data.source.GenreDataSource;

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

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by ThienNA on 02/07/2018.
 */

public class FetchGenreFromUrl extends AsyncTask<String, Void, List<Genre>> {

    private GenreDataSource.OnFetchGenreCallback mCallback;

    public FetchGenreFromUrl(GenreDataSource.OnFetchGenreCallback callback) {
        mCallback = callback;
    }

    @Override
    protected List<Genre> doInBackground(String... strings) {
        try {
            return extractGenreFromJson(getJsonStringFromUrl(strings[0]));
        } catch (JSONException e) {
            if (mCallback != null) {
                mCallback.onFetchGenreFailed(e);
            }
        } catch (IOException e) {
            if (mCallback != null) {
                mCallback.onFetchGenreFailed(e);
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(List<Genre> genres) {
        if (mCallback == null) {
            return;
        }

        mCallback.onFetchGenreSuccess(genres);
    }

    private List<Genre> extractGenreFromJson(String genreJSON) throws JSONException {
        List<Genre> genres = new ArrayList<>();

        JSONObject baseJsonResponse = new JSONObject(genreJSON);
        JSONArray genreJsonArray =
                baseJsonResponse.getJSONArray(Constants.MovieApi.API_MOVIEDB_JSONKEY_GENRES);

        for (int i = 0; i < genreJsonArray.length(); i++) {
            JSONObject currentObject = genreJsonArray.getJSONObject(i);
            int id = currentObject.getInt(Constants.MovieApi.API_MOVIEDB_JSONKEY_ID);
            String name = currentObject.getString(Constants.MovieApi.API_MOVIEDB_JSONKEY_NAME);
            genres.add(new Genre(id, name));
        }

        return genres;
    }

    private String getJsonStringFromUrl(String stringUrl) throws IOException {
        URL url = createUrl(stringUrl);
        String jsonString;
        InputStream inputStream;

        // Connection configuration and Launch
        HttpURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setRequestMethod(Constants.UrlConfig.URL_METHOD_GET);
        connection.setReadTimeout(Constants.UrlConfig.URL_REQUEST_TIMEOUT);
        connection.setConnectTimeout(Constants.UrlConfig.URL_CONNECT_TIMEOUT);
        connection.connect();

        inputStream = connection.getInputStream();
        jsonString = readFromStream(inputStream);

        connection.disconnect();

        if (inputStream != null) {
            // Closing the input stream could throw an IOException, which is why
            // the makeHttpRequest(URL url) method signature specifies than an IOException
            // could be thrown.
            inputStream.close();
        }

        return jsonString;
    }

    private URL createUrl(String stringUrl) throws MalformedURLException {
        return new URL(stringUrl);
    }

    private String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder result = new StringBuilder();

        InputStreamReader inputStreamReader =
                new InputStreamReader(inputStream, Charset.forName(Constants.UrlConfig.URL_CONNECT_CHARSET));
        BufferedReader reader = new BufferedReader(inputStreamReader);
        String line = reader.readLine();

        while (line != null) {
            result.append(line);
            line = reader.readLine();
        }

        return result.toString();
    }
}
