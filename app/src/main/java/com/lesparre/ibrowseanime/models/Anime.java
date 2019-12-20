package com.lesparre.ibrowseanime.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

// A model defining an individual Anime object of the Jikan API response
public class Anime {

    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("synopsis")
    @Expose
    private String synopsis;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("episodes")
    @Expose
    private Integer episodes;

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getType() {
        return type;
    }

    public Integer getEpisodes() {
        return episodes;
    }

}
