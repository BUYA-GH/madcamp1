package com.example.madinandroid;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.madinandroid.MainActivity.books;


public class Fragment1 extends Fragment {
    private RecyclerView recyclerview;
    RecyclerGalleryAdapter recyclerAdapter;

    private int[] imgSrc;
    private String[] ns, backColor;

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

        ns = new String[books.length()];
        imgSrc = new int[books.length()];
        backColor = new String[books.length()];

        try {
            JSONObject tmp = null;
            for(int i = 0; i < books.length(); ++i) {
                tmp = (JSONObject)books.get(i);
                ns[i] = (String)tmp.get("name");
                tmp = (JSONObject)books.get(i);
                String imgName = (String)tmp.get("image");
                imgSrc[i] = getActivity().getResources().getIdentifier(imgName, "drawable", getActivity().getPackageName());
                backColor[i] = (String)tmp.get("color");
            }
        } catch(JSONException j) {
            j.printStackTrace();
        }

        recyclerAdapter = new RecyclerGalleryAdapter(getActivity().getApplicationContext(), imgSrc, ns, backColor);
        recyclerview.setAdapter(recyclerAdapter);
        recyclerview.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), 2));

    }
}