package com.iot.plugin.iml;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import app.iot.process.database.entity.OlMonitorMinData;
import app.iot.process.plugins.AbsProcessor;
import app.tools.JsonHelper;
  
/**
 * 应用：开封(新曹路王铁路附近等微型站)
 * @author Liu Siyuan
 *
 */
public class ProcessXinaHeNormal extends AbsProcessor {
	  
	@Override
	public boolean checkData(String data) {
		boolean result = false;
		// 如果前边的数据是 {"id":null,"pointId": 开头的，表示是标准的数据
		String key = "{\"id\":null,\"pointId\"";
		if (data.indexOf(key) == 0) {
		    result = true;
		}
		return result;
	}

	@Override
	public List<OlMonitorMinData> process(String data) {
	    writeDataLog("收到：" + data);
		List<OlMonitorMinData> reuslt = new ArrayList<OlMonitorMinData>();
		try {
			OlMonitorMinData olmonitormin = JsonHelper.jsonStrToBean(data, OlMonitorMinData.class);
			
			//默认值
			olmonitormin.setCreateTime(new Date());
			olmonitormin.setPointId(olmonitormin.getSourcepointId());
			olmonitormin.setDeviceId(olmonitormin.getSourcepointId());
			reuslt.add(olmonitormin);
			writeDataLog("解析完成:"+olmonitormin.getRegionCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reuslt;
	}
	
	@Override
	public List<String> process2Json(String data) {
	    List<String> result = new ArrayList<String>();
	    List<OlMonitorMinData> dataList = process(data);
	    if(dataList == null || dataList.size() == 0) {
	        return result; 
	    }
	    for(OlMonitorMinData minData : dataList) {
	        try {
	            result.add(JsonHelper.beanToJsonStr(minData));
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    return result;
	}

	@Override
	public String getProcessName() {
		return "先河";
	}

    @Override
    public String getProcessSysCode() {
        return AIR_MONITOR_MONITOR_212;
    }
}
