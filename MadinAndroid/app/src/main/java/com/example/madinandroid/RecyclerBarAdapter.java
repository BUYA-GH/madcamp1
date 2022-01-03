package com.example.madinandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerBarAdapter extends RecyclerView.Adapter<RecyclerBarAdapter.BarViewHolder> {
    String[] colors;
    int[] colorValue;
    Context context;

    private OnSeekBarChangeListener mOnSeekBarChangeListener;

    public RecyclerBarAdapter(Context ct, String[] colors, int[] values, OnSeekBarChangeListener onSeekBarChangeListener ) {
        context = ct;
        this.colors = colors;
        colorValue = values;
        this.mOnSeekBarChangeListener = onSeekBarChangeListener;
    }

    @NonNull
    @Override
    public BarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.bar_row, parent, false);
        return new BarViewHolder(view, mOnSeekBarChangeListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerBarAdapter.BarViewHolder holder, int position) {
        holder.colorNameTextView.setText(colors[position]);
        holder.colorValueTextView.setText(Integer.toString(colorValue[position]));
        holder.colorSeekBar.setProgress(colorValue[position]);
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class BarViewHolder extends RecyclerView.ViewHolder implements SeekBar.OnSeekBarChangeListener {

        TextView colorNameTextView, colorValueTextView;
        SeekBar colorSeekBar;
        OnSeekBarChangeListener onSeekBarChangeListener;

        public BarViewHolder(@NonNull View itemView, OnSeekBarChangeListener onSeekBarChangeListener) {
            super(itemView);
            colorNameTextView = itemView.findViewById(R.id.barLabel);
            colorValueTextView = itemView.findViewById(R.id.barProgress);
            colorSeekBar = itemView.findViewById(R.id.seekbar);
            this.onSeekBarChangeListener = onSeekBarChangeListener;

            colorSeekBar.setOnSeekBarChangeListener(this);
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            onSeekBarChangeListener.onSeekBarProgressChanged(getAdapterPosition(), seekBar, i, b);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            onSeekBarChangeListener.onSeekBarStartTrackingTouch(getAdapterPosition(), seekBar);
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            onSeekBarChangeListener.onSeekBarStopTrackingTouch(getAdapterPosition(), seekBar);
        }
    }

    public interface OnSeekBarChangeListener {
        void onSeekBarProgressChanged(int position, SeekBar seekBar, int progress, boolean fromUser);

        void onSeekBarStartTrackingTouch(int position, SeekBar seekBar);

        void onSeekBarStopTrackingTouch(int position, SeekBar seekBar);
    }

}