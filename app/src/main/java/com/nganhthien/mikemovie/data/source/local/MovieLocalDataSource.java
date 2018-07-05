package com.nganhthien.mikemovie.data.source.local;

import com.nganhthien.mikemovie.data.source.MovieDataSource;

/**
 * Created by ThienNA on 29/06/2018.
 */

public class MovieLocalDataSource implements MovieDataSource.LocalDataSource {

    private static MovieLocalDataSource sInstance;

    public static MovieLocalDataSource getInstance() {
        if (sInstance == null) {
            sInstance = new MovieLocalDataSource();
        }
        return sInstance;
    }

    private MovieLocalDataSource() {
    }

    public static void destroyInstance() {
        sInstance = null;
    }
}
