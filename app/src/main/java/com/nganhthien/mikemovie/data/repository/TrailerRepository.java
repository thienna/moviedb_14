package com.nganhthien.mikemovie.data.repository;

import android.support.annotation.NonNull;

import com.nganhthien.mikemovie.data.source.TrailerDataSource;
import com.nganhthien.mikemovie.data.source.remote.TrailerRemoteDataSource;

import static com.google.common.base.Preconditions.checkNotNull;

public class TrailerRepository implements TrailerDataSource.RemoteDataSource {
    private static TrailerRepository sInstance;

    @NonNull
    private TrailerDataSource.RemoteDataSource mRemoteDataSource;

    private TrailerRepository(
            @NonNull TrailerDataSource.RemoteDataSource remoteDataSource) {
        mRemoteDataSource = checkNotNull(remoteDataSource);
    }

    public static synchronized TrailerRepository getInstance() {
        if (sInstance == null) {
            sInstance = new TrailerRepository(checkNotNull(TrailerRemoteDataSource.getInstance()));
        }

        return sInstance;
    }

    public static void destroyInstance() {
        sInstance = null;
        TrailerRemoteDataSource.destroyInstance();
    }

    @Override
    public void loadTrailer(int movieId, TrailerDataSource.OnFetchTrailerListener listener) {
        mRemoteDataSource.loadTrailer(movieId, listener);
    }
}
