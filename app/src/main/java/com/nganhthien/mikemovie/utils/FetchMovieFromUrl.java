package com.nganhthien.mikemovie.utils;

import android.util.Log;

import com.nganhthien.mikemovie.data.model.Movie;
import com.nganhthien.mikemovie.data.source.MovieDataSource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FetchMovieFromUrl extends BaseFetchDataFromUrl<Movie>{

    private MovieDataSource.OnFetchDataListener mListener;

    public FetchMovieFromUrl(MovieDataSource.OnFetchDataListener listener) {
        mListener = listener;
    }

    @Override
    protected List<Movie> doInBackground(String... strings) {
        try {
            return extractDataFromJson(getJsonStringFromUrl(strings[0]));
        } catch (JSONException e) {
            if (mListener != null) {
                mListener.onFetchPopularMoviesFailed(e);
            }
        } catch (IOException e) {
            if (mListener != null) {
                mListener.onFetchPopularMoviesFailed(e);
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        Log.d("FETching", "onPostExecute: " + movies.toString());
        if (mListener == null) {
            return;
        }
        mListener.onFetchPopularMoviesSuccess(movies);
    }

    @Override
    protected List<Movie> extractDataFromJson(String dataJSON) throws JSONException {
        List<Movie> movies = new ArrayList<>();

        JSONObject baseJsonObject = new JSONObject(dataJSON);
        JSONArray resultsArray =
                baseJsonObject.getJSONArray(Constants.MovieApi.API_MOVIEDB_JSONKEY_RESULTS);
        for (int i = 0; i < resultsArray.length(); i++) {
            JSONObject currentMovie = resultsArray.getJSONObject(i);
            int id = currentMovie.getInt(Constants.MovieApi.API_MOVIEDB_JSONKEY_ID);
            String name = currentMovie.getString(Constants.MovieApi.API_MOVIEDB_JSONKEY_TITLE);
            String overview = currentMovie.getString(Constants.MovieApi.API_MOVIEDB_JSONKEY_OVERVIEW);
            Movie movie = new Movie(id, name);
            movie.setOverview(overview);
            movies.add(movie);
        }
        Log.d("CCC", "extractDataFromJson: " + movies.toString());
        return movies;
    }
}
