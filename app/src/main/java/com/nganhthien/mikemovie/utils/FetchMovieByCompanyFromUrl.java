package com.nganhthien.mikemovie.utils;

import com.nganhthien.mikemovie.data.model.Movie;
import com.nganhthien.mikemovie.data.source.MovieDataSource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FetchMovieByCompanyFromUrl extends BaseFetchDataFromUrl<List<Movie>> {
    private MovieDataSource.OnFetchDataListener mListener;

    public FetchMovieByCompanyFromUrl(MovieDataSource.OnFetchDataListener listener) {
        mListener = listener;
        mWrapperException = new WrapperException();
    }

    @Override
    protected List<Movie> doInBackground(String... strings) {
        try {
            return extractDataFromJson(getJsonStringFromUrl(strings[0]));
        } catch (JSONException e) {
            mWrapperException.setException(e);
        } catch (IOException e) {
            mWrapperException.setException(e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        if (mListener == null) {
            return;
        }
        if (mWrapperException.getException() == null) {
            mListener.onFetchMoviesSuccess(movies);
        } else {
            mListener.onFetchMoviesFailed(mWrapperException.getException());
        }
    }

    @Override
    protected List<Movie> extractDataFromJson(String dataJSON) throws JSONException {
        List<Movie> movies = new ArrayList<>();

        JSONObject baseJsonObject = new JSONObject(dataJSON);
        JSONArray resultsArray =
                baseJsonObject.getJSONArray(Movie.JsonKey.RESULTS);
        for (int i = 0; i < resultsArray.length(); i++) {
            // Current cursor
            JSONObject currentMovie = resultsArray.getJSONObject(i);

            int id = currentMovie.optInt(Movie.JsonKey.ID);
            String title = currentMovie.optString(Movie.JsonKey.TITLE);
            String overview = currentMovie.optString(Movie.JsonKey.OVERVIEW);
            int voteAverage = currentMovie.optInt(Movie.JsonKey.VOTE_COUNT);
            String posterPath = currentMovie.optString(Movie.JsonKey.POSTER_PATH);
            String backdropPath = currentMovie.optString(Movie.JsonKey.BACKDROP_PATH);
            String releaseDate = currentMovie.optString(Movie.JsonKey.RELEASE_DATE);

            Movie movie = new Movie(id, title);
            movie.setOverview(overview);
            movie.setVoteAverage(voteAverage);
            movie.setPosterImage(posterPath);
            movie.setBackdropImage(backdropPath);
            movie.setReleaseDate(releaseDate);

            movies.add(movie);
        }

        return movies;
    }
}
