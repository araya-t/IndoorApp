package com.estimote.indoorapp.Controller.Parka;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.estimote.indoorapp.Model.Parka.CsvReader;
import com.estimote.indoorapp.Model.Parka.CsvRow;
import com.estimote.indoorapp.R;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.List;


public class ReadFileSendDataActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btnFileName1;
    private Button btnFileName2;
    private String fileName;
    private CsvReader csvReader;
    private List<CsvRow> csvRows;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_file_send_data);

        initInstances();

        btnFileName1.setOnClickListener(this);
        btnFileName2.setOnClickListener(this);

    }

    private void initInstances() {
        btnFileName1 = findViewById(R.id.btnFileName1);
        btnFileName2 = findViewById(R.id.btnFileName2);
        csvReader = new CsvReader();
    }

    @Override
    public void onClick(View v) {
        if(v == btnFileName1){
            fileName = "BeaconAccelerometerData_2018-09-25_13:47:35_sampling_3microsec.csv";

            try {
                csvRows = csvReader.readCSV(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Bundle extra = new Bundle();
            extra.putSerializable("csvRows", (Serializable) csvRows);

            Intent intent = new Intent(this, ShowCsvDataActivity.class);
            intent.putExtra ("extra", extra);
//            csvRows.clear();
            startActivity(intent);
        }

        if(v == btnFileName2){
            fileName = "BeaconAccelerometerData_2018-09-25_13:47:35_sampling_3microsec.csv";

            try {
                csvRows = csvReader.readCSV(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Bundle extra = new Bundle();
            extra.putSerializable("csvRows", (Serializable) csvRows);

            Intent intent = new Intent(this, ShowCsvDataActivity.class);
            intent.putExtra ("extra", extra);
//            csvRows.clear();
            startActivity(intent);
        }
    }




}
