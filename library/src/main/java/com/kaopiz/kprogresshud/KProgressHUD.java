package com.kaopiz.kprogresshud;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

public class KProgressHUD {

    public enum Style {
        INDETERMINATE, LABEL_INDETERMINATE, DETAIL_INDETERMINATE,
        DETERMINATE, ANNULAR_DETERMINATE, BAR_DETERMINATE
    }

    private ProgressDialog mProgressDialog;
    private Style mStyle;
    private float mDimAmount;
    private Color mWindowColor;
    private float mCornerRadius;

    private int mAnimateSpeed;
    private String mLabel;
    private String mDetailsLabel;

    private int mMaxProgress;
    private int mProgress;

    private static KProgressHUD sInstance;

    private KProgressHUD(Context context) {
        mProgressDialog = new ProgressDialog(context);
        init();
    }

    private void init() {
        // TODO: Init field values.
    }

    public static KProgressHUD getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new KProgressHUD(context);
        }
        return sInstance;
    }

    /*
     * Methods for HUD customizing
     */

    public KProgressHUD setStyle(Style style) {
        mStyle = style;
        return this;
    }

    public KProgressHUD setDimAmount(float dimAmount) {
        mDimAmount = dimAmount;
        return this;
    }

    public KProgressHUD setWindowColor(Color color) {
        mWindowColor = color;
        return this;
    }

    public KProgressHUD setAnimateSpeed(int speed) {
        mAnimateSpeed = speed;
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

    public void show() {
        mProgressDialog.show();
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
        }
    }
}
