package com.nganhthien.mikemovie.data.source;

import com.nganhthien.mikemovie.data.model.Genre;

import java.util.List;

/**
 * Created by ThienNA on 01/07/2018.
 */

public interface GenreDataSource {

    interface RemoteDataSource {
        void loadGenreRemote(OnFetchGenreCallback callback);
    }

    interface OnFetchGenreCallback {
        void onFetchGenreSuccess(List<Genre> genres);
        void onFetchGenreFailed(Exception e);
    }
}
