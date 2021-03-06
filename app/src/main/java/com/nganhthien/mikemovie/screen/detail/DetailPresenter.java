package com.nganhthien.mikemovie.screen.detail;

import com.nganhthien.mikemovie.data.model.Cast;
import com.nganhthien.mikemovie.data.model.Movie;
import com.nganhthien.mikemovie.data.model.Production;
import com.nganhthien.mikemovie.data.model.Trailer;
import com.nganhthien.mikemovie.data.repository.CastRepository;
import com.nganhthien.mikemovie.data.repository.MovieRepository;
import com.nganhthien.mikemovie.data.repository.ProductionRepository;
import com.nganhthien.mikemovie.data.repository.TrailerRepository;
import com.nganhthien.mikemovie.data.source.CastDataSource;
import com.nganhthien.mikemovie.data.source.MovieDataSource;
import com.nganhthien.mikemovie.data.source.ProductionDataSource;
import com.nganhthien.mikemovie.data.source.TrailerDataSource;

import java.util.List;

public class DetailPresenter implements DetailContract.Presenter,
        CastDataSource.OnFetchCastListener, ProductionDataSource.OnFetchDataListener, TrailerDataSource.OnFetchTrailerListener, MovieDataSource.OnGetFavoriteIdsListener, MovieDataSource.OnAddFavoriteListener, MovieDataSource.OnRemoveFavoriteListener {
    private DetailContract.View mView;
    private CastRepository mCastRepository;
    private ProductionRepository mProductionRepository;
    private TrailerRepository mTrailerRepository;
    private MovieRepository mMovieRepository;

    public DetailPresenter(MovieRepository movieRepository) {
        mMovieRepository = movieRepository;
        mCastRepository = CastRepository.getInstance();
        mProductionRepository = ProductionRepository.getInstance();
        mTrailerRepository = TrailerRepository.getInstance();
    }

    @Override
    public void loadCastRemote(int id) {
        mCastRepository.loadCastsRemote(this, id);
    }

    @Override
    public void loadProductionRemote(int id) {
        mProductionRepository.loadProductionRemote(this, id);
    }

    @Override
    public void loadTrailerRemote(int id) {
        mTrailerRepository.loadTrailer(id, this);
    }

    @Override
    public void setView(DetailContract.View view) {
        mView = view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onFetchCastSuccess(List<Cast> casts) {
        mView.showLoadCastSuccess(casts);
    }

    @Override
    public void onFetchDataSuccess(List<Production> productions) {
        mView.showLoadProductionSuccess(productions);
    }

    @Override
    public void onFetchDataFailed(Exception e) {
        mView.showLoadProductionFailed(e);
    }

    @Override
    public void onFetchCastFailed(Exception e) {
        mView.showLoadCastFailed(e);
    }

    @Override
    public void onFetchTrailerSuccess(List<Trailer> trailers) {
        mView.showLoadTrailerSuccess(trailers);
    }

    @Override
    public void onFetchTrailerFailed(Exception e) {
        mView.showLoadTrailerFailed(e);
    }

    @Override
    public void loadFavoriteIds() {
        mMovieRepository.getAllFavoriteIds(this);
    }

    @Override
    public void onGetFavoriteIdsSuccess(List<Integer> ids) {
        mView.showFavoriteIdsSuccess(ids);
    }

    @Override
    public void onGetFavoriteIdsFailed(Exception e) {
        mView.showFavoriteIdsFailed();
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
    public void onAddFavoriteSuccess(Movie movie) {
        mView.showAddFavoriteSuccess(movie);
    }

    @Override
    public void onAddFavoriteFailed(Exception e) {
        mView.showAddFavoriteFailed();
    }

    @Override
    public void onRemoveFavoriteSuccess(Movie movie) {
        mView.showDeleteFavoriteSuccess(movie);
    }

    @Override
    public void onRemoveFavoriteFailed(Exception e) {
        mView.showDeleteFavoriteFailed();
    }
}
