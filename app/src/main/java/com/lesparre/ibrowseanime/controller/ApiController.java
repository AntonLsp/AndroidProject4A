package com.lesparre.ibrowseanime.controller;

import com.lesparre.ibrowseanime.models.ByGenreRequest;
import com.lesparre.ibrowseanime.service.AnimeService;

import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// Controller for the API
public class ApiController {

    // Static global instance for the API controller
    public static ApiController instance = new ApiController();

    // Interface for retrofit callbacks
    private AnimeService animeService;

    // Class used for API callbacks
    private class RESTCallback<T> implements Callback<T> {

        // Callback methods
        Consumer<T>response;
        Runnable failure;

        public RESTCallback(Consumer<T> responseFunc, Runnable failureFunc)
        {
            this.response = responseFunc;
            this.failure = failureFunc;
        }

        // Calls the response method
        @Override
        public void onResponse(Call<T> call, Response<T> response) {
            if(response.isSuccessful())
            {
                this.response.accept(response.body());
            }
        }

        // Calls the failure method
        @Override
        public void onFailure(Call<T> call, Throwable t) {
            this.failure.run();
        }
    }

    // Create the Retrofit Service
    public ApiController() {
        animeService = new Retrofit.Builder()
                .baseUrl(AnimeService.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(AnimeService.class);
    }

    // Return this API instance
    public static ApiController getInstance()
    {
        return instance;
    }

    // Used by other classes to get the API response. The provided response/failure functions will be triggered when the job is done.
    public void getAnimesByGenre(String genreID, Consumer<ByGenreRequest> responseFunc, Runnable failureFunc)
    {
        // Asynchronous call to the online API
        animeService.getAnimesByGenre(genreID).enqueue(new RESTCallback(responseFunc,failureFunc));
    }



}
