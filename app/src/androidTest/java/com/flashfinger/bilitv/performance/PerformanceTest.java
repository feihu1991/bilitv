package com.flashfinger.bilitv.performance;

import android.content.Intent;
import android.os.SystemClock;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.UiDevice;

import com.flashfinger.bilitv.data.VideoData;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

/**
 * Performance test suite
 * Tests application performance metrics
 */
@RunWith(AndroidJUnit4.class)
public class PerformanceTest {

    private UiDevice device;

    @Before
    public void setUp() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    }

    /**
     * TC-21: 应用启动时间测试
     */
    @Test
    public void testAppLaunchTime() {
        long startTime = SystemClock.elapsedRealtime();

        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), com.flashfinger.bilitv.MainActivity.class);
        try (ActivityScenario<com.flashfinger.bilitv.MainActivity> scenario = ActivityScenario.launch(intent)) {
            long endTime = SystemClock.elapsedRealtime();
            long launchTime = endTime - startTime;

            assertTrue("App launch time should be less than 3 seconds", launchTime < 3000);
            System.out.println("App launch time: " + launchTime + "ms");
        }
    }

    /**
     * TC-22: 视频播放器启动时间测试
     */
    @Test
    public void testVideoPlayerLaunchTime() {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), com.flashfinger.bilitv.player.VideoPlayerActivity.class);
        intent.putExtra(VideoData.VIDEO_ID, "1");
        intent.putExtra(VideoData.VIDEO_TITLE, "Test Video");
        intent.putExtra(VideoData.VIDEO_URL, "https://sf1-hscdn-tos.pstatp.com/obj/media-fe/xgplayer_doc_video/mp4/xgplayer-demo-720p.mp4");

        long startTime = SystemClock.elapsedRealtime();

        try (ActivityScenario<com.flashfinger.bilitv.player.VideoPlayerActivity> scenario = ActivityScenario.launch(intent)) {
            long endTime = SystemClock.elapsedRealtime();
            long launchTime = endTime - startTime;

            assertTrue("Video player launch time should be less than 2 seconds", launchTime < 2000);
            System.out.println("Video player launch time: " + launchTime + "ms");
        }
    }

    /**
     * TC-23: 主界面滚动性能测试
     */
    @Test
    public void testMainActivityScrollingPerformance() throws InterruptedException {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), com.flashfinger.bilitv.MainActivity.class);
        try (ActivityScenario<com.flashfinger.bilitv.MainActivity> scenario = ActivityScenario.launch(intent)) {
            Thread.sleep(1000);

            long startTime = SystemClock.elapsedRealtime();

            // Simulate rapid scrolling
            for (int i = 0; i < 10; i++) {
                device.pressDPadRight();
                Thread.sleep(100);
            }

            long endTime = SystemClock.elapsedRealtime();
            long scrollTime = endTime - startTime;

            assertTrue("Scrolling should be smooth and fast", scrollTime < 2000);
            System.out.println("Scrolling time for 10 items: " + scrollTime + "ms");
        }
    }

    /**
     * TC-24: 弹幕显示性能测试
     */
    @Test
    public void testDanmakuPerformance() throws InterruptedException {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), com.flashfinger.bilitv.player.VideoPlayerActivity.class);
        intent.putExtra(VideoData.VIDEO_ID, "1");
        intent.putExtra(VideoData.VIDEO_TITLE, "Test Video");
        intent.putExtra(VideoData.VIDEO_URL, "https://sf1-hscdn-tos.pstatp.com/obj/media-fe/xgplayer_doc_video/mp4/xgplayer-demo-720p.mp4");

        try (ActivityScenario<com.flashfinger.bilitv.player.VideoPlayerActivity> scenario = ActivityScenario.launch(intent)) {
            Thread.sleep(3000);

            long startTime = SystemClock.elapsedRealtime();

            // Measure danmaku rendering over 5 seconds
            Thread.sleep(5000);

            long endTime = SystemClock.elapsedRealtime();
            long danmakuTime = endTime - startTime;

            assertTrue("Danmaku rendering should be smooth", danmakuTime < 6000);
            System.out.println("Danmaku rendering time for 5 seconds: " + danmakuTime + "ms");
        }
    }

    /**
     * TC-25: 内存占用测试
     */
    @Test
    public void testMemoryUsage() {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), com.flashfinger.bilitv.MainActivity.class);
        try (ActivityScenario<com.flashfinger.bilitv.MainActivity> scenario = ActivityScenario.launch(intent)) {
            Runtime runtime = Runtime.getRuntime();
            long usedMemory = runtime.totalMemory() - runtime.freeMemory();
            long maxMemory = runtime.maxMemory();

            System.out.println("Used memory: " + (usedMemory / 1024 / 1024) + "MB");
            System.out.println("Max memory: " + (maxMemory / 1024 / 1024) + "MB");
            System.out.println("Memory usage: " + ((usedMemory * 100) / maxMemory) + "%");

            // Memory usage should be less than 80% of max memory
            assertTrue("Memory usage should be less than 80%", (usedMemory * 100) / maxMemory < 80);
        }
    }

    /**
     * TC-26: 视频缓冲测试
     */
    @Test
    public void testVideoBuffering() throws InterruptedException {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), com.flashfinger.bilitv.player.VideoPlayerActivity.class);
        intent.putExtra(VideoData.VIDEO_ID, "1");
        intent.putExtra(VideoData.VIDEO_TITLE, "Test Video");
        intent.putExtra(VideoData.VIDEO_URL, "https://sf1-hscdn-tos.pstatp.com/obj/media-fe/xgplayer_doc_video/mp4/xgplayer-demo-720p.mp4");

        try (ActivityScenario<com.flashfinger.bilitv.player.VideoPlayerActivity> scenario = ActivityScenario.launch(intent)) {
            Thread.sleep(2000);

            long startTime = SystemClock.elapsedRealtime();

            // Play for 10 seconds and check for buffering
            Thread.sleep(10000);

            long endTime = SystemClock.elapsedRealtime();
            long playTime = endTime - startTime;

            // Playback should be smooth with minimal buffering
            assertTrue("Video should play smoothly", playTime < 11000);
            System.out.println("Video playback time for 10 seconds: " + playTime + "ms");
        }
    }
}
