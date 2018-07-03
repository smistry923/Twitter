package com.codepath.apps.restclienttemplate.models;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TimelineActivity;
import com.codepath.apps.restclienttemplate.TwitterApp;
import com.codepath.apps.restclienttemplate.TwitterClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class ComposeActivity extends AppCompatActivity {

    public TwitterClient client;
    public AsyncHttpResponseHandler handler;
    public Tweet tweet;
    public EditText text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        text = (EditText) findViewById(R.id.message);
        client = TwitterApp.getRestClient(this);
        handler = new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    tweet = Tweet.fromJSON(response);
                    Intent i = new Intent(ComposeActivity.this, TimelineActivity.class);
                    i.putExtra("tweet", Parcels.wrap(tweet));
                    setResult(20, i);
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        };

    }

    public void sendTweet(View view){

        String message = text.getText().toString();
        client.sendTweet(message, handler);

    }

    public void exit(View view){
        Intent intent = new Intent(ComposeActivity.this, TimelineActivity.class);
        startActivity(intent);
    }

}
