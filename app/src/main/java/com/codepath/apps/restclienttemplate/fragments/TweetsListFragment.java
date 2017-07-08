package com.codepath.apps.restclienttemplate.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.restclienttemplate.EndlessRecyclerViewScrollListener;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TweetAdapter;
import com.codepath.apps.restclienttemplate.TwitterApp;
import com.codepath.apps.restclienttemplate.TwitterClient;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by pratyusha98 on 7/3/17.
 */

public class TweetsListFragment extends Fragment {


    TweetAdapter tweetAdapter;
    ArrayList<Tweet> tweets;
    RecyclerView rvTweets;
    SwipeRefreshLayout swipeContainer;
    TwitterClient client;
    public static int page;
    private EndlessRecyclerViewScrollListener scrollListener;


    //inflation happens inside onCreateView


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //inflate the layout
        View v = inflater.inflate(R.layout.fragments_tweets_list, container, false);
        client = TwitterApp.getRestClient();


        //find the recycler view and swipe containerview
        rvTweets = (RecyclerView) v.findViewById(R.id.rvTweet);
        //swipeContainer = (SwipeRefreshLayout) v.findViewById(R.id.swipeContainer);
        tweets = new ArrayList<>();
        // init the array list (data source)
        //construct the adapter form this datasource
        tweetAdapter = new TweetAdapter(tweets);
        //RecyclerView setup ( layout manager, use adapter)
        //llayout= new LinearLayoutManager(getContext()) ;
        LinearLayoutManager llayout = new LinearLayoutManager(getContext());
        rvTweets.setLayoutManager(llayout);

        //set the adapter

        rvTweets.setAdapter(tweetAdapter);
        swipeContainer = (SwipeRefreshLayout) v.findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                fetchTimelineAsync(TweetsListFragment.page);
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        scrollListener = new EndlessRecyclerViewScrollListener(llayout) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                loadNextDataFromApi(page);
            }
        };

        rvTweets.addOnScrollListener(scrollListener);

        return v;
    }


    public void fetchTimelineAsync(int page) {

        // Send the network request to fetch the updated data
        // `client` here is an instance of Android Async HTTP
        // getHomeTimeline is an example endpoint.
        //if(tweetsPagerAdapter.getItem()==0){}
        if (page == 0) {
            client.getHomeTimeline(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                    //Toast.makeText(TimelineActivity.this, "Refreshing", Toast.LENGTH_SHORT).show();
                    // Remember to CLEAR OUT old items before appending in the new ones
                    tweetAdapter.clear();
                    // ...the data has come back, add new items to your adapter...


                    for (int i = 0; i < response.length(); i++) {

                        //convert eachobject to a Tweet model
                        //add that Tweet model to our data source
                        //notify the adapter that we've added an item
                        Tweet tweet = null;

                        try {
                            tweet = Tweet.fromJSON(response.getJSONObject(i));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        tweets.add(tweet);
                        tweetAdapter.notifyItemInserted(tweets.size() - 1);
                    }
                    //tweetAdapter.addAll(tweets);
                    //tweetAdapter.notifyDataSetChanged();

                    // Now we call setRefreshing(false) to signal refresh has finished
                    swipeContainer.setRefreshing(false);
                }

                public void onFailure(Throwable e) {
                    Log.d("DEBUG", "Fetch timeline error: " + e.toString());
                    Log.d("DEBUG", "Fetch timeline error: " + e.toString());
                }
            });

        } else {

            client.getMentionsTimeline(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    Log.d("TwitterClient", response.toString());
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                    //Toast.makeText(TimelineActivity.this, "Refreshing", Toast.LENGTH_SHORT).show();
                    // Remember to CLEAR OUT old items before appending in the new ones
                    tweetAdapter.clear();
                    // ...the data has come back, add new items to your adapter...


                    for (int i = 0; i < response.length(); i++) {

                        //convert eachobject to a Tweet model
                        //add that Tweet model to our data source
                        //notify the adapter that we've added an item
                        Tweet tweet = null;

                        try {
                            tweet = Tweet.fromJSON(response.getJSONObject(i));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        tweets.add(tweet);
                        tweetAdapter.notifyItemInserted(tweets.size() - 1);
                    }
                    //tweetAdapter.addAll(tweets);
                    //tweetAdapter.notifyDataSetChanged();

                    // Now we call setRefreshing(false) to signal refresh has finished
                    swipeContainer.setRefreshing(false);
                }


                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Log.d("TwitterClient", responseString);
                    throwable.printStackTrace();
                }


                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.d("TwitterClient", errorResponse.toString());
                    throwable.printStackTrace();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                    Log.d("TwitterClient", errorResponse.toString());
                    throwable.printStackTrace();
                }
            });


        }
    }

    public void addItems(JSONArray response) {


        //iterate through the JSON array
        // for each entry, deserialize the JSON onject

        for (int i = 0; i < response.length(); i++) {

            //convert eachobject to a Tweet model
            //add that Tweet model to our data source
            //notify the adapter that we've added an item
            Tweet tweet = null;

            try {
                tweet = Tweet.fromJSON(response.getJSONObject(i));
                tweets.add(tweet);
                tweetAdapter.notifyItemInserted(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

    public void addTweet(Tweet tweet) {
        Log.d("rrr", "" + tweet.uid);
        tweets.add(0, tweet);
        tweetAdapter.notifyItemInserted(0);
        rvTweets.scrollToPosition(0);


    }

    public static void setPage(int page) {
        TweetsListFragment.page = page;
    }


    public void loadNextDataFromApi(int offset) {
        // Send an API request to retrieve appropriate paginated data
        long Id = tweets.get(tweets.size() - 1).uid;
        // Toast.makeText(this, ""+Id, Toast.LENGTH_SHORT).show();
        if (page == 0) {
            client.getHomeTimelineEndless(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    Log.d("TwitterClient", response.toString());
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                    //iterate through the JSON array
                    // for each entry, deserialize the JSON onject

                    for (int i = 0; i < response.length(); i++) {

                        //convert each object to a Tweet model
                        //add that Tweet model to our data source
                        //notify the adapter that we've added an item
                        Tweet tweet = null;

                        try {
                            tweet = Tweet.fromJSON(response.getJSONObject(i));
                            tweets.add(tweet);
                            tweetAdapter.notifyItemInserted(tweets.size() - 1);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Log.d("TwitterClient", responseString);
                    throwable.printStackTrace();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.d("TwitterClient", errorResponse.toString());
                    throwable.printStackTrace();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                    Log.d("TwitterClient", errorResponse.toString());
                    throwable.printStackTrace();
                }
            }, Id);
        } else {
            client.getMentionsTimelineEndless(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    Log.d("TwitterClient", response.toString());
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                    //iterate through the JSON array
                    // for each entry, deserialize the JSON onject

                    for (int i = 0; i < response.length(); i++) {

                        //convert each object to a Tweet model
                        //add that Tweet model to our data source
                        //notify the adapter that we've added an item
                        Tweet tweet = null;

                        try {
                            tweet = Tweet.fromJSON(response.getJSONObject(i));
                            tweets.add(tweet);
                            tweetAdapter.notifyItemInserted(tweets.size() - 1);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Log.d("TwitterClient", responseString);
                    throwable.printStackTrace();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.d("TwitterClient", errorResponse.toString());
                    throwable.printStackTrace();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                    Log.d("TwitterClient", errorResponse.toString());
                    throwable.printStackTrace();
                }
            }, Id);
        }


    }



//    //    //this is for the intermediate progress bar
//    MenuItem miActionProgressItem;
//    ProgressBar v;
//    @Override
//    public void onPrepareOptionsMenu(Menu menu) {
//        // Store instance of the menu item containing progress
//        miActionProgressItem = menu.findItem(R.id.miActionProgress);
//        // Extract the action-view from the menu item
//        v = (ProgressBar) MenuItemCompat.getActionView(miActionProgressItem);
//        // Return to finish
//        //populateTimeline();
//
//        //return super.onPrepareOptionsMenu(menu);
//    }
//    public void showProgressBar() {
//        // Show progress item
//        miActionProgressItem.setVisible(true);
//    }
//
//    public void hideProgressBar() {
//        // Hide progress item
//        miActionProgressItem.setVisible(false);
//    }


}



