package com.example.rcassistant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;

import com.airbnb.lottie.LottieAnimationView;

public class splash extends AppCompatActivity {
    LottieAnimationView lottieAnimationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window=getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.white));
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_splash);
        lottieAnimationView=findViewById(R.id.lottiee);
        lottieAnimationView.animate();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                    startActivity(new Intent(splash.this, MainActivity.class));
                    finish();
            }


        },3000);
    }
}