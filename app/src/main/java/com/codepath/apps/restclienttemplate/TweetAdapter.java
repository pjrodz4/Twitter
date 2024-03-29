package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.codepath.apps.restclienttemplate.models.Tweet;

import java.util.List;

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder> {

    private List<Tweet> mTweets;
    Context context;

    // pass in the Tweets array in the constructor
    public TweetAdapter(List<Tweet> tweets) { mTweets = tweets; }

    // for each row, inflate the layout and cache references into ViewHolder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from (context);

        View tweetView = inflater.inflate(R.layout.item_tweet, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(tweetView);
        return viewHolder;
    }

    // bind the values based on the position of the element
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        // get the data according to position
        Tweet tweet = mTweets.get(i);

        // populate the views according to this data
        viewHolder.tvUserName.setText(tweet.user.name);
        viewHolder.tvUserName.setTypeface(Typeface.DEFAULT_BOLD);

        viewHolder.tvBody.setText(tweet.body);

        viewHolder.tvScreenName.setText("@" + tweet.user.screenName);
        viewHolder.tvScreenName.setTextColor(Color.GRAY);

        viewHolder.tvTimeStamp.setText(tweet.relativeTime);
        viewHolder.tvTimeStamp.setTextColor(Color.GRAY);

        Glide.with(context).load(tweet.user.profileImageUrl).apply(RequestOptions.circleCropTransform()).into(viewHolder.ivProfileImage);
        if (tweet.hasEntities) {
            String entityUrl = tweet.entity.loadUrl;
            Glide.with(context).load(entityUrl).into(viewHolder.tweet_entity);
            viewHolder.tweet_entity.setVisibility(View.VISIBLE);
        } else {
            viewHolder.tweet_entity.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mTweets.size();
    }

    public void clear() {
        mTweets.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Tweet> list) {
        mTweets.addAll(list);
        notifyDataSetChanged();
    }

    // create ViewHolder class
     public static class ViewHolder extends RecyclerView.ViewHolder {
         public ImageView ivProfileImage;
         public TextView tvUserName;
         public TextView tvBody;
         public TextView tvScreenName;
         public TextView tvTimeStamp;
         public ImageView tweet_entity;

         public ViewHolder(View itemView) {
             super(itemView);

             // perform findViewById lookups
             ivProfileImage = (ImageView) itemView.findViewById(R.id.ivProfileImage);
             tvUserName = (TextView) itemView.findViewById(R.id.tvUserName);
             tvBody = (TextView) itemView.findViewById(R.id.tvBody);
             tvScreenName = (TextView) itemView.findViewById(R.id.tvScreenName);
             tvTimeStamp = (TextView) itemView.findViewById(R.id.tvTimeStamp);
             tweet_entity = (ImageView) itemView.findViewById(R.id.entity_tweet);
         }
    }
}
