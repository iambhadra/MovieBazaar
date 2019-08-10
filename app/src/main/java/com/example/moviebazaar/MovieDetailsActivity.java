package com.example.moviebazaar;
/*
 * Title :- FavouriteMovie Bazaar Application
 * Version :- 1.0.0
 * Usage :- In this activity , the selected movie details will be displayed.
 * Creator :- Veerabhadrarao kona
 * Date :- 01-06-2019
 * */

import android.app.AlertDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviebazaar.database.MovieDao;
import com.example.moviebazaar.database.MovieDatabase;
import com.example.moviebazaar.models.Movie;
import com.example.moviebazaar.models.Review;
import com.example.moviebazaar.models.ReviewResponse;
import com.example.moviebazaar.models.Trailer;
import com.example.moviebazaar.models.TrailerResponse;
import com.example.moviebazaar.retrofit_connection.Connection;
import com.example.moviebazaar.retrofit_connection.ConnectionInterface;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.squareup.picasso.Picasso;


import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.moviebazaar.Global.APIKEY;
import static com.example.moviebazaar.Global.MOIVEDATATAG;

public class MovieDetailsActivity extends YouTubeBaseActivity {

    private ImageView moviePoster_iv;
    private Button watchTrailer_btn, favourites_btn, btn_watchreview;
    private TextView releaseYear_tv, duration_tv, movieRating_tv, movieDescription_tv, movie_title_tv;
    private String moviePosterPath, movieName, movieReleaseDate, movieDescription;
    private Double movieRating;
    Movie lMovieDetails;
    private MovieDatabase movieDatabase;

    private YouTubePlayerView youTubePlayerView;
    private YouTubePlayer.OnInitializedListener onInitializedListener;

    /*
     * This activity will be created to show the details of the movie.
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        lMovieDetails = (Movie) getIntent().getParcelableExtra(MOIVEDATATAG);
        movie_title_tv = (TextView) findViewById(R.id.movie_title_tv);
        moviePoster_iv = (ImageView) findViewById(R.id.detail_movie_poster_iv);
        releaseYear_tv = (TextView) findViewById(R.id.release_year_tv);
        duration_tv = (TextView) findViewById(R.id.movie_duration_tv);
        movieRating_tv = (TextView) findViewById(R.id.rating_tv);
        movieDescription_tv = (TextView) findViewById(R.id.movie_description);
        watchTrailer_btn = (Button) findViewById(R.id.btn_watchtrailer);
        favourites_btn = (Button) findViewById(R.id.add_to_favourites_btn);
        btn_watchreview = (Button) findViewById(R.id.btn_watchreview);

        movieDatabase = MovieDatabase.getInstance(getApplicationContext());
        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.trailer_view);


        movieName = lMovieDetails.getTitle();
        movieReleaseDate = lMovieDetails.getRelease_date();
        movieRating = lMovieDetails.getVote_average();
        movieDescription = lMovieDetails.getOverview();
        moviePosterPath = lMovieDetails.getPoster_path();


        /*
         * Here the data will be shown like poster, date of release, duration, title, rating.
         * If the data was not available , then in that place "NA" will be shown as default
         * */
        Picasso.get()
                .load("http://image.tmdb.org/t/p/w780/" + moviePosterPath)
                .placeholder(R.drawable.ic_broken_image_black_24dp)
                .error(R.drawable.ic_image_black_24dp)
                .into(moviePoster_iv);
        movie_title_tv.setText((movieName == null) ? "NA" : movieName);
        releaseYear_tv.setText(movieReleaseDate == null ? "NA" : movieReleaseDate);
        // movieRating_tv.setText(movieRating.toString() == null ? "NA" : movieRating.toString() + "/10");
        movieDescription_tv.setText(movieDescription == null ? "NA" : movieDescription);

       /* AsyncTask<Movie, Void, Movie> existedMovie =*/
       /* new queryingAsyncTask(movieDatabase.movieDao()).execute(lMovieDetails);
        if(existedMovie.getId() != null){
            favourites_btn.setText(getString(R.string.mark_unfavourite));
        }else{
            favourites_btn.setText(getString(R.string.mark_as_favorite));
        }
*/
        watchTrailer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestForTrailerLink();
            }
        });
        btn_watchreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestForReview();
            }
        });
        favourites_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (favourites_btn.getText().toString().equalsIgnoreCase(getString(R.string.mark_as_favorite))) {
                    new insertAsyncTask(movieDatabase.movieDao()).execute(lMovieDetails);
                    favourites_btn.setText(getString(R.string.mark_unfavourite));
                } else if (favourites_btn.getText().toString().equalsIgnoreCase(getString(R.string.mark_unfavourite))) {
                    new deleteAsyncTask(movieDatabase.movieDao()).execute(lMovieDetails);
                    favourites_btn.setText(getString(R.string.mark_as_favorite));
                }
            }
        });
    }

    /*
     * This API will be used to query for the reviews of the movie
     * */
    private void RequestForReview() {
        Call call = null;
        ConnectionInterface connectionInterface = Connection.getClient().create(ConnectionInterface.class);
        call = connectionInterface.getReview(lMovieDetails.getId(), APIKEY);
        Objects.requireNonNull(call).enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                if (response != null) {
                    if (response.body() != null) {
                        List<Review> reviewList = response.body().getResults();
                        ShowMovieReview(reviewList);
                    }
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });
    }

    /*
     * This method will invoke call to request the links for trailer videos.
     * then pass those list to play with youtube API in next intent.
     * */
    private void RequestForTrailerLink() {
        Call call = null;
        ConnectionInterface connectionInterface = Connection.getClient().create(ConnectionInterface.class);
        call = connectionInterface.getTrailers(lMovieDetails.getId(), APIKEY);
        Objects.requireNonNull(call).enqueue(new Callback<TrailerResponse>() {
            @Override
            public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {
                if (response != null) {
                    if (response.body() != null) {
                        List<Trailer> trailerList = response.body().getTrailerResults();
                        if (trailerList != null || !trailerList.isEmpty()) {
                            Intent trailerIntent = new Intent(MovieDetailsActivity.this, TrailerViewActivity.class);
                            trailerIntent.putExtra("TrailerList", (Parcelable) trailerList);
                            startActivity(trailerIntent);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });
    }

    /*
     * With this method , first review in the list will be shown as dialog
     * */
    private void ShowMovieReview(List<Review> reviewList) {
        AlertDialog.Builder review = new AlertDialog.Builder(MovieDetailsActivity.this);
        review.setTitle("Moview Review");
        review.setMessage(reviewList.get(0).getContent())
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
    }

    /*  @Override
      public void onConfigurationChanged(Configuration newConfig) {
          super.onConfigurationChanged(newConfig);
      }*/
    /*
     * This aAsync task will run the database insertion in backgroud thread
     * */
    private static class insertAsyncTask extends AsyncTask<Movie, Void, Void> {

        private MovieDao movieDao;

        public insertAsyncTask(MovieDao movieDao) {
            this.movieDao = movieDao;
        }

        @Override
        protected Void doInBackground(Movie... movies) {
            movieDao.insertMovieDetails(movies[0]);
            return null;
        }


    }

    /*
     * This aAsync task will run the database querying in backgroud thread
     *
     * */
    private static class queryingAsyncTask extends AsyncTask<Movie, Void, Movie> {

        private MovieDao movieDao;
        Movie existedMovie;

        public queryingAsyncTask(MovieDao movieDao) {
            this.movieDao = movieDao;
        }

        @Override
        protected Movie doInBackground(Movie... movies) {
            existedMovie = movieDao.queryMovieExistance(movies[0].getId());
            return existedMovie;

        }
    }

    /*
     * This aAsync task will run the database insertion in backgroud thread
     * */
    private static class deleteAsyncTask extends AsyncTask<Movie, Void, Void> {

        private MovieDao movieDao;

        public deleteAsyncTask(MovieDao movieDao) {
            this.movieDao = movieDao;
        }

        @Override
        protected Void doInBackground(Movie... movies) {
            movieDao.deleteMovieEntry(movies[0]);
            return null;
        }


    }

}

