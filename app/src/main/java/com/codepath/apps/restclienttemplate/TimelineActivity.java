package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.codepath.apps.restclienttemplate.fragments.TweetsPagerAdapter;
import com.codepath.apps.restclienttemplate.models.Tweet;

import java.util.ArrayList;


public class TimelineActivity extends AppCompatActivity {


//    SwipeRefreshLayout swipeContainer;


//    //this is for the intermediate progress bar
    MenuItem miActionProgressItem;
    ProgressBar v;
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Store instance of the menu item containing progress
        miActionProgressItem = menu.findItem(R.id.miActionProgress);
        // Extract the action-view from the menu item
         v = (ProgressBar) MenuItemCompat.getActionView(miActionProgressItem);
        // Return to finish

        return super.onPrepareOptionsMenu(menu);
    }
    public void showProgressBar() {
        // Show progress item
        miActionProgressItem.setVisible(true);
    }

    public void hideProgressBar() {
        // Hide progress item
        miActionProgressItem.setVisible(false);
    }



   LinearLayoutManager llayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        //get the view pager
        ViewPager vpPager = (ViewPager) findViewById(R.id.viewpager);
        //set the adapter for the pager
        vpPager.setAdapter(new TweetsPagerAdapter(getSupportFragmentManager(),this));
        // setup the tab layout to use the view pager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(vpPager);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    // ActivityOne.java
// REQUEST_CODE can be any value we like, used to determine the result type later
    private final int REQUEST_CODE = 20;
    private final int RESULT_OK = 10;

    // FirstActivity, launching an activity for a result
    public void onComposeAction(MenuItem mi) {
        Intent i = new Intent(TimelineActivity.this, ComposeActivity.class);
        //i.putExtra("mode", 2); // pass arbitrary data to launched activity
        Tweet tweet = new Tweet();
        tweet.user= null;
        ;
       // Log.d("Please", ""+tweet.user.uid);
        i.putExtra("iamhere",0);//Parcels.wrap(t)

        i.putExtra("tweet",tweet);//Parcels.wrap(t)

        startActivityForResult(i, REQUEST_CODE);
    }


    // ActivityOne.java, time to handle the result of the sub-activity aka Compose Activity


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            Tweet tweet =  data.getParcelableExtra("tweet");
            // Toast the name to display temporarily on screen
            //Toast.makeText(this, (CharSequence) name, Toast.LENGTH_SHORT).show();
        //abive make sure that tweet is processed, button is assigned right fucntion signature
            //and we getClass properly


            tweets.add(0, tweet);
            tweetAdapter.notifyItemInserted(0);
            rvTweets.scrollToPosition(0);


        }
    }


    public void onProfileView(MenuItem item) {
        //laounch the profile view
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);

    }
}
