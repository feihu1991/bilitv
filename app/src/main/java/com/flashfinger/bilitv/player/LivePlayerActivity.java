package com.flashfinger.bilitv.player;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.flashfinger.bilitv.R;
import com.flashfinger.bilitv.data.VideoData;
import com.flashfinger.bilitv.danmaku.DanmakuManager;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.video.VideoSize;

/**
 * Live Stream Player Activity with 4K support and Danmaku
 * Compatible with TV remote control
 */
public class LivePlayerActivity extends AppCompatActivity {
    private static final String TAG = "LivePlayerActivity";

    private PlayerView playerView;
    private ExoPlayer player;
    private DanmakuManager danmakuManager;
    private View controlsView;
    private TextView titleView;
    private TextView descriptionView;
    private TextView liveStatusView;
    private View liveIndicator;

    private Handler controlsHandler = new Handler(Looper.getMainLooper());
    private Runnable hideControlsRunnable;
    private boolean controlsVisible = true;
    private int windowWidth;
    private int windowHeight;
    private long viewerCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        // Force landscape orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // Get display dimensions
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        windowWidth = metrics.widthPixels;
        windowHeight = metrics.heightPixels;

        initializePlayer();
        initializeDanmaku();
        initializeControls();
        loadVideoData();

        // Auto-hide controls after 5 seconds
        setupAutoHideControls();

        // Start viewer count update
        startViewerCountUpdate();
    }

    private void initializePlayer() {
        player = new ExoPlayer.Builder(this)
                .build();

        playerView = findViewById(R.id.player_view);
        playerView.setPlayer(player);
        playerView.setUseController(false); // Use custom controls

        // Support 4K playback
        player.setVideoScalingMode(com.google.android.exoplayer2.ui.AspectRatioFrameLayout.RESIZE_MODE_FIT);

        player.addListener(new Player.Listener() {
            @Override
            public void onVideoSizeChanged(VideoSize videoSize) {
                Log.d(TAG, "Live video size: " + videoSize.width + "x" + videoSize.height);
                if (videoSize.width >= 3840 && videoSize.height >= 2160) {
                    Log.d(TAG, "Streaming 4K live");
                }
            }

            @Override
            public void onIsPlayingChanged(boolean isPlaying) {
                if (isPlaying) {
                    danmakuManager.start();
                } else {
                    danmakuManager.pause();
                }
            }
        });
    }

    private void initializeDanmaku() {
        FrameLayout danmakuContainer = findViewById(R.id.danmaku_container);
        danmakuManager = new DanmakuManager(this, danmakuContainer, windowWidth, windowHeight);
    }

    private void initializeControls() {
        controlsView = findViewById(R.id.controls_view);
        titleView = findViewById(R.id.title_text);
        descriptionView = findViewById(R.id.description_text);
        liveStatusView = findViewById(R.id.live_status);
        liveIndicator = findViewById(R.id.live_indicator);
        liveIndicator.setVisibility(View.VISIBLE);

        if (liveStatusView != null) {
            liveStatusView.setVisibility(View.VISIBLE);
        }
    }

    private void loadVideoData() {
        Intent intent = getIntent();
        String videoUrl = intent.getStringExtra(VideoData.VIDEO_URL);
        String title = intent.getStringExtra(VideoData.VIDEO_TITLE);
        String description = intent.getStringExtra(VideoData.VIDEO_DESCRIPTION);
        String danmakuUrl = intent.getStringExtra(VideoData.VIDEO_DANMAKU_URL);

        if (title != null) {
            titleView.setText("【直播】" + title);
        }
        if (description != null) {
            descriptionView.setText(description);
        }

        if (videoUrl != null) {
            MediaItem mediaItem = MediaItem.fromUri(videoUrl);
            player.setMediaItem(mediaItem);
            player.prepare();
            player.play();

            // Start live danmaku
            startLiveDanmaku();
        }
    }

    private void startLiveDanmaku() {
        String[] liveDanmakus = {
            "666666",
            "主播牛逼！",
            "这个操作太强了",
            "前排围观",
            "这波怎么说？",
            "学到了",
            "6666666",
            "太强了",
            "专业啊",
            "哈哈哈",
            "yyds",
            "爱了爱了",
            "这技术",
            "绝了",
            "好耶",
            "真香",
            "这操作",
            "大佬",
            "支持支持",
            "好看好看"
        };

        // Continuously add live danmaku
        controlsHandler.post(new Runnable() {
            private int index = 0;

            @Override
            public void run() {
                if (!isFinishing() && player != null && player.isPlaying()) {
                    danmakuManager.addDanmaku(liveDanmakus[index]);
                    index = (index + 1) % liveDanmakus.length;

                    // Add danmaku every 500-2000ms randomly
                    long delay = 500 + (long)(Math.random() * 1500);
                    controlsHandler.postDelayed(this, delay);
                }
            }
        });
    }

    private void startViewerCountUpdate() {
        controlsHandler.post(new Runnable() {
            @Override
            public void run() {
                if (!isFinishing()) {
                    // Simulate viewer count changes
                    viewerCount += (long)(Math.random() * 100) - 50;
                    if (viewerCount < 0) viewerCount = 1000;

                    if (descriptionView != null) {
                        String originalDesc = getIntent().getStringExtra(VideoData.VIDEO_DESCRIPTION);
                        descriptionView.setText(originalDesc + " | " + viewerCount + "人观看");
                    }

                    controlsHandler.postDelayed(this, 1000);
                }
            }
        });
    }

    private void setupAutoHideControls() {
        hideControlsRunnable = () -> {
            if (controlsVisible) {
                hideControls();
            }
        };
        resetHideTimer();
    }

    private void resetHideTimer() {
        controlsHandler.removeCallbacks(hideControlsRunnable);
        controlsHandler.postDelayed(hideControlsRunnable, 5000);
    }

    private void showControls() {
        controlsVisible = true;
        controlsView.setVisibility(View.VISIBLE);
        controlsView.setAlpha(0f);
        ObjectAnimator animator = ObjectAnimator.ofFloat(controlsView, "alpha", 0f, 1f);
        animator.setDuration(300);
        animator.start();
        resetHideTimer();
    }

    private void hideControls() {
        controlsVisible = false;
        ObjectAnimator animator = ObjectAnimator.ofFloat(controlsView, "alpha", 1f, 0f);
        animator.setDuration(300);
        animator.start();
        controlsHandler.postDelayed(() -> {
            controlsView.setVisibility(View.GONE);
        }, 300);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.d(TAG, "Key code: " + keyCode);

        // Handle remote control keys
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
            case KeyEvent.KEYCODE_ESCAPE:
                finish();
                return true;

            case KeyEvent.KEYCODE_DPAD_CENTER:
            case KeyEvent.KEYCODE_ENTER:
                // Toggle play/pause
                if (player.getPlayWhenReady()) {
                    player.pause();
                    danmakuManager.pause();
                } else {
                    player.play();
                    danmakuManager.start();
                }
                return true;

            case KeyEvent.KEYCODE_DPAD_UP:
            case KeyEvent.KEYCODE_DPAD_DOWN:
            case KeyEvent.KEYCODE_DPAD_LEFT:
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                // Show controls when using D-pad
                showControls();
                return super.onKeyDown(keyCode, event);

            case KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE:
                if (player.getPlayWhenReady()) {
                    player.pause();
                    danmakuManager.pause();
                } else {
                    player.play();
                    danmakuManager.start();
                }
                return true;

            case KeyEvent.KEYCODE_MEDIA_PLAY:
                player.play();
                danmakuManager.start();
                return true;

            case KeyEvent.KEYCODE_MEDIA_PAUSE:
                player.pause();
                danmakuManager.pause();
                return true;

            case KeyEvent.KEYCODE_MEDIA_STOP:
                player.stop();
                finish();
                return true;

            case KeyEvent.KEYCODE_MENU:
                // Show settings menu (implement if needed)
                return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (player != null) {
            player.pause();
        }
        if (danmakuManager != null) {
            danmakuManager.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.release();
            player = null;
        }
        if (danmakuManager != null) {
            danmakuManager.release();
        }
        controlsHandler.removeCallbacksAndMessages(null);
    }
}
