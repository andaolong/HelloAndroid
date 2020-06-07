package com.example.helloandroid.model;

import android.os.Message;
import android.util.Log;

import com.example.helloandroid.controller.Controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NetModel {

    public static void sentCmd(final String cmd) {
        /*在这里去实现怎么去做http请求，然后将返回的结果再返回给controller*/
        /*笔记：从Android4开始，为了防止程序被卡死，所有的网络编程都要求在一个新线程里面执行*/
        new Thread(new Runnable() {
            @Override
            public void run() {

                /*首先应该先定义出请求的网址*/
                String url_str = "http://192.168.1.107:8080/leisurelife/dealcmd?";
                url_str = url_str + cmd;

                /*然后打开这个url*/
                /*需要用URL这个类*/
                try {
                    URL url = new URL(url_str);
                    HttpURLConnection connection =(HttpURLConnection) url.openConnection();
                    /*提交了cmd注册以后，应该得到返回的值，再跳转到另外的activity页面*/
                    /*先看是不是提交成功了*/
                    if(connection.getResponseCode()==200){
                        Log.e("Reg","submit success");
                        /*然后通过数据流的方式得到返回的数据，在这里是返回的name，password，uid对象*/
                        InputStream inputStream=connection.getInputStream();
                        byte[] buff=new byte[1024];
                        int len=-1;
                        StringBuilder stringBuilder=new StringBuilder();
                        while((len=inputStream.read(buff))!=-1){
                            stringBuilder.append(new String(buff,0,len,"gbk"));
                        }
                        String res_str=stringBuilder.toString();
                        /*将得到的返回的数据打印一下看看对不对*/
                        Log.e("reg",res_str);

                        /*通过回调函数，将得到的message返回给controller，由controller将message返回给view*/
                        Message message = new Message();
                        message.obj=res_str;
                        Controller.handler.handleMessage(message);
                    }



                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }
}
