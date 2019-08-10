package com.example.moviebazaar.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;
/*
 * Title :- Movie Bazaar Application
 * Version :- 1.0.1
 * Usage :- This model will be used to fetch the response for review.
 * Creator :- Veerabhadrarao kona
 * Date :- 14-07-2019
 * */
public class ReviewResponse implements Serializable {
    @SerializedName("id")
    private int id;
    @SerializedName("page")
    private int page;
    @SerializedName("results")
    private List<Review> results;
    @SerializedName("total_pages")
    private int total_pages;

    @SerializedName("total_results")
    private int total_results;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Review> getResults() {
        return results;
    }

    public void setResults(List<Review> results) {
        this.results = results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }





}
