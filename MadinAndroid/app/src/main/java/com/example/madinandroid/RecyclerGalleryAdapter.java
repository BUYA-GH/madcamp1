package com.example.madinandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerGalleryAdapter extends RecyclerView.Adapter<RecyclerGalleryAdapter.GalleryViewHolder> {
    String[] names;
    int[] imgSources;
    Context context;

    public RecyclerGalleryAdapter(Context ct, int[] imgSrc, String[] nm) {
        names = nm;
        context = ct;
        imgSources = imgSrc;
    }

    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.gallery_grid, parent, false);
        return new RecyclerGalleryAdapter.GalleryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerGalleryAdapter.GalleryViewHolder holder, int position) {
        holder.nameText.setText(names[position]);
        holder.imgView.setImageResource(imgSources[position]);
    }

    @Override
    public int getItemCount() {
        return names.length;
    }

    public class GalleryViewHolder extends RecyclerView.ViewHolder {

        TextView nameText;
        ImageView imgView;

        public GalleryViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.galleryNameView);
            imgView = itemView.findViewById(R.id.galleryImageView);
        }
    }
}
