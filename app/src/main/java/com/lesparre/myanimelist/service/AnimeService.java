package com.lesparre.myanimelist.service;

import com.lesparre.myanimelist.models.ByGenreRequest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

// Interface for Retrofit defining the requests
public interface AnimeService {

    String ENDPOINT = "https://api.jikan.moe/v3/";

    @GET("genre/anime/{genre_id}")
    Call<ByGenreRequest> getAnimesByGenre(@Path("genre_id") String genre_id); // genre id : 1-Action 2-Adventure 3-Cars
}
