package com.example.madinandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MakeImoActivity extends AppCompatActivity implements RecyclerImoAdapter.OnButtonListener {
    RecyclerView recyclerView;
    RecyclerImoAdapter recyclerAdapter;
    private Toolbar toolbar;

    private String[] imoName;
    private String[] description;
    int images[] = {R.drawable.mulempyo, R.drawable.neukkimpyo};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.make_imo);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imoName = getResources().getStringArray(R.array.imoName);
        description = getResources().getStringArray(R.array.description);

        recyclerView = (RecyclerView)findViewById(R.id.imoRecyclerView);
        recyclerAdapter = new RecyclerImoAdapter(this, imoName, description, images, this);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onButtonClick(int position){
        Intent intent = new Intent();
        intent.putExtra("img", images[position]);
        setResult(Activity.RESULT_OK, intent);

        finish();
    }
}
