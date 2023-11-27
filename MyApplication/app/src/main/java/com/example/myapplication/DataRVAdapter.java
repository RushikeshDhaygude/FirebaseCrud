package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.myapplication.Models.User;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DataRVAdapter extends RecyclerView.Adapter<DataRVAdapter.ViewHolder> {
    // creating variables for our ArrayList and context
    private ArrayList<User> userArrayList;
    private Context context;

    // creating constructor for our adapter class
    public DataRVAdapter(ArrayList<User> userArrayList, Context context) {
        this.userArrayList = userArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public DataRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // passing our layout file for displaying our card item
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.data_items, parent, false));
    }







    @Override
    public void onBindViewHolder(@NonNull DataRVAdapter.ViewHolder holder, int position) {
        // setting data to our text views from our modal class.
        User users = userArrayList.get(position);
        holder.nameTV.setText(users.getName());
        holder.addressTV.setText(users.getAddress());
        holder.phoneTV.setText(users.getPhone());
    }

    @Override
    public int getItemCount() {
        // returning the size of our array list.
        return userArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        // creating variables for our text views.
        private final TextView nameTV;
        private final TextView addressTV;
        private final TextView phoneTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views.
            nameTV = itemView.findViewById(R.id.idTVname);
            addressTV = itemView.findViewById(R.id.idTVaddress);
            phoneTV = itemView.findViewById(R.id.idTVphone);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // after clicking of the item of recycler view.
                    // we are passing our course object to the new activity.
                    User users = userArrayList.get(getAdapterPosition());

                    // below line is creating a new intent.
                    Intent i = new Intent(context, UpdateData.class);

                    // below line is for putting our course object to our next activity.
                    i.putExtra("user", users);

                    // after passing the data we are starting our activity.
                    context.startActivity(i);
                }
            });



        }
    }
}
