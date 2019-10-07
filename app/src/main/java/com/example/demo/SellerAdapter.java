package com.example.demo;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.List;

public class SellerAdapter extends ArrayAdapter<SellerData> {

    List<SellerData> printData;

    Context context;

    int resource;

    public SellerAdapter(Context context, int resource, List<SellerData> printData) {
        super(context, resource, printData);
        this.context = context;
        this.resource = resource;
        this.printData = printData;
    }

    //this will return the ListView Item as a View
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(resource, null, false);

        //getting the view elements of the list from the view
        ImageView imageView = view.findViewById(R.id.sellerview_imageView);
        TextView textViewEmail = view.findViewById(R.id.sellerview_Email);
        TextView textViewProduct = view.findViewById(R.id.sellerview_Product);
        TextView textViewCredit = view.findViewById(R.id.sellerview_Credit);
        Button buttonApprove = view.findViewById(R.id.sellerview_approve);
        Button buttonReject = view.findViewById(R.id.sellerview_reject);

        //getting the hero of the specified position
        SellerData data = printData.get(position);

        //adding values to the list item
        textViewEmail.setText("Email:"+ data.Email);
        textViewProduct.setText("Product:"+ data.Product);
        textViewCredit.setText("Credit:"+ data.Credit);

        Picasso.with(context)
                .load(data.ImageUrl)
                .fit()
                .centerCrop()
                //.placeholder(R.drawable.placeholder)   // optional
                //.error(R.drawable.error)      // optional
                //.resize(400,400)                        // optional
                .into(imageView);

        //adding a click listener to the button to remove item from the list
        buttonApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //we will call this method to remove the selected value from the list
                //we are passing the position which is to be removed in the method

                //removeHero(position);
            }
        });

        buttonReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //we will call this method to remove the selected value from the list
                //we are passing the position which is to be removed in the method
                removeHero(position);
            }
        });

        //finally returning the view
        return view;
    }

    //this method will remove the item from the list
    private void removeHero(final int position) {
        //Creating an alert dialog to confirm the deletion
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Are you sure you want to delete this?");

        //if the response is positive in the alert
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //removing the item
                printData.remove(position);

                //reloading the list
                notifyDataSetChanged();
            }
        });

        //if response is negative nothing is being done
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        //creating and displaying the alert dialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}