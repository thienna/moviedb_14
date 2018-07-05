package com.nganhthien.mikemovie.screen.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.nganhthien.mikemovie.R;
import com.nganhthien.mikemovie.screen.BaseActivity;

public class DetailActivity extends BaseActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        findViewById(R.id.image_ic_detail_back).setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_ic_detail_back:
                onBackPressed();
                break;
            // TODO: add more onClick event here
        }
    }
}
