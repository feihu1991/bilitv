package com.flashfinger.bilitv.data;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Bilibili API Client
 * Handles API requests to Bilibili's public endpoints
 */
public class BilibiliApiClient {

    private static final String TAG = "BilibiliApiClient";
    private static final String BASE_URL = "https://api.bilibili.com";

    // API Endpoints
    private static final String ENDPOINT_RECOMMENDED = "/x/web-interface/index/top/rcmd";
    private static final String ENDPOINT_POPULAR = "/x/web-interface/popular";
    private static final String ENDPOINT_LIVE_LIST = "/room/v1/Room/getList";
    private static final String ENDPOINT_VIDEO_INFO = "/x/web-interface/view";
    private static final String ENDPOINT_LIVE_INFO = "/room/v1/Room/get_info";
    private static final String ENDPOINT_DANMAKU = "/x/v1/dm/list.so";

    private final OkHttpClient client;
    private final Gson gson;

    public BilibiliApiClient() {
        this.client = new OkHttpClient.Builder()
                .connectTimeout(10, java.util.concurrent.TimeUnit.SECONDS)
                .readTimeout(10, java.util.concurrent.TimeUnit.SECONDS)
                .build();
        this.gson = new Gson();
    }

    /**
     * 获取推荐视频列表
     */
    public List<VideoData> getRecommendedVideos() {
        try {
            String url = BASE_URL + ENDPOINT_RECOMMENDED;
            Request request = new Request.Builder()
                    .url(url)
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                    .header("Referer", "https://www.bilibili.com")
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    String json = response.body().string();
                    return parseVideoList(json, false);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error fetching recommended videos", e);
        }
        return new ArrayList<>();
    }

    /**
     * 获取热门视频列表
     */
    public List<VideoData> getPopularVideos() {
        try {
            String url = BASE_URL + ENDPOINT_POPULAR;
            Request request = new Request.Builder()
                    .url(url)
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                    .header("Referer", "https://www.bilibili.com")
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    String json = response.body().string();
                    return parseVideoList(json, false);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error fetching popular videos", e);
        }
        return new ArrayList<>();
    }

    /**
     * 获取直播列表
     */
    public List<VideoData> getLiveStreams() {
        try {
            String url = BASE_URL + ENDPOINT_LIVE_LIST + "?page=1&page_size=20";
            Request request = new Request.Builder()
                    .url(url)
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                    .header("Referer", "https://live.bilibili.com")
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    String json = response.body().string();
                    return parseLiveList(json);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error fetching live streams", e);
        }
        return new ArrayList<>();
    }

    /**
     * 获取视频详细信息
     */
    public VideoData getVideoInfo(String aid) {
        try {
            String url = BASE_URL + ENDPOINT_VIDEO_INFO + "?bvid=" + aid;
            Request request = new Request.Builder()
                    .url(url)
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                    .header("Referer", "https://www.bilibili.com")
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    String json = response.body().string();
                    return parseVideoInfo(json);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error fetching video info", e);
        }
        return null;
    }

    /**
     * 获取直播房间信息
     */
    public VideoData getLiveRoomInfo(String roomId) {
        try {
            String url = BASE_URL + ENDPOINT_LIVE_INFO + "?room_id=" + roomId;
            Request request = new Request.Builder()
                    .url(url)
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                    .header("Referer", "https://live.bilibili.com")
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    String json = response.body().string();
                    return parseLiveRoomInfo(json);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error fetching live room info", e);
        }
        return null;
    }

    /**
     * 解析视频列表
     */
    private List<VideoData> parseVideoList(String json, boolean is4K) {
        List<VideoData> videos = new ArrayList<>();
        try {
            JsonObject root = JsonParser.parseString(json).getAsJsonObject();
            if (root.has("data") && root.get("data").isJsonObject()) {
                JsonObject data = root.getAsJsonObject("data");
                if (data.has("item") && data.get("item").isJsonArray()) {
                    JsonArray items = data.getAsJsonArray("item");
                    for (int i = 0; i < items.size(); i++) {
                        JsonObject item = items.get(i).getAsJsonObject();
                        VideoData video = new VideoData();

                        // 基本信息
                        video.setId(item.get("bvid").getAsString());
                        video.setTitle(item.get("title").getAsString());
                        video.setDescription(item.get("desc").getAsString());
                        video.setCoverUrl(item.get("pic").getAsString());

                        // 视频时长
                        int duration = item.get("duration").getAsInt();
                        video.setDuration(formatDuration(duration));

                        // 观看数
                        video.setViewCount(item.get("stat").getAsJsonObject().get("view").getAsInt());

                        // 上传者
                        video.setUploader(item.get("owner").getAsJsonObject().get("name").getAsString());

                        // 视频URL（需要进一步获取播放地址）
                        video.setVideoUrl("https://www.bilibili.com/video/" + video.getId());

                        // 弹幕URL
                        video.setDanmakuUrl("https://comment.bilibili.com/" + video.getId() + ".xml");

                        // 4K标识
                        video.set4K(is4K);
                        video.setQuality(is4K ? "4K" : "1080P");

                        // 直播标识
                        video.setLiveStream(false);

                        videos.add(video);
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error parsing video list", e);
        }
        return videos;
    }

    /**
     * 解析直播列表
     */
    private List<VideoData> parseLiveList(String json) {
        List<VideoData> videos = new ArrayList<>();
        try {
            JsonObject root = JsonParser.parseString(json).getAsJsonObject();
            if (root.has("data") && root.get("data").isJsonObject()) {
                JsonObject data = root.getAsJsonObject("data");
                if (data.has("list") && data.get("list").isJsonArray()) {
                    JsonArray items = data.getAsJsonArray("list");
                    for (int i = 0; i < items.size(); i++) {
                        JsonObject item = items.get(i).getAsJsonObject();
                        VideoData video = new VideoData();

                        // 基本信息
                        video.setId(item.get("roomid").getAsString());
                        video.setTitle("【直播】" + item.get("title").getAsString());
                        video.setDescription(item.get("area_name").getAsString() + " - " +
                                          item.get("tags").getAsString());
                        video.setCoverUrl(item.get("user_cover").getAsString());

                        // 直播信息
                        video.setDuration("LIVE");
                        video.setViewCount(item.get("online").getAsInt());
                        video.setUploader(item.get("uname").getAsString());

                        // 直播URL
                        video.setVideoUrl("https://live.bilibili.com/" + video.getId());

                        // 弹幕URL（直播弹幕使用不同的接口）
                        video.setDanmakuUrl("https://live.bilibili.com/" + video.getId());

                        // 直播标识
                        video.setLiveStream(true);
                        video.set4K(false);
                        video.setQuality("LIVE");

                        videos.add(video);
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error parsing live list", e);
        }
        return videos;
    }

    /**
     * 解析单个视频信息
     */
    private VideoData parseVideoInfo(String json) {
        try {
            JsonObject root = JsonParser.parseString(json).getAsJsonObject();
            if (root.has("data") && root.get("data").isJsonObject()) {
                JsonObject data = root.getAsJsonObject("data");
                VideoData video = new VideoData();

                // 基本信息
                video.setId(data.get("bvid").getAsString());
                video.setTitle(data.get("title").getAsString());
                video.setDescription(data.get("desc").getAsString());
                video.setCoverUrl(data.get("pic").getAsString());

                // 视频时长
                int duration = data.get("duration").getAsInt();
                video.setDuration(formatDuration(duration));

                // 观看数
                video.setViewCount(data.get("stat").getAsJsonObject().get("view").getAsInt());

                // 上传者
                video.setUploader(data.get("owner").getAsJsonObject().get("name").getAsString());

                // 视频URL（需要进一步获取播放地址）
                video.setVideoUrl("https://www.bilibili.com/video/" + video.getId());

                // 弹幕URL
                video.setDanmakuUrl("https://comment.bilibili.com/" + video.getId() + ".xml");

                // 4K标识（根据视频质量判断）
                video.set4K(false);
                video.setQuality("1080P");

                // 直播标识
                video.setLiveStream(false);

                return video;
            }
        } catch (Exception e) {
            Log.e(TAG, "Error parsing video info", e);
        }
        return null;
    }

    /**
     * 解析直播房间信息
     */
    private VideoData parseLiveRoomInfo(String json) {
        try {
            JsonObject root = JsonParser.parseString(json).getAsJsonObject();
            if (root.has("data") && root.get("data").isJsonObject()) {
                JsonObject data = root.getAsJsonObject("data");
                VideoData video = new VideoData();

                // 基本信息
                video.setId(data.get("room_id").getAsString());
                video.setTitle("【直播】" + data.get("title").getAsString());
                video.setDescription(data.get("area_name").getAsString());
                video.setCoverUrl(data.get("user_cover").getAsString());

                // 直播信息
                video.setDuration("LIVE");
                video.setViewCount(data.get("online").getAsInt());
                video.setUploader(data.get("uname").getAsString());

                // 直播URL
                video.setVideoUrl("https://live.bilibili.com/" + video.getId());

                // 弹幕URL
                video.setDanmakuUrl("https://live.bilibili.com/" + video.getId());

                // 直播标识
                video.setLiveStream(true);
                video.set4K(false);
                video.setQuality("LIVE");

                return video;
            }
        } catch (Exception e) {
            Log.e(TAG, "Error parsing live room info", e);
        }
        return null;
    }

    /**
     * 格式化时长（秒转MM:SS）
     */
    private String formatDuration(int seconds) {
        if (seconds <= 0) return "00:00";
        int minutes = seconds / 60;
        int secs = seconds % 60;
        return String.format("%02d:%02d", minutes, secs);
    }
}
