package com.example.demo;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        TextView textViewMRP = view.findViewById(R.id.sellerview_MRP);
        Button buttonApprove = view.findViewById(R.id.sellerview_approve);
        Button buttonReject = view.findViewById(R.id.sellerview_reject);

        //getting the hero of the specified position
        final SellerData data = printData.get(position);

        //adding values to the list item
        textViewEmail.setText("Email: "+ data.Email);
        textViewProduct.setText("Product: "+ data.Product);
        textViewCredit.setText("Credit: "+ data.Credit);
        textViewMRP.setText("MRP: "+ data.MRP);

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
                Approve(data.tableid,  data.customerId, data.Credit);

            }
        });

        buttonReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //we will call this method to remove the selected value from the list
                //we are passing the position which is to be removed in the method

                reject(data.tableid, position);

                //removeHero(position);
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

    private void Approve(final String id, final String CustomerID, final String creditScore){
        String url = "https://wizardsapi.azurewebsites.net/addCredits";
        final ProgressDialog pdialog = new ProgressDialog(getContext());
        pdialog.setTitle("Approving.....");
        pdialog.setCanceledOnTouchOutside(false);
        pdialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getContext(),response,Toast.LENGTH_LONG).show();
                        pdialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(),error.toString(),Toast.LENGTH_LONG).show();
                        pdialog.dismiss();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("id",id);
                params.put("CustomerId",CustomerID);
                params.put("creditScore", creditScore);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    private void reject(final String id, final int position){
        String url = "https://wizardsapi.azurewebsites.net/rejectRequest";
        final ProgressDialog pdialog = new ProgressDialog(getContext());
        pdialog.setTitle("Rejecting Item.....");
        pdialog.setCanceledOnTouchOutside(false);
        pdialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getContext(),response,Toast.LENGTH_LONG).show();
                        pdialog.dismiss();
                        printData.remove(position);
                        //reloading the list
                        notifyDataSetChanged();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(),error.toString(),Toast.LENGTH_LONG).show();
                        pdialog.dismiss();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("id",id);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
}