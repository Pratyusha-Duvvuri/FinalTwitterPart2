package com.codepath.apps.restclienttemplate.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TweetAdapter;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by pratyusha98 on 7/3/17.
 */

public class TweetsListFragment extends Fragment {




    TweetAdapter tweetAdapter;
    ArrayList<Tweet> tweets;
    RecyclerView rvTweets;



    //inflation happens inside onCreateView


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //inflate the layout
        View v = inflater.inflate(R.layout.fragments_tweets_list, container,false);


        //find the recycler view and swipe containerview
        rvTweets = (RecyclerView) v.findViewById(R.id.rvTweet);
        //swipeContainer = (SwipeRefreshLayout) v.findViewById(R.id.swipeContainer);
        tweets = new ArrayList<>();
        // init the array list (data source)
        //construct the adapter form this datasource
        tweetAdapter = new TweetAdapter(tweets);
        //RecyclerView setup ( layout manager, use adapter)
        //llayout= new LinearLayoutManager(getContext()) ;
        rvTweets.setLayoutManager(new LinearLayoutManager(getContext()));

        //set the adapter

        rvTweets.setAdapter(tweetAdapter);



        return v;
    }

    public void addItems(JSONArray response){


        //iterate through the JSON array
        // for each entry, deserialize the JSON onject

        for(int i =0;i< response.length();i++) {

            //convert eachobject to a Tweet model
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
    }



