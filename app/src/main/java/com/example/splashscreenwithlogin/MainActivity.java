package com.example.splashscreenwithlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Handler handler;
    ImageView img;
    TextView splash;
    Animation topAnim, bottomAnim;

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        splash = (TextView) findViewById(R.id.splash);
        img = (ImageView) findViewById(R.id.img);
        topAnim = AnimationUtils.loadAnimation(this, R.anim.topanim);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.buttomanim);

        //set animation to element
        img.setAnimation(topAnim);
        splash.setAnimation(bottomAnim);
//        slogan.setAnimation(bottomAnim);
        img = findViewById(R.id.img);
        img.animate().alpha(4000).setDuration(0);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent dsp = new Intent(MainActivity.this, Display_Portal.class);
                startActivity(dsp);
                finish();
            }
        }, 6000);
    }
}
