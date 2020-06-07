package com.example.helloandroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.helloandroid.controller.Controller;

public class RegisterActivity extends AppCompatActivity {

    /*andl 2020年5月7日19:41:07*/
    public EditText usernameEditText;
    public EditText passwordEditText;
    public Button submitBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        /*注册的时候首先获取到当前view输入的name和password*/
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        submitBtn = findViewById(R.id.submitBtn);
        /*final String name_str = usernameEditText.getText().toString().trim();
        final String pass_str = passwordEditText.getText().toString();*/

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*这个功能需要进行用户是否已经存在的判断，如果已经存在的话需要提示，在这里是如果已经存在的话就跳转到另外一个activity*/
                /*read sp*/
                SharedPreferences sp = getApplicationContext().getSharedPreferences("config", 0);
                String token = null;
                token = sp.getString("token", null);
                /*如果已经有这个用户了*/
                //if (true) {
                //if (false) {
                if (token != null) {
                    /*笔记：在主线程中直接使用sleep方法的话有可能会造成程序无反应，需要使用的时候应该新开启一个线程然后在其中sleep，2020年5月9日14:47:17*/
                    /*下面两行是怎么跳转activity*/
                    Intent intent = new Intent(getApplicationContext(), RegisterHintActivity.class);
                    startActivity(intent);
                } else {
                    /*如果没有输入注册的用户，那么就调用方法去注册*/
                    String name_str = usernameEditText.getText().toString().trim();
                    String pass_str = passwordEditText.getText().toString();
                    Log.e("reg",name_str+"  "+pass_str+"   "+"这是看view里面的name和password获取到了没有");
                    registerAccount(name_str, pass_str);
                }
            }
        });

    }


    /*注册账户的方法*/
    private void registerAccount(String name_str, String pass_str) {
        /*在这里实现页面的注册功能，只需要将view里面获取到的数据拼接成"http://localhost:8080/leisurelife/dealcmd?cmd=1&name=testname&password=testpassword"的形式传过去就行了*/
        /*然后将拼接好的post出去就行了*/
        /*字符串拼接*/
        String cmd_str = "cmd=1";
        cmd_str = cmd_str + "&name=" + name_str;
        cmd_str = cmd_str + "&password=" + pass_str;
        Log.e("reg",cmd_str+"这里是在提交前展示一下提交的数据");
        /*为了联系我们的MVC架构，所以我们在哪个controller类里面去处理后续的事情，这样结构更加清晰*/

        Controller controller=new Controller(getApplicationContext());
        controller.sentCmd(cmd_str);
    }

}

