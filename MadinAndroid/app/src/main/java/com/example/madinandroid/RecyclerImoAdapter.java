package com.example.madinandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerImoAdapter extends RecyclerView.Adapter<RecyclerImoAdapter.ImoViewHolder> {
    int images[];
    Context context;

    private OnImageListener mOnImageListener;

    public RecyclerImoAdapter(Context ct, int img[], OnImageListener onImageListener) {
        context = ct;
        images = img;
        this.mOnImageListener = onImageListener;
    }

    @NonNull
    @Override
    public ImoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.imo_row, parent, false);
        return new ImoViewHolder(view, mOnImageListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ImoViewHolder holder, int position) {
        holder.imoImage.setImageResource(images[position]);
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public class ImoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imoImage;
        OnImageListener onImageListener;

        public ImoViewHolder(@NonNull View itemView, OnImageListener onImageListener) {
            super(itemView);
            imoImage = itemView.findViewById(R.id.imoImageView);
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
