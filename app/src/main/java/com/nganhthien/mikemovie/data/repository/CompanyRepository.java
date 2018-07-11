package com.nganhthien.mikemovie.data.repository;

import android.support.annotation.NonNull;

import com.nganhthien.mikemovie.data.source.CompanyDataSource;
import com.nganhthien.mikemovie.data.source.remote.CompanyRemoteDataSource;

import static com.google.common.base.Preconditions.checkNotNull;

public class CompanyRepository implements CompanyDataSource.RemoteDataSource {
    private static CompanyRepository sInstance;

    @NonNull
    private CompanyDataSource.RemoteDataSource mRemoteDataSource;

    private CompanyRepository(
            @NonNull CompanyDataSource.RemoteDataSource remoteDataSource) {
        mRemoteDataSource = checkNotNull(remoteDataSource);
    }

    public static synchronized CompanyRepository getInstance() {
        if (sInstance == null) {
            sInstance = new CompanyRepository(checkNotNull(CompanyRemoteDataSource.getInstance()));
        }

        return sInstance;
    }

    public static void destroyInstance() {
        sInstance = null;
        CompanyRemoteDataSource.destroyInstance();
    }

    @Override
    public void loadCompanyRemote(int companyId, CompanyDataSource.OnFetchDataListener listener) {
        mRemoteDataSource.loadCompanyRemote(companyId, listener);
    }
}
