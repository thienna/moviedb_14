package com.nganhthien.mikemovie.screen.company;

import com.nganhthien.mikemovie.data.model.Company;
import com.nganhthien.mikemovie.data.model.Movie;
import com.nganhthien.mikemovie.screen.BasePresenter;

import java.util.List;

public interface CompanyContract {
    interface View {

        void showCompanySuccess(Company company);

        void showCompanyFailed(Exception e);

        void showRelatedMoviesSuccess(List<Movie> movies);

        void showRelatedMoviesFailed(Exception e);
    }

    interface Presenter extends BasePresenter<CompanyContract.View> {
        void loadCompany(int movieId);

        void loadMovies(int companyId);
    }
}
