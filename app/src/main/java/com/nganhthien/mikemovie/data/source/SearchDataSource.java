package com.nganhthien.mikemovie.data.source;

import java.util.List;

public interface SearchDataSource {
    interface RemoteDataSource {
        void loadSearchResult(String query, OnFetchSearchResultListener listener);
    }

    interface OnFetchSearchResultListener {
        void onFetchSearchResultSuccess(List<Object> dataList);

        void onFetchSearchResultsFailed(Exception e);
    }
}
