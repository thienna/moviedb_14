package com.nganhthien.mikemovie.screen.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.nganhthien.mikemovie.screen.BaseActivity;
import com.nganhthien.mikemovie.screen.main.MainActivity;


/**
 * Created by ThienNA on 29/06/2018.
 */

public class SplashActivity extends BaseActivity {

    private static final int SPLASH_TIMEOUT = 1500;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set timeout for Splash Screen
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Start Home Activity after Splash Screen
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);

                finish();
            }
        }, SPLASH_TIMEOUT);
    }
}
