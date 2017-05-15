package com.example.admin.loginsignpage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import com.mobodexter.paasmerandroidcloudsdk.controller.Async;
import com.mobodexter.paasmerandroidcloudsdk.handlers.GenericHandlers;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Async async;
    String TAG="TAG";
    private ProgressDialog progressdialog;
    private TextView textViewLogin;
    private EditText name, emailAddress, password, confirmPassword, phoneNumber;
    private Spinner country, states, purposeSdk;
    private Button signUp;
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(SharedPrefManager.getInstance(this).isLoggedIn())
        {
            finish();
            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
            return;
        }

        init();
        states.setEnabled(false);
        states.setClickable(false);
        textViewLogin.setOnClickListener(this);
        signUp.setOnClickListener(this);
        progressdialog = new ProgressDialog(this);
        async=new Async();
        country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String country = (String) parent.getItemAtPosition(position);
                StateMap stateMap = new StateMap();
                states.setAdapter(new CustomAdapter(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, stateMap.map.get(country)));
                states.setEnabled(true);
                states.setClickable(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
       /* Log.v(TAG, "item not selected");*/
                states.setEnabled(true);
                states.setClickable(true);
            }
        });


    }

    private void init() {

        textViewLogin = (TextView) findViewById(R.id.alreadyRegister);
        name = (EditText) findViewById(R.id.etName);
        emailAddress = (EditText) findViewById(R.id.etEmailAddress);
        password = (EditText) findViewById(R.id.etPassword);
        confirmPassword = (EditText) findViewById(R.id.etConfirmPassword);
        phoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
        /*organization = (EditText) findViewById(R.id.etOrganization);*/
        country = (Spinner) findViewById(R.id.spCountry);
        states = (Spinner) findViewById(R.id.spStates);
        purposeSdk = (Spinner) findViewById(R.id.spSDK);
        signUp = (Button) findViewById(R.id.btRegister);


    }

    private void registerUser() {
        final String userName = name.getText().toString().trim();
        final String email = emailAddress.getText().toString().trim();
        final String pass = password.getText().toString().trim();
        final String confirmPass = confirmPassword.getText().toString().trim();
        final String phn = phoneNumber.getText().toString().trim();
        final String countryName = country.getSelectedItem().toString().trim();
        final String stateName = states.getSelectedItem().toString().trim();
        final String appPurposeName = purposeSdk.getSelectedItem().toString().trim();

        Log.d(TAG,userName);
        Log.d(TAG,email);
        Log.d(TAG,pass);
        Log.d(TAG,confirmPass);
        Log.d(TAG,phn);
        Log.d(TAG,countryName);
        Log.d(TAG,stateName);
        Log.d(TAG,appPurposeName);

        progressdialog.setMessage("User Registering.....");
        progressdialog.show();

       // loginController.userSignup(userName,pass,email,phn,confirmPass,countryName,stateName,appPurposeName,genericHandlers);
        async.userSignup(userName, pass, email, phn, confirmPass, countryName, stateName, appPurposeName, new GenericHandlers() {
            @Override
            public void onSuccess() {
                Intent intent=new Intent(MainActivity.this,EmailVerification.class);
                intent.putExtra("email",emailAddress.getText().toString());
                startActivity(intent);
            }

            @Override
            public void onSuccess(JSONObject response) {
                Intent intent=new Intent(MainActivity.this,EmailVerification.class);
                intent.putExtra("email",emailAddress.getText().toString());
                startActivity(intent);
            }

            @Override
            public void onFailure(Exception exception) {
                Toast.makeText(getApplicationContext(),"sign failed"+ exception.getMessage(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(String msg) {
                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
            }
        });
     /*   StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressdialog.dismiss();

                        if(!(response.startsWith("{")))
                            Toast.makeText(getApplicationContext(),"respose does not correct",Toast.LENGTH_SHORT).show();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressdialog.hide();
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Name", userName);
                params.put("EmailAddress", email);
                params.put("Password", pass);
                params.put("Confirm Password", confirmPass);
                params.put("Phone Number", phn);
                params.put("Country", countryName);
                params.put("States", stateName);
                params.put("Purpose SDK", appPurposeName);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
*/
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.alreadyRegister) {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            return;
        }
        if (!validateEmail(emailAddress.getText().toString())) {
            Toast.makeText(this, "invalid email address, please retry.", LENGTH_SHORT).show();
            emailAddress.setFocusable(true);
            return;
        }
        if (!validateConfirmPassword(password.getText().toString(), confirmPassword.getText().toString())) {
            Toast.makeText(this, "password mismatch, please retry.", LENGTH_SHORT).show();
            emailAddress.setFocusable(true);
            return;
        }

        if(states.getSelectedItem().toString().equals("Choose States") || country.getSelectedItem().toString().equals("Choose Country"))
        {
            Toast.makeText(this, "country or state not selected !!!.", LENGTH_SHORT).show();
            return;
        }
        if (view.getId() == R.id.btRegister) {
            registerUser();
        }


    }

    private boolean validateConfirmPassword(String pass, String confirmPass) {
        return !pass.isEmpty() && !confirmPass.isEmpty() && pass.equals(confirmPass);
    }

    private boolean validateEmail(String address) {
        return VALID_EMAIL_ADDRESS_REGEX.matcher(address).find();
    }

    GenericHandlers genericHandlers=new GenericHandlers() {
        @Override
        public void onSuccess() {
            Intent intent=new Intent(MainActivity.this,EmailVerification.class);
            intent.putExtra("email",emailAddress.getText().toString());
            startActivity(intent);
        }

        @Override
        public void onSuccess(JSONObject response) {
            Intent intent=new Intent(MainActivity.this,EmailVerification.class);
            intent.putExtra("email",emailAddress.getText().toString());
            startActivity(intent);
        }

        @Override
        public void onFailure(Exception exception) {
            Toast.makeText(getApplicationContext(),"sign failed"+ exception.getMessage(),Toast.LENGTH_LONG).show();
        }

        @Override
        public void onFailure(String msg) {
            Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
        }
    };
}
