package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;


public @Parcel class User {
    public String name;
    public long uid;
    public String screenName;
    public String profileImageUrl;
    public int followers;
    public int followingNum;
    public int likes;
    public int tweetCount;



    public static User fromJSON(JSONObject json) throws JSONException{
        User user = new User();
        user.name = json.getString("name");
        user.uid = json.getLong("id");
        user.screenName = json.getString("screen_name");
        user.profileImageUrl = json.getString("profile_image_url");
        user.followers = json.getInt("followers_count");
        user.followingNum = json.getInt("friends_count");
        user.likes= json.getInt("favourites_count");
        user.tweetCount = json.getInt("statuses_count");

        return user;
    }
}
