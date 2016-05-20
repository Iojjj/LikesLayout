package ua.vlasov.likeslayout;

import android.support.annotation.NonNull;

/**
 * Created by Alexander on 21.05.2016.
 */
public interface PositionAnimator {

    int POSITION_X = 0;
    int POSITION_Y = 1;

    void initialize(float startX, float startY, int layoutWidth, int layoutHeight,
                    @NonNull LikesAttributes attributes, @NonNull LikesAttributes defaultAttributes);

    void onAnimationUpdate(float fraction, float[] newPosition);
}
