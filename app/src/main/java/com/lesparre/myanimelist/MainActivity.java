package com.lesparre.myanimelist;

import androidx.annotation.StyleRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.lesparre.myanimelist.models.Anime;
import com.lesparre.myanimelist.ui.main.MainFragment;

public class MainActivity extends AppCompatActivity implements MainFragment.AnimeListListener, InfoFragment.InfoPanelListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);
    }

    public void onAnimeSelected(Anime anime)
    {
        InfoFragment fragment = new InfoFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.MainFragment, fragment);
        transaction.addToBackStack(null);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();
        TextView t = findViewById(R.id.bannerText);
        t.setText("SELECTED ANIME");
        fragment.setAnime(anime);
    }

    public void onAnimePanelClick(){
        getSupportFragmentManager().popBackStack();
        TextView t = findViewById(R.id.bannerText);
        t.setText("SELECT AN ANIME GENRE");
    }

}
