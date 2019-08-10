package com.example.moviebazaar.models;
/*
 * Title :- Movie Bazaar Application
 * Version :- 1.0.1
 * Usage :- This model will be used to fetch the response for Trailer.
 * Creator :- Veerabhadrarao kona
 * Date :- 14-07-2019
 * */
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
