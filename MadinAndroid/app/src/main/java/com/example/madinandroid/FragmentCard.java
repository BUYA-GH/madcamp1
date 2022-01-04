package com.example.madinandroid;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.madinandroid.MainActivity.books;

public class FragmentCard extends Fragment {
    private String name, phone, email, color, id;
    private int image;

    private ImageView cardImageView;
    private TextView cardNameText, cardPhoneText, cardEmailText;
    private Button cardEditBtn, cardDeleteBtn;
    private ImageButton exitBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card_sheet, container, false);

        setViewByID(view);
        setSettingValue();

        cardEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    for(int i = 0; i < books.length(); ++i) {
                        JSONObject jsonOb = (JSONObject)books.get(i);
                        if(id.equals((String)jsonOb.get("id"))) {
                            jsonOb.put("name", name);
                            jsonOb.put("phone", phone);
                            jsonOb.put("email", email);
                            jsonOb.put("image", getResources().getResourceEntryName(image));
                            jsonOb.put("color", color);

                            books.put(i, jsonOb);
                            break;
                        }
                    }
                } catch (JSONException j) {
                    j.printStackTrace();
                }

                String json = books.toString();
                PreferenceManager.setString(getActivity(), "books", json);

                ((GalleryDetailsActivity)getActivity()).destroyViewPager();
            }
        });

        cardDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    for(int i = 0; i < books.length(); ++i) {
                        JSONObject obj = (JSONObject)books.get(i);
                        if(id.equals((String)obj.get("id"))) {
                            books.remove(i);
                            break;
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String json = books.toString();
                PreferenceManager.setString(getActivity(), "books", json);

                ((GalleryDetailsActivity)getActivity()).destroyViewPager();
            }
        });

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((GalleryDetailsActivity)getActivity()).destroyViewPager();
            }
        });

        return view;
    }

    public void setByBundle(Bundle bundle) {
        name = bundle.getString("name");
        phone = bundle.getString("phone");
        email = bundle.getString("email");
        color = bundle.getString("color");
        image = bundle.getInt("image");
        id = bundle.getString("id");
    }

    public void setViewByID(View view) {
        cardImageView = view.findViewById(R.id.cardImageView);
        cardNameText = view.findViewById(R.id.cardNameInfo);
        cardPhoneText = view.findViewById(R.id.cardPhoneInfo);
        cardEmailText = view.findViewById(R.id.cardInstaInfo);
        cardEditBtn = view.findViewById(R.id.cardEditBtn);
        cardDeleteBtn = view.findViewById(R.id.cardDeleteBtn);
        exitBtn = view.findViewById(R.id.cardExitBtn);
    }

    public void setSettingValue() {
        cardImageView.setBackgroundColor(Color.parseColor(color));
        cardImageView.setImageResource(image);
        cardNameText.setText(name);
        cardPhoneText.setText(phone);
        cardEmailText.setText(email);
    }

}
