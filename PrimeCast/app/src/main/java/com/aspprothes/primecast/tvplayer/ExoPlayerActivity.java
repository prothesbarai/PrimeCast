package com.aspprothes.primecast.tvplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.aspprothes.primecast.R;
import com.aspprothes.primecast.netconnectioncheck.Common;

public class ExoPlayerActivity extends AppCompatActivity {
    public static String get_tv_url = "";
    String url = "https://test-streams.mux.dev/x36xhzz/x36xhzz.m3u8";
    private ImageView exoBack,exoBacward,exoForward,exoPlayPause,exoFull;
    private TextView exoTitle,exoPosition,exoDuration;
    private PlayerView playerView;
    private ExoPlayer exoPlayer;
    private boolean isFullScreen = false;
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
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );
        setContentView(R.layout.exo_player_activity);


        playerView = findViewById(R.id.playerView);
        exoBack = playerView.findViewById(R.id.exoBack);
        exoBacward = playerView.findViewById(R.id.exoBacward);
        exoForward = playerView.findViewById(R.id.exoForward);
        exoPlayPause = playerView.findViewById(R.id.exoPlayPause);
        exoFull = playerView.findViewById(R.id.exoFull);
        exoTitle = playerView.findViewById(R.id.exoTitle);
        exoPosition = playerView.findViewById(R.id.exoPosition);
        exoDuration = playerView.findViewById(R.id.exoDuration);
        exoTitle.setText("Prothes Barai");

        exoPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exoPlayer.setPlayWhenReady(!exoPlayer.getPlayWhenReady());
                exoPlayPause.setImageResource(Boolean.TRUE.equals(exoPlayer.getPlayWhenReady()) ? R.drawable.pause : R.drawable.play);
            }
        });

        exoForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exoPlayer.seekTo(exoPlayer.getCurrentPosition() + 10000);
            }
        });
        exoBacward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long num = exoPlayer.getCurrentPosition() - 10000;
                if (num<0){
                    exoPlayer.seekTo(0);
                }else{
                    exoPlayer.seekTo(exoPlayer.getCurrentPosition() - 10000);
                }
            }
        });
        exoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        exoFull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFullScreen){
                    exoTitle.setVisibility(View.INVISIBLE);
                    exoFull.setImageDrawable(getDrawable(R.drawable.fullscreenclose));
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    isFullScreen = false;
                }else{
                    exoTitle.setVisibility(View.VISIBLE);
                    exoFull.setImageDrawable(getDrawable(R.drawable.fullscreenopen));
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    isFullScreen = true;
                }
            }
        });


        exoPlayer = new ExoPlayer.Builder(this).build();
        playerView.setPlayer(exoPlayer);
        MediaItem mediaItem = MediaItem.fromUri(url);
        exoPlayer.setMediaItem(mediaItem);
        exoPlayer.prepare();
        exoPlayer.setPlayWhenReady(true);
        exoPlayer.play();











    }/////// ========================== onCreate Method End Here =====================================











    // Internet Connection Checker Start Here ===================
    public class InternetCheck extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (!Common.isNetworkConnected(ExoPlayerActivity.this)){

            }else{

            }
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        exoPlayer.play();
        super.onResume();
    }

    @Override
    protected void onPause() {
        exoPlayer.pause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        exoPlayer.release();
        super.onDestroy();
    }
}