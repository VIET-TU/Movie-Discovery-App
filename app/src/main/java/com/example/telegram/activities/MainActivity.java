package com.example.telegram.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.telegram.R;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.telegram.activities.auth.LoginActivity;
import com.example.telegram.adapters.FilmListAdapter;
import com.example.telegram.adapters.SliderAdapters;
import com.example.telegram.apis.MovieApi;
import com.example.telegram.models.Film;
import com.example.telegram.models.Genre;
import com.example.telegram.models.SliderItems;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private List<SliderItems> sliderItems;
    private List<Film> listFilms = new ArrayList<Film>();

    private TextView txt_search, txt_slider_title, txt_slide_genre1,txt_slide_genre2,txt_slide_genre3;

    private List<Genre> genres;

    RecyclerView.Adapter adapterTopRated,adapterTrending,adapterNowPlaying;
    private RecyclerView reclerViewTopRated,reclerViewNowPlaying,reclerViewTrending;
    private ProgressBar ld_now_playing,ld_top_rated,ld_treding;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
         this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        Toast.makeText(MainActivity.this, "Login successfully",
                Toast.LENGTH_SHORT).show();
        initView();
        fetGenres();
        banners();
        fetchNowPlaying();
        fetchTopRated();
        fetchTrending();

        txt_search.setOnClickListener(v -> {
            Intent intent = new Intent(this, SearchActivity.class);
            this.startActivity(intent);
        });

    }


    private void banners(){
        viewPager2 = findViewById(R.id.viewpagerSlider);
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_ALWAYS);


        CompositePageTransformer compositePageTransformer=new CompositePageTransformer();
        compositePageTransformer.addTransformer (new MarginPageTransformer( 40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                if (position == 0) {
                    Integer currentPosition = (Integer) page.getTag(R.id.viewpagerSlider);
                    if (currentPosition != null) {
                        String title = sliderItems.get(currentPosition).getTitle();
                        txt_slider_title.setText(title);
                        String genre_1,genre_2,genre_3;
                        try {
                            genre_1 = sliderItems.get(currentPosition).getGenres().get(0);
                        } catch (IndexOutOfBoundsException e) {
                            genre_1 = "Action";
                        }
                        try {
                            genre_2 = sliderItems.get(currentPosition).getGenres().get(1);
                        } catch (IndexOutOfBoundsException e) {
                            genre_2 = "Comedy";
                        }
                        try {
                            genre_3 = sliderItems.get(currentPosition).getGenres().get(2);
                        } catch (IndexOutOfBoundsException e) {
                            genre_3 = "Crime";
                        }
                            txt_slide_genre1.setText(genre_1);
                            txt_slide_genre2.setText(genre_2);
                            txt_slide_genre3.setText(genre_3);
                    }
                }

                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }



        });

        viewPager2.setPageTransformer (compositePageTransformer);
        viewPager2.setCurrentItem(1);

        sliderItems = new ArrayList<>();
        fetchBanners();
    }

    public void initView() {
        reclerViewTopRated= findViewById(R.id.view_top_rated);
        reclerViewTopRated.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        reclerViewNowPlaying= findViewById(R.id.view_now_playing);
        reclerViewNowPlaying.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        reclerViewTrending= findViewById(R.id.view_trending);
        reclerViewTrending.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        ld_now_playing=findViewById(R.id.progressBar_now_playing);
        ld_top_rated = findViewById(R.id.progressBar_top_rated);
        ld_treding = findViewById(R.id.progressBar_trending);

        txt_search =findViewById(R.id.txt_search);

        txt_slider_title = findViewById(R.id.txt_slider_title);

        txt_slide_genre1 = findViewById(R.id.txt_slide_genre1);
        txt_slide_genre2 = findViewById(R.id.txt_slide_genre2);
        txt_slide_genre3 = findViewById(R.id.txt_slide_genre3);

    }

    private void fetGenres(){

        genres = new ArrayList<>();

        String url = "https://api.themoviedb.org/3/genre/movie/list?api_key=95f2419536f533cdaa1dadf83c606027";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray results = response.getJSONArray("genres");
                            for (int i = 0; i < results.length(); i++) {
                                JSONObject jsonObject = results.getJSONObject(i);
                                int id = jsonObject.getInt("id");
                                String name = jsonObject.getString("name");
                                genres.add(new Genre(id,name));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Add the request to the RequestQueue.
        Volley.newRequestQueue(this).add(jsonObjectRequest);

    }
    private void fetchBanners() {
        String url = "https://api.themoviedb.org/3/movie/upcoming?api_key=95f2419536f533cdaa1dadf83c606027";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray results = response.getJSONArray("results");
                            for (int i = 0; i < results.length(); i++) {
                                JSONObject jsonObject = results.getJSONObject(i);
                                int id = jsonObject.getInt("id");
                                String poster = jsonObject.getString("backdrop_path"); // Fix poster path key
                                String title = jsonObject.getString("title");

                                JSONArray genreIdsArray = jsonObject.getJSONArray("genre_ids");
                                List<String> list_name_genre = new ArrayList<>();
                                for (int j = 0; j <= genreIdsArray.length() - 1; j++) {
                                       int genre_id =  genreIdsArray.getInt(j);
                                           for (Genre genre : genres) {
                                               if (genre_id == genre.id) {
                                                   list_name_genre.add(genre.name);
                                               }
                                       }
                                }
                                 SliderItems item = new     SliderItems("https://image.tmdb.org/t/p/original"+ poster,title,id,list_name_genre);
                                sliderItems.add(item);
                            }
                            SliderAdapters adapter = new SliderAdapters(sliderItems,viewPager2);
                            viewPager2.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("debug", error.getMessage());
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }

    private void fetchNowPlaying() {
        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=95f2419536f533cdaa1dadf83c606027";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            listFilms = new ArrayList<>();

                            ld_now_playing.setVisibility(View.GONE);

                            JSONArray results = response.getJSONArray("results");
                            for (int i = 0; i < results.length(); i++) {
                                JSONObject jsonObject = results.getJSONObject(i);
                                int id = jsonObject.getInt("id");
                                String title = jsonObject.getString("title");
                                String release_date = jsonObject.getString("release_date").substring(0,4);
                                Double vote_average = jsonObject.getDouble("vote_average");
                                String poster = jsonObject.getString("poster_path"); // Fix poster path key
                                int vote_count = jsonObject.getInt("vote_count");

                                Film item = new Film(id,title,release_date,"https://image.tmdb.org/t/p/w500/"+ poster,vote_average,vote_count);
                                listFilms.add(item);
                            }

                            adapterNowPlaying = new FilmListAdapter(listFilms);

                            reclerViewNowPlaying.setAdapter(adapterNowPlaying);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ld_now_playing.setVisibility(View.GONE);
                Log.d("debug", error.getMessage());
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Add the request to the RequestQueue.
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }

    private void fetchTopRated() {
        String url = "https://api.themoviedb.org/3/movie/top_rated?api_key=95f2419536f533cdaa1dadf83c606027";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                          List<Film>  listFilms = new ArrayList<>();

                            ld_top_rated.setVisibility(View.GONE);

                            JSONArray results = response.getJSONArray("results");
                            for (int i = 0; i < results.length(); i++) {
                                JSONObject jsonObject = results.getJSONObject(i);
                                int id = jsonObject.getInt("id");
                                String title = jsonObject.getString("title");
                                String release_date = jsonObject.getString("release_date").substring(0,4);
                                Double vote_average = jsonObject.getDouble("vote_average");
                                String poster = jsonObject.getString("poster_path"); // Fix poster path key
                                int vote_count = jsonObject.getInt("vote_count");

                                DecimalFormat df = new DecimalFormat("#.##");

                                Film item = new Film(id,title,release_date,"https://image.tmdb.org/t/p/w500/"+ poster,vote_average,vote_count);
                                listFilms.add(item);
                            }

                            adapterTopRated = new FilmListAdapter(listFilms);

                            reclerViewTopRated.setAdapter(adapterTopRated);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ld_top_rated.setVisibility(View.GONE);

                Log.d("debug", error.getMessage());
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Add the request to the RequestQueue.
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }

    private void fetchTrending() {
        String url = "https://api.themoviedb.org/3/movie/popular?api_key=95f2419536f533cdaa1dadf83c606027";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            List<Film>  listFilms = new ArrayList<>();

                            ld_treding.setVisibility(View.GONE);

                            JSONArray results = response.getJSONArray("results");
                            for (int i = 0; i < results.length(); i++) {
                                JSONObject jsonObject = results.getJSONObject(i);
                                int id = jsonObject.getInt("id");
                                String title = jsonObject.getString("title");
                                String release_date = jsonObject.getString("release_date").substring(0,4);
                                Double vote_average = jsonObject.getDouble("vote_average");
                                String poster = jsonObject.getString("poster_path"); // Fix poster path key
                                int vote_count = jsonObject.getInt("vote_count");

                                Film item = new Film(id,title,release_date,"https://image.tmdb.org/t/p/w500/"+ poster,vote_average,vote_count);
                                listFilms.add(item);
                            }

                            adapterTrending = new FilmListAdapter(listFilms);

                            reclerViewTrending.setAdapter(adapterTrending);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ld_treding.setVisibility(View.GONE);

                Log.d("debug", error.getMessage());
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Add the request to the RequestQueue.
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }
}