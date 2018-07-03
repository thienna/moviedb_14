package com.nganhthien.mikemovie.screen.home;

import com.nganhthien.mikemovie.data.model.Genre;
import com.nganhthien.mikemovie.screen.BasePresenter;

import java.util.List;

/**
 * Created by ThienNA on 03/07/2018.
 */

public class HomeContract {

    interface View {
        void showLoadGenresSuccess(List<Genre> genres);

        void showLoadGenresFailed();
    }

    interface Presenter extends BasePresenter<HomeContract.View> {
        void loadGenres();
    }
}
