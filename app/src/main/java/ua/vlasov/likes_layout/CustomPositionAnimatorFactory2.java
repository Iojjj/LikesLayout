package ua.vlasov.likes_layout;

import ua.vlasov.likeslayout.PositionAnimator;

/**
 * Custom position animator factory.
 */
public class CustomPositionAnimatorFactory2 implements PositionAnimator.Factory {

    @Override
    public PositionAnimator newInstance() {
        return new PositionAnimator.LinearRandomRoutePositionAnimator();
    }
}
