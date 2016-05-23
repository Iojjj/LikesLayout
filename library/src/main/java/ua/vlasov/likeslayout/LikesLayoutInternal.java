package ua.vlasov.likeslayout;

/**
 * LikesLayout interface for internal usage.
 */
interface LikesLayoutInternal extends OnChildTouchListener {

    void invalidate();

    int getWidth();

    int getHeight();
}
