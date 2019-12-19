package com.lesparre.myanimelist.controller;

import com.lesparre.myanimelist.models.Anime;
import com.lesparre.myanimelist.models.ByGenreRequest;
import com.lesparre.myanimelist.service.AnimeService;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiController {
    public static ApiController instance = new ApiController();
    private AnimeService animeService;

    private class RESTCallback<T> implements Callback<T> {

        Consumer<T>response;
        Runnable failure;

        public RESTCallback(Consumer<T> responseFunc, Runnable failureFunc)
        {
            this.response = responseFunc;
            this.failure = failureFunc;
        }

        @Override
        public void onResponse(Call<T> call, Response<T> response) {
            if(response.isSuccessful())
            {
                System.out.println("RESP : " + response.body().toString());
                this.response.accept(response.body());
            }
        }

        @Override
        public void onFailure(Call<T> call, Throwable t) {
            this.failure.run();
        }
    }

    public ApiController() {
        animeService = new Retrofit.Builder()
                .baseUrl(AnimeService.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(AnimeService.class);
    }

    public static ApiController getInstance()
    {
        return instance;
    }

    public void getAnimesByGenre(String genreID, Consumer<ByGenreRequest> responseFunc, Runnable failureFunc)
    {
        animeService.getAnimesByGenre(genreID).enqueue(new RESTCallback(responseFunc,failureFunc));
    }



}
