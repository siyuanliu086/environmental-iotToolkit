package com.iot.plugin.sdkdev.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.iot.plugin.sdkdev.service.SdkDevServer;
import com.springboot.base.util.ResultUtil;

@RestController
@RequestMapping("/rest/iot/plugin/sdkdev")
public class SdkDevRest {
	@Autowired
	private SdkDevServer devServer;
	
	/**
	 * 查询表格数据
	 * @url			: /rest/iot/plugin/sdkdev/getTableData?tableName=ol_monitor_hour&startTime=2018-07-18&endTime=2018-07-18 15:00:00
	 * @param 		:
	 * @return		:
	 * @author 		: Liu Siyuan
	 * @Date 		: 2018年7月18日 下午3:54:01
	 * @version 1.0.0
	 */
	@ResponseBody
    @RequestMapping("/getTableData")
	public ResultUtil getTableData(String tableName, String startTime, String endTime) {
	    return devServer.getTableData(tableName, startTime, endTime);
	}
	
}
