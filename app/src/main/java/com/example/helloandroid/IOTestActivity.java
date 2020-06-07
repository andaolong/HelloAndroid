package com.example.helloandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class IOTestActivity extends AppCompatActivity {

    public EditText topEditor01;
    public EditText topEditor02;
    public Button btn1;
    public Button btn2;

    public final String filename="andlFile.txt";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i_o_test);

        topEditor01=findViewById(R.id.topEditor01);
        topEditor02=findViewById(R.id.topEditor02);
        btn1=findViewById(R.id.btn1);
        btn2=findViewById(R.id.btn2);



        //写文字到文件
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //定义文件输出流
                    FileOutputStream fileOutputStream01=openFileOutput(filename, MODE_PRIVATE);
                    //转化输出流
                    PrintStream printStream01=new PrintStream(fileOutputStream01);
                    //存入文本
                    printStream01.println(topEditor01.getText());
                    //关掉IO流
                    printStream01.close();
                    fileOutputStream01.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        //读文件到视图
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    //定义文件输入流
                    FileInputStream fileInputStream01=openFileInput(filename);
                    //读的时候是按字节进行读
                    byte[] buffer=new byte[1024];
                    int hasRead =0;
                    //字符串拼装
                    StringBuilder stringBuilder01=new StringBuilder("");
                    while((hasRead=fileInputStream01.read(buffer))>0){
                        stringBuilder01.append(new String(buffer, 0,hasRead));
                    }
                    fileInputStream01.close();
                    topEditor02.setText(stringBuilder01.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
