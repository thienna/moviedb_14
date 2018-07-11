package com.nganhthien.mikemovie.screen.search;

import com.nganhthien.mikemovie.screen.BasePresenter;

import java.util.List;

public interface SearchContract {
    interface View {
        void showSearchResultsSuccess(List<Object> dataList);

        void showSearchResultsFailed(Exception e);
    }

    interface Presenter extends BasePresenter<SearchContract.View> {
        void loadSearchResults(String query);
    }
}
