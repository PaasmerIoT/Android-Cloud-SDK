package com.example.admin.loginsignpage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.mobodexter.paasmerandroidcloudsdk.controller.Async;
import com.mobodexter.paasmerandroidcloudsdk.handlers.GenericHandlers;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 13/02/17.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener
{
   private TextView textViewRegister,txtForgotPassword;
   private EditText email,pass;
   private Button signIn,cancle;
    private ProgressDialog progressDialog;
    Async async;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        txtForgotPassword= (TextView) findViewById(R.id.txtForgot);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Paasmer");

        async=new Async();
        if(SharedPrefManager.getInstance(this).isLoggedIn())
        {
            finish();
            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
            return;
        }
        init();
        progressDialog =new ProgressDialog(this);
        progressDialog.setMessage("please wait");
        signIn.setOnClickListener(this);
        cancle.setOnClickListener(this);
        textViewRegister.setOnClickListener(this);
        txtForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,ForgotPassword.class);
                startActivity(intent);
            }
        });
    }



    private void init()
    {
        textViewRegister=(TextView)findViewById(R.id.notRegister);
        signIn=(Button) findViewById(R.id.btSignIn);
        cancle=(Button)findViewById(R.id.btCancle);
        email=(EditText)findViewById(R.id.etemail);
        pass=(EditText)findViewById(R.id.etpass);

    }

    private void userLogin() throws JSONException {
        final String username=email.getText().toString().trim();
        final String password=pass.getText().toString().trim();

        progressDialog.show();
     //   loginController.userLogin(username,password,genericHandlers);
        async.userLogin(username, password, new GenericHandlers() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onSuccess(JSONObject response) {
                try {
                    JSONObject jsonObject=new JSONObject(response.toString());
                    // Toast.makeText(getApplicationContext(),jsonObject.getInt("success")+"",Toast.LENGTH_SHORT).show();
                    int success=jsonObject.getInt("success");
                    if(success==1)
                    {
                        SharedPrefManager.getInstance(getApplicationContext())
                                .userLogIn(
                                        jsonObject.getString("message")

                                );
                        startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                        finish();

                    }else {
                        Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Exception exception) {

            }

            @Override
            public void onFailure(String msg) {

            }
        });
       /* StringRequest stringRequest=new StringRequest(
                Request.Method.POST, Constants.URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();
                try {
                    JSONObject jsonObject=new JSONObject(response);
                   // Toast.makeText(getApplicationContext(),jsonObject.getInt("success")+"",Toast.LENGTH_SHORT).show();
                    int success=jsonObject.getInt("success");
                    if(success==1)
                    {
                        SharedPrefManager.getInstance(getApplicationContext())
                                .userLogIn(
                                        jsonObject.getString("message")

                                );
               startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                        finish();

                    }else {
                        Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();

                Toast.makeText(getApplicationContext(), error.getMessage(),Toast.LENGTH_LONG).show();


            }
        }
        ){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params=new HashMap<>();
                params.put("email",username);
                params.put("password",password);

                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);*/
    }

    @Override
    public void onClick(View v)
    {
        if(v.getId()==R.id.notRegister) {
            startActivity(new Intent(this, MainActivity.class));
            return;
        }

        if(v.getId()==R.id.btSignIn)
        {
            try {
                userLogin();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return;
        }
        if(v.getId()==R.id.btCancle)
        {
            finish();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            return;
        }


    }

   /* GenericHandlers genericHandlers=new GenericHandlers() {
        @Override
        public void onSuccess() {

        }

        @Override
        public void onSuccess(JSONObject response) {
            try {
                JSONObject jsonObject=new JSONObject(response.toString());
                // Toast.makeText(getApplicationContext(),jsonObject.getInt("success")+"",Toast.LENGTH_SHORT).show();
                int success=jsonObject.getInt("success");
                if(success==1)
                {
                    SharedPrefManager.getInstance(getApplicationContext())
                            .userLogIn(
                                    jsonObject.getString("message")

                            );
                    startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                    finish();

                }else {
                    Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Exception exception) {

        }

        @Override
        public void onFailure(String msg) {
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
        }
    };*/
}
