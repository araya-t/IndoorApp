package com.estimote.indoorapp.Controller.Parka;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.estimote.indoorapp.Adapter.CsvRowListAdapter;
import com.estimote.indoorapp.Model.Parka.CsvRow;
import com.estimote.indoorapp.R;

import java.util.List;


public class ShowCsvDataActivity extends AppCompatActivity {
    ListView listViewCsvRow;
    CsvRowListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_csv_data);

        initInstances();

        Bundle extra = getIntent().getBundleExtra("extra");
        List<CsvRow> csvRows = (List<CsvRow>) extra.getSerializable("csvRows");

//        if (csvRows.size() != 0) {
//            int i = 0;
//            while (i < csvRows.size()) {
//                Log.d("readfromcsvfile",
//                        String.format("row %s|%s: %s, %s, %s, %s, %s, %s, %s ,%s",
//                                i + 1
//                                , csvRows.get(i).getCountRow()
//                                , csvRows.get(i).getMillisec()
//                                , csvRows.get(i).getTimeStamp()
//                                , csvRows.get(i).getAcce_x()
//                                , csvRows.get(i).getAcce_y()
//                                , csvRows.get(i).getAcce_z()
//                                , csvRows.get(i).is_stop_engine()
//                                , csvRows.get(i).getX_position()
//                                , csvRows.get(i).getY_position()));
//                i++;
//            }
//
//        }

        if (csvRows != null){
            listAdapter.setCsvRows(csvRows);
//            listAdapter.notifyDataSetChanged();
        }

    }

    private void initInstances() {

        listViewCsvRow = (ListView) findViewById(R.id.listView);
        listAdapter = new CsvRowListAdapter();
        listViewCsvRow.setAdapter(listAdapter);

    }
}
