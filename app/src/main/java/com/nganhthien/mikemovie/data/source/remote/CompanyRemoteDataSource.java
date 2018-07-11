package com.nganhthien.mikemovie.data.source.remote;

import com.nganhthien.mikemovie.BuildConfig;
import com.nganhthien.mikemovie.data.source.CompanyDataSource;
import com.nganhthien.mikemovie.utils.Constants;
import com.nganhthien.mikemovie.utils.FetchCompanyFromUrl;

public class CompanyRemoteDataSource implements CompanyDataSource.RemoteDataSource {

    private static CompanyRemoteDataSource sInstance;

    private CompanyRemoteDataSource() {
    }

    public static CompanyRemoteDataSource getInstance() {
        if (sInstance == null) {
            sInstance = new CompanyRemoteDataSource();
        }
        return sInstance;
    }

    public static void destroyInstance() {
        sInstance = null;
    }

    @Override
    public void loadCompanyRemote(int productionId, CompanyDataSource.OnFetchDataListener listener) {
        StringBuilder url = new StringBuilder();
        url.append(Constants.UrlConfig.PROTOCOL);
        url.append(Constants.MovieApi.DOMAIN);
        url.append(Constants.MovieApi.COMPANY_DETAIL);
        url.append(productionId);
        url.append(Constants.UrlConfig.QUERY_MARK);
        url.append(Constants.MovieApi.PARAM_KEY_API_KEY + BuildConfig.API_KEY);
        url.append(Constants.UrlConfig.ADD_PARAM_MARK);
        url.append(Constants.MovieApi.PARAM_KEY_LANGUAGE + Constants.MovieApi.PARAM_VALUE_LANGUAGE);
        new FetchCompanyFromUrl(listener).execute(url.toString());
    }
}
