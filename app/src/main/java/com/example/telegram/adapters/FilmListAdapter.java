package com.example.telegram.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.telegram.R;
import com.example.telegram.activities.DetailActivity;
import com.example.telegram.activities.SearchActivity;
import com.example.telegram.models.Film;
import com.example.telegram.models.SliderItems;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

    public class FilmListAdapter extends RecyclerView.Adapter<FilmListAdapter.ViewHolder> {
       private List<Film> listFilms;
        private  Context context;
        public FilmListAdapter(List<Film> listFilms) {
            this.listFilms = listFilms;
        }

        @NonNull
        @Override
        public FilmListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            context = parent.getContext();
            View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list_films,parent,false);
            return new ViewHolder(inflate)  ;
        }

        @Override
        public void onBindViewHolder(@NonNull FilmListAdapter.ViewHolder holder, int position) {
            if(context instanceof SearchActivity) {
                holder.image_poster.setMaxWidth(100);
                holder.image_poster.setMaxHeight(130);
            }

            Film film = listFilms.get(position);
            holder.txt_title.setText(film.getTitle());
            holder.txt_release_date.setText(film.getRelease_date());
            DecimalFormat decimalFormat = new DecimalFormat("#.#");
            holder.txt_vote_average.setText(decimalFormat.format(film.getVote_average()));
            RequestOptions requestOptions = new RequestOptions();
            requestOptions = requestOptions.transform(new CenterCrop(), new RoundedCorners(30));
            Glide.with(context).load(film.getPoster()).apply(requestOptions).into(holder.image_poster);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(holder.itemView.getContext(), DetailActivity.class);
                    intent.putExtra("id",film.getId());
                    context.startActivity(intent);
                }
            });
        }
        @Override
        public int getItemCount() {
            return listFilms.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView txt_title, txt_vote_average, txt_release_date;
            ImageView image_poster;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                txt_title = itemView.findViewById(R.id.txt_listFilme_title);
                image_poster = itemView.findViewById(R.id.imageView_poster);
                txt_vote_average = itemView.findViewById(R.id.txt_movieStart);
                txt_release_date = itemView.findViewById(R.id.txt_release_date);
            }
        }



    }
