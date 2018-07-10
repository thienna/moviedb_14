package com.nganhthien.mikemovie.screen.person;

import com.nganhthien.mikemovie.data.model.Movie;
import com.nganhthien.mikemovie.data.model.Person;
import com.nganhthien.mikemovie.data.repository.MovieRepository;
import com.nganhthien.mikemovie.data.repository.PersonRepository;
import com.nganhthien.mikemovie.data.source.MovieDataSource;
import com.nganhthien.mikemovie.data.source.PersonDataSource;

import java.util.List;

public class PersonPresenter implements PersonContract.Presenter, PersonDataSource.OnFetchDataListener, MovieDataSource.OnFetchDataListener {
    private PersonContract.View mView;
    private PersonRepository mPersonRepository;
    private MovieRepository mMovieRepository;

    public PersonPresenter(){
        mPersonRepository = PersonRepository.getInstance();
        mMovieRepository = MovieRepository.getInstance();
    }

    @Override
    public void loadPerson(int personId) {
        mPersonRepository.loadPersonRemote(personId, this);
    }

    @Override
    public void loadMovies(String name) {
        mMovieRepository.loadMoviesByPerson(name, this);
    }

    @Override
    public void setView(PersonContract.View view) {
        mView = view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onFetchPersonSuccess(Person item) {
        mView.showPersonSuccess(item);
    }

    @Override
    public void onFetchPersonFailed(Exception e) {
        mView.showPersonFailed(e);
    }

    @Override
    public void onFetchMoviesSuccess(List<Movie> movies) {
        mView.showRelatedMoviesSuccess(movies);
    }

    @Override
    public void onFetchMoviesFailed(Exception e) {
        mView.showRelatedMoviesFailed(e);
    }
}
