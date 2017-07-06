package com.codepath.apps.restclienttemplate;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.codepath.apps.restclienttemplate.fragments.SearchTweetsFragment;

public class SearchActivity extends AppCompatActivity {
    //SearchTweetsFragment searchTweetsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);

        String query = getIntent().getStringExtra("query");

        //create the user fragment
        SearchTweetsFragment searchTweetsFragment = SearchTweetsFragment.newInstance(query);
        // display the user timeline fragment inside the container (dyanmic)
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        //make changes
        ft.replace(R.id.flSearch, searchTweetsFragment);
        //commit
        ft.commit();




    }
}
