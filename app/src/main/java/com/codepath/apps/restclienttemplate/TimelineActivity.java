package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.codepath.apps.restclienttemplate.fragments.EditNameDialogFragment;
import com.codepath.apps.restclienttemplate.fragments.HomeTimelineFragment;
import com.codepath.apps.restclienttemplate.fragments.TweetsListFragment;
import com.codepath.apps.restclienttemplate.fragments.TweetsPagerAdapter;
import com.codepath.apps.restclienttemplate.models.Tweet;




public class TimelineActivity extends AppCompatActivity implements EditNameDialogFragment.SendDialogListener{


//    SwipeRefreshLayout swipeContainer;


//    //this is for the intermediate progress bar
    MenuItem miActionProgressItem;
    ProgressBar v;
    TweetsPagerAdapter adapter;
    HomeTimelineFragment frag;
    ViewPager vpPager;
    LinearLayoutManager llayout;
    Integer tabPosition;


    @Override
    //TODO when does this get executed or called
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Store instance of the menu item containing progress
        miActionProgressItem = menu.findItem(R.id.miActionProgress);
        // Extract the action-view from the menu item
         v = (ProgressBar) MenuItemCompat.getActionView(miActionProgressItem);
        // Return to finish

        return super.onPrepareOptionsMenu(menu);
    }
//    public void showProgressBar() {
//        // Show progress item
//        miActionProgressItem.setVisible(true);
//    }
//
//    public void hideProgressBar() {
//        // Hide progress item
//        miActionProgressItem.setVisible(false);
//    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //TODO when are the tabs actually created? like when is the first time the stuff is instantiated??
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        adapter = new TweetsPagerAdapter(getSupportFragmentManager(),this);

        //get the view pager
         vpPager = (ViewPager) findViewById(R.id.viewpager);
        //set the adapter for the pager
        vpPager.setAdapter(adapter);
        vpPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {}
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            public void onPageSelected(int position) {
                //get an object
                //use this to set the page number to whatever using the setter
                // Check if this is the page you want.
                TweetsListFragment.setPage(position);
            }
        });

        // setup the tab layout to use the view pager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(vpPager);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);

        //return true;


        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // perform query here

                Intent i = new Intent(TimelineActivity.this, SearchActivity.class);

                i.putExtra("query",query);


                startActivityForResult(i, REQUEST_CODE);


                // workaround to avoid issues with some emulators and keyboard devices firing twice if a keyboard enter is used
                // see https://code.google.com/p/android/issues/detail?id=24599
                searchView.clearFocus();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);

    }


    // ActivityOne.java
// REQUEST_CODE can be any value we like, used to determine the result type later
    private final int REQUEST_CODE = 20;
    private final int RESULT_OK = 10;

    // FirstActivity, launching an activity for a result
//    public void onComposeAction(MenuItem mi) {
//        Intent i = new Intent(TimelineActivity.this, ComposeActivity.class);
//        //i.putExtra("mode", 2); // pass arbitrary data to launched activity
//        Tweet tweet = new Tweet();
//        //tweet.user= null;
//        ;
//        Tweet tweet = new Tweet();
//
//        i.putExtra("tweet",tweet);//Parcels.wrap(t)
//
//        startActivityForResult(i, REQUEST_CODE);
//    }


    //attempt of previous function with modal overlay
    public void onComposeAction(MenuItem mi) {
        //setContentView(R.layout.fragment_edit_name);
        Tweet tweet = new Tweet();

        FragmentManager fm = getSupportFragmentManager();
        EditNameDialogFragment editNameDialogFragment = EditNameDialogFragment.newInstance("Some Title",tweet);
        editNameDialogFragment.show(fm, "fragment_edit_name");
    }


    //
//    public void onFinishEditDialog(String inputText) {
//        Toast.makeText(this, "Hi, " + inputText, Toast.LENGTH_SHORT).show();
//
//    }



    // ActivityOne.java, time to handle the result of the sub-activity aka Compose Activity


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        Toast.makeText(this, "Hi, " + inputText, Toast.LENGTH_SHORT).show();
// REQUEST_CODE is defined above

        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            Tweet tweet =  data.getParcelableExtra("tweet");
            //TODO what does this line do--./.
            frag = (HomeTimelineFragment) adapter.getItem(0);
            vpPager.setCurrentItem(0);
            frag.addTweet(tweet);
        }
    }

    public void onProfileView(MenuItem item) {
        //launch the profile view
        Intent i = new Intent(this, ProfileActivity.class);
        i.putExtra("mark",true);
        startActivity(i);

    }

    public void onFinishEditDialog(Tweet tweet){

        frag = (HomeTimelineFragment) adapter.getItem(0);
        vpPager.setCurrentItem(0);
        frag.addTweet(tweet);

    }

}
