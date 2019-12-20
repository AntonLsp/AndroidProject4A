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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.lesparre.myanimelist.R;
import com.lesparre.myanimelist.controller.ApiController;
import com.lesparre.myanimelist.models.Anime;
import com.lesparre.myanimelist.models.ByGenreRequest;
import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment implements AdapterView.OnItemSelectedListener,MainRecyclerViewAdapter.ItemClickListener{

    private MainViewModel mViewModel;
    private MainRecyclerViewAdapter adapter;
    private View view;

    private RecyclerView recyclerView;

    private List<Anime> myAnimeList;

    private AnimeListListener animeListListener;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.main_fragment, container, false);

        // listener of click events
        animeListListener = (AnimeListListener) getActivity();

        // get data from api controller
        System.out.println("API : "+ApiController.getInstance().toString());
        ApiController.getInstance().getAnimesByGenre("1",this::getAnime, this::fail); // 1 is for Action

        // loading text
        TextView textView = view.findViewById(R.id.textViewMiddle);
        textView.setText("Loading data");

        // setup spinner
        setupSpinner();

        // set up the RecyclerView
        this.recyclerView = view.findViewById(R.id.rvAnime);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.adapter = new MainRecyclerViewAdapter(getActivity(), mViewModel.getMyAnimeList());
        adapter.setClickListener(this);
        this.recyclerView.setAdapter(adapter);

        return view;
    }

    // Public method to set the anime list by genre
    public void setAnimeGenre(String genre_id)
    {
        // Clear the list of animes
        mViewModel.clearMyAnimeList();
        this.adapter.notifyDataSetChanged();

        // Show loading text
        TextView textView = this.view.findViewById(R.id.textViewMiddle);
        textView.setText("Loading Data");
        textView.setVisibility(TextView.VISIBLE);

        // Retrieve animes data from API controller
        ApiController.getInstance().getAnimesByGenre(genre_id,this::getAnime, this::fail);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // init ViewModel
        mViewModel = ViewModelProviders.of(requireActivity()).get(MainViewModel.class);
    }

    @Override
    public void onItemClick(View view, int position) {
        animeListListener.onAnimeSelected(adapter.getItem(position));
    }

    private void getAnime(ByGenreRequest list)
    {
        // Change the list with the new animes, and update the recyclerview
        this.mViewModel.setMyAnimeList(list.getAnime());
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

    private void setupSpinner()
    {
        Spinner spinner = view.findViewById(R.id.genre_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(),
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
        setAnimeGenre(String.valueOf(pos+1));
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    public interface AnimeListListener{
        void onAnimeSelected(Anime anime);
    }

}