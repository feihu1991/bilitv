package com.flashfinger.bilitv.data;

import java.io.Serializable;

/**
 * Video Data Model for BiliTV
 * Supports both video and live stream data from Bilibili API
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
    private String quality; // "4K", "1080P", "720P", "LIVE", etc.

    // 新增字段，用于更好地匹配 Bilibili API
    private String aid; // 稿件ID（旧版）
    private String bvid; // 稿件ID（新版）
    private String cid; // 分P ID
    private String tid; // 分区ID
    private String tname; // 分区名称
    private long publishTime; // 发布时间戳
    private int likeCount; // 点赞数
    private int coinCount; // 投币数
    private int favoriteCount; // 收藏数
    private int danmakuCount; // 弹幕数
    private String area; // 直播分区
    private String areaName; // 直播分区名称
    private String tags; // 标签
    private String liveStatus; // 直播状态（0：未开播，1：直播中）
    private String roomUrl; // 直播房间URL

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

    /**
     * 创建视频数据的便捷方法
     */
    public static VideoData createVideo(String bvid, String title, String description, String coverUrl,
                                        String videoUrl, String danmakuUrl, String duration,
                                        int viewCount, String uploader, boolean is4K) {
        VideoData video = new VideoData();
        video.setId(bvid);
        video.setBvid(bvid);
        video.setTitle(title);
        video.setDescription(description);
        video.setCoverUrl(coverUrl);
        video.setVideoUrl(videoUrl);
        video.setDanmakuUrl(danmakuUrl);
        video.setDuration(duration);
        video.setViewCount(viewCount);
        video.setUploader(uploader);
        video.setLiveStream(false);
        video.set4K(is4K);
        video.setQuality(is4K ? "4K" : "1080P");
        return video;
    }

    /**
     * 创建直播数据的便捷方法
     */
    public static VideoData createLive(String roomId, String title, String description, String coverUrl,
                                       String liveUrl, int viewerCount, String uploader, String areaName) {
        VideoData live = new VideoData();
        live.setId(roomId);
        live.setTitle("【直播】" + title);
        live.setDescription(description);
        live.setCoverUrl(coverUrl);
        live.setVideoUrl(liveUrl);
        live.setDanmakuUrl(liveUrl);
        live.setDuration("LIVE");
        live.setViewCount(viewerCount);
        live.setUploader(uploader);
        live.setLiveStream(true);
        live.set4K(false);
        live.setQuality("LIVE");
        live.setAreaName(areaName);
        live.setLiveStatus("1");
        return live;
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

    // 新增字段的 getter/setter 方法

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getBvid() {
        return bvid;
    }

    public void setBvid(String bvid) {
        this.bvid = bvid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(long publishTime) {
        this.publishTime = publishTime;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getCoinCount() {
        return coinCount;
    }

    public void setCoinCount(int coinCount) {
        this.coinCount = coinCount;
    }

    public int getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(int favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public int getDanmakuCount() {
        return danmakuCount;
    }

    public void setDanmakuCount(int danmakuCount) {
        this.danmakuCount = danmakuCount;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getLiveStatus() {
        return liveStatus;
    }

    public void setLiveStatus(String liveStatus) {
        this.liveStatus = liveStatus;
    }

    public String getRoomUrl() {
        return roomUrl;
    }

    public void setRoomUrl(String roomUrl) {
        this.roomUrl = roomUrl;
    }
}
