package com.nganhthien.mikemovie.data.model;

/**
 * Created by ThienNA on 30/06/2018.
 */

public class Genre {

    private int mId;
    private String mName;

    public Genre(int id, String name) {
        mId = id;
        mName = name;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
