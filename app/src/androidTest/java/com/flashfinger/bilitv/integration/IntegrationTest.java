package com.flashfinger.bilitv.integration;

import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiSelector;

import com.flashfinger.bilitv.data.VideoData;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Integration test suite
 * Tests multiple components working together
 */
@RunWith(AndroidJUnit4.class)
public class IntegrationTest {

    private UiDevice device;

    @Before
    public void setUp() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    }

    /**
     * TC-27: 主界面到视频播放器完整流程测试
     */
    @Test
    public void testCompleteUserFlow() throws InterruptedException {
        // 1. Launch MainActivity
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), com.flashfinger.bilitv.MainActivity.class);
        try (ActivityScenario<com.flashfinger.bilitv.MainActivity> scenario = ActivityScenario.launch(intent)) {
            Thread.sleep(1000);

            // 2. Navigate to a video using remote control
            device.pressDPadRight();
            Thread.sleep(500);
            device.pressDPadRight();
            Thread.sleep(500);

            // 3. Select video
            device.pressDPadCenter();
            Thread.sleep(1000);

            // 4. Verify video player is launched
            String currentActivity = device.getCurrentActivityName();
            assertTrue("VideoPlayerActivity should be launched",
                    currentActivity.contains("VideoPlayerActivity"));
        }
    }

    /**
     * TC-28: 直播流程集成测试
     */
    @Test
    public void testLiveStreamFlow() throws InterruptedException {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), com.flashfinger.bilitv.MainActivity.class);
        try (ActivityScenario<com.flashfinger.bilitv.MainActivity> scenario = ActivityScenario.launch(intent)) {
            Thread.sleep(1000);

            // Navigate to live stream category
            device.pressDPadDown();
            device.pressDPadDown();
            Thread.sleep(500);

            // Select live stream
            device.pressDPadCenter();
            Thread.sleep(1000);

            // Verify live player is launched
            String currentActivity = device.getCurrentActivityName();
            assertTrue("LivePlayerActivity should be launched",
                    currentActivity.contains("LivePlayerActivity"));
        }
    }

    /**
     * TC-29: 视频播放和弹幕集成测试
     */
    @Test
    public void testVideoPlaybackWithDanmaku() throws InterruptedException {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), com.flashfinger.bilitv.player.VideoPlayerActivity.class);
        intent.putExtra(VideoData.VIDEO_ID, "1");
        intent.putExtra(VideoData.VIDEO_TITLE, "Test Video");
        intent.putExtra(VideoData.VIDEO_URL, "https://sf1-hscdn-tos.pstatp.com/obj/media-fe/xgplayer_doc_video/mp4/xgplayer-demo-720p.mp4");
        intent.putExtra(VideoData.VIDEO_DANMAKU_URL, "test");

        try (ActivityScenario<com.flashfinger.bilitv.player.VideoPlayerActivity> scenario = ActivityScenario.launch(intent)) {
            Thread.sleep(3000);

            // Verify video is playing
            onView(withId(R.id.player_view))
                    .check(matches(isDisplayed()));

            // Verify danmaku container is displayed
            onView(withId(R.id.danmaku_container))
                    .check(matches(isDisplayed()));

            // Verify controls are displayed
            device.pressDPadUp();
            Thread.sleep(500);
            onView(withId(R.id.controls_view))
                    .check(matches(isDisplayed()));
        }
    }

    /**
     * TC-30: 播放控制集成测试
     */
    @Test
    public void testPlaybackControlsIntegration() throws InterruptedException {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), com.flashfinger.bilitv.player.VideoPlayerActivity.class);
        intent.putExtra(VideoData.VIDEO_ID, "1");
        intent.putExtra(VideoData.VIDEO_TITLE, "Test Video");
        intent.putExtra(VideoData.VIDEO_URL, "https://sf1-hscdn-tos.pstatp.com/obj/media-fe/xgplayer_doc_video/mp4/xgplayer-demo-720p.mp4");

        try (ActivityScenario<com.flashfinger.bilitv.player.VideoPlayerActivity> scenario = ActivityScenario.launch(intent)) {
            Thread.sleep(2000);

            // Test play/pause
            device.pressDPadCenter();
            Thread.sleep(1000);
            device.pressDPadCenter();
            Thread.sleep(1000);

            // Test controls display
            device.pressDPadDown();
            Thread.sleep(500);

            // Verify controls are visible
            UiObject danmakuToggle = device.findObject(new UiSelector()
                    .resourceId("com.flashfinger.bilitv:id/danmaku_toggle"));
            assertTrue("Danmaku toggle should be visible", danmakuToggle.exists());

            UiObject qualityButton = device.findObject(new UiSelector()
                    .resourceId("com.flashfinger.bilitv:id/quality_button"));
            assertTrue("Quality button should be visible", qualityButton.exists());
        }
    }

    /**
     * TC-31: 返回导航集成测试
     */
    @Test
    public void testBackNavigationIntegration() throws InterruptedException {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), com.flashfinger.bilitv.MainActivity.class);
        try (ActivityScenario<com.flashfinger.bilitv.MainActivity> scenario = ActivityScenario.launch(intent)) {
            Thread.sleep(1000);

            // Navigate to video player
            device.pressDPadRight();
            device.pressDPadCenter();
            Thread.sleep(2000);

            // Press back
            device.pressBack();
            Thread.sleep(1000);

            // Verify we're back to MainActivity or exited
            String currentActivity = device.getCurrentActivityName();
            assertNotNull("Current activity should not be null", currentActivity);
        }
    }

    /**
     * TC-32: 4K视频播放集成测试
     */
    @Test
    public void test4KVideoPlayback() throws InterruptedException {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), com.flashfinger.bilitv.MainActivity.class);
        try (ActivityScenario<com.flashfinger.bilitv.MainActivity> scenario = ActivityScenario.launch(intent)) {
            Thread.sleep(1000);

            // Navigate to 4K category
            device.pressDPadDown();
            Thread.sleep(500);

            // Select 4K video
            device.pressDPadCenter();
            Thread.sleep(2000);

            // Verify video player launched
            String currentActivity = device.getCurrentActivityName();
            assertTrue("VideoPlayerActivity should be launched",
                    currentActivity.contains("VideoPlayerActivity"));

            // Check if 4K badge is displayed
            device.pressDPadUp();
            Thread.sleep(500);
            UiObject qualityButton = device.findObject(new UiSelector()
                    .resourceId("com.flashfinger.bilitv:id/quality_button"));
            assertTrue("Quality button should be visible", qualityButton.exists());
        }
    }
}
