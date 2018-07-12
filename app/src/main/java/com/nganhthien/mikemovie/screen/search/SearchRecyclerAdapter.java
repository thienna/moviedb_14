package com.nganhthien.mikemovie.screen.search;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.nganhthien.mikemovie.R;
import com.nganhthien.mikemovie.data.model.Movie;
import com.nganhthien.mikemovie.data.model.Person;
import com.nganhthien.mikemovie.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class SearchRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_LOADING = 0;
    private static final int VIEW_TYPE_MOVIE = 1;
    private static final int VIEW_TYPE_PERSON = 2;
    private List<Object> mDataList = new ArrayList<>();
    private LayoutInflater mLayoutInflater;
    private OnRecyclerViewItemClickListener mListener;

    public SearchRecyclerAdapter(OnRecyclerViewItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(parent.getContext());
        }

        View viewMovie = mLayoutInflater.inflate(R.layout.item_search, parent, false);
        View viewPerson = mLayoutInflater.inflate(R.layout.item_search2, parent, false);

        switch (viewType) {
            case VIEW_TYPE_MOVIE:
                return new ViewHolderMovie(viewMovie, mListener);
            case VIEW_TYPE_PERSON:
                return new ViewHolderPerson(viewPerson, mListener);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (mDataList == null || mDataList.isEmpty()) {
            return;
        }
        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MOVIE:
                ViewHolderMovie viewHolderMovie = (ViewHolderMovie) holder;
                viewHolderMovie.bindData(mDataList.get(position));
                break;
            case VIEW_TYPE_PERSON:
                ViewHolderPerson viewHolderPerson = (ViewHolderPerson) holder;
                viewHolderPerson.bindData(mDataList.get(position));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mDataList.get(position) != null) {
            if (mDataList.get(position) instanceof Movie){
                return VIEW_TYPE_MOVIE;
            } else if (mDataList.get(position) instanceof Person){
                return VIEW_TYPE_PERSON;
            }
        }
        return VIEW_TYPE_LOADING;
    }

    public void setDataMoviesOnly(List<Movie> movies) {
        if (movies == null) {
            return;
        }
        mDataList.clear();
        mDataList.addAll(movies);
        notifyDataSetChanged();
    }

    public void setData(List<Object> dataList) {
        if (dataList == null) {
            return;
        }
        mDataList.clear();
        mDataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void updateIndex(Movie movie) {
        if (movie == null){
            return;
        }
        int indexChanged = mDataList.indexOf(movie);
        if (indexChanged > -1){
            notifyItemChanged(indexChanged, movie);
        }
    }

    static class ViewHolderMovie extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Movie mMovie;
        private ImageView mImageViewPoster;
        private TextView mTextViewMovieTitle;
        private TextView mTextViewMovieOverview;
        private TextView mTextViewMovieRating;
        private ImageView mImageFloatFavorite;
        private OnRecyclerViewItemClickListener mListener;

        public ViewHolderMovie(View itemView, OnRecyclerViewItemClickListener listener) {
            super(itemView);
            mListener = listener;
            mImageViewPoster = itemView.findViewById(R.id.image_search_item);
            mTextViewMovieTitle = itemView.findViewById(R.id.text_search_item_title);
            mTextViewMovieOverview = itemView.findViewById(R.id.text_search_item_overview);
            mTextViewMovieRating = itemView.findViewById(R.id.text_search_item_rating_value);
            mImageFloatFavorite = itemView.findViewById(R.id.image_float_favorite);
            itemView.setOnClickListener(this);
            mImageFloatFavorite.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.image_float_favorite) {
                mListener.onClickFavoriteFloatButton(mMovie);
            } else {
                mListener.onMovieItemClick(mMovie);
            }
        }

        void bindData(Object item) {
            if (item == null) {
                return;
            }
            try {
                mMovie = (Movie) item;
            } catch (ClassCastException e) {
                e.printStackTrace();
            }

            Glide.with(itemView.getContext())
                    .load(mMovie.createImageUrl(Constants.MovieApi.DOMAIN_POSTER_IMAGE))
                    .apply(new RequestOptions().placeholder(R.drawable.movie_detail_poster_sample))
                    .into(mImageViewPoster);
            mTextViewMovieTitle.setText(mMovie.getTitle());
            mTextViewMovieOverview.setText(mMovie.getOverview());
            mTextViewMovieRating.setText(String.valueOf(mMovie.getVoteAverage()));
            if (mMovie.isFavorite()) {
                mImageFloatFavorite.setImageResource(R.drawable.ic_favorite_main);
            } else {
                mImageFloatFavorite.setImageResource(R.drawable.ic_favorite_white_40dp);
            }
        }
    }

    static class ViewHolderPerson extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Person mPerson;
        private ImageView mImageViewPerson;
        private TextView mTextViewPersonName;
        private TextView mTextViewPersonPopularity;
        private OnRecyclerViewItemClickListener mListener;

        public ViewHolderPerson(View itemView, OnRecyclerViewItemClickListener listener) {
            super(itemView);
            mListener = listener;
            mImageViewPerson = itemView.findViewById(R.id.image_search_item);
            mTextViewPersonName = itemView.findViewById(R.id.text_search_item_title);
            mTextViewPersonPopularity = itemView.findViewById(R.id.text_search_item_rating_value);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onPersonItemClick(mPerson);
        }

        void bindData(Object item) {
            if (item == null) {
                return;
            }
            try {
                mPerson = (Person) item;
            } catch (ClassCastException e) {
                e.printStackTrace();
            }
            Glide.with(itemView.getContext())
                    .load(mPerson.createImageUrl())
                    .apply(new RequestOptions().placeholder(R.drawable.movie_detail_poster_sample))
                    .into(mImageViewPerson);
            mTextViewPersonName.setText(mPerson.getName());
            mTextViewPersonPopularity.setText(mPerson.getPopularity());
        }
    }

    public interface OnRecyclerViewItemClickListener {
        void onPersonItemClick(Person person);

        void onMovieItemClick(Movie movie);

        void onClickFavoriteFloatButton(Movie movie);
    }
}
