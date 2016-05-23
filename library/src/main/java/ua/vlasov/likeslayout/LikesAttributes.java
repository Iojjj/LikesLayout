package ua.vlasov.likeslayout;

import android.graphics.drawable.Drawable;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Likes attributes model.
 */
public interface LikesAttributes {

    /**
     * LikesFrameLayout type.
     */
    int LAYOUT_TYPE_FRAME = 0;
    /**
     * LikesLinearLayout type.
     */
    int LAYOUT_TYPE_LINEAR = 1;
    /**
     * LikesRelativeLayout type.
     */
    int LAYOUT_TYPE_RELATIVE = 2;
    /**
     * Likes mode disabled.
     */
    int LIKES_MODE_DISABLED = 0;
    /**
     * Likes mode enabled.
     */
    int LIKES_MODE_ENABLED = 1;
    /**
     * Tint mode not set.
     */
    int TINT_MODE_NOT_SET = -1;
    /**
     * Tint mode disabled.
     */
    int TINT_MODE_OFF = 0;
    /**
     * Tint mode enabled, use colors successively.
     */
    int TINT_MODE_ON_SUCCESSIVELY = 1;
    /**
     * Tint mode enabled, use colors randomly.
     */
    int TINT_MODE_ON_RANDOM = 2;

    /**
     * Get likes drawable.
     *
     * @return likes drawable
     */
    Drawable getDrawable();

    /**
     * Get custom drawable width.
     *
     * @return custom drawable width
     */
    float getDrawableWidth();

    /**
     * Get custom drawable height.
     *
     * @return custom drawable height
     */
    float getDrawableHeight();

    /**
     * Get animation duration in milliseconds.
     *
     * @return animation duration
     */
    int getAnimationDuration();

    /**
     * Get produce likes interval in milliseconds.
     *
     * @return produce likes interval
     */
    int getProduceInterval();

    /**
     * Check if likes mode is enabled.
     *
     * @return true if likes mode is enabled, false otherwise
     */
    boolean isLikesModeEnabled();

    /**
     * Check if drawable is set.
     *
     * @return true if drawable is set, false otherwise
     */
    boolean hasDrawable();

    /**
     * Get tint colors array.
     *
     * @return tint colors array
     */
    @Nullable
    int[] getTintColors();

    /**
     * Get tint mode.
     *
     * @return tint mode
     */
    @TintMode
    int getTintMode();

    /**
     * Get instance of DrawableAnimator.Factory.
     *
     * @return instance of DrawableAnimator.Factory
     */
    DrawableAnimator.Factory getDrawableAnimatorFactory();

    /**
     * Get instance of PositionAnimator.Factory.
     *
     * @return instance of PositionAnimator.Factory
     */
    PositionAnimator.Factory getPositionAnimatorFactory();

    /**
     * Get likes drawable or use default value.
     *
     * @param defaultAttributes default attributes
     * @return likes drawable
     */
    Drawable getDrawable(@NonNull LikesAttributes defaultAttributes);

    /**
     * Get custom drawable width or use default value.
     *
     * @param defaultAttributes default attributes
     * @return custom drawable width
     */
    float getDrawableWidth(@NonNull LikesAttributes defaultAttributes);

    /**
     * Get custom drawable height or use default value.
     *
     * @param defaultAttributes default attributes
     * @return custom drawable height
     */
    float getDrawableHeight(@NonNull LikesAttributes defaultAttributes);

    /**
     * Get animation duration or use default value.
     *
     * @param defaultAttributes default attributes
     * @return animation duration
     */
    int getAnimationDuration(@NonNull LikesAttributes defaultAttributes);

    /**
     * Get produce likes interval or use default value.
     *
     * @param defaultAttributes default attributes
     * @return produce likes interval
     */
    int getProduceInterval(@NonNull LikesAttributes defaultAttributes);

    /**
     * Get tint mode or use default value.
     *
     * @param defaultAttributes default attributes
     * @return tint mode
     */
    @TintMode
    int getTintMode(@NonNull LikesAttributes defaultAttributes);

    /**
     * Get ting colors or use default value.
     *
     * @param defaultAttributes default attributes
     * @return tint colors
     */
    int[] getTintColors(@NonNull LikesAttributes defaultAttributes);

    /**
     * Check if drawable is set or default value exists.
     *
     * @param defaultAttributes default attributes
     * @return true if drawable is set, false otherwise
     */
    boolean hasDrawable(@NonNull LikesAttributes defaultAttributes);

    /**
     * Get instance of DrawableAnimator or default one.
     *
     * @return instance of DrawableAnimator
     */
    DrawableAnimator.Factory getDrawableAnimatorFactory(@NonNull LikesAttributes defaultAttributes);

    /**
     * Get instance of PositionAnimator or default one.
     *
     * @return instance of PositionAnimator
     */
    PositionAnimator.Factory getPositionAnimatorFactory(@NonNull LikesAttributes defaultAttributes);

    /**
     * Set likes drawable.
     *
     * @param drawable likes drawable or null
     */
    void setDrawable(@Nullable Drawable drawable);

    /**
     * Set custom drawable width in pixels.
     *
     * @param drawableWidth custom drawable width
     */
    void setDrawableWidth(float drawableWidth);

    /**
     * Set custom drawable height in pixels.
     *
     * @param drawableHeight custom drawable height
     */
    void setDrawableHeight(float drawableHeight);

    /**
     * Set animation duration in milliseconds.
     *
     * @param animationDuration animation duration
     */
    void setAnimationDuration(int animationDuration);

    /**
     * Set produce likes interval in milliseconds.
     *
     * @param produceInterval produce likes interval
     */
    void setProduceInterval(int produceInterval);

    /**
     * Set drawable tint mode.
     *
     * @param tintMode drawable tint mode
     */
    void setTintMode(@TintMode int tintMode);

    /**
     * Set drawable tint colors.
     *
     * @param tintColors drawable tint colors or null
     */
    void setTintColors(@Nullable int[] tintColors);

    /**
     * Set drawable animator factory.
     *
     * @param factory instance of factory that create new instances of DrawableAnimator
     */
    void setDrawableAnimatorFactory(@Nullable DrawableAnimator.Factory factory);

    /**
     * Set drawable's position animator.
     *
     * @param factory instance of factory that create new instances of PositionAnimator
     */
    void setPositionAnimatorFactory(@Nullable PositionAnimator.Factory factory);

    /**
     * Set likes mode.
     *
     * @param likesMode likes mode
     */
    void setLikesMode(@LikesMode int likesMode);

    @IntDef({LAYOUT_TYPE_FRAME, LAYOUT_TYPE_LINEAR, LAYOUT_TYPE_RELATIVE})
    @interface AttributesType {
    }

    @IntDef({TINT_MODE_NOT_SET, TINT_MODE_OFF, TINT_MODE_ON_RANDOM, TINT_MODE_ON_SUCCESSIVELY})
    @interface TintMode {
    }

    @IntDef({LIKES_MODE_DISABLED, LIKES_MODE_ENABLED})
    @interface LikesMode {
    }
}
