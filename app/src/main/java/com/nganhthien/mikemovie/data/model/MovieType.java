package com.nganhthien.mikemovie.data.model;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.nganhthien.mikemovie.data.model.MovieType.MOST_POPULAR;
import static com.nganhthien.mikemovie.data.model.MovieType.NOW_PLAYING;
import static com.nganhthien.mikemovie.data.model.MovieType.TOP_RATED;
import static com.nganhthien.mikemovie.data.model.MovieType.UPCOMING;

@StringDef({MOST_POPULAR, UPCOMING, TOP_RATED, NOW_PLAYING})
@Retention(RetentionPolicy.SOURCE)
public @interface MovieType {
    String MOST_POPULAR = "movie/popular";
    String UPCOMING = "movie/upcoming";
    String TOP_RATED = "movie/top_rated";
    String NOW_PLAYING = "movie/now_playing";
}
