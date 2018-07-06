package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.models.Tweet;
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
    private TextView counter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        counter = (TextView) findViewById(R.id.counter);
        text = (EditText) findViewById(R.id.message);
        text.addTextChangedListener(counterWatcher);
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

    private final TextWatcher counterWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //This sets a textview to the current length
            counter.setText(String.valueOf(s.length())+"/280 characters");
        }

        public void afterTextChanged(Editable s) {
        }
    };

}
