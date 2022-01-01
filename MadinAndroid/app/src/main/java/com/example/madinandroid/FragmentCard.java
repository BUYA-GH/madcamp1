package com.example.madinandroid;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.madinandroid.MainActivity.books;

public class FragmentCard extends Fragment {
    private String name, phone, email, color, id;
    private int image;

    private ImageView imageView;
    private TextView nameText, phoneText, emailText;
    private Button editBtn, deleteBtn;

    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    static Fragment2 fragmentEdit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card_sheet, container, false);

        id = getArguments().getString("id");
        try {
            for(int i = 0; i < books.length(); ++i) {
                JSONObject jsonOb = (JSONObject)books.get(i);
                if((String)jsonOb.get("id") == id) {
                    name = (String)jsonOb.get("name");
                    phone = (String)jsonOb.get("phone");
                    email = (String)jsonOb.get("email");
                    color = (String)jsonOb.get("color");
                    String img = (String)jsonOb.get("image");
                    image = getActivity().getResources().getIdentifier(img, "drawable", getActivity().getPackageName());

                    break;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        imageView = view.findViewById(R.id.cardImageView);
        nameText = view.findViewById(R.id.cardNameInfo);
        phoneText = view.findViewById(R.id.cardPhoneInfo);
        emailText = view.findViewById(R.id.cardInstaInfo);

        imageView.setBackgroundColor(Color.parseColor(color));
        imageView.setImageResource(image);
        nameText.setText(name);
        phoneText.setText(phone);
        emailText.setText(email);

        editBtn = view.findViewById(R.id.cardEditBtn);
        deleteBtn = view.findViewById(R.id.cardDeleteBtn);

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ContactsEditActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("name", name);
                intent.putExtra("phone", phone);
                intent.putExtra("email", email);
                intent.putExtra("img", image);
                intent.putExtra("color", color);
                startActivity(intent);
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;
    }

}
