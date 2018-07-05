package com.nganhthien.mikemovie.data.source;

import com.nganhthien.mikemovie.data.model.Movie;

import java.util.List;

/**
 * Created by ThienNA on 29/06/2018.
 */

public interface MovieDataSource {

    interface LocalDataSource {
    }

    interface RemoteDataSource {
        void loadMoviesRemote(String type, OnFetchDataListener listener);
    }

    interface OnFetchDataListener {
        void onFetchMoviesSuccess(List<Movie> movies);

        void onFetchMoviesFailed(Exception e);
    }
}
