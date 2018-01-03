package com.example.admin.loginsignpage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
    private List<DeviceListModel> deviceList=new ArrayList<>();
    private RecyclerView recyclerView;
    private DeviceListAdapter adapter;

    Async async;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        email = SharedPrefManager.getInstance(this).getAccessToken();
        Log.d("Token:",email);
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

        recyclerView= (RecyclerView) findViewById(R.id.recycler_view);
        adapter=new DeviceListAdapter(deviceList);
        RecyclerView.LayoutManager mLayoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                DeviceListModel model=deviceList.get(position);
                Toast.makeText(getApplicationContext(),model.getName()+","+model.getId(),Toast.LENGTH_LONG).show();

                Intent intent=new Intent(ProfileActivity.this,FeedList.class);
                intent.putExtra("id",model.getId());
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        prepareDeviceData();
        //   loginController.getDevices(email,genericHandlers);



    }

    private void prepareDeviceData() {
        async.getDevices(email, new GenericHandlers() {
            @Override
            public void onSuccess() {
                Log.d("Login","login");
            }

            @Override
            public void onSuccess(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");

                    List<String> listres = new ArrayList<>();
                    int size = jsonArray.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        listres.add(jsonObject.getString("name"));
                        DeviceListModel model=new DeviceListModel(jsonObject.getString("name"),jsonObject.getString("id"));
                        deviceList.add(model);

                    }
                    adapter.notifyDataSetChanged();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(JSONArray response) {
                try {

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
              Intent intent=new Intent(ProfileActivity.this,AddDevice.class);
              startActivity(intent);
                Toast.makeText(getApplicationContext(),"Setting Clicked",Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }


}
