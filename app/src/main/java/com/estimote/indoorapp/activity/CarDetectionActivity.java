package com.estimote.indoorapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.estimote.indoorapp.R;
import com.estimote.indoorapp.model.CsvReader;
import com.estimote.indoorapp.utils.CsvRow;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class CarDetectionActivity extends AppCompatActivity implements View.OnClickListener {
    private List<CsvRow> csvRows = new ArrayList();
    private CsvReader csvReader = new CsvReader();
    private Button btnFile1;
    private Button btnFile3;
    private boolean isReadFinish;
    private int countRow;
    private long millisec;
    private String timeStamp;
    private long timeStampLong;
    private double acce_x;
    private double acce_y;
    private double acce_z;
    private boolean is_still;
    private boolean is_stop_engine;
    private double x_position;
    private double y_position;
    private boolean isChangeGmsStatus;
    private String fcmToken;
    private double previousAcceX = 1;
    private double previousAcceY = 1;
    private double previousAcceZ = 1;
    private List<CsvRow> csvRowsForCheckAcce = new ArrayList();
//    int countIsStill = 0;
//    int countIsStopEngine = 0;
//    int previousCountRow = 0;
//    int previousCountRowStill = 0;
//    int previousCountRowStop = 0;
//    boolean isFirstValueStill = true;
//    boolean isFirstValueStop = true;
//    boolean isCarStopped = false;

    private List<CsvRow> csvRowsForCheckStill = new ArrayList();
    private List<CsvRow> csvRowsForCheckStop = new ArrayList();
    private List<CsvRow> csvRowRealSituation = new ArrayList();
    int countIsStill = 0;
    int countIsStopEngine = 0;
    int previousCountRowStill = 0;
    int previousCountRowStop = 0;
    boolean isFirstValueStill = true;
    boolean isFirstValueStop = true;
    boolean isCarStopped = false;
    int indexStillStartWhenDetectStop= 0;
    int indexStillEnd= 0;
    int indexStop = 0;


//    private boolean isPrintStill;
//    private boolean isPrintStopEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_detection);

        initInstances();

        btnFile1.setOnClickListener(this);
        btnFile3.setOnClickListener(this);
    }

    private void initInstances() {
        btnFile1 = findViewById(R.id.btnFile1);
        btnFile3 = findViewById(R.id.btnFile3);

        Intent intent = getIntent();
        Bundle extra = intent.getExtras();
        fcmToken = extra.getString("fcmToken");
        Log.i("TagToken", "CarDetectionActivity \ntoken: " + fcmToken);
    }

    @Override
    public void onClick(View v) {

        if (v == btnFile1) {
            String fileName = "BeaconAccelerometerData_2018-10-30_14:40:17_sampling_3microsec.csv";
                /** pass โอเคเลย**/

//            String fileName = "BeaconAccelerometerData_2018-10-30_14:42:34_sampling_3microsec.csv";
                /** ไฟล์น่าจะเสีย **/

//            String fileName = "BeaconAccelerometerData_2018-10-30_14:50:51_sampling_3microsec.csv";


            btnFile1.setText(fileName);

            if (fileName != null) {

                try {
                    csvRows = csvReader.readCsv(fileName);
                    isCarStopped = false;
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }

            if (csvRows.size() != 0) {
                int i = 0;
                while (i < csvRows.size()) {
                    countRow = csvRows.get(i).getCountRow();
                    millisec = csvRows.get(i).getMillisec();
                    timeStamp = csvRows.get(i).getTimeStamp();
                    timeStampLong = csvRows.get(i).getTimeStampLong();
                    acce_x = csvRows.get(i).getAcce_x();
                    acce_y = csvRows.get(i).getAcce_y();
                    acce_z = csvRows.get(i).getAcce_z();
                    is_still = csvRows.get(i).is_still();
                    is_stop_engine = csvRows.get(i).is_stop_engine();
                    x_position = csvRows.get(i).getX_position();
                    y_position = csvRows.get(i).getY_position();
                    isChangeGmsStatus = csvRows.get(i).isChangeGmsStatus();

                    Log.d("read from csv file",
                            String.format("row %s|%s: %s, %s, %s, %s, %s, %s, %s, %s ,%s",
                                    i + 2, countRow,
                                    millisec, timeStamp,
                                    acce_x, acce_y, acce_z,
                                    is_still, is_stop_engine
                                    , x_position, y_position));

                    double differentValueX = Math.abs(previousAcceX - acce_x);
                    double differentValueY = Math.abs(previousAcceY - acce_y);
                    double differentValueZ = Math.abs(previousAcceZ - acce_z);

//                    checkIsCarEngineStopped(differentValueX, differentValueY, differentValueZ, countRow, csvRows.get(i));
                    checkIsCarStillStop(differentValueX, differentValueY, differentValueZ, countRow, csvRows.get(i));

                    previousAcceX = acce_x;
                    previousAcceY = acce_y;
                    previousAcceZ = acce_z;

                    if (is_still == true && is_stop_engine == false) {
//                        Log.d("CarIsStill", "-----------> Row " + countRow + " | car IS STILL");
                    }

                    if (is_stop_engine == true) {
//                        Log.d("CarStopEngine", "-------------------> Row " + countRow + " | car IS STOPPED ENGINE");
                    }

                    i++;
                }

                if (i == csvRows.size()) {
//                    isReadFinish = true;
                    csvRows.clear();
                }

                CsvRow temp = new CsvRow();
            }
        }

        if (v == btnFile3) {
            String fileName = "BeaconAccelerometerData_2018-10-30_14:44:09_sampling_3microsec.csv";
            if (fileName != null) {

                try {
                    csvRows = csvReader.readCsv(fileName);
                    isCarStopped = false;
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }

            if (csvRows.size() != 0) {
                int i = 0;
                while (i < csvRows.size()) {
                    countRow = csvRows.get(i).getCountRow();
                    millisec = csvRows.get(i).getMillisec();
                    timeStamp = csvRows.get(i).getTimeStamp();
                    timeStampLong = csvRows.get(i).getTimeStampLong();
                    acce_x = csvRows.get(i).getAcce_x();
                    acce_y = csvRows.get(i).getAcce_y();
                    acce_z = csvRows.get(i).getAcce_z();
                    is_still = csvRows.get(i).is_still();
                    is_stop_engine = csvRows.get(i).is_stop_engine();
                    x_position = csvRows.get(i).getX_position();
                    y_position = csvRows.get(i).getY_position();
                    isChangeGmsStatus = csvRows.get(i).isChangeGmsStatus();

                    Log.d("read from csv file",
                            String.format("row %s|%s: %s, %s, %s, %s, %s, %s, %s, %s ,%s",
                                    i + 2, countRow,
                                    millisec, timeStamp,
                                    acce_x, acce_y, acce_z,
                                    is_still, is_stop_engine
                                    , x_position, y_position));

                    double differentValueX = Math.abs(previousAcceX - acce_x);
                    double differentValueY = Math.abs(previousAcceY - acce_y);
                    double differentValueZ = Math.abs(previousAcceZ - acce_z);

//                    checkIsCarEngineStopped(differentValueX, differentValueY, differentValueZ, countRow, csvRows.get(i));
                    checkIsCarStillStop(differentValueX, differentValueY, differentValueZ, countRow, csvRows.get(i));

                    previousAcceX = acce_x;
                    previousAcceY = acce_y;
                    previousAcceZ = acce_z;

                    if (is_still == true && is_stop_engine == false) {
//                        Log.d("CarIsStill", "-----------> Row " + countRow + " | car IS STILL");
                    }

                    if (is_stop_engine == true) {
//                        Log.d("CarStopEngine", "-------------------> Row " + countRow + " | car IS STOPPED ENGINE");
                    }

                    i++;
                }

                if (i == csvRows.size()) {
//                    isReadFinish = true;
                    csvRows.clear();
                }
            }
        }
    }

    private void checkIsCarStillStop(double differentValueX, double differentValueY, double differentValueZ
                                 , int currentCountRow, CsvRow csvRow) {
        // currentCountRow value - previous value
        // The main variable that we will use is acce_x and acce_y
        // initial value of previousAcceX, previousAcceY, previousAcceZ = 1

        /** Check whether car is stopped or not**/
        if (differentValueX <= 0.06 && differentValueY <= 0.06) {

            Log.d("differentValue", "differentValueX: " + differentValueX + " || differentValueY: " + differentValueY);
            Log.d("CarIsStop", "-----------> Row " + countRow + " | car IS STOPPP");

            if (isFirstValueStop) {
                countIsStopEngine++;
                csvRowsForCheckAcce.add(csvRow);
                isFirstValueStop = false;
                previousCountRowStop = currentCountRow;

//                Log.d("checkIsCarStill", "------------- First Value -------------");

            }else{

                Log.d("previousCountRow", "currentCountRow - previousCountRowStop = " + (currentCountRow - previousCountRowStop));

                if (currentCountRow - previousCountRowStop == 1) {
                    countIsStopEngine++;
                    csvRowsForCheckStop.add(csvRow);
                    previousCountRowStop = currentCountRow;

                    Log.d("checkIsCarStillStop", " row(" + currentCountRow
                            + ")------------- ("+ differentValueX + ", " + differentValueY + " )countIsStopEngine (" + countIsStopEngine + ") Value -------------");

                }else if (currentCountRow - previousCountRowStop >= 3) {
                    csvRowsForCheckStop.clear();
                    countIsStopEngine = 0;
                    previousCountRowStop = 0;
                    isFirstValueStop = true;
                }else{
                    csvRowsForCheckStop.add(csvRow);
                }

            }

            if (countIsStopEngine == 16 && isCarStopped == false) {
                isCarStopped = true;
                Toast.makeText(this, "-- The car is STOP --", Toast.LENGTH_SHORT).show();
                Log.d("checkIsCarStillStop", "--------------- The car is STOP ---------------");
                Toast.makeText(this, "SEND DATA TO SERVER \n|| @ROW " + csvRow.getCountRow() , Toast.LENGTH_SHORT).show();

                Log.d("checkIsCarStillStop", "csvRowsForCheckStill.size() : " + csvRowsForCheckStill.size()
                                                    + "\nfrom row = " + csvRowsForCheckStill.get(0).getCountRow()
                                                    + " - " + csvRowsForCheckStill.get(csvRowsForCheckStill.size()-1).getCountRow());
//                indexStillStartWhenDetectStop = csvRowsForCheckStill.get(0).getCountRow();
//                indexStillEnd = csvRowsForCheckStill.get(csvRowsForCheckStill.size()-1).getCountRow();


                if(csvRowsForCheckStill.size() != 0){
                    indexStillStartWhenDetectStop = csvRowsForCheckStill.get(0).getCountRow() - 1;
                    Log.d("indexStillStartWhenStop", "indexStillStartWhenStop = " + (csvRowsForCheckStill.get(0).getCountRow() - 1) );

                }else{
                    Log.d("indexStillStartWhenStop", "--------------- csvRowsForCheckStill.size == 0 ---------------");
                    indexStillStartWhenDetectStop = countRow - 10;
                }

                indexStop = countRow - 1;
                Log.d("indexStillStartWhenStop", " indexStop = " + (countRow - 1) );


                double averageXPosition = calculateAverageXPosition(indexStillStartWhenDetectStop, indexStop) + 17;
                double averageYPosition = calculateAverageYPosition(indexStillStartWhenDetectStop, indexStop);

                Log.d("sendDataToAppServer", "timestampLong1000 = " + timeStampLong/1000L
                        + "\n, averageXPosition = " + averageXPosition
                        + "\n, averageYPosition = " + averageYPosition + "\n fcmToken = " + fcmToken);

            }



        /** Check whether car is still or not**/
        }else if ( ((differentValueX <= 0.16) && differentValueY <= 0.16) || differentValueY <= 0.16 ) {

            Log.d("differentValue", "differentValueX: " + differentValueX + " || differentValueY: " + differentValueY);
            Log.d("CarIsStill", "-----------> Row " + countRow + " | car IS STILL");

            if (isFirstValueStill) {
                countIsStill++;
                csvRowsForCheckStill.add(csvRow);
                isFirstValueStill = false;
                previousCountRowStill = currentCountRow;
//                Log.d("checkIsCarStill", "------------- First Value -------------");
            }else{

                Log.d("previousCountRow", "currentCountRow - previousCountRow = " + (currentCountRow - previousCountRowStill));

                if (currentCountRow - previousCountRowStill <= 1) {
                    countIsStill++;
                    csvRowsForCheckStill.add(csvRow);
                    previousCountRowStill = currentCountRow;

//                    Log.d("checkIsCarStillStop", " row(" + currentCountRow + ")------------- countIsStill (" + countIsStill + ") Value -------------");
                    Log.d("checkIsCarStillStop", " row(" + currentCountRow
                            + ")------------- ("+ differentValueX + ", " + differentValueY + " )countIsStill (" + countIsStill + ") Value -------------");
                } else if (currentCountRow - previousCountRowStill >5 ){
                    Log.d("checkIsCarStillStop", "--------------- the || Clear csvRowsForCheckStill");
                    csvRowsForCheckStill.clear();
                    countIsStill = 0;
                    previousCountRowStill = 0;
                    isFirstValueStill = true;
                }else{
                    csvRowsForCheckStill.add(csvRow);
                }

            }

            if (countIsStill == 16 && isCarStopped == false) {
                Toast.makeText(this, "-- The car is STILL --", Toast.LENGTH_SHORT).show();
                Log.d("checkIsCarStillStop", "--------------- The car is STILL ---------------");

            }
        }

    }

    private double calculateAverageXPosition(int indexStillStartWhenDetectStop,int indexStop){
        int i = indexStillStartWhenDetectStop;
        int countAmount = 0;
        double sum = 0.0;
        double averageX = -100.0;

        while ( i <= indexStop) {
            sum += csvRows.get(i).getX_position();
            Log.d("calculateAverage","Row " + i
                    + "| countAmount="+ (countAmount+1)
                    + " |x_position = " + csvRows.get(i).getX_position()
                    + " | sum = " + sum);
            i++;
            countAmount++;
        }

        if(sum != 0.0){
            averageX = sum/countAmount;
        }

        return averageX;
    }

    private double calculateAverageYPosition(int indexStillStartWhenDetectStop, int indexStop){
        int i = indexStillStartWhenDetectStop;
        int countAmount = 0;
        double sum = 0.0;
        double averageY = -100.0;

        while ( i <= indexStop) {
            sum += csvRows.get(i).getY_position();
            Log.d("calculateAverage","Row " + i
                    + "| countAmount="+ (countAmount + 1)
                    + " |y_position = " + csvRows.get(i).getY_position()
                    + " | sum = " + sum);
            i++;
            countAmount++;
        }

        if(sum != 0.0){
            averageY = sum/countAmount;
        }

        return averageY;
    }

    private void checkIsCarEngineStopped(double differentValueX, double differentValueY, double differentValueZ
            , int currentCountRow, CsvRow csvRow) {

        // currentCountRow value - previous value
        // The main variable that we will use is acce_x and acce_y
        // initial value of previousAcceX, previousAcceY, previousAcceZ = 1



    }

}
