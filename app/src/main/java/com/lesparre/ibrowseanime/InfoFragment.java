package com.lesparre.ibrowseanime;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lesparre.ibrowseanime.models.Anime;
import com.squareup.picasso.Picasso;

// Anime information panel's fragment controller
public class InfoFragment extends Fragment implements View.OnClickListener {

    private View view;
    private InfoViewModel mViewModel;

    private Anime anime;

    public interface InfoPanelListener {
        void onAnimePanelClick();
    }

    private InfoPanelListener infoPanelListener;

    public static InfoFragment newInstance() {
        return new InfoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        this.view = inflater.inflate(R.layout.info_fragment, container, false);

        // The listener of the click events will be the parent activity
        infoPanelListener = (InfoPanelListener) getActivity();

        // Sets the listener for the scrollView (needed to detect click)
        ScrollView scrollView = view.findViewById(R.id.scrollView);
        scrollView.getChildAt(0).setOnClickListener(this);

        return view;
    }

    // Fills the fields with the information from the ModelView
    public void setupView(){
        TextView titleView = this.view.findViewById(R.id.title);
        titleView.setText(mViewModel.title);
        TextView typeView = this.view.findViewById(R.id.type);
        typeView.setText(mViewModel.type);
        TextView episodesView = this.view.findViewById(R.id.episodes);
        episodesView.setText(mViewModel.episodes + " episodes");
        TextView descView = this.view.findViewById(R.id.description);
        descView.setText(mViewModel.description);
        ImageView posterView = this.view.findViewById(R.id.poster);
        Picasso.get().load( mViewModel.imgURL ).into(posterView);
    }

    // Called when a click is detected, and triggers onAnimePanelClicks for the listener
    @Override
    public void onClick(View v) {
        infoPanelListener.onAnimePanelClick();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.mViewModel = ViewModelProviders.of(this).get(InfoViewModel.class);
    }

    // Setup the ViewModel after everything is done
    @Override
    public void onResume()
    {
        super.onResume();
        mViewModel.title = anime.getTitle();
        mViewModel.type = anime.getType();
        mViewModel.episodes = anime.getEpisodes();
        mViewModel.description = anime.getSynopsis();
        mViewModel.imgURL = anime.getImageUrl();
        setupView();
    }

    // Sets the current anime object
    public void setAnime(Anime anime)
    {
        this.anime = anime;
    }



}
