package com.example.madinandroid;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FragmentEmo extends Fragment implements RecyclerSmallImoAdapter.OnImageListener {
    private int images[] = {R.drawable.img_cat, R.drawable.img_clown, R.drawable.img_devil,
            R.drawable.img_dog, R.drawable.img_heart, R.drawable.img_money, R.drawable.img_party, R.drawable.img_poop};
    private String color;
    private int image;

    private ImageView imgEmoView;
    private RecyclerView imgEmoRecyclerView;
    private RecyclerSmallImoAdapter recyclerSmallImoAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card_emo, container, false);
        setViewByID(view);
        setSettingValue();

        return view;
    }

    public void setByBundle(Bundle bundle) {
        color = bundle.getString("color");
        image = bundle.getInt("image");
    }

    public void setViewByID(View view) {
        imgEmoView = view.findViewById(R.id.cardEmoImageView);
        imgEmoRecyclerView = view.findViewById(R.id.cardEmoRecyclerView);
        recyclerSmallImoAdapter = new RecyclerSmallImoAdapter(getActivity(), images, this);
        imgEmoRecyclerView.setAdapter(recyclerSmallImoAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        imgEmoRecyclerView.setLayoutManager(linearLayoutManager);
    }

    public void setSettingValue() {
        imgEmoView.setImageResource(image);
        imgEmoView.setBackgroundColor(Color.parseColor(color));
    }

    @Override
    public void onImageClick(int position) {
        image = images[position];
        imgEmoView.setImageResource(image);
    }

    public Bundle getSetValue() {
        Bundle bundle = new Bundle(1);
        bundle.putInt("image", image);

        return bundle;
    }
}
