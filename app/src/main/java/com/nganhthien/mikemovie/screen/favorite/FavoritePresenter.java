package com.nganhthien.mikemovie.screen.favorite;

import com.nganhthien.mikemovie.data.model.Movie;
import com.nganhthien.mikemovie.data.repository.MovieRepository;
import com.nganhthien.mikemovie.data.source.MovieDataSource;

import java.util.List;

public class FavoritePresenter implements FavoriteContract.Presenter, MovieDataSource.OnGetFavoriteIdsListener, MovieDataSource.OnGetAllFavoriteListener, MovieDataSource.OnAddFavoriteListener, MovieDataSource.OnRemoveFavoriteListener {
    private FavoriteContract.View mView;
    private MovieRepository mMovieRepository;

    public FavoritePresenter(MovieRepository movieRepository) {
        mMovieRepository = movieRepository;
    }

    @Override
    public void loadFavoriteMoviesIds() {
        mMovieRepository.getAllFavoriteIds(this);
    }

    @Override
    public void loadFavoriteMoviesLocal() {
        mMovieRepository.getAllMoviesLocal(this);
    }

    @Override
    public void addMovieToFavorite(Movie movie) {
        mMovieRepository.addMovieLocal(movie, this);
    }

    @Override
    public void removeMovieFromFavorite(Movie movie) {
        mMovieRepository.removeMovieLocal(movie, this);
    }

    @Override
    public void setView(FavoriteContract.View view) {
        mView = view;
    }

    @Override
    public void onGetFavoriteIdsSuccess(List<Integer> ids) {
        mView.showLoadFavoriteIdsSuccess(ids);
    }

    @Override
    public void onGetFavoriteIdsFailed(Exception e) {
        mView.showLoadFavoriteIdsFailed();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onGetAllFavoriteSuccess(List<Movie> movies) {
        mView.showLoadFavoriteMoviesLocalSuccess(movies);
    }

    @Override
    public void onGetAllFavoriteFailed(Exception e) {
        mView.showLoadFavoriteMoviesLocalFailed();
    }

    @Override
    public void onAddFavoriteFailed(Exception e) {
        mView.showAddFavoriteFailed();
    }

    @Override
    public void onRemoveFavoriteFailed(Exception e) {
        mView.showDeleteFavoriteFailed();
    }

    @Override
    public void onAddFavoriteSuccess(Movie movie) {
        mView.showAddFavoriteSuccess(movie);
    }

    @Override
    public void onRemoveFavoriteSuccess(Movie movie) {
        mView.showDeleteFavoriteSuccess(movie);
    }
}
