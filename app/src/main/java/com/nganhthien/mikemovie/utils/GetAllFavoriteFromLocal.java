package com.nganhthien.mikemovie.utils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nganhthien.mikemovie.data.model.Movie;
import com.nganhthien.mikemovie.data.source.MovieDataSource;

import java.util.ArrayList;
import java.util.List;

public class GetAllFavoriteFromLocal extends BaseGetDataFromLocal<List<Movie>> {
    private MovieDataSource.OnGetAllFavoriteListener mListener;
    private SQLiteDatabase mDatabase;

    public GetAllFavoriteFromLocal(MovieDataSource.OnGetAllFavoriteListener listener,
                                   SQLiteDatabase db) {
        mListener = listener;
        mDatabase = db;
        mWrapperException = new WrapperException();
    }

    @Override
    protected List<Movie> doInBackground(String... strings) {
        try {
            return getAll();
        } catch (Exception e) {
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
            mListener.onGetAllFavoriteSuccess(movies);
        } else {
            mListener.onGetAllFavoriteFailed(mWrapperException.getException());
        }
    }

    private List<Movie> getAll() throws Exception {
        List<Movie> movies = new ArrayList<>();

        String query = "SELECT * FROM " + Movie.DatabaseConfig.TABLE_NAME + " ORDER BY " +
                Movie.DatabaseConfig.TIMESTAMP + " DESC;";

        Cursor cursor = mDatabase.rawQuery(query, null);

        if (cursor.moveToNext()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(Movie.DatabaseConfig.ID));
                String title = cursor.getString(cursor.getColumnIndex(Movie.DatabaseConfig.TITLE));
                int voteAverage = cursor.getInt(cursor.getColumnIndex(Movie.DatabaseConfig
                        .VOTE_AVERAGE));
                String posterPath = cursor.getString(cursor.getColumnIndex(Movie.DatabaseConfig
                        .POSTER_PATH));
                String backdropPath = cursor.getString(cursor.getColumnIndex(Movie.DatabaseConfig
                        .BACKDROP_PATH));
                String overview = cursor.getString(cursor.getColumnIndex(Movie.DatabaseConfig
                        .OVERVIEW));
                String releaseDate = cursor.getString(cursor.getColumnIndex(Movie.DatabaseConfig
                        .RELEASE_DATE));
                Movie movie = new Movie(id, title);
                movie.setOverview(overview);
                movie.setPosterImage(posterPath);
                movie.setBackdropImage(backdropPath);
                movie.setVoteAverage(voteAverage);
                movie.setReleaseDate(releaseDate);

                movies.add(movie);
            } while (cursor.moveToNext());
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
        mDatabase.close();
        return movies;
    }
}
