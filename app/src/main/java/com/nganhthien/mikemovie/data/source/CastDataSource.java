package com.nganhthien.mikemovie.data.source;

import com.nganhthien.mikemovie.data.model.Cast;

import java.util.List;

public interface CastDataSource {
    interface RemoteDataSource {
        void loadCastsRemote(OnFetchCastListener listener, int id);
    }

    interface OnFetchCastListener {
        void onFetchCastSuccess(List<Cast> casts);

        void onFetchCastFailed(Exception e);
    }
}
