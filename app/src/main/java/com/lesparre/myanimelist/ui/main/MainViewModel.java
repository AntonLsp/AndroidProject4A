package com.lesparre.myanimelist.ui.main;

import androidx.lifecycle.ViewModel;

import com.lesparre.myanimelist.models.Anime;

import java.util.ArrayList;
import java.util.List;

// ViewModel for the main fragment and some getter setters
public class MainViewModel extends ViewModel {

    private List<Anime> myAnimeList;

    public MainViewModel(){
        this.myAnimeList = new ArrayList<Anime>();
    }

    public List<Anime> getMyAnimeList() {
        return myAnimeList;
    }

    public void setMyAnimeList(List<Anime> myAnimeList) {
        this.myAnimeList.clear();
        this.myAnimeList.addAll(myAnimeList);
    }

    public void clearMyAnimeList() {
        this.myAnimeList.clear();
    }


}
