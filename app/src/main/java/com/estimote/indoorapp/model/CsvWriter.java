package com.estimote.indoorapp.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CsvWriter {
    private BufferedWriter file = null;
    private long startTime;

    public CsvWriter(){

    }

    public CsvWriter(String fileName, String directory){
        createFile(fileName, directory);
    }

    public long createFile(String fileName, String directory){
        File initFileDirectory = createDirectory(directory);

        try {

            file = new BufferedWriter(new FileWriter(initFileDirectory.getAbsolutePath()+"/"+fileName));
            startTime = System.currentTimeMillis();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return startTime;

    }

    private File createDirectory(String directory){
        File initFileDirectory = new File(directory);

        if(!initFileDirectory.exists()){
            initFileDirectory.mkdirs();
        }

        return initFileDirectory;

    }

    public void writeHeadFile(String headFileStr) {
        try {
            file.write(headFileStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeToFile(String line) {
        if (file == null) {
            return;
        }

        try {
            file.write(line);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void closeFile(){
        try {
            file.close();
            file = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedWriter getFile(){
        return file;
    }

    public long getStartTime(){
        return startTime;
    }
}
