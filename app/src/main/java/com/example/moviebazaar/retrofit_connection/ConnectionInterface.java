package com.example.moviebazaar.retrofit_connection;
/*
 * Title :- Movie Bazaar Application
 * Version :- 1.0.0
 * Usage :- This class is used to establish the API end points to the movie DB.
         Here method of connection, request and response formats will be specified along with th query parameters.
 * Creator :- Veerabhadrarao kona
 * Date :- 01-06-2019
 * */


import com.example.moviebazaar.models.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ConnectionInterface {
    @GET("movie/popular")
    Call<MovieResponse> getFavouriteMovies(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/{id}")
    Call<MovieResponse> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);


}

