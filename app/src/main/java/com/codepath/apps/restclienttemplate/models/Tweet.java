package com.codepath.apps.restclienttemplate.models;

import android.text.format.DateUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

@Parcel
public class Tweet {
    // list out attributes
    public String body;
    public long uid; // database ID for the item_tweet
    public User user;
    public String createdAt;
    public String relativeTime;
    public Entity entity;
    public boolean hasEntities;

    // deserialize the JSON
    public static Tweet fromJSON(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();

        // extract all the values from JSON
        tweet.body = jsonObject.getString("text");
        tweet.uid = jsonObject.getLong("id");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
        tweet.relativeTime = getRelativeTimeAgo(tweet.createdAt);
        tweet.hasEntities = false;
        JSONObject entityObject = jsonObject.getJSONObject("entities");
        if (entityObject.has("media")) {
            JSONArray mediaEndpoint = entityObject.getJSONArray("media");
            if (mediaEndpoint != null && mediaEndpoint.length() != 0) {
                tweet.entity = Entity.fromJSONObject(jsonObject.getJSONObject("entities"));
                tweet.hasEntities = true;
            } else {
                tweet.hasEntities = false;
            }
        }
        return tweet;
    }

    // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
    public static String getRelativeTimeAgo(String rawJsonDate) {
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
}
