package com.lesparre.myanimelist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.lesparre.myanimelist.service.AnimeService;

public class MainActivity extends AppCompatActivity {

    private AnimeService animeService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);
    }

}
