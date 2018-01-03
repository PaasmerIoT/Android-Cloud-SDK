package com.example.admin.loginsignpage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.mobodexter.paasmerandroidcloudsdk.controller.Async;
import com.mobodexter.paasmerandroidcloudsdk.handlers.GenericHandlers;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Arun on 04-12-2017.
 */

public class FeedListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<FeedListModel> listModels;
    private final int sensor=0;
    private final int actuator=1;
    Async async;
    private Context context;
    public FeedListAdapter(List<FeedListModel> listModels,Context context)
    {
        this.listModels=listModels;
        async=new Async();
        this.context=context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("viewType",viewType+"");
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        switch (viewType){
            case sensor:
                Log.d("sensor",sensor+"");
                View view1=inflater.inflate(R.layout.layout_sensor,parent,false);
                viewHolder=new ViewHolderSensor(view1);
                break;
            case actuator:
                Log.d("actuator",actuator+"");
                View view2=inflater.inflate(R.layout.layout_actuator,parent,false);
                viewHolder=new ViewHolderActuator(view2);
                break;

        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final FeedListModel model=listModels.get(position);
        switch (holder.getItemViewType()){
            case sensor:
                ViewHolderSensor viewHolderSensor= (ViewHolderSensor) holder;
                viewHolderSensor.txtName.setText(model.getName());
                Log.d("ViewHolder",model.getName());
                break;
            case actuator:
                final ViewHolderActuator viewHolderActuator= (ViewHolderActuator) holder;
                viewHolderActuator.txtName.setText(model.getName());
                viewHolderActuator.aSwitch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (viewHolderActuator.aSwitch.isChecked()){
                          String token=SharedPrefManager.getInstance(context).getAccessToken();
                          async.editFeed(token, model.getId(), model.getDeviceId(), model.getName(), model.getType(), model.getPin(), null, "on", model.getProtocol(), new GenericHandlers() {
                              @Override
                              public void onSuccess() {

                              }

                              @Override
                              public void onSuccess(JSONObject response) {

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

                        }else{
                            String token=SharedPrefManager.getInstance(context).getAccessToken();
                            async.editFeed(token, model.getId(), model.getDeviceId(), model.getName(), model.getType(), model.getPin(), null, "off", model.getProtocol(), new GenericHandlers() {
                                @Override
                                public void onSuccess() {

                                }

                                @Override
                                public void onSuccess(JSONObject response) {

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
                });
        }
    }

    @Override
    public int getItemViewType(int position) {
        FeedListModel model=listModels.get(position);
        if (model.getType().equals("sensor")){
            return sensor;
        }else {
            return actuator;
        }
    }

    @Override
    public int getItemCount() {
        return listModels.size();
    }


    public class ViewHolderSensor extends RecyclerView.ViewHolder{
        private TextView txtName;
        public ViewHolderSensor(View view){
            super(view);
            txtName= (TextView) view.findViewById(R.id.txtSensorName);
        }
    }

    public class ViewHolderActuator extends RecyclerView.ViewHolder{
        private TextView txtName;
        private Switch aSwitch;

        public ViewHolderActuator(View view){
            super(view);
            txtName= (TextView) view.findViewById(R.id.txtActuatorName);
            aSwitch= (Switch) view.findViewById(R.id.swtOnOff);
        }
    }
}
