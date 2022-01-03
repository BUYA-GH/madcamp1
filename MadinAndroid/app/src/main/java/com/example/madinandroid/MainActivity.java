package com.example.madinandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import android.os.Bundle;
import android.view.View;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // read json file
        try{
            InputStream is = getAssets().open("Books.json");
            int fileSize = is.available();

            byte[] buffer = new byte[fileSize];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");

            JSONObject jsonOb = new JSONObject(json);
            books = jsonOb.getJSONArray("Books");

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