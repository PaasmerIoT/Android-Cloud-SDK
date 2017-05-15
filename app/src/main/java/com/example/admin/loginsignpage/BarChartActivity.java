package com.example.admin.loginsignpage;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

//import com.mobodexter.mylibrary.handlers.GenericHandlers;
import com.mobodexter.paasmerandroidcloudsdk.controller.Async;
import com.mobodexter.paasmerandroidcloudsdk.handlers.GenericHandlers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BarChartActivity extends ActionBarActivity {
    private String email = "";
    private static final String SENSOR_DATA = "SensorData";
    private static final String CONTROL_DATA = "ControlData";
    private JSONArray sensorData = null;
    private JSONArray controlData = null;
    private JSONObject deviceDetails = null;
    ToggleButton btControlfeed1, btControlfeed2;

    Async async;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_item_barchart);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Device Sensor data");
        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }

        async=new Async();
        btControlfeed1 = (ToggleButton) findViewById(R.id.btcontrolfeed1);
        btControlfeed2 = (ToggleButton) findViewById(R.id.btcontrolfeed2);
        final BarChart[] chart = init();
        email = SharedPrefManager.getInstance(this).getUserEmail();
        final String devicename = getIntent().getExtras().getString("devicename");
        /*Toast.makeText(getApplicationContext(),devicename,Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(),email,Toast.LENGTH_SHORT).show();*/


        getChart(chart, devicename);
        getToggle(devicename);

    }

    private void getToggle(final String devicename) {
        btControlfeed1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                try {
                   // loginController.deviceControl(email,devicename,controlData.getJSONObject(0).get("control").toString(),controlData.getJSONObject(0).get("status").toString(),genericHandlers);
                    async.deviceControl(email, devicename, controlData.getJSONObject(0).get("control").toString(), controlData.getJSONObject(0).get("status").toString(), new GenericHandlers() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onSuccess(JSONObject response) {
                            try {
                                Toast.makeText(getApplicationContext(), response.get("traceId").toString(), Toast.LENGTH_LONG).show();
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

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                /* if (isChecked) {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST,
                            Constants.URL_DEVICE_CONTROL_DATA, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response != null && !response.trim().isEmpty()) {
                                try {
                                    JSONObject json = new JSONObject(response);
                                    Toast.makeText(getApplicationContext(), json.get("traceId").toString(), Toast.LENGTH_LONG).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Internal Error", Toast.LENGTH_LONG).show();
                        }
                    }) {
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("email", email);
                            params.put("devicename", devicename);
                            try {
                                params.put("control", controlData.getJSONObject(0).get("control").toString());
                                params.put("status", controlData.getJSONObject(0).get("status").toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            return params;
                        }

                    };
                    RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

                }*/
            }

        });


        btControlfeed2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                try {
                   // loginController.deviceControl(email,devicename,controlData.getJSONObject(1).get("control").toString(),controlData.getJSONObject(1).get("status").toString(),genericHandlers);
                    async.deviceControl(email, devicename, controlData.getJSONObject(1).get("control").toString(), controlData.getJSONObject(1).get("status").toString(), new GenericHandlers() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onSuccess(JSONObject response) {
                            try {
                                Toast.makeText(getApplicationContext(), response.get("traceId").toString(), Toast.LENGTH_LONG).show();
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
                } catch (JSONException e) {
                    e.printStackTrace();
                }
               /* if (isChecked) {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST,
                            Constants.URL_DEVICE_CONTROL_DATA, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response != null && !response.trim().isEmpty()) {
                                try {
                                    JSONObject json = new JSONObject(response);
                                    Toast.makeText(getApplicationContext(), json.get("traceId").toString(), Toast.LENGTH_LONG).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Internal Error", Toast.LENGTH_LONG).show();
                        }
                    }) {
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("email", email);
                            params.put("devicename", devicename);
                            try {
                                params.put("control", controlData.getJSONObject(1).get("control").toString());
                                params.put("status", controlData.getJSONObject(1).get("status").toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            return params;
                        }

                    };
                    RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

                }*/
            }
        });
    }

    private void getChart(final BarChart[] chart, final String devicename) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_DEVICE_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
             response = "{\"SensorData\":[{\"sensor\":\"feed1\",\"graph\":{\"05:59:53\":\"2\",\"05:59:39\":\"1\",\"05:59:23\":\"3\",\"05:59:09\":\"1\",\"11:17:32\":\"4\"}},{\"sensor\":\"feed2\",\"graph\":{\"05:59:53\":\"0\",\"05:59:39\":\"0\",\"05:59:23\":\"0\",\"05:59:09\":\"0\",\"11:17:32\":\"0\"}},{\"sensor\":\"feed3\",\"graph\":{\"05:59:53\":\"0\",\"05:59:39\":\"0\",\"05:59:23\":\"0\",\"05:59:09\":\"0\",\"11:17:32\":\"0\"}},{\"sensor\":\"analogfeed\",\"graph\":{\"05:59:53\":\"0\",\"05:59:39\":\"0\",\"05:59:23\":\"0\",\"05:59:09\":\"0\",\"11:17:32\":\"1\"}}],\"ControlData\":[{\"control\":\"controlfeed1\",\"status\":\"off\",\"device\":\"esp8266\"},{\"control\":\"controlfeed2\",\"status\":\"on\",\"device\":\"esp8266\"}]}";

                try {
                    if (response != null || !response.trim().isEmpty()) {

                        deviceDetails = new JSONObject(response);
                        sensorData = deviceDetails.getJSONArray(SENSOR_DATA);
                        controlData = deviceDetails.getJSONArray(CONTROL_DATA);
                        if (sensorData != null && controlData != null) {
                            BarData data = null;
                            for (int i = 0; i < 4; i++) {
                                try {
                                    JSONObject jsonObject = sensorData.getJSONObject(i);
                                    JSONObject jsonGraph = jsonObject.getJSONObject("graph");
                                    List<String> xValues = getXAxisValues(jsonGraph);

                                    data = new BarData(xValues, getDataSet(jsonGraph, xValues, jsonObject.get("sensor").toString()));
                                    chart[i].setData(data);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                             /*chart[0].animateXY(1000, 2000);
                             chart[1].animateXY(1000, 2000);
                             chart[2].animateXY(1000, 2000);
                             chart[3].animateXY(1000, 2000);*/

                                chart[0].invalidate();
                                chart[1].invalidate();
                                chart[2].invalidate();
                                chart[3].invalidate();
                            }
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Internal Error", Toast.LENGTH_LONG).show();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("devicename", devicename);
                return params;
            }


        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    @NonNull
    private BarChart[] init() {
        final BarChart[] chart = new BarChart[4];
        chart[0] = (BarChart) findViewById(R.id.chart);
        chart[1] = (BarChart) findViewById(R.id.chart1);
        chart[2] = (BarChart) findViewById(R.id.chart2);
        chart[3] = (BarChart) findViewById(R.id.chart3);
        return chart;
    }

    private List<BarDataSet> getDataSet(JSONObject jsonGraph, List<String> xValues, String sensor) {
        List<BarEntry> barEntries = new ArrayList<>();
        for (int i = 0; i < xValues.size(); i++) {
            try {
                float val = Float.parseFloat(jsonGraph.get(xValues.get(i)).toString());
                BarEntry barEntry = new BarEntry(val, i);
                barEntries.add(barEntry);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        List<BarDataSet> dataSets = null;


        BarDataSet barDataSet1 = new BarDataSet(barEntries, sensor);
        barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);


        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);

        return dataSets;
    }

    private List<String> getXAxisValues(JSONObject jsonObject) {
        Iterator keysToCopyIterator = jsonObject.keys();
        List<String> keysList = new ArrayList<String>();
        while (keysToCopyIterator.hasNext()) {
            String key = (String) keysToCopyIterator.next();
            keysList.add(key);
        }
        return keysList;
    }



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
            try {
                Toast.makeText(getApplicationContext(), response.get("traceId").toString(), Toast.LENGTH_LONG).show();
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
    };
}