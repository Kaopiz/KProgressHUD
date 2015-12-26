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
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

class AnnularView extends View implements Determinate {
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
        whitePaint.setStrokeWidth(Helper.dpToPixel(3, getContext()));
        whitePaint.setColor(Color.WHITE);

        greyPaint = new Paint();
        greyPaint.setStyle(Paint.Style.STROKE);
        greyPaint.setStrokeWidth(Helper.dpToPixel(3, getContext()));
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
        float mAngle = mProgress * 360f / mMax;
        canvas.drawArc(mBound, 270, mAngle, false, whitePaint);
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
