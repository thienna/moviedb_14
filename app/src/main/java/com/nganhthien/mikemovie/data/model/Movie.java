package com.nganhthien.mikemovie.data.model;

/**
 * Created by ThienNA on 29/06/2018.
 */

public class Movie {
    private int mId;
    private String mTitle;
    private String mOverview;
    private int mVoteAverage;
    private String mPosterImage;
    private String mBackdropImage;
    private String mReleaseDate;

    public Movie(int id, String title) {
        mId = id;
        mTitle = title;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String name) {
        mTitle = name;
    }

    public String getOverview() {
        return mOverview;
    }

    public void setOverview(String overview) {
        mOverview = overview;
    }

    public int getVoteAverage() {
        return mVoteAverage;
    }

    public void setVoteAverage(int voteAverage) {
        mVoteAverage = voteAverage;
    }

    public String getPosterImage() {
        return mPosterImage;
    }

    public void setPosterImage(String posterImage) {
        mPosterImage = posterImage;
    }

    public String getBackdropImage() {
        return mBackdropImage;
    }

    public void setBackdropImage(String backdropImage) {
        mBackdropImage = backdropImage;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        mReleaseDate = releaseDate;
    }

    public static class JsonKey {
        public static final String ID = "id";
        public static final String TITLE = "title";
        public static final String RESULTS = "results";
        public static final String OVERVIEW = "overview";
        public static final String VOTE_COUNT = "vote_count";
        public static final String POSTER_PATH = "poster_path";
        public static final String BACKDROP_PATH = "backdrop_path";
        public static final String RELEASE_DATE = "release_date";
        public static final String GENRES = "genres";

        private JsonKey() {
        }
    }
}
