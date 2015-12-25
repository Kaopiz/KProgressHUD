package com.kaopiz.kprogresshud;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class BarView extends View implements Progress{
    private Paint outerPaint;
    private Paint innerPaint;
    private RectF mBound;
    private RectF mInBound;
    private int mMax = 100;
    private int mProgress = 0;
    public BarView(Context context) {
        super(context);
        init();
    }

    public BarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        outerPaint = new Paint();
        outerPaint.setStyle(Paint.Style.STROKE);
        outerPaint.setStrokeWidth(5);
        outerPaint.setColor(Color.WHITE);

        innerPaint = new Paint();
        innerPaint.setStyle(Paint.Style.FILL);
        innerPaint.setColor(Color.WHITE);

        mInBound = new RectF(20, 20, (getWidth()-20)*mProgress/mMax, getHeight()-20);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mBound = new RectF(10, 10, w-10, h-10);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRoundRect(mBound, 30, 30, outerPaint);
        canvas.drawRoundRect(mInBound, 30, 30, innerPaint);
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(500,70);
    }

    @Override
    public void setMax(int max) {
        this.mMax = max;
    }

    @Override
    public void setProgress(int progress) {
        this.mProgress = progress;
        mInBound = new RectF(20, 20, (getWidth()-20)*mProgress/mMax, getHeight()-20);
    }
}
