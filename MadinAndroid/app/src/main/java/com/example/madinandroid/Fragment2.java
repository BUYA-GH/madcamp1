package com.example.madinandroid;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import static android.app.Activity.RESULT_OK;
import static com.example.madinandroid.MainActivity.books;
import static com.example.madinandroid.MainActivity.nowId;

import soup.neumorphism.NeumorphCardView;

public class Fragment2 extends Fragment {
    private NeumorphCardView emojiBack;
    private ImageView emojiImg;
    private Button emojiButton;

    private Button inputBtn;

    private EditText nameEdit;
    private EditText phoneEdit;
    private EditText emailEdit;
    private int imgSrc = R.drawable.img_party;
    private int[] colorValues = {255, 255, 255};

    private ActivityResultLauncher<Intent> resultLauncher;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("LifeCycleCheck", "I am in onCreateView in Fragment2");
        View view = inflater.inflate(R.layout.fragment2, container, false);

        emojiBack = view.findViewById(R.id.emoji_back);
        emojiImg = view.findViewById(R.id.emoji_img);
        emojiButton = view.findViewById(R.id.emoji_button);

        inputBtn = view.findViewById(R.id.addBtn);

        nameEdit = view.findViewById(R.id.nameInput);
        emailEdit = view.findViewById(R.id.emailInput);
        phoneEdit = view.findViewById(R.id.phoneInput);
        phoneEdit.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        emojiBack.setBackgroundColor(Color.parseColor(parsingHex()));

        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode() == RESULT_OK) {
                    Log.d("ActivityBack", "Back to Activity Successfully");
                    Intent intent = result.getData();
                    imgSrc = intent.getIntExtra("img", R.drawable.img_party);
                    colorValues = intent.getIntArrayExtra("colors");

                    emojiImg.setImageResource(imgSrc);
                    emojiBack.setBackgroundColor(Color.parseColor(parsingHex()));
                }
            }
        });

        emojiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MakeImoActivity.class);
                intent.putExtra("img", imgSrc);
                intent.putExtra("color", colorValues);
                resultLauncher.launch(intent);
            }
        });

        inputBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String n = nameEdit.getText().toString();
                String p = phoneEdit.getText().toString();
                String e = emailEdit.getText().toString();

                nameEdit.setText("");
                phoneEdit.setText("");
                emailEdit.setText("");

                try{
                    JSONObject newOb = new JSONObject();
                    newOb.put("id", ++nowId);
                    newOb.put("name", n);
                    newOb.put("phone", p);
                    newOb.put("email", e);
                    newOb.put("image", getResources().getResourceEntryName(imgSrc));
                    newOb.put("color", parsingHex());
                    newOb.put("star",false);

                    books.put(newOb);
                } catch(JSONException j) {
                    j.printStackTrace();
                }

                String json = books.toString();
                PreferenceManager.setString(getActivity(), "books", json);
                Toast.makeText(getActivity(), "Success!", Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

    // convert string to hexcode
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