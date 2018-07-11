package com.nganhthien.mikemovie.data.source.remote;

import android.util.Log;

import com.nganhthien.mikemovie.BuildConfig;
import com.nganhthien.mikemovie.data.source.PersonDataSource;
import com.nganhthien.mikemovie.utils.Constants;
import com.nganhthien.mikemovie.utils.FetchPersonFromUrl;

public class PersonRemoteDataSource implements PersonDataSource.RemoteDataSource {

    private static PersonRemoteDataSource sInstance;

    private PersonRemoteDataSource() {
    }

    public static PersonRemoteDataSource getInstance() {
        if (sInstance == null) {
            sInstance = new PersonRemoteDataSource();
        }
        return sInstance;
    }

    public static void destroyInstance() {
        sInstance = null;
    }

    @Override
    public void loadPersonRemote(int personId, PersonDataSource.OnFetchDataListener listener) {
        StringBuilder url = new StringBuilder();
        url.append(Constants.UrlConfig.PROTOCOL);
        url.append(Constants.MovieApi.DOMAIN);
        url.append(Constants.MovieApi.PERSON_DETAIL);
        url.append(personId);
        url.append(Constants.UrlConfig.QUERY_MARK);
        url.append(Constants.MovieApi.PARAM_KEY_API_KEY + BuildConfig.API_KEY);
        url.append(Constants.UrlConfig.ADD_PARAM_MARK);
        url.append(Constants.MovieApi.PARAM_KEY_LANGUAGE + Constants.MovieApi.PARAM_VALUE_LANGUAGE);
        new FetchPersonFromUrl(listener).execute(url.toString());
    }
}
