package com.nganhthien.mikemovie.screen.main;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.nganhthien.mikemovie.R;
import com.nganhthien.mikemovie.screen.BaseActivity;

public class MainActivity extends BaseActivity implements MainContract.View {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mViewPager = findViewById(R.id.viewpager_home);
        mTabLayout = findViewById(R.id.tablayout_home);

        MainViewPagerAdapter mainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mainViewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
