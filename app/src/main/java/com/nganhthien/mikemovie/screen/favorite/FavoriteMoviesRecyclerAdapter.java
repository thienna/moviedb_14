package com.nganhthien.mikemovie.screen.favorite;

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
import com.nganhthien.mikemovie.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class FavoriteMoviesRecyclerAdapter extends RecyclerView
        .Adapter<FavoriteMoviesRecyclerAdapter.ViewHolder> {
    private List<Movie> mMovies = new ArrayList<>();
    private LayoutInflater mLayoutInflater;
    private FavoriteMoviesRecyclerAdapter.OnRecyclerViewItemClickListener mListener;

    public FavoriteMoviesRecyclerAdapter(FavoriteMoviesRecyclerAdapter.OnRecyclerViewItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public FavoriteMoviesRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(parent.getContext());
        }

        View view = mLayoutInflater.inflate(R.layout.item_favorite, parent, false);

        ViewHolder viewHolder = new ViewHolder(view, mListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteMoviesRecyclerAdapter.ViewHolder holder, int position) {
        if (mMovies == null || mMovies.isEmpty()) {
            return;
        }
        holder.bindData(mMovies.get(position));
    }

    @Override
    public int getItemCount() {
        return mMovies == null ? 0 : mMovies.size();
    }

    public void setData(List<Movie> movies) {
        if (movies == null) {
            return;
        }
        mMovies = movies;
        notifyDataSetChanged();
    }

    public void clearData() {
        if (mMovies == null) {
            return;
        }
        mMovies.clear();
        notifyDataSetChanged();
    }

    public void updateIndex(Movie movie) {
        if (movie == null){
            return;
        }
        int indexChanged = mMovies.indexOf(movie);
        if (indexChanged > -1){
            notifyItemChanged(indexChanged, movie);
        }
    }

    public interface OnRecyclerViewItemClickListener {
        void onClickFavoriteItem(Movie movie);

        void onClickFavoriteFloatButton(Movie movie);
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Movie mMovie;
        private ImageView mImageViewPoster;
        private TextView mTextViewMovieTitle;
        private TextView mTextViewMovieRating;
        private TextView mTextViewMovieOverview;
        private ImageView mFloatFavoriteButton;
        private OnRecyclerViewItemClickListener mListener;

        public ViewHolder(View itemView, OnRecyclerViewItemClickListener listener) {
            super(itemView);
            mListener = listener;
            mImageViewPoster = itemView.findViewById(R.id.image_favorite_item);
            mTextViewMovieTitle = itemView.findViewById(R.id.text_favorite_item_title);
            mTextViewMovieOverview = itemView.findViewById(R.id.text_favorite_item_overview);
            mTextViewMovieRating = itemView.findViewById(R.id.text_favorite_item_rating_value);
            mFloatFavoriteButton = itemView.findViewById(R.id.float_favorite);
            itemView.setOnClickListener(this);
            mFloatFavoriteButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.float_favorite) {
                mListener.onClickFavoriteFloatButton(mMovie);
            } else {
                mListener.onClickFavoriteItem(mMovie);
            }
        }

        void bindData(Movie item) {
            if (item == null) {
                return;
            }
            mMovie = item;
            Glide.with(itemView.getContext())
                    .load(item.createImageUrl(Constants.MovieApi.DOMAIN_POSTER_IMAGE))
                    .apply(new RequestOptions().placeholder(R.drawable.movie_detail_poster_sample))
                    .into(mImageViewPoster);
            mTextViewMovieTitle.setText(item.getTitle());
            mTextViewMovieOverview.setText(item.getOverview());
            mTextViewMovieRating.setText(String.valueOf(item.getVoteAverage()));
            if (mMovie.isFavorite()) {
                mFloatFavoriteButton.setImageResource(R.drawable.ic_favorite_main);
            } else {
                mFloatFavoriteButton.setImageResource(R.drawable.ic_favorite_white_40dp);
            }
        }
    }
}
