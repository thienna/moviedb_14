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
        private UrlConfig() {
        }

        public static final String URL_PROTOCOL = "https://";
        public static final String URL_PATH_SEPARATOR = "/";
        public static final String URL_QUERY_MARK = "?";
        public static final String URL_ADD_PARAM_MARK = "&";

        public static final String URL_METHOD_GET = "GET";
        public static final int URL_REQUEST_TIMEOUT = 10000;
        public static final int URL_CONNECT_TIMEOUT = 15000;
        public static final String URL_CONNECT_CHARSET = "UTF-8";
    }

    // MOVIEDB API
    public class MovieApi {
        private MovieApi() {
        }

        public static final String API_MOVIEDB_DOMAIN = "api.themoviedb.org/3/";

        public static final String API_MOVIEDB_GENRES_LIST_PATH = "genre/movie/list";
        public static final String API_MOVIEDB_POPULAR_LIST_PATH = "movie/popular";

        public static final String API_MOVIEDB_PARAM_KEY_LANGUAGE = "language=";
        public static final String API_MOVIEDB_PARAM_KEY_API_KEY = "api_key=";

        public static final String API_MOVIEDB_PARAM_VALUE_LANGUAGE = "en-US";

        public static final String API_MOVIEDB_JSONKEY_GENRES = "genres";
        public static final String API_MOVIEDB_JSONKEY_ID = "id";
        public static final String API_MOVIEDB_JSONKEY_NAME = "name";
    }
}
