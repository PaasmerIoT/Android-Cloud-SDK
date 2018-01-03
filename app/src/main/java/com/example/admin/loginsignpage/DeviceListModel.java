package com.example.admin.loginsignpage;

/**
 * Created by Arun on 01-12-2017.
 */

public class DeviceListModel {

    private String name;
    private String id;

    public DeviceListModel(){

    }

    public DeviceListModel(String name,String id){
        this.name=name;
        this.id=id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
