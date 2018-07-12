package com.nganhthien.mikemovie.screen.search;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.nganhthien.mikemovie.R;
import com.nganhthien.mikemovie.data.model.Movie;
import com.nganhthien.mikemovie.data.model.Person;
import com.nganhthien.mikemovie.data.repository.MovieRepository;
import com.nganhthien.mikemovie.screen.detail.DetailActivity;
import com.nganhthien.mikemovie.screen.person.PersonActivity;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements SearchContract.View,
        SearchRecyclerAdapter.OnRecyclerViewItemClickListener {

    private SearchContract.Presenter mPresenter;
    private RecyclerView mRecyclerView;
    private SearchRecyclerAdapter mSearchRecyclerAdapter;
    private ProgressBar mProgressBar;
    private List<Integer> mIds;
    private Movie mMovie;
    private RelativeLayout mRelativeLayout;
    private OnAddRemoveFavoriteSuccess mOnAddRemoveFavoriteSuccess;

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mOnAddRemoveFavoriteSuccess = (OnAddRemoveFavoriteSuccess) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        initView(view);
        mPresenter = new SearchPresenter(MovieRepository.getInstance(getContext()));
        mPresenter.setView(this);
        mPresenter.loadFavoriteMoviesIds();
        showProgressIndicator();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.setAdapter(mSearchRecyclerAdapter);
    }

    @Override
    public void showSearchResultsSuccess(List<Object> dataList) {
        if (dataList == null || dataList.isEmpty()) {
            showResultEmpty(getContext());
            return;
        }
        if (mIds == null || mIds.isEmpty()) {
            hideProgressIndicator();
            mSearchRecyclerAdapter.setData(dataList);
        } else {
            Movie temp;
            for (int i : mIds) {
                for (Object item : dataList) {
                    if (item instanceof Movie) {
                        temp = (Movie) item;
                        if (i == temp.getId()) {
                            temp.setFavorite(true);
                        }
                    }
                }
            }

            hideProgressIndicator();
            mSearchRecyclerAdapter.setData(dataList);
        }
    }

    @Override
    public void showSearchResultsFailed(Exception e) {
    }

    @Override
    public void onPersonItemClick(Person person) {
        startActivity(PersonActivity.getInstance(getContext(), Integer.parseInt(person.getId())));
    }

    @Override
    public void onMovieItemClick(Movie movie) {
        startActivity(DetailActivity.getInstance(getContext(), movie));
    }

    @Override
    public void showLoadFavoriteIdsSuccess(List<Integer> result) {
        hideProgressIndicator();
        mIds = result;
    }

    @Override
    public void showLoadFavoriteIdsFailed() {
        hideProgressIndicator();
    }

    @Override
    public void showAddFavoriteSuccess(Movie movie) {
        mMovie = movie;
        mOnAddRemoveFavoriteSuccess.updateIds();
        mMovie.setFavorite(true);
        mSearchRecyclerAdapter.updateIndex(mMovie);
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
        mMovie = movie;
        mOnAddRemoveFavoriteSuccess.updateIds();
        mMovie.setFavorite(false);
        mSearchRecyclerAdapter.updateIndex(mMovie);
        mProgressBar.setVisibility(View.GONE);
        Snackbar.make(mRelativeLayout, R.string.remove_favorite_success, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showDeleteFavoriteFailed() {
        mProgressBar.setVisibility(View.GONE);
        Snackbar.make(mRelativeLayout, R.string.remove_favorite_failed, Snackbar.LENGTH_LONG).show();
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

    public void initView(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_search);
        mSearchRecyclerAdapter = new SearchRecyclerAdapter(this);
        mProgressBar = view.findViewById(R.id.progress_indicator);
        mRelativeLayout = view.findViewById(R.id.relative_favorite_container);
    }

    public void showMoviesByGenre(List<Movie> movies) {
        if (mIds != null && !mIds.isEmpty()) {
            for (int i : mIds) {
                for (Movie item : movies) {
                    if (i == item.getId()) {
                        item.setFavorite(true);
                    }
                }
            }
        }
        mSearchRecyclerAdapter.setDataMoviesOnly(movies);
    }

    public void searchForResult(String query) {
        showProgressIndicator();
        mPresenter.loadSearchResults(query);
    }

    public void showProgressIndicator() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressIndicator() {
        mProgressBar.setVisibility(View.GONE);
    }

    public void showResultEmpty(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(getString(R.string.app_name));
        builder.setMessage(getString(R.string.dialog_no_result));
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public interface OnAddRemoveFavoriteSuccess {
        void updateIds();
    }
}
