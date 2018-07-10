package com.nganhthien.mikemovie.screen.person;

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
import com.nganhthien.mikemovie.data.model.Movie;
import com.nganhthien.mikemovie.data.model.Person;
import com.nganhthien.mikemovie.screen.BaseActivity;
import com.nganhthien.mikemovie.utils.Constants;

import java.util.List;

public class PersonActivity extends BaseActivity implements PersonContract.View, View.OnClickListener {
    private static final String EXTRA_PERSON_ID = "EXTRA_PERSON_ID";
    private PersonContract.Presenter mPresenter;
    private int mPersonId;
    private ImageView mImagePersonProfile;
    private TextView mTextPersonName;
    private TextView mTextPersonBirthday;
    private TextView mTextPersonPopularity;
    private TextView mTextPersonBiography;
    private RecyclerView mRecyclerRelatedMovies;
    private PersonMoviesRecyclerAdapter mPersonMoviesRecyclerAdapter;
    private boolean mIsTextBiographyExpanded;
    private ImageView mImageIconBack;

    public static Intent getInstance(Context context, int personId) {
        Intent intent = new Intent(context, PersonActivity.class);
        intent.putExtra(EXTRA_PERSON_ID, personId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        mPersonId = getIntent().getIntExtra(EXTRA_PERSON_ID, 0);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initView();
        mPresenter = new PersonPresenter();
        mPresenter.setView(this);
        mPresenter.loadPerson(mPersonId);

        mRecyclerRelatedMovies.setAdapter(mPersonMoviesRecyclerAdapter);
    }

    @Override
    public void showPersonSuccess(Person person) {
        mImagePersonProfile = findViewById(R.id.image_person_profile);
        mTextPersonName.setText(person.getName());
        mTextPersonBirthday.setText(person.getBirthday());
        mTextPersonPopularity.setText(person.getPopularity());
        mTextPersonBiography.setText(person.getBiography());
        Glide.with(this)
                .load(person.createImageUrl())
                .apply(new RequestOptions().placeholder(R.drawable.movie_detail_poster_sample))
                .into(mImagePersonProfile);
        mPresenter.loadMovies(person.getName());
    }

    @Override
    public void showPersonFailed(Exception e) {
    }

    @Override
    public void showRelatedMoviesSuccess(List<Movie> movies) {
        mPersonMoviesRecyclerAdapter.setData(movies);
    }

    @Override
    public void showRelatedMoviesFailed(Exception e) {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_person_biography:
                toggleExpandLine();
                break;
            case R.id.image_toolbar_back_icon:
                onBackPressed();
                break;
        }
    }

    private void initView() {
        mImagePersonProfile = findViewById(R.id.image_person_profile);
        mTextPersonName = findViewById(R.id.text_person_name);
        mTextPersonBirthday = findViewById(R.id.text_person_birthday_value);
        mTextPersonPopularity = findViewById(R.id.text_person_popular_value);
        mTextPersonBiography = findViewById(R.id.text_person_biography);
        mRecyclerRelatedMovies = findViewById(R.id.recycler_person_movies);
        mPersonMoviesRecyclerAdapter = new PersonMoviesRecyclerAdapter();
        mImageIconBack = findViewById(R.id.image_toolbar_back_icon);
        mImageIconBack.setOnClickListener(this);
        mTextPersonBiography.setOnClickListener(this);
    }

    private void toggleExpandLine() {
        if (mIsTextBiographyExpanded) {
            mTextPersonBiography.setMaxLines(Constants.DETAIL_SCREEN_OVEVERVIEW_MINLINE);
        } else {
            mTextPersonBiography.setMaxLines(Constants.DETAIL_SCREEN_OVEVERVIEW_MAXLINE);
        }
        mIsTextBiographyExpanded = !mIsTextBiographyExpanded;
    }
}
