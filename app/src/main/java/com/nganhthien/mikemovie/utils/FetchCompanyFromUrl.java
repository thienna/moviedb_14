package com.nganhthien.mikemovie.utils;

import com.nganhthien.mikemovie.data.model.Company;
import com.nganhthien.mikemovie.data.source.CompanyDataSource;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class FetchCompanyFromUrl extends BaseFetchDataFromUrl<Company> {
    private CompanyDataSource.OnFetchDataListener mListener;

    public FetchCompanyFromUrl(CompanyDataSource.OnFetchDataListener listener) {
        mListener = listener;
        mWrapperException = new WrapperException();
    }

    @Override
    protected Company doInBackground(String... strings) {
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
    protected void onPostExecute(Company company) {
        if (mListener == null) {
            return;
        }
        if (mWrapperException.getException() == null) {
            mListener.onFetchCompanySuccess(company);
        } else {
            mListener.onFetchCompanyFailed(mWrapperException.getException());
        }
    }

    @Override
    protected Company extractDataFromJson(String dataJSON) throws JSONException {
        Company company = new Company();

        JSONObject baseJsonObject = new JSONObject(dataJSON);
        String id = baseJsonObject.optString(Company.JsonKey.ID);
        String name = baseJsonObject.optString(Company.JsonKey.NAME);
        String country = baseJsonObject.optString(Company.JsonKey.COUNTRY);
        String description = baseJsonObject.optString(Company.JsonKey.DESCRIPTION);
        String logoPath = baseJsonObject.optString(Company.JsonKey.LOGO_PATH);
        String headquarters = baseJsonObject.optString(Company.JsonKey.HEADQUARTERS);
        String homepage = baseJsonObject.optString(Company.JsonKey.HOMEPAGE);

        company.setId(id);
        company.setName(name);
        company.setDescription(description);
        company.setHeadquarters(headquarters);
        company.setHomepage(homepage);
        company.setLogoPath(logoPath);
        company.setOriginCountry(country);

        return company;
    }
}
