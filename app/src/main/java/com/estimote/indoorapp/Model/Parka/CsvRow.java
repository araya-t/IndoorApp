package com.estimote.indoorapp.Model.Parka;


import java.util.Date;

public class CsvRow {
    private long millisec;
    private String timeStamp;
    private long timeStampLong;
    private double acce_x;
    private double acce_y;
    private double acce_z;
    private boolean is_stop_engine;
    private double x_position;
    private double y_position;

    public CsvRow(){

    }

    public CsvRow( String millisec, String timeStamp, long timeStamplong,
                   String acce_x, String acce_y, String acce_z,
                   String is_stop_engine, String x_position, String y_position){

        this.millisec = Long.parseLong(millisec);
        this.timeStamp = timeStamp;
        this.timeStampLong = timeStamplong;
        this.acce_x = Double.parseDouble(acce_x);
        this.acce_y = Double.parseDouble(acce_y);
        this.acce_z = Double.parseDouble(acce_z);
        this.is_stop_engine = Boolean.parseBoolean(is_stop_engine);
        this.x_position = Double.parseDouble(x_position);
        this.y_position = Double.parseDouble(y_position);
    }

    public long getMillisec() {
        return millisec;
    }

    public void setMillisec(long millisec) {
        this.millisec = millisec;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public long getTimeStampLong() {
        return timeStampLong;
    }

    public void setTimeStampLong(long timeStampLong) {
        this.timeStampLong = timeStampLong;
    }

    public double getAcce_x() {
        return acce_x;
    }

    public void setAcce_x(double acce_x) {
        this.acce_x = acce_x;
    }

    public double getAcce_y() {
        return acce_y;
    }

    public void setAcce_y(double acce_y) {
        this.acce_y = acce_y;
    }

    public double getAcce_z() {
        return acce_z;
    }

    public void setAcce_z(double acce_z) {
        this.acce_z = acce_z;
    }

    public boolean is_stop_engine() {
        return is_stop_engine;
    }

    public void setIs_stop_engine(boolean is_stop_engine) {
        this.is_stop_engine = is_stop_engine;
    }

    public double getX_position() {
        return x_position;
    }

    public void setX_position(double x_position) {
        this.x_position = x_position;
    }

    public double getY_position() {
        return y_position;
    }

    public void setY_position(double y_position) {
        this.y_position = y_position;
    }

    @Override
    public String toString() {
        return "CsvRow{" +
                "millisec=" + millisec +
                ", timeStamp=" + timeStamp +
                ", acce_x=" + acce_x +
                ", acce_y=" + acce_y +
                ", acce_z=" + acce_z +
                ", is_stop_engine=" + is_stop_engine +
                ", x_position=" + x_position +
                ", y_position=" + y_position +
                '}';
    }
}
