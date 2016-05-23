package ua.vlasov.likeslayout;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

/**
 * LikesLayout interface.
 */
public interface LikesLayout extends LikesLayoutInternal {

    /**
     * Create new layout params builder.
     *
     * @param width  width
     * @param height height
     * @return new layout params builder
     */
    @NonNull
    LayoutParamsBuilder newLayoutParamsBuilder(int width, int height);

    /**
     * Create new layout params builder.
     *
     * @param source source layout params
     * @return new layout params builder
     */
    @NonNull
    LayoutParamsBuilder newLayoutParamsBuilder(@NonNull ViewGroup.LayoutParams source);

    @NonNull
    LikesAttributes getAttributes();

    class LayoutParamsBuilder<T extends ViewGroup.LayoutParams & LikesLayoutParams> {

        private final T mLayoutParams;

        LayoutParamsBuilder(@NonNull T layoutParams) {
            mLayoutParams = layoutParams;
        }

        public LayoutParamsBuilder setDrawable(Drawable drawable) {
            mLayoutParams.getAttributes().setDrawable(drawable);
            return this;
        }

        public LayoutParamsBuilder setDrawableWidth(float drawableWidth) {
            mLayoutParams.getAttributes().setDrawableWidth(drawableWidth);
            return this;
        }

        public LayoutParamsBuilder setDrawableHeight(float drawableHeight) {
            mLayoutParams.getAttributes().setDrawableHeight(drawableHeight);
            return this;
        }

        public LayoutParamsBuilder setAnimationDuration(int animationDuration) {
            mLayoutParams.getAttributes().setAnimationDuration(animationDuration);
            return this;
        }

        public LayoutParamsBuilder setProduceInterval(int produceInterval) {
            mLayoutParams.getAttributes().setProduceInterval(produceInterval);
            return this;
        }

        public LayoutParamsBuilder setTintMode(@LikesAttributes.TintMode int tintMode) {
            mLayoutParams.getAttributes().setTintMode(tintMode);
            return this;
        }

        public LayoutParamsBuilder setTintColors(@Nullable int[] tintColors) {
            mLayoutParams.getAttributes().setTintColors(tintColors);
            return this;
        }

        public LayoutParamsBuilder setDrawableAnimatorFactory(@Nullable DrawableAnimator.Factory drawableAnimator) {
            mLayoutParams.getAttributes().setDrawableAnimatorFactory(drawableAnimator);
            return this;
        }

        public LayoutParamsBuilder setPositionAnimatorFactory(@Nullable PositionAnimator.Factory positionAnimator) {
            mLayoutParams.getAttributes().setPositionAnimatorFactory(positionAnimator);
            return this;
        }

        public LayoutParamsBuilder setLikesMode(@LikesAttributes.LikesMode int likesMode) {
            mLayoutParams.getAttributes().setLikesMode(likesMode);
            return this;
        }

        public ViewGroup.LayoutParams build() {
            return mLayoutParams;
        }
    }
}
