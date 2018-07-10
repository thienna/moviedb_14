package com.nganhthien.mikemovie.data.model;

import com.nganhthien.mikemovie.utils.Constants;

public class Company {
    private String mId;
    private String mName;
    private String mHeadquarters;
    private String mLogoPath;
    private String mOriginCountry;
    private String mHomepage;
    private String mDescription;

    public Company() {
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

    public String getHeadquarters() {
        return mHeadquarters;
    }

    public void setHeadquarters(String headquarters) {
        mHeadquarters = headquarters;
    }

    public String getLogoPath() {
        return mLogoPath;
    }

    public void setLogoPath(String logoPath) {
        mLogoPath = logoPath;
    }

    public String getOriginCountry() {
        return mOriginCountry;
    }

    public void setOriginCountry(String originCountry) {
        mOriginCountry = originCountry;
    }

    public String getHomepage() {
        return mHomepage;
    }

    public void setHomepage(String homepage) {
        mHomepage = homepage;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String createImageUrl() {
        StringBuilder url = new StringBuilder();
        url.append(Constants.UrlConfig.PROTOCOL);
        url.append(Constants.MovieApi.DOMAIN_BACKDROP_IMAGE_PRODUCTION);
        url.append(mLogoPath);
        return url.toString();
    }

    public static class JsonKey {
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String HEADQUARTERS = "headquarters";
        public static final String LOGO_PATH = "logo_path";
        public static final String HOMEPAGE = "homepage";
        public static final String DESCRIPTION = "description";
        public static final String COUNTRY = "origin_country";

        private JsonKey() {
        }
    }
}
