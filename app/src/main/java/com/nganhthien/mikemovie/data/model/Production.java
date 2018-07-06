package com.nganhthien.mikemovie.data.model;

import com.nganhthien.mikemovie.utils.Constants;

public class Production {
    private int mId;
    private String mLogoPath;
    private String mName;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getLogoPath() {
        return mLogoPath;
    }

    public void setLogoPath(String logoPath) {
        mLogoPath = logoPath;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String createImageUrl() {
        StringBuilder url = new StringBuilder();
        url.append(Constants.UrlConfig.PROTOCOL);
        url.append(String.format(Constants.MovieApi.DOMAIN_POSTER_IMAGE, mLogoPath));
        return url.toString();
    }

    public static class JsonKey {
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String LOGO_PATH = "logo_path";
        public static final String PRODUCTION_COMPANIES = "production_companies";

        private JsonKey() {
        }
    }
}
