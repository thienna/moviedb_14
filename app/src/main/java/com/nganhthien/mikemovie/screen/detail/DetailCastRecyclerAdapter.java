package com.nganhthien.mikemovie.screen.detail;

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
import com.nganhthien.mikemovie.data.model.Cast;

import java.util.List;

public class DetailCastRecyclerAdapter extends RecyclerView.Adapter<DetailCastRecyclerAdapter.ViewHolder> {

    private OnRecyclerViewItemClickListener mListener;
    private List<Cast> mCasts;
    private LayoutInflater mLayoutInflater;

    public DetailCastRecyclerAdapter(OnRecyclerViewItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(parent.getContext());
        }
        View castView = mLayoutInflater.inflate(R.layout.item_cast, parent, false);
        ViewHolder vh = new ViewHolder(castView, mListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (mCasts == null || mCasts.isEmpty()) {
            return;
        }
        holder.bindData(mCasts.get(position));
    }

    @Override
    public int getItemCount() {
        return mCasts == null ? 0 : mCasts.size();
    }

    public void setData(List<Cast> casts) {
        if (casts == null) {
            return;
        }
        mCasts = casts;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private Cast mItem;
        private TextView mCastRealName;
        private TextView mCastName;
        private ImageView mCastProfileImage;
        private View mItemView;
        private OnRecyclerViewItemClickListener mListener;

        public ViewHolder(View itemView, OnRecyclerViewItemClickListener listener) {
            super(itemView);
            mListener = listener;
            mItemView = itemView;
            mCastName = itemView.findViewById(R.id.text_cast_name);
            mCastRealName = itemView.findViewById(R.id.text_cast_real_name);
            mCastProfileImage = itemView.findViewById(R.id.image_cast);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onClickGenresRecyclerViewItem(mItem.getId());
        }

        void bindData(Cast item) {
            if (item == null) {
                return;
            }
            mCastName.setText(item.getName());
            mCastRealName.setText(item.getCharacter());
            Glide.with(mItemView)
                    .load(item.createImageUrl())
                    .apply(new RequestOptions().placeholder(R.drawable.movie_detail_poster_sample))
                    .into(mCastProfileImage);
            mItem = item;
        }
    }

    public interface OnRecyclerViewItemClickListener {
        void onClickGenresRecyclerViewItem(int id);
    }
}
