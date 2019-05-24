package com.bignerdranch.android.drawgeekbrain;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

public class MyCanvas extends View {

    Paint p;
    int mWidth;
    int mHaight;


    public MyCanvas(Context context) {
        super(context);

        p = new Paint();
        p.setColor(Color.BLACK);
        p.setStyle(Paint.Style.STROKE); //Прозрачно внутри
        p.setStrokeWidth(10f);
        p.setAntiAlias(true);
        p.setSubpixelText(true);
        p.setTextSize(128f);

        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        mWidth = displayMetrics.widthPixels;
        mHaight = displayMetrics.heightPixels;
        Log.i("my", "Haight:" + mHaight + "Width:" + mWidth);
    }

    //Метод запускается прямо перед визуализацие View
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        canvas.drawRect(200f,200f,600f,600f,p);
//        // Избекать сложных операций в onDraw
////        Rect rect = new Rect(200, 200, 500, 400);
////        canvas.drawRect(rect,p);
////
////        canvas.drawText("Hello", 300f, 300f, p);
////        canvas.drawCircle(mWidth/2,mHaight/2,mWidth/2.1f, p);
//
        canvas.drawPath(traingle2(mWidth / 2, mHaight / 2, mWidth / 2), p);
    }

    public Path traingle() {
        Path path = new Path();
        path.reset();
        path.moveTo(400, 400);
        path.lineTo(200, 400);
        path.lineTo(200, 100);
        path.lineTo(400, 400);
        path.close();
        return path;

    }

    public Path traingle2(float startX, float startY, float size) {
        Path path = new Path();
        path.reset();
//        path.moveTo(800,1000);
//        path.lineTo(400,100);

        path.moveTo(startX, startY);
        path.moveTo(startX, startY - size);
        path.lineTo(startX + size, startY + size);
        path.lineTo(startX - size, startY + size);
        path.close();
        return path;
    }
}
