package com.example.madinandroid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

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

public class Fragment2 extends Fragment {
    private ImageButton imgBtn;
    private Button inputBtn;

    private EditText nameEdit;
    private EditText phoneEdit;
    private EditText emailEdit;
    private int imgSrc = R.drawable.mulempyo;

    private ActivityResultLauncher<Intent> resultLauncher;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("LifeCycleCheck", "I am in onCreateView in Fragment2");
        View view = inflater.inflate(R.layout.fragment2, container, false);

        imgBtn = view.findViewById(R.id.makeImoge);
        inputBtn = view.findViewById(R.id.inputBtn);

        nameEdit = view.findViewById(R.id.nameInput);
        emailEdit = view.findViewById(R.id.emailInput);
        phoneEdit = view.findViewById(R.id.phoneInput);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode() == RESULT_OK) {
                    Log.d("ActivityBack", "Back to Activity Successfully");
                    Intent intent = result.getData();
                    imgSrc = intent.getIntExtra("img", R.drawable.mulempyo);

                    imgBtn.setImageResource(imgSrc);

                }
            }
        });

        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MakeImoActivity.class);
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
                    newOb.put("name", n);
                    newOb.put("phone", p);
                    newOb.put("email", e);
                    newOb.put("image", getResources().getResourceEntryName(imgSrc));

                    books.put(newOb);
                } catch(JSONException j) {
                    j.printStackTrace();
                }
            }
        });

        return view;
    }
}