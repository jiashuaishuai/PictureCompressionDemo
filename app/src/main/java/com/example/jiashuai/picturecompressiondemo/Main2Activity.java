package com.example.jiashuai.picturecompressiondemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * 质量压缩
 */
public class Main2Activity extends AppCompatActivity {
    private ImageView test_img;
    private DisplayMetrics displayMetrics;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        displayMetrics = getResources().getDisplayMetrics();
        setContentView(R.layout.activity_main2);
        test_img = (ImageView) findViewById(R.id.test_img);
        new Thread(new Runnable() {
            @Override
            public void run() {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeResource(getResources(), R.drawable.laohu_bg, options);
                options.inSampleSize = Utils.computeSampleSize(options, 600, displayMetrics.widthPixels * displayMetrics.heightPixels);
                options.inJustDecodeBounds = false;
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.laohu_bg, options);//首先将图片尺寸压缩，
                int quality = 100;
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);//将bitmap加载到输出流中
                Log.e("CPP", "  长度1:  " + outputStream.toByteArray().length);
                while (outputStream.size() / 1024 > 1024) {//判断是否大于1
                    outputStream.reset();//清空
                    quality -= 10;//递减10
                    bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);//重新加载
                }
                Log.e("CPP", "  长度2:  " + outputStream.size());
                ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());//获取输入流
                final Bitmap bitmap1 = BitmapFactory.decodeStream(inputStream);//转为bitmap
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        test_img.setImageBitmap(bitmap1);//主线程加载
                    }
                });

            }
        }).start();


    }
}
