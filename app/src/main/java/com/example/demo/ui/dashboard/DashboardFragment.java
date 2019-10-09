package com.example.demo.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.demo.MapsActivity;
import com.example.demo.R;
import com.example.demo.StepCounterActivity;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    CardView products;
    CardView stepcount;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        dashboardViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
        //        textView.setText(s);
            }
        });

        products = root.findViewById(R.id.dashboardProducts);
        products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), MapsActivity.class);
                //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //getApplicationContext().startActivity(i);
                getActivity().startActivity(i);
            }
        });

        stepcount = root.findViewById(R.id.dashboardStepCount);
        stepcount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), StepCounterActivity.class);
                getActivity().startActivity(i);
            }
        });

        return root;
    }
}