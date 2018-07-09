package com.nganhthien.mikemovie.utils;

import com.nganhthien.mikemovie.data.model.Cast;
import com.nganhthien.mikemovie.data.source.CastDataSource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FetchCastFromUrl extends BaseFetchDataFromUrl<List<Cast>> {
    private CastDataSource.OnFetchCastListener mListener;

    public FetchCastFromUrl(CastDataSource.OnFetchCastListener listener) {
        mListener = listener;
        mWrapperException = new WrapperException();
    }

    @Override
    protected List<Cast> doInBackground(String... strings) {
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
    protected void onPostExecute(List<Cast> casts) {
        if (mListener == null) {
            return;
        }
        if (mWrapperException.getException() == null) {
            mListener.onFetchCastSuccess(casts);
        } else {
            mListener.onFetchCastFailed(mWrapperException.getException());
        }
    }

    @Override
    protected List<Cast> extractDataFromJson(String dataJSON) throws JSONException {
        List<Cast> casts = new ArrayList<>();

        JSONObject baseJsonObject = new JSONObject(dataJSON);
        JSONArray resultsArray =
                baseJsonObject.getJSONArray(Cast.JsonKey.CAST);
        for (int i = 0; i < resultsArray.length(); i++) {
            // Current cursor
            JSONObject currentCast = resultsArray.getJSONObject(i);

            int id = currentCast.optInt(Cast.JsonKey.ID);
            int castId = currentCast.optInt(Cast.JsonKey.CAST_ID);
            String name = currentCast.optString(Cast.JsonKey.NAME);
            String character = currentCast.optString(Cast.JsonKey.CHARACTER);
            String profilePath = currentCast.optString(Cast.JsonKey.PROFILE_PATH);

            Cast cast = new Cast();
            cast.setId(id);
            cast.setName(name);
            cast.setCastId(castId);
            cast.setCharacter(character);
            cast.setProfilePath(profilePath);

            casts.add(cast);
        }
        return casts;
    }
}
