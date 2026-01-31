package com.flashfinger.bilitv.data;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Factory class for creating video data from Bilibili API
 * Uses BilibiliApiClient to fetch real data
 */
public class VideoDataFactory {

    private static final String TAG = "VideoDataFactory";
    private static BilibiliApiClient apiClient;
    private static boolean useMockData = false; // 用于测试时使用模拟数据

    // 缓存数据，避免频繁请求
    private static List<VideoData> cachedRecommendedVideos = null;
    private static List<VideoData> cached4KVideos = null;
    private static List<VideoData> cachedLiveStreams = null;
    private static List<VideoData> cachedPopularVideos = null;

    /**
     * 初始化 API 客户端
     */
    public static void init() {
        if (apiClient == null) {
            apiClient = new BilibiliApiClient();
        }
    }

    /**
     * 设置是否使用模拟数据（用于测试）
     */
    public static void setUseMockData(boolean useMockData) {
        VideoDataFactory.useMockData = useMockData;
    }

    /**
     * 获取推荐视频列表
     */
    public static List<VideoData> getRecommendedVideos() {
        if (useMockData) {
            return getMockRecommendedVideos();
        }

        if (cachedRecommendedVideos != null) {
            return cachedRecommendedVideos;
        }

        init();
        List<VideoData> videos = apiClient.getRecommendedVideos();

        if (videos.isEmpty()) {
            Log.w(TAG, "Failed to fetch recommended videos from API, using mock data");
            videos = getMockRecommendedVideos();
        }

        cachedRecommendedVideos = videos;
        return videos;
    }

    /**
     * 获取 4K 视频列表
     */
    public static List<VideoData> get4KVideos() {
        if (useMockData) {
            return getMock4KVideos();
        }

        if (cached4KVideos != null) {
            return cached4KVideos;
        }

        // 从推荐视频中筛选 4K 视频
        List<VideoData> recommended = getRecommendedVideos();
        List<VideoData> videos = new ArrayList<>();

        for (VideoData video : recommended) {
            if (video.is4K()) {
                videos.add(video);
            }
        }

        // 如果没有 4K 视频，使用模拟数据
        if (videos.isEmpty()) {
            videos = getMock4KVideos();
        }

        cached4KVideos = videos;
        return videos;
    }

    /**
     * 获取直播列表
     */
    public static List<VideoData> getLiveStreams() {
        if (useMockData) {
            return getMockLiveStreams();
        }

        if (cachedLiveStreams != null) {
            return cachedLiveStreams;
        }

        init();
        List<VideoData> videos = apiClient.getLiveStreams();

        if (videos.isEmpty()) {
            Log.w(TAG, "Failed to fetch live streams from API, using mock data");
            videos = getMockLiveStreams();
        }

        cachedLiveStreams = videos;
        return videos;
    }

    /**
     * 获取热门视频列表
     */
    public static List<VideoData> getPopularVideos() {
        if (useMockData) {
            return getMockPopularVideos();
        }

        if (cachedPopularVideos != null) {
            return cachedPopularVideos;
        }

        init();
        List<VideoData> videos = apiClient.getPopularVideos();

        if (videos.isEmpty()) {
            Log.w(TAG, "Failed to fetch popular videos from API, using mock data");
            videos = getMockPopularVideos();
        }

        cachedPopularVideos = videos;
        return videos;
    }

    /**
     * 获取单个视频信息
     */
    public static VideoData getVideoInfo(String bvid) {
        if (useMockData) {
            return getMockVideoInfo(bvid);
        }

        init();
        VideoData video = apiClient.getVideoInfo(bvid);

        if (video == null) {
            Log.w(TAG, "Failed to fetch video info for " + bvid + ", using mock data");
            video = getMockVideoInfo(bvid);
        }

        return video;
    }

    /**
     * 获取直播房间信息
     */
    public static VideoData getLiveRoomInfo(String roomId) {
        if (useMockData) {
            return getMockLiveRoomInfo(roomId);
        }

        init();
        VideoData live = apiClient.getLiveRoomInfo(roomId);

        if (live == null) {
            Log.w(TAG, "Failed to fetch live room info for " + roomId + ", using mock data");
            live = getMockLiveRoomInfo(roomId);
        }

        return live;
    }

    /**
     * 清除缓存
     */
    public static void clearCache() {
        cachedRecommendedVideos = null;
        cached4KVideos = null;
        cachedLiveStreams = null;
        cachedPopularVideos = null;
    }

    // ==================== 模拟数据（用于测试和离线模式） ====================

    private static List<VideoData> getMockRecommendedVideos() {
        List<VideoData> videos = new ArrayList<>();
        videos.add(VideoData.createVideo(
                "BV1GJ411x7h7",
                "【4K】Bilibili官方宣传片",
                "Bilibili 2024年度宣传片，记录UP主们的精彩瞬间",
                "https://i0.hdslb.com/bfs/archive/5a3c2d1e4f5a6b7c8d9e0f1a2b3c4d5e6.jpg",
                "https://sf1-hscdn-tos.pstatp.com/obj/media-fe/xgplayer_doc_video/mp4/xgplayer-demo-720p.mp4",
                "https://comment.bilibili.com/BV1GJ411x7h7.xml",
                "04:35",
                1250000,
                "Bilibili官方",
                true
        ));

        videos.add(VideoData.createVideo(
                "BV1GJ411x7h8",
                "【1080P】2024年热门科技产品盘点",
                "这一年都有哪些黑科技产品改变了我们的生活",
                "https://i0.hdslb.com/bfs/archive/6e1c2d3e4f5a6b7c8d9e0f1a2b3c4d5e7.jpg",
                "https://sf1-hscdn-tos.pstatp.com/obj/media-fe/xgplayer_doc_video/mp4/xgplayer-demo-720p.mp4",
                "https://comment.bilibili.com/BV1GJ411x7h8.xml",
                "12:28",
                890000,
                "科技UP主",
                false
        ));

        videos.add(VideoData.createVideo(
                "BV1GJ411x7h9",
                "【4K】绝美自然风光·阿尔卑斯山脉",
                "航拍阿尔卑斯山脉的壮丽景色",
                "https://i0.hdslb.com/bfs/archive/7e1c2d3e4f5a6b7c8d9e0f1a2b3c4d5e8.jpg",
                "https://sf1-hscdn-tos.pstatp.com/obj/media-fe/xgplayer_doc_video/mp4/xgplayer-demo-720p.mp4",
                "https://comment.bilibili.com/BV1GJ411x7h9.xml",
                "08:15",
                1560000,
                "旅游UP主",
                true
        ));

        videos.add(VideoData.createVideo(
                "BV1GJ411x7hA",
                "【1080P】轻松学编程系列 - 第一期",
                "零基础入门编程，从HelloWorld开始",
                "https://i0.hdslb.com/bfs/archive/8e1c2d3e4f5a6b7c8d9e0f1a2b3c4d5e9.jpg",
                "https://sf1-hscdn-tos.pstatp.com/obj/media-fe/xgplayer_doc_video/mp4/xgplayer-demo-720p.mp4",
                "https://comment.bilibili.com/BV1GJ411x7hA.xml",
                "15:42",
                234000,
                "编程老师",
                false
        ));

        return videos;
    }

    private static List<VideoData> getMock4KVideos() {
        List<VideoData> videos = new ArrayList<>();
        videos.add(VideoData.createVideo(
                "BV1GJ411x7hB",
                "【4K HDR】城市夜景延时摄影",
                "上海、北京、深圳等大城市的璀璨夜景",
                "https://i0.hdslb.com/bfs/archive/9e1c2d3e4f5a6b7c8d9e0f1a2b3c4d5ea.jpg",
                "https://sf1-hscdn-tos.pstatp.com/obj/media-fe/xgplayer_doc_video/mp4/xgplayer-demo-720p.mp4",
                "https://comment.bilibili.com/BV1GJ411x7hB.xml",
                "10:22",
                890000,
                "摄影UP主",
                true
        ));

        videos.add(VideoData.createVideo(
                "BV1GJ411x7hC",
                "【4K】野生动物纪录片片段",
                "非洲大草原上的动物生活",
                "https://i0.hdslb.com/bfs/archive/ae1c2d3e4f5a6b7c8d9e0f1a2b3c4d5eb.jpg",
                "https://sf1-hscdn-tos.pstatp.com/obj/media-fe/xgplayer_doc_video/mp4/xgplayer-demo-720p.mp4",
                "https://comment.bilibili.com/BV1GJ411x7hC.xml",
                "25:18",
                1200000,
                "纪录片频道",
                true
        ));

        videos.add(VideoData.createVideo(
                "BV1GJ411x7hD",
                "【4K】美食制作 - 米其林餐厅",
                "顶级厨师的精湛厨艺展示",
                "https://i0.hdslb.com/bfs/archive/be1c2d3e4f5a6b7c8d9e0f1a2b3c4d5ec.jpg",
                "https://sf1-hscdn-tos.pstatp.com/obj/media-fe/xgplayer_doc_video/mp4/xgplayer-demo-720p.mp4",
                "https://comment.bilibili.com/BV1GJ411x7hD.xml",
                "18:35",
                567000,
                "美食UP主",
                true
        ));

        videos.add(VideoData.createVideo(
                "BV1GJ411x7hE",
                "【4K】音乐现场演出",
                "顶级交响乐团的精彩演出",
                "https://i0.hdslb.com/bfs/archive/ce1c2d3e4f5a6b7c8d9e0f1a2b3c4d5ed.jpg",
                "https://sf1-hscdn-tos.pstatp.com/obj/media-fe/xgplayer_doc_video/mp4/xgplayer-demo-720p.mp4",
                "https://comment.bilibili.com/BV1GJ411x7hE.xml",
                "45:12",
                789000,
                "音乐频道",
                true
        ));

        return videos;
    }

    private static List<VideoData> getMockLiveStreams() {
        List<VideoData> videos = new ArrayList<>();
        videos.add(VideoData.createLive(
                "22477958",
                "游戏大作战",
                "正在直播：热门游戏最新版本",
                "https://i0.hdslb.com/bfs/archive/de1c2d3e4f5a6b7c8d9e0f1a2b3c4d5ee.jpg",
                "https://live.bilibili.com/22477958",
                12500,
                "游戏主播",
                "游戏"
        ));

        videos.add(VideoData.createLive(
                "22477959",
                "音乐教学",
                "正在直播：吉他入门教学",
                "https://i0.hdslb.com/bfs/archive/ee1c2d3e4f5a6b7c8d9e0f1a2b3c4d5ef.jpg",
                "https://live.bilibili.com/22477959",
                8900,
                "音乐老师",
                "音乐"
        ));

        videos.add(VideoData.createLive(
                "22477960",
                "编程直播",
                "正在直播：开发一款Android应用",
                "https://i0.hdslb.com/bfs/archive/fe1c2d3e4f5a6b7c8d9e0f1a2b3c4d5f0.jpg",
                "https://live.bilibili.com/22477960",
                6700,
                "程序员",
                "编程"
        ));

        videos.add(VideoData.createLive(
                "22477961",
                "美食制作",
                "正在直播：制作家常菜",
                "https://i0.hdslb.com/bfs/archive/0f1c2d3e4f5a6b7c8d9e0f1a2b3c4d5f1.jpg",
                "https://live.bilibili.com/22477961",
                15200,
                "美食主播",
                "美食"
        ));

        return videos;
    }

    private static List<VideoData> getMockPopularVideos() {
        List<VideoData> videos = new ArrayList<>();
        videos.add(VideoData.createVideo(
                "BV1GJ411x7hF",
                "【1080P】搞笑视频合集2024",
                "年度最佳搞笑视频精选",
                "https://i0.hdslb.com/bfs/archive/1f1c2d3e4f5a6b7c8d9e0f1a2b3c4d5f2.jpg",
                "https://sf1-hscdn-tos.pstatp.com/obj/media-fe/xgplayer_doc_video/mp4/xgplayer-demo-720p.mp4",
                "https://comment.bilibili.com/BV1GJ411x7hF.xml",
                "20:15",
                5670000,
                "搞笑UP主",
                false
        ));

        videos.add(VideoData.createVideo(
                "BV1GJ411x7hG",
                "【4K】电影解说 - 经典科幻片",
                "深度解析经典科幻电影",
                "https://i0.hdslb.com/bfs/archive/2f1c2d3e4f5a6b7c8d9e0f1a2b3c4d5f3.jpg",
                "https://sf1-hscdn-tos.pstatp.com/obj/media-fe/xgplayer_doc_video/mp4/xgplayer-demo-720p.mp4",
                "https://comment.bilibili.com/BV1GJ411x7hG.xml",
                "35:28",
                3400000,
                "电影解说",
                true
        ));

        videos.add(VideoData.createVideo(
                "BV1GJ411x7hH",
                "【1080P】健身教程 - 腹肌训练",
                "专业教练带你练出完美腹肌",
                "https://i0.hdslb.com/bfs/archive/3f1c2d3e4f5a6b7c8d9e0f1a2b3c4d5f4.jpg",
                "https://sf1-hscdn-tos.pstatp.com/obj/media-fe/xgplayer_doc_video/mp4/xgplayer-demo-720p.mp4",
                "https://comment.bilibili.com/BV1GJ411x7hH.xml",
                "25:10",
                1200000,
                "健身教练",
                false
        ));

        videos.add(VideoData.createVideo(
                "BV1GJ411x7hI",
                "【4K】旅游攻略 - 日本深度游",
                "带你深度游览日本各地",
                "https://i0.hdslb.com/bfs/archive/4f1c2d3e4f5a6b7c8d9e0f1a2b3c4d5f5.jpg",
                "https://sf1-hscdn-tos.pstatp.com/obj/media-fe/xgplayer_doc_video/mp4/xgplayer-demo-720p.mp4",
                "https://comment.bilibili.com/BV1GJ411x7hI.xml",
                "55:35",
                890000,
                "旅游UP主",
                true
        ));

        return videos;
    }

    private static VideoData getMockVideoInfo(String bvid) {
        // 根据 bvid 返回对应的模拟数据
        if (bvid.equals("BV1GJ411x7h7")) {
            return getMockRecommendedVideos().get(0);
        } else if (bvid.equals("BV1GJ411x7h8")) {
            return getMockRecommendedVideos().get(1);
        } else if (bvid.equals("BV1GJ411x7h9")) {
            return getMockRecommendedVideos().get(2);
        } else if (bvid.equals("BV1GJ411x7hA")) {
            return getMockRecommendedVideos().get(3);
        } else if (bvid.equals("BV1GJ411x7hB")) {
            return getMock4KVideos().get(0);
        } else if (bvid.equals("BV1GJ411x7hC")) {
            return getMock4KVideos().get(1);
        } else if (bvid.equals("BV1GJ411x7hD")) {
            return getMock4KVideos().get(2);
        } else if (bvid.equals("BV1GJ411x7hE")) {
            return getMock4KVideos().get(3);
        } else if (bvid.equals("BV1GJ411x7hF")) {
            return getMockPopularVideos().get(0);
        } else if (bvid.equals("BV1GJ411x7hG")) {
            return getMockPopularVideos().get(1);
        } else if (bvid.equals("BV1GJ411x7hH")) {
            return getMockPopularVideos().get(2);
        } else if (bvid.equals("BV1GJ411x7hI")) {
            return getMockPopularVideos().get(3);
        }
        return null;
    }

    private static VideoData getMockLiveRoomInfo(String roomId) {
        // 根据 roomId 返回对应的模拟数据
        if (roomId.equals("22477958")) {
            return getMockLiveStreams().get(0);
        } else if (roomId.equals("22477959")) {
            return getMockLiveStreams().get(1);
        } else if (roomId.equals("22477960")) {
            return getMockLiveStreams().get(2);
        } else if (roomId.equals("22477961")) {
            return getMockLiveStreams().get(3);
        }
        return null;
    }
}
