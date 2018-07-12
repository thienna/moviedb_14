package com.nganhthien.mikemovie.screen.favorite;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nganhthien.mikemovie.R;
import com.nganhthien.mikemovie.data.model.Movie;
import com.nganhthien.mikemovie.data.repository.MovieRepository;
import com.nganhthien.mikemovie.screen.detail.DetailActivity;

import java.util.List;

public class FavoriteFragment extends Fragment implements FavoriteContract.View,
        FavoriteMoviesRecyclerAdapter.OnRecyclerViewItemClickListener {
    private FavoriteContract.Presenter mPresenter;
    private RecyclerView mRecyclerMovies;
    private FavoriteMoviesRecyclerAdapter mRecyclerMoviesAdapter;
    private List<Integer> mIds;
    private TextView mTextEmpty;
    private ProgressBar mProgressBar;
    private RelativeLayout mRelativeLayout;
    private Movie mMovie;
    private OnAddRemoveFavoriteSuccess mOnAddRemoveFavoriteSuccess;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    public static FavoriteFragment newInstance() {
        FavoriteFragment fragment = new FavoriteFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mOnAddRemoveFavoriteSuccess = (OnAddRemoveFavoriteSuccess) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        initView(view);
        mPresenter = new FavoritePresenter(MovieRepository.getInstance(getContext()));
        mPresenter.setView(this);
        mPresenter.loadFavoriteMoviesIds();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.loadFavoriteMoviesIds();
    }

    @Override
    public void showLoadFavoriteMoviesLocalSuccess(List<Movie> movies) {
        if (mIds != null && !mIds.isEmpty()) {
            for (int i : mIds) {
                for (Movie item : movies) {
                    if (i == item.getId()) {
                        item.setFavorite(true);
                    }
                }
            }
        }
        mRecyclerMoviesAdapter.setData(movies);
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showLoadFavoriteMoviesLocalFailed() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onClickFavoriteItem(Movie movie) {
        getContext().startActivity(DetailActivity.getInstance(getContext(), movie));
    }

    @Override
    public void onClickFavoriteFloatButton(Movie movie) {
        mProgressBar.setVisibility(View.VISIBLE);
        if (movie.isFavorite()) {
            mPresenter.removeMovieFromFavorite(movie);
        } else {
            mPresenter.addMovieToFavorite(movie);
        }
    }

    @Override
    public void showLoadFavoriteIdsSuccess(List<Integer> ids) {
        mTextEmpty.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
        mIds = ids;
        mPresenter.loadFavoriteMoviesLocal();
    }

    @Override
    public void showLoadFavoriteIdsFailed() {
        mProgressBar.setVisibility(View.GONE);
        mTextEmpty.setVisibility(View.VISIBLE);
    }

    @Override
    public void showAddFavoriteSuccess(Movie movie) {
        mOnAddRemoveFavoriteSuccess.updateIdsFromFavorite();
        mMovie = movie;
        mMovie.setFavorite(true);
        mRecyclerMoviesAdapter.updateIndex(mMovie);
        mProgressBar.setVisibility(View.GONE);
        Snackbar.make(mRelativeLayout, R.string.add_favorite_success, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showAddFavoriteFailed() {
        mProgressBar.setVisibility(View.GONE);
        Snackbar.make(mRelativeLayout, R.string.add_favorite_failed, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showDeleteFavoriteSuccess(Movie movie) {
        mOnAddRemoveFavoriteSuccess.updateIdsFromFavorite();
        mMovie = movie;
        mMovie.setFavorite(false);
        mRecyclerMoviesAdapter.updateIndex(mMovie);
        mProgressBar.setVisibility(View.GONE);
        Snackbar.make(mRelativeLayout, R.string.remove_favorite_success, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showDeleteFavoriteFailed() {
        mProgressBar.setVisibility(View.GONE);
        Snackbar.make(mRelativeLayout, R.string.remove_favorite_failed, Snackbar.LENGTH_LONG).show();
    }

    public void updateIdsFromActivity() {
        mPresenter.loadFavoriteMoviesIds();
    }

    private void initView(View view) {
        mRecyclerMovies = view.findViewById(R.id.recycler_favorite);
        mRecyclerMoviesAdapter = new FavoriteMoviesRecyclerAdapter(this);
        mRecyclerMovies.setAdapter(mRecyclerMoviesAdapter);
        mTextEmpty = view.findViewById(R.id.text_no_items);
        mTextEmpty.setVisibility(View.GONE);
        mProgressBar = view.findViewById(R.id.progress_indicator);
        mRelativeLayout = view.findViewById(R.id.relative_favorite_container);
    }

    public interface OnAddRemoveFavoriteSuccess {
        void updateIdsFromFavorite();
    }
}
