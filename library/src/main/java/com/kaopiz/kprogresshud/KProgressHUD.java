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

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

public class KProgressHUD {

    public enum Style {
        INDETERMINATE, DETERMINATE, ANNULAR_DETERMINATE, BAR_DETERMINATE, CUSTOM_VIEW
    }

    private ProgressDialog mProgressDialog;
    private Style mStyle;
    private float mDimAmount;
    private int mWindowColor;
    private float mCornerRadius;
    private boolean mCancellable;

    private int mAnimateSpeed;
    private String mLabel;
    private String mDetailsLabel;

    private int mMaxProgress;
    private boolean mIsAutoDismiss;

    private View mCustomView;
    private boolean mIsCustomViewDeterminate;

    public KProgressHUD(Context context) {
        mProgressDialog = new ProgressDialog(context);
        mStyle = Style.INDETERMINATE;
        mDimAmount = 0;
        //noinspection deprecation
        mWindowColor = context.getResources().getColor(R.color.kprogresshud_default_color);
        mAnimateSpeed = 1;
        mCornerRadius = 10;
        mIsAutoDismiss = true;
    }

    /**
     * Create a new HUD with default indeterminate style. Have same effect as the constructor.
     * For convenient only.
     * @param context Activity context that the HUD bound to
     * @return An unique HUD instance
     */
    public static KProgressHUD create(Context context) {
        return new KProgressHUD(context);
    }

    /**
     * Specify the HUD style
     * @param style One of the following KProgressHUD.Style values:
     *              INDETERMINATE, DETERMINATE, ANNULAR_DETERMINATE, BAR_DETERMINATE, CUSTOM_VIEW
     * @return Current HUD
     */
    public KProgressHUD setStyle(Style style) {
        mStyle = style;
        return this;
    }

    /**
     * Specify the dim area around the HUD, like in Dialog
     * @param dimAmount May take value from 0 to 1.
     *                  0 means no dimming, 1 mean darkness
     * @return Current HUD
     */
    public KProgressHUD setDimAmount(float dimAmount) {
        if (dimAmount >= 0 && dimAmount <= 1) {
            mDimAmount = dimAmount;
        }
        return this;
    }

    /**
     * Specify the HUD background color
     * @param color ARGB color
     * @return Current HUD
     */
    public KProgressHUD setWindowColor(int color) {
        mWindowColor = color;
        return this;
    }

    /**
     * Specify corner radius of the HUD (default is 10)
     * @param radius Corner radius in dp
     * @return Current HUD
     */
    public KProgressHUD setCornerRadius(float radius) {
        mCornerRadius = radius;
        return this;
    }

    /**
     * Change animate speed relative to default. Only have effect when use with indeterminate style
     * @param scale 1 is default, 2 means double speed, 0.5 means half speed..etc.
     * @return Current HUD
     */
    public KProgressHUD setAnimationSpeed(int scale) {
        mAnimateSpeed = scale;
        return this;
    }

    /**
     * Optional label to be displayed on the HUD
     * @return Current HUD
     */
    public KProgressHUD setLabel(String label) {
        mLabel = label;
        return this;
    }

    /**
     * Optional detail description to be displayed on the HUD
     * @return Current HUD
     */
    public KProgressHUD setDetailsLabel(String detailsLabel) {
        mDetailsLabel = detailsLabel;
        return this;
    }

    /**
     * Max value for use in one of the determinate styles
     * @return Current HUD
     */
    public KProgressHUD setMaxProgress(int maxProgress) {
        mMaxProgress = maxProgress;
        return this;
    }

    /**
     * Set current progress. Only have effect when use with a determinate style, or a custom
     * view which implements Progress interface.
     */
    public void setProgress(int progress) {
        if (mStyle == Style.INDETERMINATE)
            return;
        if (mStyle == Style.CUSTOM_VIEW && !mIsCustomViewDeterminate)
            return;

        mProgressDialog.setProgress(progress);
        if (mIsAutoDismiss && mProgressDialog.isShowing() && progress >= mMaxProgress) {
            mProgressDialog.dismiss();
        }
    }

    /**
     * Provide a custom view to be displayed. Only have effect with the CUSTOM_VIEW style.
     * @param view Must not be null
     * @return Current HUD
     */
    public KProgressHUD setCustomView(View view) {
        if (view != null) {
            mCustomView = view;
            mIsCustomViewDeterminate = view instanceof Progress;
        } else {
            throw new RuntimeException("Custom view must not be null!");
        }
        return this;
    }

    /**
     * Specify whether this HUD can be cancelled by using back button (default is false)
     * @return Current HUD
     */
    public KProgressHUD setCancellable(boolean isCancellable) {
        mCancellable = isCancellable;
        return this;
    }

    /**
     * Specify whether this HUD closes itself if progress reaches max. Default is true.
     * @return Current HUD
     */
    public KProgressHUD setAutoDismiss(boolean isAutoDismiss) {
        mIsAutoDismiss = isAutoDismiss;
        return this;
    }

    public KProgressHUD show() {
        if (mProgressDialog != null && !mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
        return this;
    }

    public void dismiss() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    private class ProgressDialog extends Dialog {

        private Progress mProgressView;

        public ProgressDialog(Context context) {
            super(context);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.kprogresshud_hud);

            Window window = getWindow();
            window.setBackgroundDrawable(new ColorDrawable(0));
            window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.dimAmount = mDimAmount;
            window.setAttributes(layoutParams);

            setCanceledOnTouchOutside(false);
            setCancelable(mCancellable);

            initViews();
        }

        private void initViews() {
            BackgroundLayout background = (BackgroundLayout) findViewById(R.id.background);
            background.setBaseColor(mWindowColor);
            background.setCornerRadius(mCornerRadius);

            FrameLayout containerFrame = (FrameLayout) findViewById(R.id.container);
            View indicatorView = null;
            switch (mStyle) {
                case INDETERMINATE:
                    IndeterminateView view = new IndeterminateView(getContext());
                    view.setAnimationSpeed(mAnimateSpeed);
                    indicatorView = view;
                    break;
                case DETERMINATE:
                    DeterminateView determinateView = new DeterminateView(getContext());
                    indicatorView = determinateView;
                    mProgressView = determinateView;
                    break;
                case ANNULAR_DETERMINATE:
                    AnnularView annularView = new AnnularView(getContext());
                    indicatorView = annularView;
                    mProgressView = annularView;
                    break;
                case BAR_DETERMINATE:
                    BarView barView = new BarView(getContext());
                    indicatorView = barView;
                    mProgressView = barView;
                    break;
                case CUSTOM_VIEW:
                    if (mCustomView == null)
                        throw new RuntimeException("You need to provide a custom view!");
                    indicatorView = mCustomView;
                    if (mIsCustomViewDeterminate) {
                        mProgressView = (Progress) mCustomView;
                    }
                    break;
            }
            int wrapParam = ViewGroup.LayoutParams.WRAP_CONTENT;
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(wrapParam, wrapParam);
            containerFrame.addView(indicatorView, params);

            if (mProgressView != null) {
                mProgressView.setMax(mMaxProgress);
            }

            if (mLabel != null) {
                TextView labelText = (TextView) findViewById(R.id.label);
                labelText.setText(mLabel);
                labelText.setVisibility(View.VISIBLE);
            }
            if (mDetailsLabel != null) {
                TextView detailsText = (TextView) findViewById(R.id.details_label);
                detailsText.setText(mDetailsLabel);
                detailsText.setVisibility(View.VISIBLE);
            }
        }

        public void setProgress(int progress) {
            if (mProgressView != null) {
                mProgressView.setProgress(progress);
            }
        }
    }
}
