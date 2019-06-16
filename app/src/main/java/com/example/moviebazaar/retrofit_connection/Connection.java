package com.example.moviebazaar.retrofit_connection;
/*
 * Title :- Movie Bazaar Application
 * Version :- 1.0.0
 * Usage :- This class is used to establish the http connection to the movie DB.
         Here Retrofit method is used.
 * Creator :- Veerabhadrarao kona
 * Date :- 01-06-2019
 * */
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.*;

public class Connection {
    public static String URL;
    public static Retrofit retrofit = null;

    Connection(String url) {
        this.URL = url;
    }
/*
*  Retrofit method will establish the connection after taking proper parameters
*  */

    public static Retrofit getClient() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder().setLenient().create();
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://api.themoviedb.org/3/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
