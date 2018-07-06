package com.nganhthien.mikemovie.data.source.remote;

import com.nganhthien.mikemovie.BuildConfig;
import com.nganhthien.mikemovie.data.source.CastDataSource;
import com.nganhthien.mikemovie.utils.Constants;
import com.nganhthien.mikemovie.utils.FetchCastFromUrl;

public class CastRemoteDataSource implements CastDataSource.RemoteDataSource {
    private static CastRemoteDataSource sInstance;

    private CastRemoteDataSource() {
    }

    public static CastRemoteDataSource getsInstance() {
        if (sInstance == null) {
            sInstance = new CastRemoteDataSource();
        }
        return sInstance;
    }

    public static void destroyInstance() {
        sInstance = null;
    }

    @Override
    public void loadCastsRemote(CastDataSource.OnFetchCastListener listener, int id) {
        StringBuilder url = new StringBuilder();
        url.append(Constants.UrlConfig.PROTOCOL);
        url.append(Constants.MovieApi.DOMAIN);
        url.append(String.format(Constants.MovieApi.MOVIE_CREDITS, id));
        url.append(Constants.UrlConfig.QUERY_MARK);
        url.append(Constants.MovieApi.PARAM_KEY_API_KEY + BuildConfig.API_KEY);
        url.append(Constants.UrlConfig.ADD_PARAM_MARK);
        url.append(Constants.MovieApi.PARAM_KEY_LANGUAGE + Constants.MovieApi.PARAM_VALUE_LANGUAGE);
        new FetchCastFromUrl(listener).execute(url.toString());
    }
}
