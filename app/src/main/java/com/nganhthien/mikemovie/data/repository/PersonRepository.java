package com.nganhthien.mikemovie.data.repository;

import android.support.annotation.NonNull;

import com.nganhthien.mikemovie.data.source.PersonDataSource;
import com.nganhthien.mikemovie.data.source.remote.PersonRemoteDataSource;

import static com.google.common.base.Preconditions.checkNotNull;

public class PersonRepository implements PersonDataSource.RemoteDataSource {
    private static PersonRepository sInstance;

    @NonNull
    private PersonDataSource.RemoteDataSource mRemoteDataSource;

    private PersonRepository(
            @NonNull PersonDataSource.RemoteDataSource remoteDataSource) {
        mRemoteDataSource = checkNotNull(remoteDataSource);
    }

    public static synchronized PersonRepository getInstance() {
        if (sInstance == null) {
            sInstance = new PersonRepository(checkNotNull(PersonRemoteDataSource.getInstance()));
        }

        return sInstance;
    }

    public static void destroyInstance() {
        sInstance = null;
        PersonRemoteDataSource.destroyInstance();
    }

    @Override
    public void loadPersonRemote(int personId, PersonDataSource.OnFetchDataListener listener) {
        mRemoteDataSource.loadPersonRemote(personId, listener);
    }
}
