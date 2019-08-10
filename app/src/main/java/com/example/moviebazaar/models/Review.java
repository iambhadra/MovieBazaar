package com.example.moviebazaar.models;
/*
 * Title :- Movie Bazaar Application
 * Version :- 1.0.1
 * Usage :- This model will be used to fetch the response for review.
 * Creator :- Veerabhadrarao kona
 * Date :- 14-07-2019
 * */
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Review implements Serializable {
    @SerializedName("author")
    private String author;
    @SerializedName("content")
    private String content;
    @SerializedName("id")
    private String id;
    @SerializedName("url")
    private String url;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }





}
