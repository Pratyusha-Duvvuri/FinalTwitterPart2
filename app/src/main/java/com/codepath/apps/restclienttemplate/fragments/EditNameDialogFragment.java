package com.codepath.apps.restclienttemplate.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TwitterApp;
import com.codepath.apps.restclienttemplate.TwitterClient;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


/**
 * Created by pratyusha98 on 7/6/17.
 */


public class EditNameDialogFragment extends DialogFragment {
    //adding stuff for step 6

    TwitterClient client;
    Tweet tweet;
    private final int RESULT_OK = 10;
    private EditText mEditText;
    public TextView characterCount;
    public int state;
    public long num;
    public ImageButton send;

    // 1. Defines the listener interface with a method passing back data result.
    public interface SendDialogListener {
        void onFinishEditDialog(Tweet tweet);
    }


    public EditNameDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static EditNameDialogFragment newInstance(String title, Tweet tweet) {
        EditNameDialogFragment frag = new EditNameDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_name, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        client =  TwitterApp.getRestClient();

        mEditText = (EditText) view.findViewById(R.id.txt_your_name);
        mEditText.addTextChangedListener(mTextEditorWatcher);
        send = (ImageButton) view.findViewById(R.id.ivSend);

        send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                        returnTweet(v);
            }
        });
        characterCount = (TextView) view.findViewById(R.id.tvCharCount);
        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "Compose Tweet");
        getDialog().setTitle(title);
        // Show soft keyboard automatically and request focus to field
        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);


    }



    private final TextWatcher mTextEditorWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //This sets a textview to the current length
            int val = 140 - s.length();

            characterCount.setText("Character Count: "+String.valueOf(val));
            if (val < 135)
                characterCount.setHighlightColor(getResources().getColor(R.color.red));
            else
                characterCount.setHighlightColor(getResources().getColor(R.color.blue));
        }

        public void afterTextChanged(Editable s) {
        }
    };





    public void returnTweet(View view) {


        String str =  mEditText.getText().toString();

        //if it is a compose thing
        client.sendTweet(str, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                //super.onSuccess(statusCode, headers, response);

                tweet = null;

                //construct a new tweet here
                try {
                    tweet = Tweet.fromJSON(response);




                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(tweet!=null){

                    SendDialogListener listener = (SendDialogListener) getActivity();
                    listener.onFinishEditDialog(tweet);
                    // Close the dialog and return back to the parent activity
                    dismiss();
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.d("yooo", errorResponse.toString());
                Log.d("yooo", throwable.toString());

            }

        }, num);


    }
}
