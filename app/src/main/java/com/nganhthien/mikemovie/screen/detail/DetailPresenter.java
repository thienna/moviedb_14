package com.nganhthien.mikemovie.screen.detail;

import com.nganhthien.mikemovie.data.model.Cast;
import com.nganhthien.mikemovie.data.model.Production;
import com.nganhthien.mikemovie.data.repository.CastRepository;
import com.nganhthien.mikemovie.data.repository.ProductionRepository;
import com.nganhthien.mikemovie.data.source.CastDataSource;
import com.nganhthien.mikemovie.data.source.ProductionDataSource;

import java.util.List;

public class DetailPresenter implements DetailContract.Presenter,
        CastDataSource.OnFetchCastListener, ProductionDataSource.OnFetchDataListener {
    private DetailContract.View mView;
    private CastRepository mCastRepository;
    private ProductionRepository mProductionRepository;

    public DetailPresenter() {
        mCastRepository = CastRepository.getInstance();
        mProductionRepository = ProductionRepository.getInstance();
    }

    @Override
    public void loadCastRemote(int id) {
        mCastRepository.loadCastsRemote(this, id);
    }

    @Override
    public void loadProductionRemote(int id) {
        mProductionRepository.loadProductionRemote(this, id);
    }

    @Override
    public void setView(DetailContract.View view) {
        mView = view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onFetchCastSuccess(List<Cast> casts) {
        mView.showLoadCastSuccess(casts);
    }

    @Override
    public void onFetchDataSuccess(List<Production> productions) {
        mView.showLoadProductionSuccess(productions);
    }

    @Override
    public void onFetchDataFailed(Exception e) {
        mView.showLoadProductionFailed(e);
    }

    @Override
    public void onFetchCastFailed(Exception e) {
        mView.showLoadCastFailed(e);
    }
}
