package com.example.helloandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    public MainActivity() {
        super();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("hello","This is test start state");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("hello","This is test stop state");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("hello","This is test destroy state");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("hello","This is test pause state");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("hello","This is test resume state");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //andl :验证log的使用,2020.03.11
        Log.e("hello","This is test the log.e");
    }
}
