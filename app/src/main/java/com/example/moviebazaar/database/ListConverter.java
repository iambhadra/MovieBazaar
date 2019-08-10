package com.example.moviebazaar.database;
/*
 * Title :- Movie Bazaar Application
 * Version :- 1.0.1
 * Usage :- This class is used to convert the genre ID list to string and vice versa.
 * Creator :- Veerabhadrarao kona
 * Date :- 14-07-2019
 * */
import android.arch.persistence.room.Database;
import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ListConverter {
    /*
    * This method will be used to convert String to integer List
    * */
    @TypeConverter
    public static List<Integer> toList(String listString){
        Gson gson = new Gson();
        List<Integer> object = gson.fromJson(listString, ArrayList.class);
        return object;
    }
    /*
     * This method will be used to convert integer list to string
     * */

    @TypeConverter
    public static String toString(List<Integer> listObject){
        Gson gson = new Gson();
        String string = gson.toJson(listObject);
        return string;
    }
}
