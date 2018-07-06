package com.nganhthien.mikemovie.data.repository;

import android.support.annotation.NonNull;

import com.nganhthien.mikemovie.data.source.ProductionDataSource;
import com.nganhthien.mikemovie.data.source.remote.ProductionRemoteDataSource;

import static com.google.common.base.Preconditions.checkNotNull;

public class ProductionRepository implements ProductionDataSource.RemoteDataSource {
    private static ProductionRepository sInstance;

    @NonNull
    private ProductionDataSource.RemoteDataSource mRemoteDataSource;

    private ProductionRepository(
            @NonNull ProductionDataSource.RemoteDataSource remoteDataSource) {
        mRemoteDataSource = checkNotNull(remoteDataSource);
    }

    public static synchronized ProductionRepository getInstance() {
        if (sInstance == null) {
            sInstance = new ProductionRepository(checkNotNull(ProductionRemoteDataSource.getsInstance()));
        }

        return sInstance;
    }

    public static void destroyInstance() {
        sInstance = null;
        ProductionRemoteDataSource.destroyInstance();
    }

    @Override
    public void loadProductionRemote(ProductionDataSource.OnFetchDataListener listener, int id) {
        mRemoteDataSource.loadProductionRemote(listener, id);
    }
}
