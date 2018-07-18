package com.iot.plugin.sdkdev.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.iot.plugin.sdkdev.database.dao.DataDao;
import com.springboot.base.util.ResultUtil;

/**
 * 数据定时备份:
 * 1、分钟数据（一个月）
 * 2、国控和自控小时数据（两个月）
 * 3、天气实时数据（一周）
 * 4、天气预报数据（一周）
 * @author Liu Siyuan
 */
@Component
@Configurable
@EnableScheduling
public class DataBackupServer {
	
	@Autowired
    private DataDao dataDao;
	
	public DataBackupServer() {}
	
	// 每日凌晨0点执行一次
	// 转移备份数据
	@Scheduled(cron = "0 0 0 * * ?")
	public void dataBackupTask() {
//	    dataBackupTask("ol_monitor_hour");
//	    dataBackupTask("ol_monitor_hour_country");
//	    dataBackupTask("ol_city_weather");
//	    dataBackupTask("ol_city_weather_forecast");
	}
	
	// 每间隔4小时执行一次
	// 4小时数据量：1.3w，查询时间14s，避免超时
	// 转移备份分钟数据
	@Scheduled(cron = "0 0 0/4 * * ?")
	public void dataBackupTaskMin() {
//	    dataBackupTask("ol_monitor_min");
	}

    public ResultUtil getTableData(String tableName, String startTime, String endTime) {
        if(tableName == null || tableName.isEmpty()) {
            ResultUtil.Faild("请传入表名！");
        }
        Map<String, String> map = new HashMap<>();
        map.put("tableName", tableName);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        dataDao.getTableData(map);
        return ResultUtil.Ok("TRANSPORT START>>>");
    } 
	
  
}
