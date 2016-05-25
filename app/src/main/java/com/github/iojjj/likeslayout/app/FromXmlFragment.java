package com.github.iojjj.likeslayout.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Fragment that inflates LikesLayout from XML.
 */
public class FromXmlFragment extends BaseFragment {

    @BindView(R.id.btn_favorite)
    ImageButton mBtnFavorite;

    @BindView(R.id.btn_grade)
    ImageButton mBtnGrade;

    @BindView(R.id.btn_stars)
    ImageButton mBtnStars;

    public static FromXmlFragment newInstance() {
        Bundle args = new Bundle();
        FromXmlFragment fragment = new FromXmlFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @OnClick(R.id.btn_produce)
    void onProduceLikesClicked() {
        mLikesLayout.produceLikes(mBtnFavorite, 3, TimeUnit.SECONDS);
        mLikesLayout.produceLikes(mBtnGrade, 3, TimeUnit.SECONDS);
        mLikesLayout.produceLikes(mBtnStars, 3, TimeUnit.SECONDS);
    }
}
