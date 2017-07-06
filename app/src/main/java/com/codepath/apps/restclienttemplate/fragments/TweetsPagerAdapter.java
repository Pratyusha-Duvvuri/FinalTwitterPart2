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
    private HomeTimelineFragment homeTimelineFragment = new HomeTimelineFragment();
    private MentionsTimelineFragment mentionsTimelineFragment = new MentionsTimelineFragment();



    public TweetsPagerAdapter(FragmentManager fm, Context context){
        super(fm);
        //wow so this is where context is set--makes sense
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
        Toast.makeText(context, ""+position , Toast.LENGTH_LONG);
        if (position == 0) {

            Toast.makeText(context, "Home", Toast.LENGTH_SHORT).show();
            return  homeTimelineFragment;
        } else if (position == 1) {
            Toast.makeText(context, "Mentions", Toast.LENGTH_SHORT).show();
            return mentionsTimelineFragment;
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
