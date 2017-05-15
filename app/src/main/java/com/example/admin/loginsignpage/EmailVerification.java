package com.example.admin.loginsignpage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.mobodexter.paasmerandroidcloudsdk.controller.Async;
import com.mobodexter.paasmerandroidcloudsdk.handlers.GenericHandlers;

import org.json.JSONObject;

public class EmailVerification extends AppCompatActivity {

    Async async;
    EditText etCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verification);

        etCode= (EditText) findViewById(R.id.etVerifyCode);
        async=new Async();
        Button button= (Button) findViewById(R.id.btnVefiyCode);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=getIntent().getExtras().getString("email");
                String code=etCode.getText().toString();
               // loginController.verifyEmail(email,code.trim(),genericHandlers);
                async.verifyEmail(email, code.trim(), new GenericHandlers() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onSuccess(JSONObject response) {
                        Intent intent=new Intent(EmailVerification.this,LoginActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Exception exception) {
                        Toast.makeText(getApplicationContext(),"not verified",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(String msg) {
                        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
                    }
                });

            }
        });
    }
    /*GenericHandlers genericHandlers=new GenericHandlers() {
        @Override
        public void onSuccess() {
            Toast.makeText(getApplicationContext(),"verified",Toast.LENGTH_LONG).show();
            Intent intent=new Intent(EmailVerification.this,LoginActivity.class);
            startActivity(intent);
        }

        @Override
        public void onSuccess(JSONObject response) {
            Toast.makeText(getApplicationContext(),"verified",Toast.LENGTH_LONG).show();
            Intent intent=new Intent(EmailVerification.this,LoginActivity.class);
            startActivity(intent);
        }

        @Override
        public void onFailure(Exception exception) {
            Toast.makeText(getApplicationContext(),"not verified",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onFailure(String msg) {
            Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
        }
    };*/
}
