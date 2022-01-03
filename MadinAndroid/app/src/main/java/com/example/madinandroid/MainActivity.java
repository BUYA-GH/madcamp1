package com.example.madinandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;

import android.os.Bundle;

import android.view.View;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private PagerAdapter pagerAdapter;
    private TabLayout tabLayout;

    static JSONArray books;
    static JSONArray belongs;
    static int nowId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // read json file
        String json = PreferenceManager.getString(this, "books");
        try{
            if(json.equals("")) {
                InputStream is = getAssets().open("Books.json");
                int fileSize = is.available();

                byte[] buffer = new byte[fileSize];
                is.read(buffer);
                is.close();
                json = new String(buffer, "UTF-8");
            }
            books = new JSONArray(json);
            JSONObject tmp = (JSONObject)books.get(books.length() - 1);
            String ids = (String)tmp.get("id");
            nowId = Integer.parseInt(ids);
        } catch(IOException | JSONException i) {
            i.printStackTrace();
        }

        json = PreferenceManager.getString(this, "belongs");
        try{
            if(json.equals("")) {
                InputStream is = getAssets().open("Belongs.json");
                int fileSize = is.available();

                byte[] buffer = new byte[fileSize];
                is.read(buffer);
                is.close();
                json = new String(buffer, "UTF-8");
            }
            belongs = new JSONArray(json);
        } catch(IOException | JSONException i) {
            i.printStackTrace();
        }

        // tabLayout setting
        tabLayout = (TabLayout)findViewById(R.id.tabs);
        viewPager = (ViewPager2)findViewById(R.id.viewPager);
        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager.setAdapter(pagerAdapter);

        // set tabIcon and add new tab
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.icon_first));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.icon_second));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.icon_third));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

        // off OverScroll mode
        viewPager.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);

    }
}