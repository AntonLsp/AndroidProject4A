package com.lesparre.myanimelist.ui.main;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lesparre.myanimelist.R;
import com.lesparre.myanimelist.controller.ApiController;
import com.lesparre.myanimelist.models.Anime;
import com.lesparre.myanimelist.models.ByGenreRequest;
import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment implements MainRecyclerViewAdapter.ItemClickListener{

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

        // loading text
        TextView textView = view.findViewById(R.id.textViewMiddle);
        textView.setText("Loading data");

        // set up the RecyclerView
        this.recyclerView = view.findViewById(R.id.rvAnime);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.adapter = new MainRecyclerViewAdapter(getActivity(), this.myAnimeList);
        adapter.setClickListener(this);
        this.recyclerView.setAdapter(adapter);

        return view;
    }

    public void setAnimeGenre(String genre_id)
    {
        myAnimeList.clear();
        this.adapter.notifyDataSetChanged();

        TextView textView = this.view.findViewById(R.id.textViewMiddle);
        textView.setText("Loading Data");
        textView.setVisibility(TextView.VISIBLE);

        ApiController.getInstance().getAnimesByGenre(genre_id,this::getAnime, this::fail);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(getActivity(), "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }

    private void getAnime(ByGenreRequest list)
    {
        this.myAnimeList.clear();
        this.myAnimeList.addAll(list.getAnime());
        this.adapter.notifyDataSetChanged();

        // Hide loading textview
        TextView textView = this.view.findViewById(R.id.textViewMiddle);
        textView.setVisibility(TextView.INVISIBLE);
    }

    private void fail() {
        TextView textView = this.view.findViewById(R.id.textViewMiddle);
        textView.setText("Cannot join server.");
        textView.setVisibility(TextView.VISIBLE);
    }

}
