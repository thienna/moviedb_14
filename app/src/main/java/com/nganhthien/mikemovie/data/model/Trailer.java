package com.nganhthien.mikemovie.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Trailer implements Parcelable {
    public static final Creator<Trailer> CREATOR = new Creator<Trailer>() {
        @Override
        public Trailer createFromParcel(Parcel in) {
            return new Trailer(in);
        }

        @Override
        public Trailer[] newArray(int size) {
            return new Trailer[size];
        }
    };

    private String mId;
    private String mKey;
    private String mName;

    public Trailer() {
    }

    protected Trailer(Parcel in) {
        mId = in.readString();
        mKey = in.readString();
        mName = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mKey);
        dest.writeString(mName);
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getKey() {
        return mKey;
    }

    public void setKey(String key) {
        mKey = key;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public static class JsonKey {
        public static final String ID = "id";
        public static final String KEY = "key";
        public static final String NAME = "name";
        public static final String RESULTS = "results";

        private JsonKey() {
        }
    }
}
