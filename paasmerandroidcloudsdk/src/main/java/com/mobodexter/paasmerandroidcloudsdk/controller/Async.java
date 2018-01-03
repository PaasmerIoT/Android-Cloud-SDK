package com.mobodexter.paasmerandroidcloudsdk.controller;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.mobodexter.paasmerandroidcloudsdk.handlers.GenericHandlers;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Arun on 12-05-2017.
 */

public class Async {

    private String username;
    private String password;
    private String encryptedPass;
    private JsonParser jsonParser;

    public Async(){
        jsonParser=new JsonParser();
    }

    public Async(String username,String password){
        this.password=password;
        this.username=username;
        jsonParser=new JsonParser();
    }

    private String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuffer response = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response.toString();
    }

    public  void userLogin(final String uname, final String pwd, final GenericHandlers callback) throws JSONException {

        //callback.onSuccess(new JSONObject());
        Thread background=new Thread(new Runnable() {
            String tag_json_obj = "json_obj_req";
            @Override
            public void run() {
                try{
                    URL url=new URL(ApiURLs.SING_IN);

                    HttpURLConnection urlConnection= (HttpURLConnection) url.openConnection();

                    urlConnection.setDoInput(true);
                    urlConnection.setDoOutput(true);

                    urlConnection.setRequestProperty("Content-Type","application/json");
                    urlConnection.setRequestMethod("POST");
                    urlConnection.connect();
                    JSONObject parameters=new JSONObject();
                    parameters.put("email",uname);
                    parameters.put("password",pwd);

                    Log.d("LoginValues",uname+","+pwd+","+ApiURLs.SING_IN);
                 //   urlConnection.setRequestProperty("Authorization","authString");

                    OutputStreamWriter writer=new OutputStreamWriter(urlConnection.getOutputStream());
                    writer.write(parameters.toString());
                    writer.flush();

                    int statusCode=urlConnection.getResponseCode();
                    Log.d("Status:",statusCode+"");
                    if (statusCode==200){
                        InputStream inputStream=new BufferedInputStream(urlConnection.getInputStream());
                        String response= readStream(inputStream);
                        threadMsg(response);
                    }

                   /* List<NameValuePair> parameters=new ArrayList<NameValuePair>();

                    parameters.add(new BasicNameValuePair("email",uname));
                    parameters.add(new BasicNameValuePair("password",pwd));
                    Log.d("LoginValues",uname+","+pwd);
                    //parameters.add(new BasicNameValuePair("deviceid", params[0]));

                    JSONObject jsonObject = jsonParser.makeHttpRequest(ApiURLs.SING_IN, "POST", parameters);

                    threadMsg(jsonObject.toString());
*/
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
                            boolean i=jsonObject.getBoolean("success");

                            if (i){
                                Log.d("Loginfa",i+",");
                                callback.onSuccess(jsonObject);

                            }else {
                                Log.d("Loginsu",i+",");
                                callback.onFailure("Login failed");
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

    public void userSignup(final String username, final String password, final String email, final GenericHandlers callback){
        Thread background=new Thread(new Runnable() {
            String tag_json_obj = "json_obj_req";

            @Override
            public void run() {
                try{


                    URL url=new URL(ApiURLs.SING_UP);

                    HttpURLConnection urlConnection= (HttpURLConnection) url.openConnection();

                    urlConnection.setDoInput(true);
                    urlConnection.setDoOutput(true);

                    urlConnection.setRequestProperty("Content-Type","application/json");
                    urlConnection.setRequestMethod("POST");
                    urlConnection.connect();
                    JSONObject parameters=new JSONObject();
                    parameters.put("name",username);
                    parameters.put("password",password);
                    parameters.put("email",email);
                    parameters.put("captcha","android");

                 //   Log.d("LoginValues",uname+","+pwd);
                    //   urlConnection.setRequestProperty("Authorization","authString");

                    OutputStreamWriter writer=new OutputStreamWriter(urlConnection.getOutputStream());
                    writer.write(parameters.toString());
                    writer.flush();

                    int statusCode=urlConnection.getResponseCode();
                    if (statusCode==200){
                        InputStream inputStream=new BufferedInputStream(urlConnection.getInputStream());
                        String response= readStream(inputStream);
                        threadMsg(response);
                    }else{
                        Log.d("Status:",statusCode+"");
                    }
                    /*
                    List<NameValuePair> parameters=new ArrayList<NameValuePair>();

                    parameters.add(new BasicNameValuePair("name",username));
                    parameters.add(new BasicNameValuePair("password",password));
                    parameters.add(new BasicNameValuePair("email",email));

                    parameters.add(new BasicNameValuePair("captcha", captcha));

                    JSONObject jsonObject = jsonParser.makeHttpRequest(ApiURLs.SING_UP, "POST", parameters);
                    threadMsg(jsonObject.toString());
                */

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
                            boolean i=jsonObject.getBoolean("success");

                            if (!i){
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

    public void deviceFeed(final String token, final String deviceId, final GenericHandlers callback){
        Thread background=new Thread(new Runnable() {
            String tag_json_obj = "json_obj_req";
            StringBuilder result=new StringBuilder();
            String url = "http://ec2-52-41-46-86.us-west-2.compute.amazonaws.com/myloginval.php";
            @Override
            public void run() {
                try{


                    URL url=new URL(ApiURLs.MANAGE_DEVICES+"/"+deviceId+"/feed");
                    HttpURLConnection urlConnection= (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setRequestProperty("Content-Type","application/json");
                    urlConnection.setRequestProperty("Authorization","Bearer "+token);

                    InputStream in =new BufferedInputStream(urlConnection.getInputStream());
                    BufferedReader reader=new BufferedReader(new InputStreamReader(in));
                    String line;
                    while ((line=reader.readLine())!=null){
                        result.append(line);
                    }
                    threadMsg(result.toString());

                   /* List<NameValuePair> parameters=new ArrayList<NameValuePair>();

                    parameters.add(new BasicNameValuePair("devicename",deviceName));

                    parameters.add(new BasicNameValuePair("email",email));

                    //parameters.add(new BasicNameValuePair("deviceid", params[0]));

                    JSONObject jsonObject = jsonParser.makeHttpRequest("http://ec2-52-41-46-86.us-west-2.compute.amazonaws.com/paasmer/devicessensordata1.php", "POST", parameters);
                    threadMsg(jsonObject.toString());
                    */

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

    public void createFeed(final String token,final String deviceId,final String name,final String type,final String pin,final String pinBase,final String status,final String protocol, final GenericHandlers callback){
        Thread background=new Thread(new Runnable() {
            String tag_json_obj = "json_obj_req";
            String url = "http://ec2-52-41-46-86.us-west-2.compute.amazonaws.com/myloginval.php";
            @Override
            public void run() {
                try{


                    URL url=new URL(ApiURLs.MANAGE_DEVICES+"/"+deviceId+"/feed");

                    HttpURLConnection urlConnection= (HttpURLConnection) url.openConnection();

                    urlConnection.setDoInput(true);
                    urlConnection.setDoOutput(true);

                    urlConnection.setRequestProperty("Content-Type","application/json");
                    urlConnection.setRequestMethod("POST");

                    JSONObject parameters=new JSONObject();

                    parameters.put("name",name);
                    parameters.put("type",type);
                    parameters.put("pin",pin);
                    parameters.put("pinBase",pinBase);
                    parameters.put("protocol",protocol);



                    //   Log.d("LoginValues",uname+","+pwd);
                    urlConnection.setRequestProperty("Authorization"," Bearer "+token);
                    urlConnection.connect();
                    OutputStreamWriter writer=new OutputStreamWriter(urlConnection.getOutputStream());
                    writer.write(parameters.toString());
                    writer.flush();

                    int statusCode=urlConnection.getResponseCode();
                    if (statusCode==200){
                        InputStream inputStream=new BufferedInputStream(urlConnection.getInputStream());
                        String response= readStream(inputStream);
                        threadMsg(response);
                    }
                    /*
                    List<NameValuePair> parameters=new ArrayList<NameValuePair>();
                    parameters.add(new BasicNameValuePair("name",name));
                    parameters.add(new BasicNameValuePair("sdk",sdk));
                    parameters.add(new BasicNameValuePair("type",type));
                    parameters.add(new BasicNameValuePair("bluetooth",bluetooth));
                    parameters.add(new BasicNameValuePair("wifi",wifi));
                    //parameters.add(new BasicNameValuePair("deviceid", params[0]));

                    JSONObject jsonObject = jsonParser.makeHttpRequest("http://ec2-52-41-46-86.us-west-2.compute.amazonaws.com/paasmer/feedcontrol.php", "POST", parameters);
                    threadMsg(jsonObject.toString());*/

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

    public void editFeed(final String token,final String feedId,final String deviceId,final String name,final String type,final String pin,final String pinBase,final String status,final String protocol, final GenericHandlers callback){
        Thread background=new Thread(new Runnable() {
            String tag_json_obj = "json_obj_req";
            String url = "http://ec2-52-41-46-86.us-west-2.compute.amazonaws.com/myloginval.php";
            @Override
            public void run() {
                try{


                    URL url=new URL(ApiURLs.MANAGE_DEVICES+"/"+deviceId+"/feed"+"/"+feedId);

                    HttpURLConnection urlConnection= (HttpURLConnection) url.openConnection();

                    urlConnection.setDoInput(true);
                    urlConnection.setDoOutput(true);

                    urlConnection.setRequestProperty("Content-Type","application/json");
                    urlConnection.setRequestMethod("PUT");

                    JSONObject parameters=new JSONObject();

                    parameters.put("name",name);
                    parameters.put("type",type);
                    parameters.put("pin",pin);
                    parameters.put("pinBase",pinBase);
                    parameters.put("protocol",protocol);



                    //   Log.d("LoginValues",uname+","+pwd);
                    urlConnection.setRequestProperty("Authorization"," Bearer "+token);
                    urlConnection.connect();
                    OutputStreamWriter writer=new OutputStreamWriter(urlConnection.getOutputStream());
                    writer.write(parameters.toString());
                    writer.flush();

                    int statusCode=urlConnection.getResponseCode();
                    if (statusCode==200){
                        InputStream inputStream=new BufferedInputStream(urlConnection.getInputStream());
                        String response= readStream(inputStream);
                        threadMsg(response);
                    }
                    /*
                    List<NameValuePair> parameters=new ArrayList<NameValuePair>();
                    parameters.add(new BasicNameValuePair("name",name));
                    parameters.add(new BasicNameValuePair("sdk",sdk));
                    parameters.add(new BasicNameValuePair("type",type));
                    parameters.add(new BasicNameValuePair("bluetooth",bluetooth));
                    parameters.add(new BasicNameValuePair("wifi",wifi));
                    //parameters.add(new BasicNameValuePair("deviceid", params[0]));

                    JSONObject jsonObject = jsonParser.makeHttpRequest("http://ec2-52-41-46-86.us-west-2.compute.amazonaws.com/paasmer/feedcontrol.php", "POST", parameters);
                    threadMsg(jsonObject.toString());*/

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

    public void deleteFeed(final String token,final String feedId,final String deviceId,final GenericHandlers callback){
        Thread background=new Thread(new Runnable() {
            String tag_json_obj = "json_obj_req";
            String url = "http://ec2-52-41-46-86.us-west-2.compute.amazonaws.com/myloginval.php";
            @Override
            public void run() {
                try{


                    URL url=new URL(ApiURLs.MANAGE_DEVICES+"/"+deviceId+"/feed"+"/"+feedId);

                    HttpURLConnection urlConnection= (HttpURLConnection) url.openConnection();

                    urlConnection.setDoInput(true);
                    urlConnection.setDoOutput(true);

                    urlConnection.setRequestProperty("Content-Type","application/json");
                    urlConnection.setRequestMethod("DELETE");


                    urlConnection.setRequestProperty("Authorization"," Bearer "+token);
                    urlConnection.connect();
                    int statusCode=urlConnection.getResponseCode();
                    if (statusCode==200){
                        InputStream inputStream=new BufferedInputStream(urlConnection.getInputStream());
                        String response= readStream(inputStream);
                        threadMsg(response);
                    }
                    /*
                    List<NameValuePair> parameters=new ArrayList<NameValuePair>();
                    parameters.add(new BasicNameValuePair("name",name));
                    parameters.add(new BasicNameValuePair("sdk",sdk));
                    parameters.add(new BasicNameValuePair("type",type));
                    parameters.add(new BasicNameValuePair("bluetooth",bluetooth));
                    parameters.add(new BasicNameValuePair("wifi",wifi));
                    //parameters.add(new BasicNameValuePair("deviceid", params[0]));

                    JSONObject jsonObject = jsonParser.makeHttpRequest("http://ec2-52-41-46-86.us-west-2.compute.amazonaws.com/paasmer/feedcontrol.php", "POST", parameters);
                    threadMsg(jsonObject.toString());*/

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


    public void getFeedData(final String token,final String deviceId,final String feedId, final GenericHandlers callback){
        Thread background=new Thread(new Runnable() {


            String tag_json_obj = "json_obj_req";
            StringBuilder result=new StringBuilder();
            String url = "http://ec2-52-41-46-86.us-west-2.compute.amazonaws.com/myloginval.php";
            @Override
            public void run() {
                try{

                    URL url=new URL(ApiURLs.MANAGE_DEVICES+"/"+deviceId+"/feed/"+feedId+"/data");
                    HttpURLConnection urlConnection= (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setRequestProperty("Content-Type","application/json");
                    urlConnection.setRequestProperty("Authorization","Bearer "+token);

                    InputStream in =new BufferedInputStream(urlConnection.getInputStream());
                    BufferedReader reader=new BufferedReader(new InputStreamReader(in));
                    String line;
                    while ((line=reader.readLine())!=null){
                        result.append(line);
                    }
/*
                    List<NameValuePair> parameters=new ArrayList<NameValuePair>();



                    parameters.add(new BasicNameValuePair("email",email));

                    //parameters.add(new BasicNameValuePair("deviceid", params[0]));

                    JSONObject jsonObject = jsonParser.makeHttpRequest("http://ec2-52-41-46-86.us-west-2.compute.amazonaws.com/paasmer/deviceinfo.php", "POST", parameters);
                    threadMsg(jsonObject.toString());

*/
                    threadMsg(result.toString());
                    // return jsonObject+"";

                }catch (Exception e){
                    e.printStackTrace();
                }
                finally {

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
                    Log.d("Login",aResponse);
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



    public void getDevices(final String email, final GenericHandlers callback){
        Thread background=new Thread(new Runnable() {


            String tag_json_obj = "json_obj_req";
            StringBuilder result=new StringBuilder();
            String url = "http://ec2-52-41-46-86.us-west-2.compute.amazonaws.com/myloginval.php";
            @Override
            public void run() {
                try{

                    URL url=new URL(ApiURLs.MANAGE_DEVICES);
                    HttpURLConnection urlConnection= (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setRequestProperty("Content-Type","application/json");
                    urlConnection.setRequestProperty("Authorization","Bearer "+email);
                    Log.d("Status",email);
                    InputStream in =new BufferedInputStream(urlConnection.getInputStream());
                    BufferedReader reader=new BufferedReader(new InputStreamReader(in));
                    String line;
                    while ((line=reader.readLine())!=null){
                        result.append(line);
                    }
/*
                    List<NameValuePair> parameters=new ArrayList<NameValuePair>();



                    parameters.add(new BasicNameValuePair("email",email));

                    //parameters.add(new BasicNameValuePair("deviceid", params[0]));

                    JSONObject jsonObject = jsonParser.makeHttpRequest("http://ec2-52-41-46-86.us-west-2.compute.amazonaws.com/paasmer/deviceinfo.php", "POST", parameters);
                    threadMsg(jsonObject.toString());

*/
                threadMsg(result.toString());
                    // return jsonObject+"";

                }catch (Exception e){
                    e.printStackTrace();
                }
                finally {

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

                    if ((aResponse!=null)){

                        try {
                            JSONObject jsonObject=new JSONObject(aResponse);
                            Log.d("Login",aResponse);
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

    public void verifyEmail(final String code, final GenericHandlers callback){
        Thread background=new Thread(new Runnable() {
            String tag_json_obj = "json_obj_req";
            String url = "http://ec2-52-41-46-86.us-west-2.compute.amazonaws.com/myloginval.php";
            @Override
            public void run() {
                try{



                    URL url=new URL(ApiURLs.EMAIL_VERIFY);

                    HttpURLConnection urlConnection= (HttpURLConnection) url.openConnection();

                    urlConnection.setDoInput(true);
                    urlConnection.setDoOutput(true);

                    urlConnection.setRequestProperty("Content-Type","application/json");
                    urlConnection.setRequestMethod("POST");

                    JSONObject parameters=new JSONObject();

                    parameters.put("token",code);


                    //   Log.d("LoginValues",uname+","+pwd);
                 //   urlConnection.setRequestProperty("Authorization","Bearer "+token);
                    urlConnection.connect();
                    OutputStreamWriter writer=new OutputStreamWriter(urlConnection.getOutputStream());
                    writer.write(parameters.toString());
                    writer.flush();

                    int statusCode=urlConnection.getResponseCode();
                    if (statusCode==200){
                        InputStream inputStream=new BufferedInputStream(urlConnection.getInputStream());
                        String response= readStream(inputStream);
                        threadMsg(response);
                    }


                    /*

                    List<NameValuePair> parameters=new ArrayList<NameValuePair>();


                    parameters.add(new BasicNameValuePair("token",code));
                    //parameters.add(new BasicNameValuePair("deviceid", params[0]));

                    JSONObject jsonObject = jsonParser.makeHttpRequest(ApiURLs.EMAIL_VERIFY, "POST", parameters);
                    threadMsg(jsonObject.toString());*/

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
                            boolean i=jsonObject.getBoolean("success");

                            if (!i){
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

    public void resendVerification(final String email, final GenericHandlers callback){
        Thread background=new Thread(new Runnable() {
            String tag_json_obj = "json_obj_req";
            String url = "http://ec2-52-41-46-86.us-west-2.compute.amazonaws.com/myloginval.php";
            @Override
            public void run() {
                try{



                    URL url=new URL(ApiURLs.EMAIL_VERIFY_RESEND);

                    HttpURLConnection urlConnection= (HttpURLConnection) url.openConnection();

                    urlConnection.setDoInput(true);
                    urlConnection.setDoOutput(true);

                    urlConnection.setRequestProperty("Content-Type","application/json");
                    urlConnection.setRequestMethod("POST");

                    JSONObject parameters=new JSONObject();

                    parameters.put("email",email);


                    //   Log.d("LoginValues",uname+","+pwd);
                    //   urlConnection.setRequestProperty("Authorization","Bearer "+token);
                    urlConnection.connect();
                    OutputStreamWriter writer=new OutputStreamWriter(urlConnection.getOutputStream());
                    writer.write(parameters.toString());
                    writer.flush();

                    int statusCode=urlConnection.getResponseCode();
                    if (statusCode==200){
                        InputStream inputStream=new BufferedInputStream(urlConnection.getInputStream());
                        String response= readStream(inputStream);
                        threadMsg(response);
                    }


                    /*

                    List<NameValuePair> parameters=new ArrayList<NameValuePair>();


                    parameters.add(new BasicNameValuePair("token",code));
                    //parameters.add(new BasicNameValuePair("deviceid", params[0]));

                    JSONObject jsonObject = jsonParser.makeHttpRequest(ApiURLs.EMAIL_VERIFY, "POST", parameters);
                    threadMsg(jsonObject.toString());*/

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
                            boolean i=jsonObject.getBoolean("success");

                            if (!i){
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
    public void createDevice(final String token,final String name,final String sdk,final String type,final String bluetooth,final String wifi, final GenericHandlers callback){
        Thread background=new Thread(new Runnable() {
            String tag_json_obj = "json_obj_req";
            String url = "http://ec2-52-41-46-86.us-west-2.compute.amazonaws.com/myloginval.php";
            @Override
            public void run() {
                try{


                    URL url=new URL(ApiURLs.MANAGE_DEVICES);

                    HttpURLConnection urlConnection= (HttpURLConnection) url.openConnection();

                    urlConnection.setDoInput(true);
                    urlConnection.setDoOutput(true);

                    urlConnection.setRequestProperty("Content-Type","application/json");
                    urlConnection.setRequestMethod("POST");

                    JSONObject parameters=new JSONObject();

                    parameters.put("name",name);
                    parameters.put("paasmeerId","");
                    parameters.put("sdk",sdk);
                    parameters.put("type",type);
                    parameters.put("bluetooth",bluetooth);
                    parameters.put("wifi",wifi);


                    //   Log.d("LoginValues",uname+","+pwd);
                    urlConnection.setRequestProperty("Authorization"," Bearer "+token);
                    urlConnection.connect();
                    OutputStreamWriter writer=new OutputStreamWriter(urlConnection.getOutputStream());
                    writer.write(parameters.toString());
                    writer.flush();

                    int statusCode=urlConnection.getResponseCode();
                    Log.d("StatusCode:",statusCode+""+urlConnection.getResponseMessage());
                    if (statusCode==200){
                        InputStream inputStream=new BufferedInputStream(urlConnection.getInputStream());
                        String response= readStream(inputStream);
                        threadMsg(response);
                    }
                    /*
                    List<NameValuePair> parameters=new ArrayList<NameValuePair>();
                    parameters.add(new BasicNameValuePair("name",name));
                    parameters.add(new BasicNameValuePair("sdk",sdk));
                    parameters.add(new BasicNameValuePair("type",type));
                    parameters.add(new BasicNameValuePair("bluetooth",bluetooth));
                    parameters.add(new BasicNameValuePair("wifi",wifi));
                    //parameters.add(new BasicNameValuePair("deviceid", params[0]));

                    JSONObject jsonObject = jsonParser.makeHttpRequest("http://ec2-52-41-46-86.us-west-2.compute.amazonaws.com/paasmer/feedcontrol.php", "POST", parameters);
                    threadMsg(jsonObject.toString());*/

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


    public void editDevice(final String token,final String deviceId,final String name,final String sdk,final String type,final String bluetooth,final String wifi, final GenericHandlers callback){
        Thread background=new Thread(new Runnable() {
            String tag_json_obj = "json_obj_req";
            String url = "http://ec2-52-41-46-86.us-west-2.compute.amazonaws.com/myloginval.php";
            @Override
            public void run() {
                try{


                    URL url=new URL(ApiURLs.MANAGE_DEVICES+"/"+deviceId);

                    HttpURLConnection urlConnection= (HttpURLConnection) url.openConnection();

                    urlConnection.setDoInput(true);
                    urlConnection.setDoOutput(true);

                    urlConnection.setRequestProperty("Content-Type","application/json");
                    urlConnection.setRequestMethod("PUT");

                    JSONObject parameters=new JSONObject();

                    parameters.put("name",name);
                    parameters.put("paasmeerId","");
                    parameters.put("sdk",sdk);
                    parameters.put("type",type);
                    parameters.put("bluetooth",bluetooth);
                    parameters.put("wifi",wifi);


                    //   Log.d("LoginValues",uname+","+pwd);
                    urlConnection.setRequestProperty("Authorization"," Bearer "+token);
                    urlConnection.connect();
                    OutputStreamWriter writer=new OutputStreamWriter(urlConnection.getOutputStream());
                    writer.write(parameters.toString());
                    writer.flush();

                    int statusCode=urlConnection.getResponseCode();
                    if (statusCode==200){
                        InputStream inputStream=new BufferedInputStream(urlConnection.getInputStream());
                        String response= readStream(inputStream);
                        threadMsg(response);
                    }
                    /*
                    List<NameValuePair> parameters=new ArrayList<NameValuePair>();
                    parameters.add(new BasicNameValuePair("name",name));
                    parameters.add(new BasicNameValuePair("sdk",sdk));
                    parameters.add(new BasicNameValuePair("type",type));
                    parameters.add(new BasicNameValuePair("bluetooth",bluetooth));
                    parameters.add(new BasicNameValuePair("wifi",wifi));
                    //parameters.add(new BasicNameValuePair("deviceid", params[0]));

                    JSONObject jsonObject = jsonParser.makeHttpRequest("http://ec2-52-41-46-86.us-west-2.compute.amazonaws.com/paasmer/feedcontrol.php", "POST", parameters);
                    threadMsg(jsonObject.toString());*/

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

    public void deleteDevice(final String token,final String deviceId,final GenericHandlers callback){
        Thread background=new Thread(new Runnable() {
            String tag_json_obj = "json_obj_req";
            String url = "http://ec2-52-41-46-86.us-west-2.compute.amazonaws.com/myloginval.php";
            @Override
            public void run() {
                try{


                    URL url=new URL(ApiURLs.MANAGE_DEVICES+"/"+deviceId);

                    HttpURLConnection urlConnection= (HttpURLConnection) url.openConnection();

                    urlConnection.setDoInput(true);
                    urlConnection.setDoOutput(true);

                    urlConnection.setRequestProperty("Content-Type","application/json");
                    urlConnection.setRequestMethod("DELETE");



                    //   Log.d("LoginValues",uname+","+pwd);
                    urlConnection.setRequestProperty("Authorization"," Bearer "+token);
                    urlConnection.connect();


                    int statusCode=urlConnection.getResponseCode();
                    if (statusCode==200){
                        InputStream inputStream=new BufferedInputStream(urlConnection.getInputStream());
                        String response= readStream(inputStream);
                        threadMsg(response);
                    }
                    /*
                    List<NameValuePair> parameters=new ArrayList<NameValuePair>();
                    parameters.add(new BasicNameValuePair("name",name));
                    parameters.add(new BasicNameValuePair("sdk",sdk));
                    parameters.add(new BasicNameValuePair("type",type));
                    parameters.add(new BasicNameValuePair("bluetooth",bluetooth));
                    parameters.add(new BasicNameValuePair("wifi",wifi));
                    //parameters.add(new BasicNameValuePair("deviceid", params[0]));

                    JSONObject jsonObject = jsonParser.makeHttpRequest("http://ec2-52-41-46-86.us-west-2.compute.amazonaws.com/paasmer/feedcontrol.php", "POST", parameters);
                    threadMsg(jsonObject.toString());*/

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


    public void onForgotPassword(final String token,final String email, final GenericHandlers callback){
        Thread background=new Thread(new Runnable() {
            String tag_json_obj = "json_obj_req";
            String url = "http://ec2-52-41-46-86.us-west-2.compute.amazonaws.com/myloginval.php";
            @Override
            public void run() {
                try{


                    URL url=new URL(ApiURLs.FORGOT_PASSWORD);

                    HttpURLConnection urlConnection= (HttpURLConnection) url.openConnection();

                    urlConnection.setDoInput(true);
                    urlConnection.setDoOutput(true);

                    urlConnection.setRequestProperty("Content-Type","application/json");
                    urlConnection.setRequestMethod("POST");

                    JSONObject parameters=new JSONObject();

                    parameters.put("email",email);


                    //   Log.d("LoginValues",uname+","+pwd);
                       urlConnection.setRequestProperty("Authorization"," Bearer "+token);
                    urlConnection.connect();
                    OutputStreamWriter writer=new OutputStreamWriter(urlConnection.getOutputStream());
                    writer.write(parameters.toString());
                    writer.flush();

                    int statusCode=urlConnection.getResponseCode();
                    if (statusCode==200){
                        InputStream inputStream=new BufferedInputStream(urlConnection.getInputStream());
                        String response= readStream(inputStream);
                        threadMsg(response);
                    }

                    /*
                    List<NameValuePair> parameters=new ArrayList<NameValuePair>();

                    parameters.add(new BasicNameValuePair("email",email));

                    //parameters.add(new BasicNameValuePair("deviceid", params[0]));

                    JSONObject jsonObject = jsonParser.makeHttpRequest("http://ec2-52-41-46-86.us-west-2.compute.amazonaws.com/paasmer/resetpassword.php", "POST", parameters);
                    threadMsg(jsonObject.toString());
                    */

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



    public void onForgotPasswordVerify(final String token,final String password,final String code,final GenericHandlers callback){
        Thread background=new Thread(new Runnable() {
            String tag_json_obj = "json_obj_req";
            String url = "http://ec2-52-41-46-86.us-west-2.compute.amazonaws.com/myloginval.php";
            @Override
            public void run() {
                try{


                    URL url=new URL(ApiURLs.FORGOT_PASSWORD);

                    HttpURLConnection urlConnection= (HttpURLConnection) url.openConnection();

                    urlConnection.setDoInput(true);
                    urlConnection.setDoOutput(true);

                    urlConnection.setRequestProperty("Content-Type","application/json");
                    urlConnection.setRequestMethod("POST");

                    JSONObject parameters=new JSONObject();

                    parameters.put("token",code);
                    parameters.put("newPassword",password);


                    //   Log.d("LoginValues",uname+","+pwd);
                    urlConnection.setRequestProperty("Authorization"," Bearer "+token);
                    urlConnection.connect();
                    OutputStreamWriter writer=new OutputStreamWriter(urlConnection.getOutputStream());
                    writer.write(parameters.toString());
                    writer.flush();

                    int statusCode=urlConnection.getResponseCode();
                    if (statusCode==200){
                        InputStream inputStream=new BufferedInputStream(urlConnection.getInputStream());
                        String response= readStream(inputStream);
                        threadMsg(response);
                    }


                    /*
                    List<NameValuePair> parameters=new ArrayList<NameValuePair>();



                    parameters.add(new BasicNameValuePair("email",email));
                    parameters.add(new BasicNameValuePair("password",password));
                    parameters.add(new BasicNameValuePair("code",code));

                    //parameters.add(new BasicNameValuePair("deviceid", params[0]));

                    JSONObject jsonObject = jsonParser.makeHttpRequest("http://ec2-52-41-46-86.us-west-2.compute.amazonaws.com/paasmer/verifyuserforgotpassword.php", "POST", parameters);
                    threadMsg(jsonObject.toString());
                    */

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
