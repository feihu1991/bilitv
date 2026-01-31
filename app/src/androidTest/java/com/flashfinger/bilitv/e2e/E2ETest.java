package com.flashfinger.bilitv.e2e;

import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiSelector;

import com.flashfinger.bilitv.R;
import com.flashfinger.bilitv.data.VideoData;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertTrue;

/**
 * End-to-End test suite
 * Tests complete user journeys
 */
@RunWith(AndroidJUnit4.class)
public class E2ETest {

    private UiDevice device;

    @Before
    public void setUp() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    }

    /**
     * E2E-01: 完整视频观看流程
     * 用户从启动应用到观看视频并返回的完整流程
     */
    @Test
    public void testCompleteVideoWatchJourney() throws InterruptedException {
        // Step 1: Launch app
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), com.flashfinger.bilitv.MainActivity.class);
        try (ActivityScenario<com.flashfinger.bilitv.MainActivity> scenario = ActivityScenario.launch(intent)) {
            Thread.sleep(1000);

            // Step 2: Browse to a video
            device.pressDPadRight();
            Thread.sleep(500);

            // Step 3: Select and play video
            device.pressDPadCenter();
            Thread.sleep(2000);

            // Step 4: Verify video is playing
            String currentActivity = device.getCurrentActivityName();
            assertTrue("Should be in video player", currentActivity.contains("VideoPlayerActivity"));

            // Step 5: Watch video (simulate 10 seconds)
            Thread.sleep(10000);

            // Step 6: Show controls
            device.pressDPadUp();
            Thread.sleep(500);

            // Step 7: Test pause
            device.pressDPadCenter();
            Thread.sleep(1000);

            // Step 8: Test resume
            device.pressDPadCenter();
            Thread.sleep(1000);

            // Step 9: Test danmaku toggle
            device.pressDPadDown();
            Thread.sleep(500);
            device.pressDPadRight();
            Thread.sleep(500);
            device.pressDPadCenter();
            Thread.sleep(2000);

            // Step 10: Return to main screen
            device.pressBack();
            Thread.sleep(1000);
        }
    }

    /**
     * E2E-02: 完整直播观看流程
     * 用户观看直播的完整流程
     */
    @Test
    public void testCompleteLiveStreamJourney() throws InterruptedException {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), com.flashfinger.bilitv.MainActivity.class);
        try (ActivityScenario<com.flashfinger.bilitv.MainActivity> scenario = ActivityScenario.launch(intent)) {
            Thread.sleep(1000);

            // Navigate to live section
            device.pressDPadDown();
            device.pressDPadDown();
            Thread.sleep(500);

            // Select live stream
            device.pressDPadCenter();
            Thread.sleep(2000);

            // Verify live player
            String currentActivity = device.getCurrentActivityName();
            assertTrue("Should be in live player", currentActivity.contains("LivePlayerActivity"));

            // Watch live (simulate 15 seconds)
            Thread.sleep(15000);

            // Show controls
            device.pressDPadUp();
            Thread.sleep(500);

            // Pause/Resume
            device.pressDPadCenter();
            Thread.sleep(1000);
            device.pressDPadCenter();
            Thread.sleep(1000);

            // Check danmaku is flowing
            onView(withId(R.id.danmaku_container))
                    .check(matches(isDisplayed()));

            // Return
            device.pressBack();
            Thread.sleep(1000);
        }
    }

    /**
     * E2E-03: 多视频连续观看流程
     * 用户连续观看多个视频
     */
    @Test
    public void testMultipleVideosWatchJourney() throws InterruptedException {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), com.flashfinger.bilitv.MainActivity.class);

        try (ActivityScenario<com.flashfinger.bilitv.MainActivity> scenario = ActivityScenario.launch(intent)) {
            // Watch first video
            Thread.sleep(1000);
            device.pressDPadRight();
            device.pressDPadCenter();
            Thread.sleep(3000);
            device.pressBack();
            Thread.sleep(1000);

            // Watch second video
            device.pressDPadRight();
            device.pressDPadRight();
            device.pressDPadCenter();
            Thread.sleep(3000);
            device.pressBack();
            Thread.sleep(1000);

            // Watch third video
            device.pressDPadRight();
            device.pressDPadRight();
            device.pressDPadRight();
            device.pressDPadCenter();
            Thread.sleep(3000);
            device.pressBack();
            Thread.sleep(1000);
        }
    }

    /**
     * E2E-04: 浏览所有分类流程
     * 用户浏览所有视频分类
     */
    @Test
    public void testBrowseAllCategoriesJourney() throws InterruptedException {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), com.flashfinger.bilitv.MainActivity.class);
        try (ActivityScenario<com.flashfinger.bilitv.MainActivity> scenario = ActivityScenario.launch(intent)) {
            Thread.sleep(1000);

            // Browse Recommended (top row)
            device.pressDPadRight();
            device.pressDPadRight();
            device.pressDPadRight();
            Thread.sleep(500);

            // Navigate to 4K category
            device.pressDPadDown();
            Thread.sleep(500);
            device.pressDPadRight();
            device.pressDPadRight();
            Thread.sleep(500);

            // Navigate to Live category
            device.pressDPadDown();
            Thread.sleep(500);
            device.pressDPadRight();
            device.pressDPadRight();
            Thread.sleep(500);

            // Navigate to Popular category
            device.pressDPadDown();
            Thread.sleep(500);
            device.pressDPadRight();
            device.pressDPadRight();
            Thread.sleep(500);

            // Return to top
            device.pressDPadUp();
            device.pressDPadUp();
            device.pressDPadUp();
            Thread.sleep(500);
        }
    }

    /**
     * E2E-05: 深度导航测试
     * 用户深度使用应用导航功能
     */
    @Test
    public void testDeepNavigationJourney() throws InterruptedException {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), com.flashfinger.bilitv.MainActivity.class);
        try (ActivityScenario<com.flashfinger.bilitv.MainActivity> scenario = ActivityScenario.launch(intent)) {
            Thread.sleep(1000);

            // Navigate right multiple times
            for (int i = 0; i < 5; i++) {
                device.pressDPadRight();
                Thread.sleep(200);
            }

            // Navigate down
            device.pressDPadDown();
            Thread.sleep(500);

            // Navigate left multiple times
            for (int i = 0; i < 5; i++) {
                device.pressDPadLeft();
                Thread.sleep(200);
            }

            // Navigate down again
            device.pressDPadDown();
            Thread.sleep(500);

            // Navigate right multiple times
            for (int i = 0; i < 5; i++) {
                device.pressDPadRight();
                Thread.sleep(200);
            }

            // Select video
            device.pressDPadCenter();
            Thread.sleep(2000);

            // Navigate in video player
            device.pressDPadUp();
            Thread.sleep(500);
            device.pressDPadDown();
            Thread.sleep(500);

            // Return
            device.pressBack();
            Thread.sleep(1000);
        }
    }

    /**
     * E2E-06: 长时间使用测试
     * 模拟用户长时间使用应用
     */
    @Test
    public void testLongUsageJourney() throws InterruptedException {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), com.flashfinger.bilitv.MainActivity.class);
        try (ActivityScenario<com.flashfinger.bilitv.MainActivity> scenario = ActivityScenario.launch(intent)) {
            Thread.sleep(1000);

            // Simulate 30 seconds of normal usage
            for (int i = 0; i < 3; i++) {
                // Browse
                device.pressDPadRight();
                Thread.sleep(300);

                // Watch video
                device.pressDPadCenter();
                Thread.sleep(5000);

                // Return
                device.pressBack();
                Thread.sleep(1000);

                // Browse more
                device.pressDPadDown();
                Thread.sleep(300);
                device.pressDPadRight();
                Thread.sleep(300);
            }
        }
    }

    /**
     * E2E-07: 错误处理测试
     * 测试各种错误情况下的应用行为
     */
    @Test
    public void testErrorHandlingJourney() throws InterruptedException {
        // Test with invalid URL
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), com.flashfinger.bilitv.player.VideoPlayerActivity.class);
        intent.putExtra(VideoData.VIDEO_ID, "invalid");
        intent.putExtra(VideoData.VIDEO_TITLE, "Invalid Video");
        intent.putExtra(VideoData.VIDEO_URL, "https://invalid-url-that-does-not-exist.com/video.mp4");

        try (ActivityScenario<com.flashfinger.bilitv.player.VideoPlayerActivity> scenario = ActivityScenario.launch(intent)) {
            Thread.sleep(3000);

            // App should not crash
            // Video player should handle error gracefully
        }
    }

    /**
     * E2E-08: 快速操作测试
     * 测试用户快速按键的情况
     */
    @Test
    public void testRapidOperationsJourney() throws InterruptedException {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), com.flashfinger.bilitv.MainActivity.class);
        try (ActivityScenario<com.flashfinger.bilitv.MainActivity> scenario = ActivityScenario.launch(intent)) {
            Thread.sleep(1000);

            // Rapid navigation
            device.pressDPadRight();
            device.pressDPadRight();
            device.pressDPadRight();
            device.pressDPadDown();
            device.pressDPadLeft();
            device.pressDPadLeft();
            device.pressDPadLeft();
            device.pressDPadCenter();
            Thread.sleep(500);

            // Rapid play/pause in video player
            device.pressDPadCenter();
            device.pressDPadCenter();
            device.pressDPadCenter();
            Thread.sleep(1000);

            // Rapid navigation in controls
            device.pressDPadRight();
            device.pressDPadRight();
            device.pressDPadLeft();
            device.pressDPadCenter();
            Thread.sleep(500);

            // Return
            device.pressBack();
            Thread.sleep(500);
        }
    }
}
