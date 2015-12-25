package com.kaopiz.progresshud.demo;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

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
    }

    @Override
    public void onClick(View v) {
        KProgressHUD hud = null;
        switch (v.getId()) {
            case R.id.indeterminate:
                hud = KProgressHUD.create(MainActivity.this)
                        .setStyle(KProgressHUD.Style.INDETERMINATE);
                break;
            case R.id.label_indeterminate:
                hud = KProgressHUD.create(MainActivity.this)
                        .setStyle(KProgressHUD.Style.INDETERMINATE)
                        .setLabel("Please wait");
                break;
            case R.id.detail_indeterminate:
                hud = KProgressHUD.create(MainActivity.this)
                        .setStyle(KProgressHUD.Style.INDETERMINATE)
                        .setLabel("Please wait")
                        .setDetailsLabel("Downloading data");
                break;
            case R.id.determinate:
                hud = KProgressHUD.create(MainActivity.this)
                        .setStyle(KProgressHUD.Style.DETERMINATE)
                        .setLabel("Please wait");
                break;
            case R.id.annular_determinate:
                hud = KProgressHUD.create(MainActivity.this)
                        .setStyle(KProgressHUD.Style.ANNULAR_DETERMINATE)
                        .setLabel("Please wait");
                break;
            case R.id.bar_determinate:
                hud = KProgressHUD.create(MainActivity.this)
                        .setStyle(KProgressHUD.Style.BAR_DETERMINATE)
                        .setLabel("Please wait");
                break;
            case R.id.custom_view:
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
