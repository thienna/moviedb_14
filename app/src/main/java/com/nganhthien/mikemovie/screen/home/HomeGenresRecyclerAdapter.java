package com.nganhthien.mikemovie.screen.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nganhthien.mikemovie.R;
import com.nganhthien.mikemovie.data.model.Genre;

import java.util.List;

/**
 * Created by ThienNA on 03/07/2018.
 */

public class HomeGenresRecyclerAdapter
        extends RecyclerView.Adapter<HomeGenresRecyclerAdapter.ViewHolder> {

    private List<Genre> mGenres;
    private OnRecyclerViewItemClickListener mOnRecyclerViewItemClickListener;
    private LayoutInflater mLayoutInflater;

    public HomeGenresRecyclerAdapter(OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        mOnRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        mLayoutInflater = LayoutInflater.from(context);

        View genreView = mLayoutInflater.inflate(R.layout.item_genre, parent, false);

        ViewHolder viewHolder = new ViewHolder(genreView, mOnRecyclerViewItemClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (mGenres == null || mGenres.isEmpty()) {
            return;
        }

        holder.bindData(mGenres.get(position));
    }

    @Override
    public int getItemCount() {
        return mGenres == null ? 0 : mGenres.size();
    }

    public void setData(List<Genre> genres) {
        if (genres == null) {
            return;
        }

        mGenres = genres;

        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mNameTextView;
        private Genre mItem;

        public ViewHolder(final View itemView, final OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
            super(itemView);

            mNameTextView = itemView.findViewById(R.id.text_genre);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onRecyclerViewItemClickListener == null) {
                        return;
                    }
                    onRecyclerViewItemClickListener.onClickGenresRecyclerViewItem(mItem);
                }
            });
        }

        void bindData(Genre item) {
            if (item == null) {
                return;
            }
            mNameTextView.setText(item.getName());
            mItem = item;
        }
    }

    public interface OnRecyclerViewItemClickListener {
        void onClickGenresRecyclerViewItem(Genre item);
    }
}
