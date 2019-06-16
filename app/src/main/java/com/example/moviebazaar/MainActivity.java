package com.example.moviebazaar;
/*
 * Title :- Movie Bazaar Application
 * Version :- 1.0.0
 * Usage :- This activity is the starting point for the application.
 * Creator :- Veerabhadrarao kona
 * Date :- 01-06-2019
 * */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.moviebazaar.adapter.MovieListAdapter;
import com.example.moviebazaar.models.Movie;
import com.example.moviebazaar.models.MovieResponse;
import com.example.moviebazaar.retrofit_connection.Connection;
import com.example.moviebazaar.retrofit_connection.ConnectionInterface;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.moviebazaar.Global.APIKEY;
import static com.example.moviebazaar.Global.CATEGORYFAVORITE;
import static com.example.moviebazaar.Global.CATEGORYTOPRATED;
import static com.example.moviebazaar.Global.MOIVEDATATAG;

public class MainActivity extends AppCompatActivity implements MovieListAdapter.movieItemClickListener {
    RecyclerView movieList_rv;
    List<Movie> movies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        movieList_rv = (RecyclerView) findViewById(R.id.movie_list_rv);
        loadMovieList(CATEGORYFAVORITE);
    }
/*
* This method is used to hit the movie db server to fetch the relavant results according to the search.
* */
    private void loadMovieList(String category) {
        Call<MovieResponse> call = null;
        /*
        * The connection properties will be set.
        * By call.enqueue method the internet request will be done at back ground thread.
        * OnResponse will be called once the back ground process was completed and result will be handled in onResponse/onFailure methods
        * */
        ConnectionInterface apiService = Connection.getClient().create(ConnectionInterface.class);
        if(category.equalsIgnoreCase(CATEGORYFAVORITE)) {
           call = apiService.getFavouriteMovies(APIKEY);
        }else if(category.equalsIgnoreCase(CATEGORYTOPRATED)){
            call = apiService.getTopRatedMovies(APIKEY);
        }
        Objects.requireNonNull(call).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response != null) {
                    if (response.body() != null) {
                        movies = response.body().getResults();
                        Log.d("Number of movies: ", Integer.toString(movies.size()));
                        if (movies != null && movies.size() > 0) {
                            onSuccess(movies);
                        }
                    }
                } else {
                    Log.d("resp is not received", "null");
                }
            }
            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e("exception", t.toString());
            }
        });
    }

    /*
    * OnSucess response, the retrived data will be supplied as data source to the recycler view through adapter.
    * */
    private void onSuccess(List<Movie> movies) {
        MovieListAdapter mAdapter = new MovieListAdapter(this, movies);
        GridLayoutManager layoutManager = new GridLayoutManager(MainActivity.this, 2);
        movieList_rv.setHasFixedSize(true);
        movieList_rv.setLayoutManager(layoutManager);
        movieList_rv.setAdapter(mAdapter);

    }
/*
* OnClicking particular item in the screen, the details will be passed to the detialed screen through the below intent.
* */
    @Override
    public void onMovieItemClicked(int iPosition) {
        Intent intent = new Intent(MainActivity.this, MovieDetailsActivity.class);
        intent.putExtra(MOIVEDATATAG, (Serializable) movies.get(iPosition));
        startActivity(intent);
    }

    /*
    * Through onCreate options, user will be given opportunity to see the movies in the order of favourite or Top rated.
    * */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sort, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.favourite:
                loadMovieList(CATEGORYFAVORITE);
                break;
            case R.id.toprated:
                loadMovieList(CATEGORYTOPRATED);
                break;
            default:
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
