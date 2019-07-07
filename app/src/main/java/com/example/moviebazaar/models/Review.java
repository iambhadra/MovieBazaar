package com.example.moviebazaar.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Review implements Serializable {
    @SerializedName("author")
    private String author;
    @SerializedName("content")
    private String content;
    @SerializedName("id")
    private String reviewId;
    @SerializedName("url")
    private String reviewUrl;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public String getReviewUrl() {
        return reviewUrl;
    }

    public void setReviewUrl(String reviewUrl) {
        this.reviewUrl = reviewUrl;
    }


}
