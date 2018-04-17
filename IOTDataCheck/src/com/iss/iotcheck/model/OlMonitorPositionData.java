package com.iss.iotcheck.model;

import java.io.Serializable;
import java.util.Date;

public class OlMonitorPositionData implements IotData, Serializable{

    private static final long serialVersionUID = 6942106847027821932L;

    private String type;

    private String address;

    private String longitude;

    private String latitude;

    private String speed;

    private String direction;

    private String height;

    private Date monitorTime;

    private Date createTime;

    private String timeKey;

    private String satellite;

    private String deviceId;
    private String deviceName;
    private String pointId;
    private String pointName;
    private String regionName;
    private String regionCode;
    
    public String getType() {
        return type;
    }

    
    public void setType(String type) {
        this.type = type;
    }

    
    public String getRegionCode() {
        return regionCode;
    }

    
    public void setRegionCode(String region) {
        this.regionCode = region;
    }

    
    public String getAddress() {
        return address;
    }

    
    public void setAddress(String address) {
        this.address = address;
    }

    
    public String getLongitude() {
        return longitude;
    }

    
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    
    public String getLatitude() {
        return latitude;
    }

    
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    
    public String getSpeed() {
        return speed;
    }

    
    public void setSpeed(String speed) {
        this.speed = speed;
    }

    
    public String getDirection() {
        return direction;
    }

    
    public void setDirection(String direction) {
        this.direction = direction;
    }

    
    public String getHeight() {
        return height;
    }

    
    public void setHeight(String height) {
        this.height = height;
    }

    
    public Date getMonitorTime() {
        return monitorTime;
    }

    
    public void setMonitorTime(Date monitorTime) {
        this.monitorTime = monitorTime;
    }

    
    public Date getCreateTime() {
        return createTime;
    }

    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    
    public String getTimeKey() {
        return timeKey;
    }

    
    public void setTimeKey(String timeKey) {
        this.timeKey = timeKey;
    }

    
    public String getSatellite() {
        return satellite;
    }

    
    public void setSatellite(String satellite) {
        this.satellite = satellite;
    }


    
    public String getDeviceId() {
        return deviceId;
    }


    
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
    
    public String getDeviceName() {
        return deviceName;
    }


    
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }


    public String getPointId() {
        return pointId;
    }


    
    public void setPointId(String pointId) {
        this.pointId = pointId;
    }


    
    public String getPointName() {
        return pointName;
    }


    
    public void setPointName(String pointName) {
        this.pointName = pointName;
    }


    
    public String getRegionName() {
        return regionName;
    }


    
    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }
    

    
}