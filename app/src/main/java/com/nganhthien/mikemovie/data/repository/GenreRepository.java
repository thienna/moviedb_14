package com.nganhthien.mikemovie.data.repository;

import android.support.annotation.NonNull;

import com.nganhthien.mikemovie.data.source.GenreDataSource;
import com.nganhthien.mikemovie.data.source.remote.GenreRemoteDataSource;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by ThienNA on 01/07/2018.
 */

public class GenreRepository implements GenreDataSource.RemoteDataSource {

    private static GenreRepository sInstance;

    @NonNull
    private GenreDataSource.RemoteDataSource mGenreRemoteDataSource;

    private GenreRepository(@NonNull GenreDataSource.RemoteDataSource remoteDataSource) {
        mGenreRemoteDataSource = checkNotNull(remoteDataSource);
    }

    public static synchronized GenreRepository getInstance() {
        if (sInstance == null) {
            sInstance = new GenreRepository(GenreRemoteDataSource.getInstance());
        }
        return sInstance;
    }

    public static void destroyInstance() {
        sInstance = null;
    }

    @Override
    public void loadGenreRemote(GenreDataSource.OnFetchGenreCallback callback) {
        mGenreRemoteDataSource.loadGenreRemote(callback);
    }
}
