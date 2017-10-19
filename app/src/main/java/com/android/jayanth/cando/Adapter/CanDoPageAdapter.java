package com.android.jayanth.cando.Adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.android.jayanth.cando.Fragment.CanDoTabFragment;

import static com.android.jayanth.cando.Constants.Constants.ARG_INDEX;
import static com.android.jayanth.cando.Constants.Constants.ARG_STRING;

/**
 * Created by Jayanth Devulapally on 8/3/17.
 * Custom pager adapter configuring the fragments at the individual page
 */

public class CanDoPageAdapter extends FragmentPagerAdapter{

    //Tab titles
    private final String[] mTabs = {"To Do","Done", "Archived"};

    public CanDoPageAdapter(FragmentManager fm) {
        super(fm);
    }

    //getting the fragment of the page/tab
    @Override
    public Fragment getItem(int position) {
        CanDoTabFragment canDoTabFragment = new CanDoTabFragment();
        Bundle args = new Bundle();
        args.putString(ARG_STRING,mTabs[position]);
        args.putInt(ARG_INDEX,position);
        canDoTabFragment.setArguments(args);
        return canDoTabFragment;
    }

    @Override
    public int getCount() {
        return mTabs.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabs[position];
    }

    //this is called when we notifyDataChanged() is called.
    //returing the POSITION_NONE will re-create the fragment at that individual position.
    @Override
    public int getItemPosition(Object object) {
        /*CanDoTabFragment canDoTabFragment = (CanDoTabFragment) object;
        if (canDoTabFragment != null){
            canDoTabFragment.updateListView();
        }
        return super.getItemPosition(object);*/
        return POSITION_NONE;
    }
}