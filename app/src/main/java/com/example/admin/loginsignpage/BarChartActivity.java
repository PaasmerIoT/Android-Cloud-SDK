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
    private String id,deviceId;
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

        final BarChart[] chart = init();
        email = SharedPrefManager.getInstance(this).getAccessToken();
       id=getIntent().getExtras().getString("id");
       deviceId=getIntent().getExtras().getString("deviceId");
        /*Toast.makeText(getApplicationContext(),devicename,Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(),email,Toast.LENGTH_SHORT).show();*/


        getChart(chart, deviceId,id);

    }



    private void getChart(final BarChart[] chart, final String devicename,final String id) {

        try{

            async.getFeedData(email, devicename,id, new GenericHandlers() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onSuccess(JSONObject response) {
                        String aResponse=response.toString();
                  //  Toast.makeText(getApplicationContext(),aResponse,Toast.LENGTH_LONG).show();
                    try {
                        if (aResponse != null || !aResponse.trim().isEmpty()) {

                            deviceDetails = new JSONObject(aResponse);
                            sensorData = response.getJSONArray("data");
                            ArrayList<BarEntry> yVals1=new ArrayList<BarEntry>();

                            //controlData = deviceDetails.getJSONArray(CONTROL_DATA);
                            if (sensorData != null) {
                                BarData data = null;
                                for (int i = 0; i < sensorData.length(); i++) {
                                    try {
                                        JSONObject jsonObject = sensorData.getJSONObject(i);
                                        List<String> xValues = getXAxisValues(jsonObject);
                                        data = new BarData(xValues, getDataSet(jsonObject, xValues, ""));
                                        chart[0].setData(data);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                             /*chart[0].animateXY(1000, 2000);
                             chart[1].animateXY(1000, 2000);
                             chart[2].animateXY(1000, 2000);
                             chart[3].animateXY(1000, 2000);*/

                                    chart[0].invalidate();
                                    //chart[1].invalidate();
                                    //chart[2].invalidate();
                                    //chart[3].invalidate();
                                }


                            }
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

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @NonNull
    private BarChart[] init() {
        final BarChart[] chart = new BarChart[4];
        chart[0] = (BarChart) findViewById(R.id.chart);

        return chart;
    }

    private List<BarDataSet> getDataSet(JSONObject jsonGraph, List<String> xValues, String sensor) {
        List<BarEntry> barEntries = new ArrayList<>();
        for (int i = 0; i < xValues.size(); i++) {
            try {
                float val = Float.parseFloat(jsonGraph.getString("value"));
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

    private List<String> getXAxisValues(JSONObject jsonObject) throws JSONException {
        Iterator keysToCopyIterator = jsonObject.keys();
        List<String> keysList = new ArrayList<String>();
        String time=jsonObject.getString("time");
       keysList.add(time);
        // while (keysToCopyIterator.hasNext()) {
         //   String key = (String) keysToCopyIterator.next();
          //  keysList.add(key);
        //}
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
        public void onSuccess(JSONArray response) {

        }

        @Override
        public void onFailure(Exception exception) {

        }

        @Override
        public void onFailure(String msg) {

        }
    };
}