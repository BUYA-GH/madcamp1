package com.example.madinandroid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.zip.Inflater;

import static com.example.madinandroid.MainActivity.books;

public class GalleryDetailsActivity extends AppCompatActivity implements RecyclerGalleryDetailAdapter.OnImageListener {
    private int searchImg;
    private ArrayList<String> names;
    private ArrayList<String> phones;
    private ArrayList<String> emails;
    private ArrayList<String> hexs;
    private ArrayList<String> ids;

    private RecyclerView recyclerDetailGallery;
    private RecyclerGalleryDetailAdapter recyclerAdapter;

    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private FragmentCard fragmentCard;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_detail);

        Intent intent = getIntent();
        String filename = intent.getExtras().getString("img");
        searchImg = getResources().getIdentifier(filename, "drawable", getPackageName());

        jsonDataReset();

        recyclerDetailGallery = (RecyclerView)findViewById(R.id.galleryDetailRecyclerView);
        recyclerAdapter = new RecyclerGalleryDetailAdapter(this, searchImg, names, hexs, this);
        recyclerDetailGallery.setAdapter(recyclerAdapter);
        recyclerDetailGallery.setLayoutManager(new GridLayoutManager(this, 2));
    }

    @Override
    public void onImageClick(int position) {
        fragmentCard = new FragmentCard();

        Bundle bundle = new Bundle(1);
        bundle.putString("id", ids.get(position));
        fragmentCard.setArguments(bundle);

        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.animator.slide_up, R.animator.slide_down);
        transaction.replace(R.id.cardFrameLayout, fragmentCard).commit();
    }

    @Override
    public void onBackPressed() {
        if(fragmentCard != null) {
            fragmentManager = getSupportFragmentManager();
            transaction = fragmentManager.beginTransaction();
            transaction.setCustomAnimations(R.animator.slide_up, R.animator.slide_down);
            transaction.remove(fragmentCard);
            transaction.commit();
            fragmentCard.onDestroy();
            fragmentCard.onDetach();
            fragmentCard = null;

            jsonDataReset();
            recyclerAdapter.setItems(searchImg, names, hexs);
            recyclerAdapter.notifyDataSetChanged();

        } else {
            super.onBackPressed();
        }
    }

    public void jsonDataReset() {
        names = new ArrayList<>();
        phones = new ArrayList<>();
        emails = new ArrayList<>();
        hexs = new ArrayList<>();
        ids = new ArrayList<>();

        try {
            JSONObject tmp = null;
            for(int i = 0; i < books.length(); ++i) {
                tmp = (JSONObject)books.get(i);
                String imgSrc = (String)tmp.get("image");
                if(searchImg == getResources().getIdentifier(imgSrc, "drawable", getPackageName())) {
                    Log.d("sameCheck", "Hi!");
                    names.add((String)tmp.get("name"));
                    phones.add((String)tmp.get("phone"));
                    emails.add((String)tmp.get("email"));
                    hexs.add((String)tmp.get("color"));
                    ids.add((String)tmp.get("id"));
                }
            }
        } catch (JSONException j) {
            j.printStackTrace();
        }
    }
}
