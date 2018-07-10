package com.nganhthien.mikemovie.screen.favorite;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nganhthien.mikemovie.R;

public class FavoriteFragment extends Fragment implements FavoriteContract.View {
    public FavoriteFragment() {
        // Required empty public constructor
    }

    public static FavoriteFragment newInstance() {
        FavoriteFragment fragment = new FavoriteFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {

    }
}
