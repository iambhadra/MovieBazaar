package com.example.moviebazaar.database;
/*
 * Title :- Movie Bazaar Application
 * Version :- 1.0.1
 * Usage :- This interface is used to instance for the created database and its architecture.
 * Creator :- Veerabhadrarao kona
 * Date :- 14-07-2019
 * */
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.util.Log;

import com.example.moviebazaar.models.Movie;

@Database(entities = {Movie.class},version=1,exportSchema = false)
@TypeConverters(ListConverter.class)
public abstract class MovieDatabase extends RoomDatabase {

    private static final String LOG_TAG = MovieDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "MovieDataBase";
    private static MovieDatabase sInstance;

    public static MovieDatabase getInstance(Context context){
        if(sInstance == null){
            synchronized (LOCK){
                Log.d(LOG_TAG,"Creating new Database instance");
                sInstance= Room.databaseBuilder(context.getApplicationContext(),
                                MovieDatabase.class,MovieDatabase.DATABASE_NAME).build();
            }
        }
        Log.d(LOG_TAG,"Getting the database instance");
        return  sInstance;
    }

    public abstract MovieDao movieDao();
}
