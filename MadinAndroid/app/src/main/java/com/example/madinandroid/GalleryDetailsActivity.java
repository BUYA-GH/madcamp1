package com.example.madinandroid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

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
    private FragmentEditUser fragmentEdit;
    // private FragmentEditEmo fragmentEmo;
    // private FragmentEditColor fragmentColor;

    private ViewPager2 viewPager2;
    private CardPagerAdapter cardPagerAdapter;


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

        fragmentManager = getSupportFragmentManager();
    }

    @Override
    public void onImageClick(int position) {
        viewPager2 = findViewById(R.id.editViewPager2);
        viewPager2.setElevation(1);
        cardPagerAdapter = new CardPagerAdapter(getSupportFragmentManager(), getLifecycle());
        cardPagerAdapter.setInfoOfUser(names.get(position), phones.get(position), emails.get(position), searchImg,
                hexs.get(position), ids.get(position));

        viewPager2.setAdapter(cardPagerAdapter);
        viewPager2.setOffscreenPageLimit(3);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                if(state == ViewPager2.SCROLL_STATE_SETTLING) {
                    cardPagerAdapter.getAllSettingFromFragment();
                }
            }
        });
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
