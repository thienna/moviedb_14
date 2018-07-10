package com.nganhthien.mikemovie.utils;

import com.nganhthien.mikemovie.data.model.Trailer;
import com.nganhthien.mikemovie.data.source.TrailerDataSource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FetchTrailerFromUrl extends BaseFetchDataFromUrl<List<Trailer>>{
    private TrailerDataSource.OnFetchTrailerListener mListener;

    public FetchTrailerFromUrl(TrailerDataSource.OnFetchTrailerListener listener) {
        mListener = listener;
        mWrapperException = new WrapperException();
    }

    @Override
    protected List<Trailer> doInBackground(String... strings) {
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
    protected void onPostExecute(List<Trailer> trailers) {
        if (mListener == null) {
            return;
        }
        if (mWrapperException.getException() == null) {
            mListener.onFetchTrailerSuccess(trailers);
        } else {
            mListener.onFetchTrailerFailed(mWrapperException.getException());
        }
    }

    @Override
    protected List<Trailer> extractDataFromJson(String dataJSON) throws JSONException {
        List<Trailer> trailers = new ArrayList<>();

        JSONObject baseJsonObject = new JSONObject(dataJSON);
        JSONArray resultsArray =
                baseJsonObject.getJSONArray(Trailer.JsonKey.RESULTS);
        for (int i = 0; i < resultsArray.length(); i++) {
            // Current cursor
            JSONObject currentTrailer = resultsArray.getJSONObject(i);

            String id = currentTrailer.optString(Trailer.JsonKey.ID);
            String key = currentTrailer.optString(Trailer.JsonKey.KEY);
            String name = currentTrailer.optString(Trailer.JsonKey.NAME);

            Trailer trailer = new Trailer();
            trailer.setId(id);
            trailer.setName(name);
            trailer.setKey(key);

            trailers.add(trailer);
        }
        return trailers;
    }
}
