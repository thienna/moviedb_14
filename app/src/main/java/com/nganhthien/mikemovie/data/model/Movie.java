package com.nganhthien.mikemovie.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.nganhthien.mikemovie.utils.Constants;

/**
 * Created by ThienNA on 29/06/2018.
 */

public class Movie implements Parcelable {
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

    protected Movie(Parcel in) {
        mId = in.readInt();
        mTitle = in.readString();
        mOverview = in.readString();
        mVoteAverage = in.readInt();
        mPosterImage = in.readString();
        mBackdropImage = in.readString();
        mReleaseDate = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mId);
        parcel.writeString(mTitle);
        parcel.writeString(mOverview);
        parcel.writeInt(mVoteAverage);
        parcel.writeString(mPosterImage);
        parcel.writeString(mBackdropImage);
        parcel.writeString(mReleaseDate);
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

    public String createImageUrl(String type) {
        StringBuilder url = new StringBuilder();
        url.append(Constants.UrlConfig.PROTOCOL);
        String typeAfterFormat;
        if (type.equals(Constants.MovieApi.DOMAIN_BACKDROP_IMAGE)) {
            typeAfterFormat = String.format(type, mBackdropImage);
        } else {
            typeAfterFormat = String.format(type, getPosterImage());
        }
        return url.append(typeAfterFormat).toString();
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
