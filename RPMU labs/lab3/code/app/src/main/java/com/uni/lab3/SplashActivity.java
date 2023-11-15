package com.uni.lab3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.uni.lab3.activities.MainActivity;

public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
