package com.lesparre.myanimelist.ui.main;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lesparre.myanimelist.R;
import com.lesparre.myanimelist.controller.ApiController;
import com.lesparre.myanimelist.models.Anime;
import com.lesparre.myanimelist.models.ByGenreRequest;
import com.lesparre.myanimelist.service.AnimeService;

import java.io.Console;
import java.util.Arrays;
import java.util.List;

import javax.security.auth.callback.Callback;

public class MainFragment extends Fragment {

    private MainViewModel mViewModel;
    private MainRecyclerViewAdapter adapter;

    private List<Anime> myAnimeList;


    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);



        // data to populate the RecyclerView with
        List<Pair<String,String>> animalNames = Arrays.asList(
                Pair.create("Naruto", "Très bon anime"),
                Pair.create("DBZ", "Avec sangoku !"),
                Pair.create("DBZ", "Avec sangoku !"),
                Pair.create("DBZ", "Avec sangoku !"),
                Pair.create("DBZ", "Avec sangoku !"),
                Pair.create("DBZ", "Avec sangoku !"),
                Pair.create("DBZ", "Avec sangoku !"),
                Pair.create("One Piece", "Beaucoup d'épisodes !"),
                Pair.create("One Piece", "Beaucoup d'épisodes !"),
                Pair.create("One Piece", "Beaucoup d'épisodes !")
        );

        TextView textView = view.findViewById(R.id.textViewBottom);
        textView.setText("Modified");

        // set up the RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.rvAnime);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new MainRecyclerViewAdapter(getActivity(), animalNames);
        //adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        System.out.println("API : "+ApiController.getInstance().toString());
        ApiController.getInstance().getAnimesByGenre("1",this::getAnime, this::fail);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        // TODO: Use the ViewModel
    }

    private void getAnime(ByGenreRequest list)
    {
        System.out.println(list.getAnime().get(0).getTitle());
        myAnimeList = list.getAnime();
    }

    private void fail() {
        System.out.println("FAIL");
    }

}
