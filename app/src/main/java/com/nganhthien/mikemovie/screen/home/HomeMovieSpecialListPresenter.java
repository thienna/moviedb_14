package com.nganhthien.mikemovie.screen.home;

import android.util.Log;

import com.nganhthien.mikemovie.data.model.Movie;
import com.nganhthien.mikemovie.data.repository.MovieRepository;
import com.nganhthien.mikemovie.data.source.MovieDataSource;

import java.util.List;

import static com.nganhthien.mikemovie.utils.Constants.SpecialMovieList.SPECIAL_MOVIE_LIST_MOST_POPULAR;
import static com.nganhthien.mikemovie.utils.Constants.SpecialMovieList.SPECIAL_MOVIE_LIST_NOW_PLAYING;
import static com.nganhthien.mikemovie.utils.Constants.SpecialMovieList.SPECIAL_MOVIE_LIST_TOP_RATE;
import static com.nganhthien.mikemovie.utils.Constants.SpecialMovieList.SPECIAL_MOVIE_LIST_UPCOMING;

public class HomeMovieSpecialListPresenter
        implements HomeMovieSpecialListContract.Presenter, MovieDataSource.OnFetchDataListener {

    private HomeMovieSpecialListContract.View mView;
    private MovieRepository mMovieRepository;

    public HomeMovieSpecialListPresenter(MovieRepository movieRepository) {
        mMovieRepository = movieRepository;
    }

    @Override
    public void loadMovieSpecialList(String type) {
        switch (type){
            case SPECIAL_MOVIE_LIST_MOST_POPULAR:
                mMovieRepository.loadMoviePopularRemote(this);
                return;
            case SPECIAL_MOVIE_LIST_NOW_PLAYING:
                mMovieRepository.loadMoviePopularRemote(this);
                return;
            case SPECIAL_MOVIE_LIST_TOP_RATE:
                mMovieRepository.loadMoviePopularRemote(this);
                return;
            case SPECIAL_MOVIE_LIST_UPCOMING:
                mMovieRepository.loadMoviePopularRemote(this);
                return;
        }
    }

    @Override
    public void setView(HomeMovieSpecialListContract.View view) {
        mView = view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onFetchPopularMoviesSuccess(List<Movie> movies) {
        Log.d("Presenterrrr", "onFetchPopularMoviesSuccess: " + movies.toString());
        mView.showMovieSpecialListSuccess(movies);
    }

    @Override
    public void onFetchPopularMoviesFailed(Exception e) {
        // TODO: Handle exception
        mView.showMovieSpecialListFailed();
    }
}
