package com.nganhthien.mikemovie.screen.home;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nganhthien.mikemovie.R;
import com.nganhthien.mikemovie.data.model.Movie;
import com.nganhthien.mikemovie.data.repository.MovieRepository;
import com.nganhthien.mikemovie.screen.detail.DetailActivity;
import com.nganhthien.mikemovie.utils.Constants;

import java.util.List;

import static com.nganhthien.mikemovie.data.model.MovieType.MOST_POPULAR;
import static com.nganhthien.mikemovie.data.model.MovieType.NOW_PLAYING;
import static com.nganhthien.mikemovie.data.model.MovieType.TOP_RATED;
import static com.nganhthien.mikemovie.data.model.MovieType.UPCOMING;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeMoviesFragment extends Fragment
        implements HomeMoviesContract.View, View.OnClickListener {

    private static final String ARGUMENT_PAGE = "ARGUMENT_PAGE";
    private static final int MOVIE_MAIN_INDEX = 0;
    private static final int MOVIE_SUB1_INDEX = 1;
    private static final int MOVIE_SUB2_INDEX = 2;
    private static final int MOVIE_SUB3_INDEX = 3;
    private HomeMoviesContract.Presenter mPresenter;
    private TextView mTextMoviesTypeHead;
    private TextView mTextMainItemTitle;
    private TextView mTextMainItemDescription;
    private TextView mTextSubItem1Title;
    private TextView mTextSubItem2Title;
    private TextView mTextSubItem3Title;
    private ImageView mImageMainItemBackdrop;
    private ImageView mImageMainItemPoster;
    private ImageView mImageSubItem1Poster;
    private ImageView mImageSubItem2Poster;
    private ImageView mImageSubItem3Poster;
    private View mViewWrapperMainItem;
    private View mViewWrapperSubItem1;
    private View mViewWrapperSubItem2;
    private View mViewWrapperSubItem3;
    private List<Movie> mMovies;
    private String mType;
    private TextView mTextMore;
    private OnMoreMovieClickListener mOnMoreMovieClickListener;

    public HomeMoviesFragment() {
        // Required empty public constructor
    }

    public static HomeMoviesFragment newInstance(String type) {
        HomeMoviesFragment fragment = new HomeMoviesFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT_PAGE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mOnMoreMovieClickListener = (OnMoreMovieClickListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        mType = getArguments().getString(ARGUMENT_PAGE);
        View view = inflater.inflate(R.layout.fragment_movies, container, false);
        initView(view);
        mPresenter = new HomeMoviesPresenter(MovieRepository.getInstance());
        mPresenter.setView(this);

        switch (mType) {
            case MOST_POPULAR:
                mTextMoviesTypeHead.setText(getResources().getString(R.string.most_popular));
                break;
            case NOW_PLAYING:
                mTextMoviesTypeHead.setText(getResources().getString(R.string.now_playing));
                break;
            case TOP_RATED:
                mTextMoviesTypeHead.setText(getResources().getString(R.string.top_rate));
                break;
            case UPCOMING:
                mTextMoviesTypeHead.setText(getResources().getString(R.string.upcoming));
                break;
            default:
                mTextMoviesTypeHead.setText(getResources().getString(R.string.app_name));
        }
        mPresenter.loadMovies(mType);
        return view;
    }

    private void initView(View view) {
        mTextMoviesTypeHead = view.findViewById(R.id.text_head_movie_special_list);

        mTextMainItemTitle = view.findViewById(R.id.text_title_movie_special_list);
        mTextMainItemDescription = view.findViewById(R.id.text_description_movie_special_list);
        mTextSubItem1Title = view.findViewById(R.id.text_sub_1_movie_special_list);
        mTextSubItem2Title = view.findViewById(R.id.text_sub_2_movie_special_list);
        mTextSubItem3Title = view.findViewById(R.id.text_sub_3_movie_special_list);

        mImageMainItemBackdrop = view.findViewById(R.id.image_view_movie_special_list);
        mImageMainItemPoster = view.findViewById(R.id.image_view_small_movie_special_list);
        mImageSubItem1Poster = view.findViewById(R.id.image_view_sub_1_movie_special_list);
        mImageSubItem2Poster = view.findViewById(R.id.image_view_sub_2_movie_special_list);
        mImageSubItem3Poster = view.findViewById(R.id.image_view_sub_3_movie_special_list);

        mViewWrapperMainItem = view.findViewById(R.id.view_main_item_wrapper);
        mViewWrapperSubItem1 = view.findViewById(R.id.view_sub_item1_wrapper);
        mViewWrapperSubItem2 = view.findViewById(R.id.view_sub_item2_wrapper);
        mViewWrapperSubItem3 = view.findViewById(R.id.view_sub_item3_wrapper);

        mTextMore = view.findViewById(R.id.text_more_movie_special_list);
    }

    @Override
    public void showMoviesSuccess(List<Movie> movies) {
        mMovies = movies;
        mTextMainItemTitle.setText(mMovies.get(MOVIE_MAIN_INDEX).getTitle());
        mTextMainItemDescription.setText(movies.get(MOVIE_MAIN_INDEX).getOverview());
        mTextSubItem1Title.setText(mMovies.get(MOVIE_SUB1_INDEX).getTitle());
        mTextSubItem2Title.setText(mMovies.get(MOVIE_SUB2_INDEX).getTitle());
        mTextSubItem3Title.setText(mMovies.get(MOVIE_SUB3_INDEX).getTitle());

        mViewWrapperMainItem.setOnClickListener(this);
        mViewWrapperSubItem1.setOnClickListener(this);
        mViewWrapperSubItem2.setOnClickListener(this);
        mViewWrapperSubItem3.setOnClickListener(this);

        mTextMore.setOnClickListener(this);

        glideForImage(MOVIE_MAIN_INDEX,
                Constants.MovieApi.DOMAIN_BACKDROP_IMAGE, mImageMainItemBackdrop);
        glideForImage(MOVIE_MAIN_INDEX,
                Constants.MovieApi.DOMAIN_POSTER_IMAGE, mImageMainItemPoster);
        glideForImage(MOVIE_SUB1_INDEX,
                Constants.MovieApi.DOMAIN_POSTER_IMAGE, mImageSubItem1Poster);
        glideForImage(MOVIE_SUB2_INDEX,
                Constants.MovieApi.DOMAIN_POSTER_IMAGE, mImageSubItem2Poster);
        glideForImage(MOVIE_SUB3_INDEX,
                Constants.MovieApi.DOMAIN_POSTER_IMAGE, mImageSubItem3Poster);
    }

    @Override
    public void showMoviesFailed(Exception e) {
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.view_main_item_wrapper:
                startDetailActivity(MOVIE_MAIN_INDEX);
                break;
            case R.id.view_sub_item1_wrapper:
                startDetailActivity(MOVIE_SUB1_INDEX);
                break;
            case R.id.view_sub_item2_wrapper:
                startDetailActivity(MOVIE_SUB2_INDEX);
                break;
            case R.id.view_sub_item3_wrapper:
                startDetailActivity(MOVIE_SUB3_INDEX);
                break;
            case R.id.text_more_movie_special_list:
                mOnMoreMovieClickListener.expandingBottomSheet(mType);
                break;
        }
    }

    private void startDetailActivity(int movieIndex) {
        startActivity(DetailActivity.getInstance(getContext(), mMovies.get(movieIndex)));
    }

    private void glideForImage(int movieIndex, String type, ImageView image) {
        Glide.with(getContext()).load(mMovies.get(movieIndex).createImageUrl(type)).into(image);
    }

    public interface OnMoreMovieClickListener {
        void expandingBottomSheet(String type);
    }
}
