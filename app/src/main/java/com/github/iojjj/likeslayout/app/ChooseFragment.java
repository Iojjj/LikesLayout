package com.github.iojjj.likeslayout.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

/**
 * Fragment that allows to choose what demo to show.
 */
public class ChooseFragment extends ListFragment implements AdapterView.OnItemClickListener {

    public static ChooseFragment newInstance() {
        Bundle args = new Bundle();
        ChooseFragment fragment = new ChooseFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String[] items = getResources().getStringArray(R.array.choices);
        setListAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, items));
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Fragment fragment;
        if (position == 0) {
            fragment = FromXmlFragment.newInstance();
        } else {
            fragment = FromCodeFragment.newInstance();
        }
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
