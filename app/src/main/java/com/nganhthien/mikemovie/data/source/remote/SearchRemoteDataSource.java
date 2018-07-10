package com.nganhthien.mikemovie.data.source.remote;

import com.nganhthien.mikemovie.BuildConfig;
import com.nganhthien.mikemovie.data.source.SearchDataSource;
import com.nganhthien.mikemovie.utils.Constants;
import com.nganhthien.mikemovie.utils.FetchSearchResultFromUrl;

public class SearchRemoteDataSource implements SearchDataSource.RemoteDataSource{
    private static SearchRemoteDataSource sInstance;

    private SearchRemoteDataSource() {
    }

    public static SearchRemoteDataSource getInstance() {
        if (sInstance == null) {
            sInstance = new SearchRemoteDataSource();
        }
        return sInstance;
    }

    public static void destroyInstance() {
        sInstance = null;
    }

    @Override
    public void loadSearchResult(String query, SearchDataSource.OnFetchSearchResultListener listener) {
        StringBuilder url = new StringBuilder();
        url.append(Constants.UrlConfig.PROTOCOL);
        url.append(Constants.MovieApi.DOMAIN);
        url.append(Constants.MovieApi.MULTI_SEARCH);
        url.append(Constants.UrlConfig.QUERY_MARK);
        url.append(Constants.MovieApi.PARAM_KEY_API_KEY + BuildConfig.API_KEY);
        url.append(Constants.UrlConfig.ADD_PARAM_MARK);
        url.append(Constants.MovieApi.PARAM_KEY_LANGUAGE + Constants.MovieApi.PARAM_VALUE_LANGUAGE);
        url.append(Constants.UrlConfig.ADD_PARAM_MARK);
        url.append(Constants.MovieApi.PARAM_KEY_QUERY);
        url.append(query);
        new FetchSearchResultFromUrl(listener).execute(url.toString());
    }
}
