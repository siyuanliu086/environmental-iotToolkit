package com.iot.plugin.sdkdev.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.iot.plugin.sdkdev.database.dao.DataDao;
import com.springboot.base.util.ResultUtil;

@Component
@Configurable
@EnableScheduling
public class SdkDevServer {
	
	@Autowired
    private DataDao dataDao;
	
	public SdkDevServer() {}
	
	// 每日凌晨0点执行一次
	// 转移备份数据
	@Scheduled(cron = "0 0 0 * * ?")
	public void dataBackupTask() {
//	    getTableData("ol_monitor_hour");
	}
	
	// 每间隔4小时执行一次
	// 4小时数据量：1.3w，查询时间14s，避免超时
	// 转移备份分钟数据
	@Scheduled(cron = "0 0 0/4 * * ?")
	public void dataBackupTaskMin() {
//	    getTableData("ol_monitor_min", Now());
	}
	
	/**
	 *  查询表内数据
	 * @className 	:
	 * @description :
	 * @business	: 
	 * @url			:
	 * @param 		:
	 * @return		:
	 * @author 		: Liu Siyuan
	 * @Date 		: 2018年7月18日 下午3:53:40
	 * @version 1.0.0
	 */
	public ResultUtil getTableData(final String tableName, final String startTime, final String endTime) {
	    if(tableName == null || tableName.isEmpty()) {
            ResultUtil.Faild("请传入表名！");
        }
	    Map<String, String> map = new HashMap<>();
	    map.put("tableName", tableName);
	    map.put("startTime", startTime);
	    map.put("endTime", endTime);
	    List<Map<String, Object>> dataList = dataDao.getTableData(map);
        return ResultUtil.Ok("TRANSPORT START>>>", dataList);
    } 
	
  
}
