package com.flashfinger.bilitv;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.flashfinger.bilitv.data.VideoData;
import com.flashfinger.bilitv.data.VideoDataFactory;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Automated test for VideoData model
 * Tests video data properties, setters/getters, and API integration
 */
@RunWith(AndroidJUnit4.class)
public class VideoDataTest {

    private VideoData videoData;

    @Before
    public void setUp() {
        videoData = new VideoData();
        // 使用模拟数据进行测试
        VideoDataFactory.setUseMockData(true);
    }

    @Test
    public void testVideoDataCreation() {
        assertNotNull("VideoData should be created", videoData);
    }

    @Test
    public void testVideoDataWithConstructor() {
        VideoData video = new VideoData(
                "test_id",
                "Test Title",
                "Test Description",
                "http://example.com/cover.jpg",
                "http://example.com/video.mp4",
                "http://example.com/danmaku.xml",
                "10:30",
                1000000,
                "Test Uploader",
                false,
                true
        );

        assertEquals("Id should match", "test_id", video.getId());
        assertEquals("Title should match", "Test Title", video.getTitle());
        assertEquals("Description should match", "Test Description", video.getDescription());
        assertEquals("Cover URL should match", "http://example.com/cover.jpg", video.getCoverUrl());
        assertEquals("Video URL should match", "http://example.com/video.mp4", video.getVideoUrl());
        assertEquals("Danmaku URL should match", "http://example.com/danmaku.xml", video.getDanmakuUrl());
        assertEquals("Duration should match", "10:30", video.getDuration());
        assertEquals("View count should match", 1000000, video.getViewCount());
        assertEquals("Uploader should match", "Test Uploader", video.getUploader());
        assertFalse("Should not be live stream", video.isLiveStream());
        assertTrue("Should be 4K", video.is4K());
    }

    @Test
    public void testVideoDataWithFactoryMethods() {
        // 测试创建视频的便捷方法
        VideoData video = VideoData.createVideo(
                "BV1GJ411x7h7",
                "Test Video",
                "Test Description",
                "http://example.com/cover.jpg",
                "http://example.com/video.mp4",
                "http://example.com/danmaku.xml",
                "10:30",
                1000000,
                "Test Uploader",
                true
        );

        assertEquals("BVID should match", "BV1GJ411x7h7", video.getBvid());
        assertEquals("Title should match", "Test Video", video.getTitle());
        assertEquals("Should be 4K", true, video.is4K());
        assertEquals("Quality should be 4K", "4K", video.getQuality());
        assertFalse("Should not be live stream", video.isLiveStream());

        // 测试创建直播的便捷方法
        VideoData live = VideoData.createLive(
                "22477958",
                "Test Live",
                "Test Live Description",
                "http://example.com/live_cover.jpg",
                "https://live.bilibili.com/22477958",
                5000,
                "Test Streamer",
                "Game"
        );

        assertEquals("Room ID should match", "22477958", live.getId());
        assertEquals("Title should contain live prefix", true, live.getTitle().contains("【直播】"));
        assertEquals("Should be live stream", true, live.isLiveStream());
        assertEquals("Quality should be LIVE", "LIVE", live.getQuality());
        assertEquals("Area name should match", "Game", live.getAreaName());
    }

    @Test
    public void testSetId() {
        videoData.setId("123");
        assertEquals("Id should be set", "123", videoData.getId());
    }

    @Test
    public void testSetTitle() {
        videoData.setTitle("New Title");
        assertEquals("Title should be set", "New Title", videoData.getTitle());
    }

    @Test
    public void testSetDescription() {
        videoData.setDescription("New Description");
        assertEquals("Description should be set", "New Description", videoData.getDescription());
    }

    @Test
    public void testSetCoverUrl() {
        videoData.setCoverUrl("http://example.com/new_cover.jpg");
        assertEquals("Cover URL should be set", "http://example.com/new_cover.jpg", videoData.getCoverUrl());
    }

    @Test
    public void testSetVideoUrl() {
        videoData.setVideoUrl("http://example.com/new_video.mp4");
        assertEquals("Video URL should be set", "http://example.com/new_video.mp4", videoData.getVideoUrl());
    }

    @Test
    public void testSetDanmakuUrl() {
        videoData.setDanmakuUrl("http://example.com/new_danmaku.xml");
        assertEquals("Danmaku URL should be set", "http://example.com/new_danmaku.xml", videoData.getDanmakuUrl());
    }

    @Test
    public void testSetDuration() {
        videoData.setDuration("15:20");
        assertEquals("Duration should be set", "15:20", videoData.getDuration());
    }

    @Test
    public void testSetViewCount() {
        videoData.setViewCount(5000);
        assertEquals("View count should be set", 5000, videoData.getViewCount());
    }

    @Test
    public void testSetUploader() {
        videoData.setUploader("Test User");
        assertEquals("Uploader should be set", "Test User", videoData.getUploader());
    }

    @Test
    public void testSetLiveStream() {
        videoData.setLiveStream(true);
        assertTrue("Should be live stream", videoData.isLiveStream());

        videoData.setLiveStream(false);
        assertFalse("Should not be live stream", videoData.isLiveStream());
    }

    @Test
    public void testSet4K() {
        videoData.set4K(true);
        assertTrue("Should be 4K", videoData.is4K());
        assertEquals("Quality should be 4K", "4K", videoData.getQuality());

        videoData.set4K(false);
        assertFalse("Should not be 4K", videoData.is4K());
    }

    @Test
    public void testSetQuality() {
        videoData.setQuality("1080P");
        assertEquals("Quality should be set", "1080P", videoData.getQuality());
    }

    @Test
    public void testNewFields() {
        // 测试新增字段
        videoData.setAid("123456");
        assertEquals("AID should be set", "123456", videoData.getAid());

        videoData.setBvid("BV1GJ411x7h7");
        assertEquals("BVID should be set", "BV1GJ411x7h7", videoData.getBvid());

        videoData.setCid("789012");
        assertEquals("CID should be set", "789012", videoData.getCid());

        videoData.setTid("33");
        assertEquals("TID should be set", "33", videoData.getTid());

        videoData.setTname("游戏");
        assertEquals("TName should be set", "游戏", videoData.getTname());

        videoData.setPublishTime(1704067200L);
        assertEquals("Publish time should be set", 1704067200L, videoData.getPublishTime());

        videoData.setLikeCount(1000);
        assertEquals("Like count should be set", 1000, videoData.getLikeCount());

        videoData.setCoinCount(500);
        assertEquals("Coin count should be set", 500, videoData.getCoinCount());

        videoData.setFavoriteCount(200);
        assertEquals("Favorite count should be set", 200, videoData.getFavoriteCount());

        videoData.setDanmakuCount(300);
        assertEquals("Danmaku count should be set", 300, videoData.getDanmakuCount());

        videoData.setArea("game");
        assertEquals("Area should be set", "game", videoData.getArea());

        videoData.setAreaName("游戏");
        assertEquals("Area name should be set", "游戏", videoData.getAreaName());

        videoData.setTags("游戏,直播,热门");
        assertEquals("Tags should be set", "游戏,直播,热门", videoData.getTags());

        videoData.setLiveStatus("1");
        assertEquals("Live status should be set", "1", videoData.getLiveStatus());

        videoData.setRoomUrl("https://live.bilibili.com/123456");
        assertEquals("Room URL should be set", "https://live.bilibili.com/123456", videoData.getRoomUrl());
    }

    @Test
    public void testConstants() {
        assertEquals("VIDEO_ID constant", "video_id", VideoData.VIDEO_ID);
        assertEquals("VIDEO_TITLE constant", "video_title", VideoData.VIDEO_TITLE);
        assertEquals("VIDEO_DESCRIPTION constant", "video_description", VideoData.VIDEO_DESCRIPTION);
        assertEquals("VIDEO_URL constant", "video_url", VideoData.VIDEO_URL);
        assertEquals("VIDEO_COVER constant", "video_cover", VideoData.VIDEO_COVER);
        assertEquals("VIDEO_DANMAKU_URL constant", "video_danmaku_url", VideoData.VIDEO_DANMAKU_URL);
    }

    @Test
    public void testGetRecommendedVideos() {
        List<VideoData> videos = VideoDataFactory.getRecommendedVideos();
        assertNotNull("Recommended videos should not be null", videos);
        assertTrue("Should have at least one recommended video", videos.size() > 0);

        VideoData firstVideo = videos.get(0);
        assertNotNull("First video should not be null", firstVideo);
        assertNotNull("First video should have ID", firstVideo.getId());
        assertNotNull("First video should have title", firstVideo.getTitle());
        assertNotNull("First video should have cover URL", firstVideo.getCoverUrl());
    }

    @Test
    public void testGet4KVideos() {
        List<VideoData> videos = VideoDataFactory.get4KVideos();
        assertNotNull("4K videos should not be null", videos);
        assertTrue("Should have at least one 4K video", videos.size() > 0);

        for (VideoData video : videos) {
            assertTrue("All videos should be 4K", video.is4K());
            assertEquals("Quality should be 4K", "4K", video.getQuality());
        }
    }

    @Test
    public void testGetLiveStreams() {
        List<VideoData> videos = VideoDataFactory.getLiveStreams();
        assertNotNull("Live streams should not be null", videos);
        assertTrue("Should have at least one live stream", videos.size() > 0);

        VideoData firstLive = videos.get(0);
        assertNotNull("First live stream should not be null", firstLive);
        assertTrue("First live stream should be live", firstLive.isLiveStream());
        assertEquals("Quality should be LIVE", "LIVE", firstLive.getQuality());
        assertTrue("Title should contain live prefix", firstLive.getTitle().contains("【直播】"));
    }

    @Test
    public void testGetPopularVideos() {
        List<VideoData> videos = VideoDataFactory.getPopularVideos();
        assertNotNull("Popular videos should not be null", videos);
        assertTrue("Should have at least one popular video", videos.size() > 0);

        VideoData firstVideo = videos.get(0);
        assertNotNull("First popular video should not be null", firstVideo);
        assertTrue("First popular video should have high view count", firstVideo.getViewCount() > 1000000);
    }

    @Test
    public void testGetVideoInfo() {
        VideoData video = VideoDataFactory.getVideoInfo("BV1GJ411x7h7");
        assertNotNull("Video info should not be null", video);
        assertEquals("BVID should match", "BV1GJ411x7h7", video.getBvid());
        assertNotNull("Title should not be null", video.getTitle());
    }

    @Test
    public void testGetLiveRoomInfo() {
        VideoData live = VideoDataFactory.getLiveRoomInfo("22477958");
        assertNotNull("Live room info should not be null", live);
        assertEquals("Room ID should match", "22477958", live.getId());
        assertTrue("Should be live stream", live.isLiveStream());
    }

    @Test
    public void testCacheClear() {
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
}
