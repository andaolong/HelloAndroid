package com.example.helloandroid;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class CameraActivity extends AppCompatActivity {

    public Button openPhotoBtn;
    public ImageView imageShowPanel;
    public Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        openPhotoBtn=findViewById(R.id.openPhotoBtn);
        imageShowPanel=findViewById(R.id.imageShowPanel);

        openPhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //定义摄像头获取到的照片存储的地方，这里定义一个文件
                File tempImage = new File(getExternalCacheDir(),"temp.jpg");
                if(tempImage.exists()){
                    tempImage.delete();
                    //如果当前已经存在一个叫做tempImage的文件，那么就将它删掉
                }
                //然后创建tempImage这个文件
                try {
                    tempImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //下面获取照片到我们创建好的文件里面，其实我们是通过contentProvider这个东西，从另外一个app即照相机里面来获取照相机提供的contentProvider
                //所以上面我们才定义了一个Uri
                //首先得到URI
                imageUri= FileProvider.getUriForFile(CameraActivity.this,"com.example.helloandroid.fileProvider",tempImage);
                //打开摄像头
                Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                //下面用的是startActivityForResult()而不是startActivity方法，是因为我们要等着结果返回然后使用结果
                startActivityForResult(intent,1);
                //结果回来以后，将图片显示到视图层，通过startActivityForResult()的回调函数

            }
        });
    }

    //回调函数，下面用的这个回调函数其实是上面那个startActivityForResult()的回调函数
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                if(resultCode  ==  RESULT_OK){
                    //先进行一下图片格式的转换
                    //先定义一个位图
                    Bitmap bitmap=null;
                    //然后将位图从intent里面去取出来
                    try {
                        bitmap= BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    //取出来以后就将图片加载到我们的imageView上，显示出来
                    imageShowPanel.setImageBitmap(bitmap);
                }

                break;
            default:
                break;
        }
    }
}
