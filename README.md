
# Exoplayer Media3 Implement Guide Line

`Exoplayer Media3 Implement Guide Line`


## Installation

### Step 1 : write this code :---- Manifest.xml File

```xml

    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />




    <application

        android:usesCleartextTraffic="true"

    >
        <!-- This Activity is Exoplayer Activity -->
        <activity

            android:configChanges="keyboardHidden|orientation|screenSize"

            />
    </application>

```


### Step 2 : write this code :---- to build.gradle (Module:app) 

```java
dependencies {

    implementation "androidx.media3:media3-exoplayer:1.4.1"
    implementation "androidx.media3:media3-exoplayer-dash:1.4.1"
    implementation "androidx.media3:media3-exoplayer-hls:1.4.1"
    implementation "androidx.media3:media3-ui:1.4.1"
    implementation 'com.github.ybq:Android-SpinKit:1.4.0'

}

```


### Step 2.1 : write this code :---- to setting.gradle ( Project Setting )

```java
dependencyResolutionManagement {
    repositories {
        
        maven { url "https://jitpack.io" }

    }
}

```


### Step 2.2 : Uses Color to colors.xml File

```xml

    <color name="red">#E91E63</color>
    <color name="transparent">#00FFFFFF</color>
    <color name="yellow">#FFEB3B</color>

    <!--Neumorphism Color List-->
    <color name="primary">#2D7DF6</color>
    <color name="background">#E4EBF5</color>
    <color name="primary_text">#0B0D0C</color>
    <color name="secondary_text">#7D818A</color>
    <color name="dark_shadow">#CDD5EA</color>
    <color name="light_shadow">#F8F9FE</color>
    <color name="dark_icon">#212121</color>

    <color name="color1">#fa0553</color>
    <color name="color2">#760d6a</color>
    <color name="color3">#9450fd</color>
    <color name="color4">#6071ff</color>
    <color name="color5">#1aa3e7</color>
    <color name="color6">#95feff</color>
    <color name="color7">#6071ff</color>

    <array name="funny_color">
        <item>@color/color1</item>
        <item>@color/color2</item>
        <item>@color/color3</item>
        <item>@color/color4</item>
        <item>@color/color5</item>
        <item>@color/color6</item>
        <item>@color/color7</item>
    </array>

```



### Step 3 : Open this file :---- ExoPlayer Activity XML FIle [ Main Player XML File ]

```xml

<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:background="@color/black"
    tools:ignore="MissingConstraints"
    android:layout_gravity="center"
    >

    <androidx.media3.ui.PlayerView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:id="@+id/playerView"
        app:resize_mode="fit"
        android:background="@color/black"
        app:played_color="@color/color3"
        app:buffered_color="@color/white"
        app:unplayed_color="@color/red"
        app:scrubber_color="@color/color5"
        app:controller_layout_id="@layout/custom_controls_player"
        />

    <!--
    If you Add This SpinKitView Progress So Remove first
        app:show_buffering="when_playing"
    from PlayerView..... Then Write Code Java File
    Exoplayer Custom Progress Bar Add Start Here Lines
    -->

    <com.github.ybq.android.spinkit.SpinKitView
        android:id="@+id/spin_kit"
        style="@style/SpinKitView.Large.Circle"
        android:layout_width="50dp"
        android:layout_height="50dp"
        android:layout_gravity="center"
        app:SpinKit_Color="@color/background"
        app:layout_constraintBottom_toBottomOf="@id/playerView"
        app:layout_constraintEnd_toEndOf="@id/playerView"
        app:layout_constraintStart_toStartOf="@id/playerView"
        app:layout_constraintTop_toTopOf="parent"
        />

</androidx.constraintlayout.widget.ConstraintLayout>


```


### Step 4 : create this file :---- custom_controls_player xml FIle

```xml

<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:background="#33000000"
    tools:ignore="MissingConstraints"
    >

    <ImageView
        android:layout_width="32dp"
        android:layout_height="32dp"
        android:id="@+id/exoBack"
        android:layout_marginStart="10dp"
        android:layout_marginTop="10dp"
        android:src="@drawable/backbutton"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        />

    <TextView
        android:layout_width="150dp"
        android:layout_height="wrap_content"
        android:id="@+id/exoTitle"
        android:textColor="@color/white"
        android:textStyle="bold"
        android:textSize="15sp"
        android:singleLine="true"
        android:layout_marginStart="10dp"
        android:visibility="gone"
        app:layout_constraintBottom_toBottomOf="@id/exoBack"
        app:layout_constraintStart_toEndOf="@id/exoBack"
        app:layout_constraintTop_toTopOf="@id/exoBack"
        />

    <ImageView
        android:layout_width="32dp"
        android:layout_height="32dp"
        android:id="@+id/exoPlayPause"
        android:visibility="invisible"
        android:src="@drawable/pause"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        />

    <ImageView
        android:layout_width="28dp"
        android:layout_height="28dp"
        android:id="@+id/exoQualitySetting"
        android:layout_marginTop="15dp"
        android:layout_marginEnd="10dp"
        android:src="@drawable/outline_settings_24"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        />


    <ImageView
        android:id="@+id/exoSpeed"
        android:layout_width="28dp"
        android:layout_height="28dp"
        android:layout_marginTop="15dp"
        android:layout_marginEnd="13dp"
        android:src="@drawable/baseline_slow_motion_video_24"
        app:layout_constraintEnd_toStartOf="@id/exoQualitySetting"
        app:layout_constraintTop_toTopOf="parent" />
    
    <ImageView
        android:layout_width="28dp"
        android:layout_height="28dp"
        android:src="@drawable/outline_aspect_ratio_24"
        android:id="@+id/exoResize"
        android:layout_marginTop="15dp"
        android:layout_marginEnd="15dp"
        app:layout_constraintEnd_toStartOf="@id/exoSpeed"
        app:layout_constraintTop_toTopOf="parent"
        />

    <ImageView
        android:layout_width="32dp"
        android:layout_height="32dp"
        android:id="@+id/exoForward"
        android:src="@drawable/forward"
        app:layout_constraintBottom_toBottomOf="@id/exoPlayPause"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toEndOf="@id/exoPlayPause"
        app:layout_constraintTop_toTopOf="@id/exoPlayPause"
        />


    <ImageView
        android:layout_width="32dp"
        android:layout_height="32dp"
        android:id="@+id/exoBacward"
        android:src="@drawable/backward"
        app:layout_constraintBottom_toBottomOf="@id/exoPlayPause"
        app:layout_constraintEnd_toEndOf="@id/exoPlayPause"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="@id/exoPlayPause"
        />

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:id="@+id/linearLayout"
        android:layout_marginBottom="10dp"
        android:gravity="center_vertical"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        >

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:id="@+id/currentTimeView"
            android:layout_marginStart="10dp"
            android:text="00:00"
            android:textSize="14sp"
            android:textStyle="bold"
            android:textColor="@color/white"
            />

        <androidx.media3.ui.DefaultTimeBar
            android:layout_width="0dp"
            android:layout_height="18dp"
            android:layout_weight="1"
            android:id="@+id/timeBar"
            />

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:id="@+id/exoDuration"
            android:text="00:00"
            android:textSize="14sp"
            android:textStyle="bold"
            android:textColor="@color/white"
            />

        <ImageView
            android:layout_width="24dp"
            android:layout_height="24dp"
            android:layout_marginStart="10dp"
            android:layout_marginEnd="10dp"
            android:src="@drawable/fullscreenclose"
            android:id="@+id/exoFull"/>

    </LinearLayout>

</androidx.constraintlayout.widget.ConstraintLayout>

```


### Step 5 : Write Code to themes.xml File
```xml

    <!-- Exoplayer Setting Option Track Theme Style -->
    <style name="TrackSelectionDialogThemeOverlay" parent="ThemeOverlay.AppCompat.Dialog.Alert">
        <item name="windowNoTitle">false</item>
    </style>
    
```

### Step 6 : Write Code to strings.xml File
```xml

    <!--Exoplayer Setting Options Some String-->
    <string name="select_quality">Select Quality</string>
    <string name="track_selection_title">Select Tracks</string>
    <string name="exo_track_selection_title_audio">Audio</string>
    <string name="exo_track_selection_title_text">Text</string>
    <string name="exo_track_selection_title_video">Video</string>
    <string name="exo_track_selection_title_image">Images</string>
    
```


### Step 7 : Write Code Exoplayer Activity Java File :---- This Java File Connected with Step 3 XML File 

```java

    public static String get_tv_url = "";
    public static String getTvName = "";
    private String[] speeds = {"0.5x","0.75x","Normal","1.25x","1.5x"};
    private boolean isFullScreen = false;
    private boolean isShowingTrackSelectionDialog;
    private int RESIZE_MODE = 0;
    private ImageView exoBack,exoBacward,exoForward,exoPlayPause,exoFull,exoSpeed,exoResize,exoQualitySetting;
    private TextView exoTitle,currentTimeView,exoDuration;
    private DefaultTimeBar timeBar;
    private PlayerView playerView;
    private ExoPlayer exoPlayer;
    private final Handler handler = new Handler(Looper.getMainLooper());


    // After setContentView and write this code .. [ সেট কন্টেন্ট ভিউ এর পূর্বে Display FullScreen করে নিতে হবে]
    //--------------------পরিচয় পর্ব-----------------------
    playerView = findViewById(R.id.playerView);
    exoBack = playerView.findViewById(R.id.exoBack);
    exoBacward = playerView.findViewById(R.id.exoBacward);
    exoForward = playerView.findViewById(R.id.exoForward);
    exoPlayPause = playerView.findViewById(R.id.exoPlayPause);
    exoFull = playerView.findViewById(R.id.exoFull);
    exoSpeed = playerView.findViewById(R.id.exoSpeed);
    exoResize = playerView.findViewById(R.id.exoResize);
    exoTitle = playerView.findViewById(R.id.exoTitle);
    exoQualitySetting = playerView.findViewById(R.id.exoQualitySetting);
    currentTimeView = playerView.findViewById(R.id.currentTimeView);
    exoDuration = playerView.findViewById(R.id.exoDuration);
    timeBar = playerView.findViewById(R.id.timeBar);

    exoTitle.setText(""+getTvName);



    // ------------------------ Exoplayer Is Start ----------------------------

    exoPlayer = new ExoPlayer.Builder(this).build();
    playerView.setPlayer(exoPlayer);
    MediaItem mediaItem = MediaItem.fromUri(get_tv_url);
    exoPlayer.setMediaItem(mediaItem);
    exoPlayer.prepare();
    exoPlayer.setPlayWhenReady(true);
    exoPlayer.play();




    // ------------------------ ExoPlayer Custom Button Functionality Here ----------------------------



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

        exoQualitySetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // এই কোড টুকু লেখার পূর্বে অবশ্যই Step Setting For TrackSelectionDialog (নিচে দেওয়া আছে) -- Follow করতে হবে এবং ফাইল ডাউনলোড করে JAVA ফোল্ডারে এবং XML ফোল্ডারে দিয়ে নিতে হবে 
                if (!isShowingTrackSelectionDialog
                        && TrackSelectionDialog.willHaveContent(exoPlayer)) {
                    isShowingTrackSelectionDialog = true;
                    TrackSelectionDialog trackSelectionDialog =
                            TrackSelectionDialog.createForPlayer(
                                    exoPlayer,
                                    /* onDismissListener= */ dismissedDialog -> isShowingTrackSelectionDialog = false);
                    trackSelectionDialog.show(getSupportFragmentManager(), /* tag= */ null);
                }
            }
        });






    // ------------------------ ExoPlayer Custom Loader Start Here ----------------------------




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


    



    // ------------------------ ExoPlayer Custom TimeBar Start Here ----------------------------



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



        /////// ========================== onCreate Method End Here ===================================== 



    ///////////////////////////////         Warning        ////////////////////////////////

                    /*নিচের কোডটুকু লেখার পূর্বে দেখে নিন ===    Warning =========
                    যেখানে onCreate Method শেষ হয়েছে তারপরে এই কোড টুকু লিখুন*/


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

```


### Resource For exoQualitySetting [ Step Setting For TrackSelectionDialog ] 
- [ Download After Extract Paste JAVA and XML Folder ]
- Download Zip File : [exoQualitySetting File Download](https://github.com/prothesbarai/PrimeCast/blob/main/TrackSelectionDialog.zip) 
- OR [ Download From Drive ](https://drive.google.com/file/d/1Zoi8uluQBQ_-IILDp83CRYSPLzmAcR09/view?usp=sharing)





### Drawable Resource [ Download After Extract Paste Drawable Folder ]
- Download Zip File : [Click Here](https://github.com/prothesbarai/PrimeCast/blob/main/Exoplayer_Image_Resource.zip) 
- OR [ Download From Drive ](https://drive.google.com/file/d/11vfdSJrm2EQM_iHwYb57kxlKQyCsn_2B/view?usp=sharing)


## Screenshots

![App Screenshot](https://via.placeholder.com/468x300?text=App+Screenshot+Here)





## Author
- [Prothes Barai](https://prothesbarai.github.io/prothesbarai/)

