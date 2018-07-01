package com.nganhthien.mikemovie.screen.main;

/**
 * Created by ThienNA on 30/06/2018.
 */

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mView;

    @Override
    public void setView(MainContract.View view) {
        mView = view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }
}
