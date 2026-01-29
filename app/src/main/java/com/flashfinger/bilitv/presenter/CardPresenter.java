package com.flashfinger.bilitv.presenter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.leanback.widget.Presenter;

import com.bumptech.glide.Glide;
import com.flashfinger.bilitv.R;
import com.flashfinger.bilitv.data.VideoData;

/**
 * Card Presenter for displaying video thumbnails
 */
public class CardPresenter extends Presenter {
    private static final String TAG = "CardPresenter";
    private static final int CARD_WIDTH = 313;
    private static final int CARD_HEIGHT = 176;
    private static int sSelectedBackgroundColor;
    private static int sDefaultBackgroundColor;

    private Drawable mDefaultCardImage;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        sDefaultBackgroundColor = ContextCompat.getColor(parent.getContext(), R.color.default_background);
        sSelectedBackgroundColor = ContextCompat.getColor(parent.getContext(), R.color.selected_background);
        mDefaultCardImage = ContextCompat.getDrawable(parent.getContext(), R.drawable.movie_poster_placeholder);

        ImageCardView cardView = new ImageCardView(parent.getContext()) {
            @Override
            public void setSelected(boolean selected) {
                updateCardBackgroundColor(this, selected);
                super.setSelected(selected);
            }
        };

        cardView.setFocusable(true);
        cardView.setFocusableInTouchMode(true);
        updateCardBackgroundColor(cardView, false);
        return new ViewHolder(cardView);
    }

    private static void updateCardBackgroundColor(ImageCardView view, boolean selected) {
        int color = selected ? sSelectedBackgroundColor : sDefaultBackgroundColor;
        view.setBackgroundColor(color);
        view.findViewById(R.id.info_field).setBackgroundColor(color);
    }

    @Override
    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object item) {
        VideoData video = (VideoData) item;

        ImageCardView cardView = (ImageCardView) viewHolder.view;

        if (video.getCoverUrl() != null) {
            cardView.setTitleText(video.getTitle());
            cardView.setContentText(video.getUploader() + " · " + video.getDuration());
            if (video.is4K()) {
                cardView.setContentText(video.getUploader() + " · 4K · " + video.getDuration());
            }
            cardView.setMainImageDimensions(CARD_WIDTH, CARD_HEIGHT);

            Glide.with(viewHolder.view.getContext())
                    .load(video.getCoverUrl())
                    .centerCrop()
                    .error(mDefaultCardImage)
                    .into(cardView.getMainImageView());
        }
    }

    @Override
    public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {
        ImageCardView cardView = (ImageCardView) viewHolder.view;
        cardView.setBadgeImage(null);
        cardView.setMainImage(null);
    }

    static class ViewHolder extends Presenter.ViewHolder {
        private ImageCardView cardView;
        private Drawable defaultCardImage;
        private ImageView imageView;

        public ViewHolder(ImageCardView view) {
            super(view);
            cardView = view;
            imageView = (ImageView) view.findViewById(R.id.main_image);
        }

        public ImageCardView getCardView() {
            return cardView;
        }

        public void updateCardViewImage(Context context, String imageUrl) {
            Glide.with(context)
                    .load(imageUrl)
                    .centerCrop()
                    .error(defaultCardImage)
                    .into(imageView);
        }
    }
}
