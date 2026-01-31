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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Automated test for VideoPlayerActivity
 * Tests video playback, controls, and danmaku
 * Uses real Bilibili API data for testing
 */
@RunWith(AndroidJUnit4.class)
public class VideoPlayerActivityTest {

    private UiDevice device;

    @Before
    public void setUp() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        // 使用模拟数据进行测试，避免网络请求
        VideoDataFactory.setUseMockData(true);
    }

    @Test
    public void testVideoPlayerLaunches() {
        // 从 API 获取视频数据
        List<VideoData> videos = VideoDataFactory.getRecommendedVideos();
        assertTrue("Should have videos", videos.size() > 0);

        VideoData videoData = videos.get(0);
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), com.flashfinger.bilitv.player.VideoPlayerActivity.class);
        intent.putExtra(VideoData.VIDEO_ID, videoData.getId());
        intent.putExtra(VideoData.VIDEO_TITLE, videoData.getTitle());
        intent.putExtra(VideoData.VIDEO_URL, videoData.getVideoUrl());

        try (ActivityScenario<com.flashfinger.bilitv.player.VideoPlayerActivity> scenario = ActivityScenario.launch(intent)) {
            scenario.onActivity(activity -> {
                assertNotNull(activity);
            });
        }
    }

    @Test
    public void testPlayPause() throws InterruptedException {
        // 从 API 获取视频数据
        List<VideoData> videos = VideoDataFactory.getRecommendedVideos();
        assertTrue("Should have videos", videos.size() > 0);

        VideoData videoData = videos.get(0);
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), com.flashfinger.bilitv.player.VideoPlayerActivity.class);
        intent.putExtra(VideoData.VIDEO_ID, videoData.getId());
        intent.putExtra(VideoData.VIDEO_TITLE, videoData.getTitle());
        intent.putExtra(VideoData.VIDEO_URL, videoData.getVideoUrl());

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
        // 从 API 获取视频数据
        List<VideoData> videos = VideoDataFactory.getRecommendedVideos();
        assertTrue("Should have videos", videos.size() > 0);

        VideoData videoData = videos.get(0);
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), com.flashfinger.bilitv.player.VideoPlayerActivity.class);
        intent.putExtra(VideoData.VIDEO_ID, videoData.getId());
        intent.putExtra(VideoData.VIDEO_TITLE, videoData.getTitle());
        intent.putExtra(VideoData.VIDEO_URL, videoData.getVideoUrl());

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
        // 从 API 获取视频数据
        List<VideoData> videos = VideoDataFactory.getRecommendedVideos();
        assertTrue("Should have videos", videos.size() > 0);

        VideoData videoData = videos.get(0);
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), com.flashfinger.bilitv.player.VideoPlayerActivity.class);
        intent.putExtra(VideoData.VIDEO_ID, videoData.getId());
        intent.putExtra(VideoData.VIDEO_TITLE, videoData.getTitle());
        intent.putExtra(VideoData.VIDEO_URL, videoData.getVideoUrl());
        intent.putExtra(VideoData.VIDEO_DANMAKU_URL, videoData.getDanmakuUrl());

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
        // 从 API 获取视频数据
        List<VideoData> videos = VideoDataFactory.getRecommendedVideos();
        assertTrue("Should have videos", videos.size() > 0);

        VideoData videoData = videos.get(0);
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), com.flashfinger.bilitv.player.VideoPlayerActivity.class);
        intent.putExtra(VideoData.VIDEO_ID, videoData.getId());
        intent.putExtra(VideoData.VIDEO_TITLE, videoData.getTitle());
        intent.putExtra(VideoData.VIDEO_URL, videoData.getVideoUrl());

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
        // 从 API 获取视频数据
        List<VideoData> videos = VideoDataFactory.getRecommendedVideos();
        assertTrue("Should have videos", videos.size() > 0);

        VideoData videoData = videos.get(0);
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), com.flashfinger.bilitv.player.VideoPlayerActivity.class);
        intent.putExtra(VideoData.VIDEO_ID, videoData.getId());
        intent.putExtra(VideoData.VIDEO_TITLE, videoData.getTitle());
        intent.putExtra(VideoData.VIDEO_DESCRIPTION, videoData.getDescription());
        intent.putExtra(VideoData.VIDEO_URL, videoData.getVideoUrl());

        try (ActivityScenario<com.flashfinger.bilitv.player.VideoPlayerActivity> scenario = ActivityScenario.launch(intent)) {
            Thread.sleep(2000);

            // Check if title is displayed correctly
            UiObject titleText = device.findObject(new UiSelector()
                    .resourceId("com.flashfinger.bilitv:id/title_text"));

            assertTrue("Title should be displayed", titleText.exists());
            String displayedTitle;
            try {
                displayedTitle = titleText.getText();
            } catch (androidx.test.uiautomator.UiObjectNotFoundException e) {
                displayedTitle = "";
            }
            assertTrue("Title should match", displayedTitle.contains(videoData.getTitle()));
        }
    }

    @Test
    public void testVideoDataFromApi() {
        // 测试从 API 获取视频数据
        List<VideoData> videos = VideoDataFactory.getRecommendedVideos();
        assertNotNull("Videos should not be null", videos);
        assertTrue("Should have at least one video", videos.size() > 0);

        VideoData firstVideo = videos.get(0);
        assertNotNull("First video should not be null", firstVideo);
        assertNotNull("Video should have ID", firstVideo.getId());
        assertNotNull("Video should have title", firstVideo.getTitle());
        assertNotNull("Video should have cover URL", firstVideo.getCoverUrl());
        assertFalse("Should not be live stream", firstVideo.isLiveStream());
    }
}
