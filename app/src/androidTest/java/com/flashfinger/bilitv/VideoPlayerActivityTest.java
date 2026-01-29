package com.flashfinger.bilitv;

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
 * Automated test for VideoPlayerActivity
 * Tests video playback, controls, and danmaku
 */
@RunWith(AndroidJUnit4.class)
public class VideoPlayerActivityTest {

    private UiDevice device;

    @Before
    public void setUp() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    }

    @Test
    public void testVideoPlayerLaunches() {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), com.flashfinger.bilitv.player.VideoPlayerActivity.class);
        intent.putExtra(VideoData.VIDEO_ID, "1");
        intent.putExtra(VideoData.VIDEO_TITLE, "Test Video");
        intent.putExtra(VideoData.VIDEO_URL, "https://sf1-hscdn-tos.pstatp.com/obj/media-fe/xgplayer_doc_video/mp4/xgplayer-demo-720p.mp4");

        try (ActivityScenario<com.flashfinger.bilitv.player.VideoPlayerActivity> scenario = ActivityScenario.launch(intent)) {
            scenario.onActivity(activity -> {
                assertNotNull(activity);
            });
        }
    }

    @Test
    public void testPlayPause() throws InterruptedException {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), com.flashfinger.bilitv.player.VideoPlayerActivity.class);
        intent.putExtra(VideoData.VIDEO_ID, "1");
        intent.putExtra(VideoData.VIDEO_TITLE, "Test Video");
        intent.putExtra(VideoData.VIDEO_URL, "https://sf1-hscdn-tos.pstatp.com/obj/media-fe/xgplayer_doc_video/mp4/xgplayer-demo-720p.mp4");

        try (ActivityScenario<com.flashfinger.bilitv.player.VideoPlayerActivity> scenario = ActivityScenario.launch(intent)) {
            Thread.sleep(2000);

            // Press DPAD_CENTER to toggle play/pause
            device.pressDPadCenter();
            Thread.sleep(1000);

            // Press DPAD_CENTER again to resume
            device.pressDPadCenter();
            Thread.sleep(1000);
        }
    }

    @Test
    public void testControlsDisplay() throws InterruptedException {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), com.flashfinger.bilitv.player.VideoPlayerActivity.class);
        intent.putExtra(VideoData.VIDEO_ID, "1");
        intent.putExtra(VideoData.VIDEO_TITLE, "Test Video");
        intent.putExtra(VideoData.VIDEO_URL, "https://sf1-hscdn-tos.pstatp.com/obj/media-fe/xgplayer_doc_video/mp4/xgplayer-demo-720p.mp4");

        try (ActivityScenario<com.flashfinger.bilitv.player.VideoPlayerActivity> scenario = ActivityScenario.launch(intent)) {
            Thread.sleep(6000); // Wait for auto-hide

            // Press any DPAD key to show controls
            device.pressDPadUp();
            Thread.sleep(500);

            // Check if controls view is displayed
            onView(withId(R.id.controls_view))
                    .check(matches(isDisplayed()));
        }
    }

    @Test
    public void testDanmakuDisplay() throws InterruptedException {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), com.flashfinger.bilitv.player.VideoPlayerActivity.class);
        intent.putExtra(VideoData.VIDEO_ID, "1");
        intent.putExtra(VideoData.VIDEO_TITLE, "Test Video");
        intent.putExtra(VideoData.VIDEO_URL, "https://sf1-hscdn-tos.pstatp.com/obj/media-fe/xgplayer_doc_video/mp4/xgplayer-demo-720p.mp4");
        intent.putExtra(VideoData.VIDEO_DANMAKU_URL, "test");

        try (ActivityScenario<com.flashfinger.bilitv.player.VideoPlayerActivity> scenario = ActivityScenario.launch(intent)) {
            Thread.sleep(3000);

            // Danmaku should be visible after 3 seconds
            // Check if danmaku container is displayed
            onView(withId(R.id.danmaku_container))
                    .check(matches(isDisplayed()));
        }
    }

    @Test
    public void testBackButton() throws InterruptedException {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), com.flashfinger.bilitv.player.VideoPlayerActivity.class);
        intent.putExtra(VideoData.VIDEO_ID, "1");
        intent.putExtra(VideoData.VIDEO_TITLE, "Test Video");
        intent.putExtra(VideoData.VIDEO_URL, "https://sf1-hscdn-tos.pstatp.com/obj/media-fe/xgplayer_doc_video/mp4/xgplayer-demo-720p.mp4");

        try (ActivityScenario<com.flashfinger.bilitv.player.VideoPlayerActivity> scenario = ActivityScenario.launch(intent)) {
            Thread.sleep(2000);

            // Press back button
            device.pressBack();
            Thread.sleep(500);

            // Activity should be finished
            String currentActivity = device.getCurrentActivityName();
            // Should return to MainActivity or exit
        }
    }

    @Test
    public void testVideoMetadata() throws InterruptedException {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), com.flashfinger.bilitv.player.VideoPlayerActivity.class);
        intent.putExtra(VideoData.VIDEO_ID, "1");
        intent.putExtra(VideoData.VIDEO_TITLE, "Test Video Title");
        intent.putExtra(VideoData.VIDEO_DESCRIPTION, "Test Description");
        intent.putExtra(VideoData.VIDEO_URL, "https://sf1-hscdn-tos.pstatp.com/obj/media-fe/xgplayer_doc_video/mp4/xgplayer-demo-720p.mp4");

        try (ActivityScenario<com.flashfinger.bilitv.player.VideoPlayerActivity> scenario = ActivityScenario.launch(intent)) {
            Thread.sleep(2000);

            // Check if title is displayed correctly
            UiObject titleText = device.findObject(new UiSelector()
                    .resourceId("com.flashfinger.bilitv:id/title_text"));

            assertTrue("Title should be displayed", titleText.exists());
            String displayedTitle = titleText.getText();
            assertTrue("Title should match", displayedTitle.contains("Test Video Title"));
        }
    }
}
