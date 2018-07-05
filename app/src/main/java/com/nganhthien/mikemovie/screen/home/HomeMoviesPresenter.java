package com.nganhthien.mikemovie.screen.home;

import com.nganhthien.mikemovie.data.model.Movie;
import com.nganhthien.mikemovie.data.repository.MovieRepository;
import com.nganhthien.mikemovie.data.source.MovieDataSource;

import java.util.List;

public class HomeMoviesPresenter
        implements HomeMoviesContract.Presenter, MovieDataSource.OnFetchDataListener {

    private HomeMoviesContract.View mView;
    private MovieRepository mMovieRepository;

    public HomeMoviesPresenter(MovieRepository movieRepository) {
        mMovieRepository = movieRepository;
    }

    @Override
    public void loadMovies(String type) {
        mMovieRepository.loadMoviesRemote(type, this);
    }

    @Override
    public void setView(HomeMoviesContract.View view) {
        mView = view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onFetchMoviesSuccess(List<Movie> movies) {
        mView.showMoviesSuccess(movies);
    }

    @Override
    public void onFetchMoviesFailed(Exception e) {
        mView.showMoviesFailed(e);
    }
}
