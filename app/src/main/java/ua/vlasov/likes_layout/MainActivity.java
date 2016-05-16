package ua.vlasov.likes_layout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

import ua.vlasov.likeslayout.LikesLinearLayout;
import ua.vlasov.likeslayout.OnChildTouchListener;

public class MainActivity extends AppCompatActivity implements OnChildTouchListener {

    private TextView mStatus;
    private int mFavoriteCounter;
    private int mGradeCounter;
    private int mStarsCounter;

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStatus = (TextView) findViewById(R.id.status);
        final LikesLinearLayout likesLayout = ((LikesLinearLayout) findViewById(R.id.likes_layout));
        likesLayout.setOnChildTouchListener(this);
        updateStatus();
    }

    @Override
    public void onChildTouched(View child) {
        switch (child.getId()) {
            case R.id.btn_favorite: {
                mFavoriteCounter = 0;
                break;
            }
            case R.id.btn_grade: {
                mGradeCounter = 0;
                break;
            }
            case R.id.btn_stars: {
                mStarsCounter = 0;
                break;
            }
        }
        updateStatus();
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
        updateStatus();
    }

    private void updateStatus() {
        mStatus.setText(String.format(Locale.US, "Counter. favorites: %1$d, grades: %2$d, stars: %3$d",
                mFavoriteCounter, mGradeCounter, mStarsCounter));
    }
}
