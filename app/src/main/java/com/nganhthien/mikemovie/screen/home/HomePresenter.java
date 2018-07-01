package com.nganhthien.mikemovie.screen.home;

import com.nganhthien.mikemovie.data.model.Genre;
import com.nganhthien.mikemovie.data.repository.GenreRepository;
import com.nganhthien.mikemovie.data.source.GenreDataSource;

import java.util.List;

/**
 * Created by ThienNA on 03/07/2018.
 */

public class HomePresenter
        implements HomeContract.Presenter, GenreDataSource.OnFetchGenreCallback {

    private HomeContract.View mView;
    private GenreRepository mGenreRepository;

    public HomePresenter(GenreRepository genreRepository) {
        mGenreRepository = genreRepository;
    }

    @Override
    public void setView(HomeContract.View view) {
        mView = view;
    }

    @Override
    public void loadGenres() {
        mGenreRepository.loadGenreRemote(this);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onFetchGenreSuccess(List<Genre> genres) {
        mView.showLoadGenresSuccess(genres);
    }

    @Override
    public void onFetchGenreFailed(Exception e) {
        // TODO: Handle exception
        mView.showLoadGenresFailed();
    }
}
