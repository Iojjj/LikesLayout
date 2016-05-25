package com.github.iojjj.likeslayout.app;

import com.github.iojjj.likeslayout.DrawableAnimator;

/**
 * Custom drawable animator factory.
 */
public class CustomDrawableAnimatorFactory implements DrawableAnimator.Factory {
    @Override
    public DrawableAnimator newInstance() {
        return new DrawableAnimator.AlphaDrawableAnimator();
    }
}
