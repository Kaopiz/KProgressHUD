package com.kaopiz.kprogresshud;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

public class AnnularView extends View implements Progress{
    private Paint whitePaint;
    private Paint greyPaint;
    private RectF mBound;
    private int mMax = 100;
    private int mProgress = 0;
    public AnnularView(Context context) {
        super(context);
        init(context);
    }

    public AnnularView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AnnularView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        whitePaint = new Paint();
        whitePaint.setStyle(Paint.Style.STROKE);
        whitePaint.setStrokeWidth(10);
        whitePaint.setColor(Color.WHITE);

        greyPaint = new Paint();
        greyPaint.setStyle(Paint.Style.STROKE);
        greyPaint.setStrokeWidth(10);
        greyPaint.setColor(ContextCompat.getColor(context, R.color.kprogresshud_grey_color));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mBound = new RectF(10,10, w-10, h-10);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float mAngle = mProgress / mMax * 360;
        canvas.drawArc(mBound, 270, mAngle, false, whitePaint);
        canvas.drawArc(mBound, 270 + mAngle, 360 - mAngle, false, greyPaint);
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(150, 150);
    }

    @Override
    public void setMax(int max) {
        this.mMax = max;
    }

    @Override
    public void setProgress(int progress) {
        this.mProgress = progress;
    }
}
