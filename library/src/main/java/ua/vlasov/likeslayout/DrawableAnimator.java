package ua.vlasov.likeslayout;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

/**
 * Created by Alexander on 21.05.2016.
 */
public interface DrawableAnimator {

    void initialize(int layoutWidth, int layoutHeight,
                    @NonNull LikesAttributes attributes, @NonNull LikesAttributes defaultAttributes);

    void onAnimationUpdate(float fraction, @NonNull Drawable drawable);
}
