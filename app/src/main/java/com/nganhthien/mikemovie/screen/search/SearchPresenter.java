package com.nganhthien.mikemovie.screen.search;

import com.nganhthien.mikemovie.data.repository.SearchRepository;
import com.nganhthien.mikemovie.data.source.SearchDataSource;

import java.util.List;

public class SearchPresenter implements SearchContract.Presenter, SearchDataSource.OnFetchSearchResultListener {

    private SearchRepository mSearchRepository;
    private SearchContract.View mView;

    public SearchPresenter (){
        mSearchRepository = SearchRepository.getInstance();
    }

    @Override
    public void loadSearchResults(String query) {
        mSearchRepository.loadSearchResult(query, this);
    }

    @Override
    public void onFetchSearchResultSuccess(List<Object> dataList) {
        mView.showSearchResultsSuccess(dataList);
    }

    @Override
    public void onFetchSearchResultsFailed(Exception e) {
        mView.showSearchResultsFailed(e);
    }

    @Override
    public void setView(SearchContract.View view) {
        mView = view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }
}
