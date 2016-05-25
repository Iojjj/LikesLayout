package com.github.iojjj.likeslayout;

import android.animation.IntEvaluator;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.Keep;
import android.support.annotation.NonNull;

/**
 * Interface of drawable animator. {@link #initialize(int, int, LikesAttributes, LikesAttributes)}
 * method of implementations of this interface will be called once to configure animator. Then subsequent calls of
 * {@link #onAnimationUpdate(float, Drawable)} will be performed.
 */
@Keep
public interface DrawableAnimator {

    /**
     * Initialize animator.
     *
     * @param layoutWidth       LikesLayout width
     * @param layoutHeight      LikesLayout height
     * @param attributes        attributes of drawable
     * @param defaultAttributes default attributes of LikesLayout
     */
    void initialize(int layoutWidth, int layoutHeight,
                    @NonNull LikesAttributes attributes, @NonNull LikesAttributes defaultAttributes);

    /**
     * Called every time of animation progress.
     *
     * @param fraction animation fraction in range {@code [0, 1]}
     * @param drawable instance of drawable that should be animated
     */
    void onAnimationUpdate(float fraction, @NonNull Drawable drawable);

    /**
     * Factory that creates new instances of DrawableAnimator.
     * Implementations of this factory must have public constructor without arguments.
     */
    @Keep
    interface Factory {

        /**
         * Create new instance of DrawableAnimator.
         *
         * @return new instance of DrawableAnimator
         */
        DrawableAnimator newInstance();
    }


    @Keep
    class DefaultFactory implements Factory {

        @Override
        public DrawableAnimator newInstance() {
            return new ScaleDrawableAnimator();
        }
    }

    /**
     * DrawableAnimator implementation that changes alpha of drawable in fadeout manner.
     */
    @Keep
    class AlphaDrawableAnimator implements DrawableAnimator {

        private final IntEvaluator mAlphaEvaluator = new IntEvaluator();

        @Override
        public void initialize(int layoutWidth, int layoutHeight,
                               @NonNull LikesAttributes attributes, @NonNull LikesAttributes defaultAttributes) {

        }

        @Override
        public void onAnimationUpdate(float fraction, @NonNull Drawable drawable) {
            // skip until the end
            if (fraction < 0.75) {
                return;
            }
            final float animationFraction = Utilities.normalize(fraction, 0.75f, 1.0f);
            drawable.setAlpha(mAlphaEvaluator.evaluate(animationFraction, 255, 0));
        }
    }

    /**
     * DrawableAnimator implementation that changes drawable's size and alpha at the beginning and the end of animation.
     */
    @Keep
    class ScaleDrawableAnimator implements DrawableAnimator {

        private LikesAttributes mAttributes, mDefaultAttributes;

        @Override
        public void initialize(int layoutWidth, int layoutHeight, @NonNull LikesAttributes attributes, @NonNull LikesAttributes defaultAttributes) {
            mAttributes = attributes;
            mDefaultAttributes = defaultAttributes;
        }

        @Override
        public void onAnimationUpdate(float fraction, @NonNull Drawable drawable) {
            final float scale = Utilities.trapeze(fraction, 0, 0, 1, 0.25f, 1, 0.75f, 0, 1);
            final int alpha = (int) Utilities.trapeze(fraction, 0, 0, 255, 0.10f, 255, 0.90f, 0, 1);
            final Rect bounds = drawable.getBounds();
            final float halfW = mAttributes.getDrawableWidth(mDefaultAttributes) / 2f;
            final float halfH = mAttributes.getDrawableHeight(mDefaultAttributes) / 2f;
            final int l = (int) (bounds.centerX() - halfW * scale);
            final int t = (int) (bounds.centerY() - halfH * scale);
            final int r = (int) (bounds.centerX() + halfW * scale);
            final int b = (int) (bounds.centerY() + halfH * scale);
            drawable.setBounds(l, t, r, b);
            drawable.setAlpha(alpha);
        }
    }
}
