package com.github.iojjj.likeslayout;

import android.animation.FloatEvaluator;
import android.support.annotation.Keep;
import android.support.annotation.NonNull;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Interface of drawable's position animator. {@link #initialize(float, float, int, int, LikesAttributes, LikesAttributes)}
 * method of implementations of this interface will be called once to configure animator. Then subsequent calls of
 * {@link #onAnimationUpdate(float, float[])} will be performed.
 */
@Keep
public interface PositionAnimator {

    /**
     * X-coordinate index.
     */
    int POSITION_X = 0;

    /**
     * Y-coordinate index.
     */
    int POSITION_Y = 1;

    /**
     * Initialize animator.
     *
     * @param startX start center point's X-coordinate
     * @param startY start center point's Y-coordinate
     * @param layoutWidth       LikesLayout width
     * @param layoutHeight      LikesLayout height
     * @param attributes        attributes of drawable
     * @param defaultAttributes default attributes of LikesLayout
     */
    void initialize(float startX, float startY, int layoutWidth, int layoutHeight,
                    @NonNull LikesAttributes attributes, @NonNull LikesAttributes defaultAttributes);

    /**
     * Called every time of animation progress. Note that coordinates should represent a point in center of drawable.
     * <pre>
     * --------------
     * |            |
     * |     x      |
     * |            |
     * --------------
     * </pre>
     * where {@code x} - center point.
     *
     * @param fraction    animation fraction in range {@code [0, 1]}
     * @param newPosition array with length of 2 where you should save new drawables coordinates
     * @see #POSITION_X
     * @see #POSITION_Y
     */
    void onAnimationUpdate(float fraction, float[] newPosition);

    /**
     * Factory that creates new instances of DrawableAnimator.
     * Implementations of this factory must have public constructor without arguments.
     */
    @Keep
    interface Factory {

        /**
         * Create new instance of PositionAnimator.
         *
         * @return new instance of PositionAnimator
         */
        PositionAnimator newInstance();
    }

    @Keep
    class DefaultFactory implements Factory {

        private final FloatEvaluator mFloatEvaluator = new FloatEvaluator();

        private final Random mRandom = new Random();

        @Override
        public PositionAnimator newInstance() {
            return new SinPositionAnimator(mFloatEvaluator, mRandom);
        }
    }

    abstract class AbstractLinearPositionAnimator implements PositionAnimator {

        static final int PATH_CENTER = 0;
        static final int PATH_LEFT = 1;
        static final int PATH_RIGHT = 2;

        private final FloatEvaluator mPositionEvaluator;
        private float mStartX, mStartY, mEndX, mEndY;

        AbstractLinearPositionAnimator() {
            mPositionEvaluator = new FloatEvaluator();
        }

        @Override
        public void initialize(float startX, float startY,
                               int layoutWidth, int layoutHeight,
                               @NonNull LikesAttributes attributes,
                               @NonNull LikesAttributes defaultAttributes) {
            mStartX = startX;
            mStartY = startY;
            int path = getPath();
            if (path == PATH_CENTER) {
                mEndX = mStartX; // center
            } else if (path == PATH_LEFT) {
                mEndX = mStartX - attributes.getDrawableHeight(defaultAttributes) / 2f; // left
            } else {
                mEndX = mStartX + attributes.getDrawableHeight(defaultAttributes) / 2f; // right
            }
            mEndY = attributes.getDrawableHeight(defaultAttributes);
        }

        /**
         * Get the path for drawable.
         *
         * @return path for the drawable
         */
        protected abstract int getPath();

        @Override
        public void onAnimationUpdate(float fraction, float[] newPosition) {
            newPosition[POSITION_X] = mPositionEvaluator.evaluate(fraction, mStartX, mEndX);
            newPosition[POSITION_Y] = mPositionEvaluator.evaluate(fraction, mStartY, mEndY);
        }
    }

    /**
     * Linear position animator that randomly choose the route for moving drawable.
     */
    class LinearRandomRoutePositionAnimator extends AbstractLinearPositionAnimator {

        private final Random mRandom;

        public LinearRandomRoutePositionAnimator() {
            mRandom = new Random();
        }

        @Override
        protected int getPath() {
            return mRandom.nextInt(PATH_RIGHT + 1);
        }
    }

    /**
     * Linear position animator that randomly choose the route for moving drawable.
     */
    class LinearSuccessiveRoutePositionAnimator extends AbstractLinearPositionAnimator {

        private final AtomicInteger mCounter;

        /**
         * Constructor.
         * @param counter single instance of counter that belongs to factory.
         */
        public LinearSuccessiveRoutePositionAnimator(@NonNull AtomicInteger counter) {
            mCounter = counter;
        }

        @Override
        protected int getPath() {
            return mCounter.getAndIncrement() % (PATH_RIGHT + 1);
        }
    }

    /**
     * Implementation of PositionAnimator that animates drawable position using {@code sin()} function.
     */
    @Keep
    class SinPositionAnimator implements PositionAnimator {

        private static float sMaxAngle = 360.0f;

        private final FloatEvaluator mPositionEvaluator;
        private final Random mRandom;
        private float mStartX, mStartY, mEndY, mEndX;
        private float mCoefficient;

        public SinPositionAnimator() {
            mPositionEvaluator = new FloatEvaluator();
            mRandom = new Random();
        }

        public SinPositionAnimator(FloatEvaluator positionEvaluator, Random random) {
            mPositionEvaluator = positionEvaluator;
            mRandom = random;
        }

        @Override
        public void initialize(float startX, float startY,
                               int layoutWidth, int layoutHeight,
                               @NonNull LikesAttributes attributes,
                               @NonNull LikesAttributes defaultAttributes) {
            mStartX = startX;
            mStartY = startY;
            mEndX = attributes.getDrawableWidth(defaultAttributes) / 2;
            mEndY = attributes.getDrawableHeight(defaultAttributes);
            mCoefficient = mRandom.nextBoolean() ? 1 : -1;
        }

        @Override
        public void onAnimationUpdate(float fraction, float[] newPosition) {
            final double radians = Math.toRadians(sMaxAngle * fraction);
            newPosition[POSITION_X] = (float) (mStartX + Math.sin(radians) * mCoefficient * mEndX);
            newPosition[POSITION_Y] = mPositionEvaluator.evaluate(fraction, mStartY, mEndY);
        }

        @Keep
        public static void setMaxAngle(float maxAngle) {
            sMaxAngle = maxAngle;
        }
    }
}
