package com.datamonitor.message;

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
    
    String getServer();
    String getPort();
    String getDeviceId();
    String getMessage();
    String getDeviceType();
}
