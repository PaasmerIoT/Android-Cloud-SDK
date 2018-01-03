package com.example.admin.loginsignpage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.mobodexter.paasmerandroidcloudsdk.controller.Async;
import com.mobodexter.paasmerandroidcloudsdk.handlers.GenericHandlers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AddDevice extends AppCompatActivity {

    private EditText etName;
    private Spinner spSDK,spType;
    private CheckBox wifi,bluetooth;
    private Button btnCreate;
    Async async;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);

        etName= (EditText) findViewById(R.id.etName);
        spSDK= (Spinner) findViewById(R.id.spSDK);
        spType= (Spinner) findViewById(R.id.spType);
        wifi= (CheckBox) findViewById(R.id.checkBox);
        bluetooth= (CheckBox) findViewById(R.id.checkBox2);
        btnCreate= (Button) findViewById(R.id.button);
        async=new Async();
        List<String> sdk=new ArrayList<String>();
        sdk.add("C");
        sdk.add("Python");
        sdk.add("Java");

        ArrayAdapter<String> sdkAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,sdk);
        sdkAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSDK.setAdapter(sdkAdapter);

        List<String> type=new ArrayList<String>();
        type.add("CC3200");
        type.add("SBC");

        ArrayAdapter<String> typeAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,type);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spType.setAdapter(typeAdapter);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=etName.getText().toString();
                String sdk=spSDK.getSelectedItem().toString();
                String type=spType.getSelectedItem().toString();
                String iswifi="";
                String isbluetooth="";
                String token=SharedPrefManager.getInstance(getApplicationContext()).getAccessToken();
                if (wifi.isChecked()){
                    iswifi="wifi";
                }
                if (bluetooth.isChecked()){
                    isbluetooth="bluetooth";
                }

                async.createDevice(token, name, sdk, type, isbluetooth, iswifi, new GenericHandlers() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onSuccess(JSONObject response) {

                        try {
                            if(response.getBoolean("success")){
                                JSONObject jsonObject=response.getJSONObject("data");
                                String id=jsonObject.getString("id");
                                Intent intent=new Intent(AddDevice.this,AddFeeds.class);
                                intent.putExtra("id",id);
                                startActivity(intent);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
