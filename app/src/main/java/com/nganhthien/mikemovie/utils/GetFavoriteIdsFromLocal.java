package com.nganhthien.mikemovie.utils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nganhthien.mikemovie.data.model.Movie;
import com.nganhthien.mikemovie.data.source.MovieDataSource;

import java.util.ArrayList;
import java.util.List;

public class GetFavoriteIdsFromLocal extends BaseGetDataFromLocal<List<Integer>> {
    private MovieDataSource.OnGetFavoriteIdsListener mListener;
    private SQLiteDatabase mDatabase;

    public GetFavoriteIdsFromLocal(MovieDataSource.OnGetFavoriteIdsListener listener,
                                   SQLiteDatabase db) {
        mListener = listener;
        mDatabase = db;
        mWrapperException = new WrapperException();
    }

    @Override
    protected List<Integer> doInBackground(String... strings) {
        try {
            return getIds();
        } catch (Exception e) {
            mWrapperException.setException(e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Integer> ids) {
        if (mListener == null) {
            return;
        }
        if (mWrapperException.getException() == null) {
            mListener.onGetFavoriteIdsSuccess(ids);
        } else {
            mListener.onGetFavoriteIdsFailed(mWrapperException.getException());
        }
    }

    private List<Integer> getIds() throws Exception {
        List<Integer> results = new ArrayList<>();

        String query = "SELECT " + Movie.DatabaseConfig.ID + " FROM " + Movie.DatabaseConfig
                .TABLE_NAME + ";";

        Cursor cursor = mDatabase.rawQuery(query, null);

        if (cursor.moveToNext()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(Movie.DatabaseConfig.ID));
                results.add(id);
            } while (cursor.moveToNext());
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
        mDatabase.close();
        return results;
    }
}
