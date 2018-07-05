package com.nganhthien.mikemovie.screen.home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nganhthien.mikemovie.R;
import com.nganhthien.mikemovie.data.model.Movie;
import com.nganhthien.mikemovie.data.repository.MovieRepository;
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
        implements HomeMoviesContract.View {

    private static final String ARGUMENT_PAGE = "ARGUMENT_PAGE";
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
    private String mType;

    public static HomeMoviesFragment newInstance(String type) {
        HomeMoviesFragment fragment = new HomeMoviesFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT_PAGE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    public HomeMoviesFragment() {
        // Required empty public constructor
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
    }

    @Override
    public void showMoviesSuccess(List<Movie> movies) {
        mTextMainItemTitle.setText(movies.get(0).getTitle());
        mTextMainItemDescription.setText(movies.get(0).getOverview());
        mTextSubItem1Title.setText(movies.get(1).getTitle());
        mTextSubItem2Title.setText(movies.get(2).getTitle());
        mTextSubItem3Title.setText(movies.get(3).getTitle());

        Glide.with(getContext())
                .load(makeImageUrl(movies, 0, Constants.MovieApi.DOMAIN_BACKDROP_IMAGE))
                .into(mImageMainItemBackdrop);
        Glide.with(getContext())
                .load(makeImageUrl(movies, 0, Constants.MovieApi.DOMAIN_POSTER_IMAGE))
                .into(mImageMainItemPoster);
        Glide.with(getContext())
                .load(makeImageUrl(movies, 1, Constants.MovieApi.DOMAIN_POSTER_IMAGE))
                .into(mImageSubItem1Poster);
        Glide.with(getContext())
                .load(makeImageUrl(movies, 2, Constants.MovieApi.DOMAIN_POSTER_IMAGE))
                .into(mImageSubItem2Poster);
        Glide.with(getContext())
                .load(makeImageUrl(movies, 3, Constants.MovieApi.DOMAIN_POSTER_IMAGE))
                .into(mImageSubItem3Poster);
    }

    @Override
    public void showMoviesFailed(Exception e) {
        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
    }

    private String makeImageUrl(List<Movie> movies, int index, String type) {
        StringBuilder imageUrl = new StringBuilder();
        imageUrl.append(Constants.UrlConfig.PROTOCOL);
        String typeAfterFormat;
        if (type.equals(Constants.MovieApi.DOMAIN_BACKDROP_IMAGE)) {
            typeAfterFormat = String.format(type, movies.get(index).getBackdropImage());
        } else {
            typeAfterFormat = String.format(type, movies.get(index).getPosterImage());
        }
        return imageUrl.append(typeAfterFormat).toString();
    }
}
