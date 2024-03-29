package com.example.user.appproject;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class UpdateActivity extends AppCompatActivity implements View.OnClickListener{


    /*
       This file allows the user to update their details by inputting their new details and those
       details are posted to a php script and forwarded to the database
     */

   public static final String UPDATE_URL = "http://ourSystem.000webhostapp.com/updateUserDetails.php";
    //public static final String UPDATE_URL = "http://192.168.1.9/updateUserDetails.php";
    public static final String KEY_EMAIL="email";
    public static final String KEY_PASSWORD="password";

    private EditText updateEmail;
    private EditText updatePassword;
    private Button buttonLogin;
    private String email;
    private String password;
    public Boolean CheckEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        updateEmail = (EditText) findViewById(R.id.emailInput);
        updatePassword = (EditText) findViewById(R.id.passInput);
        updatePassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(this);
    }


    private void update() {
        final String usersEmail = getIntent().getExtras().getString("currentEmail");
        Log.d("EMAIL",usersEmail);
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Updating");
        dialog.setProgressStyle(dialog.STYLE_SPINNER);
        dialog.show();
        email = updateEmail.getText().toString().trim();
        password = updatePassword.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPDATE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("updated")) {
                            goToLogin();
                            dialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UpdateActivity.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put(KEY_EMAIL,email);
                map.put(KEY_PASSWORD,password);
                map.put("currentEmail",usersEmail); //post current email to be checked against in database
                return map;
            }
        };

        // reset the retry policy for network connection
        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void goToLogin(){
        Intent intent = new Intent(UpdateActivity.this,UserLogin.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        checkIfEditextsAreEmpty();
        if(CheckEditText) {
            update();
        }
        else {

            Toast.makeText(UpdateActivity.this, "Please fill all form fields.", Toast.LENGTH_LONG).show();
        }

    }

    // method to check if all the edittexts have been filled out or not
    private void checkIfEditextsAreEmpty(){
        // Getting values from EditText.
        String emailHolder = updateEmail.getText().toString().trim();
        String PasswordHolder = updatePassword.getText().toString().trim();

        // Checking whether EditText value is empty or not.
        if (TextUtils.isEmpty(emailHolder) || TextUtils.isEmpty(PasswordHolder)) {

            // If any of EditText is empty then set variable value as False.
            CheckEditText = false;

        } else {

            // If any of EditText is filled then set variable value as True.
            CheckEditText = true;
        }
    }

}
