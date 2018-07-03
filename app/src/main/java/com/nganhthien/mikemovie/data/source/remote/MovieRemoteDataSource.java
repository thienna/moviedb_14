package com.nganhthien.mikemovie.data.source.remote;

import android.util.Log;

import com.nganhthien.mikemovie.BuildConfig;
import com.nganhthien.mikemovie.data.source.MovieDataSource;
import com.nganhthien.mikemovie.utils.Constants;
import com.nganhthien.mikemovie.utils.FetchMovieFromUrl;

/**
 * Created by ThienNA on 29/06/2018.
 */

public class MovieRemoteDataSource implements MovieDataSource.RemoteDataSource {

    private static MovieRemoteDataSource sInstance;

    public static MovieRemoteDataSource getsInstance() {
        if (sInstance == null) {
            sInstance = new MovieRemoteDataSource();
        }

        return sInstance;
    }

    private MovieRemoteDataSource() {
    }

    @Override
    public void loadMoviePopularRemote(MovieDataSource.OnFetchDataListener listener) {
        StringBuilder url = new StringBuilder();
        url.append(Constants.UrlConfig.URL_PROTOCOL);
        url.append(Constants.MovieApi.API_MOVIEDB_DOMAIN);
        url.append(Constants.MovieApi.API_MOVIEDB_POPULAR_LIST_PATH);
        url.append(Constants.UrlConfig.URL_QUERY_MARK);
        url.append(Constants.MovieApi.API_MOVIEDB_PARAM_KEY_API_KEY + BuildConfig.API_KEY);
        url.append(Constants.UrlConfig.URL_ADD_PARAM_MARK);
        url.append(Constants.MovieApi.API_MOVIEDB_PARAM_KEY_LANGUAGE + Constants.MovieApi.API_MOVIEDB_PARAM_VALUE_LANGUAGE);
        new FetchMovieFromUrl(listener)
                .execute(url.toString());
        Log.d("BCA", "loadMoviePopularRemote: " + url);
    }
}
