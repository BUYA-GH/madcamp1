package com.example.madinandroid;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

public class FragmentEditUser extends Fragment {
    private String name, phone, email, color;
    private int image;

    private ImageView imgEditView;
    private EditText nameEditText;
    private EditText phoneEditText;
    private EditText emailEditText;
    private Button exitBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_user, container, false);
        setViewByID(view);
        setSettingValue();

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
    }

    public void setViewByID(View view) {
        imgEditView = view.findViewById(R.id.cardEditImageView);
        nameEditText = view.findViewById(R.id.cardEditNameInfo);
        phoneEditText = view.findViewById(R.id.cardEditPhoneInfo);
        phoneEditText.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        emailEditText = view.findViewById(R.id.cardInstaInfo);
        exitBtn = view.findViewById(R.id.cardExitBtn);
    }

    public void setSettingValue() {
        imgEditView.setBackgroundColor(Color.parseColor(color));
        imgEditView.setImageResource(image);
        nameEditText.setText(name);
        phoneEditText.setText(phone);
        emailEditText.setText(email);
    }

    public Bundle getSetValue() {
        String n = nameEditText.getText().toString();
        String p = phoneEditText.getText().toString();
        String e = emailEditText.getText().toString();

        Bundle bundle = new Bundle(3);
        bundle.putString("name", n);
        bundle.putString("phone", p);
        bundle.putString("email", e);

        return bundle;
    }
}