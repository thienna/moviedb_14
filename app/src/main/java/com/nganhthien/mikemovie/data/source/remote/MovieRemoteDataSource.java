package com.nganhthien.mikemovie.data.source.remote;

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
    public void loadMoviesRemote(String type, MovieDataSource.OnFetchDataListener listener) {
        StringBuilder url = new StringBuilder();
        url.append(Constants.UrlConfig.PROTOCOL);
        url.append(Constants.MovieApi.DOMAIN);
        url.append(type);
        url.append(Constants.UrlConfig.QUERY_MARK);
        url.append(Constants.MovieApi.PARAM_KEY_API_KEY + BuildConfig.API_KEY);
        url.append(Constants.UrlConfig.ADD_PARAM_MARK);
        url.append(Constants.MovieApi.PARAM_KEY_LANGUAGE + Constants.MovieApi.PARAM_VALUE_LANGUAGE);
        new FetchMovieFromUrl(listener)
                .execute(url.toString());
    }

    @Override
    public void loadMoviesByGenre(int genreId, MovieDataSource.OnFetchDataListener listener) {
        StringBuilder url = new StringBuilder();
        url.append(Constants.UrlConfig.PROTOCOL);
        url.append(Constants.MovieApi.DOMAIN);
        url.append(String.format(Constants.MovieApi.MOVIE_BY_GENRE, genreId));
        url.append(Constants.UrlConfig.QUERY_MARK);
        url.append(Constants.MovieApi.PARAM_KEY_API_KEY + BuildConfig.API_KEY);
        url.append(Constants.UrlConfig.ADD_PARAM_MARK);
        url.append(Constants.MovieApi.PARAM_KEY_LANGUAGE + Constants.MovieApi.PARAM_VALUE_LANGUAGE);
        new FetchMovieFromUrl(listener)
                .execute(url.toString());
    }

    public static void destroyInstance() {
        sInstance = null;
    }
}
