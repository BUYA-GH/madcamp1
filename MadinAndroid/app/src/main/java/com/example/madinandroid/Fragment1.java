package com.example.madinandroid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static com.example.madinandroid.MainActivity.belongs;
import static com.example.madinandroid.MainActivity.books;

public class Fragment1 extends Fragment implements RecyclerGalleryAdapter.OnImageListener {
    private RecyclerView recyclerview;
    private RecyclerGalleryAdapter recyclerAdapter;

    private ArrayList<Integer> imgSrc;
    private ArrayList<String> imgName;
    private ArrayList<Integer> count;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("LifeCycleCheck", "I am in onCreateView in Fragment1");
        View view = inflater.inflate(R.layout.fragment1, container, false);
        jsonDataReset();

        recyclerview = (RecyclerView)view.findViewById(R.id.galleryRecyclerView);
        recyclerAdapter = new RecyclerGalleryAdapter(getActivity(), imgSrc, count, imgName, this);
        recyclerview.setAdapter(recyclerAdapter);
        recyclerview.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), 2));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("LifeCycleCheck", "I am in onResume in Fragment1");
        jsonDataReset();

        recyclerAdapter.setItems(imgSrc, count, imgName);
        recyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onImageClick(int position) {
        int img = imgSrc.get(position);
        Intent intent = new Intent(getActivity(), GalleryDetailsActivity.class);
        intent.putExtra("img", getResources().getResourceEntryName(img));
        intent.putExtra("imgname", imgName.get(position));
        startActivity(intent);
    }

    @Override
    public void onLongImageClick(int position) {
        RecyclerView.ViewHolder viewHolder = recyclerview.findViewHolderForAdapterPosition(position);
        recyclerAdapter.changeTexttoEdit((RecyclerGalleryAdapter.GalleryViewHolder)viewHolder, position);

    }

    @Override
    public boolean onEnterInput(View view, int keyCode, KeyEvent event, int position) {
        if((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

            RecyclerGalleryAdapter.GalleryViewHolder viewHolder = (RecyclerGalleryAdapter.GalleryViewHolder)recyclerview.findViewHolderForAdapterPosition(position);
            String input = viewHolder.editName.getText().toString();
            imgName.set(position, input);
            String fileName = getResources().getResourceEntryName(imgSrc.get(position));
            try {
                for(int i = 0; i < belongs.length(); ++i) {
                    JSONObject jsonOb = (JSONObject)belongs.get(i);
                    if(fileName.equals((String)jsonOb.get("image"))) {
                        jsonOb.put("name", input);
                        belongs.put(i, jsonOb);

                        break;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String json = belongs.toString();
            PreferenceManager.setString(getActivity(), "belongs", json);

            recyclerAdapter.setItems(imgSrc, count, imgName);
            recyclerAdapter.notifyDataSetChanged();
            recyclerAdapter.changeEdittoText((RecyclerGalleryAdapter.GalleryViewHolder)viewHolder, position);

            return true;
        }
        return false;
    }

    public void jsonDataReset() {
        imgSrc = new ArrayList<>();
        imgName = new ArrayList<>();
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

        try {
            JSONObject tmp = null;
            for(int i = 0; i < imgSrc.size(); ++i) {
                int imgSource = (Integer)imgSrc.get(i);
                for(int j = 0; j < belongs.length(); ++j) {
                    tmp = (JSONObject)belongs.get(j);
                    String img = (String)tmp.get("image");

                    int imgID = getActivity().getResources().getIdentifier(img, "drawable", getActivity().getPackageName());
                    if(imgID == imgSource) {
                        img = (String)tmp.get("name");
                        imgName.add(img);

                        break;
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}