package com.example.madinandroid;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.madinandroid.MainActivity.books;

public class Fragment0 extends Fragment {

    RecyclerView recyclerView;
    RecyclerContactAdapter recyclerAdapter;
    private JSONArray array;

    private String[] ns;
    private String[] ps;
    private String[] es;
    private int[] imgSrc;
    private String[] backgroundColor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("LifeCycleCheck", "I am in onCreateView in Fragment0");
        View view =  inflater.inflate(R.layout.fragment0,container,false);
        recyclerView = (RecyclerView)view.findViewById(R.id.contactRecyclerView);

        return view;
    }

    @Override
    public void onResume() {
        Log.d("LifeCycleCheck", "I am in onResume in Fragment0");
        super.onResume();

        ns = new String[books.length()];
        ps = new String[books.length()];
        es = new String[books.length()];
        imgSrc = new int[books.length()];
        backgroundColor = new String[books.length()];

        try{
            JSONObject tmp = null;
            for(int i = 0; i < books.length(); ++i) {
                tmp = (JSONObject)books.get(i);
                ns[i] = (String)tmp.get("name");
                ps[i] = (String)tmp.get("phone");
                es[i] = (String)tmp.get("email");
                String imgname = (String)tmp.get("image");
                imgSrc[i] = getActivity().getResources().getIdentifier(imgname, "drawable", getActivity().getPackageName());
                backgroundColor[i] = (String)tmp.get("color");
            }
        } catch(JSONException j) {
            j.printStackTrace();
        }

        recyclerAdapter = new RecyclerContactAdapter(getActivity(), ns, ps, es, imgSrc, backgroundColor);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}