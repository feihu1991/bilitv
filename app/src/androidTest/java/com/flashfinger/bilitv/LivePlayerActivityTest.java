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
 * Automated test for LivePlayerActivity
 * Tests live stream playback and real-time danmaku
 */
@RunWith(AndroidJUnit4.class)
public class LivePlayerActivityTest {

    private UiDevice device;

    @Before
    public void setUp() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    }

    @Test
    public void testLivePlayerLaunches() {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), com.flashfinger.bilitv.player.LivePlayerActivity.class);
        intent.putExtra(VideoData.VIDEO_ID, "201");
        intent.putExtra(VideoData.VIDEO_TITLE, "Test Live Stream");
        intent.putExtra(VideoData.VIDEO_URL, "https://sf1-hscdn-tos.pstatp.com/obj/media-fe/xgplayer_doc_video/mp4/xgplayer-demo-720p.mp4");

        try (ActivityScenario<com.flashfinger.bilitv.player.LivePlayerActivity> scenario = ActivityScenario.launch(intent)) {
            scenario.onActivity(activity -> {
                assertNotNull(activity);
            });
        }
    }

    @Test
    public void testLiveIndicatorDisplayed() throws InterruptedException {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), com.flashfinger.bilitv.player.LivePlayerActivity.class);
        intent.putExtra(VideoData.VIDEO_ID, "201");
        intent.putExtra(VideoData.VIDEO_TITLE, "Test Live Stream");
        intent.putExtra(VideoData.VIDEO_URL, "https://sf1-hscdn-tos.pstatp.com/obj/media-fe/xgplayer_doc_video/mp4/xgplayer-demo-720p.mp4");

        try (ActivityScenario<com.flashfinger.bilitv.player.LivePlayerActivity> scenario = ActivityScenario.launch(intent)) {
            Thread.sleep(2000);

            // Check if live indicator is displayed
            onView(withId(R.id.live_indicator))
                    .check(matches(isDisplayed()));
        }
    }

    @Test
    public void testLiveTitleContainsLivePrefix() throws InterruptedException {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), com.flashfinger.bilitv.player.LivePlayerActivity.class);
        intent.putExtra(VideoData.VIDEO_ID, "201");
        intent.putExtra(VideoData.VIDEO_TITLE, "Test Stream");
        intent.putExtra(VideoData.VIDEO_URL, "https://sf1-hscdn-tos.pstatp.com/obj/media-fe/xgplayer_doc_video/mp4/xgplayer-demo-720p.mp4");

        try (ActivityScenario<com.flashfinger.bilitv.player.LivePlayerActivity> scenario = ActivityScenario.launch(intent)) {
            Thread.sleep(2000);

            // Check if title contains "【直播】" prefix
            UiObject titleText = device.findObject(new UiSelector()
                    .resourceId("com.flashfinger.bilitv:id/title_text"));

            assertTrue("Title should contain live prefix", titleText.exists());
            String displayedTitle = titleText.getText();
            assertTrue("Title should contain 【直播】", displayedTitle.contains("【直播】"));
        }
    }

    @Test
    public void testRealtimeDanmaku() throws InterruptedException {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), com.flashfinger.bilitv.player.LivePlayerActivity.class);
        intent.putExtra(VideoData.VIDEO_ID, "201");
        intent.putExtra(VideoData.VIDEO_TITLE, "Test Live Stream");
        intent.putExtra(VideoData.VIDEO_URL, "https://sf1-hscdn-tos.pstatp.com/obj/media-fe/xgplayer_doc_video/mp4/xgplayer-demo-720p.mp4");

        try (ActivityScenario<com.flashfinger.bilitv.player.LivePlayerActivity> scenario = ActivityScenario.launch(intent)) {
            Thread.sleep(5000);

            // After 5 seconds, danmaku should be continuously displayed
            onView(withId(R.id.danmaku_container))
                    .check(matches(isDisplayed()));
        }
    }

    @Test
    public void testViewerCountUpdate() throws InterruptedException {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), com.flashfinger.bilitv.player.LivePlayerActivity.class);
        intent.putExtra(VideoData.VIDEO_ID, "201");
        intent.putExtra(VideoData.VIDEO_TITLE, "Test Live Stream");
        intent.putExtra(VideoData.VIDEO_URL, "https://sf1-hscdn-tos.pstatp.com/obj/media-fe/xgplayer_doc_video/mp4/xgplayer-demo-720p.mp4");

        try (ActivityScenario<com.flashfinger.bilitv.player.LivePlayerActivity> scenario = ActivityScenario.launch(intent)) {
            Thread.sleep(2000);

            // Check if description contains viewer count
            UiObject descriptionText = device.findObject(new UiSelector()
                    .resourceId("com.flashfinger.bilitv:id/description_text"));

            assertTrue("Description should contain viewer count", descriptionText.exists());
            String displayedDesc = descriptionText.getText();
            assertTrue("Description should contain '人观看'", displayedDesc.contains("人观看"));
        }
    }

    @Test
    public void testLivePlayPause() throws InterruptedException {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), com.flashfinger.bilitv.player.LivePlayerActivity.class);
        intent.putExtra(VideoData.VIDEO_ID, "201");
        intent.putExtra(VideoData.VIDEO_TITLE, "Test Live Stream");
        intent.putExtra(VideoData.VIDEO_URL, "https://sf1-hscdn-tos.pstatp.com/obj/media-fe/xgplayer_doc_video/mp4/xgplayer-demo-720p.mp4");

        try (ActivityScenario<com.flashfinger.bilitv.player.LivePlayerActivity> scenario = ActivityScenario.launch(intent)) {
            Thread.sleep(2000);

            // Press DPAD_CENTER to pause
            device.pressDPadCenter();
            Thread.sleep(1000);

            // Press DPAD_CENTER again to resume
            device.pressDPadCenter();
            Thread.sleep(1000);
        }
    }

    @Test
    public void testLiveControlsDisplay() throws InterruptedException {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), com.flashfinger.bilitv.player.LivePlayerActivity.class);
        intent.putExtra(VideoData.VIDEO_ID, "201");
        intent.putExtra(VideoData.VIDEO_TITLE, "Test Live Stream");
        intent.putExtra(VideoData.VIDEO_URL, "https://sf1-hscdn-tos.pstatp.com/obj/media-fe/xgplayer_doc_video/mp4/xgplayer-demo-720p.mp4");

        try (ActivityScenario<com.flashfinger.bilitv.player.LivePlayerActivity> scenario = ActivityScenario.launch(intent)) {
            Thread.sleep(6000);

            // Press DPAD to show controls
            device.pressDPadUp();
            Thread.sleep(500);

            onView(withId(R.id.controls_view))
                    .check(matches(isDisplayed()));
        }
    }
}
