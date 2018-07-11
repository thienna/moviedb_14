package com.nganhthien.mikemovie.data.source.remote;

import com.nganhthien.mikemovie.BuildConfig;
import com.nganhthien.mikemovie.data.source.TrailerDataSource;
import com.nganhthien.mikemovie.utils.Constants;
import com.nganhthien.mikemovie.utils.FetchTrailerFromUrl;

public class TrailerRemoteDataSource implements TrailerDataSource.RemoteDataSource {
    private static TrailerRemoteDataSource sInstance;

    private TrailerRemoteDataSource() {
    }

    public static TrailerRemoteDataSource getInstance() {
        if (sInstance == null) {
            sInstance = new TrailerRemoteDataSource();
        }
        return sInstance;
    }

    public static void destroyInstance() {
        sInstance = null;
    }

    @Override
    public void loadTrailer(int movieId, TrailerDataSource.OnFetchTrailerListener listener) {
        StringBuilder url = new StringBuilder();
        url.append(Constants.UrlConfig.PROTOCOL);
        url.append(Constants.MovieApi.DOMAIN);
        url.append(String.format(Constants.MovieApi.MOVIE_TRAILER, movieId));
        url.append(Constants.UrlConfig.QUERY_MARK);
        url.append(Constants.MovieApi.PARAM_KEY_API_KEY + BuildConfig.API_KEY);
        url.append(Constants.UrlConfig.ADD_PARAM_MARK);
        url.append(Constants.MovieApi.PARAM_KEY_LANGUAGE + Constants.MovieApi.PARAM_VALUE_LANGUAGE);
        new FetchTrailerFromUrl(listener).execute(url.toString());
    }
}
