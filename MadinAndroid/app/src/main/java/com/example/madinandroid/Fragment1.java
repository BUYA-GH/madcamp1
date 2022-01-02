package com.example.madinandroid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

import static com.example.madinandroid.MainActivity.books;

public class Fragment1 extends Fragment implements RecyclerGalleryAdapter.OnImageListener {
    private RecyclerView recyclerview;
    RecyclerGalleryAdapter recyclerAdapter;

    private ArrayList<Integer> imgSrc;
    private ArrayList<Integer> count;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("LifeCycleCheck", "I am in onCreateView in Fragment1");
        View view = inflater.inflate(R.layout.fragment1, container, false);
        jsonDataReset();

        recyclerview = (RecyclerView)view.findViewById(R.id.galleryRecyclerView);
        recyclerAdapter = new RecyclerGalleryAdapter(getActivity(), imgSrc, count, this);
        recyclerview.setAdapter(recyclerAdapter);
        recyclerview.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), 2));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("LifeCycleCheck", "I am in onResume in Fragment1");
        jsonDataReset();

        recyclerAdapter.setItems(imgSrc, count);
        recyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onImageClick(int position) {
        int img = imgSrc.get(position);
        Intent intent = new Intent(getActivity(), GalleryDetailsActivity.class);
        intent.putExtra("img", getResources().getResourceEntryName(img));
        startActivity(intent);
    }

    public void jsonDataReset() {
        imgSrc = new ArrayList<>();
        count = new ArrayList<>();

        try {
            JSONObject tmp = null;
            for(int i = 0; i < books.length(); ++i) {
                tmp = (JSONObject)books.get(i);
                String img = (String)tmp.get("image");

                int imgID = getActivity().getResources().getIdentifier(img, "drawable", getActivity().getPackageName());
                int index = imgSrc.indexOf(imgID);
                if(index == -1) {
                    count.add(1);
                    imgSrc.add(imgID);
                } else {
                    count.set(index, count.get(index)+1);
                }
            }
        } catch (JSONException j) {
            j.printStackTrace();
        }
    }
}