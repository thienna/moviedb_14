package com.nganhthien.mikemovie.data.source.local;

import android.content.Context;

import com.nganhthien.mikemovie.data.model.Movie;
import com.nganhthien.mikemovie.data.source.MovieDataSource;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by ThienNA on 29/06/2018.
 */

public class MovieLocalDataSource implements MovieDataSource.LocalDataSource {

    private static MovieLocalDataSource sInstance;
    private MovieDatabaseHelper mDatabaseHelper;

    private MovieLocalDataSource(MovieDatabaseHelper movieDatabaseHelper) {
        mDatabaseHelper = movieDatabaseHelper;
    }

    public static MovieLocalDataSource getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new MovieLocalDataSource(checkNotNull(MovieDatabaseHelper.getInstance
                    (context)));
        }
        return sInstance;
    }

    public static void destroyInstance() {
        sInstance = null;
        MovieDatabaseHelper.destroyInstance();
    }

    @Override
    public void getAllMoviesLocal(MovieDataSource.OnGetAllFavoriteListener listener) {
        mDatabaseHelper.getAll(listener);
    }

    @Override
    public void addMovieLocal(Movie movie, MovieDataSource.OnAddFavoriteListener listener) {
        mDatabaseHelper.insertSingle(movie, listener);
    }

    @Override
    public void removeMovieLocal(Movie movie, MovieDataSource.OnRemoveFavoriteListener listener) {
        mDatabaseHelper.deleteSingle(movie, listener);
    }

    @Override
    public void getAllFavoriteIds(MovieDataSource.OnGetFavoriteIdsListener listener) {
        mDatabaseHelper.getIds(listener);
    }
}
