package com.example.moviebazaar.models;
/*
 * Title :- Movie Bazaar Application
 * Version :- 1.0.0
 * Usage :- This is a model class of movie response.
        It used to response from the movie DB server.
 * Creator :- Veerabhadrarao kona
 * Date :- 01-06-2019
 * */

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class MovieResponse implements Serializable {
    @SerializedName("page")
    private int page;
    @SerializedName("results")
    private List<Movie> results;
    @SerializedName("total_results")
    private int totalResults;
    @SerializedName("total_pages")
    private int totalPages;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
