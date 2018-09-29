package com.estimote.indoorapp.etc;


import android.os.Environment;
import android.util.Log;
import android.widget.TextView;

import com.estimote.indoorsdk_module.cloud.LocationPosition;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BeaconFileWriter {

    BufferedWriter file;
    long startTime;
    int count = 1;
    ArrayList<LocationPosition> listLocationPosition;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss");

    public void startRecording() {
        // Prepare data storage

        String directory = Environment.getExternalStorageDirectory() + "/Download";
        Date now = new Date(System.currentTimeMillis());
        String name = "BeaconData_" + sdf.format(now) + ".csv";

        File filename = new File(directory, name);

        try {
            file = new BufferedWriter(new java.io.FileWriter(filename));
            startTime = System.currentTimeMillis();
            writeHeadFile();
            listLocationPosition = new ArrayList<LocationPosition>();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<LocationPosition> stopRecording() {
        try {
            file.close();
            return listLocationPosition;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

//    public void writeBeaconDataInList(ArrayList<LocationPosition> list) {
//        if (file == null) {
//            return;
//        }
//
//        long millisec = System.currentTimeMillis() - startTime;
//
//        String line = "";
//
//        int i = 0;
//
//
//        for (LocationPosition locationPosition : list) {
//            line = millisec + "," + locationPosition.getX() + "," + locationPosition.getY() + "," + "\n";
//            writeToFile(line);
//            Log.i("Write Beacon data", "From(size:" + list.size()
//                    + ") i=" + ++i + "--> x:" + locationPosition.getX() + " y:" + locationPosition.getY());
//
//        }
//    }

    public void writeBeaconDataEachLine(LocationPosition locationPosition) {
        if (file == null) {
            return;
        }

        long millisec = System.currentTimeMillis() - startTime;

        String line = "";

        line = count++ + "," + millisec + "," + locationPosition.getX() + "," + locationPosition.getY() + "," + "\n";
        writeToFile(line);
//        saveDataToList(locationPosition);
        Log.i("Write Beacon data", "Location: --> x:" + locationPosition.getX() + ", y:" + locationPosition.getY());
    }

    private void writeHeadFile() {

        String line = ("Count" + "," + "Millisec" + "," + "X Position" + "," + "Y Position" + "," + "\n");
        writeToFile(line);

    }

    private void writeToFile(String line) {

        try {
            file.write(line);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void saveDataToList(LocationPosition locationPosition) {
        listLocationPosition.add(locationPosition);
        Log.i("In save data to list", " --> list Loca size = " + listLocationPosition.size());
//        tvPositionXY.setText("X:" + locationPosition.x + "  Y:" + locationPosition.y)

    }

    public void settvPositionXY(TextView tvPositionXY, LocationPosition locationPosition) {
        // Cannot set value to TextView for sure because the value of position will not update
        tvPositionXY.setText("X:" + locationPosition.getX() + "  Y:" + locationPosition.getY());
    }

}
