package com.example.jiashuai.picturecompressiondemo.cut;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import com.example.jiashuai.picturecompressiondemo.R;


/**
 * Created by JiaShuai on 2017/9/11.
 */

public class ShaderTestView extends View {
    private static final int RECT_SIZE = 400;
    private DisplayMetrics displayMetrics;
    private Paint mPaint;
    private int left, top, right, bottom;

    private int screenY;
    private int screenX;

    public ShaderTestView(Context context) {
        this(context, null);
    }

    public ShaderTestView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShaderTestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        displayMetrics = getResources().getDisplayMetrics();



        mPaint = new Paint();

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test);

        /*
        计算缩放比例
         */
        Matrix matrix = new Matrix();
        float w = 400 * 1.0f / bitmap.getWidth();
        float h = 400 * 1.0f / bitmap.getHeight();
        matrix.preScale(w, h);
        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);//TileMode三种模式，拉伸，镜像，平铺
        shader.setLocalMatrix(matrix);
        mPaint.setShader(shader);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawRect(left,top,right,bottom,mPaint);
//        canvas.drawCircle(screenX, screenY, 200, mPaint);
        canvas.drawRoundRect(mRoundRect,50,50,mPaint);
    }
    RectF mRoundRect;
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
        // 圆角图片的范围
            mRoundRect = new RectF(0, 0, getWidth(), getHeight());
    }
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_MOVE:
//                screenX = (int) event.getX();
//                screenY = (int) event.getY();
//                invalidate();
//                break;
//        }
//        return true;
//
//    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        screenY = 400/ 2;
        screenX = 400 / 2;
        left = screenX - RECT_SIZE;
        right = screenX + RECT_SIZE;
        top = screenY - RECT_SIZE;
        bottom = screenY + RECT_SIZE;
        setMeasuredDimension(400, 400);
    }
}
