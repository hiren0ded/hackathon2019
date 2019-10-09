package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.roger.catloadinglibrary.CatLoadingView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private String url = "https://wizardsapi.azurewebsites.net/login";
    public static boolean login = false;
    private ProgressDialog dialog;
    CatLoadingView catLoadingView;

    String user_role = "USER";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText email, password;
        final Button login_button;
        final TextView register;
        final RadioGroup radioGroup;


        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();

        String login = pref.getString("login", null);
        if(login !=null)
        {
            Intent myIntent = new Intent(LoginActivity.this, MainNavagationActivity.class);
            LoginActivity.this.startActivity(myIntent);
        }


        email = findViewById(R.id.Login_Email);
        password = findViewById(R.id.Login_Password);
        login_button = findViewById(R.id.Login_LoginButton);
        register = findViewById(R.id.Login_Register);
        radioGroup = findViewById(R.id.Login_RadioGroup);

        //dialog = new ProgressDialog(LoginActivity.this);
        //dialog.setCanceledOnTouchOutside(false);

        catLoadingView = new CatLoadingView();
        catLoadingView.setCanceledOnTouchOutside(false);



        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb) {
                    Log.d("LOGIN TYPE", rb.getText().toString());
                    user_role = rb.getText().toString();
                }

            }
        });

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean email_check = email_pattern(email);
                boolean password_check = password_pattern(password);

                if(email_check && password_check)
                {
                    //dialog.setMessage("Verifying.......");
                    //dialog.show();
                    catLoadingView.show(getSupportFragmentManager(), "");
                    loginUser(email.getText().toString(), password.getText().toString());

                }
                else
                {
                    Log.d("Status","Failed");

                }

            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Register","Register");
            }
        });




    }

    private void loginUser(final String email, final String password){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(LoginActivity.this,response,Toast.LENGTH_LONG).show();
                        login = parseData(response);
                        catLoadingView.dismiss();

                        if(login) {
                            Intent myIntent = new Intent(LoginActivity.this, MainNavagationActivity.class);
                            LoginActivity.this.startActivity(myIntent);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                        catLoadingView.dismiss();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("email",email);
                params.put("password",password);
                params.put("role", user_role);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public boolean parseData(String response) {

        try {
            Toast.makeText(this, "kk::"+response, Toast.LENGTH_SHORT).show();
            JSONObject json = new JSONObject(response);
            boolean status = json.getBoolean("success");
            String userid = null;
            if(status == true) {
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
                userid = json.getString("userid");
                editor.putString("login", userid);
                editor.putString("role", user_role);
                editor.commit();
                return true;
            }
            else{
                Toast.makeText(this, "Invalid Email and Password", Toast.LENGTH_SHORT).show();
            }


        } catch (Exception e) {
            Toast.makeText(this, "Error" +e, Toast.LENGTH_SHORT).show();
        }
        return false;

    }
    boolean email_pattern(EditText e){
        String email = e.getText().toString();
        if(email.matches(emailPattern))
        {
            return true;
        }
        e.setError("Invalid Email Address");

        return false;
    }

    boolean password_pattern(EditText p){
        String password = p.getText().toString();
        Log.d("length",""+password.length());
        if(password.length()<4){
            p.setError("Password Length Should Be Greater than 4");
            return false;
        }
        return true;
    }



}
