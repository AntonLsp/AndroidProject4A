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
import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {

    private MainViewModel mViewModel;
    private MainRecyclerViewAdapter adapter;
    private View view;

    private RecyclerView recyclerView;

    private List<Anime> myAnimeList;


    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.main_fragment, container, false);

        this.myAnimeList = new ArrayList<Anime>();
        // get data from api controller
        System.out.println("API : "+ApiController.getInstance().toString());
        ApiController.getInstance().getAnimesByGenre("1",this::getAnime, this::fail); // 1 is for Action

        // Texte de chargement
        TextView textView = view.findViewById(R.id.textViewMiddle);
        textView.setText("Chargement");

        // set up the RecyclerView
        this.recyclerView = view.findViewById(R.id.rvAnime);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.adapter = new MainRecyclerViewAdapter(getActivity(), this.myAnimeList);
        this.recyclerView.setAdapter(adapter);

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
        this.myAnimeList.addAll(list.getAnime());
        this.adapter.notifyDataSetChanged();

        // Hide loading textview
        TextView textView = this.view.findViewById(R.id.textViewMiddle);
        textView.setVisibility(TextView.INVISIBLE);
    }

    private void fail() {
        TextView textView = this.view.findViewById(R.id.textViewMiddle);
        textView.setText("Impossible de joindre le serveur.");
        textView.setVisibility(TextView.VISIBLE);
    }

}
