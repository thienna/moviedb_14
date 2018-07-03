package com.nganhthien.mikemovie.data.model;

/**
 * Created by ThienNA on 29/06/2018.
 */

public class Movie {
    private int mId;
    private String mName;
    private String mOverview;
    private int mVoteAverage;
    private String mImage;
    private int mRuntime; // Only have in Movie Detail

    public Movie(int id, String name) {
        mId = id;
        mName = name;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getOverview() {
        return mOverview;
    }

    public void setOverview(String overview) {
        this.mOverview = overview;
    }

    public int getVoteAverage() {
        return mVoteAverage;
    }

    public void setVoteAverage(int voteAverage) {
        this.mVoteAverage = voteAverage;
    }

    public int getRuntime() {
        return mRuntime;
    }

    public void setRuntime(int runtime) {
        this.mRuntime = runtime;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        this.mImage = image;
    }
}
