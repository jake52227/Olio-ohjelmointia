package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Displayer extends RecyclerView.Adapter<Displayer.ViewHolder> {

    private ArrayList<String> contentArray;
    private TheatresList theatreList;
    private MovieList movieList;
    private Parser parser;
    private ContentFilter filter;

    public Displayer() {
        this.contentArray = new ArrayList<>();
        this.theatreList = new TheatresList();
        this.movieList = new MovieList();
        this.parser = new Parser();
        this.filter = new ContentFilter();

        this.theatreList.populateList(this.parser);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView displayText;

        public ViewHolder(View v) {
            super(v);
            displayText = v.findViewById(R.id.displayText);
        }
    }


    public void setSpinnerAdapter(Spinner spin, Context mainContext) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mainContext, android.R.layout.simple_spinner_item, this.theatreList.getPlacesAsStringArray());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
    }


    public void updateContentList(Spinner spin, String date, String movie, TextView infoText) {
        this.contentArray.clear();
        this.movieList.clearList();

        if (movie.isEmpty() && !spin.getSelectedItem().toString().equals("NO SELECTION")) {
            this.movieList.populateListByDate(this.parser, this.theatreList.getTheatreByName(spin.getSelectedItem().toString()).getId(), date);
            if (movieList.getList().isEmpty()) {
                infoText.setText("Sorry, no movies were found");
            } else {
                infoText.setText("at " + date + " " + spin.getSelectedItem().toString() + " is presenting:");
            }
            this.contentArray = movieList.getMoviesAsStrings(this.filter);
        }
        else if (!movie.isEmpty() && !spin.getSelectedItem().toString().equals("NO SELECTION")) {
            this.movieList.populateListByDate(this.parser, this.theatreList.getTheatreByName(spin.getSelectedItem().toString()).getId(), date);
            Movie m = movieList.getMovieByName(movie);
            if (m != null) {
                this.contentArray.add(movieList.formatMovie(m, this.filter));
                infoText.setText(spin.getSelectedItem().toString() + " presents '" + movie + "' at:");
            } else {
                infoText.setText("Sorry, found no movie by the name " + movie);
            }
        }
        else if (!movie.isEmpty()){
            this.contentArray = filter.filterTheatresByMovie(this.movieList, this.theatreList, movie, date, spin.getSelectedItem().toString());
            if (!contentArray.isEmpty()) {
                infoText.setText("Today '"+ movie + "' is shown in:");
            } else {
                infoText.setText("Sorry, found no movie by the name " + movie);
            }
        }
        this.notifyDataSetChanged();
    }


    public void filterByTime(String startTime, String endTime) {
        this.contentArray = filter.filterContentsByTime(this.contentArray, startTime, endTime);
        this.notifyDataSetChanged();
    }


    @NonNull
    @Override
    public Displayer.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View displayView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_recycler_view, parent, false);
        return new ViewHolder(displayView);
    }


    @Override
    public void onBindViewHolder(@NonNull Displayer.ViewHolder holder, int position) {
        String rowOnDisplay = this.contentArray.get(position);
        holder.displayText.setText(rowOnDisplay);
    }


    @Override
    public int getItemCount() {
        return this.contentArray.size();
    }
}
