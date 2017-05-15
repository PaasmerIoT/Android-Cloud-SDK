package com.example.admin.loginsignpage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.widget.ListView;



import com.mobodexter.paasmerandroidcloudsdk.controller.Async;
import com.mobodexter.paasmerandroidcloudsdk.handlers.GenericHandlers;


import org.json.JSONArray;

import org.json.JSONObject;


import java.util.ArrayList;

import java.util.List;


public class ProfileActivity extends AppCompatActivity {


    private static final String TAG = ProfileActivity.class.getSimpleName();
    private String email="";
    private String DeviceNames="";

    Async async;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        email = SharedPrefManager.getInstance(this).getUserEmail();

        async=new Async();
        // mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
//        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Devices List Paasmer");
        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }
     //   loginController.getDevices(email,genericHandlers);
        async.getDevices(email, new GenericHandlers() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onSuccess(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("Devicenames");

                    List<String> listres = new ArrayList<>();
                    int size = jsonArray.length();
                    for (int i = 0; i < size; i++) {
                        listres.add(jsonArray.get(i).toString());
                    }
                    Log.d(TAG, String.valueOf(response));
                    ListView devicelistView = (ListView) findViewById(R.id.device_list);
                    ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(),
                            R.layout.device_layout_listview, listres);
                    devicelistView.setAdapter(adapter);
                    devicelistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String DeviceNames = (String) parent.getItemAtPosition(position);
                            Intent intent = new Intent(getApplicationContext(), BarChartActivity.class);
                            intent.putExtra("devicename", DeviceNames);
                            startActivity(intent);
                        }
                    });
                }catch (Exception e){
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
     /*   StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_DEVICE_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("Devicenames");
                            List<String> listres=new ArrayList<>();
                            int size = jsonArray.length();
                            for(int i = 0; i< size ; i++){
                                listres.add(jsonArray.get(i).toString());
                            }
                            Log.d(TAG, String.valueOf(jsonObject));
                            ListView devicelistView = (ListView) findViewById(R.id.device_list);
                            ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(),
                                    R.layout.device_layout_listview,listres);
                            devicelistView.setAdapter(adapter);
                            devicelistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    String DeviceNames= (String) parent.getItemAtPosition(position);
                              Intent intent=  new Intent(getApplicationContext(),BarChartActivity.class);
                                    intent.putExtra("devicename",DeviceNames);
                                    startActivity(intent);


                       /*           String name = (String) parent.getItemAtPosition(position);
                                    DeviceNames=name;
                                    Toast.makeText(getApplicationContext(),name,Toast.LENGTH_SHORT).show();
                                    StringRequest stringRequest=new StringRequest(Request.Method.POST,
                                            Constants.URL_DEVICE_DATA, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {

                                            try {
                                                JSONObject jsonObject = new JSONObject(response);
                                                JSONArray jsonArray = jsonObject.getJSONArray("SensorData");
//







 //
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }


                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {

                                        }
                                    }){

                                        protected Map<String, String> getParams() throws AuthFailureError {
                                            Map<String, String> params = new HashMap<>();
                                            params.put("email", email);
                                            params.put("Devicenames", DeviceNames);
                                            return params;
                                        }


                                    };

                                    RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);*/

/*
                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "device not found", Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menuLogout:
                 SharedPrefManager.getInstance(this).logout();
                finish();
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                    break;

            case R.id.menuSetting:
                Toast.makeText(getApplicationContext(),"Setting Clicked",Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    GenericHandlers genericHandlers=new GenericHandlers() {
        @Override
        public void onSuccess() {

        }

        @Override
        public void onSuccess(JSONObject response) {
          //  Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
           try {
               JSONArray jsonArray = response.getJSONArray("Devicenames");

               List<String> listres = new ArrayList<>();
               int size = jsonArray.length();
               for (int i = 0; i < size; i++) {
                   listres.add(jsonArray.get(i).toString());
               }
               Log.d(TAG, String.valueOf(response));
               ListView devicelistView = (ListView) findViewById(R.id.device_list);
               ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(),
                       R.layout.device_layout_listview, listres);
               devicelistView.setAdapter(adapter);
               devicelistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                   @Override
                   public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                       String DeviceNames = (String) parent.getItemAtPosition(position);
                       Intent intent = new Intent(getApplicationContext(), BarChartActivity.class);
                       intent.putExtra("devicename", DeviceNames);
                       startActivity(intent);
                   }
               });
           }catch (Exception e){
               e.printStackTrace();
           }
        }

        @Override
        public void onFailure(Exception exception) {

        }

        @Override
        public void onFailure(String msg) {

        }
    };
}
