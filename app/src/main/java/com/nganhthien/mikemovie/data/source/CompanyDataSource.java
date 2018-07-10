package com.nganhthien.mikemovie.data.source;

import com.nganhthien.mikemovie.data.model.Company;

public interface CompanyDataSource {
    interface RemoteDataSource {
        void loadCompanyRemote(int productionId, OnFetchDataListener listener);
    }

    interface OnFetchDataListener {
        void onFetchCompanySuccess(Company item);

        void onFetchCompanyFailed(Exception e);
    }
}
