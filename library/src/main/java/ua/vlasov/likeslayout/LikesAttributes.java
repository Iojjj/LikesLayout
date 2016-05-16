package ua.vlasov.likeslayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Likes attributes model.
 */
class LikesAttributes {

    /*
     * STATIC VARIABLES
     */

    @IntDef({LAYOUT_TYPE_FRAME, LAYOUT_TYPE_LINEAR, LAYOUT_TYPE_RELATIVE})
    @interface AttributesType { }

    @IntDef({TINT_MODE_NOT_SET, TINT_MODE_OFF, TINT_MODE_ON_RANDOM, TINT_MODE_ON_SUCCESSIVELY})
    @interface TintMode {}

    /**
     * LikesFrameLayout type.
     */
    static final int LAYOUT_TYPE_FRAME = 0;

    /**
     * LikesLinearLayout type.
     */
    static final int LAYOUT_TYPE_LINEAR = 1;

    /**
     * LikesRelativeLayout type.
     */
    static final int LAYOUT_TYPE_RELATIVE = 2;

    /**
     * Likes mode disabled.
     */
    private static final int LIKES_MODE_DISABLED = 0;

    /**
     * Likes mode enabled.
     */
    private static final int LIKES_MODE_ENABLED = 1;

    /**
     * Tint mode not set.
     */
    static final int TINT_MODE_NOT_SET = -1;

    /**
     * Tint mode disabled.
     */
    static final int TINT_MODE_OFF = 0;

    /**
     * Tint mode enabled, use colors successively.
     */
    static final int TINT_MODE_ON_SUCCESSIVELY = 1;

    /**
     * Tint mode enabled, use colors randomly.
     */
    static final int TINT_MODE_ON_RANDOM = 2;

    private static final int ATTRIBUTE_MODE = 0;
    private static final int ATTRIBUTE_DRAWABLE = 1;
    private static final int ATTRIBUTE_DRAWABLE_WIDTH = 2;
    private static final int ATTRIBUTE_DRAWABLE_HEIGHT = 3;
    private static final int ATTRIBUTE_ANIMATION_DURATION = 4;
    private static final int ATTRIBUTE_PRODUCE_INTERVAL = 5;
    private static final int ATTRIBUTE_TINT_MODE = 6;
    private static final int ATTRIBUTE_TINT_COLORS = 7;

    private static final int[][] STYLEABLES = new int[][] {
            R.styleable.LikesFrameLayout,
            R.styleable.LikesLinearLayout,
            R.styleable.LikesRelativeLayout
    };

    private static final int[][] ATTRIBUTES = new int[][] {
            {
                    R.styleable.LikesFrameLayout_likes_mode,
                    R.styleable.LikesFrameLayout_likes_drawable,
                    R.styleable.LikesFrameLayout_likes_drawableWidth,
                    R.styleable.LikesFrameLayout_likes_drawableHeight,
                    R.styleable.LikesFrameLayout_likes_animationDuration,
                    R.styleable.LikesFrameLayout_likes_produceInterval,
                    R.styleable.LikesFrameLayout_likes_tintMode,
                    R.styleable.LikesFrameLayout_likes_tintColors,
            },
            {
                    R.styleable.LikesLinearLayout_likes_mode,
                    R.styleable.LikesLinearLayout_likes_drawable,
                    R.styleable.LikesLinearLayout_likes_drawableWidth,
                    R.styleable.LikesLinearLayout_likes_drawableHeight,
                    R.styleable.LikesLinearLayout_likes_animationDuration,
                    R.styleable.LikesLinearLayout_likes_produceInterval,
                    R.styleable.LikesLinearLayout_likes_tintMode,
                    R.styleable.LikesLinearLayout_likes_tintColors,
            },
            {
                    R.styleable.LikesRelativeLayout_likes_mode,
                    R.styleable.LikesRelativeLayout_likes_drawable,
                    R.styleable.LikesRelativeLayout_likes_drawableWidth,
                    R.styleable.LikesRelativeLayout_likes_drawableHeight,
                    R.styleable.LikesRelativeLayout_likes_animationDuration,
                    R.styleable.LikesRelativeLayout_likes_produceInterval,
                    R.styleable.LikesRelativeLayout_likes_tintMode,
                    R.styleable.LikesRelativeLayout_likes_tintColors,
            }
    };

    /*
     * INSTANCE VARIABLES
     */

    private int mType;
    private Drawable mDrawable;
    private float mDrawableWidth, mDrawableHeight;
    private int mAnimationDuration;
    private int mProduceInterval;
    private int mTintMode;
    @Nullable
    private int[] mTintColors;

    private LikesAttributes() {
        //no instance
    }

    private LikesAttributes(@NonNull LikesAttributes other) {
        this.mType = other.mType;
        this.mDrawable = other.mDrawable;
        this.mDrawableWidth = other.mDrawableWidth;
        this.mDrawableHeight = other.mDrawableHeight;
        this.mAnimationDuration = other.mAnimationDuration;
        this.mProduceInterval = other.mProduceInterval;
        this.mTintMode = other.mTintMode;
        this.mTintColors = other.mTintColors;
    }

    /**
     * Get likes drawable.
     * @return likes drawable
     */
    public Drawable getDrawable() {
        return mDrawable;
    }

    /**
     * Get custom drawable width.
     * @return custom drawable width
     */
    public float getDrawableWidth() {
        return mDrawableWidth;
    }

    /**
     * Get custom drawable height.
     * @return custom drawable height
     */
    public float getDrawableHeight() {
        return mDrawableHeight;
    }

    /**
     * Get animation duration in milliseconds.
     * @return animation duration
     */
    public int getAnimationDuration() {
        return mAnimationDuration;
    }

    /**
     * Get produce likes interval in milliseconds.
     * @return produce likes interval
     */
    public int getProduceInterval() {
        return mProduceInterval;
    }

    /**
     * Check if likes mode is enabled.
     * @return true if likes mode is enabled, false otherwise
     */
    public boolean isLikesModeEnabled() {
        return mType == LIKES_MODE_ENABLED;
    }

    /**
     * Get tint colors array.
     * @return tint colors array
     */
    @Nullable
    public int[] getTintColors() {
        return mTintColors;
    }

    /**
     * Get tint mode.
     * @return tint mode
     */
    @TintMode
    public int getTintMode() {
        return mTintMode;
    }

    /**
     * Get likes drawable or use default value.
     * @param defaultAttributes default attributes
     * @return likes drawable
     */
    public Drawable getDrawable(@NonNull LikesAttributes defaultAttributes) {
        if (mDrawable != null) {
            return mDrawable;
        }
        return defaultAttributes.getDrawable();
    }

    /**
     * Get custom drawable width or use default value.
     * @param defaultAttributes default attributes
     * @return custom drawable width
     */
    public float getDrawableWidth(@NonNull LikesAttributes defaultAttributes) {
        if (mDrawableWidth > 0) {
            return mDrawableWidth;
        }
        if (defaultAttributes.getDrawableWidth() > 0) {
            return defaultAttributes.getDrawableWidth();
        }
        if (mDrawable == null) {
            return 0;
        }
        return mDrawable.getIntrinsicWidth();
    }

    /**
     * Get custom drawable height or use default value.
     * @param defaultAttributes default attributes
     * @return custom drawable height
     */
    public float getDrawableHeight(@NonNull LikesAttributes defaultAttributes) {
        if (mDrawableHeight > 0) {
            return mDrawableHeight;
        }
        if (defaultAttributes.getDrawableHeight() > 0) {
            return defaultAttributes.getDrawableHeight();
        }
        if (mDrawable == null) {
            return 0;
        }
        return mDrawable.getIntrinsicHeight();
    }

    /**
     * Get animation duration or use default value.
     * @param defaultAttributes default attributes
     * @return animation duration
     */
    public int getAnimationDuration(@NonNull LikesAttributes defaultAttributes) {
        if (mAnimationDuration > 0) {
            return mAnimationDuration;
        }
        return defaultAttributes.getAnimationDuration();
    }

    /**
     * Get produce likes interval or use default value.
     * @param defaultAttributes default attributes
     * @return produce likes interval
     */
    public int getProduceInterval(@NonNull LikesAttributes defaultAttributes) {
        if (mProduceInterval > 0) {
            return mProduceInterval;
        }
        return defaultAttributes.getProduceInterval();
    }

    /**
     * Get tint mode or use default value.
     * @param defaultAttributes default attributes
     * @return tint mode
     */
    @TintMode
    public int getTintMode(@NonNull LikesAttributes defaultAttributes) {
        if (mTintMode != TINT_MODE_NOT_SET) {
            return mTintMode;
        }
        return defaultAttributes.getTintMode();
    }

    /**
     * Get ting colors or use default value.
     * @param defaultAttributes default attributes
     * @return tint colors
     */
    public int[] getTintColors(@NonNull LikesAttributes defaultAttributes) {
        if (mTintColors != null) {
            return mTintColors;
        }
        return defaultAttributes.getTintColors();
    }

    /*
     * FACTORY METHODS
     */

    /**
     * Create new LikesAttributes depending on the type.
     * @param context instance of Context
     * @param attrs instance of AttributesSet
     * @param type attributes type
     * @return new LikesAttributes
     */
    public static LikesAttributes create(Context context, AttributeSet attrs, @AttributesType int type) {
        int defAnimDuration = context.getResources().getInteger(R.integer.likes_default_animation_duration);
        int defProdInterval = context.getResources().getInteger(R.integer.likes_default_produce_interval);
        LikesAttributes holder = new LikesAttributes();
        TypedArray array = context.obtainStyledAttributes(attrs, STYLEABLES[type]);
        int[] attributesArray = ATTRIBUTES[type];
        holder.mType = array.getInt(attributesArray[ATTRIBUTE_MODE], LIKES_MODE_DISABLED);
        holder.mDrawable = array.getDrawable(attributesArray[ATTRIBUTE_DRAWABLE]);
        holder.mDrawableWidth = array.getDimension(attributesArray[ATTRIBUTE_DRAWABLE_WIDTH], 0);
        holder.mDrawableHeight = array.getDimension(attributesArray[ATTRIBUTE_DRAWABLE_HEIGHT], 0);
        holder.mAnimationDuration = array.getInt(attributesArray[ATTRIBUTE_ANIMATION_DURATION], defAnimDuration);
        holder.mProduceInterval = array.getInt(attributesArray[ATTRIBUTE_PRODUCE_INTERVAL], defProdInterval);
        holder.mTintMode = array.getInt(attributesArray[ATTRIBUTE_TINT_MODE], TINT_MODE_NOT_SET);
        int arrayId = array.getResourceId(attributesArray[ATTRIBUTE_TINT_COLORS], 0);
        if (arrayId != 0) {
            try {
                TypedArray colorsArray = array.getResources().obtainTypedArray(arrayId);
                int[] colors = new int[colorsArray.length()];
                for (int i = 0; i < colors.length; i++) {
                    colors[i] = colorsArray.getColor(i, Color.TRANSPARENT);
                }
                holder.mTintColors = colors;
                colorsArray.recycle();
            } catch (UnsupportedOperationException e) {
                // in editor mode
            }
        }
        array.recycle();
        return holder;
    }

    /**
     * Create empty LikesAttributes.
     * @return empty LikesAttributes
     */
    public static LikesAttributes empty() {
        return new LikesAttributes();
    }

    /**
     * Create new LikesAttributes using provided instance.
     * @param likesAttributes instance of LikesAttributes
     * @return copy of LikesAttributes
     */
    public static LikesAttributes copy(@Nullable LikesAttributes likesAttributes) {
        if (likesAttributes == null) {
            return empty();
        }
        return new LikesAttributes(likesAttributes);
    }
}
