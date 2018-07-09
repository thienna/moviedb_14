package com.nganhthien.mikemovie.data.repository;

import android.support.annotation.NonNull;

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

    private MovieRepository(
            @NonNull MovieDataSource.LocalDataSource movieLocalDataSource,
            @NonNull MovieDataSource.RemoteDataSource movieRemoteDataSource) {
        mMovieLocalDataSource = checkNotNull(movieLocalDataSource);
        mMovieRemoteDataSource = checkNotNull(movieRemoteDataSource);
    }

    public static synchronized MovieRepository getInstance() {
        if (sInstance == null) {
            sInstance = new MovieRepository(checkNotNull(MovieLocalDataSource.getInstance()),
                    checkNotNull(MovieRemoteDataSource.getsInstance()));
        }

        return sInstance;
    }

    public static void destroyInstance() {
        sInstance = null;
        MovieRemoteDataSource.destroyInstance();
    }
}
