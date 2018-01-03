package com.example.admin.loginsignpage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Arun on 04-12-2017.
 */

public class DeviceListAdapter extends RecyclerView.Adapter<DeviceListAdapter.MyViewHolder> {

    private List<DeviceListModel> deviceList;
    private Context _context;

    public DeviceListAdapter(List<DeviceListModel> list){
        this.deviceList=list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.device_layout_listview,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
            DeviceListModel listModel=deviceList.get(position);
            holder.txtName.setText(listModel.getName());
    }

    @Override
    public int getItemCount() {
        return deviceList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView txtName;
        public MyViewHolder(View view){
            super(view);
            txtName= (TextView) view.findViewById(R.id.label);
        }
    }


}
