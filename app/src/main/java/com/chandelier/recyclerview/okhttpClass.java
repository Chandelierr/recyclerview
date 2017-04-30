package com.chandelier.recyclerview;

import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;


public class okhttpClass {
    OkHttpClient mOkHttpClient;
    String TAG = "okhttpClass";
    public okhttpClass() throws IOException {
        Log.i(TAG,"in okhttpClass!");
        mOkHttpClient = new OkHttpClient();
    }
    public String getOp(String url) throws IOException{
        Log.i(TAG,"in getOp!");
        Request request = new Request.Builder().url(url).build();
        Response response = mOkHttpClient.newCall(request).execute();
        Log.i(TAG,"get responce");
        String str;
        if (response.isSuccessful()){
            ResponseBody responceBody = response.body();
            str = responceBody.string();
        }else{
            return "error";
        }
        return str;
    }
}
