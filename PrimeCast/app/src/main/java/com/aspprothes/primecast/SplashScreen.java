package com.aspprothes.primecast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.makeramen.roundedimageview.RoundedImageView;
import com.mursaat.extendedtextview.AnimatedGradientTextView;

public class SplashScreen extends AppCompatActivity {
    private RoundedImageView roundedImg;
    private AnimatedGradientTextView animatedTextView;
    private Animation animation1,animation2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
            WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
            layoutParams.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            getWindow().setAttributes(layoutParams);
        }
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );
        setContentView(R.layout.splash_screen);



        roundedImg = findViewById(R.id.roundedImg);
        animatedTextView = findViewById(R.id.animatedTextView);
        animation1 = AnimationUtils.loadAnimation(SplashScreen.this,R.anim.up_to_down);
        animation2 = AnimationUtils.loadAnimation(SplashScreen.this,R.anim.zoom_in_fade);
        roundedImg.setAnimation(animation1);
        animatedTextView.setAnimation(animation2);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                startProgress();
                goHomeActivity();
            }
        });

        thread.start();

    } /////////////////////// ============================= onCreate Method End Here ==================================/////////////

    public void startProgress(){
        try {
            Thread.sleep(4300,200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void goHomeActivity(){
        startActivity(new Intent(SplashScreen.this, HomeActivity.class));
        finish();
    }

}