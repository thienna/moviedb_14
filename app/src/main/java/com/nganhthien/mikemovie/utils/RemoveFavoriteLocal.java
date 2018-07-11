package com.nganhthien.mikemovie.utils;

import android.database.sqlite.SQLiteDatabase;

import com.nganhthien.mikemovie.data.model.Movie;
import com.nganhthien.mikemovie.data.source.MovieDataSource;

public class RemoveFavoriteLocal extends BaseGetDataFromLocal<Movie> {
    private MovieDataSource.OnRemoveFavoriteListener mListener;
    private SQLiteDatabase mDatabase;
    private Movie mMovie;

    public RemoveFavoriteLocal(Movie movie, MovieDataSource.OnRemoveFavoriteListener listener,
                            SQLiteDatabase db) {
        mListener = listener;
        mDatabase = db;
        mMovie = movie;
        mWrapperException = new WrapperException();
    }

    @Override
    protected Movie doInBackground(String... strings) {
        try {
            return numbOfRow();
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
            mListener.onRemoveFavoriteSuccess(movie);
        } else {
            mListener.onRemoveFavoriteFailed(mWrapperException.getException());
        }
    }

    private Movie numbOfRow() throws Exception {
        mDatabase.beginTransaction();
        String selection = Movie.DatabaseConfig.ID + Constants.DatabaseConfig.QUERY_SELECTION;
        String[] selectionArgs = {String.valueOf(mMovie.getId())};
        int numb = mDatabase.delete(Movie.DatabaseConfig.TABLE_NAME, selection, selectionArgs);
        mDatabase.setTransactionSuccessful();
        mDatabase.endTransaction();
        mDatabase.close();
        if (numb == 0){
            return null;
        }
        return mMovie;
    }
}
