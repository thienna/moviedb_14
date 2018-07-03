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
        void loadMoviePopularRemote(OnFetchDataListener listener);
    }

    interface OnFetchDataListener {
        void onFetchPopularMoviesSuccess(List<Movie> movies);
        void onFetchPopularMoviesFailed(Exception e);
    }
}
