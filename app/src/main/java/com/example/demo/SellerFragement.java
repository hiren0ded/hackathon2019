package com.example.demo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SellerFragement extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static ProgressDialog dialog;

    List<SellerData> seller_data = new ArrayList<>();
    private String url = "https://myspring.azurewebsites.net/seller";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ListView listView;


    public SellerFragement() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SellerFragement.
     */
    // TODO: Rename and change types and number of parameters
    public static SellerFragement newInstance(String param1, String param2) {
        SellerFragement fragment = new SellerFragement();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_seller_fragement, container, false);

       /* List<TableClass> data = new ArrayList<>();
        data.add(new TableClass("Title1","1"));
        data.add(new TableClass("Title1","2"));
        data.add(new TableClass("Title1","3"));
        data.add(new TableClass("Title1","4"));


        listView = (ListView) rootView.findViewById(R.id.seller_listView);
        MyAdapter adapter=new MyAdapter(this.getActivity(), R.layout.myxml ,data);
        listView.setAdapter(adapter);*/

        /*List<SellerData> data = new ArrayList<>();
        data.add(new SellerData("https://imagevars.gulfnews.com/2019/06/16/apple-3860991_1920_16b6049de6c_large.jpg","Apple","40","hiren@gmail.com"));
        data.add(new SellerData("https://images.unsplash.com/photo-1515778767554-42d4b373f2b3?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&w=1000&q=80","Grapes","20","hiren@gmail.com"));
        data.add(new SellerData("https://thumbs.dreamstime.com/b/watermelon-slices-wooden-table-55476817.jpg","Watermalon","10","hiren@gmail.com"));*/


        dialog = new ProgressDialog(getContext());
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage("Fetching Latest Data.......");
        dialog.show();
        getSellerData();

        listView = (ListView) rootView.findViewById(R.id.seller_listView);



        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Sellet Activity...");
        SellerAdapter adapter=new SellerAdapter(this.getActivity(), R.layout.seller_cardview ,seller_data);
        listView.setAdapter(adapter);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void getSellerData(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseData(response);
                        //Toast.makeText(getContext(),response,Toast.LENGTH_LONG).show();
                        SellerAdapter adapter=new SellerAdapter(getContext(), R.layout.seller_cardview ,seller_data);
                        listView.setAdapter(adapter);

                        dialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(),error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){

                SharedPreferences pref = getContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                Map<String,String> params = new HashMap<String, String>();
                String loginid = pref.getString("login","1");
                params.put("sellerid", loginid);
                Log.d("HIREN TEST LOGINI",loginid);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    public boolean parseData(String response) {

        try {
            List<String> allNames = new ArrayList<String>();
            JSONObject jsonResponse = new JSONObject(response);

            JSONArray result = jsonResponse.getJSONArray("Result");
            Log.d("HIREN TEST SIZE DATA", ""+result.length());
            for (int i=0; i<result.length()-1; i++) {
                JSONObject data = result.getJSONObject(i);
                String URl = data.getString("ImageUrl");
                String Email = data.getString("CustomerEmail");
                String Product = data.getString("ProductName");
                String Credit = data.getString("creditScore");
                String MRP = data.getString("MRP");
                String tableid = data.getString("id");
                String customerid = data.getString("CustomerId");
                seller_data.add(new SellerData(URl, Product, Credit, Email, MRP, tableid, customerid));
                Log.d("HIREN TESTING", URl+ " "+ Credit + "size:"+seller_data.size());
            }


            //SellerAdapter adapter=new SellerAdapter(this.getActivity(), R.layout.seller_cardview ,seller_data);
            //listView.setAdapter(adapter);


            Log.d("HIREN TESTING SIZE", ""+seller_data.size());

        } catch (Exception e) {
            Toast.makeText(getContext(), "Error while Parsing Data" +e, Toast.LENGTH_SHORT).show();
        }
        return false;

    }







}
