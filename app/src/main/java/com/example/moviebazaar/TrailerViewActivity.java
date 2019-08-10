package com.example.moviebazaar;
/*
 * Title :- Movie Bazaar Application
 * Version :- 1.0.1
 * Usage :- This activity is used to play the selected trailer of the movie.
 * Creator :- Veerabhadrarao kona
 * Date :- 14-07-2019
 * */
import android.app.Dialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.moviebazaar.models.Trailer;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.List;

import static com.example.moviebazaar.Global.YOUTUBEAPI;

public class TrailerViewActivity extends YouTubeBaseActivity {

    private YouTubePlayerView youTubePlayerView;
    private YouTubePlayer.OnInitializedListener onInitializedListener;
    String trailerKey = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer_view);
        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.trailer_view);
        final List<Trailer> TrailerList = (List<Trailer>) getIntent().getParcelableExtra("TrailerList");
        selectTrailerVideo(TrailerList);

    }
/*
* This method will create a dialog to select a trailer video from the provided list related to movie
* */
    private boolean selectTrailerVideo(final List<Trailer> trailerList) {
        final Dialog dialog = new Dialog(TrailerViewActivity.this, R.style.Dialog);
        dialog.setTitle(getString(R.string.selectMovie));
        dialog.setContentView(R.layout.radio_dialog);
        final RadioGroup rg_radioGroup = (RadioGroup) dialog.findViewById(R.id.rg_radiodialog);
        for (int i = 0; i < trailerList.size(); i++) {
            RadioButton rb_movieOption = new RadioButton(this);
            rb_movieOption.setText(trailerList.get(i).getMovieName());
            rb_movieOption.setPadding(5, 10, 5, 5);
            rg_radioGroup.addView(rb_movieOption);
        }
        dialog.show();

        rg_radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                int count = group.getChildCount();
                for (int i = 0; i < count; i++) {
                    RadioButton rb = (RadioButton) group.getChildAt(i);
                    if (rb.getId() == checkedId) {
                        for (int j = 0; j < trailerList.size(); j++) {
                            if (rb.getText().toString().equalsIgnoreCase(trailerList.get(i).getMovieName())) {
                                trailerKey = trailerList.get(i).getTrailerKey();
                                dialog.dismiss();
                                displayTrailer(trailerKey);
                            }
                        }
                    }
                }
            }
        });

        return false;

    }
/*
* This method will play the selected trailer using youtube library YouTubeAndroidPlayerAPi.jar
* */
    private void displayTrailer(final String finalTrailerKey) {
        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                youTubePlayer.loadVideo(finalTrailerKey);
                if (!b) {
                    youTubePlayer.cueVideo(finalTrailerKey);
                }
            }
            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

                String errorMessage = youTubeInitializationResult.toString();
                Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
                Log.d("errorMessage:", errorMessage);
            }
        };
        youTubePlayerView.initialize(YOUTUBEAPI, onInitializedListener);

    }
/*
* This method will be used to restore the configuration and state of playback of video while rotated.
* */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
