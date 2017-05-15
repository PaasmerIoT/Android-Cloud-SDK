package com.mobodexter.mylibrary.controllers;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;


import com.mobodexter.mylibrary.handlers.GenericHandlers;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arun on 19-04-2017.
 */

public class LoginController {

    private String username;
    private String password;
    private String encryptedPass;
    private JsonParser jsonParser;

    public LoginController(){
        jsonParser=new JsonParser();
    }
    public LoginController(String username,String password){
        this.password=password;
        this.username=username;
        jsonParser=new JsonParser();


    }



    public void userLogin(final String uname, final String pwd, final GenericHandlers callback) throws JSONException {

        //callback.onSuccess(new JSONObject());
        Thread background=new Thread(new Runnable() {
            String tag_json_obj = "json_obj_req";
            String url = "http://ec2-52-41-46-86.us-west-2.compute.amazonaws.com/myloginval.php";
            @Override
            public void run() {
                try{
                    List<NameValuePair> parameters=new ArrayList<NameValuePair>();

                    parameters.add(new BasicNameValuePair("email",uname));
                    parameters.add(new BasicNameValuePair("password",pwd));
                    Log.d("LoginValues",uname+","+pwd);
                    //parameters.add(new BasicNameValuePair("deviceid", params[0]));

                    JSONObject jsonObject = jsonParser.makeHttpRequest("http://ec2-52-41-46-86.us-west-2.compute.amazonaws.com/paasmer/loginvalidation.php", "POST", parameters);

                    threadMsg(jsonObject.toString());

                    // return jsonObject+"";

                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            private void threadMsg(String msg){
                if (!msg.equals(null) && !msg.equals("")){
                    Message msgObj=handler.obtainMessage();
                    Bundle b=new Bundle();
                    b.putString("message",msg);
                    msgObj.setData(b);
                    handler.sendMessage(msgObj);
                }
            }

            private final Handler handler=new Handler(){
                  public void handleMessage(Message msg){
                      String aResponse=msg.getData().getString("message");
                      if ((null!=aResponse)){
                          Log.d("Login",aResponse);
                          try {
                              JSONObject jsonObject=new JSONObject(aResponse);
                              int i=jsonObject.getInt("success");

                              if (i==0){
                                  Log.d("Loginfa",i+",");
                                  callback.onFailure("Login failed");
                              }else {
                                  Log.d("Loginsu",i+",");
                                  callback.onSuccess(jsonObject);
                              }
                          }catch (Exception e){
                              e.printStackTrace();
                              callback.onFailure(e);
                          }
                      }else {
                          Log.d("not logins","not got response");
                      }
                  }
            };
        });
        background.start();
    }

    public void userSignup(final String username, final String password, final String email, final String mobile, final String cpass, final String country, final String state, final String purpose, final GenericHandlers callback){
        Thread background=new Thread(new Runnable() {
            String tag_json_obj = "json_obj_req";
            String url = "http://ec2-52-41-46-86.us-west-2.compute.amazonaws.com/myloginval.php";
            @Override
            public void run() {
                try{
                    List<NameValuePair> parameters=new ArrayList<NameValuePair>();

                    parameters.add(new BasicNameValuePair("username",username));
                    parameters.add(new BasicNameValuePair("password",password));
                    parameters.add(new BasicNameValuePair("email",email));
                    parameters.add(new BasicNameValuePair("mobile",mobile));
                    parameters.add(new BasicNameValuePair("cpass",cpass));
                    parameters.add(new BasicNameValuePair("country",country));
                    parameters.add(new BasicNameValuePair("state",state));
                    parameters.add(new BasicNameValuePair("purpose",purpose));
                    //parameters.add(new BasicNameValuePair("deviceid", params[0]));

                    JSONObject jsonObject = jsonParser.makeHttpRequest("http://ec2-52-41-46-86.us-west-2.compute.amazonaws.com/paasmer/userregistration.php", "POST", parameters);
                    threadMsg(jsonObject.toString());


                    // return jsonObject+"";

                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            private void threadMsg(String msg){
                if (!msg.equals(null) && !msg.equals("")){
                    Message msgObj=handler.obtainMessage();
                    Bundle b=new Bundle();
                    b.putString("message",msg);
                    msgObj.setData(b);
                    handler.sendMessage(msgObj);
                }
            }

            private final Handler handler=new Handler(){
                public void handleMessage(Message msg){
                    String aResponse=msg.getData().getString("message");
                    if ((null!=aResponse)){
                        Log.d("Login",aResponse);
                        try {
                            JSONObject jsonObject=new JSONObject(aResponse);
                            int i=jsonObject.getInt("success");

                            if (i==0){
                                Log.d("Loginfa",i+",");
                                callback.onFailure(jsonObject.getString("message"));
                            }else {
                                Log.d("Loginsu",i+",");
                                callback.onSuccess(jsonObject);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            callback.onFailure(e);
                        }
                    }else {
                        Log.d("not logins","not got response");
                    }
                }
            };
        });
        background.start();
    }

    public void deviceFeed(final String email, final String deviceName, final GenericHandlers callback){
        Thread background=new Thread(new Runnable() {
            String tag_json_obj = "json_obj_req";
            String url = "http://ec2-52-41-46-86.us-west-2.compute.amazonaws.com/myloginval.php";
            @Override
            public void run() {
                try{
                    List<NameValuePair> parameters=new ArrayList<NameValuePair>();

                    parameters.add(new BasicNameValuePair("devicename",deviceName));

                    parameters.add(new BasicNameValuePair("email",email));

                    //parameters.add(new BasicNameValuePair("deviceid", params[0]));

                    JSONObject jsonObject = jsonParser.makeHttpRequest("http://ec2-52-41-46-86.us-west-2.compute.amazonaws.com/paasmer/devicessensordata1.php", "POST", parameters);
                    threadMsg(jsonObject.toString());


                    // return jsonObject+"";

                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            private void threadMsg(String msg){
                if (!msg.equals(null) && !msg.equals("")){
                    Message msgObj=handler.obtainMessage();
                    Bundle b=new Bundle();
                    b.putString("message",msg);
                    msgObj.setData(b);
                    handler.sendMessage(msgObj);
                }
            }

            private final Handler handler=new Handler(){
                public void handleMessage(Message msg){
                    String aResponse=msg.getData().getString("message");
                    if ((null!=aResponse)){
                        callback.onSuccess();
                        Log.d("Login",aResponse);
                    }else {
                        callback.onFailure("error in feeds");
                        Log.d("not logins","not got response");
                    }
                }
            };
        });
        background.start();
    }


    public void getDevices(final String email, final GenericHandlers callback){
        Thread background=new Thread(new Runnable() {
            String tag_json_obj = "json_obj_req";
            String url = "http://ec2-52-41-46-86.us-west-2.compute.amazonaws.com/myloginval.php";
            @Override
            public void run() {
                try{
                    List<NameValuePair> parameters=new ArrayList<NameValuePair>();



                    parameters.add(new BasicNameValuePair("email",email));

                    //parameters.add(new BasicNameValuePair("deviceid", params[0]));

                    JSONObject jsonObject = jsonParser.makeHttpRequest("http://ec2-52-41-46-86.us-west-2.compute.amazonaws.com/paasmer/deviceinfo.php", "POST", parameters);
                    threadMsg(jsonObject.toString());


                    // return jsonObject+"";

                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            private void threadMsg(String msg){
                if (!msg.equals(null) && !msg.equals("")){
                    Message msgObj=handler.obtainMessage();
                    Bundle b=new Bundle();
                    b.putString("message",msg);
                    msgObj.setData(b);
                    handler.sendMessage(msgObj);
                }
            }

            private final Handler handler=new Handler(){
                public void handleMessage(Message msg){
                    String aResponse=msg.getData().getString("message");
                    if ((null!=aResponse)){

                        try {
                            JSONObject jsonObject=new JSONObject(aResponse);
                            callback.onSuccess(jsonObject);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("Login",aResponse);
                    }else {
                        callback.onFailure("error in feeds");
                        Log.d("not logins","not got response");
                    }
                }
            };
        });
        background.start();
    }

    public void verifyEmail(final String email,final String code, final GenericHandlers callback){
        Thread background=new Thread(new Runnable() {
            String tag_json_obj = "json_obj_req";
            String url = "http://ec2-52-41-46-86.us-west-2.compute.amazonaws.com/myloginval.php";
            @Override
            public void run() {
                try{
                    List<NameValuePair> parameters=new ArrayList<NameValuePair>();



                    parameters.add(new BasicNameValuePair("email",email));
                    parameters.add(new BasicNameValuePair("code",code));
                    //parameters.add(new BasicNameValuePair("deviceid", params[0]));

                    JSONObject jsonObject = jsonParser.makeHttpRequest("http://ec2-52-41-46-86.us-west-2.compute.amazonaws.com/paasmer/emailverifyforsdk.php", "POST", parameters);
                    threadMsg(jsonObject.toString());


                    // return jsonObject+"";

                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            private void threadMsg(String msg){
                if (!msg.equals(null) && !msg.equals("")){
                    Message msgObj=handler.obtainMessage();
                    Bundle b=new Bundle();
                    b.putString("message",msg);
                    msgObj.setData(b);
                    handler.sendMessage(msgObj);
                }
            }

            private final Handler handler=new Handler(){
                public void handleMessage(Message msg){
                    String aResponse=msg.getData().getString("message");
                    if ((null!=aResponse)){
                        Log.d("Login",aResponse);
                        try {
                            JSONObject jsonObject=new JSONObject(aResponse);
                            int i=jsonObject.getInt("success");

                            if (i==0){
                                Log.d("Loginfa",i+",");
                                callback.onFailure("verification failed");
                            }else {
                                Log.d("Loginsu",i+",");
                                callback.onSuccess(jsonObject);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            callback.onFailure(e);
                        }
                    }else {
                        Log.d("not logins","not got response");
                    }
                }
            };
        });
        background.start();
    }


    public void deviceControl(final String email,final String devicename,final String control,final String status, final GenericHandlers callback){
        Thread background=new Thread(new Runnable() {
            String tag_json_obj = "json_obj_req";
            String url = "http://ec2-52-41-46-86.us-west-2.compute.amazonaws.com/myloginval.php";
            @Override
            public void run() {
                try{
                    List<NameValuePair> parameters=new ArrayList<NameValuePair>();



                    parameters.add(new BasicNameValuePair("email",email));
                    parameters.add(new BasicNameValuePair("devicename",devicename));
                    parameters.add(new BasicNameValuePair("control",control));
                    parameters.add(new BasicNameValuePair("status",status));
                    //parameters.add(new BasicNameValuePair("deviceid", params[0]));

                    JSONObject jsonObject = jsonParser.makeHttpRequest("http://ec2-52-41-46-86.us-west-2.compute.amazonaws.com/paasmer/feedcontrol.php", "POST", parameters);
                    threadMsg(jsonObject.toString());


                    // return jsonObject+"";

                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            private void threadMsg(String msg){
                if (!msg.equals(null) && !msg.equals("")){
                    Message msgObj=handler.obtainMessage();
                    Bundle b=new Bundle();
                    b.putString("message",msg);
                    msgObj.setData(b);
                    handler.sendMessage(msgObj);
                }
            }

            private final Handler handler=new Handler(){
                public void handleMessage(Message msg){
                    String aResponse=msg.getData().getString("message");
                    if ((null!=aResponse)){

                        try {
                            JSONObject jsonObject=new JSONObject(aResponse);
                            callback.onSuccess(jsonObject);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("Login",aResponse);
                    }else {
                        callback.onFailure("error in feeds");
                        Log.d("not logins","not got response");
                    }
                }
            };
        });
        background.start();
    }

    public void onForgotPassword(final String email, final GenericHandlers callback){
        Thread background=new Thread(new Runnable() {
            String tag_json_obj = "json_obj_req";
            String url = "http://ec2-52-41-46-86.us-west-2.compute.amazonaws.com/myloginval.php";
            @Override
            public void run() {
                try{
                    List<NameValuePair> parameters=new ArrayList<NameValuePair>();



                    parameters.add(new BasicNameValuePair("email",email));

                    //parameters.add(new BasicNameValuePair("deviceid", params[0]));

                    JSONObject jsonObject = jsonParser.makeHttpRequest("http://ec2-52-41-46-86.us-west-2.compute.amazonaws.com/paasmer/resetpassword.php", "POST", parameters);
                    threadMsg(jsonObject.toString());


                    // return jsonObject+"";

                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            private void threadMsg(String msg){
                if (!msg.equals(null) && !msg.equals("")){
                    Message msgObj=handler.obtainMessage();
                    Bundle b=new Bundle();
                    b.putString("message",msg);
                    msgObj.setData(b);
                    handler.sendMessage(msgObj);
                }
            }

            private final Handler handler=new Handler(){
                public void handleMessage(Message msg){
                    String aResponse=msg.getData().getString("message");
                    if ((null!=aResponse)){

                        try {
                            JSONObject jsonObject=new JSONObject(aResponse);
                            callback.onSuccess(jsonObject);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("Login",aResponse);
                    }else {
                        callback.onFailure("error in feeds");
                        Log.d("not logins","not got response");
                    }
                }
            };
        });
        background.start();
    }

    public void onForgotPasswordVerify(final String email,final String password,final String code,final GenericHandlers callback){
        Thread background=new Thread(new Runnable() {
            String tag_json_obj = "json_obj_req";
            String url = "http://ec2-52-41-46-86.us-west-2.compute.amazonaws.com/myloginval.php";
            @Override
            public void run() {
                try{
                    List<NameValuePair> parameters=new ArrayList<NameValuePair>();



                    parameters.add(new BasicNameValuePair("email",email));
                    parameters.add(new BasicNameValuePair("password",password));
                    parameters.add(new BasicNameValuePair("code",code));
                    //parameters.add(new BasicNameValuePair("deviceid", params[0]));

                    JSONObject jsonObject = jsonParser.makeHttpRequest("http://ec2-52-41-46-86.us-west-2.compute.amazonaws.com/paasmer/verifyuserforgotpassword.php", "POST", parameters);
                    threadMsg(jsonObject.toString());


                    // return jsonObject+"";

                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            private void threadMsg(String msg){
                if (!msg.equals(null) && !msg.equals("")){
                    Message msgObj=handler.obtainMessage();
                    Bundle b=new Bundle();
                    b.putString("message",msg);
                    msgObj.setData(b);
                    handler.sendMessage(msgObj);
                }
            }

            private final Handler handler=new Handler(){
                public void handleMessage(Message msg){
                    String aResponse=msg.getData().getString("message");
                    if ((null!=aResponse)){

                        try {
                            JSONObject jsonObject=new JSONObject(aResponse);
                            callback.onSuccess();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("Login",aResponse);
                    }else {
                        callback.onFailure("Password change failed");
                        Log.d("not logins","not got response");
                    }
                }
            };
        });
        background.start();
    }
}
