package ua.vlasov.likeslayout;

import android.animation.FloatEvaluator;
import android.support.annotation.Keep;
import android.support.annotation.NonNull;

import java.util.Random;

/**
 * Created by Alexander on 21.05.2016.
 */
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
