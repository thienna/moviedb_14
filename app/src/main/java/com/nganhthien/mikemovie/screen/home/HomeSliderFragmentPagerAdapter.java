package com.nganhthien.mikemovie.screen.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.nganhthien.mikemovie.utils.Constants;

/**
 * Created by ThienNA on 02/07/2018.
 */

public class HomeSliderFragmentPagerAdapter extends FragmentPagerAdapter {

    public HomeSliderFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return HomeSliderPagerFragment.newInstance(++position);
    }

    @Override
    public int getCount() {
        return Constants.HOME_SLIDER_TOTAL_PAGE;
    }
}
