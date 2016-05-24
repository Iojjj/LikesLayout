package ua.vlasov.likeslayout;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Likes drawer class. It detects touch events and produces likes.
 */
class LikesDrawer implements View.OnTouchListener {

    private final SparseArray<DrawingTaskProducer> mProducers;
    private final List<DrawingTask> mDrawingTasks;
    private final LikesAttributes mDefaultAttributes;
    private final LikesLayoutInternal mLikesLayout;
    private final Handler mUiHandler;
    private final Interpolator mInterpolator;
    private final Random mRandom;
    private final SparseArray<Integer> mTintColorIndexes;
    private final Queue<CustomValueAnimator> mAvailableValueAnimators;
    private final Queue<DrawingTask> mAvailableDrawingTasks;
    private final Queue<DrawingTaskProducer> mAvailableDrawingTaskProducers;
    private final SmartInvalidator mSmartInvalidator;

    public LikesDrawer(@NonNull LikesLayoutInternal likesLayout, @NonNull LikesAttributes defaultAttributes) {
        mDefaultAttributes = defaultAttributes;
        mLikesLayout = likesLayout;
        mUiHandler = new Handler(Looper.getMainLooper());
        mInterpolator = new LinearInterpolator();
        mRandom = new Random();
        mProducers = new SparseArray<>();
        mDrawingTasks = new CopyOnWriteArrayList<>();
        mTintColorIndexes = new SparseArray<>();
        mAvailableValueAnimators = new LinkedList<>();
        mAvailableDrawingTasks = new LinkedList<>();
        mAvailableDrawingTaskProducers = new LinkedList<>();
        mSmartInvalidator = new SmartInvalidator();
    }

    public void onDraw(@NonNull Canvas canvas) {
        for (DrawingTask drawingTask : mDrawingTasks) {
            drawingTask.mDrawable.draw(canvas);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            startProducingLikes(v, true);
            mLikesLayout.onChildTouched(v);
            v.setPressed(true);
        } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
            stopProducingLikes(v, mProducers.get(v.getId()), true);
            mLikesLayout.onChildReleased(v, event.getAction() == MotionEvent.ACTION_CANCEL);
            v.setPressed(false);
        }
        return true;
    }

    public LikesProducer newLikesProducer(@NonNull View v, long timeout) {
        LikesProducerImpl producer = new LikesProducerImpl(v);
        producer.start(timeout);
        return producer;
    }

    private DrawingTaskProducer startProducingLikes(View v, boolean fromTouch) {
        LikesAttributes attributes = ((LikesLayoutParams) v.getLayoutParams()).getAttributes();
        DrawingTaskProducer drawingTaskProducer;
        if (fromTouch) {
            drawingTaskProducer = mProducers.get(v.getId());
            if (drawingTaskProducer == null) {
                drawingTaskProducer = getAvailableDrawingTaskProducer(attributes, v);
                mProducers.put(v.getId(), drawingTaskProducer);
                drawingTaskProducer.run();
            }
        } else {
            drawingTaskProducer = getAvailableDrawingTaskProducer(attributes, v);
            drawingTaskProducer.run();
        }
        return drawingTaskProducer;
    }

    @NonNull
    private DrawingTaskProducer getAvailableDrawingTaskProducer(LikesAttributes attributes, View v) {
        DrawingTaskProducer drawingTaskProducer = mAvailableDrawingTaskProducers.poll();
        if (drawingTaskProducer == null) {
            drawingTaskProducer = new DrawingTaskProducer(attributes, v);
        } else {
            drawingTaskProducer.initialize(attributes, v);
        }
        return drawingTaskProducer;
    }

    private void stopProducingLikes(@NonNull View v, @Nullable DrawingTaskProducer drawingTaskProducer, boolean fromTouch) {
        if (drawingTaskProducer != null) {
            drawingTaskProducer.setCancelled(true);
            mUiHandler.removeCallbacks(drawingTaskProducer);
            mAvailableDrawingTaskProducers.add(drawingTaskProducer);
        }
        if (fromTouch) {
            mProducers.remove(v.getId());
        }
    }

    private class DrawingTask implements Animator.AnimatorListener, ValueAnimator.AnimatorUpdateListener {

        private final PositionAnimator mPositionAnimator;
        private final DrawableAnimator mDrawableAnimator;
        private final float[] mPositions;
        private Drawable mBaseDrawable;
        private Drawable mDrawable;

        public DrawingTask(LikesAttributes attributes, float cx, float cy, int viewId) {
            mPositions = new float[2];
            mPositionAnimator = attributes.getPositionAnimatorFactory(mDefaultAttributes).newInstance();
            mDrawableAnimator = attributes.getDrawableAnimatorFactory(mDefaultAttributes).newInstance();
            initialize(attributes, cx, cy, viewId);
        }

        private void initialize(LikesAttributes attributes, float cx, float cy, int viewId) {
            Drawable baseDrawable = attributes.getDrawable(mDefaultAttributes);
            if (baseDrawable != mBaseDrawable) {
                // update current drawable only if base drawables aren't the same
                mDrawable = attributes.getDrawable(mDefaultAttributes).getConstantState().newDrawable().mutate();
                mBaseDrawable = baseDrawable;
            }
            final float halfW = attributes.getDrawableWidth(mDefaultAttributes) / 2;
            final float halfH = attributes.getDrawableHeight(mDefaultAttributes) / 2;
            mDrawable.setBounds((int) (cx - halfW), ((int) (cy - halfH)), ((int) (cx + halfW)), ((int) (cy + halfH)));
            int tintMode = attributes.getTintMode(mDefaultAttributes);
            if (tintMode != LikesAttributes.TINT_MODE_OFF && tintMode != LikesAttributes.TINT_MODE_NOT_SET) {
                int[] tintColors = attributes.getTintColors(mDefaultAttributes);
                if (tintColors != null && tintColors.length > 0) {
                    if (tintMode == LikesAttributes.TINT_MODE_ON_SUCCESSIVELY) {
                        Integer index = mTintColorIndexes.get(viewId);
                        if (index == null) {
                            index = -1;
                        }
                        index += 1;
                        if (index >= tintColors.length) {
                            index = 0;
                        }
                        DrawableCompat.setTint(mDrawable, tintColors[index]);
                        mTintColorIndexes.put(viewId, index);
                    } else {
                        int index = 0;
                        if (tintColors.length > 1) {
                            index = mRandom.nextInt(tintColors.length);
                        }
                        DrawableCompat.setTint(mDrawable, tintColors[index]);
                    }
                }
            }
            mPositionAnimator.initialize(cx, cy, mLikesLayout.getWidth(), mLikesLayout.getHeight(),
                    attributes, mDefaultAttributes);
            mDrawableAnimator.initialize(mLikesLayout.getWidth(), mLikesLayout.getHeight(), attributes, mDefaultAttributes);
        }

        private void updatePosition() {
            final float halfW = mDrawable.getBounds().width() / 2;
            final float halfH = mDrawable.getBounds().height() / 2;
            final float cx = mPositions[PositionAnimator.POSITION_X];
            final float cy = mPositions[PositionAnimator.POSITION_Y];
            mDrawable.setBounds((int) (cx - halfW), ((int) (cy - halfH)), ((int) (cx + halfW)), ((int) (cy + halfH)));
        }

        @Override
        public void onAnimationStart(Animator animation) {
            mDrawingTasks.add(this);
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            animation.removeListener(this);
            mDrawingTasks.remove(this);
            mAvailableDrawingTasks.add(this);
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            Arrays.fill(mPositions, 0.0f);
            mPositionAnimator.onAnimationUpdate(animation.getAnimatedFraction(), mPositions);
            updatePosition();
            mDrawableAnimator.onAnimationUpdate(animation.getAnimatedFraction(), mDrawable);
        }
    }

    private class DrawingTaskProducer implements Runnable {

        private LikesAttributes mAttributes;
        private float mCx, mCy;
        private View mView;
        private boolean mCancelled;

        public DrawingTaskProducer(@NonNull LikesAttributes attributes, @NonNull View view) {
            initialize(attributes, view);
        }

        private void initialize(@NonNull LikesAttributes attributes, @NonNull View view) {
            mAttributes = attributes;
            mView = view;
            mCx = view.getLeft() + view.getWidth() / 2f;
            mCy = view.getTop() + view.getHeight() / 2f;
            mCancelled = false;
        }

        @Override
        public void run() {
            if (mCancelled) {
                return;
            }
            startDrawingTask(mView.getId());
            mLikesLayout.onLikeProduced(mView);
            mUiHandler.postDelayed(this, mAttributes.getProduceInterval(mDefaultAttributes));
        }

        public void setCancelled(boolean cancelled) {
            mCancelled = cancelled;
        }

        private void startDrawingTask(int viewId) {
            final DrawingTask drawingTask = getAvailableDrawingTask(viewId, mAttributes, mCx, mCy);
            ValueAnimator animator = getAvailableAnimator();
            animator.setDuration(mAttributes.getAnimationDuration(mDefaultAttributes));
            animator.addListener(drawingTask);
            animator.addUpdateListener(drawingTask);
            animator.start();
        }
    }

    @NonNull
    private DrawingTask getAvailableDrawingTask(int viewId, LikesAttributes attributes, float cx, float cy) {
        DrawingTask drawingTask = mAvailableDrawingTasks.poll();
        if (drawingTask == null) {
            drawingTask = new DrawingTask(attributes, cx, cy, viewId);
        } else {
            drawingTask.initialize(attributes, cx, cy, viewId);
        }
        return drawingTask;
    }

    @NonNull
    private ValueAnimator getAvailableAnimator() {
        CustomValueAnimator animator = mAvailableValueAnimators.poll();
        if (animator == null) {
            animator = new CustomValueAnimator();
            animator.setFloatValues(0, 1);
            animator.setInterpolator(mInterpolator);
            animator.addListener(animator);
            animator.addListener(mSmartInvalidator);
        }
        return animator;
    }

    /**
     * LikesProducer implementation.
     */
    private class LikesProducerImpl implements LikesProducer {

        private final View mChildView;
        private boolean mStopped;
        private DrawingTaskProducer mDrawingTaskProducer;

        public LikesProducerImpl(@NonNull View childView) {
            mChildView = childView;
        }

        public void start(long delay) {
            mDrawingTaskProducer = startProducingLikes(mChildView, false);
            mUiHandler.postDelayed(this::stop, delay);
        }

        @Override
        public void stop() {
            if (mStopped) {
                return;
            }
            mStopped = true;
            stopProducingLikes(mChildView, mDrawingTaskProducer, false);
        }
    }

    private class CustomValueAnimator extends ValueAnimator implements Animator.AnimatorListener {

        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {
            mAvailableValueAnimators.add(this);
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    }

    private class SmartInvalidator implements ValueAnimator.AnimatorUpdateListener, Animator.AnimatorListener {

        private final AtomicInteger mAnimationsCounter;
        private final ValueAnimator mValueAnimator;

        public SmartInvalidator() {
            mAnimationsCounter = new AtomicInteger();
            mValueAnimator = ValueAnimator.ofFloat(0, 1);
            mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
            mValueAnimator.setDuration(1000);
            mValueAnimator.setInterpolator(mInterpolator);
            mValueAnimator.addUpdateListener(this);
        }

        public void onAnimationStarted() {
            mAnimationsCounter.incrementAndGet();
            if (!mValueAnimator.isRunning()) {
                mValueAnimator.start();
            }
        }

        public void onAnimationEnded() {
            final int count = mAnimationsCounter.decrementAndGet();
            if (count <= 0) {
                if (mValueAnimator.isRunning()) {
                    mValueAnimator.cancel();
                }
            }
        }

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            mLikesLayout.invalidate();
        }

        @Override
        public void onAnimationStart(Animator animation) {
            onAnimationStarted();
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            onAnimationEnded();
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    }
}
