package ua.vlasov.likeslayout;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.Log;

/**
 * Created by Alexander on 21.05.2016.
 */
public class ScaleDrawableAnimator implements DrawableAnimator {

    private LikesAttributes mAttributes, mDefaultAttributes;

    @Override
    public void initialize(int layoutWidth, int layoutHeight, @NonNull LikesAttributes attributes, @NonNull LikesAttributes defaultAttributes) {
        mAttributes = attributes;
        mDefaultAttributes = defaultAttributes;
    }

    @Override
    public void onAnimationUpdate(float fraction, @NonNull Drawable drawable) {
        final float scale = DrawUtil.trapeze(fraction, 0, 0, 1, 0.25f, 1, 0.75f, 0, 1);
        final int alpha = (int) DrawUtil.trapeze(fraction, 0, 0, 255, 0.10f, 255, 0.90f, 0, 1);
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
