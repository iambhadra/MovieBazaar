package com.example.moviebazaar;
/*
 * Title :- FavouriteMovie Bazaar Application
 * Version :- 1.0.0
 * Usage :- This activity is the starting point for the application.
 * Creator :- Veerabhadrarao kona
 * Date :- 01-06-2019
 * */

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.moviebazaar.adapter.MovieListAdapter;
//import com.example.moviebazaar.database.FavouriteMovie;
import com.example.moviebazaar.database.MovieDatabase;
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
    private MovieDatabase movieDatabase;

    private ArrayList<Movie> movieInstance = new ArrayList<>();
    int mScrollPosition;
    MovieListAdapter mAdapter;
    GridLayoutManager gridLayoutManager=new GridLayoutManager(this, 2);
    private final String KEY_RECYCLER_STATE = "recycler_state";
    private static Bundle mBundleRecyclerViewState;
    private Parcelable mListState = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        movieList_rv = (RecyclerView) findViewById(R.id.movie_list_rv);
        movieDatabase = MovieDatabase.getInstance(getApplicationContext());
        mAdapter = new MovieListAdapter(this, movieInstance);
        movieList_rv.setAdapter(mAdapter);
        movieList_rv.setLayoutManager(gridLayoutManager);
        loadMovieList(CATEGORYFAVORITE);
   }
/*
* Here we are saving the state of the recycer view to restore after rotation
* */
    @Override
    protected void onPause() {
        super.onPause();
        mBundleRecyclerViewState = new Bundle();
        mListState = movieList_rv.getLayoutManager().onSaveInstanceState();
        mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE, mListState);
    }
/*
* In the below configuration , we are defining the restored state along the number columns on orientation of device.
* */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
      //  columns = getResources().getInteger(R.integer.gallery_columns);

        if (mBundleRecyclerViewState != null) {
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    mListState = mBundleRecyclerViewState.getParcelable(KEY_RECYCLER_STATE);
                    movieList_rv.getLayoutManager().onRestoreInstanceState(mListState);

                }
            }, 50);
        }

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

            gridLayoutManager.setSpanCount(4);

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {

            gridLayoutManager.setSpanCount(2);

        }
        movieList_rv.setLayoutManager(gridLayoutManager);
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
        if (category.equalsIgnoreCase(CATEGORYFAVORITE)) {
            call = apiService.getFavouriteMovies(APIKEY);
        } else if (category.equalsIgnoreCase(CATEGORYTOPRATED)) {
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
    private void onSuccess(List<Movie> moviesList) {
        movies = moviesList;
        MovieListAdapter mAdapter = new MovieListAdapter(this, moviesList);
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
        intent.putExtra(MOIVEDATATAG, (Parcelable) movies.get(iPosition));
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
        switch (item.getItemId()) {
            case R.id.favourite:
                loadMovieList(CATEGORYFAVORITE);
                break;
            case R.id.toprated:
                loadMovieList(CATEGORYTOPRATED);
                break;
            /*
             * This option is added for the pivoting the saved favourite movie collection from database
             * */
            case R.id.mylist:
                displayMyFavouriteList();
            default:
                break;

        }
        return super.onOptionsItemSelected(item);
    }
/*
* This method will be used to retrive the saved favourite movie list from database.
* */
    private void displayMyFavouriteList() {
        final LiveData<List<Movie>> moviesList = movieDatabase.movieDao().loadFavouriteMovies();
        moviesList.observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> moviesList) {
                onSuccess(moviesList);
            }
        });
    }
}
