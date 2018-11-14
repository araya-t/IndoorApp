package com.estimote.indoorapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.estimote.indoorapp.model.CsvReader;
import com.estimote.indoorapp.utils.CsvRow;
import com.estimote.indoorapp.R;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


public class ReadFileSendDataActivity extends AppCompatActivity implements View.OnClickListener{
//    private Button btnFileName1;
//    private Button btnFileName2;
    private Button btnScenario1_1;
    private Button btnScenario1_2;
    private Button btnScenario1_3;
    private Button btnScenario1_4;
    private Button btnScenario2_1;
    private Button btnScenario2_2;
    private Button btnScenario3_1;
    private Button btnScenario3_2;
    private Button btnScenario4_1;
    private Button btnScenario4_2;
    private Button btnScenario5_1;
    private Button btnScenario5_2;
    private Button btnScenario6_1;
    private Button btnScenario6_2;
    private String fileName;
    private CsvReader csvReader;
    private List<CsvRow> csvRows;
    private int rowToTrigger;
    private String fcmToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_file_send_data);

        initInstances();

//        btnFileName1.setOnClickListener(this);
//        btnFileName2.setOnClickListener(this);

        btnScenario1_1.setOnClickListener(this);
        btnScenario1_2.setOnClickListener(this);
        btnScenario1_3.setOnClickListener(this);
        btnScenario1_4.setOnClickListener(this);
        btnScenario2_1.setOnClickListener(this);
        btnScenario2_2.setOnClickListener(this);
        btnScenario3_1.setOnClickListener(this);
        btnScenario3_2.setOnClickListener(this);
        btnScenario4_1.setOnClickListener(this);
        btnScenario4_2.setOnClickListener(this);
        btnScenario5_1.setOnClickListener(this);
        btnScenario5_2.setOnClickListener(this);
        btnScenario6_1.setOnClickListener(this);
        btnScenario6_2.setOnClickListener(this);

    }

    private void initInstances() {
//        btnFileName1 = findViewById(R.id.btnFileName1);
//        btnFileName2 = findViewById(R.id.btnFileName2);
        btnScenario1_1 = findViewById(R.id.btnScenario1_1);
        btnScenario1_2 = findViewById(R.id.btnScenario1_2);
        btnScenario1_3 = findViewById(R.id.btnScenario1_3);
        btnScenario1_4 = findViewById(R.id.btnScenario1_4);
        btnScenario2_1 = findViewById(R.id.btnScenario2_1);
        btnScenario2_2 = findViewById(R.id.btnScenario2_2);
        btnScenario3_1 = findViewById(R.id.btnScenario3_1);
        btnScenario3_2 = findViewById(R.id.btnScenario3_2);
        btnScenario4_1 = findViewById(R.id.btnScenario4_1);
        btnScenario4_2 = findViewById(R.id.btnScenario4_2);
        btnScenario5_1 = findViewById(R.id.btnScenario5_1);
        btnScenario5_2 = findViewById(R.id.btnScenario5_2);
        btnScenario6_1 = findViewById(R.id.btnScenario6_1);
        btnScenario6_2 = findViewById(R.id.btnScenario6_2);

        csvReader = new CsvReader();
        csvRows = new ArrayList<>();

        Intent intent = getIntent();
        Bundle extra = intent.getExtras();
        fcmToken = extra.getString("fcmToken");
        Log.i("TagToken","ReadFileSendDataActivity \ntoken: " + fcmToken);
    }

    @Override
    public void onClick(View v) {
//        if(v == btnFileName1){
//            fileName = "BeaconAccelerometerData_2018-09-25_13_47_35_sampling_3microsec.csv";
//            rowToTrigger = 25;
//            int positionId = 322;
//
//            try {
//                csvRows.clear();
//                csvRows = csvReader.readCSV("BeaconSensorCsvFile/",fileName);
//                Log.d("ReadFileSendDataAct","file name 1 --> size: " +csvRows.size() );
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//
//            Bundle extra = new Bundle();
//            extra.putSerializable("csvRows", (Serializable) csvRows);
//
//            startShowCsvDataActivity(extra,rowToTrigger,fcmToken,positionId);
//        }
//
//        if(v == btnFileName2){
//            fileName = "BeaconAccelerometerData_2018-09-25_13_41_04_sampling_3microsec.csv";
//            rowToTrigger = 21;
//            int positionId = 324;
//
//            try {
//                csvRows.clear();
//                csvRows = csvReader.readCSV("BeaconSensorCsvFile/",fileName);
//                Log.d("ReadFileSendDataAct","file name 2 --> size: " +csvRows.size() );
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//
//            Bundle extra = new Bundle();
//            extra.putSerializable("csvRows", (Serializable) csvRows);
//
//            startShowCsvDataActivity(extra,rowToTrigger,fcmToken,positionId);
//        }


/** แก้โค้ด row to trigger ใน อันนี้ กับ ใน ShowCsvDataActivity
 * อย่าลืมใส่ชื่อไฟล์ด้วย **/
        if(v == btnScenario1_1){
            fileName = "BeaconAccelerometerData_2018-10-17_13:17:29_sampling_3microsec.csv";
//            rowToTrigger = 21;
            int positionId = 328;

            try {
                csvRows.clear();
                csvRows = csvReader.readCsvOldVersion("BeaconSensorCsvFile/",fileName);
                Log.d("ReadFileSendDataAct","btnScenario1_1 --> size: " +csvRows.size() );

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Bundle extra = new Bundle();
            extra.putSerializable("csvRows", (Serializable) csvRows);

            startShowCsvDataActivity(extra,rowToTrigger,fcmToken,positionId);
        }

        /** แก้โค้ด row to trigger ใน อันนี้ กับ ใน ShowCsvDataActivity
         * อย่าลืมใส่ชื่อไฟล์ด้วย **/

        if(v == btnScenario1_2){
            fileName = "-------------------------------------------------------------";
            int positionId = 328;

            try {
                csvRows.clear();
                csvRows = csvReader.readCsvOldVersion("BeaconSensorCsvFile/",fileName);
                Log.d("ReadFileSendDataAct","btnScenario1_2 --> size: " +csvRows.size() );

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Bundle extra = new Bundle();
            extra.putSerializable("csvRows", (Serializable) csvRows);

            startShowCsvDataActivity(extra,rowToTrigger,fcmToken,positionId);
        }

        if(v == btnScenario1_3){
            fileName = "--------------------------------------------------";
            int positionId = 328;

            try {
                csvRows.clear();
                csvRows = csvReader.readCsvOldVersion("BeaconSensorCsvFile/",fileName);
                Log.d("ReadFileSendDataAct","btnScenario1_3 --> size: " +csvRows.size() );

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Bundle extra = new Bundle();
            extra.putSerializable("csvRows", (Serializable) csvRows);

            startShowCsvDataActivity(extra,rowToTrigger,fcmToken,positionId);
        }

        if(v == btnScenario1_4){
            fileName = "--------------------------------------------------";
            int positionId = 328;

            try {
                csvRows.clear();
                csvRows = csvReader.readCsvOldVersion("BeaconSensorCsvFile/",fileName);
                Log.d("ReadFileSendDataAct","btnScenario1_4 --> size: " +csvRows.size() );

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Bundle extra = new Bundle();
            extra.putSerializable("csvRows", (Serializable) csvRows);

            startShowCsvDataActivity(extra,rowToTrigger,fcmToken,positionId);
        }

        if(v == btnScenario2_1){
            fileName = "--------------------------------------------------";
            int positionId = 329;

            try {
                csvRows.clear();
                csvRows = csvReader.readCsvOldVersion("BeaconSensorCsvFile/",fileName);
                Log.d("ReadFileSendDataAct","btnScenario2_1 --> size: " +csvRows.size() );

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Bundle extra = new Bundle();
            extra.putSerializable("csvRows", (Serializable) csvRows);

            startShowCsvDataActivity(extra,rowToTrigger,fcmToken,positionId);
        }

        if(v == btnScenario2_2){
            fileName = "--------------------------------------------------";
            int positionId = 329;

            try {
                csvRows.clear();
                csvRows = csvReader.readCsvOldVersion("BeaconSensorCsvFile/",fileName);
                Log.d("ReadFileSendDataAct","btnScenario2_2 --> size: " +csvRows.size() );

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Bundle extra = new Bundle();
            extra.putSerializable("csvRows", (Serializable) csvRows);

            startShowCsvDataActivity(extra,rowToTrigger,fcmToken,positionId);
        }


    }

    public void startShowCsvDataActivity(Bundle extra, int rowToTrigger, String fcmToken, int positionId){
        Intent intent = new Intent(this, ShowCsvDataActivity.class);
        intent.putExtra("extra", extra);
        intent.putExtra("rowToTrigger",rowToTrigger);
        intent.putExtra("fcmToken",fcmToken);
        intent.putExtra("positionId",positionId);

//        csvRows.clear();
        startActivity(intent);
    }




}
