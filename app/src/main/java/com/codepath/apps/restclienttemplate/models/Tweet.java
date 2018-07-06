package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;


public @Parcel class Tweet {
    public String body;
    public long uid;
    public User user;
    public String createdAt;
    public String handle;
    public String count;
    public String retweets;
    public String likes;
    public boolean retweeted;

    //deserialize the JSON
    public static Tweet fromJSON(JSONObject jsonObject) throws JSONException{
        Tweet tweet = new Tweet();

        //extract the values from JSON
        tweet.body = jsonObject.getString("text");
        tweet.uid = jsonObject.getLong("id");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
        tweet.handle = tweet.user.screenName;
        tweet.retweets = jsonObject.getString("retweet_count");
        tweet.likes = jsonObject.getString("favorite_count");


        return tweet;
    }

}
