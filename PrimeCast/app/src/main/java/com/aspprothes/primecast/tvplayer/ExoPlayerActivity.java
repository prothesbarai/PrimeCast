package com.aspprothes.primecast.tvplayer;

import androidx.annotation.OptIn;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.MediaItem;
import androidx.media3.common.PlaybackParameters;
import androidx.media3.common.Player;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.AspectRatioFrameLayout;
import androidx.media3.ui.DefaultTimeBar;
import androidx.media3.ui.PlayerView;
import androidx.media3.ui.TimeBar;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.aspprothes.primecast.R;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;

public class ExoPlayerActivity extends AppCompatActivity {
    public static String get_tv_url = "";
    public static String getTvName = "";
    private String[] speeds = {"0.5x","0.75x","Normal","1.25x","1.5x"};
    private boolean isFullScreen = false;
    private int RESIZE_MODE = 0;
    private ImageView exoBack,exoBacward,exoForward,exoPlayPause,exoFull,exoSpeed,exoResize;
    private TextView exoTitle,currentTimeView,exoDuration;
    private DefaultTimeBar timeBar;
    private PlayerView playerView;
    private ExoPlayer exoPlayer;
    private final Handler handler = new Handler(Looper.getMainLooper());

    @OptIn(markerClass = UnstableApi.class)
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
        exoSpeed = playerView.findViewById(R.id.exoSpeed);
        exoResize = playerView.findViewById(R.id.exoResize);
        exoTitle = playerView.findViewById(R.id.exoTitle);
        currentTimeView = playerView.findViewById(R.id.currentTimeView);
        exoDuration = playerView.findViewById(R.id.exoDuration);
        timeBar = playerView.findViewById(R.id.timeBar);

        exoTitle.setText(""+getTvName);



        exoPlayer = new ExoPlayer.Builder(this).build();
        playerView.setPlayer(exoPlayer);
        MediaItem mediaItem = MediaItem.fromUri(get_tv_url);
        exoPlayer.setMediaItem(mediaItem);
        exoPlayer.prepare();
        exoPlayer.setPlayWhenReady(true);
        exoPlayer.play();



        exoPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exoPlayer.setPlayWhenReady(!exoPlayer.getPlayWhenReady());
                exoPlayPause.setImageResource(Boolean.TRUE.equals(exoPlayer.getPlayWhenReady()) ? R.drawable.pause : R.drawable.play);
                // When Video is stoped then again radey to play
                if (exoPlayer.getPlaybackState() == Player.STATE_ENDED) {
                    // গান শেষ হলে পুনরায় শুরু করুন
                    exoPlayer.seekTo(0); // শুরুতে নিয়ে যান
                    exoPlayer.play();   // প্লে করুন
                    exoPlayPause.setImageResource(R.drawable.pause); // প্লে আইকন দেখান
                }
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

        exoSpeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ExoPlayerActivity.this);
                builder.setTitle("Set Speeds");
                builder.setItems(speeds, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0){
                            PlaybackParameters parameters = new PlaybackParameters(0.5f);
                            exoPlayer.setPlaybackParameters(parameters);
                        }if (which == 1){
                            PlaybackParameters parameters = new PlaybackParameters(0.75f);
                            exoPlayer.setPlaybackParameters(parameters);
                        }if (which == 2){
                            PlaybackParameters parameters = new PlaybackParameters(1f);
                            exoPlayer.setPlaybackParameters(parameters);

                        }if (which == 3){
                            PlaybackParameters parameters = new PlaybackParameters(1.25f);
                            exoPlayer.setPlaybackParameters(parameters);

                        }if (which == 4){
                            PlaybackParameters parameters = new PlaybackParameters(1.5f);
                            exoPlayer.setPlaybackParameters(parameters);

                        }
                    }
                });
                builder.show();
            }
        });


        exoResize.setOnClickListener(new View.OnClickListener() {
            @OptIn(markerClass = UnstableApi.class)
            @Override
            public void onClick(View v) {
                if (RESIZE_MODE == 0){
                    playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
                    RESIZE_MODE = 1;
                } else if (RESIZE_MODE == 1) {
                    playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_HEIGHT);
                    RESIZE_MODE = 2;
                }else if (RESIZE_MODE == 2) {
                    playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_ZOOM);
                    RESIZE_MODE = 3;
                }else if (RESIZE_MODE == 3) {
                    playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);
                    RESIZE_MODE = 0;
                }
            }
        });






        // ===================================================== Exoplayer Custom Loader Start Here ==========================================================
        ProgressBar progressBar = (ProgressBar)findViewById(R.id.spin_kit);
        Sprite circle = new Circle();
        progressBar.setIndeterminateDrawable(circle);

        exoPlayer.addListener(new Player.Listener() {
            @Override
            public void onPlaybackStateChanged(int state) {
                if (state == Player.STATE_READY){
                    progressBar.setVisibility(View.GONE);
                    exoPlayPause.setVisibility(View.VISIBLE);
                }else if (state == Player.STATE_BUFFERING){
                    progressBar.setVisibility(View.VISIBLE);
                    exoPlayPause.setVisibility(View.INVISIBLE);
                } // This Method Used Call From Bottom Setup Default Time Bar And Reset All Process
                else if (state == Player.STATE_ENDED) {
                    exoPlayPause.setImageResource(R.drawable.play);
                    timeBar.setPosition(0);
                    timeBar.setBufferedPosition(0);
                    timeBar.setDuration(0);
                    currentTimeView.setText(formatTime(0));
                    exoDuration.setText(formatTime(0));
                } else{
                    progressBar.setVisibility(View.GONE);
                    exoPlayPause.setVisibility(View.VISIBLE);
                    // This Method Used Call From Bottom Setup Default Time Bar
                    updateUI();
                }
            }
        });// ===================================================== Exoplayer Custom Loader End Here ==========================================================



        // ============================================ TimeBar Onclick Listner Start Here =================================================================
        timeBar.addListener(new TimeBar.OnScrubListener() {
            @OptIn(markerClass = UnstableApi.class)
            @Override
            public void onScrubStart(TimeBar timeBar, long position) {
                // Pause player while scrubbing
                exoPlayer.pause();
            }
            @OptIn(markerClass = UnstableApi.class)
            @Override
            public void onScrubMove(TimeBar timeBar, long position) {
                currentTimeView.setText(formatTime(position));
            }
            @OptIn(markerClass = UnstableApi.class)
            @Override
            public void onScrubStop(TimeBar timeBar, long position, boolean canceled) {
                // Seek to the scrubbed position and resume playback
                exoPlayer.seekTo(position);
                exoPlayer.play();
            }
        });// ============================================ TimeBar Onclick Listner End Here =================================================================



        startUpdatingUI();

    }/////// ========================== onCreate Method End Here =====================================


    // This Class Write For TimeBar And Show Current Time ( Start Here ) ==================================================
    @OptIn(markerClass = UnstableApi.class)
    private void updateUI() {
        long currentPosition = exoPlayer.getCurrentPosition();
        long duration = exoPlayer.getDuration();
        currentTimeView.setText(formatTime(currentPosition));
        exoDuration.setText(formatTime(duration));
        timeBar.setBufferedPosition(exoPlayer.getBufferedPosition());
        timeBar.setPosition(currentPosition);
        timeBar.setDuration(duration);
    }

    private void startUpdatingUI() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (exoPlayer != null && exoPlayer.isPlaying()) {
                    updateUI();
                }
                handler.postDelayed(this, 1000); // Update every second
            }
        }, 0);
    }

    private String formatTime(long timeInMillis) {
        long seconds = (timeInMillis / 1000) % 60;
        long minutes = (timeInMillis / (1000 * 60)) % 60;
        long hours = (timeInMillis / (1000 * 60 * 60));

        if (hours > 0) {
            return String.format("%02d:%02d:%02d", hours, minutes, seconds);
        } else {
            return String.format("%02d:%02d", minutes, seconds);
        }
    }// This Class Write For TimeBar And Show Current Time ( End Here ) ==================================================




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
        if (exoPlayer != null) {
            exoPlayer.release();
        }
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}