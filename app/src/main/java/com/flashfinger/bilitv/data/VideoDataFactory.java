package com.flashfinger.bilitv.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Factory class for creating sample video data
 * In production, replace with Bilibili API integration
 */
public class VideoDataFactory {

    private static List<VideoData> recommendedVideos = new ArrayList<>();
    private static List<VideoData> fourKVideos = new ArrayList<>();
    private static List<VideoData> liveStreams = new ArrayList<>();
    private static List<VideoData> popularVideos = new ArrayList<>();

    static {
        // Recommended Videos (使用示例视频URL，实际使用时替换为Bilibili API)
        recommendedVideos.add(new VideoData(
                "1",
                "【4K】Bilibili官方宣传片",
                "Bilibili 2024年度宣传片，记录UP主们的精彩瞬间",
                "https://i0.hdslb.com/bfs/archive/5a3c2d1e4f5a6b7c8d9e0f1a2b3c4d5e6.jpg",
                "https://sf1-hscdn-tos.pstatp.com/obj/media-fe/xgplayer_doc_video/mp4/xgplayer-demo-720p.mp4",
                "",
                "04:35",
                1250000,
                "Bilibili官方",
                false,
                true
        ));

        recommendedVideos.add(new VideoData(
                "2",
                "【1080P】2024年热门科技产品盘点",
                "这一年都有哪些黑科技产品改变了我们的生活",
                "https://i0.hdslb.com/bfs/archive/6e1c2d3e4f5a6b7c8d9e0f1a2b3c4d5e7.jpg",
                "https://sf1-hscdn-tos.pstatp.com/obj/media-fe/xgplayer_doc_video/mp4/xgplayer-demo-720p.mp4",
                "",
                "12:28",
                890000,
                "科技UP主",
                false,
                false
        ));

        recommendedVideos.add(new VideoData(
                "3",
                "【4K】绝美自然风光·阿尔卑斯山脉",
                "航拍阿尔卑斯山脉的壮丽景色",
                "https://i0.hdslb.com/bfs/archive/7e1c2d3e4f5a6b7c8d9e0f1a2b3c4d5e8.jpg",
                "https://sf1-hscdn-tos.pstatp.com/obj/media-fe/xgplayer_doc_video/mp4/xgplayer-demo-720p.mp4",
                "",
                "08:15",
                1560000,
                "旅游UP主",
                false,
                true
        ));

        recommendedVideos.add(new VideoData(
                "4",
                "【1080P】轻松学编程系列 - 第一期",
                "零基础入门编程，从HelloWorld开始",
                "https://i0.hdslb.com/bfs/archive/8e1c2d3e4f5a6b7c8d9e0f1a2b3c4d5e9.jpg",
                "https://sf1-hscdn-tos.pstatp.com/obj/media-fe/xgplayer_doc_video/mp4/xgplayer-demo-720p.mp4",
                "",
                "15:42",
                234000,
                "编程老师",
                false,
                false
        ));

        // 4K Videos
        fourKVideos.add(new VideoData(
                "101",
                "【4K HDR】城市夜景延时摄影",
                "上海、北京、深圳等大城市的璀璨夜景",
                "https://i0.hdslb.com/bfs/archive/9e1c2d3e4f5a6b7c8d9e0f1a2b3c4d5ea.jpg",
                "https://sf1-hscdn-tos.pstatp.com/obj/media-fe/xgplayer_doc_video/mp4/xgplayer-demo-720p.mp4",
                "",
                "10:22",
                890000,
                "摄影UP主",
                false,
                true
        ));

        fourKVideos.add(new VideoData(
                "102",
                "【4K】野生动物纪录片片段",
                "非洲大草原上的动物生活",
                "https://i0.hdslb.com/bfs/archive/ae1c2d3e4f5a6b7c8d9e0f1a2b3c4d5eb.jpg",
                "https://sf1-hscdn-tos.pstatp.com/obj/media-fe/xgplayer_doc_video/mp4/xgplayer-demo-720p.mp4",
                "",
                "25:18",
                1200000,
                "纪录片频道",
                false,
                true
        ));

        fourKVideos.add(new VideoData(
                "103",
                "【4K】美食制作 - 米其林餐厅",
                "顶级厨师的精湛厨艺展示",
                "https://i0.hdslb.com/bfs/archive/be1c2d3e4f5a6b7c8d9e0f1a2b3c4d5ec.jpg",
                "https://sf1-hscdn-tos.pstatp.com/obj/media-fe/xgplayer_doc_video/mp4/xgplayer-demo-720p.mp4",
                "",
                "18:35",
                567000,
                "美食UP主",
                false,
                true
        ));

        fourKVideos.add(new VideoData(
                "104",
                "【4K】音乐现场演出",
                "顶级交响乐团的精彩演出",
                "https://i0.hdslb.com/bfs/archive/ce1c2d3e4f5a6b7c8d9e0f1a2b3c4d5ed.jpg",
                "https://sf1-hscdn-tos.pstatp.com/obj/media-fe/xgplayer_doc_video/mp4/xgplayer-demo-720p.mp4",
                "",
                "45:12",
                789000,
                "音乐频道",
                false,
                true
        ));

        // Live Streams
        liveStreams.add(new VideoData(
                "201",
                "【直播】游戏大作战",
                "正在直播：热门游戏最新版本",
                "https://i0.hdslb.com/bfs/archive/de1c2d3e4f5a6b7c8d9e0f1a2b3c4d5ee.jpg",
                "https://sf1-hscdn-tos.pstatp.com/obj/media-fe/xgplayer_doc_video/mp4/xgplayer-demo-720p.mp4",
                "",
                "LIVE",
                12500,
                "游戏主播",
                true,
                false
        ));

        liveStreams.add(new VideoData(
                "202",
                "【直播】音乐教学",
                "正在直播：吉他入门教学",
                "https://i0.hdslb.com/bfs/archive/ee1c2d3e4f5a6b7c8d9e0f1a2b3c4d5ef.jpg",
                "https://sf1-hscdn-tos.pstatp.com/obj/media-fe/xgplayer_doc_video/mp4/xgplayer-demo-720p.mp4",
                "",
                "LIVE",
                8900,
                "音乐老师",
                true,
                false
        ));

        liveStreams.add(new VideoData(
                "203",
                "【直播】编程直播",
                "正在直播：开发一款Android应用",
                "https://i0.hdslb.com/bfs/archive/fe1c2d3e4f5a6b7c8d9e0f1a2b3c4d5f0.jpg",
                "https://sf1-hscdn-tos.pstatp.com/obj/media-fe/xgplayer_doc_video/mp4/xgplayer-demo-720p.mp4",
                "",
                "LIVE",
                6700,
                "程序员",
                true,
                false
        ));

        liveStreams.add(new VideoData(
                "204",
                "【直播】美食制作",
                "正在直播：制作家常菜",
                "https://i0.hdslb.com/bfs/archive/0f1c2d3e4f5a6b7c8d9e0f1a2b3c4d5f1.jpg",
                "https://sf1-hscdn-tos.pstatp.com/obj/media-fe/xgplayer_doc_video/mp4/xgplayer-demo-720p.mp4",
                "",
                "LIVE",
                15200,
                "美食主播",
                true,
                false
        ));

        // Popular Videos
        popularVideos.add(new VideoData(
                "301",
                "【1080P】搞笑视频合集2024",
                "年度最佳搞笑视频精选",
                "https://i0.hdslb.com/bfs/archive/1f1c2d3e4f5a6b7c8d9e0f1a2b3c4d5f2.jpg",
                "https://sf1-hscdn-tos.pstatp.com/obj/media-fe/xgplayer_doc_video/mp4/xgplayer-demo-720p.mp4",
                "",
                "20:15",
                5670000,
                "搞笑UP主",
                false,
                false
        ));

        popularVideos.add(new VideoData(
                "302",
                "【4K】电影解说 - 经典科幻片",
                "深度解析经典科幻电影",
                "https://i0.hdslb.com/bfs/archive/2f1c2d3e4f5a6b7c8d9e0f1a2b3c4d5f3.jpg",
                "https://sf1-hscdn-tos.pstatp.com/obj/media-fe/xgplayer_doc_video/mp4/xgplayer-demo-720p.mp4",
                "",
                "35:28",
                3400000,
                "电影解说",
                false,
                true
        ));

        popularVideos.add(new VideoData(
                "303",
                "【1080P】健身教程 - 腹肌训练",
                "专业教练带你练出完美腹肌",
                "https://i0.hdslb.com/bfs/archive/3f1c2d3e4f5a6b7c8d9e0f1a2b3c4d5f4.jpg",
                "https://sf1-hscdn-tos.pstatp.com/obj/media-fe/xgplayer_doc_video/mp4/xgplayer-demo-720p.mp4",
                "",
                "25:10",
                1200000,
                "健身教练",
                false,
                false
        ));

        popularVideos.add(new VideoData(
                "304",
                "【4K】旅游攻略 - 日本深度游",
                "带你深度游览日本各地",
                "https://i0.hdslb.com/bfs/archive/4f1c2d3e4f5a6b7c8d9e0f1a2b3c4d5f5.jpg",
                "https://sf1-hscdn-tos.pstatp.com/obj/media-fe/xgplayer_doc_video/mp4/xgplayer-demo-720p.mp4",
                "",
                "55:35",
                890000,
                "旅游UP主",
                false,
                true
        ));
    }

    public static List<VideoData> getRecommendedVideos() {
        return recommendedVideos;
    }

    public static List<VideoData> get4KVideos() {
        return fourKVideos;
    }

    public static List<VideoData> getLiveStreams() {
        return liveStreams;
    }

    public static List<VideoData> getPopularVideos() {
        return popularVideos;
    }
}
