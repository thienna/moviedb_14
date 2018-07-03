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
    public void loadMoviePopularRemote(MovieDataSource.OnFetchDataListener listener) {
        mMovieRemoteDataSource.loadMoviePopularRemote(listener);
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
    }
}
