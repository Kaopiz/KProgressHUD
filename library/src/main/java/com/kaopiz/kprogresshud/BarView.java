package com.kaopiz.kprogresshud;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class BarView extends View implements Progress {

    private Paint outerPaint;
    private Paint innerPaint;
    private RectF mBound;
    private RectF mInBound;
    private int mMax = 100;
    private int mProgress = 0;
    private float mRadius;
    private float mBoundGap;

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

    private void init() {
        outerPaint = new Paint();
        outerPaint.setStyle(Paint.Style.STROKE);
        outerPaint.setStrokeWidth(Helper.dpToPixel(2, getContext()));
        outerPaint.setColor(Color.WHITE);

        innerPaint = new Paint();
        innerPaint.setStyle(Paint.Style.FILL);
        innerPaint.setColor(Color.WHITE);

        mBoundGap = Helper.dpToPixel(5, getContext());
        mInBound = new RectF(mBoundGap, mBoundGap,
                (getWidth() - mBoundGap) * mProgress / mMax, getHeight() - mBoundGap);

        mRadius = Helper.dpToPixel(10, getContext());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int padding = Helper.dpToPixel(2, getContext());
        mBound = new RectF(padding, padding, w - padding, h - padding);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRoundRect(mBound, mRadius, mRadius, outerPaint);
        canvas.drawRoundRect(mInBound, mRadius, mRadius, innerPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthDimension = Helper.dpToPixel(100, getContext());
        int heightDimension = Helper.dpToPixel(20, getContext());
        setMeasuredDimension(widthDimension, heightDimension);
    }

    @Override
    public void setMax(int max) {
        this.mMax = max;
    }

    @Override
    public void setProgress(int progress) {
        this.mProgress = progress;
        mInBound.set(mBoundGap, mBoundGap,
                (getWidth() - mBoundGap) * mProgress / mMax, getHeight() - mBoundGap);
        invalidate();
    }
}
