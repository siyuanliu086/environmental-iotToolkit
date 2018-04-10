package com.datamonitor.message;

import com.alibaba.fastjson.JSONObject;

/**
 * 设备接口
 * @author Liu Siyuan
 *
 */
public interface IDeviceData {
    /**212协议的系统编号：22代表空气质量监测，39代表工地扬尘*/
    /**地表水环境监测系统编码*/
    String SURFACE_WATER_MONITOR_212 = "21";
    /**大气环境监测系统编码*/
    String AIR_MONITOR_MONITOR_212 = "22";
    /**空气污染监测系统编码*/
    String AIR_POLLUTE_MONITOR_212 = "31";
    
    public static final int TYPE_AIR_NA212 = 0;
    public static final int TYPE_AIR_SIM212 = 1;
    public static final int TYPE_AIR_TVOC212 = 2;
    public static final int TYPE_WATER_NA212 = 3;
    public static final int TYPE_POS_NA212 = 4;
    
    String getServer();
    String getPort();
    String getDeviceId();
    String getMessage();
    String getDeviceType();
    int getDataType();
    
    void setDeviceId(String deviceId);
    void setFactorStyle(JSONObject jo);
    void setAutoFullFactor(boolean b);
}
