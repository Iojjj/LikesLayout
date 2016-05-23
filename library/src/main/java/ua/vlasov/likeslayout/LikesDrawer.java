package ua.vlasov.likeslayout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

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
    private final ValueAnimator.AnimatorUpdateListener mUpdateListener;
    private final Random mRandom;
    private final SparseArray<Integer> mTintColorIndexes;

    public LikesDrawer(@NonNull LikesLayoutInternal likesLayout, @NonNull LikesAttributes defaultAttributes) {
        mDefaultAttributes = defaultAttributes;
        mLikesLayout = likesLayout;
        mUiHandler = new Handler(Looper.getMainLooper());
        mInterpolator = new LinearInterpolator();
        mRandom = new Random();
        mUpdateListener = animation -> mLikesLayout.invalidate();
        mProducers = new SparseArray<>();
        mDrawingTasks = new CopyOnWriteArrayList<>();
        mTintColorIndexes = new SparseArray<>();
    }

    public void onDraw(@NonNull Canvas canvas) {
        for (DrawingTask drawingTask : mDrawingTasks) {
            drawingTask.mDrawable.draw(canvas);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            LikesAttributes attributes = ((LikesLayoutParams) v.getLayoutParams()).getAttributes();
            DrawingTaskProducer drawingTaskProducer = mProducers.get(v.getId());
            if (drawingTaskProducer == null) {
                drawingTaskProducer = new DrawingTaskProducer(attributes, v);
                mProducers.put(v.getId(), drawingTaskProducer);
            }
            drawingTaskProducer.run();
            mLikesLayout.onChildTouched(v);
            v.setPressed(true);
        } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
            DrawingTaskProducer drawingTaskProducer = mProducers.get(v.getId());
            if (drawingTaskProducer != null) {
                drawingTaskProducer.setCancelled(true);
                mUiHandler.removeCallbacks(drawingTaskProducer);
                mProducers.remove(v.getId());
            }
            mLikesLayout.onChildReleased(v, event.getAction() == MotionEvent.ACTION_CANCEL);
            v.setPressed(false);
        }
        return true;
    }

    private class DrawingTask {

        private final Drawable mDrawable;
        private final PositionAnimator mPositionAnimator;
        private final DrawableAnimator mDrawableAnimator;
        private final float[] mPositions;

        public DrawingTask(LikesAttributes attributes, float cx, float cy, int viewId) {
            mPositions = new float[2];
            mDrawable = attributes.getDrawable(mDefaultAttributes).getConstantState().newDrawable().mutate();
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
            mPositionAnimator = attributes.getPositionAnimatorFactory(mDefaultAttributes).newInstance();
            mPositionAnimator.initialize(cx, cy, mLikesLayout.getWidth(), mLikesLayout.getHeight(),
                    attributes, mDefaultAttributes);
            mDrawableAnimator = attributes.getDrawableAnimatorFactory(mDefaultAttributes).newInstance();
            mDrawableAnimator.initialize(mLikesLayout.getWidth(), mLikesLayout.getHeight(), attributes, mDefaultAttributes);

        }

        private void applyAnimations(@NonNull ValueAnimator animation) {
            Arrays.fill(mPositions, 0.0f);
            mPositionAnimator.onAnimationUpdate(animation.getAnimatedFraction(), mPositions);
            updatePosition();
            mDrawableAnimator.onAnimationUpdate(animation.getAnimatedFraction(), mDrawable);
        }

        private void updatePosition() {
            final float halfW = mDrawable.getBounds().width() / 2;
            final float halfH = mDrawable.getBounds().height() / 2;
            final float cx = mPositions[PositionAnimator.POSITION_X];
            final float cy = mPositions[PositionAnimator.POSITION_Y];
            mDrawable.setBounds((int) (cx - halfW), ((int) (cy - halfH)), ((int) (cx + halfW)), ((int) (cy + halfH)));
        }
    }

    private class DrawingTaskProducer implements Runnable {

        private final LikesAttributes mAttributes;
        private final float mCx, mCy;
        private final View mView;
        private boolean isCancelled;

        public DrawingTaskProducer(@NonNull LikesAttributes attributes, @NonNull View view) {
            mAttributes = attributes;
            mView = view;
            mCx = view.getLeft() + view.getWidth() / 2f;
            mCy = view.getTop() + view.getHeight() / 2f;
        }

        @Override
        public void run() {
            if (isCancelled) {
                return;
            }
            startDrawingTask(mView.getId());
            mLikesLayout.onLikeProduced(mView);
            mUiHandler.postDelayed(this, mAttributes.getProduceInterval(mDefaultAttributes));
        }

        public void setCancelled(boolean cancelled) {
            isCancelled = cancelled;
        }

        private void startDrawingTask(int viewId) {
            final DrawingTask drawingTask = new DrawingTask(mAttributes, mCx, mCy, viewId);
            mDrawingTasks.add(drawingTask);
            ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
            animator.setDuration(mAttributes.getAnimationDuration(mDefaultAttributes));
            animator.setInterpolator(mInterpolator);
            animator.addUpdateListener(drawingTask::applyAnimations);
            animator.addUpdateListener(mUpdateListener);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mDrawingTasks.remove(drawingTask);
                }
            });
            animator.start();
        }
    }
}
