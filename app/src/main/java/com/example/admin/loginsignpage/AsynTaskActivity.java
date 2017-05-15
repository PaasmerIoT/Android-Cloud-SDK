package com.example.admin.loginsignpage;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by admin on 15/02/17.
 */

public class AsynTaskActivity extends AsyncTask<String, String, String> {

    private String reqUrl;
    HttpURLConnection httpConn = null;

    AsynTaskActivity(String reqUrl){
        this.reqUrl = reqUrl;
    }

    @Override
    protected String doInBackground(String... params) {
        int responseCode;
        StringBuffer sb = new StringBuffer();
        try {
            URL url = new URL(reqUrl);
            httpConn = (HttpURLConnection)url.openConnection();

            responseCode = httpConn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream is = null;
                try {
                    is = httpConn.getInputStream();
                    int ch;
                    while ((ch = is.read()) != -1) {
                        sb.append((char) ch);
                    }
                    return sb.toString();
                } catch (IOException e) {
                    throw e;
                } finally {
                    if (is != null) {
                        is.close();
                    }
                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }
}
