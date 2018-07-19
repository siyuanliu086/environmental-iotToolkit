package com.iot.plugin.iml;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

import app.iot.process.database.entity.OlMonitorMinData;
import app.iot.process.plugins.AbsProcessor;
import app.tools.JsonHelper;

/**
 * 应用：承德（滦河热电07pm25站）
 * @author Liu Siyuan
 *
 */
public class ProcessLuling1 extends AbsProcessor {
	
	@Override
	public boolean checkData(String data) {
		boolean result = false;
		// 如果前边的数据是 {"id":null,"pointId": 开头的，表示是标准的数据
		String key = "{\"id\":";
		if (data.indexOf(key) == 0 && data.indexOf("gps_idtx")>0 && data.indexOf("gps_x")>0  ) {
		    result = true;
		    writeDataLog("数据校验完成");
		}
		return result;
	}

	@Override
	public String getProcessName() {
		return "LulingProcess";
	}

	// {"id":"6C611107H","fno":154,"type":"data","datetime":"2018-05-06 06:06:30","pm25":14.0,"pmSen":1,"gps_x":"000.000000","gps_idtx":"U","gps_y":"00.000000","gps_idty":"U","len":176,"crc":"F8C0"}
	@Override
	public List<OlMonitorMinData> process(String data) {
	    writeDataLog("解析:" + data);
		List<OlMonitorMinData> reuslt = new ArrayList<OlMonitorMinData>();
		try {
		    JSONObject jo = JSONObject.parseObject(data);
			OlMonitorMinData olmonitormin = new OlMonitorMinData();
			// 默认值
			olmonitormin.setCreateTime(new Date());
			olmonitormin.setPointId(jo.getString("id"));
			olmonitormin.setSourcepointId(jo.getString("id"));			
			olmonitormin.setDeviceId(jo.getString("id"));
			
			String monitorTime = jo.getString("datetime");
			if(monitorTime != null && !monitorTime.isEmpty()) {
			    try {
                    olmonitormin.setMonitorTime(simpleDateFormat.parse(monitorTime));
                } catch (Exception e) {
                    // 时间转换失败
                    olmonitormin.setMonitorTime(olmonitormin.getCreateTime());
                    e.printStackTrace();
                }
			} else {
			    olmonitormin.setMonitorTime(olmonitormin.getCreateTime());
			}
			
			olmonitormin.setPm25(jo.getDouble("pm25"));
			olmonitormin.setCreateTime(new Date());
			
			reuslt.add(olmonitormin);
			// 备注。临时发送到其他端口
			writeDataLog("解析完成(luling data1):"+data);
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
	
	   public static void main(String[] args) {
//	        String data = "{\"dtuId\":\"38FFD6054E47353720510851\",\"phoneImei\":\"12345678901234\",\"dtuState\":\"0\",\"data\":[{\"manufacturer\":\"lenovo_llcx\",\"id\":\"9H51107H\",\"type\":\"PM10\",\"value\":\"35.0\",\"deviceState\":\"0\"}],\"datetime\":\"2018-03-29 00:03:15\",\"gps_x\":\"000.000000\",\"gps_y\":\"00.000000\"}";
	      String data = "{\"id\":\"9H51157H\",\"fno\":117,\"type\":\"data\",\"datetime\":\"2018-03-29 00:12:43\",\"pm25\":43.0,\"pmSen\":1,\"gps_x\":\"000.000000\",\"gps_idtx\":\"U\",\"gps_y\":\"00.000000\",\"gps_idty\":\"U\",\"len\":175,\"crc\":\"38F0\"}";
//	        String data = "{\"id\":null,\"pointId\":\"hnkf2140\",\"pointName\":null,\"deviceId\":\"hnkf2140\",\"deviceName\":null,\"regionCode\":\"410200\",\"regionName\":\"开封市\",\"sourcepointId\":\"hnkf2140\",\"monitorTime\":\"2018-03-28T16:10:00.000+0000\",\"createTime\":null,\"aqi\":null,\"so2\":null,\"co\":1.026,\"no\":null,\"no2\":null,\"pm25\":113.64,\"pm10\":276.81,\"o3\":null,\"tsp\":null,\"noise\":null,\"vocs\":null,\"tvocs\":null,\"nh3\":null,\"gas\":null,\"coc12\":null,\"ch4s\":null,\"h2s\":null,\"cl2\":null,\"secaline\":null,\"c2h6s\":null,\"c2h6s2\":null,\"cs2\":null,\"c8h8\":null,\"wd\":0.0,\"ws\":0.0,\"tem\":19.8,\"pa\":0.0,\"rh\":77.0,\"v1\":null,\"v2\":null,\"v3\":null,\"v4\":null,\"v5\":null}\r\n";
	        ProcessLuling1 p = new ProcessLuling1();
	        if(p.checkData(data)) {
	            List<OlMonitorMinData> min = p.process(data);
	            System.out.println(min.size());
	        }
	    }

    @Override
    public String getProcessSysCode() {
        return AIR_MONITOR_MONITOR_212;
    }
}
