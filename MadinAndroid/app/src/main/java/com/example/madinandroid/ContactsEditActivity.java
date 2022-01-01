package com.example.madinandroid;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.madinandroid.MainActivity.books;

public class ContactsEditActivity extends AppCompatActivity {
    private ImageButton imgBtn;
    private Button inputBtn;

    private EditText nameEdit;
    private EditText phoneEdit;
    private EditText emailEdit;

    private String name, phone, email, color, id;
    private int imgSrc;
    private int[] colorValues = {255, 255, 255};

    private ActivityResultLauncher<Intent> resultLauncher;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment2);

        Intent intent = getIntent();
        id = intent.getExtras().getString("id");
        name = intent.getExtras().getString("name");
        phone = intent.getExtras().getString("phone");
        email = intent.getExtras().getString("email");
        color = intent.getExtras().getString("color");
        imgSrc = intent.getExtras().getInt("img");

        imgBtn = findViewById(R.id.makeImoge);
        inputBtn = findViewById(R.id.inputBtn);
        nameEdit = findViewById(R.id.nameInput);
        phoneEdit = findViewById(R.id.phoneInput);
        emailEdit = findViewById(R.id.emailInput);

        imgBtn.setImageResource(imgSrc);
        imgBtn.setBackgroundColor(Color.parseColor(color));
        inputBtn.setText("Edit your Friend!");
        nameEdit.setText(name);
        phoneEdit.setText(phone);
        emailEdit.setText(email);

        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {

            @Override
            public void onActivityResult(ActivityResult result) {
                Intent intent = result.getData();
                imgSrc = intent.getIntExtra("img", R.drawable.img_cat);
                colorValues = intent.getIntArrayExtra("colors");

                imgBtn.setImageResource(imgSrc);
                imgBtn.setBackgroundColor(Color.parseColor(parsingHex()));
            }
        });

        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactsEditActivity.this, MakeImoActivity.class);
                resultLauncher.launch(intent);
            }
        });

        inputBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String n = nameEdit.getText().toString();
                String p = phoneEdit.getText().toString();
                String e = emailEdit.getText().toString();

                Log.d("CheckID", id);

                try{
                    for(int i = 0; i < books.length(); ++i) {
                        JSONObject jsonOb = (JSONObject)books.get(i);
                        if(id.equals((String)jsonOb.get("id"))) {
                            jsonOb.put("name", n);
                            jsonOb.put("phone", p);
                            jsonOb.put("email", e);
                            jsonOb.put("image", getResources().getResourceEntryName(imgSrc));
                            jsonOb.put("color", parsingHex());

                            books.put(i, jsonOb);
                            break;
                        }
                    }
                } catch (JSONException jsonException) {
                    jsonException.printStackTrace();
                }

                String json = books.toString();
                PreferenceManager.setString(ContactsEditActivity.this, "books", json);

                Toast.makeText(ContactsEditActivity.this, "Edit Success!", Toast.LENGTH_LONG);
                finish();
            }
        });
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
