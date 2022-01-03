package com.example.madinandroid;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MakeImoActivity extends AppCompatActivity implements RecyclerImoAdapter.OnImageListener, RecyclerBarAdapter.OnSeekBarChangeListener {

    private ImageView imageview;
    private RecyclerView recyclerEmoView, recyclerBarView;
    private RecyclerImoAdapter recyclerEmoAdapter;
    private RecyclerBarAdapter recyclerBarAdapter;

    private Button selBtn;

    private int images[] = {R.drawable.img_cat, R.drawable.img_clown, R.drawable.img_devil,
            R.drawable.img_dog, R.drawable.img_heart, R.drawable.img_money, R.drawable.img_party, R.drawable.img_poop};
    private int myImage = R.drawable.img_cat;

    private String colors[] = {"R", "G", "B"};
    private int colorValues[] = {255, 255, 255};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.make_imo);

        Intent intent = getIntent();
        myImage = intent.getExtras().getInt("img");
        colorValues = intent.getExtras().getIntArray("color");

        imageview = (ImageView)findViewById(R.id.makeImoImageView);
        selBtn = (Button)findViewById(R.id.imoSelectBtn);

        imageview.setImageResource(myImage);
        imageview.setBackgroundColor(Color.parseColor(parsingHex()));

        recyclerEmoView = (RecyclerView)findViewById(R.id.imoRecyclerView);
        recyclerEmoAdapter = new RecyclerImoAdapter(this, images, this);
        recyclerEmoView.setAdapter(recyclerEmoAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager .setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerEmoView.setLayoutManager(linearLayoutManager);

        recyclerBarView = (RecyclerView)findViewById(R.id.colorBarRecyclerView);
        recyclerBarAdapter = new RecyclerBarAdapter(this, colors, colorValues, this);
        recyclerBarView.setAdapter(recyclerBarAdapter);
        recyclerBarView.setLayoutManager(new LinearLayoutManager(this));

        selBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("img", myImage);
                intent.putExtra("colors", colorValues);
                setResult(Activity.RESULT_OK, intent);

                finish();
            }
        });
    }

    @Override
    public void onImageClick(int position){
        imageview.setImageResource(images[position]);
        myImage = images[position];
    }

    @Override
    public void onSeekBarProgressChanged(int position, SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onSeekBarStartTrackingTouch(int position, SeekBar seekBar) {

    }

    @Override
    public void onSeekBarStopTrackingTouch(int position, SeekBar seekBar) {
        colorValues[position] = seekBar.getProgress();
        imageview.setBackgroundColor(Color.parseColor(parsingHex()));
        recyclerBarAdapter.notifyDataSetChanged();
    }

    public String parsingHex() {
        String a, b, c;

        a = Integer.toHexString(colorValues[0]);
        if(colorValues[0] <= 15)  a = "0" + a;

        b = Integer.toHexString(colorValues[1]);
        if(colorValues[1] <= 15)  b = "0" + b;

        c = Integer.toHexString(colorValues[2]);
        if(colorValues[2] <= 15)  c = "0" + c;

        return "#" + a + b + c;
    }
}
