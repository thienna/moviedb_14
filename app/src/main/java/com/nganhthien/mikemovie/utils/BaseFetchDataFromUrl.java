package com.nganhthien.mikemovie.utils;

import android.os.AsyncTask;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public abstract class BaseFetchDataFromUrl<T> extends AsyncTask<String, Void, List<T>> {

    @Override
    protected abstract List<T> doInBackground(String... strings);

    @Override
    protected abstract void onPostExecute(List<T> genres);

    protected abstract List<T> extractDataFromJson(String dataJSON) throws JSONException;

    protected String getJsonStringFromUrl(String stringUrl) throws IOException {
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

    protected URL createUrl(String stringUrl) throws MalformedURLException {
        return new URL(stringUrl);
    }

    protected String readFromStream(InputStream inputStream) throws IOException {
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
