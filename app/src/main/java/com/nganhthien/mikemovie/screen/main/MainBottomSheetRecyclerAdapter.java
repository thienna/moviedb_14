package com.nganhthien.mikemovie.screen.main;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nganhthien.mikemovie.R;
import com.nganhthien.mikemovie.data.model.Movie;
import com.nganhthien.mikemovie.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class MainBottomSheetRecyclerAdapter
        extends RecyclerView.Adapter<MainBottomSheetRecyclerAdapter.ViewHolder> {
    private List<Movie> mMovies = new ArrayList<>();
    private LayoutInflater mLayoutInflater;
    private OnRecyclerViewItemClickListener mListener;

    public MainBottomSheetRecyclerAdapter (OnRecyclerViewItemClickListener listener){
        mListener = listener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(parent.getContext());
        }

        View view = mLayoutInflater.inflate(R.layout.item_movies, parent, false);

        ViewHolder viewHolder = new ViewHolder(view, mListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
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

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Movie mMovie;
        private ImageView mImageViewBackdrop;
        private TextView mTextViewMovieTitle;
        private TextView mTextViewMovieRating;
        private OnRecyclerViewItemClickListener mListener;

        public ViewHolder(View itemView, OnRecyclerViewItemClickListener listener) {
            super(itemView);
            mListener = listener;
            mImageViewBackdrop = itemView.findViewById(R.id.image_movies_item);
            mTextViewMovieTitle = itemView.findViewById(R.id.text_movies_title);
            mTextViewMovieRating = itemView.findViewById(R.id.text_movies_item_rating_value);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onClickGenresRecyclerViewItem(mMovie);
        }

        void bindData(Movie item) {
            if (item == null) {
                return;
            }
            mMovie = item;
            Glide.with(itemView.getContext())
                    .load(item.createImageUrl(Constants.MovieApi.DOMAIN_BACKDROP_IMAGE))
                    .into(mImageViewBackdrop);
            mTextViewMovieTitle.setText(item.getTitle());
            mTextViewMovieRating.setText(String.valueOf(item.getVoteAverage()));
        }
    }

    public interface OnRecyclerViewItemClickListener {
        void onClickGenresRecyclerViewItem(Movie movie);
    }
}
