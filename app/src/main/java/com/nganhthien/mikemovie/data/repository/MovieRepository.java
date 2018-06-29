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

    private MovieRepository(
            @NonNull MovieDataSource.LocalDataSource movieLocalDataSource,
            @NonNull MovieDataSource.RemoteDataSource movieRemoteDataSource) {
        mMovieLocalDataSource = checkNotNull(movieLocalDataSource);
        mMovieRemoteDataSource = checkNotNull(movieRemoteDataSource);
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param remoteDataSource the backend data source
     * @param localDataSource  the device storage data source
     * @return the {@link MovieRepository} instance
     */
    public static synchronized MovieRepository getInstance(
            @NonNull MovieDataSource.LocalDataSource localDataSource,
            @NonNull MovieDataSource.RemoteDataSource remoteDataSource) {

        if (sInstance == null) {
            sInstance = new MovieRepository(checkNotNull(localDataSource),
                    checkNotNull(remoteDataSource));
        }

        return sInstance;
    }

    /**
     * Used to force
     * {@link #getInstance(MovieDataSource.LocalDataSource, MovieDataSource.RemoteDataSource)}
     * to create a new instance
     * next time it's called.
     */
    public static void destroyInstance() {
        sInstance = null;
    }
}
