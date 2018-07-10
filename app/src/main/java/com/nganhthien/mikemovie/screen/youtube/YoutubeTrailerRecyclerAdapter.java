package com.nganhthien.mikemovie.screen.youtube;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nganhthien.mikemovie.R;
import com.nganhthien.mikemovie.data.model.Trailer;

import java.util.List;

public class YoutubeTrailerRecyclerAdapter extends RecyclerView
        .Adapter<YoutubeTrailerRecyclerAdapter.ViewHolder> {
    private List<Trailer> mDataList;
    private LayoutInflater mLayoutInflater;
    private OnRecyclerViewItemClickListener mListener;
    private int mPosition;

    public YoutubeTrailerRecyclerAdapter(OnRecyclerViewItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = mLayoutInflater.inflate(R.layout.item_youtube_trailer, parent, false);
        ViewHolder vh = new ViewHolder(view, mListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (mDataList == null || mDataList.isEmpty()) {
            return;
        }
        holder.bindData(mDataList.get(position), mPosition);
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    public void setData(List<Trailer> dataList) {
        if (dataList == null) {
            return;
        }
        mDataList = dataList;
        notifyDataSetChanged();
    }

    public void setPosition(int position) {
        notifyItemChanged(mPosition);
        mPosition = position;
        notifyItemChanged(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTextTrailerName;
        private Trailer mItem;
        private OnRecyclerViewItemClickListener mListener;

        public ViewHolder(View itemView, OnRecyclerViewItemClickListener listener) {
            super(itemView);
            mListener = listener;
            mTextTrailerName = itemView.findViewById(R.id.text_trailer_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.trailerItemClick(mItem, this.getLayoutPosition());
        }

        void bindData(Trailer item, int position) {
            if (item == null) {
                return;
            }
            mItem = item;
            mTextTrailerName.setText(item.getName());
            setColor(position);
        }

        void setColor(int position){
            if (position == getLayoutPosition()) {
                mTextTrailerName.setTextColor(itemView.getResources().getColor(R.color.colorPrimaryDark));
            } else {
                mTextTrailerName.setTextColor(itemView.getResources().getColor(R.color.color_text_sub_title));
            }
        }
    }

    public interface OnRecyclerViewItemClickListener {
        void trailerItemClick(Trailer trailer, int position);
    }
}
