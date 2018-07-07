package com.codepath.apps.restclienttemplate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.codepath.apps.restclienttemplate.models.GlideApp;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.models.User;

import org.parceler.Parcels;

public class UserInfoActivity extends AppCompatActivity {
    public ImageView ivProfileImage;
    public TextView tvUsername;
    public TextView tvHandle;
    public TextView followers;
    public TextView followingNum;
    public TextView tweetCount;
    public TextView likes;

    Tweet tweet;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);


        ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        tvUsername = (TextView) findViewById(R.id.tvUserName);
        tvHandle = (TextView) findViewById(R.id.tvHandle);
        followers = (TextView) findViewById(R.id.followers);
        followingNum = (TextView) findViewById(R.id.following);
        tweetCount= (TextView) findViewById(R.id.tweetCount);
        likes = (TextView) findViewById(R.id.likes);

        tweet = (Tweet) Parcels.unwrap(getIntent().getParcelableExtra(Tweet.class.getSimpleName()));
        user = tweet.user;

        tvUsername.setText(tweet.user.name);
        tvHandle.setText("@"+tweet.handle);
        followers.setText("Followers: " + user.followers);
        followingNum.setText("Following: " + user.followingNum);
        tweetCount.setText("Tweet Count: " +user.tweetCount);
        likes.setText("Likes: "+user.likes);


        GlideApp.with(this).load(tweet.user.profileImageUrl).transform(new RoundedCorners(70)).into(ivProfileImage);


    }


}
