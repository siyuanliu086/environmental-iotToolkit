package com.iot.plugin.sdkdev.database.dao;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.DELETE_FROM;
import static org.apache.ibatis.jdbc.SqlBuilder.FROM;
import static org.apache.ibatis.jdbc.SqlBuilder.SELECT;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;

import java.util.HashMap;
import java.util.Map;

public class DataSqlProvider {

	/**
	 * @className 	:删除城市空气日数据(指定城市名、日期，查询日表数据是否存在)
	 * @author 		:siyuan
	 */
    public String deleteData(HashMap<String, Object> paramMap) {
	    String tableName = String.valueOf(paramMap.get("tableName"));
        String startTime = String.valueOf(paramMap.get("startTime"));
        String endTime = String.valueOf(paramMap.get("endTime"));
        
		BEGIN();
		DELETE_FROM(tableName);
		WHERE(" monitor_time >= '" + startTime + "' and monitor_time < '" + endTime+"'");
		return SQL();
	}
	
	/**
	 * @className 	:获取城市空气日数据(指定城市名、日期，查询日表数据是否存在)
	 * @author 		:siyuan
	 */
    public String getTableData(Map<String, Object> paramMap) {
	    String tableName = String.valueOf(paramMap.get("tableName"));
	    String startTime = String.valueOf(paramMap.get("startTime"));
	    String endTime = String.valueOf(paramMap.get("endTime"));
	    
	    BEGIN();
	    SELECT("*");
	    FROM(tableName);
	    WHERE(" monitor_time >= '" + startTime + "' and monitor_time < '" + endTime+"'");
	    return SQL();
	}
   
    public String checkIdExist(Map<String, Object> paramMap) {
        String tableName = String.valueOf(paramMap.get("tableName"));
        String id = String.valueOf(paramMap.get("id"));
        
        SELECT("id");
        FROM(tableName);
        WHERE(" id = '" + id + "';");
        return SQL();
    }
}
