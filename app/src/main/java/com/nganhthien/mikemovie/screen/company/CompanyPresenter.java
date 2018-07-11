package com.nganhthien.mikemovie.screen.company;

import com.nganhthien.mikemovie.data.model.Company;
import com.nganhthien.mikemovie.data.model.Movie;
import com.nganhthien.mikemovie.data.repository.CompanyRepository;
import com.nganhthien.mikemovie.data.repository.MovieRepository;
import com.nganhthien.mikemovie.data.source.CompanyDataSource;
import com.nganhthien.mikemovie.data.source.MovieDataSource;

import java.util.List;

public class CompanyPresenter implements CompanyContract.Presenter, CompanyDataSource
        .OnFetchDataListener, MovieDataSource.OnFetchDataListener {
    private CompanyContract.View mView;
    private CompanyRepository mCompanyRepository;
    private MovieRepository mMovieRepository;

    public CompanyPresenter(MovieRepository movieRepository) {
        mCompanyRepository = CompanyRepository.getInstance();
        mMovieRepository = movieRepository;
    }

    @Override
    public void setView(CompanyContract.View view) {
        mView = view;
    }

    @Override
    public void loadCompany(int companyId) {
        mCompanyRepository.loadCompanyRemote(companyId, this);
    }

    @Override
    public void loadMovies(int companyId) {
        mMovieRepository.loadMoviesByCompany(companyId, this);
    }


    @Override
    public void onFetchCompanySuccess(Company item) {
        mView.showCompanySuccess(item);
    }

    @Override
    public void onFetchCompanyFailed(Exception e) {
        mView.showCompanyFailed(e);
    }

    @Override
    public void onFetchMoviesSuccess(List<Movie> movies) {
        mView.showRelatedMoviesSuccess(movies);
    }

    @Override
    public void onFetchMoviesFailed(Exception e) {
        mView.showRelatedMoviesFailed(e);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }
}
