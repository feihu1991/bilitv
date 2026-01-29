package com.flashfinger.bilitv;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.leanback.app.BackgroundManager;
import androidx.leanback.app.BrowseSupportFragment;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.HeaderItem;
import androidx.leanback.widget.ListRow;
import androidx.leanback.widget.ListRowPresenter;
import androidx.leanback.widget.OnItemViewClickedListener;
import androidx.leanback.widget.OnItemViewSelectedListener;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.Row;
import androidx.leanback.widget.RowPresenter;

import com.flashfinger.bilitv.data.VideoData;
import com.flashfinger.bilitv.data.VideoDataFactory;
import com.flashfinger.bilitv.player.VideoPlayerActivity;
import com.flashfinger.bilitv.presenter.CardPresenter;
import com.flashfinger.bilitv.presenter.LiveCardPresenter;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Main Browse Fragment for BiliTV
 * Displays video and live stream categories
 */
public class MainBrowseFragment extends BrowseSupportFragment {
    private static final String TAG = "MainBrowseFragment";

    private static final int BACKGROUND_UPDATE_DELAY = 300;
    private static final String BACKGROUND_URI = "https://i0.hdslb.com/bfs/archive/6e1234567890abcdef1234567890abcdef12345678.jpg";

    private final Timer mBackgroundTimer;
    private final Drawable mDefaultBackground;
    private BackgroundManager mBackgroundManager;
    private ArrayObjectAdapter mRowsAdapter;
    private VideoData mSelectedVideo;

    public MainBrowseFragment() {
        mBackgroundTimer = null;
        mDefaultBackground = null;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onActivityCreated(savedInstanceState);

        prepareBackgroundManager();
        setupUIElements();
        setupEventListeners();
        loadRows();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mBackgroundTimer) {
            Log.d(TAG, "onDestroy: " + mBackgroundTimer.toString());
            mBackgroundTimer.cancel();
        }
    }

    private void prepareBackgroundManager() {
        mBackgroundManager = BackgroundManager.getInstance(getActivity());
        mBackgroundManager.attach(getActivity().getWindow());
        mBackgroundManager.setDrawable(getResources().getDrawable(R.drawable.default_background));
    }

    private void setupUIElements() {
        // Set title
        setTitle("BiliTV - 哔哩哔哩");

        // Set header color
        setHeadersState(HEADERS_ENABLED);
        setHeadersTransitionOnBackEnabled(true);

        // Set brand color (Bilibili pink)
        setBrandColor(Color.parseColor("#FB7299"));

        // Search icon color
        setSearchAffordanceColor(Color.parseColor("#FB7299"));
    }

    private void loadRows() {
        mRowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
        CardPresenter cardPresenter = new CardPresenter();
        LiveCardPresenter liveCardPresenter = new LiveCardPresenter();

        // Recommended Videos Row
        ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(cardPresenter);
        listRowAdapter.addAll(0, VideoDataFactory.getRecommendedVideos());
        HeaderItem header = new HeaderItem(0, "推荐视频");
        mRowsAdapter.add(new ListRow(header, listRowAdapter));

        // 4K Videos Row
        ArrayObjectAdapter listRowAdapter2 = new ArrayObjectAdapter(cardPresenter);
        listRowAdapter2.addAll(0, VideoDataFactory.get4KVideos());
        HeaderItem header2 = new HeaderItem(1, "4K超清");
        mRowsAdapter.add(new ListRow(header2, listRowAdapter2));

        // Live Streams Row
        ArrayObjectAdapter listRowAdapter3 = new ArrayObjectAdapter(liveCardPresenter);
        listRowAdapter3.addAll(0, VideoDataFactory.getLiveStreams());
        HeaderItem header3 = new HeaderItem(2, "正在直播");
        mRowsAdapter.add(new ListRow(header3, listRowAdapter3));

        // Popular Videos Row
        ArrayObjectAdapter listRowAdapter4 = new ArrayObjectAdapter(cardPresenter);
        listRowAdapter4.addAll(0, VideoDataFactory.getPopularVideos());
        HeaderItem header4 = new HeaderItem(3, "热门视频");
        mRowsAdapter.add(new ListRow(header4, listRowAdapter4));

        setAdapter(mRowsAdapter);
    }

    private void setupEventListeners() {
        setOnSearchClickedListener(view -> {
            Toast.makeText(getActivity(), "搜索功能开发中", Toast.LENGTH_SHORT).show();
        });

        setOnItemViewClickedListener(new ItemViewClickedListener());
        setOnItemViewSelectedListener(new ItemViewSelectedListener());
    }

    private final class ItemViewClickedListener implements OnItemViewClickedListener {
        @Override
        public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item,
                                  RowPresenter.ViewHolder rowViewHolder, Row row) {
            if (item instanceof VideoData) {
                VideoData video = (VideoData) item;
                Log.d(TAG, "Item clicked: " + video.getTitle());

                Intent intent;
                if (video.isLiveStream()) {
                    intent = new Intent(getActivity(), com.flashfinger.bilitv.player.LivePlayerActivity.class);
                } else {
                    intent = new Intent(getActivity(), VideoPlayerActivity.class);
                }
                intent.putExtra(VideoData.VIDEO_ID, video.getId());
                intent.putExtra(VideoData.VIDEO_TITLE, video.getTitle());
                intent.putExtra(VideoData.VIDEO_DESCRIPTION, video.getDescription());
                intent.putExtra(VideoData.VIDEO_URL, video.getVideoUrl());
                intent.putExtra(VideoData.VIDEO_COVER, video.getCoverUrl());
                intent.putExtra(VideoData.VIDEO_DANMAKU_URL, video.getDanmakuUrl());
                startActivity(intent);
            }
        }
    }

    private final class ItemViewSelectedListener implements OnItemViewSelectedListener {
        @Override
        public void onItemSelected(Presenter.ViewHolder itemViewHolder, Object item,
                                   RowPresenter.ViewHolder rowViewHolder, Row row) {
            if (item instanceof VideoData) {
                mSelectedVideo = (VideoData) item;
                updateBackground(mSelectedVideo.getCoverUrl());
            }
        }
    }

    private void updateBackground(String uri) {
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;

        Glide.with(getActivity())
                .load(uri)
                .centerCrop()
                .error(mDefaultBackground)
                .into(new SimpleTarget<Drawable>(width, height) {
                    @Override
                    public void onResourceReady(Drawable resource,
                                                Transition<? super Drawable> transition) {
                        mBackgroundManager.setDrawable(resource);
                    }
                });

        mBackgroundTimer.cancel();
    }

    private static class SimpleTarget<T> implements Target<T> {
        private final int mWidth;
        private final int mHeight;

        SimpleTarget(int width, int height) {
            mWidth = width;
            mHeight = height;
        }

        @Override
        public void onResourceReady(T resource, Transition<? super T> transition) {
            // Implemented by anonymous class
        }

        @Override
        public void onLoadCleared(Drawable placeholder) {
        }

        @Override
        public void onLoadStarted(Drawable placeholder) {
        }

        @Override
        public void onLoadFailed(Drawable errorDrawable) {
        }

        @Override
        public void getSize(SizeReadyListener cb) {
            cb.onSizeReady(mWidth, mHeight);
        }

        @Override
        public void removeCallback(SizeReadyListener cb) {
        }

        @Override
        public void setRequest(Request request) {
        }

        @Override
        public Request getRequest() {
            return null;
        }
    }
}
