package com.example.moviebazaar;
/*
 * Title :- Movie Bazaar Application
 * Version :- 1.0.0
 * Usage :- In this activity , the selected movie details will be displayed.
 * Creator :- Veerabhadrarao kona
 * Date :- 01-06-2019
 * */

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.moviebazaar.models.Movie;
import com.example.moviebazaar.models.MovieResponse;
import com.example.moviebazaar.models.Review;
import com.example.moviebazaar.models.ReviewResponse;
import com.example.moviebazaar.models.Trailer;
import com.example.moviebazaar.models.TrailerResponse;
import com.example.moviebazaar.retrofit_connection.Connection;
import com.example.moviebazaar.retrofit_connection.ConnectionInterface;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
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
    private Button watchTrailer_btn,favourites_btn,btn_watchreview;
    private TextView releaseYear_tv, duration_tv, movieRating_tv, movieDescription_tv, movie_title_tv;
    private String moviePosterPath, movieName, movieReleaseDate, movieDescription;
    private Double movieRating;
    Movie lMovieDetails;


    private YouTubePlayerView youTubePlayerView;
    private YouTubePlayer.OnInitializedListener onInitializedListener;

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
        watchTrailer_btn = (Button) findViewById(R.id.btn_watchtrailer);
        favourites_btn = (Button) findViewById(R.id.add_to_favourites_btn);
        btn_watchreview = (Button) findViewById(R.id.btn_watchreview);

        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.trailer_view);


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
    }

    private void RequestForReview() {
        Call call = null;
        ConnectionInterface connectionInterface = Connection.getClient().create(ConnectionInterface.class);
        call = connectionInterface.getReview(lMovieDetails.getId(),APIKEY);
        Objects.requireNonNull(call).enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                if(response!= null){
                    if(response.body() != null){
                        List<Review> reviewList = response.body().getReviewResults();
                        ShowMovieReview(reviewList);
                    }
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });
    }

    private void RequestForTrailerLink() {
        Call call = null;
        ConnectionInterface connectionInterface = Connection.getClient().create(ConnectionInterface.class);
        call = connectionInterface.getTrailers(lMovieDetails.getId(),APIKEY);
        Objects.requireNonNull(call).enqueue(new Callback<TrailerResponse>() {
            @Override
            public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {
                if(response!=null){
                    if(response.body() != null){
                        List<Trailer> trailerList = response.body().getTrailerResults();
                        if(trailerList!= null || !trailerList.isEmpty()){
                       /*
                       * Here In Landscape mode , player is not playing continuously.So Commented this method
                       * */

                            //     PlayTrailer(trailerList);

                            Intent trailerIntent = new Intent(MovieDetailsActivity.this,TrailerViewActivity.class);
                            trailerIntent.putExtra("TrailerList",(Serializable) trailerList);
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


    private void ShowMovieReview(List<Review> reviewList) {
        AlertDialog.Builder review = new AlertDialog.Builder(MovieDetailsActivity.this);
        review.setTitle("Moview Review");
        review.setMessage(reviewList.get(0).getContent())
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });


        /*Dialog dialog = new Dialog(MovieDetailsActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.review_display);
        dialog.show();*/


    }


    private void PlayTrailer(final List<Trailer> TrailerList) {
        /*
        * Youtube api approach
        * */

        youTubePlayerView.setVisibility(View.VISIBLE);
        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                youTubePlayer.loadVideo(TrailerList.get(0).getTrailerKey());
                if(!b){
                    youTubePlayer.cueVideo(TrailerList.get(0).getTrailerKey() );
                }
               /*  * Dialog Approach
                 *
                Dialog dialog = new Dialog(MovieDetailsActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.review_display);
                dialog.show();
                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(
                        WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                layoutParams.copyFrom(dialog.getWindow().getAttributes());
                VideoView videoView = (VideoView) dialog.findViewById(R.id.trailer_view_dialog);
                Uri uri = Uri.parse("http://www.youtube.com/watch?v="+ TrailerList.get(0).getTrailerKey());
                videoView.set(youTubePlayer.loadVideo(TrailerList.get(0).getTrailerKey()));
                videoView.canPause();
                videoView.canSeekBackward();
                videoView.canSeekForward();
                videoView.start();*/
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

                String errorMessage = youTubeInitializationResult.toString();
                Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
                Log.d("errorMessage:", errorMessage);
            }
        };

        youTubePlayerView.initialize("AIzaSyCfvXI0NylDIEVczIV_gSI7q3TG2Py9dkU",onInitializedListener);

/*
* Intent Approach
* Here with this method , external browser will redirected to play trailer.
* */
     /*   Intent trailerIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + TrailerList.get(0).getTrailerKey()));
        startActivity(trailerIntent);*/
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }


}
