package com.nganhthien.mikemovie.data.model;

import com.nganhthien.mikemovie.utils.Constants;

public class Cast {
    private int mCastId;
    private int mId;
    private String mCharacter;
    private String mName;
    private String mProfilePath;

    public int getCastId() {
        return mCastId;
    }

    public void setCastId(int castId) {
        mCastId = castId;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getCharacter() {
        return mCharacter;
    }

    public void setCharacter(String character) {
        mCharacter = character;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getProfilePath() {
        return mProfilePath;
    }

    public void setProfilePath(String profilePath) {
        mProfilePath = profilePath;
    }

    public String createImageUrl() {
        StringBuilder url = new StringBuilder();
        url.append(Constants.UrlConfig.PROTOCOL);
        url.append(String.format(Constants.MovieApi.DOMAIN_POSTER_IMAGE, mProfilePath));
        return url.toString();
    }

    public static class JsonKey {
        public static final String ID = "id";
        public static final String CAST = "cast";
        public static final String CAST_ID = "cast_id";
        public static final String CHARACTER = "character";
        public static final String NAME = "name";
        public static final String PROFILE_PATH = "profile_path";

        private JsonKey() {
        }
    }
}
