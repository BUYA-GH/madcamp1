package com.example.mytablayout;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class itemAdapter extends RecyclerView.Adapter<itemAdapter.itemViewHolder>{

    String data1[], data2[], data3[]; // text objects
    Context context;

    // initalize the class
    public itemAdapter(Context ct, String s1[], String s2[], String s3[]){
        context = ct;
        data1 = s1;
        data2 = s2;
        data3 = s3;
    }

    //define itemAdapter method
    @NonNull
    @Override
    public itemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflater converts xml resource to view object
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item, parent, false);
        return new itemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull itemViewHolder holder, int position) {
        holder.text1.setText(data1[position]);
        holder.text2.setText(data2[position]);
        holder.text3.setText(data3[position]);
    }

    @Override
    public int getItemCount() {
        return data1.length;
    }


    // define itemViewHolder class
    public class itemViewHolder extends RecyclerView.ViewHolder{
        TextView text1, text2, text3;

        public itemViewHolder(View itemView){
            super(itemView);
            text1 = itemView.findViewById(R.id.name_txt);
            text2 = itemView.findViewById(R.id.phone_txt);
            text3 = itemView.findViewById(R.id.insta_txt);
        }

    }
}
