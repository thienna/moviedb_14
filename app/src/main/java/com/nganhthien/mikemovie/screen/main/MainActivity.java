package com.nganhthien.mikemovie.screen.main;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nganhthien.mikemovie.R;
import com.nganhthien.mikemovie.data.model.Genre;
import com.nganhthien.mikemovie.data.model.Movie;
import com.nganhthien.mikemovie.data.repository.MovieRepository;
import com.nganhthien.mikemovie.screen.BaseActivity;
import com.nganhthien.mikemovie.screen.detail.DetailActivity;
import com.nganhthien.mikemovie.screen.favorite.FavoriteFragment;
import com.nganhthien.mikemovie.screen.home.HomeFragment;
import com.nganhthien.mikemovie.screen.home.HomeMoviesFragment;
import com.nganhthien.mikemovie.screen.more.MoreFragment;
import com.nganhthien.mikemovie.screen.search.SearchFragment;
import com.nganhthien.mikemovie.utils.NetworkReceiver;

import java.util.List;

import static com.nganhthien.mikemovie.data.model.MovieType.MOST_POPULAR;
import static com.nganhthien.mikemovie.data.model.MovieType.NOW_PLAYING;
import static com.nganhthien.mikemovie.data.model.MovieType.TOP_RATED;
import static com.nganhthien.mikemovie.data.model.MovieType.UPCOMING;

public class MainActivity extends BaseActivity implements MainContract.View,
        SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener,
        HomeMoviesFragment.OnMoreMovieClickListener, HomeFragment.OnClickSearchMoviesByGenre,
        MainBottomSheetRecyclerAdapter.OnRecyclerViewItemClickListener,
        SearchFragment.OnAddRemoveFavoriteSuccess, FavoriteFragment.OnAddRemoveFavoriteSuccess {

    private final float BOTTOM_SHEET_SLIDE_OFFSET = 0.1f;
    private MainContract.Presenter mPresenter;
    private Fragment mFragment;
    private HomeFragment mHomeFragment;
    private FavoriteFragment mFavoriteFragment;
    private MoreFragment mMoreFragment;
    private SearchFragment mSearchFragment;
    private FragmentManager mFragmentManager;
    private BottomNavigationView mBottomNavigationView;
    private ConstraintLayout mBottomSheet;
    private BottomSheetBehavior mBottomSheetBehavior;
    private ImageButton mCloseBottomSheetButton;
    private TextView mBottomSheetPeekTitle;
    private RecyclerView mBottomSheetRecycler;
    private MainBottomSheetRecyclerAdapter mBottomSheetRecyclerAdapter;
    private SearchView mSearchView;
    private MenuItem mSearchMenu;
    private ProgressBar mProgressBar;
    private List<Integer> mIds;
    private boolean mIsNeedReloaded;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    hideShowFragment(mFragment, mHomeFragment);
                    mFragment = mHomeFragment;
                    return true;
                // Have not yet others frag
                case R.id.navigation_favorite:
                    mFavoriteFragment.updateIdsFromActivity();
                    hideShowFragment(mFragment, mFavoriteFragment);
                    mFragment = mFavoriteFragment;
                    return true;
                case R.id.navigation_more:
                    hideShowFragment(mFragment, mMoreFragment);
                    mFragment = mMoreFragment;
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initView();
        createFragments();
        mPresenter = new MainPresenter(MovieRepository.getInstance(this));
        mPresenter.setView(this);
        mBottomSheetRecycler.setAdapter(mBottomSheetRecyclerAdapter);
        mPresenter.loadFavoriteMoviesIds();

        initNetworkBroadcast();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu_search, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        mSearchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        mSearchView.setIconifiedByDefault(true);
        mSearchView.setOnQueryTextListener(this);
        mSearchMenu = menu.findItem(R.id.action_search);
        mSearchMenu.setOnActionExpandListener(this);
        return true;
    }

    @Override
    public void searchMoviesByGenre(Genre genre) {
        mSearchMenu.expandActionView();
        mSearchView.setQuery(genre.getName(), false);
        mPresenter.loadFavoriteMoviesIds();
        mPresenter.loadMoviesByGenre(genre.getId());
        mSearchView.clearFocus();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        mSearchView.setQuery(query, false);
        mSearchFragment.searchForResult(query);
        mSearchView.clearFocus();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        mBottomNavigationView.setVisibility(View.GONE);
        mFragmentManager.beginTransaction()
                .add(R.id.frame_container, mSearchFragment)
                .hide(mFragment)
                .addToBackStack(null)
                .commit();
        return true;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        mBottomNavigationView.setVisibility(View.VISIBLE);
        mFragmentManager.popBackStack();
        return true;
    }

    @Override
    public void expandingBottomSheet(String type) {
        mProgressBar.setVisibility(View.VISIBLE);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        switch (type) {
            case MOST_POPULAR:
                mBottomSheetPeekTitle.setText(getResources().getString(R.string.most_popular));
                break;
            case NOW_PLAYING:
                mBottomSheetPeekTitle.setText(getResources().getString(R.string.now_playing));
                break;
            case TOP_RATED:
                mBottomSheetPeekTitle.setText(getResources().getString(R.string.top_rate));
                break;
            case UPCOMING:
                mBottomSheetPeekTitle.setText(getResources().getString(R.string.upcoming));
                break;
            default:
                mBottomSheetPeekTitle.setText(getResources().getString(R.string.app_name));
        }
        mPresenter.loadMoviesByType(type);
    }

    @Override
    public void showLoadMoviesByGenreSuccess(List<Movie> movies) {
        if (mIds != null && !mIds.isEmpty()) {
            for (int i : mIds) {
                for (Movie item : movies) {
                    if (i == item.getId()) {
                        item.setFavorite(true);
                    }
                }
            }
        }
        mSearchFragment.showMoviesByGenre(movies);
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showLoadMoviesByGenreFailed(Exception e) {
    }

    @Override
    public void showLoadMoviesByTypeSuccess(List<Movie> movies) {
        mBottomSheetRecyclerAdapter.setData(movies);
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showLoadMoviesByTypeFailed(Exception e) {
    }

    @Override
    public void onBackPressed() {
        if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onClickGenresRecyclerViewItem(Movie movie) {
        startActivity(DetailActivity.getInstance(this, movie));
    }

    @Override
    public void showLoadFavoriteIdsSuccess(List<Integer> result) {
        mIds = result;
    }

    @Override
    public void showLoadFavoriteIdsFailed() {

    }

    @Override
    public void updateIds() {
        mFavoriteFragment.updateIdsFromActivity();
    }

    @Override
    public void updateIdsFromFavorite() {
        mFavoriteFragment.updateIdsFromActivity();
    }

    private void initView() {
        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation_bottom);
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mFragmentManager = getSupportFragmentManager();
        mBottomSheet = findViewById(R.id.bottom_sheet);
        mBottomSheetBehavior = BottomSheetBehavior.from(mBottomSheet);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        mCloseBottomSheetButton = findViewById(R.id.bottom_sheet_close_button);
        mBottomSheetPeekTitle = findViewById(R.id.bottom_sheet_peek_title);
        mBottomSheetRecycler = findViewById(R.id.recycler_movies);
        mProgressBar = findViewById(R.id.progress_indicator);
        mBottomSheetRecyclerAdapter = new MainBottomSheetRecyclerAdapter(this);
        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                if (slideOffset >= BOTTOM_SHEET_SLIDE_OFFSET) {
                    mBottomNavigationView.setVisibility(View.GONE);
                } else {
                    mBottomNavigationView.setVisibility(View.VISIBLE);
                }
            }
        });
        mCloseBottomSheetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
    }

    private void hideShowFragment(Fragment hide, Fragment show) {
        mFragmentManager.beginTransaction().hide(hide).show(show).commit();
    }

    private void createFragments() {
        mSearchFragment = SearchFragment.newInstance();
        mHomeFragment = HomeFragment.newInstance();
        mFavoriteFragment = FavoriteFragment.newInstance();
        mMoreFragment = MoreFragment.newInstance();
        addHideFragment(mFavoriteFragment);
        addHideFragment(mMoreFragment);
        mFragmentManager.beginTransaction().add(R.id.frame_container, mHomeFragment).commit();
        mFragment = mHomeFragment;
    }

    private void addHideFragment(Fragment fragment) {
        mFragmentManager.beginTransaction().add(R.id.frame_container, fragment).hide(fragment).commit();
    }

    private void initNetworkBroadcast() {
        initNetworkBroadcastReceiver(new NetworkReceiver.NetworkStateListener() {
            @Override
            public void onNetworkConnected() {
            }

            @Override
            public void onNetworkDisconnected() {
                mProgressBar.setVisibility(View.GONE);
                showDialogNoInternet();
            }
        });
    }
}
