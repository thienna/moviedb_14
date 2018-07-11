package com.nganhthien.mikemovie.data.repository;

import android.support.annotation.NonNull;

import com.nganhthien.mikemovie.data.source.SearchDataSource;
import com.nganhthien.mikemovie.data.source.remote.SearchRemoteDataSource;

import static com.google.common.base.Preconditions.checkNotNull;

public class SearchRepository implements SearchDataSource.RemoteDataSource{
    private static SearchRepository sInstance;

    @NonNull
    private SearchDataSource.RemoteDataSource mRemoteDataSource;

    private SearchRepository(
            @NonNull SearchDataSource.RemoteDataSource remoteDataSource) {
        mRemoteDataSource = checkNotNull(remoteDataSource);
    }

    public static synchronized SearchRepository getInstance() {
        if (sInstance == null) {
            sInstance = new SearchRepository(checkNotNull(SearchRemoteDataSource.getInstance()));
        }

        return sInstance;
    }

    public static void destroyInstance() {
        sInstance = null;
        SearchRemoteDataSource.destroyInstance();
    }

    @Override
    public void loadSearchResult(String query, SearchDataSource.OnFetchSearchResultListener listener) {
        mRemoteDataSource.loadSearchResult(query, listener);
    }
}
