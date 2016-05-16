package ua.vlasov.likeslayout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Likes drawer class. It detects touch events and produces likes.
 */
class LikesDrawer implements View.OnTouchListener {

    private final SparseArray<Producer> mProducers;
    private final List<DrawTask> mDrawTasks;
    private final LikesAttributes mDefaultAttributes;
    private final LikesLayout mLikesLayout;
    private final Handler mUiHandler;
    private final Interpolator mInterpolator;
    private final ValueAnimator.AnimatorUpdateListener mUpdateListener;
    private final Random mRandom;
    private final SparseArray<Integer> mTintColorIndexes;

    public LikesDrawer(@NonNull LikesLayout likesLayout, @NonNull LikesAttributes defaultAttributes) {
        mDefaultAttributes = defaultAttributes;
        mLikesLayout = likesLayout;
        mUiHandler = new Handler(Looper.getMainLooper());
        mInterpolator = new LinearInterpolator();
        mRandom = new Random();
        mUpdateListener = animation -> mLikesLayout.invalidate();
        mProducers = new SparseArray<>();
        mDrawTasks = new CopyOnWriteArrayList<>();
        mTintColorIndexes = new SparseArray<>();
    }

    public void onDraw(@NonNull Canvas canvas) {
        for (DrawTask drawTask : mDrawTasks) {
            drawTask.mDrawable.draw(canvas);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            LikesAttributes attributes = ((LikesLayoutParams) v.getLayoutParams()).getAttributes();
            Producer producer = mProducers.get(v.getId());
            if (producer == null) {
                producer = new Producer(attributes, v);
                mProducers.put(v.getId(), producer);
            }
            producer.run();
            mLikesLayout.onChildTouched(v);
            v.setPressed(true);
        } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
            Producer producer = mProducers.get(v.getId());
            if (producer != null) {
                producer.setCancelled(true);
                mUiHandler.removeCallbacks(producer);
                mProducers.remove(v.getId());
            }
            mLikesLayout.onChildReleased(v, event.getAction() == MotionEvent.ACTION_CANCEL);
            v.setPressed(false);
        }
        return true;
    }

    private class DrawTask {

        private final LikesAttributes mAttributes;
        private final Drawable mDrawable;
        private float mCx, mCy;

        public DrawTask(LikesAttributes attributes, float cx, float cy, int viewId) {
            mAttributes = attributes;
            mDrawable = attributes.getDrawable(mDefaultAttributes).getConstantState().newDrawable().mutate();
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
            mCx = cx;
            mCy = cy;
            updatePosition();
        }

        private void updatePosition() {
            final float halfW = mAttributes.getDrawableWidth(mDefaultAttributes) / 2;
            final float halfH = mAttributes.getDrawableHeight(mDefaultAttributes) / 2;
            mDrawable.setBounds((int) (mCx - halfW), ((int) (mCy - halfH)), ((int) (mCx + halfW)), ((int) (mCy + halfH)));
        }

        @Keep
        public void setPositionX(float cx) {
            mCx = cx;
            updatePosition();
        }

        @Keep
        public void setPositionY(float cy) {
            mCy = cy;
            updatePosition();
        }

        @Keep
        public void setAlpha(int alpha) {
            this.mDrawable.setAlpha(alpha);
        }
    }

    private class Producer implements Runnable {

        private final LikesAttributes mAttributes;
        private final float mCx, mCy;
        private final View mView;
        private boolean isCancelled;

        public Producer(@NonNull LikesAttributes attributes, @NonNull View view) {
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
            startDrawing(mView.getId());
            mLikesLayout.onLikeProduced(mView);
            mUiHandler.postDelayed(this, mAttributes.getProduceInterval(mDefaultAttributes));
        }

        public void setCancelled(boolean cancelled) {
            isCancelled = cancelled;
        }

        private void startDrawing(int viewId) {
            final DrawTask drawTask = new DrawTask(mAttributes, mCx, mCy, viewId);
            mDrawTasks.add(drawTask);
            int path = mRandom.nextInt(3);
            float toX;
            if (path == 0) {
                toX = mCx; // center
            } else if (path == 1) {
                toX = mCx - mAttributes.getDrawableHeight(mDefaultAttributes) / 2f; // left
            } else {
                toX = mCx + mAttributes.getDrawableHeight(mDefaultAttributes) / 2f; // right
            }
            PropertyValuesHolder positionX = PropertyValuesHolder.ofFloat("positionX", mCx , toX);
            PropertyValuesHolder positionY = PropertyValuesHolder.ofFloat("positionY", mCy, mAttributes.getDrawable().getIntrinsicHeight());
            PropertyValuesHolder alpha = PropertyValuesHolder.ofInt("alpha", 255, 0);
            ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(
                    drawTask,
                    positionX,
                    positionY,
                    alpha
            );
            animator.setDuration(mAttributes.getAnimationDuration(mDefaultAttributes));
            animator.setInterpolator(mInterpolator);

            animator.addUpdateListener(mUpdateListener);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mDrawTasks.remove(drawTask);
                }
            });
            animator.start();
        }
    }

    interface LikesLayout extends OnChildTouchListener {

        void invalidate();
    }

    interface LikesLayoutParams {

        LikesAttributes getAttributes();
    }
}
