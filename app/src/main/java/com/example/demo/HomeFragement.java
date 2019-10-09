package com.example.demo;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.roger.catloadinglibrary.CatLoadingView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator;


public class HomeFragement extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    CatLoadingView catLoadingView;
    CircularProgressIndicator circularProgress;



    public HomeFragement() {
        // Required empty public constructor
    }

    public static HomeFragement newInstance(String param1, String param2) {
        HomeFragement fragment = new HomeFragement();
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
        View view =  inflater.inflate(R.layout.fragment_home_fragement, container, false);
        catLoadingView = new CatLoadingView();
        catLoadingView.setCanceledOnTouchOutside(false);
        catLoadingView.show(getFragmentManager(),"");

        getUserCredit();

        return view;

    }





    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        super.getActivity().setTitle("HOME FRAGEMENT");

        circularProgress = view.findViewById(R.id.user_credit);

    }

    private void getUserCredit(){
        String url = "https://wizardsapi.azurewebsites.net/getCredit";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(getContext(),response,Toast.LENGTH_LONG).show();
                        double credit = 0;
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String data = jsonResponse.getString("Credit");
                            credit = Double.parseDouble(data);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        circularProgress.setProgress(credit, 1000);
                        catLoadingView.dismiss();
                        //dialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(),error.toString(),Toast.LENGTH_LONG).show();
                        circularProgress.setProgress(0, 1000);
                        catLoadingView.dismiss();
                        //dialog.dismiss();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){

                SharedPreferences pref = getContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                Map<String,String> params = new HashMap<String, String>();
                String loginid = pref.getString("login","1");
                params.put("id", loginid);
                Log.d("HIREN TEST LOGINI",loginid);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
}
