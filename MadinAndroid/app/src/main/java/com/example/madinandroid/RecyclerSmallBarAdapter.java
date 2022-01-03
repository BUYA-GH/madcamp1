package com.example.madinandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerSmallBarAdapter extends RecyclerView.Adapter<RecyclerSmallBarAdapter.SmallBarViewHolder> {
    String[] colors;
    int[] colorValue;
    Context context;

    private OnSmallSeekBarChangeListener mOnSeekBarChangeListener;

    public RecyclerSmallBarAdapter(Context ct, String[] colors, int[] values, OnSmallSeekBarChangeListener onSeekBarChangeListener ) {
        context = ct;
        this.colors = colors;
        colorValue = values;
        mOnSeekBarChangeListener = onSeekBarChangeListener;
    }

    @NonNull
    @Override
    public SmallBarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.bar_row, parent, false);
        return new SmallBarViewHolder(view, mOnSeekBarChangeListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerSmallBarAdapter.SmallBarViewHolder holder, int position) {
        holder.colorNameTextView.setText(colors[position]);
        holder.colorValueTextView.setText(Integer.toString(colorValue[position]));
        holder.colorSeekBar.setProgress(colorValue[position]);
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class SmallBarViewHolder extends RecyclerView.ViewHolder implements SeekBar.OnSeekBarChangeListener {

        TextView colorNameTextView, colorValueTextView;
        SeekBar colorSeekBar;
        OnSmallSeekBarChangeListener onSeekBarChangeListener;

        public SmallBarViewHolder(@NonNull View itemView, OnSmallSeekBarChangeListener onSeekBarChangeListener) {
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

    public interface OnSmallSeekBarChangeListener {
        void onSeekBarProgressChanged(int position, SeekBar seekBar, int progress, boolean fromUser);

        void onSeekBarStartTrackingTouch(int position, SeekBar seekBar);

        void onSeekBarStopTrackingTouch(int position, SeekBar seekBar);
    }
}
