package com.example.madinandroid;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class CardPagerAdapter extends FragmentStateAdapter {
    private String name, phone, email, color;
    private int image ,id;
    private List<Fragment> fragments = new ArrayList<>();

    public CardPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);

        fragments.add(new FragmentCard());
        fragments.add(new FragmentEditUser());
        fragments.add(new FragmentEmo());
        fragments.add(new FragmentColor());
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }

    public void setInfoOfUser(String name, String phone, String email, int image, String color, int id) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.image = image;
        this.color = color;
        this.id = id;

        sendInfoToFragments();
    }

    public Bundle wrapBundle() {
        Bundle bundle = new Bundle(6);
        bundle.putString("name", name);
        bundle.putString("phone", phone);
        bundle.putString("email", email);
        bundle.putInt("image", image);
        bundle.putString("color", color);
        bundle.putInt("id", id);

        return bundle;
    }

    public void sendInfoToFragments() {
        Bundle bundle = wrapBundle();

        FragmentCard fragmentCard = (FragmentCard)fragments.get(0);
        fragmentCard.setByBundle(bundle);
        FragmentEditUser fragmentEditUser = (FragmentEditUser)fragments.get(1);
        fragmentEditUser.setByBundle(bundle);
        FragmentEmo fragmentEmo = (FragmentEmo)fragments.get(2);
        fragmentEmo.setByBundle(bundle);
        FragmentColor fragmentColor = (FragmentColor)fragments.get(3);
        fragmentColor.setByBundle(bundle);
    }

    public void updateInfoToFragments() {
        Bundle bundle = wrapBundle();

        FragmentCard fragmentCard = (FragmentCard)fragments.get(0);
        fragmentCard.setByBundle(bundle);
        fragmentCard.setSettingValue();

        FragmentEditUser fragmentEditUser = (FragmentEditUser)fragments.get(1);
        fragmentEditUser.setByBundle(bundle);
        fragmentEditUser.setSettingValue();

        FragmentEmo fragmentEmo = (FragmentEmo)fragments.get(2);
        fragmentEmo.setByBundle(bundle);
        fragmentEmo.setSettingValue();

        FragmentColor fragmentColor = (FragmentColor)fragments.get(3);
        fragmentColor.setByBundle(bundle);
        fragmentColor.setSettingValue();
    }

    public void getAllSettingFromFragment() {
        Bundle bundle;

        FragmentEditUser fragmentEditUser = (FragmentEditUser)fragments.get(1);
        bundle = fragmentEditUser.getSetValue();
        name = bundle.getString("name");
        phone = bundle.getString("phone");
        email = bundle.getString("email");

        FragmentEmo fragmentEmo = (FragmentEmo)fragments.get(2);
        bundle = fragmentEmo.getSetValue();
        image = bundle.getInt("image");

        FragmentColor fragmentColor = (FragmentColor)fragments.get(3);
        bundle = fragmentColor.getSetValue();
        color = bundle.getString("color");

        updateInfoToFragments();
    }

    public void removeAllFragment() {
        FragmentColor fragmentColor = (FragmentColor)fragments.get(3);
        fragmentColor.onDestroy();
        fragmentColor.onDetach();
        fragments.remove(3);

        FragmentEmo fragmentEmo = (FragmentEmo)fragments.get(2);
        fragmentEmo.onDestroy();
        fragmentEmo.onDetach();
        fragments.remove(2);

        FragmentEditUser fragmentEditUser = (FragmentEditUser)fragments.get(1);
        fragmentEditUser.onDestroy();
        fragmentEditUser.onDetach();
        fragments.remove(1);


        FragmentCard fragmentCard = (FragmentCard)fragments.get(0);
        fragmentCard.onDestroy();
        fragmentCard.onDetach();
        fragments.remove(0);

        notifyDataSetChanged();
    }
}
