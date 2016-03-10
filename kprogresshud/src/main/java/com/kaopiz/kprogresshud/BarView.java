/*
 *    Copyright 2015 Kaopiz Software Co., Ltd.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.kaopiz.kprogresshud;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

class BarView extends View implements Determinate {

    private Paint mOuterPaint;
    private Paint mInnerPaint;
    private RectF mBound;
    private RectF mInBound;
    private int mMax = 100;
    private int mProgress = 0;
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
        mOuterPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mOuterPaint.setStyle(Paint.Style.STROKE);
        mOuterPaint.setStrokeWidth(Helper.dpToPixel(2, getContext()));
        mOuterPaint.setColor(Color.WHITE);

        mInnerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mInnerPaint.setStyle(Paint.Style.FILL);
        mInnerPaint.setColor(Color.WHITE);

        mBoundGap = Helper.dpToPixel(5, getContext());
        mInBound = new RectF(mBoundGap, mBoundGap,
                (getWidth() - mBoundGap) * mProgress / mMax, getHeight() - mBoundGap);

        mBound = new RectF();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int padding = Helper.dpToPixel(2, getContext());
        mBound.set(padding, padding, w - padding, h - padding);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRoundRect(mBound, mBound.height()/2, mBound.height()/2, mOuterPaint);
        canvas.drawRoundRect(mInBound, mInBound.height()/2, mInBound.height()/2, mInnerPaint);
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
