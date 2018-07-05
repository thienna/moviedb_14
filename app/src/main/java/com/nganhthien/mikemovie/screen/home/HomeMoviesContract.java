package com.nganhthien.mikemovie.screen.home;

import com.nganhthien.mikemovie.data.model.Movie;
import com.nganhthien.mikemovie.screen.BasePresenter;

import java.util.List;

public class HomeMoviesContract {
    interface View {
        void showMoviesSuccess(List<Movie> movies);

        void showMoviesFailed(Exception e);
    }

    interface Presenter extends BasePresenter<HomeMoviesContract.View> {

        void loadMovies(String type);
    }
}
