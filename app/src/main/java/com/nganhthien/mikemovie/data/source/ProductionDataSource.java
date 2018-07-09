package com.nganhthien.mikemovie.data.source;

import com.nganhthien.mikemovie.data.model.Production;

import java.util.List;

public interface ProductionDataSource {
    interface RemoteDataSource {
        void loadProductionRemote(OnFetchDataListener listener, int id);
    }

    interface OnFetchDataListener {
        void onFetchDataSuccess(List<Production> productions);

        void onFetchDataFailed(Exception e);
    }
}
