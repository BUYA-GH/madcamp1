package com.example.madinandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerGalleryAdapter extends RecyclerView.Adapter<RecyclerGalleryAdapter.GalleryViewHolder> {
    private ArrayList<Integer> imgSources;
    private ArrayList<Integer> count;
    private Context context;

    private OnImageListener mOnImageListener;

    public RecyclerGalleryAdapter(Context ct, ArrayList<Integer> imgSrc, ArrayList<Integer> cnt, OnImageListener onImageListener) {
        context = ct;
        imgSources = new ArrayList<>(imgSrc);
        count = new ArrayList<>(cnt);
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
        holder.textView.setText(Integer.toString(count.get(position)));
    }

    @Override
    public int getItemCount() { return imgSources.size(); }

    public class GalleryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgView;
        TextView textView;
        OnImageListener onImageListener;

        public GalleryViewHolder(@NonNull View itemView, OnImageListener onImageListener) {
            super(itemView);
            imgView = itemView.findViewById(R.id.galleryImageView);
            textView = itemView.findViewById(R.id.galleryCountText);
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
