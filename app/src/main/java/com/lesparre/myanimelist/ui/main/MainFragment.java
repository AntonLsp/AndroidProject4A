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

        // Initialize the list with Action genre animes
        setAnimeGenre("1");

        // setup spinner
        setupSpinner();

        // set up the RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.rvAnime);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.adapter = new MainRecyclerViewAdapter(getActivity(), mViewModel.getMyAnimeList());
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        return view;
    }

    // Public method to retrieve data from API and set/update the anime list
    public void setAnimeGenre(String genre_id)
    {
        // Clear the list of animes
        try {
            mViewModel.clearMyAnimeList();
            this.adapter.notifyDataSetChanged();
        } catch (NullPointerException e){

        }

        // Show loading text
        TextView textView = this.view.findViewById(R.id.textViewMiddle);
        textView.setText(getText(R.string.loading_message));
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

    // Callback method called when the API request is successfull
    private void getAnime(ByGenreRequest list)
    {
        // Change the list with the new animes, and update the recyclerview
        this.mViewModel.setMyAnimeList(list.getAnime());
        this.adapter.notifyDataSetChanged();

        // Hide loading textview
        TextView textView = this.view.findViewById(R.id.textViewMiddle);
        textView.setVisibility(TextView.INVISIBLE);
    }

    // Callback method called when the API request fails
    private void fail() {
        TextView textView = this.view.findViewById(R.id.textViewMiddle);
        textView.setText(getText(R.string.cannot_load_from_api));
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

    // Called when a new item has been selected in the spinner
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // A genre was selected, so we must set it
        setAnimeGenre(String.valueOf(pos+1));
    }

    // Called when nothing is selected in the spinner
    public void onNothingSelected(AdapterView<?> parent) {
    }

    // Interface defining the callback for anime selection
    public interface AnimeListListener{
        void onAnimeSelected(Anime anime);
    }

}