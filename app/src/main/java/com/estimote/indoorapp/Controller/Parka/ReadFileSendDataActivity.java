package com.estimote.indoorapp.Controller.Parka;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.estimote.indoorapp.Model.Parka.CsvReader;
import com.estimote.indoorapp.R;


public class ReadFileSendDataActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btnFileName1;
    private Button btnFileName2;
    private String fileName;
    private CsvReader csvReader;

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
            fileName = "";
//            List<String> list = csvReader.readCSV(fileName);


        }

        if(v == btnFileName1){

        }
    }
}
