package com.estimote.indoorapp.Model.dao;

import com.google.gson.annotations.SerializedName;

public class CarPosition {
    @SerializedName("carPosition_id")       int carId;
    @SerializedName("building_name")        String buildingName;
    @SerializedName("floor_name")           String floorName;
    @SerializedName("position_id")          int positionId;
    @SerializedName("position_name")        String positionName;
    @SerializedName("time_created")         long timeCreated;
    @SerializedName("zone_name")            String zoneName;
    @SerializedName("user_id")              int userId;
    @SerializedName("is_driveOut")          boolean isDriveOut;

    public CarPosition(){

    }

    public CarPosition(int carId, String buildingName, String floorName, int positionId, String positionName, long timeCreated, String zoneName, int userId, boolean isDriveOut) {
        this.carId = carId;
        this.buildingName = buildingName;
        this.floorName = floorName;
        this.positionId = positionId;
        this.positionName = positionName;
        this.timeCreated = timeCreated;
        this.zoneName = zoneName;
        this.userId = userId;
        this.isDriveOut = isDriveOut;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public int getPositionId() {
        return positionId;
    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public long getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(long timeCreated) {
        this.timeCreated = timeCreated;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isDriveOut() {
        return isDriveOut;
    }

    public void setDriveOut(boolean driveOut) {
        isDriveOut = driveOut;
    }
}

