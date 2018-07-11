package com.nganhthien.mikemovie.data.model;

import com.nganhthien.mikemovie.utils.Constants;

public class Person {
    private String mId;
    private String mName;
    private String mBirthday;
    private String mBiography;
    private String mPopularity;
    private String mProfilePath;
    private String mGender;

    public Person() {
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getBirthday() {
        return mBirthday;
    }

    public void setBirthday(String birthday) {
        mBirthday = birthday;
    }

    public String getBiography() {
        return mBiography;
    }

    public void setBiography(String biography) {
        mBiography = biography;
    }

    public String getPopularity() {
        return mPopularity;
    }

    public void setPopularity(String popularity) {
        mPopularity = popularity;
    }

    public String getProfilePath() {
        return mProfilePath;
    }

    public void setProfilePath(String profilePath) {
        mProfilePath = profilePath;
    }

    public String getGender() {
        return mGender;
    }

    public void setGender(String gender) {
        mGender = gender;
    }

    public String createImageUrl() {
        StringBuilder url = new StringBuilder();
        url.append(Constants.UrlConfig.PROTOCOL);
        url.append(String.format(Constants.MovieApi.DOMAIN_POSTER_IMAGE, mProfilePath));
        return url.toString();
    }

    public static class JsonKey {
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String BIOGRAPHY = "biography";
        public static final String POPULARITY = "popularity";
        public static final String BIRTHDAY = "birthday";
        public static final String GENDER = "gender";
        public static final String PROFILE_PATH = "profile_path";
        public static final String KNOWN_FOR = "known_for";

        private JsonKey() {
        }
    }
}
