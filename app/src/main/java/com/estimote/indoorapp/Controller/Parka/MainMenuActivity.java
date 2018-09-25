package com.estimote.indoorapp.Controller.Parka;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.estimote.indoorapp.R;


public class MainMenuActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnBeaconAcceCsv;
    private Button btnReadFileSendData;

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
    }

    @Override
    public void onClick(View v) {
        if (v == btnBeaconAcceCsv){
            String locationId = "six-slots-only--floor-10b";
//            Intent intent = new Intent(this, CsvBeaconAcceDataActivity.class);
            startActivity(CsvBeaconAcceDataActivity.Companion.createIntent(this,locationId));
        }

        if (v == btnReadFileSendData){
            Intent intent = new Intent(this, ReadFileSendDataActivity.class);
            startActivity(intent);
        }
    }
}
