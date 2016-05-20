package ua.vlasov.likeslayout;

import android.animation.FloatEvaluator;
import android.support.annotation.NonNull;

import java.util.Random;

/**
 * Created by Alexander on 21.05.2016.
 */
class LinearPositionAnimator implements PositionAnimator {

    private final FloatEvaluator mPositionEvaluator;
    private final Random mRandom;
    private float mStartX, mStartY, mEndX, mEndY;

    public LinearPositionAnimator() {
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
        int path = mRandom.nextInt(3);
        if (path == 0) {
            mEndX = mStartX; // center
        } else if (path == 1) {
            mEndX = mStartX - attributes.getDrawableHeight(defaultAttributes) / 2f; // left
        } else {
            mEndX = mStartX + attributes.getDrawableHeight(defaultAttributes) / 2f; // right
        }
        mEndY = attributes.getDrawableHeight(defaultAttributes);
    }

    @Override
    public void onAnimationUpdate(float fraction, float[] newPosition) {
        newPosition[POSITION_X] = mPositionEvaluator.evaluate(fraction, mStartX, mEndX);
        newPosition[POSITION_Y] = mPositionEvaluator.evaluate(fraction, mStartY, mEndY);
    }
}
