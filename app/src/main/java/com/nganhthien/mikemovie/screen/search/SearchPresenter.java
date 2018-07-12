package com.nganhthien.mikemovie.screen.search;

import com.nganhthien.mikemovie.data.model.Movie;
import com.nganhthien.mikemovie.data.repository.MovieRepository;
import com.nganhthien.mikemovie.data.repository.SearchRepository;
import com.nganhthien.mikemovie.data.source.MovieDataSource;
import com.nganhthien.mikemovie.data.source.SearchDataSource;

import java.util.List;

public class SearchPresenter implements SearchContract.Presenter,
        SearchDataSource.OnFetchSearchResultListener,
        MovieDataSource.OnGetFavoriteIdsListener, MovieDataSource.OnAddFavoriteListener,
        MovieDataSource.OnRemoveFavoriteListener {

    private SearchRepository mSearchRepository;
    private SearchContract.View mView;
    private MovieRepository mMovieRepository;

    public SearchPresenter(MovieRepository movieRepository) {
        mSearchRepository = SearchRepository.getInstance();
        mMovieRepository = movieRepository;
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
    public void loadFavoriteMoviesIds() {
        mMovieRepository.getAllFavoriteIds(this);
    }

    @Override
    public void addMovieToFavorite(Movie movie) {
        mMovieRepository.addMovieLocal(movie, this);
    }

    @Override
    public void removeMovieFromFavorite(Movie movie) {
        mMovieRepository.removeMovieLocal(movie, this);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onGetFavoriteIdsSuccess(List<Integer> ids) {
        mView.showLoadFavoriteIdsSuccess(ids);
    }

    @Override
    public void onGetFavoriteIdsFailed(Exception e) {
        mView.showLoadFavoriteIdsFailed();
    }

    @Override
    public void onAddFavoriteSuccess(Movie movie) {
        mView.showAddFavoriteSuccess(movie);
    }

    @Override
    public void onAddFavoriteFailed(Exception e) {
        mView.showAddFavoriteFailed();
    }

    @Override
    public void onRemoveFavoriteSuccess(Movie movie) {
        mView.showDeleteFavoriteSuccess(movie);
    }

    @Override
    public void onRemoveFavoriteFailed(Exception e) {
        mView.showDeleteFavoriteFailed();
    }
}
