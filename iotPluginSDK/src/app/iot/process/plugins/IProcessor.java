package app.iot.process.plugins;

import java.text.SimpleDateFormat;
import java.util.List;

import app.iot.process.database.entity.IotData;

public interface IProcessor {
    /**地表水环境监测系统编码*/
    String SURFACE_WATER_MONITOR_212 = "21";
    /**大气环境监测系统编码*/
    String AIR_MONITOR_MONITOR_212 = "22";
    /**空气污染监测系统编码*/
    String AIR_POLLUTE_MONITOR_212 = "31";
    /**工地扬尘污染源系统编码*/
    String DUST_POLLUTE_MONITOR_212 = "39";
    
    /**移动定位监测系统编码*/
    String POSITION_MONITOR_212 = "65";
    
    /**协议指定数据时间格式*/
    SimpleDateFormat dataDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 检查数据是否是本协议的数据
	 */
	boolean CheckData(String data);
	
	/**
	 * 是否需要计算AQI
	 */
	boolean IsComputerAQI();
	
	/**
	 * 是否分析异常数据，主要是分析
	 */
	boolean IsAnalyzeExceptionValue();
	
	/**
	 * 解析器名称
	 */
	String GetProcessName();
	
	/**
	 * 解析器解析编码
	 */
	String GetProcessSysCode();
	
	/**
	 * 处理本数据，正常情况应该是返回一条或者多条，
	 * 如果返回数据少于一条，那就是解析失败，数据需要保存到失败数据库
	 */
	List<? extends IotData> Process(String data);
}
