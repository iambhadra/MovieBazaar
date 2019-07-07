package com.example.moviebazaar.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class TrailerResponse implements Serializable {
    @SerializedName("id")
    private String movieId;

    @SerializedName("results")
    private List<Trailer> trailerResults;

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public List<Trailer> getTrailerResults() {
        return trailerResults;
    }

    public void setTrailerResults(List<Trailer> trailerResults) {
        this.trailerResults = trailerResults;
    }

}
