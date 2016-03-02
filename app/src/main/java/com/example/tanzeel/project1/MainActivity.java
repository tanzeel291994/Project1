package com.example.tanzeel.project1;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    HttpURLConnection urlConnection=null;
    BufferedReader reader=null;
    String jsonstring=null;
    String TAG_RESULTS="results";
    String TAG_POSTER_PATH="poster_path";
    JSONArray result_list;
    public static ArrayList<Uri> image_uri;
    int RESULT_STATUS;
    Movie_adapter madapter;
    String LOG_CAT="ef";
    public static ArrayList<Movie_model> movies;
    String discover_movies="https://api.themoviedb.org/3/discover/movie?api_key=60e7c427c564cf915fd06a078398855a&page=1";
    String sort_movies="https://api.themoviedb.org/3/discover/movie?api_key=60e7c427c564cf915fd06a078398855a&page=1&sort_by=vote_average.des";
    Networking networking;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image_uri=new ArrayList<Uri>();
        GridView gridView = (GridView) findViewById(R.id.grid_view);
        madapter=new Movie_adapter(this);
        movies=new ArrayList<Movie_model>();
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        gridView.setAdapter(madapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Intent i = new Intent(getApplicationContext(), Movie_details.class);
                i.putExtra("id", position);
                startActivity(i);
            }
        });
        networking=new Networking();
        networking.execute(discover_movies);

    }
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item)
    {
        super.onOptionsItemSelected(item);
        switch(item.getItemId())
        {
            case R.id.sort:
                Toast.makeText(getBaseContext(), "Sorting......", Toast.LENGTH_SHORT).show();
                networking.cancel(true);
                movies.clear();
                image_uri.clear();
                Networking networking_sort=new Networking();
                networking_sort.execute(sort_movies);
                break;
        }
        return true;
    }
    public void networkingcode(String s) throws IOException
    {
        URL url = new URL(s);
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.connect();
        InputStream inputStream=urlConnection.getInputStream();
        StringBuffer stringBuffer=new StringBuffer();
        reader=new BufferedReader( new InputStreamReader(inputStream));
        String line;
        while ((line=reader.readLine())!=null)
        {
            stringBuffer.append(line + "\n");
        }
        if(stringBuffer.length()==0)
        {
            jsonstring=null; //buffer was empty
        }
        else
        {
            jsonstring = stringBuffer.toString();
            Log.v(LOG_CAT,jsonstring);
        }
       if(jsonstring!=null)
        {
            try
            {
                JSONObject jsonObj = new JSONObject(jsonstring);
                result_list=jsonObj.getJSONArray(TAG_RESULTS);
                RESULT_STATUS=1;
                for(int i=0;i<result_list.length();i++)
                {
                    JSONObject jobj=result_list.getJSONObject(i);
                     String poster_path="http://image.tmdb.org/t/p/w300"+jobj.getString(TAG_POSTER_PATH);
                    movies.add(new Movie_model(
                            "http://image.tmdb.org/t/p/w300" + jobj.getString("backdrop_path"),
                            jobj.getString("original_title"), jobj.getString("release_date"),
                            jobj.getString("overview"),
                            jobj.getString("vote_average")
                    ));

                    Uri uri=Uri.parse(poster_path);
                   // Log.v(LOG_CAT,movies.get(i).date);
                    image_uri.add(uri);
                }

            }
            catch(JSONException e)
            {

            }
        }
        if(urlConnection!=null)
            urlConnection.disconnect();
        if(reader!=null)
        {
            try{reader.close();}
            catch (final IOException e){}
        }
    }
    public class Networking extends AsyncTask <String, Void,Void>
    {


        @Override
        protected Void doInBackground(String... params)
        {
            try
            {
                networkingcode(params[0]);
            }
            catch (IOException e){}
                return  null;
        }



        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
           if(RESULT_STATUS==1)
           {
               madapter.setGridData();
           }

        }
    }
}
