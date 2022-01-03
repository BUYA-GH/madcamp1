package com.example.madinandroid;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.madinandroid.MainActivity.books;

public class Fragment0 extends Fragment {

    RecyclerView recyclerView;
    EditText contactSearch;
    RecyclerContactAdapter recyclerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("LifeCycleCheck", "I am in onCreateView in Fragment0");

        View view =  inflater.inflate(R.layout.fragment0,container,false);
        recyclerView = (RecyclerView)view.findViewById(R.id.contactRecyclerView);
        contactSearch = (EditText) view.findViewById(R.id.contactSearch);
        return view;
    }

    @Override
    public void onResume() {
        Log.d("LifeCycleCheck", "I am in onResume in Fragment0");
        super.onResume();

        recyclerAdapter = new RecyclerContactAdapter(getActivity(), books);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // attach swiper helper to recycler view
        ContactSwiper contactswiper = new ContactSwiper();
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(contactswiper);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        // editText
        contactSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                recyclerAdapter.getFilter().filter(charSequence);
                Log.d("ontext",charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });




    }
}