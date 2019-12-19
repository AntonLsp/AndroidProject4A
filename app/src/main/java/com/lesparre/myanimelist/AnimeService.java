package com.lesparre.myanimelist;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AnimeService {

    public static final String ENDPOINT = "https://api.jikan.moe/v3";

    @GET("/genre/anime/{genre_id}")
    Call<List<Anime>> getAnimesByGenre(@Path("genre_id") String genre_id); // genre id : 1-Action 2-Adventure 3-Cars
}
