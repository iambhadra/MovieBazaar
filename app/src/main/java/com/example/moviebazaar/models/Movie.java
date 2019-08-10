package com.example.moviebazaar.models;
/*
 * Title :- FavouriteMovie Bazaar Application
 * Version :- 1.0.0
 * Usage :-This is a model class of movie details.
        It used to hold the list of movie details retrieved from the response.
 * Creator :- Veerabhadrarao kona
 * Date :- 01-06-2019
 * */


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;


import java.io.Serializable;
import java.util.List;

import io.reactivex.annotations.NonNull;

@Entity(tableName = "favouriteMovies")
public class Movie implements Parcelable {
   @ColumnInfo(name = "poster_path")
   private String poster_path;


    @ColumnInfo(name = "adult")
    private boolean adult;


    @ColumnInfo(name = "overview")
    private String overview;


    @ColumnInfo(name = "release_date")
    private String release_date;



     @ColumnInfo(name = "genre_ids")
     private List<Integer> genre_ids;


    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "id")
    private Integer id;


    @ColumnInfo(name = "original_title")
    private String original_title;


    @ColumnInfo(name = "original_language")
    private String original_language;


    @ColumnInfo(name = "title")
    private String title;


    @ColumnInfo(name = "backdrop_path")
    private String backdrop_path;


    @ColumnInfo(name = "popularity")
    private Double popularity;


    @ColumnInfo(name = "vote_count")
    private Integer vote_count;


    @ColumnInfo(name = "video")
    private Boolean video;


    @ColumnInfo(name = "vote_average")
    private Double vote_average;



    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public List<Integer> getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(List<Integer> genre_ids) {
        this.genre_ids = genre_ids;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public Integer getVote_count() {
        return vote_count;
    }

    public void setVote_count(Integer vote_count) {
        this.vote_count = vote_count;
    }

    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }

    public Double getVote_average() {
        return vote_average;
    }

    public void setVote_average(Double vote_average) {
        this.vote_average = vote_average;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @ColumnInfo(name = "key")
    private String key;

    public Movie(String poster_path, boolean adult, String overview, String release_date, List<Integer> genre_ids, Integer id,
                 String original_title, String original_language, String title, String backdrop_path, Double popularity,
                 Integer vote_count, Boolean video, Double vote_average) {
        this.poster_path = poster_path;
        this.adult = adult;
        this.overview = overview;
        this.release_date = release_date;
        this.genre_ids = genre_ids;
        this.id = id;
        this.original_title = original_title;
        this.original_language = original_language;
        this.title = title;
        this.backdrop_path = backdrop_path;
        this.popularity = popularity;
        this.vote_count = vote_count;
        this.video = video;
        this.vote_average = vote_average;
    }



    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(this.poster_path);
        dest.writeByte(this.adult ? (byte) 1 : (byte) 0);
        dest.writeString(this.overview);
        dest.writeString(this.release_date);
        dest.writeList(this.genre_ids);
        dest.writeValue(this.id);
        dest.writeString(this.original_title);
        dest.writeString(this.original_language);
        dest.writeString(this.title);
        dest.writeString(this.backdrop_path);
        dest.writeValue(this.popularity);
        dest.writeValue(this.vote_count);
        dest.writeValue(this.video);
        dest.writeValue(this.vote_average);

    }

    @Override
    public int describeContents() {
        return 0;
    }
    protected Movie(Parcel in) {
        this.title = in.readString();

        original_language = in.readString();
        original_title = in.readString();
        backdrop_path = in.readString();
        if (in.readByte() == 0) {
            popularity = null;
        } else {
            popularity = in.readDouble();
        }
        if (in.readByte() == 0) {
            vote_count = null;
        } else {
            vote_count = in.readInt();
        }
        poster_path = in.readString();
        adult = in.readByte() != 0;
        overview = in.readString();
        release_date = in.readString();
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }

        byte tmpVideo = in.readByte();
        video = tmpVideo == 0 ? null : tmpVideo == 1;
        if (in.readByte() == 0) {
            vote_average = null;
        } else {
            vote_average = in.readDouble();
        }
        key = in.readString();
    }
}