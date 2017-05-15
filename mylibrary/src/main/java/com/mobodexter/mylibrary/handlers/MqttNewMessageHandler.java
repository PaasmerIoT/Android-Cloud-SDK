package com.mobodexter.mylibrary.handlers;

/**
 * Created by Arun on 26-04-2017.
 */

public interface MqttNewMessageHandler {

    public void onNewMessage(String message);
}
