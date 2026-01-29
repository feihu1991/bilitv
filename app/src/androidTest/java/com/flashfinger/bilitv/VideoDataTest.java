package com.flashfinger.bilitv;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.flashfinger.bilitv.data.VideoData;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Automated test for VideoData model
 * Tests video data properties and setters/getters
 */
@RunWith(AndroidJUnit4.class)
public class VideoDataTest {

    private VideoData videoData;

    @Before
    public void setUp() {
        videoData = new VideoData();
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
    public void testConstants() {
        assertEquals("VIDEO_ID constant", "video_id", VideoData.VIDEO_ID);
        assertEquals("VIDEO_TITLE constant", "video_title", VideoData.VIDEO_TITLE);
        assertEquals("VIDEO_DESCRIPTION constant", "video_description", VideoData.VIDEO_DESCRIPTION);
        assertEquals("VIDEO_URL constant", "video_url", VideoData.VIDEO_URL);
        assertEquals("VIDEO_COVER constant", "video_cover", VideoData.VIDEO_COVER);
        assertEquals("VIDEO_DANMAKU_URL constant", "video_danmaku_url", VideoData.VIDEO_DANMAKU_URL);
    }
}
