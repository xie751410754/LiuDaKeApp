package com.cdxz.liudake.ui.main;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class LancherActivity extends AppCompatActivity implements Runnable {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().postDelayed(this, 1500);
    }

    @Override
    public void run() {
        if (isFinishing()) {
            finish();
            return;
        }

        WelcomeActivity.start(this);
        finish();
    }
}
