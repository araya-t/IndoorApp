package com.estimote.indoorapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.estimote.indoorapp.R;


public class MainMenuActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnBeaconAcceCsv;
    private Button btnReadFileSendData;
    private Button btnCarDetection;
    private Button btnRealSituation;
    private String fcmToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        initInstances();

        btnBeaconAcceCsv.setOnClickListener(this);
        btnReadFileSendData.setOnClickListener(this);
        btnCarDetection.setOnClickListener(this);
        btnRealSituation.setOnClickListener(this);
    }

    private void initInstances() {
        btnBeaconAcceCsv = findViewById(R.id.btnBeaconAcceCsv);
        btnReadFileSendData = findViewById(R.id.btnReadFileSendData);
        btnCarDetection = findViewById(R.id.btnCarDetection);
        btnRealSituation = findViewById(R.id.btnRealSituation);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        fcmToken = bundle.getString("fcmToken");
        Log.i("TagToken","MainMenuActivity \ntoken: " + fcmToken);
    }

    @Override
    public void onClick(View v) {
        if (v == btnBeaconAcceCsv){
            String locationId = "six-slots-only--floor-10b";
            startActivity(CsvBeaconAcceDataActivity.Companion.createIntent(this,locationId));
        }

        if (v == btnReadFileSendData){
            Intent intentReadFile = new Intent(this, ReadFileSendDataActivity.class);
            Bundle extra = new Bundle();
            extra.putString("fcmToken",fcmToken);
            intentReadFile.putExtras(extra);

            startActivity(intentReadFile);
        }

        if (v == btnCarDetection){
            Intent intentReadFile = new Intent(this, CarDetectionActivity.class);
            Bundle extra = new Bundle();
            extra.putString("fcmToken",fcmToken);
            intentReadFile.putExtras(extra);

            startActivity(intentReadFile);
        }

        if (v == btnRealSituation){
            String locationId = "six-slots-only--floor-10b";

            Intent intentReadFile = new Intent(this, RealSituationActivity.class);
            Bundle extra = new Bundle();
            extra.putString("fcmToken",fcmToken);
            intentReadFile.putExtras(extra);

            startActivity(RealSituationActivity.Companion.createIntent(this,locationId,fcmToken));
//            startActivity(intentReadFile);
        }
    }

}
