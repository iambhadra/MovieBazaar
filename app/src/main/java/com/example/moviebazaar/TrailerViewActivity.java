package com.example.moviebazaar;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.moviebazaar.models.Trailer;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.List;

public class TrailerViewActivity extends YouTubeBaseActivity {

    private YouTubePlayerView youTubePlayerView;
    private YouTubePlayer.OnInitializedListener onInitializedListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer_view);
        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.trailer_view);
        final List<Trailer> TrailerList = (List<Trailer>) getIntent().getSerializableExtra("TrailerList");
        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                     youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                youTubePlayer.loadVideo(TrailerList.get(0).getTrailerKey());
                if(!b){
                    youTubePlayer.cueVideo(TrailerList.get(0).getTrailerKey() );
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

                String errorMessage = youTubeInitializationResult.toString();
                Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
                Log.d("errorMessage:", errorMessage);
            }
        };

        youTubePlayerView.initialize("AIzaSyCfvXI0NylDIEVczIV_gSI7q3TG2Py9dkU",onInitializedListener);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
