package com.example.madinandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerGalleryAdapter extends RecyclerView.Adapter<RecyclerGalleryAdapter.GalleryViewHolder> {
    private ArrayList<Integer> imgSources;
    private Context context;

    private OnImageListener mOnImageListener;

    public RecyclerGalleryAdapter(Context ct, ArrayList<Integer> imgSrc, OnImageListener onImageListener) {
        context = ct;
        imgSources = new ArrayList<>(imgSrc);
        mOnImageListener = onImageListener;
    }

    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.gallery_grid, parent, false);
        return new GalleryViewHolder(view, mOnImageListener);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryViewHolder holder, int position) {
        holder.imgView.setImageResource(imgSources.get(position));
    }

    @Override
    public int getItemCount() { return imgSources.size(); }

    public class GalleryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgView;
        OnImageListener onImageListener;

        public GalleryViewHolder(@NonNull View itemView, OnImageListener onImageListener) {
            super(itemView);
            imgView = itemView.findViewById(R.id.galleryImageView);
            this.onImageListener = onImageListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onImageListener.onImageClick(getAdapterPosition());
        }
    }

    public interface OnImageListener {
        void onImageClick(int position);
    }
}
