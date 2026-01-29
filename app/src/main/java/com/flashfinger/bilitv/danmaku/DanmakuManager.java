package com.flashfinger.bilitv.danmaku;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Danmaku Manager for displaying scrolling comments over video
 */
public class DanmakuManager {
    private static final String TAG = "DanmakuManager";
    private static final int DANMAKU_HEIGHT = 48;
    private static final int DANMAKU_PADDING = 8;
    private static final int DANMAKU_SPEED_BASE = 10000; // Base speed in ms
    private static final int DANMAKU_SPEED_VARIANCE = 5000; // Speed variance

    private Context context;
    private FrameLayout container;
    private int screenWidth;
    private int screenHeight;
    private List<String> danmakuList;
    private int danmakuIndex = 0;
    private boolean isRunning = false;
    private boolean isPaused = false;
    private Random random;

    public DanmakuManager(Context context, FrameLayout container, int screenWidth, int screenHeight) {
        this.context = context;
        this.container = container;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.danmakuList = new ArrayList<>();
        this.random = new Random();
        this.isRunning = true;
    }

    /**
     * Load danmaku from URL (would connect to Bilibili API in production)
     */
    public void loadDanmaku(String url) {
        Log.d(TAG, "Loading danmaku from: " + url);
        // In production, this would fetch danmaku from Bilibili API
        // For now, we'll use demo danmaku
        loadDemoDanmaku();
    }

    private void loadDemoDanmaku() {
        danmakuList.add("前面的区域以后再来探索吧");
        danmakuList.add("23333333");
        danmakuList.add("好耶！");
        danmakuList.add("这个太棒了");
        danmakuList.add("666666");
        danmakuList.add("UP主太强了");
        danmakuList.add("这也太帅了吧");
        danmakuList.add("期待下一期");
        danmakuList.add("画质真不错");
        danmakuList.add("收藏了");
        danmakuList.add("已投币");
        danmakuList.add("三连支持");
        danmakuList.add("这技术可以啊");
        danmakuList.add("学到了");
        danmakuList.add("这就是专业");
        danmakuList.add("太强了");
        danmakuList.add("大佬大佬");
        danmakuList.add("爱了爱了");
        danmakuList.add("绝了");
        danmakuList.add("yyds");
        danmakuList.add("前方高能");
        danmakuList.add("火钳刘明");
        danmakuList.add("弹幕护体");
        danmakuList.add("前方高能预警");
        danmakuList.add("见证历史");
        danmakuList.add("高能预警");
    }

    /**
     * Add a single danmaku text
     */
    public void addDanmaku(String text) {
        if (isPaused || text == null || text.isEmpty()) {
            return;
        }

        // Create danmaku view
        TextView danmakuView = createDanmakuView(text);

        // Calculate random position
        int maxRow = (int) ((screenHeight * 0.6) / DANMAKU_HEIGHT); // Use 60% of height
        int row = random.nextInt(maxRow);

        // Calculate speed
        int speed = DANMAKU_SPEED_BASE + random.nextInt(DANMAKU_SPEED_VARIANCE);

        // Set initial position (off-screen to the right)
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                DANMAKU_HEIGHT
        );
        params.topMargin = row * DANMAKU_HEIGHT + DANMAKU_PADDING;
        params.leftMargin = screenWidth;
        danmakuView.setLayoutParams(params);

        // Add to container
        container.addView(danmakuView);

        // Measure view to get width
        danmakuView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int danmakuWidth = danmakuView.getMeasuredWidth();

        // Animate
        animateDanmaku(danmakuView, danmakuWidth, speed);
    }

    private TextView createDanmakuView(String text) {
        TextView textView = new TextView(context);
        textView.setText(text);
        textView.setTextSize(24f);
        textView.setTextColor(Color.WHITE);
        textView.setShadowLayer(4f, 2f, 2f, Color.BLACK);
        textView.setPadding(16, 0, 16, 0);
        textView.setSingleLine(true);
        return textView;
    }

    private void animateDanmaku(final TextView view, int width, int duration) {
        ValueAnimator animator = ValueAnimator.ofInt(screenWidth, -width);
        animator.setDuration(duration);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(animation -> {
            int value = (int) animation.getAnimatedValue();
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
            params.leftMargin = value;
            view.setLayoutParams(params);
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                container.removeView(view);
            }
        });
        animator.start();
    }

    /**
     * Start danmaku playback
     */
    public void start() {
        isPaused = false;
        isRunning = true;
        Log.d(TAG, "Danmaku started");
    }

    /**
     * Pause danmaku playback
     */
    public void pause() {
        isPaused = true;
        Log.d(TAG, "Danmaku paused");
    }

    /**
     * Stop danmaku playback
     */
    public void stop() {
        isRunning = false;
        isPaused = false;
        Log.d(TAG, "Danmaku stopped");
    }

    /**
     * Clear all danmaku from screen
     */
    public void clear() {
        if (container != null) {
            container.removeAllViews();
        }
    }

    /**
     * Release resources
     */
    public void release() {
        clear();
        isRunning = false;
        if (danmakuList != null) {
            danmakuList.clear();
            danmakuList = null;
        }
    }
}
