package com.example.admin.loginsignpage;

/**
 * Created by Arun on 04-12-2017.
 */

public class FeedListModel {

    private String id,name,type,pin,status,protocol,deviceId;

    public FeedListModel(String id,String name,String type,String pin,String status,String protocol,String deivceId){
        this.id=id;
        this.name=name;
        this.type=type;
        this.pin=pin;
        this.status=status;
        this.protocol=protocol;
        this.deviceId=deivceId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getPin() {
        return pin;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
