package com.nganhthien.mikemovie.utils;

import com.nganhthien.mikemovie.data.model.Person;
import com.nganhthien.mikemovie.data.source.PersonDataSource;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class FetchPersonFromUrl extends BaseFetchDataFromUrl<Person> {
    private PersonDataSource.OnFetchDataListener mListener;

    public FetchPersonFromUrl(PersonDataSource.OnFetchDataListener listener) {
        mListener = listener;
        mWrapperException = new WrapperException();
    }

    @Override
    protected Person doInBackground(String... strings) {
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
    protected void onPostExecute(Person person) {
        if (mListener == null) {
            return;
        }
        if (mWrapperException.getException() == null) {
            mListener.onFetchPersonSuccess(person);
        } else {
            mListener.onFetchPersonFailed(mWrapperException.getException());
        }
    }

    @Override
    protected Person extractDataFromJson(String dataJSON) throws JSONException {
        Person person = new Person();

        JSONObject baseJsonObject = new JSONObject(dataJSON);
        String birthday = baseJsonObject.optString(Person.JsonKey.BIRTHDAY);
        String id = baseJsonObject.optString(Person.JsonKey.ID);
        String name = baseJsonObject.optString(Person.JsonKey.NAME);
        String biography = baseJsonObject.optString(Person.JsonKey.BIOGRAPHY);
        String profilePath = baseJsonObject.optString(Person.JsonKey.PROFILE_PATH);
        String gender = baseJsonObject.optString(Person.JsonKey.GENDER);
        String popularity = baseJsonObject.optString(Person.JsonKey.POPULARITY);

        person.setId(id);
        person.setName(name);
        person.setBirthday(birthday);
        person.setBiography(biography);
        person.setProfilePath(profilePath);
        person.setGender(gender);
        person.setPopularity(popularity);

        return person;
    }
}
