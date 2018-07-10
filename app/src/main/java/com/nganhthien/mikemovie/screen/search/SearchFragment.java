package com.nganhthien.mikemovie.screen.search;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.nganhthien.mikemovie.R;
import com.nganhthien.mikemovie.data.model.Movie;
import com.nganhthien.mikemovie.data.model.Person;
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

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        initView(view);
        mPresenter = new SearchPresenter();
        mPresenter.setView(this);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.setAdapter(mSearchRecyclerAdapter);
    }

    @Override
    public void showSearchResultsSuccess(List<Object> dataList) {
        hideProgressIndicator();
        if (dataList == null || dataList.isEmpty()){
            showResultEmpty(getContext());
        }
        mSearchRecyclerAdapter.setData(dataList);
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

    public void initView(View view){
        mRecyclerView = view.findViewById(R.id.recycler_search);
        mSearchRecyclerAdapter = new SearchRecyclerAdapter(this);
        mProgressBar = view.findViewById(R.id.progress_indicator);
    }

    public void showMoviesByGenre(List<Movie> movies){
        mSearchRecyclerAdapter.setDataMoviesOnly(movies);
    }

    public void searchForResult(String query){
        showProgressIndicator();
        mPresenter.loadSearchResults(query);
    }

    public void showProgressIndicator(){
        mProgressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressIndicator(){
        mProgressBar.setVisibility(View.GONE);
    }

    public void showResultEmpty(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(getString(R.string.app_name));
        builder.setMessage(getString(R.string.dialog_no_result));
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
