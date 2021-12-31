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

    private ArrayList<Integer> imgSrc = new ArrayList<>();
    //private int[] imgSrc;
    //private String[] ns, backColor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("LifeCycleCheck", "I am in onCreateView in Fragment1");
        View view = inflater.inflate(R.layout.fragment1, container, false);
        recyclerview = (RecyclerView)view.findViewById(R.id.galleryRecyclerView);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("LifeCycleCheck", "I am in onResume in Fragment1");

        try {
            JSONObject tmp = null;
            for(int i = 0; i < books.length(); ++i) {
                tmp = (JSONObject)books.get(i);
                String img = (String)tmp.get("image");
                int imgID = getActivity().getResources().getIdentifier(img, "drawable", getActivity().getPackageName());

                if(imgSrc.indexOf(imgID) == -1) imgSrc.add(imgID);
            }
        } catch (JSONException j) {
            j.printStackTrace();
        }

        recyclerAdapter = new RecyclerGalleryAdapter(getActivity(), imgSrc, this);
        recyclerview.setAdapter(recyclerAdapter);
        recyclerview.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), 2));
    }

    @Override
    public void onImageClick(int position) {
        int img = imgSrc.get(position);
        Intent intent = new Intent(getActivity(), GalleryDetailsActivity.class);
        intent.putExtra("img", getResources().getResourceEntryName(img));
        startActivity(intent);
    }
}