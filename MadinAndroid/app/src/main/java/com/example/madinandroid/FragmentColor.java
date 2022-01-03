package com.example.madinandroid;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FragmentColor extends Fragment implements RecyclerSmallBarAdapter.OnSmallSeekBarChangeListener {
    private String colors[] = {"R", "G", "B"};
    private int colorValues[] = {255, 255, 255};

    private String color;
    private int image;

    private ImageView imgColorView;
    private ImageButton exitBtn;
    private RecyclerView imgColorRecyclerView;
    private RecyclerSmallBarAdapter recyclerSmallBarAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card_color, container, false);
        setViewByID(view);
        setSettingValue();

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((GalleryDetailsActivity)getActivity()).destroyViewPager();
            }
        });

        return view;
    }

    public void setByBundle(Bundle bundle) {
        color = bundle.getString("color");
        image = bundle.getInt("image");
        changeStringtoHexArray();
    }

    public void setViewByID(View view) {
        imgColorView = view.findViewById(R.id.cardColorImageView);

        imgColorRecyclerView = view.findViewById(R.id.cardColorRecyclerView);
        recyclerSmallBarAdapter = new RecyclerSmallBarAdapter(getActivity(), colors, colorValues, this);
        imgColorRecyclerView.setAdapter(recyclerSmallBarAdapter);
        imgColorRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        exitBtn = view.findViewById(R.id.cardExitBtn);
    }

    public void setSettingValue() {
        imgColorView.setImageResource(image);
        imgColorView.setBackgroundColor(Color.parseColor(color));
    }

    @Override
    public void onSeekBarProgressChanged(int position, SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onSeekBarStartTrackingTouch(int position, SeekBar seekBar) {

    }

    @Override
    public void onSeekBarStopTrackingTouch(int position, SeekBar seekBar) {
        colorValues[position] = seekBar.getProgress();
        imgColorView.setBackgroundColor(Color.parseColor(parsingHex()));
    }

    public String parsingHex() {
        String a, b, c;

        a = Integer.toHexString(colorValues[0]);
        if(colorValues[0] <= 15)  a = "0" + a;

        b = Integer.toHexString(colorValues[1]);
        if(colorValues[1] <= 15)  b = "0" + b;

        c = Integer.toHexString(colorValues[2]);
        if(colorValues[2] <= 15)  c = "0" + c;

        return "#" + a + b + c;
    }

    public void changeStringtoHexArray() {
        String sub = color.substring(1,3);
        colorValues[0] = Integer.parseInt(sub, 16);

        sub = color.substring(3,5);
        colorValues[1] = Integer.parseInt(sub, 16);

        sub = color.substring(5,7);
        colorValues[2] = Integer.parseInt(sub, 16);
    }

    public Bundle getSetValue() {
        Bundle bundle = new Bundle(1);
        bundle.putString("color", parsingHex());

        return bundle;
    }
}
