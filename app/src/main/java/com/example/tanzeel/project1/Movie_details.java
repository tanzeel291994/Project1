package com.example.tanzeel.project1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;


public class Movie_details extends Activity {
    MainActivity main;
    int id;
    String LOG_CAT="ef";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);
        main = new MainActivity();
        Intent i = getIntent();
        //Log.v(LOG_CAT, "ini");
        id = i.getExtras().getInt("id");
        TextView title_text = (TextView) findViewById(R.id.original_title);
        TextView rating_text = (TextView) findViewById(R.id.user_rating);
        TextView date_text = (TextView) findViewById(R.id.release_date);
        TextView synopsis_text = (TextView) findViewById(R.id.synopsis);
        ImageView thumbnail = (ImageView) findViewById(R.id.thumbnail_image_view);
        try
        {
            title_text.setText(MainActivity.movies.get(id).title);
            rating_text.setText(MainActivity.movies.get(id).ratings);
            synopsis_text.setText(MainActivity.movies.get(id).synopsis);
            date_text.setText(MainActivity.movies.get(id).date);
            Picasso.with(this)
                    .load(MainActivity.movies.get(id).thumbnail_path)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(thumbnail);

        }
       catch (Exception e)
       {
           Log.v(LOG_CAT,e.toString());
       }
    }
}