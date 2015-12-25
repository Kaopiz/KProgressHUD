package com.kaopiz.kprogresshud;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class DeterminateView extends View implements Progress{
    private Paint whitePaint;
    private Paint greyPaint;
    private RectF mBound;
    private int mMax = 100;
    private int mProgress = 0;
    public DeterminateView(Context context) {
        super(context);
        init();
    }

    public DeterminateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DeterminateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        whitePaint = new Paint();
        whitePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        whitePaint.setStrokeWidth(Helper.dpToPixel(2, getContext()));
        whitePaint.setColor(Color.WHITE);

        greyPaint = new Paint();
        greyPaint.setStyle(Paint.Style.STROKE);
        greyPaint.setStrokeWidth(Helper.dpToPixel(2, getContext()));
        greyPaint.setColor(Color.WHITE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mBound = new RectF(10, 10, w-10, h-10);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float mAngle = mProgress * 360f / mMax;
        canvas.drawArc(mBound, 270, mAngle, true, whitePaint);
        canvas.drawArc(mBound, 270 + mAngle, 360 - mAngle, false, greyPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int dimension = Helper.dpToPixel(40, getContext());
        setMeasuredDimension(dimension, dimension);
    }

    @Override
    public void setMax(int max) {
        this.mMax = max;
    }

    @Override
    public void setProgress(int progress) {
        this.mProgress = progress;
        invalidate();
    }
}
