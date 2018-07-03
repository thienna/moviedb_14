package com.nganhthien.mikemovie.screen.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.nganhthien.mikemovie.R;

/**
 * Created by ThienNA on 02/07/2018.
 */

public class HomeSliderPagerFragment extends Fragment {
    // the fragment initialization parameters
    private static final String ARGUMENT_PAGE = "ARG_PAGE";
    private static final int IMAGE_SCALE_HEIGHT = 500;
    private static final int IMAGE_SCALE_WIDTH = 300;

    private int mPage;

    public HomeSliderPagerFragment() {
        // Required empty public constructor
    }

    public static HomeSliderPagerFragment newInstance(int page) {
        HomeSliderPagerFragment fragment = new HomeSliderPagerFragment();
        Bundle args = new Bundle();
        args.putInt(ARGUMENT_PAGE, page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() == null) {
            return;
        }

        mPage = getArguments().getInt(ARGUMENT_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_slider_pager_item, container, false);
        ImageView imageView = view.findViewById(R.id.image_slider_item);

        // Resize image
        Glide.with(view)
                .load(R.drawable.slider_image_sample)
                .apply(new RequestOptions().override(IMAGE_SCALE_HEIGHT, IMAGE_SCALE_WIDTH))
                .into(imageView);

        return view;
    }
}
