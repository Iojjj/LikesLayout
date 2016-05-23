package ua.vlasov.likes_layout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ua.vlasov.likeslayout.LikesLinearLayout;
import ua.vlasov.likeslayout.OnChildTouchListener;

/**
 * Base fragment implementation.
 */
public class BaseFragment extends Fragment implements OnChildTouchListener {

    @BindView(R.id.status)
    TextView mStatus;

    @BindView(R.id.likes_layout)
    LikesLinearLayout mLikesLayout;

    private int mFavoriteCounter;
    private int mGradeCounter;
    private int mStarsCounter;
    private Unbinder mUnbinder;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, view);
        mLikesLayout.setOnChildTouchListener(this);
        updateStatus();
    }

    @Override
    public void onDestroyView() {
        mUnbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void onChildTouched(View child) {

    }

    @Override
    public void onLikeProduced(View child) {
        // do something here
        switch (child.getId()) {
            case R.id.btn_favorite: {
                mFavoriteCounter++;
                break;
            }
            case R.id.btn_grade: {
                mGradeCounter++;
                break;
            }
            case R.id.btn_stars: {
                mStarsCounter++;
                break;
            }
        }
        updateStatus();
    }

    @Override
    public void onChildReleased(View child, boolean isCanceled) {

    }

    private void updateStatus() {
        mStatus.setText(String.format(Locale.US, "Counter. favorites: %1$d, grades: %2$d, stars: %3$d",
                mFavoriteCounter, mGradeCounter, mStarsCounter));
    }
}
