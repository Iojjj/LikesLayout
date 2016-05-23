package ua.vlasov.likes_layout;

import ua.vlasov.likeslayout.DrawableAnimator;

/**
 * Custom drawable animator factory.
 */
public class CustomDrawableAnimatorFactory implements DrawableAnimator.Factory {
    @Override
    public DrawableAnimator newInstance() {
        return new DrawableAnimator.AlphaDrawableAnimator();
    }
}
