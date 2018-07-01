package com.nganhthien.mikemovie.data.source.remote;

import com.nganhthien.mikemovie.BuildConfig;
import com.nganhthien.mikemovie.data.source.GenreDataSource;
import com.nganhthien.mikemovie.utils.Constants;
import com.nganhthien.mikemovie.utils.FetchGenreFromUrl;

/**
 * Created by ThienNA on 01/07/2018.
 */

public class GenreRemoteDataSource implements GenreDataSource.RemoteDataSource {

    private static GenreRemoteDataSource sInstance;

    private GenreRemoteDataSource() {
    }

    public static synchronized GenreRemoteDataSource getInstance() {
        if (sInstance == null) {
            sInstance = new GenreRemoteDataSource();
        }
        return sInstance;
    }

    public static void destroyInstance() {
        sInstance = null;
    }

    @Override
    public void loadGenreRemote(GenreDataSource.OnFetchGenreCallback callback) {
        StringBuilder url = new StringBuilder();
        url.append(Constants.UrlConfig.PROTOCOL);
        url.append(Constants.MovieApi.DOMAIN);
        url.append(Constants.MovieApi.GENRES_LIST_PATH);
        url.append(Constants.UrlConfig.QUERY_MARK);
        url.append(Constants.MovieApi.PARAM_KEY_API_KEY + BuildConfig.API_KEY);
        url.append(Constants.UrlConfig.ADD_PARAM_MARK);
        url.append(Constants.MovieApi.PARAM_KEY_LANGUAGE + Constants.MovieApi.PARAM_VALUE_LANGUAGE);
        new FetchGenreFromUrl(callback)
                .execute(url.toString());
    }
}
