package com.nganhthien.mikemovie.data.source.remote;

import com.nganhthien.mikemovie.BuildConfig;
import com.nganhthien.mikemovie.data.source.ProductionDataSource;
import com.nganhthien.mikemovie.utils.Constants;
import com.nganhthien.mikemovie.utils.FetchProductionFromUrl;

public class ProductionRemoteDataSource implements ProductionDataSource.RemoteDataSource {
    private static ProductionRemoteDataSource sInstance;

    private ProductionRemoteDataSource() {
    }

    public static ProductionRemoteDataSource getsInstance() {
        if (sInstance == null) {
            sInstance = new ProductionRemoteDataSource();
        }
        return sInstance;
    }

    public static void destroyInstance() {
        sInstance = null;
    }

    @Override
    public void loadProductionRemote(ProductionDataSource.OnFetchDataListener listener, int id) {
        StringBuilder url = new StringBuilder();
        url.append(Constants.UrlConfig.PROTOCOL);
        url.append(Constants.MovieApi.DOMAIN);
        url.append(Constants.MovieApi.MOVIE_DETAIL);
        url.append(id);
        url.append(Constants.UrlConfig.QUERY_MARK);
        url.append(Constants.MovieApi.PARAM_KEY_API_KEY + BuildConfig.API_KEY);
        url.append(Constants.UrlConfig.ADD_PARAM_MARK);
        url.append(Constants.MovieApi.PARAM_KEY_LANGUAGE + Constants.MovieApi.PARAM_VALUE_LANGUAGE);
        new FetchProductionFromUrl(listener).execute(url.toString());
    }
}
