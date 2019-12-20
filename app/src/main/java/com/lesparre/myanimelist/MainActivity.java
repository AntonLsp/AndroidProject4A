package com.lesparre.myanimelist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.lesparre.myanimelist.service.AnimeService;
import com.lesparre.myanimelist.ui.main.MainFragment;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);
        setupSpinner();
    }

    private void setupSpinner()
    {
        Spinner spinner = findViewById(R.id.genre_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.anime_genres, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // A genre was selected, so we must set it
        setGenre(pos);
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    public void setGenre(int genre_id)
    {
        FragmentManager fm = getSupportFragmentManager();
        MainFragment f =  (MainFragment) fm.findFragmentById(R.id.MainFragment);
        f.setAnimeGenre(String.valueOf(genre_id+1)); // On the API
    }

}
