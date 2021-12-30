package com.example.madinandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerContactAdapter extends RecyclerView.Adapter<RecyclerContactAdapter.ContactViewHolder> {

    String[] names, phones, emails;
    int[] imgSources;
    Context context;

    public RecyclerContactAdapter(Context ct, String[] ns, String[] ps, String[] es, int[] imgSrc) {
        context = ct;
        names = ns;
        phones = ps;
        emails = es;
        imgSources = imgSrc;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.contact_row, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        holder.nameText.setText(names[position]);
        holder.phoneText.setText(phones[position]);
        holder.emailText.setText(emails[position]);
        holder.imgView.setImageResource(imgSources[position]);
    }

    @Override
    public int getItemCount() {
        return names.length;
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {

        TextView nameText, phoneText, emailText;
        ImageView imgView;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.contactNameView);
            phoneText = itemView.findViewById(R.id.contactPhoneView);
            emailText = itemView.findViewById(R.id.contactEmailView);
            imgView = itemView.findViewById(R.id.contactImageView);
        }
    }
}
