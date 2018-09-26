package com.estimote.indoorapp.Model.dao;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class CarPositions {
    @SerializedName("carPosition_id")           int carId;
    @SerializedName("position_id")              int positionId;
    @SerializedName("user")                     int userId;
    @SerializedName("is_driveOut")
    Boolean isDriveOut;
    @SerializedName("time_created")
    Date timeCreated;

    public CarPositions(){

    }

    public CarPositions(int positionId, int userId, Boolean isDriveOut) {
        this.positionId = positionId;
        this.userId = userId;
        this.isDriveOut = isDriveOut;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public int getPositionId() {
        return positionId;
    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Boolean getDriveOut() {
        return isDriveOut;
    }

    public void setDriveOut(Boolean driveOut) {
        isDriveOut = driveOut;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }
}
