package ua.vlasov.likeslayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;

/**
 * Likes attributes model implementation.
 */
class LikesAttributesImpl implements LikesAttributes {

    /*
     * STATIC VARIABLES
     */

    private static final int ATTRIBUTE_MODE = 0;
    private static final int ATTRIBUTE_DRAWABLE = 1;
    private static final int ATTRIBUTE_DRAWABLE_WIDTH = 2;
    private static final int ATTRIBUTE_DRAWABLE_HEIGHT = 3;
    private static final int ATTRIBUTE_ANIMATION_DURATION = 4;
    private static final int ATTRIBUTE_PRODUCE_INTERVAL = 5;
    private static final int ATTRIBUTE_TINT_MODE = 6;
    private static final int ATTRIBUTE_TINT_COLORS = 7;
    private static final int ATTRIBUTE_DRAWABLE_ANIMATOR = 8;
    private static final int ATTRIBUTE_POSITION_ANIMATOR = 9;

    private static final int[][] STYLEABLES = new int[][]{
            R.styleable.LikesFrameLayout,
            R.styleable.LikesLinearLayout,
            R.styleable.LikesRelativeLayout
    };

    private static final int[][] ATTRIBUTES = new int[][]{
            {
                    R.styleable.LikesFrameLayout_likes_mode,
                    R.styleable.LikesFrameLayout_likes_drawable,
                    R.styleable.LikesFrameLayout_likes_drawableWidth,
                    R.styleable.LikesFrameLayout_likes_drawableHeight,
                    R.styleable.LikesFrameLayout_likes_animationDuration,
                    R.styleable.LikesFrameLayout_likes_produceInterval,
                    R.styleable.LikesFrameLayout_likes_tintMode,
                    R.styleable.LikesFrameLayout_likes_tintColors,
                    R.styleable.LikesFrameLayout_likes_drawableAnimator,
                    R.styleable.LikesFrameLayout_likes_drawablePositionAnimator,
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
                    R.styleable.LikesLinearLayout_likes_drawableAnimator,
                    R.styleable.LikesLinearLayout_likes_drawablePositionAnimator,
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
                    R.styleable.LikesRelativeLayout_likes_drawableAnimator,
                    R.styleable.LikesRelativeLayout_likes_drawablePositionAnimator,
            }
    };

    /*
     * INSTANCE VARIABLES
     */

    private int mLikesMode;
    private Drawable mDrawable;
    private float mDrawableWidth, mDrawableHeight;
    private int mAnimationDuration;
    private int mProduceInterval;
    private int mTintMode = TINT_MODE_NOT_SET;
    @Nullable
    private int[] mTintColors;
    private DrawableAnimator.Factory mDrawableAnimatorFactory;
    private PositionAnimator.Factory mPositionAnimatorFactory;

    private LikesAttributesImpl() {
        mDrawableAnimatorFactory = new DrawableAnimator.DefaultFactory();
        mPositionAnimatorFactory = new PositionAnimator.DefaultFactory();
    }

    public LikesAttributesImpl(LikesAttributesImpl other) {
        this.mLikesMode = other.mLikesMode;
        this.mDrawable = other.mDrawable;
        this.mDrawableWidth = other.mDrawableWidth;
        this.mDrawableHeight = other.mDrawableHeight;
        this.mAnimationDuration = other.mAnimationDuration;
        this.mProduceInterval = other.mProduceInterval;
        this.mTintMode = other.mTintMode;
        this.mTintColors = other.mTintColors;
        this.mDrawableAnimatorFactory = other.mDrawableAnimatorFactory;
        this.mPositionAnimatorFactory = other.mPositionAnimatorFactory;
    }


    @Override
    public Drawable getDrawable() {
        return mDrawable;
    }

    @Override
    public float getDrawableWidth() {
        return mDrawableWidth;
    }

    @Override
    public float getDrawableHeight() {
        return mDrawableHeight;
    }

    @Override
    public int getAnimationDuration() {
        return mAnimationDuration;
    }

    @Override
    public int getProduceInterval() {
        return mProduceInterval;
    }

    @Override
    public boolean isLikesModeEnabled() {
        return mLikesMode == LIKES_MODE_ENABLED;
    }

    @Override
    public boolean hasDrawable() {
        return mDrawable != null;
    }

    @Override
    @Nullable
    public int[] getTintColors() {
        return mTintColors;
    }

    @Override
    public int getTintMode() {
        return mTintMode;
    }

    @Override
    public DrawableAnimator.Factory getDrawableAnimatorFactory() {
        return mDrawableAnimatorFactory;
    }

    @Override
    public PositionAnimator.Factory getPositionAnimatorFactory() {
        return mPositionAnimatorFactory;
    }

    @Override
    public Drawable getDrawable(@NonNull LikesAttributes defaultAttributes) {
        if (mDrawable != null) {
            return mDrawable;
        }
        return defaultAttributes.getDrawable();
    }

    @Override
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

    @Override
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

    @Override
    public int getAnimationDuration(@NonNull LikesAttributes defaultAttributes) {
        if (mAnimationDuration > 0) {
            return mAnimationDuration;
        }
        return defaultAttributes.getAnimationDuration();
    }

    @Override
    public int getProduceInterval(@NonNull LikesAttributes defaultAttributes) {
        if (mProduceInterval > 0) {
            return mProduceInterval;
        }
        return defaultAttributes.getProduceInterval();
    }

    @Override
    public int getTintMode(@NonNull LikesAttributes defaultAttributes) {
        if (mTintMode != TINT_MODE_NOT_SET) {
            return mTintMode;
        }
        return defaultAttributes.getTintMode();
    }

    @Override
    public int[] getTintColors(@NonNull LikesAttributes defaultAttributes) {
        if (mTintColors != null) {
            return mTintColors;
        }
        return defaultAttributes.getTintColors();
    }

    @Override
    public boolean hasDrawable(@NonNull LikesAttributes defaultAttributes) {
        if (mDrawable != null) {
            return true;
        }
        return defaultAttributes.hasDrawable();
    }

    @Override
    public DrawableAnimator.Factory getDrawableAnimatorFactory(@NonNull LikesAttributes defaultAttributes) {
        if (mDrawableAnimatorFactory != null) {
            return mDrawableAnimatorFactory;
        }
        return defaultAttributes.getDrawableAnimatorFactory();
    }

    @Override
    public PositionAnimator.Factory getPositionAnimatorFactory(@NonNull LikesAttributes defaultAttributes) {
        if (mPositionAnimatorFactory != null) {
            return mPositionAnimatorFactory;
        }
        return defaultAttributes.getPositionAnimatorFactory();
    }

    @Override
    public void setDrawable(Drawable drawable) {
        mDrawable = drawable;
    }

    @Override
    public void setDrawableWidth(float drawableWidth) {
        mDrawableWidth = drawableWidth;
    }

    @Override
    public void setDrawableHeight(float drawableHeight) {
        mDrawableHeight = drawableHeight;
    }

    @Override
    public void setAnimationDuration(int animationDuration) {
        mAnimationDuration = animationDuration;
    }

    @Override
    public void setProduceInterval(int produceInterval) {
        mProduceInterval = produceInterval;
    }

    @Override
    public void setTintMode(int tintMode) {
        mTintMode = tintMode;
    }

    @Override
    public void setTintColors(@Nullable int[] tintColors) {
        mTintColors = tintColors;
    }

    @Override
    public void setDrawableAnimatorFactory(DrawableAnimator.Factory drawableAnimator) {
        mDrawableAnimatorFactory = drawableAnimator;
    }

    @Override
    public void setPositionAnimatorFactory(PositionAnimator.Factory positionAnimator) {
        mPositionAnimatorFactory = positionAnimator;
    }

    @Override
    public void setLikesMode(@LikesMode int likesMode) {
        mLikesMode = likesMode;
    }

    /*
     * FACTORY METHODS
     */

    /**
     * Create new LikesAttributes depending on the type.
     *
     * @param context instance of Context
     * @param attrs   instance of AttributesSet
     * @param type    attributes type
     * @return new LikesAttributes
     */
    @SuppressWarnings("TryWithIdenticalCatches")
    public static LikesAttributesImpl create(Context context, AttributeSet attrs, @AttributesType int type) {
        int defAnimDuration = context.getResources().getInteger(R.integer.likes_default_animation_duration);
        int defProdInterval = context.getResources().getInteger(R.integer.likes_default_produce_interval);
        LikesAttributesImpl holder = new LikesAttributesImpl();
        TypedArray array = context.obtainStyledAttributes(attrs, STYLEABLES[type]);
        int[] attributesArray = ATTRIBUTES[type];
        holder.mLikesMode = array.getInt(attributesArray[ATTRIBUTE_MODE], LIKES_MODE_DISABLED);
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
        String drawableAnimator = array.getString(attributesArray[ATTRIBUTE_DRAWABLE_ANIMATOR]);
        if (!TextUtils.isEmpty(drawableAnimator)) {
            try {
                //noinspection unchecked
                Class<? extends DrawableAnimator.Factory> cls = (Class<? extends DrawableAnimator.Factory>) Class.forName(drawableAnimator);
                holder.mDrawableAnimatorFactory = cls.newInstance();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if (holder.mDrawableAnimatorFactory == null) {
            holder.mDrawableAnimatorFactory = new DrawableAnimator.DefaultFactory();
        }
        String positionAnimator = array.getString(attributesArray[ATTRIBUTE_POSITION_ANIMATOR]);
        if (!TextUtils.isEmpty(positionAnimator)) {
            try {
                //noinspection unchecked
                Class<? extends PositionAnimator.Factory> cls = (Class<? extends PositionAnimator.Factory>) Class.forName(positionAnimator);
                holder.mPositionAnimatorFactory = cls.newInstance();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if (holder.mPositionAnimatorFactory == null) {
            holder.mPositionAnimatorFactory = new PositionAnimator.DefaultFactory();
        }
        array.recycle();
        return holder;
    }

    /**
     * Create empty LikesAttributes.
     *
     * @return empty LikesAttributes
     */
    public static LikesAttributesImpl empty() {
        return new LikesAttributesImpl();
    }

    /**
     * Create new LikesAttributes using provided instance.
     *
     * @param likesAttributes instance of LikesAttributes
     * @return copy of LikesAttributes
     */
    public static LikesAttributesImpl copy(@Nullable LikesAttributesImpl likesAttributes) {
        if (likesAttributes == null) {
            return empty();
        }
        return new LikesAttributesImpl(likesAttributes);
    }
}
