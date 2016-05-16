package ua.vlasov.likes_layout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import ua.vlasov.likeslayout.LikesLinearLayout;
import ua.vlasov.likeslayout.OnChildTouchListener;

public class MainActivity extends AppCompatActivity implements OnChildTouchListener {

    private Toast mToast;

    @SuppressLint("ShowToast")
    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        mToast.setGravity(Gravity.TOP, 0, (int) (getResources().getDisplayMetrics().density * 56));
        final LikesLinearLayout likesLayout = ((LikesLinearLayout) findViewById(R.id.likes_layout));
        likesLayout.setOnChildTouchListener(this);
    }

    @Override
    public void onChildTouched(View child) {
        mToast.setText(String.format("touched: %s", getResources().getResourceEntryName(child.getId())));
        mToast.show();
    }

    @Override
    public void onLikeProduced(View child) {
        // do something here
    }

    @Override
    public void onChildReleased(View child, boolean isCanceled) {
        mToast.cancel();
    }
}
