package com.example.moviebazaar.database;
/*
 * Title :- Movie Bazaar Application
 * Version :- 1.0.1
 * Usage :- This interface is used to write the queries for the database.
 * Creator :- Veerabhadrarao kona
 * Date :- 14-07-2019
 * */
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.moviebazaar.models.Movie;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM favouriteMovies ORDER BY id DESC")
    LiveData<List<Movie>> loadFavouriteMovies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovieDetails(Movie favouriteMovieData);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMovieData(Movie favouriteMovieData);

    @Query("SELECT * from favouriteMovies where id == :MovieId")
    Movie queryMovieExistance(int MovieId);
    @Delete
    void deleteMovieEntry(Movie favouriteMovieData);
}
