package com.nganhthien.mikemovie.screen.home;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nganhthien.mikemovie.R;
import com.nganhthien.mikemovie.data.model.Genre;
import com.nganhthien.mikemovie.data.model.MovieType;
import com.nganhthien.mikemovie.data.repository.GenreRepository;
import com.nganhthien.mikemovie.utils.Constants;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment
        implements HomeContract.View, HomeGenresRecyclerAdapter.OnRecyclerViewItemClickListener {

    private HomeContract.Presenter mPresenter;

    // Setup for Slider
    private ViewPager mSliderPager;
    private HomeSliderFragmentPagerAdapter mSliderPagerAdapter;
    private TabLayout mSliderTab;

    // Setup Handler and Runnable for Slider interval
    private static final int SLIDER_INTERVAL_TIMEOUT = 5500;
    private final Handler mSliderHandler = new Handler();
    private Runnable mSliderInterval;

    // Setup for Genre RecyclerView
    private HomeGenresRecyclerAdapter mHomeGenresRecyclerAdapter;
    private OnClickSearchMoviesByGenre mOnClickSearchMoviesByGenre;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initSlider(view);

        mPresenter = new HomePresenter(GenreRepository.getInstance());
        mPresenter.setView(this);
        mPresenter.loadGenres();

        RecyclerView genreRecyclerView = view.findViewById(R.id.recycler_genres);
        genreRecyclerView
                .setLayoutManager(new LinearLayoutManager(getActivity(),
                        LinearLayoutManager.HORIZONTAL, false));

        mHomeGenresRecyclerAdapter = new HomeGenresRecyclerAdapter(this);
        genreRecyclerView.setAdapter(mHomeGenresRecyclerAdapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction
                .add(R.id.list_top_genre_popular,
                        HomeMoviesFragment.newInstance(MovieType.MOST_POPULAR));
        fragmentTransaction
                .add(R.id.list_top_now_playing,
                        HomeMoviesFragment.newInstance(MovieType.NOW_PLAYING));
        fragmentTransaction
                .add(R.id.list_top_top_rate,
                        HomeMoviesFragment.newInstance(MovieType.TOP_RATED));
        fragmentTransaction
                .add(R.id.list_top_upcoming,
                        HomeMoviesFragment.newInstance(MovieType.UPCOMING));
        fragmentTransaction.commit();
    }

    @Override
    public void onStart() {
        super.onStart();
        startSliderInterval(mSliderPager);
    }

    @Override
    public void onStop() {
        super.onStop();
        stopSliderInterval();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mOnClickSearchMoviesByGenre = (OnClickSearchMoviesByGenre) context;
    }

    @Override
    public void showLoadGenresSuccess(List<Genre> genres) {
        mHomeGenresRecyclerAdapter.setData(genres);
    }

    @Override
    public void showLoadGenresFailed(Exception e) {
    }

    @Override
    public void onClickGenresRecyclerViewItem(Genre item) {
        if (item == null) {
            return;
        }
        mOnClickSearchMoviesByGenre.searchMoviesByGenre(item);
    }

    private void initSlider(View view) {
        mSliderPager = view.findViewById(R.id.viewpager_slider);
        mSliderPagerAdapter =
                new HomeSliderFragmentPagerAdapter(getActivity().getSupportFragmentManager());
        mSliderPager.setAdapter(mSliderPagerAdapter);

        mSliderTab = view.findViewById(R.id.tablayout_slider);
        mSliderTab.setupWithViewPager(mSliderPager, true);
    }

    private void startSliderInterval(final ViewPager pager) {
        mSliderInterval = new Runnable() {
            @Override
            public void run() {
                int count = pager.getCurrentItem();
                if (count == Constants.HOME_SLIDER_TOTAL_PAGE - 1) count = -1;
                pager.setCurrentItem(++count, true);

                mSliderHandler.postDelayed(this, SLIDER_INTERVAL_TIMEOUT);
            }
        };

        mSliderHandler.postDelayed(mSliderInterval, SLIDER_INTERVAL_TIMEOUT);
    }

    private void stopSliderInterval() {
        mSliderHandler.removeCallbacks(mSliderInterval);
    }

    public interface OnClickSearchMoviesByGenre {
        void searchMoviesByGenre(Genre genre);
    }
}
