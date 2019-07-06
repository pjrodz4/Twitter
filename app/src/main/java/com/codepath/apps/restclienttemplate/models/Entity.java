package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class Entity {

    public String loadUrl;

    public Entity() {}

    public static Entity fromJSONObject(JSONObject jsonObject) throws JSONException {
        Entity entity = new Entity();
        entity.loadUrl = jsonObject.getJSONArray("media").getJSONObject(0).getString("media_url_https");
        return entity;
    }
}
