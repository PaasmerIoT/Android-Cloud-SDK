package com.example.admin.loginsignpage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.mobodexter.paasmerandroidcloudsdk.controller.Async;
import com.mobodexter.paasmerandroidcloudsdk.handlers.GenericHandlers;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AddFeeds extends AppCompatActivity {
    private EditText etName;
    private EditText etPin;
    private Spinner spType,spProtocol;
    private Button btnCreate;
    Async async;
    private String deviceId=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_feeds);

        etName= (EditText) findViewById(R.id.etFeedName);
        etPin= (EditText) findViewById(R.id.etFeedPin);
        spType= (Spinner) findViewById(R.id.spFeedType);
        spProtocol= (Spinner) findViewById(R.id.spProtocol);
        btnCreate= (Button) findViewById(R.id.btnFeedCreate);
        async=new Async();
        deviceId= getIntent().getExtras().getString("id");
        final List<String> type=new ArrayList<>();
        type.add("sensor");
        type.add("actuator");

        ArrayAdapter<String> typeAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,type);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spType.setAdapter(typeAdapter);

        List<String> protocol=new ArrayList<>();
        protocol.add("GPIO");
        protocol.add("zigbee");

        ArrayAdapter<String> protocoalAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,protocol);
        protocoalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spProtocol.setAdapter(protocoalAdapter);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String token=SharedPrefManager.getInstance(getApplicationContext()).getAccessToken();
                String name=etName.getText().toString();
                String pin=etPin.getText().toString();
                String sType=spType.getSelectedItem().toString();
                String sProtocol=spProtocol.getSelectedItem().toString();

                async.createFeed(token, deviceId, name, sType, pin, "", "false", sProtocol, new GenericHandlers() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onSuccess(JSONObject response) {
                        Log.d("Feeds inster",response.toString());
                    }

                    @Override
                    public void onSuccess(JSONArray response) {

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
}
