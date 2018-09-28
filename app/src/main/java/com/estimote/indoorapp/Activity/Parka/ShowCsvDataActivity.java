package com.estimote.indoorapp.Activity.Parka;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.estimote.indoorapp.Adapter.Parka.CsvRowListAdapter;
import com.estimote.indoorapp.Manager.Contextor;
import com.estimote.indoorapp.Manager.HttpManager;
import com.estimote.indoorapp.Model.Parka.CsvRow;
import com.estimote.indoorapp.Model.dao.CarPosition;
import com.estimote.indoorapp.R;

import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ShowCsvDataActivity extends AppCompatActivity {
    private ListView listViewCsvRow;
    private CsvRowListAdapter listAdapter;
    private List<CsvRow> csvRows;
    private int rowToTrigger;
    private boolean is_triggered = false;
    private boolean is_stop_engine = false;
    private int floor_id = 5018;
    private DecimalFormat dcm = new DecimalFormat("0.000000");
    private String fcmToken;
    private Toast toast;
    private int positionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_csv_data);
        initInstances();

        Log.d("ShowCsvDataActivity", "csvRows size = " + csvRows.size());
        Log.d("ShowCsvDataActivity", "rowToTrigger = " + rowToTrigger);

        if (csvRows.size() != 0) {
            int i = 0;
            while (i < csvRows.size()) {
                Log.d("ShowCsvDataActivity", "csvRows size = " + csvRows.size());

                //if value of 'csvRows.get(i).getCountRow()' == true --> send trigger to GMS
                if (csvRows.get(i).getCountRow() == rowToTrigger && !is_triggered) {
                    System.out.println("{10}getCountRow(" + csvRows.get(i).getCountRow() + ") = rowToTrigger");
                    is_triggered = true;

                    //set data to send
                    String is_available = "False";
                    Call<Void> callGMS = HttpManager.getInstance()
                            .getServiceGMS().changeStatus
                                    (positionId, is_available);
                    Log.d("sendDataTrigger"," position id = " + positionId);

                    //call GMS server
                    callGMS.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                Log.d("responseSuccess"," send trigger to GMS SUCCESS");

                                toast = Toast.makeText(ShowCsvDataActivity.this
                                        , "Send trigger to GMS ==> SUCCESS", Toast.LENGTH_SHORT);

                                showSuccessToast();

                            }else{
                                Log.d("responseSuccess", "\ncan connect with server but failed"
                                        + "\n 'Failed' response.message() -----> " + response.message());

                                toast = Toast.makeText(
                                        ShowCsvDataActivity.this,
                                        response.message(),
                                        Toast.LENGTH_SHORT);
                                showFailedToast();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Log.d("responseSuccess","Failed Error message is " + t);

                            toast = Toast.makeText(ShowCsvDataActivity.this
                                    , "Send trigger to GMS ==> FAILED", Toast.LENGTH_SHORT);
                            showFailedToast();
                        }
                    });
                }

        /** --------------------------------------------------------------------------------------- **/
                //if value of 'csvRows.get(i).is_stop_engine()' == true --> send data to App Server
                if (csvRows.get(i).is_stop_engine() == true && !is_stop_engine) {
                    System.out.println("stop engine = true | " + csvRows.get(i).is_stop_engine());
                    is_stop_engine = true;

                    final String userToken = "XxOwrl57E9BKjtenCkhDi3TloSvQqcRU";
                    final long timestampLong1000 = csvRows.get(i).getTimeStampLong() / 1000L;
                    final double x_position = csvRows.get(i).getX_position();
                    final double y_position = csvRows.get(i).getY_position();

                    Log.d("sendDataToAppServer","timestampLong1000 = " + timestampLong1000
                                                    + ", x_position = " + x_position
                                                    + ", y_position = " + y_position);



                    //set data to send for processing position that user parked
                    Call<CarPosition> callParka = HttpManager.getInstance()
                            .getServiceParka()
                            .sendXYPosition(
                                    userToken
                                    ,x_position
                                    ,y_position
                                    ,floor_id
                                    ,fcmToken
                                    ,System.currentTimeMillis()/1000L);



                    //call Parka server
                    callParka.enqueue(new Callback<CarPosition>() {
                        @Override
                        public void onResponse(retrofit2.Call<CarPosition> call, Response<CarPosition> response) {
                            if (response.isSuccessful()) {
                                Log.d("responseSuccess", "Send data to 'Parka' ==> SUCCESS");

                                toast = Toast.makeText(
                                        Contextor.getInstance().getContext(),
                                        "Send data to 'Parka' ==> SUCCESS",
                                        Toast.LENGTH_SHORT);
                                showSuccessToast();
                            }else{
                                Log.d("responseSuccess", "\ncan connect with server but failed"
                                        + "\n 'Failed' response.message() -----> " + response.message());

                                toast = Toast.makeText(
                                            ShowCsvDataActivity.this,
                                        response.message(),
                                            Toast.LENGTH_SHORT);
                                showFailedToast();
                            }
                        }

                        @Override
                        public void onFailure(retrofit2.Call<CarPosition> call, Throwable t) {
                            Log.d("responseSuccess","Failed Error message is " + t);

                            toast = Toast.makeText(
                                        ShowCsvDataActivity.this,
                                        "Can not contact Server ==> FAILED \n"+t.toString(),
                                        Toast.LENGTH_SHORT);
                            showFailedToast();
                        }
                    });
                }

                Log.d("readfromcsvfile",
                        String.format("row %s|%s: %s, %s, %s, %s, %s, %s, %s ,%s"
                                , i + 1
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

        Intent intent = getIntent();
        Bundle extra = intent.getBundleExtra("extra");
        fcmToken = intent.getStringExtra("fcmToken");
        csvRows = (List<CsvRow>) extra.getSerializable("csvRows");
        rowToTrigger = intent.getIntExtra("rowToTrigger", 1);
        positionId = intent.getIntExtra("positionId",positionId);

        Log.i("TagToken","ShowCsvDataActivity \ntoken: " + fcmToken);

    }

    private void showSuccessToast(){
        View view = toast.getView();
        int backgroundColor = ContextCompat.getColor(ShowCsvDataActivity.this, R.color.mint_cocktail);
        view.setBackgroundColor(backgroundColor);
        TextView text = view.findViewById(android.R.id.message);
        int textColor = ContextCompat.getColor(ShowCsvDataActivity.this, R.color.black);
        text.setTextColor(textColor);
        toast.show();
    }

    private void showFailedToast(){
        View view = toast.getView();
        int backgroundColor = ContextCompat.getColor(ShowCsvDataActivity.this, R.color.red);
        view.setBackgroundColor(backgroundColor);
        TextView text = view.findViewById(android.R.id.message);
        int textColor = ContextCompat.getColor(ShowCsvDataActivity.this, R.color.black);
        text.setTextColor(textColor);
        toast.show();
    }
}
