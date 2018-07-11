package com.nganhthien.mikemovie.utils;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.nganhthien.mikemovie.data.model.Movie;
import com.nganhthien.mikemovie.data.source.MovieDataSource;

public class AddFavoriteLocal extends BaseGetDataFromLocal<Movie> {
    private MovieDataSource.OnAddFavoriteListener mListener;
    private SQLiteDatabase mDatabase;
    private Movie mMovie;

    public AddFavoriteLocal(Movie movie, MovieDataSource.OnAddFavoriteListener listener,
                                   SQLiteDatabase db) {
        mListener = listener;
        mDatabase = db;
        mMovie = movie;
        mWrapperException = new WrapperException();
    }

    @Override
    protected Movie doInBackground(String... strings) {
        try {
            return getResult();
        } catch (Exception e) {
            mWrapperException.setException(e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Movie movie) {
        if (mListener == null) {
            return;
        }
        if (mWrapperException.getException() == null && movie != null) {
            mListener.onAddFavoriteSuccess(movie);
        } else {
            mListener.onAddFavoriteFailed(mWrapperException.getException());
        }
    }

    private Movie getResult() throws Exception {
        mDatabase.beginTransaction();
        ContentValues values = new ContentValues();
        values.put(Movie.DatabaseConfig.ID, mMovie.getId());
        values.put(Movie.DatabaseConfig.TITLE, mMovie.getTitle());
        values.put(Movie.DatabaseConfig.VOTE_AVERAGE, mMovie.getVoteAverage());
        values.put(Movie.DatabaseConfig.POSTER_PATH, mMovie.getPosterImage());
        values.put(Movie.DatabaseConfig.BACKDROP_PATH, mMovie.getBackdropImage());
        values.put(Movie.DatabaseConfig.OVERVIEW, mMovie.getOverview());
        values.put(Movie.DatabaseConfig.RELEASE_DATE, mMovie.getReleaseDate());
        long result = mDatabase.insert(Movie.DatabaseConfig.TABLE_NAME, null, values);
        mDatabase.setTransactionSuccessful();
        mDatabase.endTransaction();
        mDatabase.close();

        if (result == -1) {
            return null;
        }
        return mMovie;
    }
}
