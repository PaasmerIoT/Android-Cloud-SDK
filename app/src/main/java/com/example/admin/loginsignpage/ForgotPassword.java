package com.example.admin.loginsignpage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.mobodexter.paasmerandroidcloudsdk.controller.Async;
import com.mobodexter.paasmerandroidcloudsdk.handlers.GenericHandlers;

import org.json.JSONObject;

public class ForgotPassword extends AppCompatActivity {
    private EditText etEmail,etPassword,etCode;
    private Button btnRequest,btnReset;

    Async async;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        etEmail= (EditText) findViewById(R.id.etEmail);
        etPassword= (EditText) findViewById(R.id.etPassword);
        etCode= (EditText) findViewById(R.id.etCode);
        btnRequest= (Button) findViewById(R.id.btnRequest);
        btnReset= (Button) findViewById(R.id.btnChange);
        async=new Async();



        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=etEmail.getText().toString();
                etCode.setVisibility(View.VISIBLE);
                etPassword.setVisibility(View.VISIBLE);
                btnReset.setVisibility(View.VISIBLE);
                btnRequest.setVisibility(View.GONE);
              //  loginController.onForgotPassword(email,genericHandlers);
                async.onForgotPassword(email, new GenericHandlers() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onSuccess(JSONObject response) {

                    }

                    @Override
                    public void onFailure(Exception exception) {

                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                });
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=etEmail.getText().toString();
                String code=etCode.getText().toString();
                String password=etPassword.getText().toString();
               // loginController.onForgotPasswordVerify(email,password,code,genericHandlers2);
                async.onForgotPasswordVerify(email, password, code, new GenericHandlers() {
                    @Override
                    public void onSuccess() {
                        finish();
                    }

                    @Override
                    public void onSuccess(JSONObject response) {

                    }

                    @Override
                    public void onFailure(Exception exception) {

                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                });
            }
        });
    }

   /* GenericHandlers genericHandlers=new GenericHandlers() {
        @Override
        public void onSuccess() {

        }

        @Override
        public void onSuccess(JSONObject response) {

        }

        @Override
        public void onFailure(Exception exception) {

        }

        @Override
        public void onFailure(String msg) {

        }
    };

    GenericHandlers genericHandlers2=new GenericHandlers() {
        @Override
        public void onSuccess() {
            finish();
        }

        @Override
        public void onSuccess(JSONObject response) {

        }

        @Override
        public void onFailure(Exception exception) {

        }

        @Override
        public void onFailure(String msg) {

        }
    };*/
}
