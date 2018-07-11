package com.nganhthien.mikemovie.screen.youtube;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.nganhthien.mikemovie.BuildConfig;
import com.nganhthien.mikemovie.R;
import com.nganhthien.mikemovie.data.model.Movie;
import com.nganhthien.mikemovie.data.model.Trailer;

import java.util.ArrayList;
import java.util.List;

public class YoutubeActivity extends YouTubeBaseActivity
        implements YouTubePlayer.OnInitializedListener, View.OnClickListener,
        YoutubeTrailerRecyclerAdapter.OnRecyclerViewItemClickListener {

    private static final String EXTRA_TRAILER = "EXTRA_TRAILER";
    private static final String EXTRA_MOVIE = "EXTRA_MOVIE";
    private static final int RECOVERY_REQUEST = 1;
    private YouTubePlayerView mYouTubePlayerView;
    private YouTubePlayer mYouTubePlayer;
    private PlayerStateChangeListener mPlayerStateChangeListener;
    private ImageView mIconBack;
    private List<Trailer> mTrailers;
    private Movie mMovie;
    private List<String> mVideos = new ArrayList<>();
    private TextView mMovieTitle;
    private TextView mMovieRating;
    private TextView mMovieReleaseDate;
    private RecyclerView mRecyclerTrailers;
    private YoutubeTrailerRecyclerAdapter mRecyclerTrailersAdapter;

    public static Intent getInstance(Context context, ArrayList<Trailer> trailers, Movie movie) {
        Intent intent = new Intent(context, YoutubeActivity.class);
        intent.putExtra(EXTRA_MOVIE, movie);
        intent.putParcelableArrayListExtra(EXTRA_TRAILER, trailers);
        return intent;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);
        initView();
        initViewContent();
    }

    @Override
    public void onDestroy() {
        if (mYouTubePlayerView != null) {
            mYouTubePlayerView = null;
        }
        super.onDestroy();
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                        YouTubePlayer youTubePlayer, boolean wasRestored) {
        mYouTubePlayer = youTubePlayer;
        mYouTubePlayer.setPlayerStateChangeListener(mPlayerStateChangeListener);
        if (wasRestored) {
            return;
        }
        mYouTubePlayer.loadVideos(mVideos, 0, 0);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        YouTubeInitializationResult youTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else {
            String error = String
                    .format(getString(R.string.player_error), youTubeInitializationResult.toString());
            showMessage(error);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(BuildConfig.API_KEY_YOUTUBE, this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_ic_youtube_back:
                onBackPressed();
                break;
        }
    }

    @Override
    public void trailerItemClick(Trailer trailer, int position) {
        mYouTubePlayer.loadVideos(mVideos, position, 0);
        mRecyclerTrailersAdapter.setPosition(position);
    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return mYouTubePlayerView;
    }

    private void initView() {
        mYouTubePlayerView = findViewById(R.id.youtube_player);
        mPlayerStateChangeListener = new PlayerStateChangeListener();
        mIconBack = findViewById(R.id.image_ic_youtube_back);
        mIconBack.setOnClickListener(this);
        mMovieTitle = findViewById(R.id.text_youtube_title);
        mMovieReleaseDate = findViewById(R.id.text_youtube_release_value);
        mMovieRating = findViewById(R.id.text_youtube_rating_value);
        mRecyclerTrailers = findViewById(R.id.recycler_youtube);
    }

    private void initViewContent() {
        mYouTubePlayerView.initialize(BuildConfig.API_KEY_YOUTUBE, this);
        mTrailers = getIntent().getParcelableArrayListExtra(EXTRA_TRAILER);
        mMovie = getIntent().getParcelableExtra(EXTRA_MOVIE);

        mMovieTitle.setText(mMovie.getTitle());
        mMovieRating.setText(String.valueOf(mMovie.getVoteAverage()));
        mMovieReleaseDate.setText(mMovie.getReleaseDate());
        for (Trailer trailer : mTrailers) {
            mVideos.add(trailer.getKey());
        }
        mRecyclerTrailersAdapter = new YoutubeTrailerRecyclerAdapter(this);
        mRecyclerTrailersAdapter.setData(mTrailers);
        mRecyclerTrailers.setAdapter(mRecyclerTrailersAdapter);
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private final class PlayerStateChangeListener implements YouTubePlayer.PlayerStateChangeListener {
        @Override
        public void onLoading() {
        }

        @Override
        public void onLoaded(String s) {
            mYouTubePlayer.play();
        }

        @Override
        public void onAdStarted() {
        }

        @Override
        public void onVideoStarted() {
        }

        @Override
        public void onVideoEnded() {
        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {
            Toast.makeText(YoutubeActivity.this, errorReason.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
