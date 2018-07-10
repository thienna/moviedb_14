package com.nganhthien.mikemovie.data.source;

import com.nganhthien.mikemovie.data.model.Trailer;

import java.util.List;

public interface TrailerDataSource {
    interface RemoteDataSource {
        void loadTrailer(int movieId, OnFetchTrailerListener listener);
    }

    interface OnFetchTrailerListener {
        void onFetchTrailerSuccess(List<Trailer> trailers);

        void onFetchTrailerFailed(Exception e);
    }
}
