package com.github.iojjj.likeslayout.app;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.widget.Space;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.github.iojjj.likeslayout.LikesAttributes;
import com.github.iojjj.likeslayout.LikesLinearLayout;

/**
 * Fragment that constructs LikesLayout from code.
 */
public class FromCodeFragment extends BaseFragment {

    public static FromCodeFragment newInstance() {
        Bundle args = new Bundle();
        FromCodeFragment fragment = new FromCodeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_code, container, false);
        LikesLinearLayout likesLinearLayout = new LikesLinearLayout(getContext());
        likesLinearLayout.setId(R.id.likes_layout);
        final RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                (int) (getResources().getDisplayMetrics().density * 250));
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        likesLinearLayout.setLayoutParams(params);
        likesLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        likesLinearLayout.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
        likesLinearLayout.setPadding(0, 0, 0, (int) (getResources().getDisplayMetrics().density * 16));
        likesLinearLayout.getAttributes().setAnimationDuration(1200);
        likesLinearLayout.getAttributes().setTintMode(LikesAttributes.TINT_MODE_ON_SUCCESSIVELY);
        TypedArray colorsArray = getResources().obtainTypedArray(R.array.drawable_colors);
        int[] colors = new int[colorsArray.length()];
        for (int i = 0; i < colors.length; i++) {
            colors[i] = colorsArray.getColor(i, Color.TRANSPARENT);
        }
        likesLinearLayout.getAttributes().setTintColors(colors);
        colorsArray.recycle();
        addFavoriteButton(likesLinearLayout);
        addSpace(likesLinearLayout);
        addGradeButton(likesLinearLayout);
        addSpace(likesLinearLayout);
        addStarsButton(likesLinearLayout);
        view.addView(likesLinearLayout);
        return view;
    }

    private void addSpace(LikesLinearLayout likesLinearLayout) {
        Space space = new Space(getContext());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams((int) (getResources().getDisplayMetrics().density * 24),
                ViewGroup.LayoutParams.MATCH_PARENT);
        space.setLayoutParams(params);
        likesLinearLayout.addView(space);
    }

    private void addFavoriteButton(LikesLinearLayout likesLinearLayout) {
        ImageButton button = new ImageButton(getContext(), null, R.style.LikeButton_Favorite);
        button.setId(R.id.btn_favorite);
        button.setImageResource(R.drawable.ic_favorite);
        DrawableCompat.setTint(button.getDrawable(), ContextCompat.getColor(getContext(), R.color.colorAccent));
        final ViewGroup.LayoutParams params = likesLinearLayout
                .newLayoutParamsBuilder(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_favorite_normal))
                .setAnimationDuration(1000)
                .setProduceInterval(500)
                .setDrawableAnimatorFactory(new CustomDrawableAnimatorFactory())
                .setLikesMode(LikesAttributes.LIKES_MODE_ENABLED)
                .build();
        button.setLayoutParams(params);
        likesLinearLayout.addView(button);
    }

    private void addGradeButton(LikesLinearLayout likesLinearLayout) {
        ImageButton button = new ImageButton(getContext(), null, R.style.LikeButton_Grade);
        button.setId(R.id.btn_grade);
        button.setImageResource(R.drawable.ic_grade);
        DrawableCompat.setTint(button.getDrawable(), ContextCompat.getColor(getContext(), R.color.colorAccent));
        final ViewGroup.LayoutParams params = likesLinearLayout
                .newLayoutParamsBuilder(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_grade_normal))
                .setAnimationDuration(3000)
                .setProduceInterval(500)
                .setPositionAnimatorFactory(new CustomPositionAnimatorFactory2())
                .setLikesMode(LikesAttributes.LIKES_MODE_ENABLED)
                .build();
        button.setLayoutParams(params);
        likesLinearLayout.addView(button);
    }

    private void addStarsButton(LikesLinearLayout likesLinearLayout) {
        ImageButton button = new ImageButton(getContext(), null, R.style.LikeButton_Stars);
        button.setId(R.id.btn_stars);
        button.setImageResource(R.drawable.ic_stars);
        DrawableCompat.setTint(button.getDrawable(), ContextCompat.getColor(getContext(), R.color.colorAccent));
        final ViewGroup.LayoutParams params = likesLinearLayout
                .newLayoutParamsBuilder(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_stars_colored))
                .setAnimationDuration(1600)
                .setProduceInterval(200)
                .setPositionAnimatorFactory(new CustomPositionAnimatorFactory())
                .setTintMode(LikesAttributes.TINT_MODE_OFF)
                .setDrawableWidth(getResources().getDisplayMetrics().density * 32)
                .setDrawableHeight(getResources().getDisplayMetrics().density * 32)
                .setLikesMode(LikesAttributes.LIKES_MODE_ENABLED)
                .build();
        button.setLayoutParams(params);
        likesLinearLayout.addView(button);
    }

}
