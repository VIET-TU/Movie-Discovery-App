package com.example.telegram.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.telegram.R;
import com.example.telegram.adapters.ActorListAdapter;
import com.example.telegram.adapters.FilmListAdapter;
import com.example.telegram.models.Actor;
import com.example.telegram.models.DetailMovie;
import com.example.telegram.models.Film;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private TextView txt_movieName,txt_movieStart,txt_movieTime, txt_overview, txt_name_video;
    private int movieId;
    private ImageView image_backdrop,image_poster,image_back;
    private RecyclerView.Adapter adapterActorList,adapterGenres;
    private RecyclerView recyclerViewActor,recyclerCast;
    private NestedScrollView scrollView;
    private DetailMovie deltailMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);

        movieId = getIntent().getIntExtra("id",0);







        initView();
        fetchMovieDetail();

        fetchMoveVideo();
    }

    private void EmbedVideoMovie(String key){
        WebView webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        String videoUrl = "https://www.youtube.com/embed/" + key;
        webView.loadUrl(videoUrl);

    }

    private void fetchMoveVideo(){
        String url = "https://api.themoviedb.org/3/movie/" + movieId + "/videos?api_key=95f2419536f533cdaa1dadf83c606027";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray results = response.getJSONArray("results");
                            for (int i = 0; i < results.length(); i++) {
                                JSONObject jsonObject = results.getJSONObject(i);
                                String name = jsonObject.getString("name");
                                String key = jsonObject.getString("key");
                                txt_name_video.setText(name);
                                EmbedVideoMovie(key);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);

                Log.d("debug", error.getMessage());
                Toast.makeText(DetailActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Add the request to the RequestQueue.
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }

    private void initView(){
        txt_movieName = findViewById(R.id.txt_movieName);
        progressBar= findViewById(R.id.progressBar_detail);
        scrollView = findViewById(R.id.scrollView3);
        image_backdrop = findViewById(R.id.image_backdrop);
        txt_movieStart = findViewById(R.id.txt_movieStart);
        txt_movieTime = findViewById(R.id.txt_movieTime);
        image_back = findViewById(R.id.image_back);

        recyclerViewActor = findViewById(R.id.recycler_casts);
        recyclerViewActor.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        txt_overview = findViewById(R.id.txt_overview);

        txt_name_video = findViewById(R.id.txt_name_video);

        image_back.setOnClickListener( v -> {
            finish();
        });
    }

    public void fetchMovieDetail(){
        progressBar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);

        String url = "https://api.themoviedb.org/3/movie/" + movieId + "?api_key=95f2419536f533cdaa1dadf83c606027";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            List<Film> listFilms = new ArrayList<>();

                            progressBar.setVisibility(View.GONE);
                            scrollView.setVisibility(View.VISIBLE);

                            int id = response.getInt("id");
                                String title = response.getString("title");
                                String release_date = response.getString("release_date");
                                Double vote_average =  response.getDouble("vote_average");
                                String poster = "https://image.tmdb.org/t/p/original" + response.getString("poster_path"); // Fix poster path key
                                int vote_count = response.getInt("vote_count");
                            String overview = response.getString("overview");
                                String backdrop_path = " https://image.tmdb.org/t/p/original" + response.getString("backdrop_path");
                            txt_movieName.setText(title);
                               txt_movieStart.setText(vote_average.toString());
                               txt_movieTime.setText(release_date);
                               txt_overview.setText(overview);
                            Glide.with(DetailActivity.this).load(backdrop_path.trim()).into(image_backdrop);
                                fetchCredits();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);

                Log.d("debug", error.getMessage());
                Toast.makeText(DetailActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Add the request to the RequestQueue.
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }

    private void fetchCredits() {
        String url = "https://api.themoviedb.org/3/movie/" + movieId + "/credits?api_key=95f2419536f533cdaa1dadf83c606027";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            List<Actor> actors = new ArrayList<>();
                            JSONArray results = response.getJSONArray("cast");
                            for (int i = 0; i < results.length(); i++) {
                                JSONObject jsonObject = results.getJSONObject(i);
                                String name_actor = jsonObject.getString("name");
                                String image_actor = "https://image.tmdb.org/t/p/original" + jsonObject.getString("profile_path");
                                Actor actor = new Actor(name_actor,image_actor.trim());
                                actors.add(actor);
                            }

                            adapterActorList = new ActorListAdapter(actors);

                            recyclerViewActor.setAdapter(adapterActorList);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DetailActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Add the request to the RequestQueue.
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }
}