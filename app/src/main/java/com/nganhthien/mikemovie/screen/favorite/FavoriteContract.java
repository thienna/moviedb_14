package com.nganhthien.mikemovie.screen.favorite;

import com.nganhthien.mikemovie.data.model.Movie;
import com.nganhthien.mikemovie.screen.BasePresenter;

import java.util.List;

public interface FavoriteContract {
    interface View {
        void showLoadFavoriteMoviesLocalSuccess(List<Movie> movies);

        void showLoadFavoriteMoviesLocalFailed();

        void showLoadFavoriteIdsSuccess(List<Integer> result);

        void showLoadFavoriteIdsFailed();

        void showAddFavoriteSuccess(Movie movie);

        void showAddFavoriteFailed();

        void showDeleteFavoriteSuccess(Movie movie);

        void showDeleteFavoriteFailed();
    }

    interface Presenter extends BasePresenter<FavoriteContract.View> {
        void loadFavoriteMoviesLocal();

        void loadFavoriteMoviesIds();

        void addMovieToFavorite(Movie movie);

        void removeMovieFromFavorite(Movie movie);
    }
}
