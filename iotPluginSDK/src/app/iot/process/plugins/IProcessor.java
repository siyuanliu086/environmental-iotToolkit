package app.iot.process.plugins;

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
	
	/**
	 * 检查数据是否是本协议的数据
	 */
	boolean checkData(String data);
	
	/**是否打印日志*/
	void setWriteLog(boolean isWriteLog, String logPath);
	
	/**
	 * 解析器名称
	 */
	String getProcessName();
	
	/**
	 * 解析器解析编码
	 */
	String getProcessSysCode();
	
	/**
	 * 处理本数据，正常情况应该是返回一条或者多条，
	 * 如果返回数据少于一条，那就是解析失败，数据需要保存到失败数据库
	 */
	List<? extends IotData> process(String data);
	
	List<String> process2Json(String data);
	
	void setMQExchange(String exchangeName);
}
