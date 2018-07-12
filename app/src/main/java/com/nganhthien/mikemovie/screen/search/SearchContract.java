package com.nganhthien.mikemovie.screen.search;

import com.nganhthien.mikemovie.data.model.Movie;
import com.nganhthien.mikemovie.screen.BasePresenter;

import java.util.List;

public interface SearchContract {
    interface View {
        void showSearchResultsSuccess(List<Object> dataList);

        void showSearchResultsFailed(Exception e);

        void showLoadFavoriteIdsSuccess(List<Integer> result);

        void showLoadFavoriteIdsFailed();

        void showAddFavoriteSuccess(Movie movie);

        void showAddFavoriteFailed();

        void showDeleteFavoriteSuccess(Movie movie);

        void showDeleteFavoriteFailed();
    }

    interface Presenter extends BasePresenter<SearchContract.View> {
        void loadSearchResults(String query);

        void loadFavoriteMoviesIds();

        void addMovieToFavorite(Movie movie);

        void removeMovieFromFavorite(Movie movie);
    }
}
