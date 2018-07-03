package com.nganhthien.mikemovie.screen.home;

import com.nganhthien.mikemovie.data.model.Movie;
import com.nganhthien.mikemovie.screen.BasePresenter;

import java.util.List;

public class HomeMovieSpecialListContract {
    interface View {
        void showMovieSpecialListSuccess(List<Movie> movies);

        void showMovieSpecialListFailed();
    }

    interface Presenter extends BasePresenter<HomeMovieSpecialListContract.View> {

        void loadMovieSpecialList(String type);
    }
}
