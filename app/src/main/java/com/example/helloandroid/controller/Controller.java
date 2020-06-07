package com.example.helloandroid.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import com.example.helloandroid.RegisterSuccessActivity;
import com.example.helloandroid.model.NetModel;

import org.json.JSONException;
import org.json.JSONObject;

public class Controller {

    /*将uid传给sharedPreference的时候需要上下文，然后上下文的话是这样写，需要构造方法哦*/
    private static Context context;
    public Controller(Context context) {
        this.context = context;
    }


    public static Handler handler=new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            String json_str=msg.obj.toString();
            /*下面解析json*/
            try {
                JSONObject jsonObject=new JSONObject(json_str);
                /*将json里的信息解析出来*/
                String cmd=jsonObject.getString("cmd");
                String code=jsonObject.getString("code");
                /*如果当前的操作是注册，那么就将返回的uid放到sharedPreference里面去*/
                if (cmd!=null&&code!=null&&code.equals("0")){
                    if (cmd.equals("1")){
                        String uid=jsonObject.getString("uid");
                        SharedPreferences sharedPreferences=context.getSharedPreferences("config",0);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString("token",uid);
                        editor.commit();

                        /*将返回的uid放到sharedPreference里面去以后，将页面跳转到另外一个activity中去*/
                        Intent intent = new Intent(context, RegisterSuccessActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }else {
                        /*现在先不写*/
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };


    public void sentCmd(String cmd){
        /*为了保持MVC，在这里我们的controller只负责转发commend给netModel类，同时接受netModel类返回的信息返回给页面*/
        NetModel.sentCmd(cmd);
    }

}



