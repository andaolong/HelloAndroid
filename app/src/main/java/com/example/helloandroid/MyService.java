package com.example.helloandroid;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {

    public int count = 0;
    private boolean quit = false;
    private MyBinder binder = new MyBinder();

    public MyService() {
    }


    public class MyBinder extends Binder {
        public int getCount() {
            return count;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Log.e("Service", "Service Binding~");
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("Service", "Service create~");
        new Thread() {
            @Override
            public void run() {
                super.run();
                while (!quit) {
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    count++;
                }
            }
        }.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("Service", "Service start~");

        //需要先把广播所需要的Intent先定义好
        Intent broadcastIntent=new Intent();
        //setAction里面是你要对当前这个意图定义一个表示，一般我们用包名加上我们定义的名字来标识intent，这里定义好以后还需要在manifest里面配置它一下
        broadcastIntent.setAction("com.example.helloandroid.serviceSender");
        //将count加载到这个Intent中来
        broadcastIntent.putExtra("msg",String.valueOf(count));
        //在service的onStartCommand方法里面发布一个广播
        sendBroadcast(broadcastIntent);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.e("Service", "Service destroy~");
        super.onDestroy();
        quit = true;
    }


    @Override
    public boolean onUnbind(Intent intent) {
        Log.e("Service", "Service unbind~");
        return super.onUnbind(intent);
    }

}
