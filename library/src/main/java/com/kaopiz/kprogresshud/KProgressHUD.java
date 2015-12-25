package com.kaopiz.kprogresshud;

import android.app.Activity;
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
    private boolean mIsActivityHandleBackPress;

    private int mAnimateSpeed;
    private String mLabel;
    private String mDetailsLabel;

    private int mMaxProgress;
    private int mProgress;

    private View mCustomView;

    public KProgressHUD(Context context) {
        mProgressDialog = new ProgressDialog(context);
        mStyle = Style.INDETERMINATE;
        mDimAmount = 0;
        //noinspection deprecation
        mWindowColor = context.getResources().getColor(R.color.default_window_color);
        mAnimateSpeed = 1;
        mCornerRadius = 15;
    }

    public static KProgressHUD create(Context context) {
        return new KProgressHUD(context);
    }

    /*
     * Methods for HUD customizing
     */

    public KProgressHUD setStyle(Style style) {
        mStyle = style;
        return this;
    }

    public KProgressHUD setDimAmount(float dimAmount) {
        if (dimAmount >= 0 && dimAmount <= 1) {
            mDimAmount = dimAmount;
        }
        return this;
    }

    public KProgressHUD setWindowColor(int color) {
        mWindowColor = color;
        return this;
    }

    public KProgressHUD setAnimateSpeed(int scale) {
        mAnimateSpeed = scale;
        return this;
    }

    public KProgressHUD setLabel(String label) {
        mLabel = label;
        return this;
    }

    public KProgressHUD setDetailsLabel(String detailsLabel) {
        mDetailsLabel = detailsLabel;
        return this;
    }

    public KProgressHUD setMaxProgress(int maxProgress) {
        mMaxProgress = maxProgress;
        return this;
    }

    public KProgressHUD setProgress(int progress) {
        return this;
    }

    public KProgressHUD setCustomView(View view) {
        if (view != null) {
            mCustomView = view;
        } else {
            throw new RuntimeException("Custom view must not be null!");
        }
        return this;
    }

    public KProgressHUD letActivityHandleBackPress(boolean isAllow) {
        mIsActivityHandleBackPress = isAllow;
        return this;
    }

    public KProgressHUD show() {
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
        return this;
    }

    public void dismiss() {
        mProgressDialog.dismiss();
    }

    private class ProgressDialog extends Dialog {

        public ProgressDialog(Context context) {
            super(context);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.hud);

            Window window = getWindow();
            window.setBackgroundDrawable(new ColorDrawable(0));
            window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.dimAmount = mDimAmount;
            window.setAttributes(layoutParams);

            setCanceledOnTouchOutside(false);

            initViews();
        }

        @Override
        public void onBackPressed() {
            if (mIsActivityHandleBackPress) {
                dismiss();
                ((Activity) getContext()).onBackPressed();
            }
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
                    indicatorView = new DeterminateView(getContext());
                    break;
                case ANNULAR_DETERMINATE:
                    indicatorView = new AnnularView(getContext());
                    break;
                case BAR_DETERMINATE:
                    indicatorView = new BarView(getContext());
                    break;
                case CUSTOM_VIEW:
                    if (mCustomView == null)
                        throw new RuntimeException("You need to provide a custom view!");
                    indicatorView = mCustomView;
                    break;
            }
            int wrapParam = ViewGroup.LayoutParams.WRAP_CONTENT;
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(wrapParam, wrapParam);
            containerFrame.addView(indicatorView, params);

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
    }
}
