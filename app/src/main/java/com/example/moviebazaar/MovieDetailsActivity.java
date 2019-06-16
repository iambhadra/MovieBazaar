package com.example.moviebazaar;
/*
 * Title :- Movie Bazaar Application
 * Version :- 1.0.0
 * Usage :- In this activity , the selected movie details will be displayed.
 * Creator :- Veerabhadrarao kona
 * Date :- 01-06-2019
 * */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moviebazaar.models.Movie;
import com.squareup.picasso.Picasso;


import static com.example.moviebazaar.Global.MOIVEDATATAG;

public class MovieDetailsActivity extends AppCompatActivity {

    private ImageView moviePoster_iv;
    private TextView releaseYear_tv, duration_tv, movieRating_tv, movieDescription_tv, movie_title_tv;
    private String moviePosterPath, movieName, movieReleaseDate, movieDescription;
    private Double movieRating;

    Movie lMovieDetails;

    /*
     * This activity will be created to show the details of the movie.
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        lMovieDetails = (Movie) getIntent().getSerializableExtra(MOIVEDATATAG);
        movie_title_tv = (TextView) findViewById(R.id.movie_title_tv);
        moviePoster_iv = (ImageView) findViewById(R.id.detail_movie_poster_iv);
        releaseYear_tv = (TextView) findViewById(R.id.release_year_tv);
        duration_tv = (TextView) findViewById(R.id.movie_duration_tv);
        movieRating_tv = (TextView) findViewById(R.id.rating_tv);
        movieDescription_tv = (TextView) findViewById(R.id.movie_description);

        movieName = lMovieDetails.getTitle();
        movieReleaseDate = lMovieDetails.getReleaseDate();
        movieRating = lMovieDetails.getVoteAverage();
        movieDescription = lMovieDetails.getOverview();
        moviePosterPath = lMovieDetails.getPosterPath();

        /*
         * Here the data will be shown like poster, date of release, duration, title, rating.
         * If the data was not available , then in that place "NA" will be shown as default
         * */
        Picasso.get()
                .load("http://image.tmdb.org/t/p/w780/" + moviePosterPath)
                .placeholder(R.drawable.ic_broken_image_black_24dp)
                .error(R.drawable.ic_image_black_24dp)
                .into(moviePoster_iv);
        movie_title_tv.setText((movieName == null) ? "  NA" : movieName);
        releaseYear_tv.setText(movieReleaseDate == null ? "NA" : movieReleaseDate);
        movieRating_tv.setText(movieRating.toString() == null ? "NA" : movieRating.toString());
        movieDescription_tv.setText(movieDescription == null ? "NA" : movieDescription);
    }
}
