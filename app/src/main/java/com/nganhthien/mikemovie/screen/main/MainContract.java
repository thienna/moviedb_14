package com.nganhthien.mikemovie.screen.main;

import com.nganhthien.mikemovie.data.model.Movie;
import com.nganhthien.mikemovie.screen.BasePresenter;

import java.util.List;

/**
 * Created by ThienNA on 30/06/2018.
 */

interface MainContract {

    interface View {
        void showLoadMoviesByGenreSuccess(List<Movie> movies);

        void showLoadMoviesByGenreFailed(Exception e);

        void showLoadMoviesByTypeSuccess(List<Movie> movies);

        void showLoadMoviesByTypeFailed(Exception e);
    }

    interface Presenter extends BasePresenter<MainContract.View> {
        void loadMoviesByGenre(int genreId);

        void loadMoviesByType(String type);
    }
}
