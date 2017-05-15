package com.mobodexter.mylibrary.handlers;

import org.json.JSONObject;

/**
 * Created by Arun on 19-04-2017.
 */

public interface GenericHandlers {

    public void onSuccess();
    public void onSuccess(JSONObject response);

    public void onFailure(Exception exception);
    public void onFailure(String msg);
}
