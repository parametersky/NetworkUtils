package com.kcc.httputils;

import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by ford-pro2 on 16/7/18.
 */
public class HttpPost extends HttpRequest {
    public static final String TAG = "HttpPost";
    private HashMap<String, String> params;

    public HttpPost(String url, int timeout) {
        super(url, timeout);
    }

    public HttpPost(String url) {
        super(url);
    }

    public HttpPost(String url, int timeout, HashMap<String, String> params) {
        super(url, timeout);
        this.params = new HashMap<String, String>();
        this.params.putAll(params);
    }

    public HttpPost(String url, HashMap<String, String> params) {
        super(url);
        this.params = new HashMap<String, String>();
        this.params.putAll(params);
    }



    public HttpResponse connect() {
        HttpResponse response = new HttpResponse();

        HttpURLConnection connection = null;
        try {
            URL mUrl = new URL(url);
            connection = (HttpURLConnection) mUrl.openConnection();
            connection.setConnectTimeout(timeout);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            byte[] bytes = new String("menu_name=post test").getBytes("UTF8");
//            connection.set
            OutputStream os = connection.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(os);
            bos.write(bytes);
            bos.flush();
            bos.close();

            response.setCode(connection.getResponseCode());
            InputStream is = connection.getInputStream();
            response.setContent(getString(is));
        } catch (MalformedURLException e) {
            e.printStackTrace();
            response.setCode(-1);
            response.setContent(ERROR_URL);
        } catch (IOException e) {
            e.printStackTrace();
            try {
                response.setError(getString(connection.getErrorStream()));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return response;
    }

    public byte[] getParams(HashMap<String, String> params) {
        StringBuffer buffer = new StringBuffer();
        boolean first = true;
        for (String key : params.keySet()) {
            if (first) {
                buffer.append(key + "=" + params.get(key));
                first = false;
            } else {
                buffer.append("&" + key + "=" + params.get(key));
            }
        }
        Log.d(TAG, "getParams: "+buffer.toString());
        return buffer.toString().getBytes();
    }

}
