package com.nganhthien.mikemovie.screen.detail;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nganhthien.mikemovie.R;
import com.nganhthien.mikemovie.data.model.Production;

import java.util.List;

public class DetailProductionRecyclerAdapter
        extends RecyclerView.Adapter<DetailProductionRecyclerAdapter.ViewHolder> {

    private List<Production> mDataList;
    private LayoutInflater mLayoutInflater;

    public DetailProductionRecyclerAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view = mLayoutInflater.inflate(R.layout.item_production, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (mDataList == null || mDataList.isEmpty()) {
            return;
        }
        holder.bindData(mDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    public void setData(List<Production> dataList) {
        if (dataList == null) {
            return;
        }
        mDataList = dataList;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mProductionName;
        private ImageView mProductionImage;
        private View mItemView;

        public ViewHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
            mProductionName = itemView.findViewById(R.id.text_production);
            mProductionImage = itemView.findViewById(R.id.image_production);
        }

        void bindData(Production item) {
            if (item == null) {
                return;
            }
            mProductionName.setText(item.getName());
            Glide.with(mItemView)
                    .load(item.createImageUrl())
                    .into(mProductionImage);
        }
    }
}
