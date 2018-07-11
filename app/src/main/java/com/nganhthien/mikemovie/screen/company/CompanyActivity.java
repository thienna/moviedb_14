package com.nganhthien.mikemovie.screen.company;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.nganhthien.mikemovie.R;
import com.nganhthien.mikemovie.data.model.Company;
import com.nganhthien.mikemovie.data.model.Movie;
import com.nganhthien.mikemovie.data.repository.MovieRepository;
import com.nganhthien.mikemovie.screen.BaseActivity;

import java.util.List;

public class CompanyActivity extends BaseActivity
        implements CompanyContract.View, View.OnClickListener {
    private static final String EXTRA_COMPANY_ID = "EXTRA_COMPANY_ID";
    private int mCompanyId;
    private CompanyContract.Presenter mPresenter;
    private RecyclerView mRecyclerRelatedMovies;
    private CompanyMoviesRecyclerAdapter mCompanyMoviesRecyclerAdapter;
    private ImageView mImageCompanyLogo;
    private TextView mTextCompanyName;
    private TextView mTextCompanyHeadquarters;
    private TextView mTextCompanyCountry;
    private ImageView mImageIconBack;

    public static Intent getInstance(Context context, int companyId) {
        Intent intent = new Intent(context, CompanyActivity.class);
        intent.putExtra(EXTRA_COMPANY_ID, companyId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);
        mCompanyId = getIntent().getIntExtra(EXTRA_COMPANY_ID, 0);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initView();
        mPresenter = new CompanyPresenter(MovieRepository.getInstance(this));
        mPresenter.setView(this);
        mPresenter.loadCompany(mCompanyId);
        mPresenter.loadMovies(mCompanyId);

        mRecyclerRelatedMovies.setAdapter(mCompanyMoviesRecyclerAdapter);
    }

    @Override
    public void showCompanySuccess(Company company) {
        Glide.with(this)
                .load(company.createImageUrl())
                .apply(new RequestOptions().placeholder(R.drawable.movie_detail_poster_sample))
                .into(mImageCompanyLogo);
        mTextCompanyCountry.setText(company.getOriginCountry());
        mTextCompanyHeadquarters.setText(company.getHeadquarters());
        mTextCompanyName.setText(company.getName());
    }

    @Override
    public void showCompanyFailed(Exception e) {
    }

    @Override
    public void showRelatedMoviesSuccess(List<Movie> movies) {
        mCompanyMoviesRecyclerAdapter.setData(movies);
    }

    @Override
    public void showRelatedMoviesFailed(Exception e) {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_toolbar_back_icon:
                onBackPressed();
                break;
        }
    }

    private void initView() {
        mImageCompanyLogo = findViewById(R.id.image_company_profile);
        mImageIconBack = findViewById(R.id.image_toolbar_back_icon);
        mTextCompanyCountry = findViewById(R.id.text_company_origin_country_value);
        mTextCompanyHeadquarters = findViewById(R.id.text_company_headquarters_value);
        mTextCompanyName = findViewById(R.id.text_company_name);
        mRecyclerRelatedMovies = findViewById(R.id.recycler_company_movies);
        mCompanyMoviesRecyclerAdapter = new CompanyMoviesRecyclerAdapter();
        mImageIconBack.setOnClickListener(this);
    }
}
