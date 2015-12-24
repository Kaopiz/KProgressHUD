package com.kaopiz.kprogresshud;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class BackgroundLayout extends FrameLayout {

    private float mCornerRadius;
    private Paint mPaint;
    private RectF mRect;

    public BackgroundLayout(Context context) {
        super(context);
        init();
    }

    public BackgroundLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BackgroundLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @SuppressWarnings("deprecation")
    private void init() {
        setBaseColor(getContext().getResources().getColor(R.color.default_window_color));
        // Remove background, we will drawing background using base color
        setBackgroundColor(getContext().getResources().getColor(android.R.color.transparent));
    }

    public void setCornerRadius(float radius) {
        mCornerRadius = radius;
    }

    public void setBaseColor(int color) {
        mPaint = new Paint();
        //noinspection deprecation
        mPaint.setColor(color);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRect = new RectF(0, 0, w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRoundRect(mRect, mCornerRadius, mCornerRadius, mPaint);
    }
}
