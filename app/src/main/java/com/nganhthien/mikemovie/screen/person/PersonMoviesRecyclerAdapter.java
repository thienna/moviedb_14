package com.nganhthien.mikemovie.screen.person;

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
import com.nganhthien.mikemovie.screen.detail.DetailActivity;
import com.nganhthien.mikemovie.utils.Constants;

import java.util.List;

public class PersonMoviesRecyclerAdapter
        extends RecyclerView.Adapter<PersonMoviesRecyclerAdapter.ViewHolder>{

    private List<Movie> mMovies;
    private LayoutInflater mLayoutInflater;

    public PersonMoviesRecyclerAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(parent.getContext());
        }

        View genreView = mLayoutInflater.inflate(R.layout.item_person_movies, parent, false);

        ViewHolder viewHolder = new ViewHolder(genreView);
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
        private TextView mTextMovieTitle;
        private ImageView mImageMoviePoster;
        private Movie mItem;

        public ViewHolder(final View itemView) {
            super(itemView);
            mTextMovieTitle = itemView.findViewById(R.id.text_person_movie_name);
            mImageMoviePoster = itemView.findViewById(R.id.image_person_movie);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemView
                    .getContext()
                    .startActivity(DetailActivity.getInstance(itemView.getContext(), mItem));
        }

        void bindData(Movie item) {
            if (item == null) {
                return;
            }
            mItem = item;
            mTextMovieTitle.setText(mItem.getTitle());
            Glide.with(itemView.getContext())
                    .load(mItem.createImageUrl(Constants.MovieApi.DOMAIN_POSTER_IMAGE))
                    .apply(new RequestOptions().placeholder(R.drawable.movie_detail_poster_sample))
                    .into(mImageMoviePoster);
        }
    }
}
