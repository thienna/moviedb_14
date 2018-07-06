package com.nganhthien.mikemovie.utils;

/**
 * Created by ThienNA on 29/06/2018.
 */

public final class Constants {

    private Constants() {
    }

    // Home Screen Slider
    public static final int HOME_SLIDER_TOTAL_PAGE = 5;

    // URL Config
    public class UrlConfig {
        public static final String PROTOCOL = "https://";
        public static final String PATH_SEPARATOR = "/";
        public static final String QUERY_MARK = "?";
        public static final String ADD_PARAM_MARK = "&";

        public static final String METHOD_GET = "GET";
        public static final int REQUEST_TIMEOUT = 10000;
        public static final int CONNECT_TIMEOUT = 15000;
        public static final String CONNECT_CHARSET = "UTF-8";

        private UrlConfig() {
        }
    }

    // MOVIEDB API
    public static class MovieApi {
        public static final String DOMAIN = "api.themoviedb.org/3/";
        public static final String DOMAIN_POSTER_IMAGE = "image.tmdb.org/t/p/w200/%s";
        public static final String DOMAIN_BACKDROP_IMAGE = "image.tmdb.org/t/p/w500_and_h282_face/%s";

        public static final String GENRES_LIST_PATH = "genre/movie/list";
        public static final String MOVIE_CREDITS = "movie/%s/credits";
        public static final String MOVIE_DETAIL = "movie/";

        public static final String PARAM_KEY_LANGUAGE = "language=";
        public static final String PARAM_KEY_API_KEY = "api_key=";

        public static final String PARAM_VALUE_LANGUAGE = "en-US";

        private MovieApi() {
        }
    }

    public static final int DETAIL_SCREEN_OVEVERVIEW_MINLINE = 3;
    public static final int DETAIL_SCREEN_OVEVERVIEW_MAXLINE = 9999;
}
