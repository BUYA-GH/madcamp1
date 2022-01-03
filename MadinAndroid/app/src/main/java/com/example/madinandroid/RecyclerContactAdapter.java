package com.example.madinandroid;

import static com.example.madinandroid.MainActivity.books;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class RecyclerContactAdapter extends RecyclerView.Adapter<RecyclerContactAdapter.ContactViewHolder> implements Filterable {

    String[] names, phones, emails, backgroundColors;
    int[] imgSources;
    Context context;
    JSONArray jsonarray;
    JSONArray jsonarrayFiltered;
    

    public RecyclerContactAdapter(Context ct, JSONArray array) {
        context = ct;
        jsonarray = array;
        jsonarrayFiltered = jsonarray;
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
        /*
        holder.nameText.setText(names[position]);
        holder.phoneText.setText(phones[position]);
        holder.emailText.setText(emails[position]);
        holder.imgView.setImageResource(imgSources[position]);
        holder.imgView.setBackgroundColor(Color.parseColor(backgroundColors[position]));
         */

        try{
            JSONObject tmp = (JSONObject)jsonarrayFiltered.get(position);
            holder.nameText.setText((String)tmp.get("name"));
            holder.phoneText.setText((String)tmp.get("phone"));
            holder.emailText.setText((String)tmp.get("email"));

            String img_name = (String)tmp.get("image");
            int img_int = context.getResources().getIdentifier(img_name, "drawable", context.getPackageName());
            holder.imgView.setImageResource(img_int);
            holder.imgView.setBackgroundColor(Color.parseColor((String)tmp.get("color")));
        } catch(JSONException j) {
            j.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return jsonarrayFiltered.length();
    }


    public class ContactViewHolder extends RecyclerView.ViewHolder{

        TextView nameText, phoneText, emailText;
        ImageView imgView;
        View foregroundView, backgroundView;
        Boolean isClamp = false;
        Button contactButton;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.contactNameView);
            phoneText = itemView.findViewById(R.id.contactPhoneView);
            emailText = itemView.findViewById(R.id.contactEmailView);
            imgView = itemView.findViewById(R.id.contactImageView);
            foregroundView = itemView.findViewById(R.id.contactFg);
            backgroundView = itemView.findViewById(R.id.contactBg);

            // contactButton Listener
            contactButton = itemView.findViewById(R.id.contactButton);
            contactButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) { // it works for individual view only which isCLamp
                    if(isClamp){
                        CharSequence text = (CharSequence) nameText.getText();
                        Toast myToast = Toast.makeText(context, text,1);
                        myToast.show();
                    }

                }
            });

            // item click Listener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(isClamp){
                        isClamp = false;
                        foregroundView.setTranslationX(0f);
                        Toast myToast = Toast.makeText(context, "test",1);
                        myToast.show();
                    }
                }
            });

        }

        public View getView(){
            return foregroundView;
        }
        // set isClamp to make only one item be clamp
        public void setIsClamp(Boolean val){
            isClamp = val;
        }
        public Boolean getIsClamp(){
            return isClamp;
        }

    }

    // make new contact Filter
    @Override
    public Filter getFilter(){
        return contactFilter;
    }
    private Filter contactFilter = new Filter(){
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            JSONArray filteredArray = new JSONArray();

            // charSequence is userInput
            if(charSequence == null || charSequence.length() == 0){
                filteredArray = jsonarray;
            }else{
                String filterString = charSequence.toString().toLowerCase().trim();
                // for each jsonobject check if it contains the userinput
                for(int i=0 ; i<jsonarray.length(); i++){
                    JSONObject tmp = null;
                    try { tmp = (JSONObject)jsonarray.get(i);
                    } catch (JSONException e) { e.printStackTrace(); }
                    try {
                        if(tmp.get("name").toString().toLowerCase().contains(filterString) ||
                                tmp.get("phone").toString().toLowerCase().contains(filterString) ||
                                    tmp.get("email").toString().toLowerCase().contains(filterString)){
                            filteredArray.put(tmp);
                        }
                    } catch (JSONException e) { e.printStackTrace(); }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredArray;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            jsonarrayFiltered = (JSONArray) filterResults.values;
        }
    };


}
