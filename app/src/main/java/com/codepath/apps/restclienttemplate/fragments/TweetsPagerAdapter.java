package com.codepath.apps.restclienttemplate.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.Toast;

/**
 * Created by pratyusha98 on 7/3/17.
 */

public class TweetsPagerAdapter extends FragmentPagerAdapter {
    private String tabTiltles[]= new String[]{"Home", "mentions"};
    private Context context;



    public TweetsPagerAdapter(FragmentManager fm, Context context){

        super(fm);
        this.context = context;
    }

//return the total number of fragments
    @Override
    public int getCount(){
        return 2;
    }

    //return the fragment to use depending on the position

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            Toast.makeText(context, "HOme", Toast.LENGTH_SHORT).show();
            return new HomeTimelineFragment();
        } else if (position == 1) {
            Toast.makeText(context, "Mentions", Toast.LENGTH_SHORT).show();
            return new MentionsTimelineFragment();
        } else
        {            Toast.makeText(context, "null", Toast.LENGTH_SHORT).show();

            return null;}
    }

    //return title that is used by each tab

    public CharSequence getPageTitle(int position) {
        //generate title based  on item position
        return tabTiltles[position];
    }
}
