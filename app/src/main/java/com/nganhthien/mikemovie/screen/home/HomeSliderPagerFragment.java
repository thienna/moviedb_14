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
    private static final int IMAGE_SCALE_HEIGHT = 300;
    private static final int IMAGE_SCALE_WIDTH = 600;
    private static final int IMAGE_BANNER_1 = 1;
    private static final int IMAGE_BANNER_2 = 2;
    private static final int IMAGE_BANNER_3 = 3;
    private static final int IMAGE_BANNER_4 = 4;
    private static final int IMAGE_BANNER_5 = 5;

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
        switch (mPage) {
            case IMAGE_BANNER_1:
                glideResizeImage(view, imageView, R.drawable.slider_image_sample);
                break;
            case IMAGE_BANNER_2:
                glideResizeImage(view, imageView, R.drawable.slider_image_sample2);
                break;
            case IMAGE_BANNER_3:
                glideResizeImage(view, imageView, R.drawable.slider_image_sample3);
                break;
            case IMAGE_BANNER_4:
                glideResizeImage(view, imageView, R.drawable.slider_image_sample4);
                break;
            case IMAGE_BANNER_5:
                glideResizeImage(view, imageView, R.drawable.slider_image_sample5);
                break;
        }

        return view;
    }

    private void glideResizeImage(View view, ImageView imageView, int imageId) {
        Glide.with(view)
                .load(imageId)
                .apply(new RequestOptions().override(IMAGE_SCALE_WIDTH, IMAGE_SCALE_HEIGHT))
                .into(imageView);
    }
}
