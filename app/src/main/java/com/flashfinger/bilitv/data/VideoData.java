package com.flashfinger.bilitv.data;

import java.io.Serializable;

/**
 * Video Data Model for BiliTV
 */
public class VideoData implements Serializable {
    public static final String VIDEO_ID = "video_id";
    public static final String VIDEO_TITLE = "video_title";
    public static final String VIDEO_DESCRIPTION = "video_description";
    public static final String VIDEO_URL = "video_url";
    public static final String VIDEO_COVER = "video_cover";
    public static final String VIDEO_DANMAKU_URL = "video_danmaku_url";

    private String id;
    private String title;
    private String description;
    private String coverUrl;
    private String videoUrl;
    private String danmakuUrl;
    private String duration;
    private int viewCount;
    private String uploader;
    private boolean isLiveStream;
    private boolean is4K;
    private String quality; // "4K", "1080P", "720P", etc.

    public VideoData() {
    }

    public VideoData(String id, String title, String description, String coverUrl,
                     String videoUrl, String danmakuUrl, String duration,
                     int viewCount, String uploader, boolean isLiveStream, boolean is4K) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.coverUrl = coverUrl;
        this.videoUrl = videoUrl;
        this.danmakuUrl = danmakuUrl;
        this.duration = duration;
        this.viewCount = viewCount;
        this.uploader = uploader;
        this.isLiveStream = isLiveStream;
        this.is4K = is4K;
        this.quality = is4K ? "4K" : "1080P";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getDanmakuUrl() {
        return danmakuUrl;
    }

    public void setDanmakuUrl(String danmakuUrl) {
        this.danmakuUrl = danmakuUrl;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public String getUploader() {
        return uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

    public boolean isLiveStream() {
        return isLiveStream;
    }

    public void setLiveStream(boolean liveStream) {
        isLiveStream = liveStream;
    }

    public boolean is4K() {
        return is4K;
    }

    public void set4K(boolean is4K) {
        is4K = is4K;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }
}
