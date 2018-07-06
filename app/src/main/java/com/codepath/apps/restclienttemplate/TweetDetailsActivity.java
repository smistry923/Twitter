package com.codepath.apps.restclienttemplate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

public class TweetDetailsActivity extends AppCompatActivity {
    public ImageView ivProfileImage;
    public TextView tvUsername;
    public TextView tvBody;
    public TextView tvTimeStamp;
    public TextView tvHandle;
    public TextView tvLikes;
    public TextView tvRetweets;

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

    }
}





