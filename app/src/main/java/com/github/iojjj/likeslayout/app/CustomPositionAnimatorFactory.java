package com.github.iojjj.likeslayout.app;

import com.github.iojjj.likeslayout.PositionAnimator;

import java.util.concurrent.atomic.AtomicInteger;

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
