package com.nganhthien.mikemovie.data.source;

import com.nganhthien.mikemovie.data.model.Movie;

import java.util.List;

/**
 * Created by ThienNA on 29/06/2018.
 */

public interface MovieDataSource {

    interface LocalDataSource {
        void getAllMoviesLocal(OnGetAllFavoriteListener listener);

        void addMovieLocal(Movie movie, OnAddFavoriteListener listener);

        void removeMovieLocal(Movie movie, OnRemoveFavoriteListener listener);

        void getAllFavoriteIds(OnGetFavoriteIdsListener listener);
    }

    interface RemoteDataSource {
        void loadMoviesRemote(String type, OnFetchDataListener listener);

        void loadMoviesByGenre(int genreId, OnFetchDataListener listener);

        void loadMoviesByPerson(String personName, OnFetchDataListener listener);

        void loadMoviesByCompany(int companyId, OnFetchDataListener listener);
    }

    interface OnFetchDataListener {
        void onFetchMoviesSuccess(List<Movie> movies);

        void onFetchMoviesFailed(Exception e);
    }

    interface OnGetAllFavoriteListener {
        void onGetAllFavoriteSuccess(List<Movie> movies);

        void onGetAllFavoriteFailed(Exception e);
    }

    interface OnGetFavoriteIdsListener {
        void onGetFavoriteIdsSuccess(List<Integer> ids);

        void onGetFavoriteIdsFailed(Exception e);
    }

    interface OnAddFavoriteListener {
        void onAddFavoriteSuccess(Movie movie);

        void onAddFavoriteFailed(Exception e);
    }

    interface OnRemoveFavoriteListener {
        void onRemoveFavoriteSuccess(Movie movie);

        void onRemoveFavoriteFailed(Exception e);
    }
}
