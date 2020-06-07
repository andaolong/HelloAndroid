package com.example.helloandroid;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class testServiceActivity extends AppCompatActivity {

    public Button btn1;
    public Button btn2;
    public Button btn3;
    public Button btn4;
    public Button btn5;
    public Button btn6;
    public TextView showContentTV;
    public MyService.MyBinder binder;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e("Service", "Service Connected");
            binder = (MyService.MyBinder)service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("Service", "Service Disconnected");
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_service);

        btn1 = findViewById(R.id.serviceBtn1);
        btn2 = findViewById(R.id.serviceBtn2);
        btn3 = findViewById(R.id.serviceBtn3);
        btn4 = findViewById(R.id.serviceBtn4);
        btn5 = findViewById(R.id.serviceBtn5);
        btn6 = findViewById(R.id.serviceBtn6);
        showContentTV = findViewById(R.id.showContentTV);
        final Intent intent = new Intent();
        intent.setAction("com.example.helloandroid.MyService");
        intent.setPackage("com.example.helloandroid");


        //点击btn1，调用startService
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(intent);
            }
        });
        //点击btn2，调用stopService
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(intent);
            }
        });


        //点击btn3，bind Service
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bindService(intent,connection,BIND_AUTO_CREATE);
            }
        });
        //点击btn4，get the count
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showContentTV.setText(String.valueOf(binder.getCount()));
            }
        });
        //点击btn5，unbind the service
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unbindService(connection);
            }
        });

        //点击btn6，播放音乐
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AudioManager audioManager=(AudioManager) getSystemService(Service.AUDIO_SERVICE);
                MediaPlayer mediaPlayer=MediaPlayer.create(testServiceActivity.this,R.raw.music01);
                mediaPlayer.start();
            }
        });
    }
}
