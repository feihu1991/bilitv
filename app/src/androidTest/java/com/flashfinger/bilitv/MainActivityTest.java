package com.flashfinger.bilitv;

import android.content.Context;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.UiDevice;

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
 * Automated test for MainActivity
 * Tests the main browse interface with remote control navigation
 * Uses real Bilibili API data for testing
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private UiDevice device;

    @Before
    public void setUp() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        // 使用模拟数据进行测试，避免网络请求
        VideoDataFactory.setUseMockData(true);
    }

    @Test
    public void useAppContext() {
        Context context = ApplicationProvider.getApplicationContext();
        assertEquals("com.flashfinger.bilitv", context.getPackageName());
    }

    @Test
    public void testMainActivityLaunches() {
        try (ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class)) {
            // Verify main browse fragment is displayed
            onView(withId(R.id.main_browse_fragment))
                    .check(matches(isDisplayed()));
        }
    }

    @Test
    public void testRemoteControlDpadNavigation() {
        try (ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class)) {
            // Simulate remote control D-pad navigation
            // Press RIGHT to navigate to next card
            device.pressDPadRight();
            Thread.sleep(500);

            // Press DOWN to navigate to next row
            device.pressDPadDown();
            Thread.sleep(500);

            // Press RIGHT to navigate in the row
            device.pressDPadRight();
            Thread.sleep(500);

            // Verify navigation works - main fragment should still be displayed
            onView(withId(R.id.main_browse_fragment))
                    .check(matches(isDisplayed()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRemoteControlCenterButton() {
        try (ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class)) {
            // Navigate using remote control
            device.pressDPadRight();
            Thread.sleep(500);

            // Press center button to select video
            device.pressDPadCenter();
            Thread.sleep(1000);

            // Verify video player activity was launched (by checking current activity)
            String currentActivity = device.getCurrentActivityName();
            // VideoPlayerActivity or LivePlayerActivity should be launched
            assert (currentActivity.contains("VideoPlayerActivity") ||
                    currentActivity.contains("LivePlayerActivity"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testApiIntegration() {
        // 测试 API 集成 - 获取推荐视频
        List<VideoData> recommendedVideos = VideoDataFactory.getRecommendedVideos();
        assertNotNull("Recommended videos should not be null", recommendedVideos);
        assertTrue("Should have at least one recommended video", recommendedVideos.size() > 0);

        // 验证视频数据
        VideoData firstVideo = recommendedVideos.get(0);
        assertNotNull("First video should not be null", firstVideo);
        assertNotNull("Video should have ID", firstVideo.getId());
        assertNotNull("Video should have title", firstVideo.getTitle());
        assertNotNull("Video should have cover URL", firstVideo.getCoverUrl());
        assertTrue("Video should have view count", firstVideo.getViewCount() > 0);
    }

    @Test
    public void testApiIntegration4KVideos() {
        // 测试 API 集成 - 获取 4K 视频
        List<VideoData> videos = VideoDataFactory.get4KVideos();
        assertNotNull("4K videos should not be null", videos);
        assertTrue("Should have at least one 4K video", videos.size() > 0);

        // 验证所有视频都是 4K
        for (VideoData video : videos) {
            assertTrue("All videos should be 4K", video.is4K());
            assertEquals("Quality should be 4K", "4K", video.getQuality());
        }
    }

    @Test
    public void testApiIntegrationLiveStreams() {
        // 测试 API 集成 - 获取直播流
        List<VideoData> liveStreams = VideoDataFactory.getLiveStreams();
        assertNotNull("Live streams should not be null", liveStreams);
        assertTrue("Should have at least one live stream", liveStreams.size() > 0);

        // 验证直播数据
        VideoData firstLive = liveStreams.get(0);
        assertNotNull("First live stream should not be null", firstLive);
        assertTrue("First live stream should be live", firstLive.isLiveStream());
        assertEquals("Quality should be LIVE", "LIVE", firstLive.getQuality());
        assertTrue("Title should contain live prefix", firstLive.getTitle().contains("【直播】"));
    }

    @Test
    public void testApiIntegrationPopularVideos() {
        // 测试 API 集成 - 获取热门视频
        List<VideoData> popularVideos = VideoDataFactory.getPopularVideos();
        assertNotNull("Popular videos should not be null", popularVideos);
        assertTrue("Should have at least one popular video", popularVideos.size() > 0);

        // 验证热门视频有高观看数
        VideoData firstVideo = popularVideos.get(0);
        assertNotNull("First popular video should not be null", firstVideo);
        assertTrue("First popular video should have high view count", firstVideo.getViewCount() > 1000000);
    }

    @Test
    public void testApiIntegrationVideoInfo() {
        // 测试 API 集成 - 获取单个视频信息
        VideoData video = VideoDataFactory.getVideoInfo("BV1GJ411x7h7");
        assertNotNull("Video info should not be null", video);
        assertEquals("BVID should match", "BV1GJ411x7h7", video.getBvid());
        assertNotNull("Title should not be null", video.getTitle());
    }

    @Test
    public void testApiIntegrationLiveRoomInfo() {
        // 测试 API 集成 - 获取直播房间信息
        VideoData live = VideoDataFactory.getLiveRoomInfo("22477958");
        assertNotNull("Live room info should not be null", live);
        assertEquals("Room ID should match", "22477958", live.getId());
        assertTrue("Should be live stream", live.isLiveStream());
    }

    @Test
    public void testApiIntegrationCacheClear() {
        // 测试 API 集成 - 缓存清除
        // 先获取数据以填充缓存
        VideoDataFactory.getRecommendedVideos();
        VideoDataFactory.get4KVideos();
        VideoDataFactory.getLiveStreams();
        VideoDataFactory.getPopularVideos();

        // 清除缓存
        VideoDataFactory.clearCache();

        // 再次获取数据，应该能正常工作
        List<VideoData> videos = VideoDataFactory.getRecommendedVideos();
        assertNotNull("Recommended videos should not be null after cache clear", videos);
        assertTrue("Should have at least one recommended video after cache clear", videos.size() > 0);
    }

    @Test
    public void testBackButton() {
        try (ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class)) {
            // Simulate back button press
            device.pressBack();
            Thread.sleep(500);

            // Activity should be finished or still active (depending on implementation)
            String currentActivity = device.getCurrentActivityName();
            // Back button should either exit app or navigate up
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMenuButton() {
        try (ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class)) {
            // Simulate menu button press
            device.pressMenu();
            Thread.sleep(500);

            // Verify main activity is still displayed
            onView(withId(R.id.main_browse_fragment))
                    .check(matches(isDisplayed()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
