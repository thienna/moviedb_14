package com.nganhthien.mikemovie.screen.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.nganhthien.mikemovie.screen.home.HomeFragment;

/**
 * Created by ThienNA on 01/07/2018.
 */

public class MainViewPagerAdapter extends FragmentPagerAdapter{

    private static final int PAGE_COUNT = 3;

    public MainViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return new HomeFragment();
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
