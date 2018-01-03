package com.example.admin.loginsignpage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.mobodexter.paasmerandroidcloudsdk.controller.Async;
import com.mobodexter.paasmerandroidcloudsdk.handlers.GenericHandlers;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    Async async;
    String TAG="TAG";
    private ProgressDialog progressdialog;
    private TextView textViewLogin,txtResend;
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



        textViewLogin.setOnClickListener(this);
        signUp.setOnClickListener(this);
        txtResend.setOnClickListener(this);
        progressdialog = new ProgressDialog(this);
        async=new Async();



    }

    private void init() {

        textViewLogin = (TextView) findViewById(R.id.alreadyRegister);
        name = (EditText) findViewById(R.id.etName);
        emailAddress = (EditText) findViewById(R.id.etEmailAddress);
        password = (EditText) findViewById(R.id.etPassword);
        confirmPassword = (EditText) findViewById(R.id.etConfirmPassword);
        txtResend= (TextView) findViewById(R.id.txtResend);
        /*organization = (EditText) findViewById(R.id.etOrganization);*/


        purposeSdk = (Spinner) findViewById(R.id.spSDK);
        signUp = (Button) findViewById(R.id.btRegister);



    }

    private void registerUser() {
        final String userName = name.getText().toString().trim();
        final String email = emailAddress.getText().toString().trim();
        final String pass = password.getText().toString().trim();
        final String confirmPass = confirmPassword.getText().toString().trim();

//        final String appPurposeName = purposeSdk.getSelectedItem().toString().trim();

        Log.d(TAG,userName);
        Log.d(TAG,email);
        Log.d(TAG,pass);
        Log.d(TAG,confirmPass);

       // Log.d(TAG,appPurposeName);

        progressdialog.setMessage("User Registering.....");
        progressdialog.show();

       // loginController.userSignup(userName,pass,email,phn,confirmPass,countryName,stateName,appPurposeName,genericHandlers);
        async.userSignup(userName, pass, email, new GenericHandlers() {
            @Override
            public void onSuccess() {
                Intent intent=new Intent(MainActivity.this,EmailVerification.class);
                intent.putExtra("email",emailAddress.getText().toString());
                startActivity(intent);
            }

            @Override
            public void onSuccess(JSONArray response) {

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

    }
    private void resendVerifier(){
        final String userName = name.getText().toString().trim();
        final String email = emailAddress.getText().toString().trim();
        final String pass = password.getText().toString().trim();
        final String confirmPass = confirmPassword.getText().toString().trim();

//        final String appPurposeName = purposeSdk.getSelectedItem().toString().trim();

        Log.d(TAG,userName);
        Log.d(TAG,email);
        Log.d(TAG,pass);
        Log.d(TAG,confirmPass);

        // Log.d(TAG,appPurposeName);

        progressdialog.setMessage("User Registering.....");
        progressdialog.show();

        // loginController.userSignup(userName,pass,email,phn,confirmPass,countryName,stateName,appPurposeName,genericHandlers);
        async.resendVerification( email, new GenericHandlers() {
            @Override
            public void onSuccess() {
                Intent intent=new Intent(MainActivity.this,EmailVerification.class);
                intent.putExtra("email",emailAddress.getText().toString());
                startActivity(intent);
            }

            @Override
            public void onSuccess(JSONArray response) {

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


        if (view.getId() == R.id.btRegister) {
            registerUser();
        }

        if(view.getId()==R.id.txtResend){
            resendVerifier();
        }




    }

    private boolean validateConfirmPassword(String pass, String confirmPass) {
        return !pass.isEmpty() && !confirmPass.isEmpty() && pass.equals(confirmPass);
    }

    private boolean validateEmail(String address) {
        return VALID_EMAIL_ADDRESS_REGEX.matcher(address).find();
    }



    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
