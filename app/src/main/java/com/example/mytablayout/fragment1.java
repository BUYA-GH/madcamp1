package com.example.mytablayout;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

// to use static variable defined on MainActivity
import static com.example.mytablayout.MainActivity.jsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class fragment1 extends Fragment {

    RecyclerView itemView;
    itemAdapter itemAdapter;
    private JSONArray itemArray;

    private String[] s1;
    private String[] s2;
    private String[] s3;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // set context as parent activity
        // to call activity define view object and use findViewById
        View v = inflater.inflate(R.layout.fragment_fragment1, container, false);
        itemView = (RecyclerView)v.findViewById(R.id.itemView);

        return v;
    }

    @Override
    public void onResume(){
        super.onResume();

        // initialize string data
        s1 = new String[jsonArray.length()];
        s2 = new String[jsonArray.length()];
        s3 = new String[jsonArray.length()];

        try{
            JSONObject obj = null;
            for(int i=0 ; i<jsonArray.length() ; i++){
                obj = jsonArray.getJSONObject(i);
                s1[i] = (String)obj.get("name");
                s2[i] = (String)obj.get("phone");
                s3[i] = (String)obj.get("insta");
            }

            // print parsing data on logcat
            Log.d("jsonObject",s1[0]);
        }catch(JSONException e){
            e.printStackTrace();
        }

        // to get parent activity as context use getActivity()
        itemAdapter itemAdapter = new itemAdapter(getActivity(), s1, s2, s3);
        itemView.setAdapter(itemAdapter);
        itemView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }
}