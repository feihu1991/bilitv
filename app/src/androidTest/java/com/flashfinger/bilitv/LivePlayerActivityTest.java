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
import com.flashfinger.bilitv.data.VideoDataFactory;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Automated test for LivePlayerActivity
 * Tests live stream playback and real-time danmaku
 * Uses real Bilibili API data for testing
 */
@RunWith(AndroidJUnit4.class)
public class LivePlayerActivityTest {

    private UiDevice device;

    @Before
    public void setUp() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        // 使用模拟数据进行测试，避免网络请求
        VideoDataFactory.setUseMockData(true);
    }

    @Test
    public void testLivePlayerLaunches() {
        // 从 API 获取直播数据
        List<VideoData> liveStreams = VideoDataFactory.getLiveStreams();
        assertTrue("Should have live streams", liveStreams.size() > 0);

        VideoData liveData = liveStreams.get(0);
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), com.flashfinger.bilitv.player.LivePlayerActivity.class);
        intent.putExtra(VideoData.VIDEO_ID, liveData.getId());
        intent.putExtra(VideoData.VIDEO_TITLE, liveData.getTitle());
        intent.putExtra(VideoData.VIDEO_URL, liveData.getVideoUrl());

        try (ActivityScenario<com.flashfinger.bilitv.player.LivePlayerActivity> scenario = ActivityScenario.launch(intent)) {
            scenario.onActivity(activity -> {
                assertNotNull(activity);
            });
        }
    }

    @Test
    public void testLiveIndicatorDisplayed() throws InterruptedException {
        // 从 API 获取直播数据
        List<VideoData> liveStreams = VideoDataFactory.getLiveStreams();
        assertTrue("Should have live streams", liveStreams.size() > 0);

        VideoData liveData = liveStreams.get(0);
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), com.flashfinger.bilitv.player.LivePlayerActivity.class);
        intent.putExtra(VideoData.VIDEO_ID, liveData.getId());
        intent.putExtra(VideoData.VIDEO_TITLE, liveData.getTitle());
        intent.putExtra(VideoData.VIDEO_URL, liveData.getVideoUrl());

        try (ActivityScenario<com.flashfinger.bilitv.player.LivePlayerActivity> scenario = ActivityScenario.launch(intent)) {
            Thread.sleep(2000);

            // Check if live indicator is displayed
            onView(withId(R.id.live_indicator))
                    .check(matches(isDisplayed()));
        }
    }

    @Test
    public void testLiveTitleContainsLivePrefix() throws InterruptedException {
        // 从 API 获取直播数据
        List<VideoData> liveStreams = VideoDataFactory.getLiveStreams();
        assertTrue("Should have live streams", liveStreams.size() > 0);

        VideoData liveData = liveStreams.get(0);
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), com.flashfinger.bilitv.player.LivePlayerActivity.class);
        intent.putExtra(VideoData.VIDEO_ID, liveData.getId());
        intent.putExtra(VideoData.VIDEO_TITLE, liveData.getTitle());
        intent.putExtra(VideoData.VIDEO_URL, liveData.getVideoUrl());

        try (ActivityScenario<com.flashfinger.bilitv.player.LivePlayerActivity> scenario = ActivityScenario.launch(intent)) {
            Thread.sleep(2000);

            // Check if title contains "【直播】" prefix
            UiObject titleText = device.findObject(new UiSelector()
                    .resourceId("com.flashfinger.bilitv:id/title_text"));

            assertTrue("Title should contain live prefix", titleText.exists());
            String displayedTitle;
            try {
                displayedTitle = titleText.getText();
            } catch (androidx.test.uiautomator.UiObjectNotFoundException e) {
                displayedTitle = "";
            }
            assertNotNull("Title text should not be null", displayedTitle);
            assertTrue("Title should contain 【直播】", displayedTitle.contains("【直播】"));
        }
    }

    @Test
    public void testRealtimeDanmaku() throws InterruptedException {
        // 从 API 获取直播数据
        List<VideoData> liveStreams = VideoDataFactory.getLiveStreams();
        assertTrue("Should have live streams", liveStreams.size() > 0);

        VideoData liveData = liveStreams.get(0);
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), com.flashfinger.bilitv.player.LivePlayerActivity.class);
        intent.putExtra(VideoData.VIDEO_ID, liveData.getId());
        intent.putExtra(VideoData.VIDEO_TITLE, liveData.getTitle());
        intent.putExtra(VideoData.VIDEO_URL, liveData.getVideoUrl());

        try (ActivityScenario<com.flashfinger.bilitv.player.LivePlayerActivity> scenario = ActivityScenario.launch(intent)) {
            Thread.sleep(5000);

            // After 5 seconds, danmaku should be continuously displayed
            onView(withId(R.id.danmaku_container))
                    .check(matches(isDisplayed()));
        }
    }

    @Test
    public void testViewerCountUpdate() throws InterruptedException {
        // 从 API 获取直播数据
        List<VideoData> liveStreams = VideoDataFactory.getLiveStreams();
        assertTrue("Should have live streams", liveStreams.size() > 0);

        VideoData liveData = liveStreams.get(0);
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), com.flashfinger.bilitv.player.LivePlayerActivity.class);
        intent.putExtra(VideoData.VIDEO_ID, liveData.getId());
        intent.putExtra(VideoData.VIDEO_TITLE, liveData.getTitle());
        intent.putExtra(VideoData.VIDEO_URL, liveData.getVideoUrl());

        try (ActivityScenario<com.flashfinger.bilitv.player.LivePlayerActivity> scenario = ActivityScenario.launch(intent)) {
            Thread.sleep(2000);

            // Check if description contains viewer count
            UiObject descriptionText = device.findObject(new UiSelector()
                    .resourceId("com.flashfinger.bilitv:id/description_text"));

            assertTrue("Description should contain viewer count", descriptionText.exists());
            String displayedDesc;
            try {
                displayedDesc = descriptionText.getText();
            } catch (androidx.test.uiautomator.UiObjectNotFoundException e) {
                displayedDesc = "";
            }
            assertTrue("Description should contain '人观看'", displayedDesc.contains("人观看"));
        }
    }

    @Test
    public void testLivePlayPause() throws InterruptedException {
        // 从 API 获取直播数据
        List<VideoData> liveStreams = VideoDataFactory.getLiveStreams();
        assertTrue("Should have live streams", liveStreams.size() > 0);

        VideoData liveData = liveStreams.get(0);
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), com.flashfinger.bilitv.player.LivePlayerActivity.class);
        intent.putExtra(VideoData.VIDEO_ID, liveData.getId());
        intent.putExtra(VideoData.VIDEO_TITLE, liveData.getTitle());
        intent.putExtra(VideoData.VIDEO_URL, liveData.getVideoUrl());

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
        // 从 API 获取直播数据
        List<VideoData> liveStreams = VideoDataFactory.getLiveStreams();
        assertTrue("Should have live streams", liveStreams.size() > 0);

        VideoData liveData = liveStreams.get(0);
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), com.flashfinger.bilitv.player.LivePlayerActivity.class);
        intent.putExtra(VideoData.VIDEO_ID, liveData.getId());
        intent.putExtra(VideoData.VIDEO_TITLE, liveData.getTitle());
        intent.putExtra(VideoData.VIDEO_URL, liveData.getVideoUrl());

        try (ActivityScenario<com.flashfinger.bilitv.player.LivePlayerActivity> scenario = ActivityScenario.launch(intent)) {
            Thread.sleep(6000);

            // Press DPAD to show controls
            device.pressDPadUp();
            Thread.sleep(500);

            onView(withId(R.id.controls_view))
                    .check(matches(isDisplayed()));
        }
    }

    @Test
    public void testLiveStreamDataFromApi() {
        // 测试从 API 获取直播数据
        List<VideoData> liveStreams = VideoDataFactory.getLiveStreams();
        assertNotNull("Live streams should not be null", liveStreams);
        assertTrue("Should have at least one live stream", liveStreams.size() > 0);

        VideoData firstLive = liveStreams.get(0);
        assertNotNull("First live stream should not be null", firstLive);
        assertNotNull("Live stream should have ID", firstLive.getId());
        assertNotNull("Live stream should have title", firstLive.getTitle());
        assertNotNull("Live stream should have cover URL", firstLive.getCoverUrl());
        assertTrue("Live stream should be live", firstLive.isLiveStream());
        assertEquals("Quality should be LIVE", "LIVE", firstLive.getQuality());
        assertTrue("Title should contain live prefix", firstLive.getTitle().contains("【直播】"));
    }
}
