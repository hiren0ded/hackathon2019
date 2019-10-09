package com.example.demo.ui.account;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.demo.BottomNAvigationActivity;
import com.example.demo.LoginActivity;
import com.example.demo.MainActivity;
import com.example.demo.MainNavagationActivity;
import com.example.demo.R;
import com.example.demo.StepCounterActivity;

public class AccountsFragment extends Fragment implements View.OnClickListener {


    Button btn;

       private AccountsViewModel accountsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        accountsViewModel =
                ViewModelProviders.of(this).get(AccountsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
        accountsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });


        btn = (Button) root.findViewById(R.id.accoutn_logout);
        btn.setOnClickListener(this);



        return root;
    }


    @Override
    public void onClick(View v) {


        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();

        editor.remove("login");
        editor.remove("role");
        editor.commit();
        Intent myIntent = new Intent(getActivity(), LoginActivity.class);
        startActivity(myIntent);


    }


    public void goToAttract(View v)
    {
        Intent intent = new Intent(getActivity(), MainNavagationActivity.class);
        startActivity(intent);
    }
}