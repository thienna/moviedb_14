package com.nganhthien.mikemovie.data.repository;

import android.support.annotation.NonNull;

import com.nganhthien.mikemovie.data.source.CastDataSource;
import com.nganhthien.mikemovie.data.source.remote.CastRemoteDataSource;

import static com.google.common.base.Preconditions.checkNotNull;

public class CastRepository implements CastDataSource.RemoteDataSource {

    private static CastRepository sInstance;

    @NonNull
    private CastDataSource.RemoteDataSource mRemoteDataSource;

    private CastRepository(
            @NonNull CastDataSource.RemoteDataSource remoteDataSource) {
        mRemoteDataSource = checkNotNull(remoteDataSource);
    }

    public static synchronized CastRepository getInstance() {
        if (sInstance == null) {
            sInstance = new CastRepository(checkNotNull(CastRemoteDataSource.getsInstance()));
        }

        return sInstance;
    }

    public static void destroyInstance() {
        sInstance = null;
        CastRemoteDataSource.destroyInstance();
    }

    @Override
    public void loadCastsRemote(CastDataSource.OnFetchCastListener listener, int id) {
        mRemoteDataSource.loadCastsRemote(listener, id);
    }
}
