package ua.vlasov.likeslayout;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.concurrent.TimeUnit;

/**
 * RelativeLayout based implementation of likes layout.
 */
public class LikesRelativeLayout extends RelativeLayout implements LikesLayout {

    private LikesDrawer mLikesDrawer;
    private LikesAttributes mDefaultAttributes;
    private OnChildTouchListener mOnChildTouchListener;

    public LikesRelativeLayout(Context context) {
        this(context, null);
    }

    public LikesRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LikesRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LikesRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(@NonNull Context context, @Nullable AttributeSet attrs) {
        setWillNotDraw(false);
        if (attrs != null) {
            mDefaultAttributes = LikesAttributesImpl.create(context, attrs, LikesAttributes.LAYOUT_TYPE_RELATIVE);
        } else {
            mDefaultAttributes = LikesAttributesImpl.empty();
        }
        mLikesDrawer = new LikesDrawer(this, mDefaultAttributes);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        if (p instanceof LayoutParams) {
            return new LayoutParams(((LayoutParams) p));
        }
        return new LayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
        if (layoutParams.mAttributes.isLikesModeEnabled() && layoutParams.mAttributes.hasDrawable(mDefaultAttributes)) {
            child.setOnTouchListener(mLikesDrawer);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mLikesDrawer != null) {
            mLikesDrawer.onDraw(canvas);
        }
    }

    /**
     * Set instance of OnChildTouchListener.
     * @param onChildTouchListener instance of OnChildTouchListener
     */
    public void setOnChildTouchListener(@Nullable OnChildTouchListener onChildTouchListener) {
        mOnChildTouchListener = onChildTouchListener;
    }

    @Override
    public void onChildTouched(View child) {
        if (mOnChildTouchListener != null) {
            mOnChildTouchListener.onChildTouched(child);
        }
    }

    @Override
    public void onChildReleased(View child, boolean isCanceled) {
        if (mOnChildTouchListener != null) {
            mOnChildTouchListener.onChildReleased(child, isCanceled);
        }
    }

    @Override
    public void onLikeProduced(View child) {
        if (mOnChildTouchListener != null) {
            mOnChildTouchListener.onLikeProduced(child);
        }
    }

    @NonNull
    @Override
    public LayoutParamsBuilder newLayoutParamsBuilder(int width, int height) {
        return new LayoutParamsBuilder<>(new LayoutParams(width, height));
    }

    @NonNull
    @Override
    public LayoutParamsBuilder newLayoutParamsBuilder(@NonNull ViewGroup.LayoutParams source) {
        return new LayoutParamsBuilder<>(new LayoutParams(source));
    }

    @NonNull
    @Override
    public LikesAttributes getAttributes() {
        return mDefaultAttributes;
    }

    @Override
    public LikesProducer produceLikes(@NonNull View childView, long timeout) {
        if (childView.getLayoutParams() instanceof LayoutParams && childView.getParent() == this) {
            return mLikesDrawer.newLikesProducer(childView, timeout);
        }
        throw new IllegalArgumentException("Child view is not a child of this LikesLayout");
    }

    @Override
    public LikesProducer produceLikes(@NonNull View childView, long time, @NonNull TimeUnit timeUnit) {
        return produceLikes(childView, TimeUnit.MILLISECONDS.convert(time, timeUnit));
    }

    /**
     * Per-child layout information associated with LikesRelativeLayout.
     */
    public static class LayoutParams extends RelativeLayout.LayoutParams implements LikesLayoutParams {

        private final LikesAttributesImpl mAttributes;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            mAttributes = LikesAttributesImpl.create(c, attrs, LikesAttributes.LAYOUT_TYPE_RELATIVE);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
            mAttributes = LikesAttributesImpl.empty();
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
            mAttributes = LikesAttributesImpl.empty();
        }

        @SuppressWarnings("IncompleteCopyConstructor")
        @TargetApi(Build.VERSION_CODES.KITKAT)
        public LayoutParams(LayoutParams source) {
            super(source);
            mAttributes = LikesAttributesImpl.copy(source.mAttributes);
        }

        @Override
        public LikesAttributes getAttributes() {
            return mAttributes;
        }
    }
}
