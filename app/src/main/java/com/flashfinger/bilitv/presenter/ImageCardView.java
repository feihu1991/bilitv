package com.flashfinger.bilitv.presenter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.leanback.widget.BaseCardView;

import com.flashfinger.bilitv.R;

/**
 * Custom Image Card View for video thumbnails
 */
public class ImageCardView extends BaseCardView {

    private ImageView mMainImageView;
    private TextView mTitleView;
    private TextView mContentView;
    private ImageView mBadgeView;

    public ImageCardView(Context context) {
        super(context);
        buildCardView(context);
    }

    public ImageCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        buildCardView(context);
    }

    public ImageCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        buildCardView(context);
    }

    private void buildCardView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.image_card_view, this, true);
        setFocusable(true);
        setFocusableInTouchMode(true);

        mMainImageView = findViewById(R.id.main_image);
        mTitleView = findViewById(R.id.title_text);
        mContentView = findViewById(R.id.content_text);
        mBadgeView = findViewById(R.id.badge_image);
    }

    public ImageView getMainImageView() {
        return mMainImageView;
    }

    public void setTitleText(String text) {
        if (mTitleView != null) {
            mTitleView.setText(text);
            mTitleView.setVisibility(text != null && text.length() > 0 ? View.VISIBLE : View.GONE);
        }
    }

    public void setContentText(String text) {
        if (mContentView != null) {
            mContentView.setText(text);
            mContentView.setVisibility(text != null && text.length() > 0 ? View.VISIBLE : View.GONE);
        }
    }

    public void setBadgeImage(Drawable drawable) {
        if (mBadgeView != null) {
            mBadgeView.setImageDrawable(drawable);
            mBadgeView.setVisibility(drawable != null ? View.VISIBLE : View.GONE);
        }
    }

    public void setMainImageDimensions(int width, int height) {
        if (mMainImageView != null) {
            mMainImageView.getLayoutParams().width = width;
            mMainImageView.getLayoutParams().height = height;
        }
    }

    public void setMainImage(Drawable drawable) {
        if (mMainImageView != null) {
            mMainImageView.setImageDrawable(drawable);
        }
    }
}
