package com.example.madinandroid;

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

public class FragmentCard extends Fragment {
    private String name, phone, email, color;
    private int image;

    private ImageView imageView;
    private TextView nameText, phoneText, emailText;
    private Button editBtn, deleteBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card_sheet, container, false);

        name = getArguments().getString("name");
        phone = getArguments().getString("phone");
        email = getArguments().getString("email");
        image = getArguments().getInt("img");
        color = getArguments().getString("color");

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
