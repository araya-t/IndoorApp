package com.estimote.indoorapp.Controller.Parka;

import android.app.Application;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.estimote.indoorapp.Adapter.CsvRowListAdapter;
import com.estimote.indoorapp.Manager.HttpManager;
import com.estimote.indoorapp.Model.Parka.CsvRow;
import com.estimote.indoorapp.Model.dao.CarPositions;
import com.estimote.indoorapp.Model.dao.Token;
import com.estimote.indoorapp.R;

import java.nio.file.Path;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ShowCsvDataActivity extends AppCompatActivity {
    ListView listViewCsvRow;
    CsvRowListAdapter listAdapter;
    List<CsvRow> csvRows;
    int rowToTrigger;
    boolean is_triggered = false;
    boolean is_stop_engine = false;
    int floor_id = 5018;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_csv_data);

        initInstances();

        Log.d("ShowCsvDataActivity", "After initInstances()");

        Intent intent = getIntent();
        Bundle extra = intent.getBundleExtra("extra");
        csvRows = (List<CsvRow>) extra.getSerializable("csvRows");
        rowToTrigger = intent.getIntExtra("rowToTrigger", 1);

        Log.d("ShowCsvDataActivity", "csvRows size = " + csvRows.size());
        Log.d("ShowCsvDataActivity", "rowToTrigger = " + rowToTrigger);


        if (csvRows.size() != 0) {
            int i = 0;
            while (i < csvRows.size()) {
                Log.d("ShowCsvDataActivity", "csvRows size = " + csvRows.size());
                Log.d("ShowCsvDataActivity"
                        , "index " + i + " | in loop to get element from csvRows --> " + csvRows);

                //if value of 'csvRows.get(i).getCountRow()' == true --> send trigger to GMS
                if (csvRows.get(i).getCountRow() == rowToTrigger && !is_triggered) {
                    System.out.println("{10}getCountRow(" + csvRows.get(i).getCountRow() + ") = rowToTrigger");
                    is_triggered = true;

// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
                    //set data to send
                    boolean is_available = false;
                    Call<Response> callGMS = HttpManager.getInstance()
                            .getServiceGMS().changeStatus
                                    (324, String.valueOf(is_available));

                    //call GMS
                    callGMS.enqueue(new Callback<Response>() {
                        @Override
                        public void onResponse(Call<Response> call, Response<Response> response) {
                            if (response.isSuccessful()) {
                                Log.d("PAsssssss","Passssssss");
                            }
                        }

                        @Override
                        public void onFailure(Call<Response> call, Throwable t) {

                        }
                    });
                }


                /** -------------------------------------------------------------------------------- **/

                //if value of 'csvRows.get(i).is_stop_engine()' == true --> send data to App Server
                if (csvRows.get(i).is_stop_engine() == true && !is_stop_engine) {
                    System.out.println("stop engine = true | " + csvRows.get(i).is_stop_engine());
                    is_stop_engine = true;

                    //set data to send
                    Call<CarPositions> callParka = HttpManager.getInstance()
                            .getServiceParka().sendXYPosition
                                    ("XxOwrl57E9BKjtenCkhDi3TloSvQqcRU"
                                            , Token.getToken()
                                            , csvRows.get(i).getTimeStampLong() / 1000L
                                            , floor_id
                                            , csvRows.get(i).getX_position()
                                            , csvRows.get(i).getY_position());

                    //call Parka
                    callParka.enqueue(new Callback<CarPositions>() {
                        @Override
                        public void onResponse(retrofit2.Call<CarPositions> call, Response<CarPositions> response) {
                            if (response.isSuccessful()) {
                                Log.d("responseSuccess", "Send data to 'Parka' ==> SUCCESS");
                            }
                        }

                        @Override
                        public void onFailure(retrofit2.Call<CarPositions> call, Throwable t) {
                            Log.d("responseSuccess","Failed Error message is " + t);
                        }
                    });
                }

                Log.d("readfromcsvfile",
                        String.format("row %s|%s: %s, %s, %s, %s, %s, %s, %s ,%s",
                                i + 1
                                , csvRows.get(i).getCountRow()
                                , csvRows.get(i).getMillisec()
                                , csvRows.get(i).getTimeStamp()
                                , csvRows.get(i).getAcce_x()
                                , csvRows.get(i).getAcce_y()
                                , csvRows.get(i).getAcce_z()
                                , csvRows.get(i).is_stop_engine()
                                , csvRows.get(i).getX_position()
                                , csvRows.get(i).getY_position()));
                i++;
            }
        }

        if (csvRows != null) {
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
