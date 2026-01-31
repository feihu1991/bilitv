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
 * Based on bilibili-API-collect documentation
 */
public class BilibiliApiClient {

    private static final String TAG = "BilibiliApiClient";
    private static final String BASE_URL = "https://api.bilibili.com";
    private static final String LIVE_BASE_URL = "https://api.live.bilibili.com";

    // API Endpoints (根据 bilibili-API-collect 文档)
    private static final String ENDPOINT_RECOMMENDED = "/x/web-interface/archive/related";
    private static final String ENDPOINT_POPULAR = "/x/web-interface/popular";
    private static final String ENDPOINT_LIVE_RECOMMEND = "/xlive/web-interface/v1/webMain/getMoreRecList";
    private static final String ENDPOINT_LIVE_INFO = "/room/v1/Room/get_info";
    private static final String ENDPOINT_VIDEO_INFO = "/x/web-interface/view";
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
     * API: https://api.bilibili.com/x/web-interface/archive/related
     * 需要 bvid 或 aid 参数
     */
    public List<VideoData> getRecommendedVideos() {
        try {
            // 使用热门视频作为推荐（因为推荐视频需要指定视频ID）
            String url = BASE_URL + ENDPOINT_POPULAR + "?ps=20&pn=1";
            Request request = new Request.Builder()
                    .url(url)
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                    .header("Referer", "https://www.bilibili.com")
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    String json = response.body().string();
                    return parsePopularVideoList(json);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error fetching recommended videos", e);
        }
        return new ArrayList<>();
    }

    /**
     * 获取热门视频列表
     * API: https://api.bilibili.com/x/web-interface/popular
     * 参数: pn(页码), ps(每页项数，默认20)
     */
    public List<VideoData> getPopularVideos() {
        try {
            String url = BASE_URL + ENDPOINT_POPULAR + "?ps=20&pn=1";
            Request request = new Request.Builder()
                    .url(url)
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                    .header("Referer", "https://www.bilibili.com")
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    String json = response.body().string();
                    return parsePopularVideoList(json);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error fetching popular videos", e);
        }
        return new ArrayList<>();
    }

    /**
     * 获取直播推荐列表
     * API: https://api.live.bilibili.com/xlive/web-interface/v1/webMain/getMoreRecList
     * 参数: platform(平台类型，必填)
     */
    public List<VideoData> getLiveStreams() {
        try {
            String url = LIVE_BASE_URL + ENDPOINT_LIVE_RECOMMEND + "?platform=web";
            Request request = new Request.Builder()
                    .url(url)
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                    .header("Referer", "https://live.bilibili.com")
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    String json = response.body().string();
                    return parseLiveRecommendList(json);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error fetching live streams", e);
        }
        return new ArrayList<>();
    }

    /**
     * 获取视频详细信息
     * API: https://api.bilibili.com/x/web-interface/view
     * 参数: bvid 或 aid (二选一)
     */
    public VideoData getVideoInfo(String bvid) {
        try {
            String url = BASE_URL + ENDPOINT_VIDEO_INFO + "?bvid=" + bvid;
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
     * API: https://api.live.bilibili.com/room/v1/Room/get_info
     * 参数: room_id (直播间ID)
     */
    public VideoData getLiveRoomInfo(String roomId) {
        try {
            String url = LIVE_BASE_URL + ENDPOINT_LIVE_INFO + "?room_id=" + roomId;
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
     * 解析热门视频列表
     * API响应结构: {code: 0, data: {list: [...], no_more: false}}
     */
    private List<VideoData> parsePopularVideoList(String json) {
        List<VideoData> videos = new ArrayList<>();
        try {
            JsonObject root = JsonParser.parseString(json).getAsJsonObject();
            if (root.has("code") && root.get("code").getAsInt() == 0) {
                JsonObject data = root.getAsJsonObject("data");
                if (data.has("list") && data.get("list").isJsonArray()) {
                    JsonArray items = data.getAsJsonArray("list");
                    for (int i = 0; i < items.size(); i++) {
                        JsonObject item = items.get(i).getAsJsonObject();
                        VideoData video = parseVideoData(item);
                        if (video != null) {
                            videos.add(video);
                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error parsing popular video list", e);
        }
        return videos;
    }

    /**
     * 解析直播推荐列表
     * API响应结构: {code: 0, data: {recommend_room_list: [...], top_room_id: ...}}
     */
    private List<VideoData> parseLiveRecommendList(String json) {
        List<VideoData> videos = new ArrayList<>();
        try {
            JsonObject root = JsonParser.parseString(json).getAsJsonObject();
            if (root.has("code") && root.get("code").getAsInt() == 0) {
                JsonObject data = root.getAsJsonObject("data");
                if (data.has("recommend_room_list") && data.get("recommend_room_list").isJsonArray()) {
                    JsonArray items = data.getAsJsonArray("recommend_room_list");
                    for (int i = 0; i < items.size(); i++) {
                        JsonObject item = items.get(i).getAsJsonObject();
                        VideoData video = parseLiveData(item);
                        if (video != null) {
                            videos.add(video);
                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error parsing live recommend list", e);
        }
        return videos;
    }

    /**
     * 解析单个视频数据
     */
    private VideoData parseVideoData(JsonObject item) {
        try {
            VideoData video = new VideoData();

            // 基本信息
            video.setId(item.get("bvid").getAsString());
            video.setBvid(item.get("bvid").getAsString());
            video.setAid(String.valueOf(item.get("aid").getAsLong()));
            video.setTitle(item.get("title").getAsString());
            video.setDescription(item.get("desc").getAsString());
            video.setCoverUrl(item.get("pic").getAsString());

            // 分区信息
            if (item.has("tid")) {
                video.setTid(String.valueOf(item.get("tid").getAsInt()));
            }
            if (item.has("tname")) {
                video.setTname(item.get("tname").getAsString());
            }

            // 时间信息
            if (item.has("pubdate")) {
                video.setPublishTime(item.get("pubdate").getAsLong());
            }

            // 视频时长
            int duration = item.get("duration").getAsInt();
            video.setDuration(formatDuration(duration));

            // 统计信息
            JsonObject stat = item.getAsJsonObject("stat");
            if (stat != null) {
                video.setViewCount(stat.get("view").getAsInt());
                video.setDanmakuCount(stat.get("danmaku").getAsInt());
                video.setLikeCount(stat.get("like").getAsInt());
                video.setCoinCount(stat.get("coin").getAsInt());
                video.setFavoriteCount(stat.get("favorite").getAsInt());
            }

            // 上传者信息
            JsonObject owner = item.getAsJsonObject("owner");
            if (owner != null) {
                video.setUploader(owner.get("name").getAsString());
            }

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
        } catch (Exception e) {
            Log.e(TAG, "Error parsing video data", e);
            return null;
        }
    }

    /**
     * 解析单个直播数据
     */
    private VideoData parseLiveData(JsonObject item) {
        try {
            VideoData video = new VideoData();

            // 基本信息
            video.setId(String.valueOf(item.get("roomid").getAsLong()));
            video.setTitle("【直播】" + item.get("title").getAsString());
            video.setDescription(item.get("area_v2_name").getAsString());
            video.setCoverUrl(item.get("cover").getAsString());

            // 分区信息
            if (item.has("area_v2_id")) {
                video.setArea(String.valueOf(item.get("area_v2_id").getAsInt()));
            }
            if (item.has("area_v2_name")) {
                video.setAreaName(item.get("area_v2_name").getAsString());
            }
            if (item.has("area_v2_parent_name")) {
                video.setTags(item.get("area_v2_parent_name").getAsString());
            }

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
            video.setLiveStatus("1");

            return video;
        } catch (Exception e) {
            Log.e(TAG, "Error parsing live data", e);
            return null;
        }
    }

    /**
     * 解析单个视频信息
     * API响应结构: {code: 0, data: {...}}
     */
    private VideoData parseVideoInfo(String json) {
        try {
            JsonObject root = JsonParser.parseString(json).getAsJsonObject();
            if (root.has("code") && root.get("code").getAsInt() == 0) {
                JsonObject data = root.getAsJsonObject("data");
                return parseVideoData(data);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error parsing video info", e);
        }
        return null;
    }

    /**
     * 解析直播房间信息
     * API响应结构: {code: 0, data: {...}}
     */
    private VideoData parseLiveRoomInfo(String json) {
        try {
            JsonObject root = JsonParser.parseString(json).getAsJsonObject();
            if (root.has("code") && root.get("code").getAsInt() == 0) {
                JsonObject data = root.getAsJsonObject("data");
                VideoData video = new VideoData();

                // 基本信息
                video.setId(String.valueOf(data.get("room_id").getAsLong()));
                video.setTitle("【直播】" + data.get("title").getAsString());
                video.setDescription(data.get("description").getAsString());
                video.setCoverUrl(data.get("user_cover").getAsString());

                // 分区信息
                if (data.has("area_name")) {
                    video.setAreaName(data.get("area_name").getAsString());
                }
                if (data.has("parent_area_name")) {
                    video.setTags(data.get("parent_area_name").getAsString());
                }

                // 直播信息
                video.setDuration("LIVE");
                video.setViewCount(data.get("online").getAsInt());
                video.setUploader(data.get("uid").getAsString());

                // 直播URL
                video.setVideoUrl("https://live.bilibili.com/" + video.getId());

                // 弹幕URL
                video.setDanmakuUrl("https://live.bilibili.com/" + video.getId());

                // 直播标识
                video.setLiveStream(true);
                video.set4K(false);
                video.setQuality("LIVE");

                // 直播状态
                int liveStatus = data.get("live_status").getAsInt();
                video.setLiveStatus(String.valueOf(liveStatus));

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
