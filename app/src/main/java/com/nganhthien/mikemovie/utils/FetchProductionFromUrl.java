package com.nganhthien.mikemovie.utils;

import com.nganhthien.mikemovie.data.model.Production;
import com.nganhthien.mikemovie.data.source.ProductionDataSource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FetchProductionFromUrl extends BaseFetchDataFromUrl<List<Production>> {
    private ProductionDataSource.OnFetchDataListener mListener;

    public FetchProductionFromUrl(ProductionDataSource.OnFetchDataListener listener) {
        mListener = listener;
        mWrapperException = new WrapperException();
    }

    @Override
    protected List<Production> doInBackground(String... strings) {
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
    protected void onPostExecute(List<Production> casts) {
        if (mListener == null) {
            return;
        }
        if (mWrapperException.getException() == null) {
            mListener.onFetchDataSuccess(casts);
        } else {
            mListener.onFetchDataFailed(mWrapperException.getException());
        }
    }

    @Override
    protected List<Production> extractDataFromJson(String dataJSON) throws JSONException {
        List<Production> listData = new ArrayList<>();

        JSONObject baseJsonObject = new JSONObject(dataJSON);
        JSONArray resultsArray =
                baseJsonObject.getJSONArray(Production.JsonKey.PRODUCTION_COMPANIES);
        for (int i = 0; i < resultsArray.length(); i++) {
            // Current cursor
            JSONObject currentItemData = resultsArray.getJSONObject(i);

            int id = currentItemData.optInt(Production.JsonKey.ID);
            String logoPath = currentItemData.optString(Production.JsonKey.LOGO_PATH);
            String name = currentItemData.optString(Production.JsonKey.NAME);

            Production production = new Production();
            production.setId(id);
            production.setName(name);
            production.setLogoPath(logoPath);

            listData.add(production);
        }
        return listData;
    }
}
