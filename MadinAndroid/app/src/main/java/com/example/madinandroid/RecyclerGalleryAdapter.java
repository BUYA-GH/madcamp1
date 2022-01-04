package com.example.madinandroid;

import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerGalleryAdapter extends RecyclerView.Adapter<RecyclerGalleryAdapter.GalleryViewHolder> {
    private ArrayList<Integer> imgSources;
    private ArrayList<Integer> count;
    private ArrayList<String> imgName;
    private Context context;

    private OnImageListener mOnImageListener;

    public RecyclerGalleryAdapter(Context ct, ArrayList<Integer> imgSrc, ArrayList<Integer> cnt, ArrayList<String> imgName, OnImageListener onImageListener) {
        context = ct;
        imgSources = new ArrayList<>(imgSrc);
        count = new ArrayList<>(cnt);
        this.imgName = new ArrayList<>(imgName);
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
        holder.textCountView.setText(Integer.toString(count.get(position)));
        holder.textNameView.setDisplayedChild(0);
        holder.textName.setText(imgName.get(position));
    }

    @Override
    public int getItemCount() { return imgSources.size(); }

    public void setItems(ArrayList<Integer> imgSrc, ArrayList<Integer> cnt, ArrayList<String> imgName) {
        imgSources = new ArrayList<>(imgSrc);
        count = new ArrayList<>(cnt);
        this.imgName = new ArrayList<>(imgName);
    }

    public void changeTexttoEdit(@NonNull GalleryViewHolder holder, int pos) {
        holder.textNameView.setDisplayedChild(1);
        holder.editName.setText(imgName.get(pos));
    }

    public void changeEdittoText(@NonNull GalleryViewHolder holder, int pos) {
        holder.textNameView.setDisplayedChild(0);
        holder.textName.setText(imgName.get(pos));
    }

    public class GalleryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener, View.OnKeyListener {
        ImageView imgView;
        TextView textCountView, textName;
        EditText editName;
        ViewSwitcher textNameView;
        OnImageListener onImageListener;

        public GalleryViewHolder(@NonNull View itemView, OnImageListener onImageListener) {
            super(itemView);
            imgView = itemView.findViewById(R.id.galleryImageView);
            textCountView = itemView.findViewById(R.id.galleryCountText);
            textNameView = itemView.findViewById(R.id.galleryNameSwitcher);
            textName = itemView.findViewById(R.id.galleryNameText);
            editName = itemView.findViewById(R.id.galleryNameEdit);

            this.onImageListener = onImageListener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            editName.setOnKeyListener(this);
        }

        @Override
        public void onClick(View view) {
            onImageListener.onImageClick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            onImageListener.onLongImageClick(getAdapterPosition());

            return true;
        }

        @Override
        public boolean onKey(View view, int i, KeyEvent event) {
            boolean result = onImageListener.onEnterInput(view, i, event, getAdapterPosition());

            return result;
        }
    }



    public interface OnImageListener {
        void onImageClick(int position);

        void onLongImageClick(int position);

        boolean onEnterInput(View view, int i, KeyEvent event, int position);
    }
}
