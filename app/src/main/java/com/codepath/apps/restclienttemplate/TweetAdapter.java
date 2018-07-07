package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.codepath.apps.restclienttemplate.models.GlideApp;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.parceler.Parcels;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder> {

    private List <Tweet> mTweets;
    public Context context;
    Tweet tweet;
    AsyncHttpResponseHandler handler = new JsonHttpResponseHandler();
    TwitterClient client = TwitterApp.getRestClient(context);


    //pass in Tweets array in the constructor
    public TweetAdapter(List<Tweet> tweets){ mTweets = tweets; }

    //and for each row inflate the layout and cache references into ViewHolder

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View tweetView = inflater.inflate(R.layout.item_tweet, parent, false);
        ViewHolder viewHolder = new ViewHolder(tweetView);
        return viewHolder;
    }

    //bind values based on the position of the element

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //get data according to position
        final Tweet tweet = mTweets.get(position);


        //populate the views according to this data
        holder.tvUsername.setText(tweet.user.name);
        holder.tvBody.setText(tweet.body);
        holder.tvTimeStamp.setText(getRelativeTimeAgo(tweet.createdAt));
        holder.tvHandle.setText(" @"+tweet.handle);
        holder.tvRetweets.setText(tweet.retweets);
        holder.tvLikes.setText(tweet.likes);


        holder.imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                client.retweet(tweet.uid, handler);
            }
        });

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                client.like(tweet.uid, handler);
            }
        });



        GlideApp.with(context).load(tweet.user.profileImageUrl).transform(new RoundedCorners(70)).into(holder.ivProfileImage);

        holder.ivProfileImage.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, UserInfoActivity.class);
                i.putExtra(Tweet.class.getSimpleName(), Parcels.wrap(tweet));
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mTweets.size();
    }

    //create ViewHolder class

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView ivProfileImage;
        public TextView tvUsername;
        public TextView tvBody;
        public TextView tvTimeStamp;
        public TextView tvHandle;
        public TextView tvLikes;
        public TextView tvRetweets;
        public ImageView imageView;
        public ImageView imageView2;
        public ImageView imageView4;


        public ViewHolder(View itemView){
            super(itemView);

            ivProfileImage = (ImageView) itemView.findViewById(R.id.ivProfileImage);
            tvUsername = (TextView) itemView.findViewById(R.id.tvUserName);
            tvBody = (TextView) itemView.findViewById(R.id.tvBody);
            tvTimeStamp = (TextView) itemView.findViewById(R.id.tvTimeStamp);
            tvHandle = (TextView) itemView.findViewById(R.id.tvHandle);
            tvLikes = (TextView) itemView.findViewById(R.id.tvLikes);
            tvRetweets = (TextView) itemView.findViewById(R.id.tvRetweets);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            imageView2 = (ImageView) itemView.findViewById(R.id.imageView2) ;
            imageView4 = (ImageView) itemView.findViewById(R.id.imageView4);
            tvBody.setOnClickListener(this);
            imageView4.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // gets item position
            int position = getAdapterPosition();
            // make sure the position is valid, i.e. actually exists in the view
            Log.d("Location", "You clicked");
            if (position != RecyclerView.NO_POSITION) {
                // get the movie at the position, this won't work if the class is static
                Tweet tweet = mTweets.get(position);
                Log.d("Location", "Got a tweet");
                // create intent for the new activity
                Intent intent = new Intent(context, TweetDetailsActivity.class);
                // serialize the movie using parceler, use its short name as a key
                // show the activity
                intent.putExtra(Tweet.class.getSimpleName(), Parcels.wrap(tweet));
                context.startActivity(intent);


            }
        }

    }

    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

    // Clean all elements of the recycler
    public void clear() {
        mTweets.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Tweet> list) {
        mTweets.addAll(list);
        notifyDataSetChanged();
    }







}
