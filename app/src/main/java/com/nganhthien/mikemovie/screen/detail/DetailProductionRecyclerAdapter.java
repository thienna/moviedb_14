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
import com.nganhthien.mikemovie.data.model.Production;
import com.nganhthien.mikemovie.screen.company.CompanyActivity;

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

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mProductionName;
        private ImageView mProductionImage;
        private Production mItem;

        public ViewHolder(View itemView) {
            super(itemView);
            mProductionName = itemView.findViewById(R.id.text_production);
            mProductionImage = itemView.findViewById(R.id.image_production);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemView.getContext()
                    .startActivity(CompanyActivity.getInstance(itemView.getContext(), mItem.getId()));
        }

        void bindData(Production item) {
            if (item == null) {
                return;
            }
            mItem = item;
            mProductionName.setText(item.getName());
            Glide.with(itemView)
                    .load(item.createImageUrl())
                    .apply(new RequestOptions().placeholder(R.drawable.movie_detail_poster_sample))
                    .into(mProductionImage);
        }
    }
}
