package com.estimote.indoorapp.Activity.Parka;

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
    private String fcmToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        initInstances();

        btnBeaconAcceCsv.setOnClickListener(this);
        btnReadFileSendData.setOnClickListener(this);
    }

    private void initInstances() {
        btnBeaconAcceCsv = findViewById(R.id.btnBeaconAcceCsv);
        btnReadFileSendData = findViewById(R.id.btnReadFileSendData);

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
    }

}
