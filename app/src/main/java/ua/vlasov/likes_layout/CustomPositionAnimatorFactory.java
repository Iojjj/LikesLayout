package ua.vlasov.likes_layout;

import java.util.concurrent.atomic.AtomicInteger;

import ua.vlasov.likeslayout.PositionAnimator;

/**
 * Custom position animator factory.
 */
public class CustomPositionAnimatorFactory implements PositionAnimator.Factory {

    private final AtomicInteger pathGenerator = new AtomicInteger();

    @Override
    public PositionAnimator newInstance() {
        return new PositionAnimator.LinearSuccessiveRoutePositionAnimator(pathGenerator);
    }
}
