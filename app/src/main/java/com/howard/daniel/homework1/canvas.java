package com.howard.daniel.homework1;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.pes.androidmaterialcolorpickerdialog.ColorPicker;
import java.util.ArrayList;

public class canvas extends View {

    public Bitmap mBitmap;
    private Canvas mCanvas;
    public Path mPath;
    public Paint mPaint;
    private Paint bitmapPaint;
    private float mX, mY;
    private static final float TOLERANCE = 5;
    Context context;
    ColorPicker cp = new ColorPicker((Activity) getContext(), 0, 0, 0);
    private ArrayList<Path> paths = new ArrayList<Path>();
    private ArrayList<Paint> myColors = new ArrayList<Paint>();


    public canvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        mPath = new Path();
        mPaint = new Paint();
        bitmapPaint = new Paint(Paint.DITHER_FLAG);
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(6f);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap, 0, 0, bitmapPaint);
        mCanvas.drawPath(mPath, mPaint);
        for (Path p : paths) {

            canvas.drawPath(p, mPaint);
            mPaint.setColor(cp.getColor());
            myColors.add(mPaint);

        }

    }
    private void startTouch(float x, float y) {
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }

    private void moveTouch(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOLERANCE || dy >= TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX) / 2,(y + mY) / 2);
            mX = x;
            mY = y;
        }
    }

    public void changeColor(int color) {
        mPaint.setColor(color);
    }

    public void clearPath() {
        mPath.reset();
        invalidate();
    }

    public void clearCanvas() {
        paths.removeAll(paths);
        mBitmap = Bitmap.createBitmap(getWidth() ,getHeight(), Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        mPath = new Path();
        invalidate();
}

    private void upTouch() {
        mPath.lineTo(mX, mY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startTouch(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                moveTouch(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                upTouch();
                invalidate();
                break;

        }
        return true;
    }

}
