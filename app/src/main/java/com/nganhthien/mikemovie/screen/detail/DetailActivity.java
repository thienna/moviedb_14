package com.nganhthien.mikemovie.screen.detail;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.nganhthien.mikemovie.R;
import com.nganhthien.mikemovie.data.model.Cast;
import com.nganhthien.mikemovie.data.model.Movie;
import com.nganhthien.mikemovie.data.model.Production;
import com.nganhthien.mikemovie.data.model.Trailer;
import com.nganhthien.mikemovie.data.repository.MovieRepository;
import com.nganhthien.mikemovie.screen.BaseActivity;
import com.nganhthien.mikemovie.screen.person.PersonActivity;
import com.nganhthien.mikemovie.screen.youtube.YoutubeActivity;
import com.nganhthien.mikemovie.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends BaseActivity
        implements DetailContract.View, View.OnClickListener,
        DetailCastRecyclerAdapter.OnRecyclerViewItemClickListener {
    private static final String EXTRA_MOVIE = "EXTRA_MOVIE";
    private DetailContract.Presenter mPresenter;
    private Movie mMovie;
    private ImageView mImageBackdrop;
    private ImageView mImagePoster;
    private TextView mTextTitle;
    private TextView mTextRating;
    private TextView mTextReleaseDate;
    private TextView mTextOverview;
    private boolean mIsTextOverviewExpanded;
    private RecyclerView mRecyclerViewCast;
    private RecyclerView mRecyclerViewProduction;
    private DetailCastRecyclerAdapter mDetailCastRecyclerAdapter;
    private DetailProductionRecyclerAdapter mDetailProductionRecyclerAdapter;
    private ImageView mPlayYoutubeButton;
    private List<Trailer> mTrailers;
    private ImageView mFloatingFavoriteButton;
    private ProgressBar mProgressBar;
    private List<Integer> mIds;
    private ConstraintLayout mConstraintLayout;

    public static Intent getInstance(Context context, Movie movie) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(EXTRA_MOVIE, movie);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mMovie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        initView();
        initViewContent();
        mPresenter = new DetailPresenter(MovieRepository.getInstance(this));
        mPresenter.setView(this);
        mPresenter.loadCastRemote(mMovie.getId());
        mPresenter.loadProductionRemote(mMovie.getId());
        mPresenter.loadTrailerRemote(mMovie.getId());
        mPresenter.loadFavoriteIds();

        mRecyclerViewCast.setAdapter(mDetailCastRecyclerAdapter);
        mRecyclerViewProduction.setAdapter(mDetailProductionRecyclerAdapter);

        findViewById(R.id.image_ic_detail_back).setOnClickListener(this);
        mTextOverview.setOnClickListener(this);
        mPlayYoutubeButton.setOnClickListener(this);
    }

    @Override
    public void showLoadProductionSuccess(List<Production> productions) {
        mDetailProductionRecyclerAdapter.setData(productions);
    }

    @Override
    public void showLoadProductionFailed(Exception e) {
    }

    @Override
    public void showLoadCastSuccess(List<Cast> casts) {
        mDetailCastRecyclerAdapter.setData(casts);
    }

    @Override
    public void showLoadCastFailed(Exception e) {
    }

    @Override
    public void showLoadTrailerSuccess(List<Trailer> trailers) {
        mTrailers = trailers;
    }

    @Override
    public void showLoadTrailerFailed(Exception e) {
    }

    @Override
    public void onClickGenresRecyclerViewItem(int id) {
        startActivity(PersonActivity.getInstance(this, id));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        mMovie = intent.getParcelableExtra(EXTRA_MOVIE);
        initViewContent();
        mPresenter.loadCastRemote(mMovie.getId());
        mPresenter.loadProductionRemote(mMovie.getId());
        mPresenter.loadTrailerRemote(mMovie.getId());
    }

    @Override
    public void showFavoriteIdsSuccess(List<Integer> ids) {
        mIds = ids;
        for (int i : mIds) {
            if (mMovie.getId() == i){
                mMovie.setFavorite(true);
            }
        }
        if (mMovie.isFavorite()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mFloatingFavoriteButton.setBackgroundTintList(ColorStateList.valueOf(getResources
                        ().getColor(R.color
                        .colorPrimary)));
            }
        }
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showFavoriteIdsFailed() {
    }

    @Override
    public void showAddFavoriteSuccess(Movie movie) {
        mMovie.setFavorite(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mFloatingFavoriteButton.setBackgroundTintList(ColorStateList.valueOf(getResources
                    ().getColor(R.color
                    .colorPrimary)));
        }
        mProgressBar.setVisibility(View.GONE);
        Snackbar.make(mConstraintLayout, R.string.add_favorite_success, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showAddFavoriteFailed() {
        mProgressBar.setVisibility(View.GONE);
        Snackbar.make(mConstraintLayout, R.string.add_favorite_failed, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showDeleteFavoriteSuccess(Movie movie) {
        mMovie.setFavorite(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mFloatingFavoriteButton.setBackgroundTintList(ColorStateList.valueOf(getResources
                    ().getColor(R.color
                    .color_search_item_overview)));
        }
        mProgressBar.setVisibility(View.GONE);
        Snackbar.make(mConstraintLayout, R.string.remove_favorite_success, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showDeleteFavoriteFailed() {
        mProgressBar.setVisibility(View.GONE);
        Snackbar.make(mConstraintLayout, R.string.remove_favorite_failed, Snackbar.LENGTH_LONG).show();
    }

    private void initView() {
        mImageBackdrop = findViewById(R.id.image_detail_backdrop);
        mImagePoster = findViewById(R.id.image_detail_poster);
        mTextTitle = findViewById(R.id.text_detail_title);
        mTextRating = findViewById(R.id.text_detail_rating_value);
        mTextReleaseDate = findViewById(R.id.text_detail_release_value);
        mTextOverview = findViewById(R.id.text_detail_overview);
        mRecyclerViewCast = findViewById(R.id.recycler_detail_casts);
        mRecyclerViewCast.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mDetailCastRecyclerAdapter = new DetailCastRecyclerAdapter(this);
        mRecyclerViewProduction = findViewById(R.id.recycler_detail_production);
        mDetailProductionRecyclerAdapter = new DetailProductionRecyclerAdapter();
        mPlayYoutubeButton = findViewById(R.id.image_ic_detail_play);
        mFloatingFavoriteButton = findViewById(R.id.float_favorite);
        mFloatingFavoriteButton.setOnClickListener(this);
        mProgressBar = findViewById(R.id.progress_indicator);
        mConstraintLayout = findViewById(R.id.constraint_container);
    }

    private void initViewContent() {
        Glide.with(this)
                .load(mMovie.createImageUrl(Constants.MovieApi.DOMAIN_BACKDROP_IMAGE))
                .apply(new RequestOptions().placeholder(R.drawable.movie_detail_poster_sample))
                .into(mImageBackdrop);
        Glide.with(this)
                .load(mMovie.createImageUrl(Constants.MovieApi.DOMAIN_POSTER_IMAGE))
                .apply(new RequestOptions().placeholder(R.drawable.movie_detail_poster_sample))
                .into(mImagePoster);
        mTextTitle.setText(mMovie.getTitle());
        mTextRating.setText(String.valueOf(mMovie.getVoteAverage()));
        mTextReleaseDate.setText(String.valueOf(mMovie.getReleaseDate()));
        mTextOverview.setText(mMovie.getOverview());
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_ic_detail_back:
                onBackPressed();
                break;
            case R.id.text_detail_overview:
                toggleExpandLine();
                break;
            case R.id.image_ic_detail_play:
                playYoutubeVideo((ArrayList<Trailer>) mTrailers, mMovie);
                break;
            case R.id.float_favorite:
                mProgressBar.setVisibility(View.VISIBLE);
                if (mMovie.isFavorite()){
                    mPresenter.removeMovieFromFavorite(mMovie);
                } else {
                    mPresenter.addMovieToFavorite(mMovie);
                }
                break;
            // TODO: add more onClick event here
        }
    }

    private void toggleExpandLine() {
        if (mIsTextOverviewExpanded) {
            mTextOverview.setMaxLines(Constants.DETAIL_SCREEN_OVEVERVIEW_MINLINE);
        } else {
            mTextOverview.setMaxLines(Constants.DETAIL_SCREEN_OVEVERVIEW_MAXLINE);
        }
        mIsTextOverviewExpanded = !mIsTextOverviewExpanded;
    }

    private void playYoutubeVideo(ArrayList<Trailer> trailers, Movie movie) {
        if (trailers == null) {
            Toast.makeText(this, getString(R.string.no_trailers), Toast.LENGTH_SHORT).show();
            return;
        }
        startActivity(YoutubeActivity.getInstance(this, trailers, movie));
    }
}
