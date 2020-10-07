package com.shopping_point.user_shopping_point.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsFeedResponse {

    @SerializedName("posters")
    private List<NewsFeed> posters;

    public List<NewsFeed> getPosters() {
        return posters;
    }

    public void setPosters(List<NewsFeed> posters) {
        this.posters = posters;
    }
}
