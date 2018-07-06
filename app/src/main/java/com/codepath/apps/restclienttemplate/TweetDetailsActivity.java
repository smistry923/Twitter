package com.codepath.apps.restclienttemplate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.codepath.apps.restclienttemplate.models.GlideApp;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class TweetDetailsActivity extends AppCompatActivity {
    public ImageView ivProfileImage;
    public TextView tvUsername;
    public TextView tvBody;
    public TextView tvTimeStamp;
    public TextView tvHandle;
    public TextView tvLikes;
    public TextView tvRetweets;
    public TwitterClient client;
    public AsyncHttpResponseHandler handler;

    public EditText text;
    private TextView counter;


    Tweet tweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tweet_details);
        ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        tvUsername = (TextView) findViewById(R.id.tvUserName);
        tvBody = (TextView) findViewById(R.id.tvBody);
        tvTimeStamp = (TextView) findViewById(R.id.tvTimeStamp);
        tvHandle = (TextView) findViewById(R.id.tvHandle);
        tvLikes = (TextView) findViewById(R.id.tvLikes);
        tvRetweets = (TextView) findViewById(R.id.tvRetweets);
        tweet = (Tweet) Parcels.unwrap(getIntent().getParcelableExtra(Tweet.class.getSimpleName()));

        tvUsername.setText(tweet.user.name);
        tvBody.setText(tweet.body);
        tvHandle.setText(" @" + tweet.handle);
        tvRetweets.setText(tweet.retweets);
        tvLikes.setText(tweet.likes);

        GlideApp.with(this).load(tweet.user.profileImageUrl).transform(new RoundedCorners(70)).into(ivProfileImage);

        counter = (TextView) findViewById(R.id.counter);
        text = (EditText) findViewById(R.id.message);
        text.setText("@" + tweet.handle + " ");
        text.addTextChangedListener(counterWatcher);
        client = TwitterApp.getRestClient(this);
        handler = new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    tweet =  Tweet.fromJSON(response);
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

    public void like(View view){
        long id = tweet.uid;
        client.like(id,handler);
    }

    public void retweet(View view){
        long id = tweet.uid;
        client.retweet(id,handler);
    }
}





