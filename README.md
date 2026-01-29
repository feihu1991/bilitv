# BiliTV - 小米电视Bilibili应用

## 项目概述

BiliTV是一个专为小米电视（Redmi G Pro 27U）等Android TV系统开发的Bilibili视频播放应用。该应用支持4K视频播放、直播流、弹幕功能，并完全兼容电视遥控器操作。

## 功能特性

### 1. 4K超清视频播放
- 支持高达3840x2160分辨率的视频播放
- 基于ExoPlayer的高性能视频解码
- 自适应视频缩放模式

### 2. 直播功能
- 实时直播流播放
- 实时弹幕显示
- 在线观众数量显示

### 3. 弹幕系统
- 滚动弹幕显示
- 弹幕开关控制
- 随机速度和轨道分配

### 4. 遥控器支持
- 完整的D-pad方向键导航
- 确认键（中心键）播放/暂停
- 返回键退出
- 菜单键打开设置
- 媒体键支持（播放/暂停/停止）

### 5. 主界面
- 推荐视频列表
- 4K视频专区
- 正在直播
- 热门视频

## 技术架构

### 核心库
- **Leanback**: Android TV官方UI库，用于浏览界面
- **ExoPlayer**: Google开源媒体播放器，支持4K视频
- **Glide**: 图片加载库
- **OkHttp**: 网络请求库
- **Gson**: JSON解析库

### 项目结构
```
app/src/main/java/com/flashfinger/bilitv/
├── MainActivity.java                    # 主Activity
├── MainBrowseFragment.java              # 主浏览Fragment
├── data/
│   ├── VideoData.java                   # 视频数据模型
│   └── VideoDataFactory.java            # 视频数据工厂
├── presenter/
│   ├── CardPresenter.java               # 视频卡片展示器
│   ├── LiveCardPresenter.java           # 直播卡片展示器
│   └── ImageCardView.java               # 自定义卡片视图
├── player/
│   ├── VideoPlayerActivity.java        # 视频播放器Activity
│   └── LivePlayerActivity.java          # 直播播放器Activity
└── danmaku/
    └── DanmakuManager.java              # 弹幕管理器
```

## 开发环境要求

- Android Studio Arctic Fox或更高版本
- Android SDK 34
- JDK 1.8或更高版本
- Gradle 8.2.0

## 构建步骤

1. 克隆项目
```bash
git clone <repository-url>
cd flashfinger
```

2. 打开项目
```bash
使用Android Studio打开项目
```

3. 同步Gradle
```bash
./gradlew build
```

4. 安装到设备
```bash
./gradlew installDebug
```

## 使用说明

### 主界面导航
- **方向键上/下**: 在不同分类之间切换
- **方向键左/右**: 在同一分类的视频之间切换
- **确认键**: 播放选中的视频
- **返回键**: 返回上一级或退出应用

### 视频播放界面
- **确认键**: 播放/暂停
- **方向键**: 显示控制面板
- **返回键**: 返回主界面

### 控制面板
- **播放/暂停按钮**: 控制视频播放
- **弹幕开关**: 开启/关闭弹幕显示
- **画质按钮**: 切换视频画质

## 开发计划

### 第一阶段（已完成）
- [x] 项目基础结构搭建
- [x] 主界面UI实现
- [x] 视频播放器实现
- [x] 弹幕系统实现
- [x] 直播功能实现
- [x] 遥控器支持

### 第二阶段（计划中）
- [ ] Bilibili API集成
- [ ] 用户登录功能
- [ ] 收藏和历史记录
- [ ] 搜索功能
- [ ] 设置界面
- [ ] 视频清晰度选择

### 第三阶段（计划中）
- [ ] 评论功能
- [ ] 分享功能
- [ ] UP主主页
- [ ] 关注列表
- [ ] 个性化推荐

## 已知问题

- 当前使用示例视频URL，需要集成Bilibili API获取真实视频源
- 弹幕数据需要从Bilibili弹幕服务器获取
- 直播流地址需要从Bilibili直播API获取

## 贡献指南

欢迎提交Issue和Pull Request！

## 许可证

MIT License
