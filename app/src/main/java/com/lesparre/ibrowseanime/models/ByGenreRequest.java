package com.lesparre.ibrowseanime.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ByGenreRequest {

    @SerializedName("anime")
    @Expose
    private List<Anime> anime = null;

    public List<Anime> getAnime() {
        return anime;
    }

    public void setAnime(List<Anime> anime) {
        this.anime = anime;
    }
}
