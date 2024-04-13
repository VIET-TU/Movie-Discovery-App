package com.example.telegram.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.telegram.R;
import com.example.telegram.adapters.FilmListAdapter;
import com.example.telegram.models.Film;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView reclerViewFilmSearch;
    private  RecyclerView.LayoutManager layoutManagerFilmSearch;
    private  RecyclerView.Adapter adapterListFilmSearch;

    private ProgressBar ld_film_search;

    private List<Film> listFilms;
    private EditText edt_search;

    boolean isLoading = false;

    private int page = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ld_film_search = findViewById(R.id.progressBar_film_search);
        reclerViewFilmSearch = findViewById(R.id.view_film_searh);
        layoutManagerFilmSearch = new GridLayoutManager(this,3);
        reclerViewFilmSearch.setLayoutManager(layoutManagerFilmSearch);

        handleSearch();

        fetchNowPlaying(page);

//        initScrollListener();

    }

    private void handleSearch() {
        edt_search = findViewById(R.id.edt_search);
        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().equals("")) {
                    fetchNowPlaying(1);
                } else if(s.toString() != null) {
                    searchMovie(1,s.toString());

                }}
            @Override
            public void afterTextChanged(Editable s) {}
        });
    }


    private void initScrollListener() {
        reclerViewFilmSearch.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (!isLoading) {
                    GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0
                            && !isLoading) {
                        // Load more items
                        loadMoreItems();
                    }
                }
            }
        });
    }

    private void loadMoreItems() {
        isLoading = true;
        fetchNowPlaying(++page);
    }






    private void fetchNowPlaying(int page) {
        if(page < 0) page = 1;
        String url = "https://api.themoviedb.org/3/movie/popular?api_key=95f2419536f533cdaa1dadf83c606027&page=" + page;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            ld_film_search.setVisibility(View.GONE);
                            listFilms = new ArrayList<>();
                            JSONArray results = response.getJSONArray("results");
                            for (int i = 0; i < results.length() ; i++) {
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

                            adapterListFilmSearch = new FilmListAdapter(listFilms);

                            reclerViewFilmSearch.setAdapter(adapterListFilmSearch);

                            reclerViewFilmSearch.setHasFixedSize(true);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ld_film_search.setVisibility(View.GONE);
                Toast.makeText(SearchActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }

    private  void searchMovie(int page, String query) {
        if(page < 0) page = 1;
        String url = "https://api.themoviedb.org/3/search/movie?api_key=95f2419536f533cdaa1dadf83c606027&query=" + query + "&page=" + page;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            ld_film_search.setVisibility(View.GONE);
                            listFilms = new ArrayList<>();
                            JSONArray results = response.getJSONArray("results");
                            for (int i = 0; i < results.length() ; i++) {
                                JSONObject jsonObject = results.getJSONObject(i);
                                int id = jsonObject.getInt("id");
                                String title = jsonObject.getString("title");
                                String release_date = jsonObject.getString("release_date");
                                Double vote_average = jsonObject.getDouble("vote_average");
                                String poster = jsonObject.getString("poster_path"); // Fix poster path key
                                int vote_count = jsonObject.getInt("vote_count");

                                Film item = new Film(id,title,release_date,"https://image.tmdb.org/t/p/w500/"+ poster,vote_average,vote_count);
                                listFilms.add(item);
                            }

                            adapterListFilmSearch = new FilmListAdapter(listFilms);

                            reclerViewFilmSearch.setAdapter(adapterListFilmSearch);

                            reclerViewFilmSearch.setHasFixedSize(true);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ld_film_search.setVisibility(View.GONE);
                Toast.makeText(SearchActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Add the request to the RequestQueue.
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }
}