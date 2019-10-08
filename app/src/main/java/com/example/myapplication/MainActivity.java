package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //创建两个bitmap对象由文件转换到内存得来 用当前文件的文件名进行命名
        Bitmap bmp1= BitmapFactory.decodeByteArray(null,0,0);
        Bitmap bmp2= BitmapFactory.decodeByteArray(null,0,0);
        CacheUtil<String, Bitmap> cacheUtil=new CacheUtil<>();
        cacheUtil.put("/user/home/app/xxx.bmp",bmp1);
        cacheUtil.put("/user/home/app/yyy.bmp",bmp2);

        //使用软引用包装的bitmap可以避免过大而OOM 会被GC回收
        Bitmap bmp=cacheUtil.get("/user/home/app/yyy.bmp");
        if(bmp==null) return;







    }
}
