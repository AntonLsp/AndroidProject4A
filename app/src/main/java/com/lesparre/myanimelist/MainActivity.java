package com.lesparre.myanimelist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.TextView;

import com.lesparre.myanimelist.models.Anime;
import com.lesparre.myanimelist.ui.main.MainFragment;

public class MainActivity extends AppCompatActivity implements MainFragment.AnimeListListener, InfoFragment.InfoPanelListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
    }

    // This method is called when an anime has been clicked in the recyclerview
    public void onAnimeSelected(Anime anime)
    {
        // Switch to the info panel fragment
        InfoFragment fragment = new InfoFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.MainFragment, fragment);
        transaction.addToBackStack(null);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();
        TextView t = findViewById(R.id.bannerText);
        t.setText( getString(R.string.anime_info) );
        fragment.setAnime(anime);
    }

    // This method is called when the user clicks anywhere on the anime info panel
    public void onAnimePanelClick(){
        getSupportFragmentManager().popBackStack();
        TextView t = findViewById(R.id.bannerText);
        t.setText( getString(R.string.select_genre) );
    }

}
