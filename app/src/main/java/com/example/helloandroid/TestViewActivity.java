package com.example.helloandroid;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TestViewActivity extends AppCompatActivity {

    public Button btnTop01;

    public String[] listView02Array = {"Shanghai", "Jilin", "Beijing"};
    public ListView listView02Tab;

    public String[] listView03ArrayTv1 = {"Jane", "Trump", "Elizabeth"};
    public String[] listView03ArrayTv2 = {"35", "89", "90"};
    public ListView listView03Tab;

    //练习sharedPreference的用法
    public SharedPreferences sharedPreferences01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_view);

        //在这里先创建字符串数组和ListView，然后将xml里面的ListView通过findViewById绑定到这里创建的ListView，然后用适配器将
        //字符串数组传递到新的适配器里面，最后将适配器装载到绑定好的ListView就行了
        listView02Tab = findViewById(R.id.listView02);
        ArrayAdapter<String> listView02Adapter = new ArrayAdapter<String>(TestViewActivity.this, android.R.layout.simple_list_item_1, listView02Array);
        listView02Tab.setAdapter(listView02Adapter);

        //这里新创建的ListView3是需要将自定义视图装载到xml里去，因为是自定义的视图所以没有现成的装载器可供使用，因此需要自己写一个adapter
        listView03Tab = findViewById(R.id.listView03);
        listView03Tab.setAdapter(new MyAdapter(TestViewActivity.this));

        //练习sharedPreference
        sharedPreferences01=getSharedPreferences("spCount", MODE_PRIVATE);
        int spCount=sharedPreferences01.getInt("spCount", 0);
        SharedPreferences.Editor spEditor=sharedPreferences01.edit();
        spEditor.putInt("spCount", ++spCount);
        spEditor.commit();
        btnTop01=findViewById(R.id.textTop01);
        btnTop01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),
                        String.valueOf(sharedPreferences01.getInt("spCount",0)),
                        Toast.LENGTH_SHORT).show();
                //Toast.makeText(getApplicationContext(), "点击了数字按钮", Toast.LENGTH_SHORT).show();
            }
        });
    }



    private class MyAdapter extends BaseAdapter {
        Context context;

        public MyAdapter(Context context) {
            super();
            this.context = context;
        }

        public MyAdapter() {
            super();
        }

        @Override
        public int getCount() {
            return listView02Array.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View v, ViewGroup parent) {
            if (position % 2 == 0) {
                v = LayoutInflater.from(context).inflate(R.layout.activity_my_list_tab, null);
            } else {
                v = LayoutInflater.from(context).inflate(R.layout.activity_my_list_tab02, null);
            }
            TextView tvv1 = v.findViewById(R.id.tvv1);
            TextView tvv2 = v.findViewById(R.id.tvv2);

            tvv1.setText(listView03ArrayTv1[position]);
            tvv2.setText(listView03ArrayTv2[position]);

            return v;
        }
    }
}
