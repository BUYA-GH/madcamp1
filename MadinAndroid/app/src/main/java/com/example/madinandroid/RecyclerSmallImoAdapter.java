package com.example.madinandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerSmallImoAdapter extends RecyclerView.Adapter<RecyclerSmallImoAdapter.ImoSmallViewHolder> {
    int images[];
    Context context;

    private OnImageListener mOnImageListener;

    public RecyclerSmallImoAdapter(Context ct, int img[], OnImageListener onImageListener) {
        context = ct;
        images = img;
        this.mOnImageListener = onImageListener;
    }

    @NonNull
    @Override
    public ImoSmallViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.imo_row_small, parent, false);
        return new ImoSmallViewHolder(view, mOnImageListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ImoSmallViewHolder holder, int position) {
        holder.imoImage.setImageResource(images[position]);
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public class ImoSmallViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imoImage;
        OnImageListener onImageListener;

        public ImoSmallViewHolder(@NonNull View itemView, OnImageListener onImageListener) {
            super(itemView);
            imoImage = itemView.findViewById(R.id.imoImageSmallView);
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
