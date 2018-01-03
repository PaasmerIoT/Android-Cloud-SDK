package com.example.admin.loginsignpage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.mobodexter.paasmerandroidcloudsdk.controller.Async;
import com.mobodexter.paasmerandroidcloudsdk.handlers.GenericHandlers;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FeedList extends AppCompatActivity {

    private String token;
    private RecyclerView recyclerView;
    private FeedListAdapter feedListAdapter;
    private List<FeedListModel> listModels=new ArrayList<>();
    private String deviceId;
    Async async;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_list);

        async=new Async();
        token=SharedPrefManager.getInstance(getApplicationContext()).getAccessToken();
        deviceId=getIntent().getExtras().getString("id");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Feeds List Paasmer");

        recyclerView= (RecyclerView) findViewById(R.id.recycler_view_feed);
        feedListAdapter=new FeedListAdapter(listModels,this);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(feedListAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                FeedListModel model=listModels.get(position);
                if (model.getType().equals("sensor")){
                    Intent intent=new Intent(FeedList.this,BarChartActivity.class);
                    intent.putExtra("id",model.getId());
                    intent.putExtra("deviceId",model.getDeviceId());
                    startActivity(intent);
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        prepareFeedsData();
    }

    private void prepareFeedsData() {
        async.deviceFeed(token, deviceId, new GenericHandlers() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onSuccess(JSONObject response) {
                Log.d("REsponse:",response.toString());
                try {
                    JSONArray jsonArray = response.getJSONArray("data");

                    int size = jsonArray.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        FeedListModel model=new FeedListModel(jsonObject.getString("id"),jsonObject.getString("name"),jsonObject.getString("type"),jsonObject.getString("pin"),jsonObject.getString("status"),jsonObject.getString("protocol"),jsonObject.getString("deviceId"));
                        listModels.add(model);

                    }
                    feedListAdapter.notifyDataSetChanged();

                }catch (Exception e){
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
}
