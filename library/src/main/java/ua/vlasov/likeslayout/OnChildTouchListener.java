package ua.vlasov.likeslayout;

import android.view.MotionEvent;
import android.view.View;

/**
 * Listener for touch events of child views.
 */
public interface OnChildTouchListener {

    /**
     * Called when child was touched.
     * @param child child view
     */
    void onChildTouched(View child);

    /**
     * Called when child was released.
     * @param child child view
     * @param isCanceled true if view was released because of {@link MotionEvent#ACTION_CANCEL} event, false otherwise
     */
    void onChildReleased(View child, boolean isCanceled);

    /**
     * Called when likes is produced.
     * @param child child view
     */
    void onLikeProduced(View child);
}
