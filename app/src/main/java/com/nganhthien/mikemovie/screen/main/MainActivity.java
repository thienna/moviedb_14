package com.nganhthien.mikemovie.screen.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.nganhthien.mikemovie.R;
import com.nganhthien.mikemovie.screen.BaseActivity;
import com.nganhthien.mikemovie.screen.home.HomeFragment;

public class MainActivity extends BaseActivity implements MainContract.View {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_bottom);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        setContentViewForFrameLayout(new HomeFragment());
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    setContentViewForFrameLayout(new HomeFragment());
                    return true;
                // Have not yet others frag
                case R.id.navigation_favorite:
                    setContentViewForFrameLayout(new HomeFragment());
                    return true;
                case R.id.navigation_more:
                    setContentViewForFrameLayout(new HomeFragment());
                    return true;
            }
            return false;
        }
    };

    private void setContentViewForFrameLayout(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_container, fragment)
                .commit();
    }
}
