package com.nganhthien.mikemovie.data.repository;

import android.content.Context;
import android.support.annotation.NonNull;

import com.nganhthien.mikemovie.data.model.Movie;
import com.nganhthien.mikemovie.data.source.MovieDataSource;
import com.nganhthien.mikemovie.data.source.local.MovieLocalDataSource;
import com.nganhthien.mikemovie.data.source.remote.MovieRemoteDataSource;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by ThienNA on 29/06/2018.
 */

public class MovieRepository
        implements MovieDataSource.RemoteDataSource, MovieDataSource.LocalDataSource {

    private static MovieRepository sInstance;

    @NonNull
    private MovieDataSource.LocalDataSource mMovieLocalDataSource;

    @NonNull
    private MovieDataSource.RemoteDataSource mMovieRemoteDataSource;

    private MovieRepository(
            @NonNull MovieDataSource.LocalDataSource movieLocalDataSource,
            @NonNull MovieDataSource.RemoteDataSource movieRemoteDataSource) {
        mMovieLocalDataSource = checkNotNull(movieLocalDataSource);
        mMovieRemoteDataSource = checkNotNull(movieRemoteDataSource);
    }

    public static synchronized MovieRepository getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new MovieRepository(checkNotNull(MovieLocalDataSource.getInstance(context)),
                    checkNotNull(MovieRemoteDataSource.getsInstance()));
        }

        return sInstance;
    }

    public static void destroyInstance() {
        sInstance = null;
        MovieRemoteDataSource.destroyInstance();
    }

    @Override
    public void loadMoviesRemote(String type, MovieDataSource.OnFetchDataListener listener) {
        mMovieRemoteDataSource.loadMoviesRemote(type, listener);
    }

    @Override
    public void loadMoviesByGenre(int genreId, MovieDataSource.OnFetchDataListener listener) {
        mMovieRemoteDataSource.loadMoviesByGenre(genreId, listener);
    }

    @Override
    public void loadMoviesByPerson(String personName, MovieDataSource.OnFetchDataListener listener) {
        mMovieRemoteDataSource.loadMoviesByPerson(personName, listener);
    }

    @Override
    public void loadMoviesByCompany(int companyId, MovieDataSource.OnFetchDataListener listener) {
        mMovieRemoteDataSource.loadMoviesByCompany(companyId, listener);
    }

    @Override
    public void getAllMoviesLocal(MovieDataSource.OnGetAllFavoriteListener listener) {
        mMovieLocalDataSource.getAllMoviesLocal(listener);
    }

    @Override
    public void addMovieLocal(Movie movie, MovieDataSource.OnAddFavoriteListener listener) {
        mMovieLocalDataSource.addMovieLocal(movie, listener);
    }

    @Override
    public void removeMovieLocal(Movie movie, MovieDataSource.OnRemoveFavoriteListener listener) {
        mMovieLocalDataSource.removeMovieLocal(movie, listener);
    }

    @Override
    public void getAllFavoriteIds(MovieDataSource.OnGetFavoriteIdsListener listener) {
        mMovieLocalDataSource.getAllFavoriteIds(listener);
    }
}
