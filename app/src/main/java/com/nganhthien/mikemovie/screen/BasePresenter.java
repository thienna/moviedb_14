package com.nganhthien.mikemovie.screen;

/**
 * Created by ThienNA on 29/06/2018.
 */

public interface BasePresenter<T> {

    void setView(T view);

    void onStart();

    void onStop();
}
