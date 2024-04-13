package com.example.telegram.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.telegram.R;
import com.example.telegram.models.Actor;
import com.example.telegram.models.Film;

import java.util.List;

public class ActorListAdapter  extends RecyclerView.Adapter<ActorListAdapter.ViewHolder>{

    List<Actor> listActors;
    Context context;

    public ActorListAdapter(List<Actor> listActors) {
        this.listActors = listActors;
    }

    @NonNull
    @Override
    public ActorListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_actor,parent,false);

        return new ActorListAdapter.ViewHolder(inflate)  ;
    }

    @Override
    public void onBindViewHolder(@NonNull ActorListAdapter.ViewHolder holder, int position) {
        Actor actor = listActors.get(position);
        Glide.with(context).load(actor.getImage()).into(holder.image_actor);
        holder.name_actor.setText(actor.getName());


    }

    @Override
    public int getItemCount() {
        return listActors.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image_actor;
        TextView name_actor;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image_actor = itemView.findViewById(R.id.image_actor);
            name_actor = itemView.findViewById(R.id.name_actor);
        }
    }
}
