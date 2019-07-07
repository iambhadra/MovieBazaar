package com.example.moviebazaar.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ReviewResponse implements Serializable {
    @SerializedName("id")
    private int id;
    @SerializedName("page")
    private int page;
    @SerializedName("results")
    private List<Review> reviewResults;
    @SerializedName("total_pages")
    private int totalPages;
    @SerializedName("total_results")
    private int totalResults;

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

    public List<Review> getReviewResults() {
        return reviewResults;
    }

    public void setReviewResults(List<Review> reviewResults) {
        this.reviewResults = reviewResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }


}
