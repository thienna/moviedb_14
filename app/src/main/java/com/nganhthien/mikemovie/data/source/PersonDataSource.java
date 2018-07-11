package com.nganhthien.mikemovie.data.source;

import com.nganhthien.mikemovie.data.model.Person;

public interface PersonDataSource {
    interface RemoteDataSource {
        void loadPersonRemote(int personId, OnFetchDataListener listener);
    }

    interface OnFetchDataListener {
        void onFetchPersonSuccess(Person item);

        void onFetchPersonFailed(Exception e);
    }
}
