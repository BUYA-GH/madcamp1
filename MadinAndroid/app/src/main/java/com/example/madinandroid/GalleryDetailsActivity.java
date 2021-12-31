package com.example.madinandroid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.madinandroid.MainActivity.books;

public class GalleryDetailsActivity extends AppCompatActivity {
    private int searchImg;
    //private ArrayList<String> imgs = new ArrayList<>();
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> hexs = new ArrayList<>();

    private RecyclerView recyclerDetailGallery;
    private RecyclerGalleryDetailAdapter recyclerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_detail);

        Intent intent = getIntent();
        String filename = intent.getExtras().getString("img");
        searchImg = getResources().getIdentifier(filename, "drawable", getPackageName());

        try {
            JSONObject tmp = null;
            for(int i = 0; i < books.length(); ++i) {
                tmp = (JSONObject)books.get(i);
                String imgSrc = (String)tmp.get("image");
                if(searchImg == getResources().getIdentifier(imgSrc, "drawable", getPackageName())) {
                    Log.d("sameCheck", "Hi!");
                    names.add((String)tmp.get("name"));
                    hexs.add((String)tmp.get("color"));
                }
            }
        } catch (JSONException j) {
            j.printStackTrace();
        }

        recyclerDetailGallery = (RecyclerView)findViewById(R.id.galleryDetailRecyclerView);
        recyclerAdapter = new RecyclerGalleryDetailAdapter(this, searchImg, names, hexs);
        recyclerDetailGallery.setAdapter(recyclerAdapter);
        recyclerDetailGallery.setLayoutManager(new GridLayoutManager(this, 2));
    }
}
