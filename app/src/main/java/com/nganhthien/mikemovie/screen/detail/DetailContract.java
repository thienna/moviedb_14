package com.nganhthien.mikemovie.screen.detail;

import com.nganhthien.mikemovie.data.model.Cast;
import com.nganhthien.mikemovie.data.model.Production;
import com.nganhthien.mikemovie.data.model.Trailer;
import com.nganhthien.mikemovie.screen.BasePresenter;

import java.util.List;

public interface DetailContract {
    interface View {
        void showLoadCastSuccess(List<Cast> casts);

        void showLoadCastFailed(Exception e);

        void showLoadProductionSuccess(List<Production> productions);

        void showLoadProductionFailed(Exception e);

        void showLoadTrailerSuccess(List<Trailer> trailers);

        void showLoadTrailerFailed(Exception e);

    }

    interface Presenter extends BasePresenter<DetailContract.View> {
        void loadCastRemote(int id);

        void loadProductionRemote(int id);

        void loadTrailerRemote(int id);
    }
}
