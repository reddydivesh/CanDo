package com.android.jayanth.cando.Activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.android.jayanth.cando.Adapter.CanDoPageAdapter;
import com.android.jayanth.cando.Fragment.CanDoDetailsFragment;
import com.android.jayanth.cando.R;


public class MainActivity extends AppCompatActivity implements CanDoDetailsFragment.OnActionItemClickListener {

    private static final String TAG = MainActivity.class.getName();

    Toolbar mToolBar;
    TabLayout mTabLayout;
    ViewPager mViewPager;
    CanDoPageAdapter mCanDoPageAdapter;
    FloatingActionButton mAddFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setting the layout for the activity
        setContentView(R.layout.activity_main);

        //setting the toolbar from layout
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);

        //initializing the pager adapter
        mCanDoPageAdapter = new CanDoPageAdapter(getSupportFragmentManager());

        //setting the pager from layout
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mCanDoPageAdapter);

        //setting the on page change listener. notifying the data changed when to update the list.
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mViewPager.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //setting the tablayout from the layout
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mTabLayout.setupWithViewPager(mViewPager);

        //setting the FAB
        mAddFab = (FloatingActionButton) findViewById(R.id.fab_add);
        mAddFab.setOnClickListener(new FabOnClickListener(this));
    }

    //Onclick listener for the FAB
    private class FabOnClickListener implements View.OnClickListener{


        CanDoDetailsFragment.OnActionItemClickListener onActionItemClickListener;

        //getting the on action item click listener from constructor
        public FabOnClickListener(CanDoDetailsFragment.OnActionItemClickListener onActionItemClickListener){
            this.onActionItemClickListener = onActionItemClickListener;
        }
        @Override
        public void onClick(View v) {

            //getting the blank detail to do fragment
            CanDoDetailsFragment addTodoFragment = new CanDoDetailsFragment();

            //setting the on click listener
            addTodoFragment.setOnActionItemClickListener(onActionItemClickListener);

            //getting the fragment manager and starting the displaying the fragment
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.add(android.R.id.content, addTodoFragment).addToBackStack(null).commit();
        }
    }

    //Implementation of CanDoDetailsFragment.OnActionItemClickListener
    @Override
    public void onActionItemClick() {
        Log.i(TAG, "onActionItemClick: called");
        //updating the adapter
        mViewPager.getAdapter().notifyDataSetChanged();
    }
}
