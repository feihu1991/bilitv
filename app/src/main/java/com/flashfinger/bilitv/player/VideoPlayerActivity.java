package com.flashfinger.bilitv.player;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
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
 * Video Player Activity with 4K support and Danmaku
 * Compatible with TV remote control
 */
public class VideoPlayerActivity extends AppCompatActivity {
    private static final String TAG = "VideoPlayerActivity";

    private PlayerView playerView;
    private ExoPlayer player;
    private DanmakuManager danmakuManager;
    private View controlsView;
    private TextView titleView;
    private TextView descriptionView;
    private View progressView;
    private View liveIndicator;

    private Handler controlsHandler = new Handler(Looper.getMainLooper());
    private Runnable hideControlsRunnable;
    private boolean controlsVisible = true;
    private int windowWidth;
    private int windowHeight;

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
                Log.d(TAG, "Video size: " + videoSize.width + "x" + videoSize.height);
                if (videoSize.width >= 3840 && videoSize.height >= 2160) {
                    Log.d(TAG, "Playing 4K video");
                }
            }

            @Override
            public void onPlaybackStateChanged(int playbackState) {
                if (playbackState == Player.STATE_ENDED) {
                    finish();
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
        progressView = findViewById(R.id.progress_view);
        liveIndicator = findViewById(R.id.live_indicator);
        liveIndicator.setVisibility(View.GONE);
    }

    private void loadVideoData() {
        Intent intent = getIntent();
        String videoUrl = intent.getStringExtra(VideoData.VIDEO_URL);
        String title = intent.getStringExtra(VideoData.VIDEO_TITLE);
        String description = intent.getStringExtra(VideoData.VIDEO_DESCRIPTION);
        String danmakuUrl = intent.getStringExtra(VideoData.VIDEO_DANMAKU_URL);

        if (title != null) {
            titleView.setText(title);
        }
        if (description != null) {
            descriptionView.setText(description);
        }

        if (videoUrl != null) {
            MediaItem mediaItem = MediaItem.fromUri(videoUrl);
            player.setMediaItem(mediaItem);
            player.prepare();
            player.play();

            // Load danmaku if available
            if (danmakuUrl != null && !danmakuUrl.isEmpty()) {
                danmakuManager.loadDanmaku(danmakuUrl);
            } else {
                // Generate demo danmaku
                generateDemoDanmaku();
            }
        }
    }

    private void generateDemoDanmaku() {
        String[] demoDanmakus = {
            "前面的区域以后再来探索吧",
            "23333333",
            "好耶！",
            "这个太棒了",
            "666666",
            "UP主太强了",
            "这也太帅了吧",
            "期待下一期",
            "画质真不错",
            "收藏了",
            "已投币",
            "三连支持",
            "这技术可以啊",
            "学到了",
            "这就是专业",
            "太强了",
            "大佬大佬",
            "爱了爱了",
            "绝了",
            "yyds"
        };

        for (int i = 0; i < demoDanmakus.length; i++) {
            final int delay = i * 2000;
            controlsHandler.postDelayed(() -> {
                danmakuManager.addDanmaku(demoDanmakus[i]);
            }, delay);
        }
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

    private void toggleControls() {
        if (controlsVisible) {
            hideControls();
        } else {
            showControls();
        }
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
                } else {
                    player.play();
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
                } else {
                    player.play();
                }
                return true;

            case KeyEvent.KEYCODE_MEDIA_PLAY:
                player.play();
                return true;

            case KeyEvent.KEYCODE_MEDIA_PAUSE:
                player.pause();
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
        player.pause();
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
