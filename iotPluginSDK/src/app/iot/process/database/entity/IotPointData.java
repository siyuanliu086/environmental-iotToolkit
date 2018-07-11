package app.iot.process.database.entity;

import java.util.Date;

public abstract class IotPointData implements IotData{
    String id;

    String pointId;

    String pointName;

    String deviceId;

    String deviceName;

    String regionCode;
    
    String regionName;

    String areaName;

    String sourcepointId;

    String pointClassificat;//监测点类别（级别）
    
    Date monitorTime;
    
    String moTime;

    Date createTime;
    
    int overTime;// 超时时间
    
    public String getId() {
        return id;
    }

    
    public void setId(String id) {
        this.id = id;
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

    
    public String getRegionCode() {
        return regionCode;
    }

    
    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }
    
    
    public String getRegionName() {
        return regionName;
    }
    
    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }


    public String getAreaName() {
        return areaName;
    }
    
    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }


    public String getSourcepointId() {
        return sourcepointId;
    }

    
    public void setSourcepointId(String sourcepointId) {
        this.sourcepointId = sourcepointId;
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


    
    public String getPointClassificat() {
        return pointClassificat;
    }


    
    public void setPointClassificat(String pointClassificat) {
        this.pointClassificat = pointClassificat;
    }
    
    
    public int getOverTime() {
        return overTime;
    }

    public void setOverTime(int overTime) {
        this.overTime = overTime;
    }


    public String getMoTime() {
		return moTime;
	}


	public void setMoTime(String moTime) {
		this.moTime = moTime;
	}


	/**
     * 获取站点类型(子类必须实现)
     * @author 		: Liu Siyuan
     * @Date 		: 2018年6月14日 下午7:21:12
     * @version 1.0.0
     */
    public abstract String getPointType();
    
}
