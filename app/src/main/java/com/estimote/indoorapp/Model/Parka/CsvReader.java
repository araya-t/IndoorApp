package com.estimote.indoorapp.Model.Parka;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class CsvReader {
    private List<CsvRow> csvRows;
    private String fileDirectory = "BeaconSensorCsvFile"; //default directory
    SimpleDateFormat sdfTimeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSSSSS");
    private int countRow = 0;


    public CsvReader() {
        csvRows = new ArrayList<>();
    }

    public List<CsvRow> readCSV(String fileName) throws IOException, ParseException {
        String fileDirectory = getFileDirectory(this.fileDirectory);
        File fileToGet = new File(fileDirectory, fileName);

        BufferedReader bufferedReader = getBufferedReader(fileToGet);
        String line;
        String csvSplitBy = ",";

        bufferedReader.readLine();

        while ((line = bufferedReader.readLine()) != null) {
            String[] row = line.split(csvSplitBy);

            String millisec = row[0];
            Date dateTimeStamp = sdfTimeStamp.parse(row[1]);
            String timeStamp = sdfTimeStamp.format(dateTimeStamp);
            long timeStampLong = dateTimeStamp.getTime();
            String acce_x = row[2];
            String acce_y = row[3];
            String acce_z = row[4];
            String is_stop_engine = row[5];
            String x_position = row[6];
            String y_position = row[7];

            CsvRow csvRow = new CsvRow(++countRow,millisec,timeStamp,timeStampLong,acce_x,acce_y,acce_z,
                    is_stop_engine,x_position,y_position);

            csvRows.add(csvRow);
        }

        bufferedReader.close();

        return csvRows;
    }

    public List<CsvRow> readCSV(String directory, String fileName) throws IOException, ParseException {
        String fileDirectory = getFileDirectory(directory);
        File fileToGet = new File(fileDirectory, fileName);

        BufferedReader bufferedReader = getBufferedReader(fileToGet);
        String line;
        String csvSplitBy = ",";

        bufferedReader.readLine();

        while ((line = bufferedReader.readLine()) != null) {
            String[] row = line.split(csvSplitBy);

            String millisec = row[0];
            Date dateTimeStamp = sdfTimeStamp.parse(row[1]);
            String timeStamp = sdfTimeStamp.format(dateTimeStamp);
            long timeStampLong = dateTimeStamp.getTime();
            String acce_x = row[2];
            String acce_y = row[3];
            String acce_z = row[4];
            String is_stop_engine = row[5];
            String x_position = row[6];
            String y_position = row[7];

            CsvRow csvRow = new CsvRow(++countRow,millisec,timeStamp,timeStampLong,acce_x,acce_y,acce_z,
                    is_stop_engine,x_position,y_position);

            csvRows.add(csvRow);
        }

        bufferedReader.close();

        return csvRows;
    }

    public static String getFileDirectory(String directory){
        String fileDirectory = Environment.getExternalStorageDirectory() + "/_Parka/" + directory;
        return fileDirectory;
    }

    public static BufferedReader getBufferedReader(File fileToGet){
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(fileToGet));
        }catch (IOException ex){
            Log.d("IOException", "File not found");
        }

        return bufferedReader;
    }

    public List<CsvRow> getCsvRows(){
        return csvRows;
    }

}