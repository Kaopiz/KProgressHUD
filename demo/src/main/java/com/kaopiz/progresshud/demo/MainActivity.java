package com.kaopiz.progresshud.demo;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.kaopiz.progresshud.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button indeterminate = (Button) findViewById(R.id.indeterminate);
        indeterminate.setOnClickListener(this);

        Button labelIndeterminate = (Button) findViewById(R.id.label_indeterminate);
        labelIndeterminate.setOnClickListener(this);

        Button detailIndeterminate = (Button) findViewById(R.id.detail_indeterminate);
        detailIndeterminate.setOnClickListener(this);

        Button determinate = (Button) findViewById(R.id.determinate);
        determinate.setOnClickListener(this);

        Button annularDeterminate = (Button) findViewById(R.id.annular_determinate);
        annularDeterminate.setOnClickListener(this);

        Button barDeterminate = (Button) findViewById(R.id.bar_determinate);
        barDeterminate.setOnClickListener(this);

        Button customView = (Button) findViewById(R.id.custom_view);
        customView.setOnClickListener(this);

        Button dimBackground = (Button) findViewById(R.id.dim_background);
        dimBackground.setOnClickListener(this);

        Button customColor = (Button) findViewById(R.id.custom_color_animate);
        customColor.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        KProgressHUD hud = null;
        switch (v.getId()) {
            case R.id.indeterminate:
                hud = KProgressHUD.create(this)
                        .setStyle(KProgressHUD.Style.INDETERMINATE);
                break;
            case R.id.label_indeterminate:
                hud = KProgressHUD.create(this)
                        .setStyle(KProgressHUD.Style.INDETERMINATE)
                        .setLabel("Please wait")
                        .setCancellable(true);
                break;
            case R.id.detail_indeterminate:
                hud = KProgressHUD.create(this)
                        .setStyle(KProgressHUD.Style.INDETERMINATE)
                        .setLabel("Please wait")
                        .setDetailsLabel("Downloading data");
                break;
            case R.id.determinate:
                break;
            case R.id.annular_determinate:
                break;
            case R.id.bar_determinate:
                break;
            case R.id.custom_view:
                ImageView imageView = new ImageView(this);
                imageView.setImageResource(R.mipmap.ic_launcher);
                hud = KProgressHUD.create(this)
                        .setStyle(KProgressHUD.Style.CUSTOM_VIEW)
                        .setCustomView(imageView)
                        .setLabel("This is a custom view");
                break;
            case R.id.dim_background:
                hud = KProgressHUD.create(this)
                        .setStyle(KProgressHUD.Style.INDETERMINATE)
                        .setDimAmount(0.6f);
                break;
            case R.id.custom_color_animate:
                //noinspection deprecation
                hud = KProgressHUD.create(this)
                        .setStyle(KProgressHUD.Style.INDETERMINATE)
                        .setWindowColor(getResources().getColor(R.color.colorPrimary))
                        .setAnimateSpeed(2);
                break;
        }
        if (hud != null) {
            hud.show();
            Handler handler = new Handler();
            final KProgressHUD finalHud = hud;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    finalHud.dismiss();
                }
            }, 4000);
        }
    }
}
