package com.estimote.indoorapp.Model.dao;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class CarPosition {

    @SerializedName("carPosition_id")           int carId;
    @SerializedName("position_id")              int positionId;
    @SerializedName("zone_name")                String zoneName;
    @SerializedName("floor_name")               String floorName;
    @SerializedName("building_name")            String buildingName;
    @SerializedName("is_driveOut")              boolean isDriveOut;
    @SerializedName("user_id")                  int userId;
    @SerializedName("time_created")             long timeCreated;

    public CarPosition(){

    }

    public CarPosition(int carId, int positionId,
                       String zoneName, String floorName, String buildingName,
                       boolean isDriveOut, int userId, long timeCreated) {
        this.carId = carId;
        this.positionId = positionId;
        this.zoneName = zoneName;
        this.floorName = floorName;
        this.buildingName = buildingName;
        this.isDriveOut = isDriveOut;
        this.userId = userId;
        this.timeCreated = timeCreated;
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

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public boolean isDriveOut() {
        return isDriveOut;
    }

    public void setDriveOut(boolean driveOut) {
        isDriveOut = driveOut;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public long getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(long timeCreated) {
        this.timeCreated = timeCreated;
    }
}
