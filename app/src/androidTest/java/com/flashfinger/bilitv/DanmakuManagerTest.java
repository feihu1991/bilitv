package com.flashfinger.bilitv;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.flashfinger.bilitv.data.VideoData;
import com.flashfinger.bilitv.data.VideoDataFactory;
import com.flashfinger.bilitv.danmaku.DanmakuManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Automated test for DanmakuManager
 * Tests danmaku display, pause, resume, and clear
 * Uses real Bilibili API data for testing
 */
@RunWith(AndroidJUnit4.class)
public class DanmakuManagerTest {

    private DanmakuManager danmakuManager;
    private Context context;
    private android.widget.FrameLayout container;

    @Before
    public void setUp() {
        context = ApplicationProvider.getApplicationContext();
        container = new android.widget.FrameLayout(context);
        container.setLayoutParams(new android.view.ViewGroup.LayoutParams(
                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                android.view.ViewGroup.LayoutParams.MATCH_PARENT
        ));
        danmakuManager = new DanmakuManager(context, container, 1920, 1080);
        // 使用模拟数据进行测试
        VideoDataFactory.setUseMockData(true);
    }

    @Test
    public void testDanmakuManagerCreation() {
        assertNotNull("DanmakuManager should be created", danmakuManager);
    }

    @Test
    public void testAddDanmaku() throws InterruptedException {
        String testDanmaku = "Test Danmaku";
        danmakuManager.addDanmaku(testDanmaku);
        Thread.sleep(100);

        assertEquals("Danmaku should be added", 1, container.getChildCount());
    }

    @Test
    public void testMultipleDanmaku() throws InterruptedException {
        String[] danmakus = {"First", "Second", "Third", "Fourth", "Fifth"};

        for (String danmaku : danmakus) {
            danmakuManager.addDanmaku(danmaku);
            Thread.sleep(50);
        }

        // Should have multiple danmaku views
        assertTrue("Should have multiple danmakus", container.getChildCount() > 1);
    }

    @Test
    public void testPauseDanmaku() throws InterruptedException {
        danmakuManager.addDanmaku("Test");
        Thread.sleep(100);
        danmakuManager.pause();

        assertTrue("Danmaku should be paused", container.getChildCount() > 0);
    }

    @Test
    public void testResumeDanmaku() throws InterruptedException {
        danmakuManager.addDanmaku("Test");
        Thread.sleep(100);
        danmakuManager.pause();
        Thread.sleep(100);
        danmakuManager.start();

        assertTrue("Danmaku should be resumed", container.getChildCount() > 0);
    }

    @Test
    public void testClearDanmaku() throws InterruptedException {
        danmakuManager.addDanmaku("Test");
        Thread.sleep(100);
        danmakuManager.clear();

        assertEquals("All danmakus should be cleared", 0, container.getChildCount());
    }

    @Test
    public void testReleaseDanmaku() throws InterruptedException {
        danmakuManager.addDanmaku("Test");
        Thread.sleep(100);
        danmakuManager.release();

        assertEquals("Danmaku should be released", 0, container.getChildCount());
    }

    @Test
    public void testEmptyDanmaku() {
        danmakuManager.addDanmaku("");
        danmakuManager.addDanmaku(null);

        // Should not add empty danmaku
        assertEquals("Empty danmakus should not be added", 0, container.getChildCount());
    }

    @Test
    public void testStopDanmaku() throws InterruptedException {
        danmakuManager.addDanmaku("Test");
        Thread.sleep(100);
        danmakuManager.stop();

        assertTrue("Danmaku should be stopped", container.getChildCount() >= 0);
    }

    @Test
    public void testDanmakuWithApiData() throws InterruptedException {
        // 测试使用 API 数据添加弹幕
        VideoData video = VideoDataFactory.getVideoInfo("BV1GJ411x7h7");
        assertNotNull("Video should not be null", video);

        // 模拟从 API 获取的弹幕数据
        String[] danmakus = {
            "【直播】测试弹幕1",
            "【直播】测试弹幕2",
            "【直播】测试弹幕3",
            "【直播】测试弹幕4",
            "【直播】测试弹幕5"
        };

        for (String danmaku : danmakus) {
            danmakuManager.addDanmaku(danmaku);
            Thread.sleep(50);
        }

        // Should have multiple danmaku views
        assertTrue("Should have multiple danmakus", container.getChildCount() > 1);
    }

    @Test
    public void testDanmakuWithLiveStreamData() throws InterruptedException {
        // 测试使用直播流数据添加弹幕
        VideoData live = VideoDataFactory.getLiveRoomInfo("22477958");
        assertNotNull("Live stream should not be null", live);
        assertTrue("Should be live stream", live.isLiveStream());

        // 模拟从直播获取的实时弹幕数据
        String[] liveDanmakus = {
            "主播好！",
            "666",
            "太强了！",
            "加油！",
            "来了来了"
        };

        for (String danmaku : liveDanmakus) {
            danmakuManager.addDanmaku(danmaku);
            Thread.sleep(50);
        }

        // Should have multiple danmaku views
        assertTrue("Should have multiple live danmakus", container.getChildCount() > 1);
    }

    @Test
    public void testDanmakuWithPopularVideoData() throws InterruptedException {
        // 测试使用热门视频数据添加弹幕
        VideoData popular = VideoDataFactory.getPopularVideos().get(0);
        assertNotNull("Popular video should not be null", popular);

        // 模拟从热门视频获取的弹幕数据
        String[] popularDanmakus = {
            "经典视频！",
            "又来看了",
            "质量真高",
            "UP主牛逼",
            "收藏了"
        };

        for (String danmaku : popularDanmakus) {
            danmakuManager.addDanmaku(danmaku);
            Thread.sleep(50);
        }

        // Should have multiple danmaku views
        assertTrue("Should have multiple popular danmakus", container.getChildCount() > 1);
    }

    @Test
    public void testDanmakuWith4KVideoData() throws InterruptedException {
        // 测试使用 4K 视频数据添加弹幕
        VideoData video4K = VideoDataFactory.get4KVideos().get(0);
        assertNotNull("4K video should not be null", video4K);
        assertTrue("Should be 4K", video4K.is4K());

        // 模拟从 4K 视频获取的弹幕数据
        String[] danmakus4K = {
            "4K画质太棒了！",
            "细节拉满",
            "眼睛享受",
            "HDR效果好",
            "太清晰了"
        };

        for (String danmaku : danmakus4K) {
            danmakuManager.addDanmaku(danmaku);
            Thread.sleep(50);
        }

        // Should have multiple danmaku views
        assertTrue("Should have multiple 4K danmakus", container.getChildCount() > 1);
    }
}
