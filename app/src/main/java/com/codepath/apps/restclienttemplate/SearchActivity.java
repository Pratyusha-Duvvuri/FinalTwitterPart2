package com.codepath.apps.restclienttemplate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.codepath.apps.restclienttemplate.fragments.SearchTweetsFragment;

public class SearchActivity extends AppCompatActivity {
    SearchTweetsFragment searchTweetsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        if (savedInstanceState == null) {
//            searchTweetsFragment  = (SearchTweetsFragment)
//                    getSupportFragmentManager().findFragmentById(R.id.searchTweetsFragment);
        }
    }
}
