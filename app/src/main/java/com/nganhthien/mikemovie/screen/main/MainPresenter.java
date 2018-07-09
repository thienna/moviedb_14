package com.nganhthien.mikemovie.screen.main;

import com.nganhthien.mikemovie.data.model.Movie;
import com.nganhthien.mikemovie.data.repository.MovieRepository;
import com.nganhthien.mikemovie.data.source.MovieDataSource;

import java.util.List;

/**
 * Created by ThienNA on 30/06/2018.
 */

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mView;
    private MovieRepository mMovieRepository;

    public MainPresenter () {
        mMovieRepository = MovieRepository.getInstance();
    }

    @Override
    public void loadMoviesByType(String type) {
        mMovieRepository.loadMoviesRemote(type, new MovieDataSource.OnFetchDataListener() {
            @Override
            public void onFetchMoviesSuccess(List<Movie> movies) {
                mView.showLoadMoviesByTypeSuccess(movies);
            }

            @Override
            public void onFetchMoviesFailed(Exception e) {
                mView.showLoadMoviesByTypeFailed(e);
            }
        });
    }

    @Override
    public void loadMoviesByGenre(int genreId) {
        mMovieRepository.loadMoviesByGenre(genreId, new MovieDataSource.OnFetchDataListener() {
            @Override
            public void onFetchMoviesSuccess(List<Movie> movies) {
                mView.showLoadMoviesByGenreSuccess(movies);
            }

            @Override
            public void onFetchMoviesFailed(Exception e) {
                mView.showLoadMoviesByGenreFailed(e);
            }
        });
    }

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
