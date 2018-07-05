package com.nganhthien.mikemovie.utils;

import com.nganhthien.mikemovie.data.model.Genre;
import com.nganhthien.mikemovie.data.source.GenreDataSource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThienNA on 02/07/2018.
 */

public class FetchGenreFromUrl extends BaseFetchDataFromUrl<List<Genre>> {
    private GenreDataSource.OnFetchGenreCallback mCallback;

    public FetchGenreFromUrl(GenreDataSource.OnFetchGenreCallback callback) {
        mCallback = callback;
        mWrapperException = new WrapperException();
    }

    @Override
    protected List<Genre> doInBackground(String... strings) {
        try {
            return extractDataFromJson(getJsonStringFromUrl(strings[0]));
        } catch (JSONException e) {
            mWrapperException.setException(e);
        } catch (IOException e) {
            mWrapperException.setException(e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Genre> genres) {
        if (mCallback == null) {
            return;
        }
        if (mWrapperException.getException() == null) {
            mCallback.onFetchGenreSuccess(genres);
        } else {
            mCallback.onFetchGenreFailed(mWrapperException.getException());
        }
    }

    @Override
    protected List<Genre> extractDataFromJson(String dataJSON) throws JSONException {
        List<Genre> genres = new ArrayList<>();

        JSONObject baseJsonResponse = new JSONObject(dataJSON);
        JSONArray genreJsonArray =
                baseJsonResponse.getJSONArray(Genre.JsonKey.GENRES);

        for (int i = 0; i < genreJsonArray.length(); i++) {
            JSONObject currentObject = genreJsonArray.getJSONObject(i);
            int id = currentObject.getInt(Genre.JsonKey.ID);
            String name = currentObject.getString(Genre.JsonKey.NAME);
            genres.add(new Genre(id, name));
        }
        return genres;
    }
}
