package com.iss.iotcheck.plugin;

import java.util.List;

import com.iss.iotcheck.model.IotData;

public interface IProcessing {
    /**地表水环境监测系统编码*/
    String SURFACE_WATER_MONITOR_212 = "21";
    /**大气环境监测系统编码*/
    String AIR_MONITOR_MONITOR_212 = "22";
    /**空气污染监测系统编码*/
    String AIR_POLLUTE_MONITOR_212 = "31";
	
	/*
	 * 检查数据是否是本协议的数据
	 */
	boolean CheckData(String data);
	
	/*
	 * 是否需要计算AQI
	 */
	boolean IsComputerAQI();
	
	
	/*
	 * 是否分析异常数据，主要是分析
	 */
	boolean IsAnalyzeExceptionValue();
	
	/*
	 * 解析名称
	 */
	String GetProcessName();
	
	/*
	 * 解析名称
	 */
	String GetProcessSysCode();
	
	/*
	 * 处理本数据，正常情况应该是返回一条或者多条，
	 * 如果返回数据少于一条，那就是解析失败，数据需要保存到失败数据库
	 */
	List<? extends IotData> Process(String data);
}
