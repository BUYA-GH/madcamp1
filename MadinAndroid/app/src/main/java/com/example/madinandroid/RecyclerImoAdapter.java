package com.example.madinandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerImoAdapter extends RecyclerView.Adapter<RecyclerImoAdapter.ImoViewHolder> {
    String[] imoName, description;
    int images[];
    Context context;

    private OnButtonListener mOnButtonListener;

    public RecyclerImoAdapter(Context ct, String[] imoName, String[] description, int img[], OnButtonListener onButtonListener) {
        context = ct;
        this.imoName = imoName;
        this.description = description;
        images = img;
        this.mOnButtonListener = onButtonListener;
    }

    @NonNull
    @Override
    public ImoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.imo_row, parent, false);
        return new ImoViewHolder(view, mOnButtonListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ImoViewHolder holder, int position) {
        holder.imoNameText.setText(imoName[position]);
        holder.descripText.setText(description[position]);
        holder.imoImage.setImageResource(images[position]);
    }

    @Override
    public int getItemCount() {
        return imoName.length;
    }

    public class ImoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView imoNameText, descripText;
        ImageView imoImage;
        OnButtonListener onButtonListener;

        public ImoViewHolder(@NonNull View itemView, OnButtonListener onButtonListener) {
            super(itemView);
            imoNameText = itemView.findViewById(R.id.imoNameView);
            descripText = itemView.findViewById(R.id.imoDescriptView);
            imoImage = itemView.findViewById(R.id.imoImageView);
            this.onButtonListener = onButtonListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onButtonListener.onButtonClick(getAdapterPosition());
        }
    }

    public interface OnButtonListener {
        void onButtonClick(int position);
    }
}
