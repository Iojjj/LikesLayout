package ua.vlasov.likes_layout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Fragment that inflates LikesLayout from XML.
 */
public class FromXmlFragment extends BaseFragment {

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

}
