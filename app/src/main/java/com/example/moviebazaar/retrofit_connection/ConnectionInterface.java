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
import com.example.moviebazaar.models.ReviewResponse;
import com.example.moviebazaar.models.TrailerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ConnectionInterface {
    @GET("popular")
    Call<MovieResponse> getFavouriteMovies(@Query("api_key") String apiKey);

    @GET("top_rated")
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("{id}")
    Call<MovieResponse> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);

    @GET("{id}/videos")
    Call<TrailerResponse> getTrailers(@Path("id") int movieId, @Query("api_key") String apikey);

    @GET("{id}/reviews")
    Call<ReviewResponse> getReview(@Path("id") int movieId, @Query("api_key") String apiKey);

}

