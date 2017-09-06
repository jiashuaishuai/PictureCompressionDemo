package com.example.jiashuai.picturecompressiondemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.annotation.UiThread;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.BitmapCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;

import java.lang.annotation.Annotation;

/**
 * 图片长宽压缩
 */
public class MainActivity extends AppCompatActivity {
    private ImageView my_img, my_img2, my_img3, my_img4, my_img5;
    private Handler handler = new Handler();
    private DisplayMetrics displayMetrics;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayMetrics = getResources().getDisplayMetrics();
        my_img = (ImageView) findViewById(R.id.my_img);
        my_img2 = (ImageView) findViewById(R.id.my_img2);
        my_img3 = (ImageView) findViewById(R.id.my_img3);
        my_img4 = (ImageView) findViewById(R.id.my_img4);
        my_img5 = (ImageView) findViewById(R.id.my_img5);
        new Thread(new Runnable() {
            @Override
            public void run() {
                int imgH = my_img.getHeight();
                int imgW = my_img.getWidth();
                Log.e("PPC", "imgh " + imgH + "  imgw " + imgW);
                final BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;//不将图片解码到内存中
                BitmapFactory.decodeResource(getResources(), R.drawable.laohu_bg, options);
                Log.e("PPC", "压缩前 H " + options.outHeight + "  W " + options.outWidth);
                options.inSampleSize = Utils.computeSampleSize(options, 600, displayMetrics.widthPixels * displayMetrics.heightPixels);//指定一个最小宽度或者高度，和分辨率，这里采用屏幕的分辨率，实际运用可以适当调整
//                options.inSampleSize = Utils.calculateInSampleSIze(options, imgW, imgH);//这个是自己写的算法，需指定一个w和h
                options.inJustDecodeBounds = false;//将图片解码到内存中
                final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.laohu_bg, options);
                final Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.laohu_bg, options);
                final Bitmap bitmap3 = BitmapFactory.decodeResource(getResources(), R.drawable.laohu_bg, options);
                final Bitmap bitmap4 = BitmapFactory.decodeResource(getResources(), R.drawable.laohu_bg, options);
                final Bitmap bitmap5 = BitmapFactory.decodeResource(getResources(), R.drawable.laohu_bg, options);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        my_img.setImageBitmap(bitmap);
                        my_img2.setImageBitmap(bitmap2);
                        my_img3.setImageBitmap(bitmap3);
                        my_img4.setImageBitmap(bitmap4);
                        my_img5.setImageBitmap(bitmap5);
                        Log.e("PPC", "压缩后 h " + bitmap.getHeight() + "  w " + bitmap.getWidth());
                    }
                });
            }
        }).start();
    }


}
