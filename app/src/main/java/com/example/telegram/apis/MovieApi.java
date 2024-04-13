package com.example.telegram.apis;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.telegram.adapters.MovieAdapter;
import com.example.telegram.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class MovieApi {
    private RequestQueue requestQueue;
    private static MovieApi mInstance;

    private List<Movie> movieList;


    private MovieApi(Context context){
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static synchronized MovieApi getmInstance(Context context){

        if (mInstance == null){
            mInstance = new MovieApi(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue(){return requestQueue;}

    private void fetchMovies(RecyclerView recyclerView, Context ctx) {
        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=95f2419536f533cdaa1dadf83c606027";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray results = response.getJSONArray("results");
                            for (int i = 0; i < results.length(); i++) {
                                JSONObject jsonObject = results.getJSONObject(i);
                                int id = jsonObject.getInt("id");
                                String title = jsonObject.getString("title");
                                String overview = jsonObject.getString("overview");
                                String poster = jsonObject.getString("poster_path"); // Fix poster path key
                                double rating = jsonObject.getDouble("vote_average"); // Fix rating key
                                String release_date = jsonObject.getString("release_date").substring(0,4);

                                Movie movie = new Movie(id, title,"https://image.tmdb.org/t/p/w500/"+ poster, overview, rating,release_date);
                                movieList.add(movie);
                            }

                            MovieAdapter adapter = new MovieAdapter(ctx, movieList);
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("debug", error.getMessage());
                Toast.makeText(ctx, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Add the request to the RequestQueue.
        Volley.newRequestQueue(ctx).add(jsonObjectRequest);
    }
}
