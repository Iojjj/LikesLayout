package com.github.iojjj.likeslayout.app;

import com.github.iojjj.likeslayout.PositionAnimator;

/**
 * Custom position animator factory.
 */
public class CustomPositionAnimatorFactory2 implements PositionAnimator.Factory {

    @Override
    public PositionAnimator newInstance() {
        return new PositionAnimator.LinearRandomRoutePositionAnimator();
    }
}
