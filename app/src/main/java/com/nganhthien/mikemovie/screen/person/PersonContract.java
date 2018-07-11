package com.nganhthien.mikemovie.screen.person;

import com.nganhthien.mikemovie.data.model.Movie;
import com.nganhthien.mikemovie.data.model.Person;
import com.nganhthien.mikemovie.screen.BasePresenter;

import java.util.List;

public interface PersonContract {
    interface View {
        void showPersonSuccess(Person person);

        void showPersonFailed(Exception e);

        void showRelatedMoviesSuccess(List<Movie> movies);

        void showRelatedMoviesFailed(Exception e);
    }

    interface Presenter extends BasePresenter<PersonContract.View> {
        void loadPerson(int personId);

        void loadMovies(String name);
    }
}
