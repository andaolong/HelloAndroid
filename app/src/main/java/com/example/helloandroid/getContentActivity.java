package com.example.helloandroid;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class getContentActivity extends AppCompatActivity {

    public ListView getContentShowPanel;
    public Button getContentBtn;
    public Button getImageBtn;

    ArrayList<String> names = new ArrayList<String>();
    ArrayList<String> phones = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_content);


        getContentShowPanel = findViewById(R.id.getContentShowPanel);
        getContentBtn = findViewById(R.id.getContentBtn);
        getImageBtn = findViewById(R.id.getImageBtn);

        getImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //这四行是老师拓展里面的code，直接复制的
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivity(intent);
            }
        });

        getContentBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                //获取通讯录里所有联系人的姓名和手机号
                Cursor cursor=getContentResolver().query(
                        ContactsContract.Contacts.CONTENT_URI,
                        null,
                        null,
                        null,
                        null);
                while (cursor.moveToNext()){
                    //获取联系人姓名
                    String contactsName=cursor.getString(
                            cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    //获取联系人ID
                    String contactsId=cursor.getString(
                            cursor.getColumnIndex(ContactsContract.Contacts._ID));

                    //用一个子查询来查电话号码
                    Cursor cursorPhoneNum=getContentResolver().query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID+"="+contactsId,
                            null,
                            null
                    );
                    //存储所有手机号
                    ArrayList<String> contactsPhones=new ArrayList<String>();
                    while (cursorPhoneNum.moveToNext()){
                        String contactsPhone = cursorPhoneNum.getString(
                                cursorPhoneNum.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        contactsPhones.add(contactsPhone);
                    }
                    names.add(contactsName);
                    phones.add(contactsPhones.get(0));
                    //到这里将联系人和电话号码装载到那两个ListView里去了
                }
                getContentShowPanel.setAdapter(new MyAdapter(getContentActivity.this));
            }
        });

    }





    private class MyAdapter extends BaseAdapter implements com.example.helloandroid.MyAdapter {
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
            return names.size();
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

            tvv1.setText(names.get(position));
            tvv2.setText(phones.get(position));

            return v;
        }
    }

}
