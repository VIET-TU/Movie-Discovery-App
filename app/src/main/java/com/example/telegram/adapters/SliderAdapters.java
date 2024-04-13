package com.example.telegram.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.telegram.R;
import com.example.telegram.models.SliderItems;

import java.util.List;

public class SliderAdapters extends RecyclerView.Adapter<SliderAdapters.SliderViewHolder> {
   private List<SliderItems> sliderItems;
   private ViewPager2 viewPager2;
   private Context context;
    public SliderAdapters(List<SliderItems> sliderItems, ViewPager2 viewPager2) {
        this.sliderItems = sliderItems;
        this.viewPager2 = viewPager2;
    }

        @NonNull
        @Override
        public SliderAdapters.SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            context = parent.getContext();
            SliderAdapters.SliderViewHolder viewHolder = new SliderViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.slide_item_container,parent,false
            ));
            int position = viewHolder.getAdapterPosition();
            viewHolder.itemView.setTag(R.id.viewpagerSlider, position);
            return viewHolder;
        }

    @Override
    public void onBindViewHolder(@NonNull SliderAdapters.SliderViewHolder holder, int position) {
        SliderItems sliderItem = sliderItems.get(position);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transform(new CenterCrop(), new RoundedCorners(30));
        Glide.with(context).load(sliderItem.getPoster()).apply(requestOptions).into(holder.imageView);

        holder.itemView.setTag(R.id.viewpagerSlider, position);
    }



    @Override
    public int getItemCount() {
        return sliderItems.size();
    }
    public class SliderViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageSlide);
        }
    }


}
