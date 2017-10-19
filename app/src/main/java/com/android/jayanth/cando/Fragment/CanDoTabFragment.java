package com.android.jayanth.cando.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.jayanth.cando.Adapter.CanDoListRecyclerViewAdapter;
import com.android.jayanth.cando.R;

import static com.android.jayanth.cando.Constants.Constants.ARG_INDEX;

/**
 * Created by Jayanth Devulapally on 8/3/17.
 * Tab fragment to display the tabs
 */

public class CanDoTabFragment extends Fragment {

    RecyclerView mCanDoListView;
    CanDoListRecyclerViewAdapter mCanDoListAdpter;
    RecyclerView.LayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //getting the arguments
        Bundle args = getArguments();
        int index = args.getInt(ARG_INDEX);

        //setting the layout of the fragment
        View rootView = inflater.inflate(R.layout.can_do_tab_fragment,container,false);
        mCanDoListView = (RecyclerView) rootView.findViewById(R.id.rv_can_do_list);

        //setting the Linear layout manager and adapter to the recycler view
        mLayoutManager = new LinearLayoutManager(getContext());
        mCanDoListAdpter = new CanDoListRecyclerViewAdapter(getActivity(),index,getFragmentManager());
        mCanDoListView.setLayoutManager(mLayoutManager);
        mCanDoListView.setAdapter(mCanDoListAdpter);

        return rootView;
    }
}
