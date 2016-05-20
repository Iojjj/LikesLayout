package ua.vlasov.likeslayout;

import android.animation.IntEvaluator;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

/**
 * Created by Alexander on 21.05.2016.
 */
class AlphaDrawableAnimator implements DrawableAnimator {

    private final IntEvaluator mAlphaEvaluator = new IntEvaluator();

    @Override
    public void initialize(int layoutWidth, int layoutHeight,
                           @NonNull LikesAttributes attributes, @NonNull LikesAttributes defaultAttributes) {

    }

    @Override
    public void onAnimationUpdate(float fraction, @NonNull Drawable drawable) {
        drawable.setAlpha(mAlphaEvaluator.evaluate(fraction, 255, 0));
    }
}
