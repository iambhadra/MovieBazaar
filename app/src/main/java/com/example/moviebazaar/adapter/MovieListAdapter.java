package com.example.moviebazaar.adapter;
/*
 * Title :- FavouriteMovie Bazaar Application
 * Version :- 1.0.0
 * Usage :-This is a recycler view class used to show the movie posters as menu to user.
 * Creator :- Veerabhadrarao kona
 * Date :- 01-06-2019
 * */
import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moviebazaar.R;
import com.example.moviebazaar.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/*
* FavouriteMovie adapter was created to make connection to recycler view and data source
 * */

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieHolder> {


    final private movieItemClickListener mOnMovieClicked;
    private List<Movie> lMovieList ;

    public MovieListAdapter(movieItemClickListener myListener, List<Movie> movieList) {
        this.mOnMovieClicked = myListener;
        this.lMovieList = movieList;
    }

    /*
     * * View holder is created to create the template of the item and store the values.
     */
    class MovieHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView moviePoster_iv;
        TextView duration_tv, releaseYear_tv;

        public MovieHolder(@NonNull View itemView) {
            super(itemView);
            moviePoster_iv = (ImageView) itemView.findViewById(R.id.movie_poster_iv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnMovieClicked.onMovieItemClicked(getAdapterPosition());
        }
    }

    /*
      Here the item will instiated and will load the template
      */
    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutIdMovieitem = R.layout.movie_list_adapter;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachParentImmediatley = false;
        View view = inflater.inflate(layoutIdMovieitem, viewGroup, shouldAttachParentImmediatley);
        MovieHolder viewHolder = new MovieHolder(view);


          return viewHolder;
    }

    /*
    In bind view holder, the data will be binded.
    */

    @Override
    public void onBindViewHolder(@NonNull MovieHolder viewHolder, int i) {

        /*
        Here picaso library has been used to display the movie posters.
        If movie poster is not available, Then appropriate icon will be showed as indicated in the code
        */

        Picasso.get()
                .load("http://image.tmdb.org/t/p/w780/" + lMovieList.get(i).getPoster_path())
                .placeholder(R.drawable.ic_broken_image_black_24dp)
                .error(R.drawable.ic_image_black_24dp)
                .into(viewHolder.moviePoster_iv);
    }

    /*
    This method will retrun the count of the items.Accordingly, the queue for recycler will be created
    */
    @Override
    public int getItemCount() {
        return lMovieList.size();
    }

    /*
    This interface is created to provide clickable feature for the posters that are displayed
    */
    public interface movieItemClickListener {
        void onMovieItemClicked(int iPosition);
    }


}
