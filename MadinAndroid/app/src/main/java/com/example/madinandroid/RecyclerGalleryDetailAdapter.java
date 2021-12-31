package com.example.madinandroid;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerGalleryDetailAdapter extends RecyclerView.Adapter<RecyclerGalleryDetailAdapter.GalleryDetailViewHolder>{
    private int searchImg;
    private ArrayList<String> names, hexs;
    private Context context;

    public RecyclerGalleryDetailAdapter(Context ct, int resourceId, ArrayList<String> nm, ArrayList<String> hx ) {
        this.context = ct;
        this.searchImg = resourceId;
        this.names = new ArrayList<>(nm);
        this.hexs = new ArrayList<>(hx);
    }

    @NonNull
    @Override
    public GalleryDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.gallery_detail_grid, parent, false);
        return new GalleryDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryDetailViewHolder holder, int position) {
        holder.imgView.setImageResource(searchImg);
        holder.imgView.setBackgroundColor(Color.parseColor(hexs.get(position)));
        holder.textView.setText(names.get(position));
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public class GalleryDetailViewHolder extends RecyclerView.ViewHolder {
        ImageView imgView;
        TextView textView;

        public GalleryDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            imgView = itemView.findViewById(R.id.galleryDetailImageView);
            textView = itemView.findViewById(R.id.galleryDetailNameView);
        }
    }
}
