package iot.plugin;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import app.iot.process.database.entity.OlMonitorMinData;
import app.iot.process.plugins.AbsProcessing;

public class Process212 extends AbsProcessing {
	/*
	 * 检查数据是否是本协议的数据
	 */
	@Override
	public boolean CheckData(String data) {
		boolean result = false;
        String[] tempArr = data.split("ST=");
        if(tempArr.length == 2) {
            String systemCode = tempArr[1].substring(0, 2);
            if (GetProcessSysCode().equals(systemCode) || DUST_POLLUTE_MONITOR_212.equals(systemCode)) {//系统编码22,大气环境监测 || (工地扬尘)
                if (data.length() >= 2 && data.indexOf("##") >= 0 && !(data.contains("a34004") || data.contains("a01001"))) {//不包含PM2.5 或者温度的国标编码
                    result = true;
                    printInfoLog("设备数据校验完成");
                }
            }
        }
		return result;
	}

	/*
	 * 处理本数据，正常情况应该是返回一条或者多条， 如果返回数据少于一条，那就是解析失败，数据需要保存到失败数据库
	 */
	@Override
	public List<OlMonitorMinData> Process(String command) {
	    printInfoLog("收到设备预处理数据:" + command);
		List<OlMonitorMinData> result = new ArrayList<OlMonitorMinData>();
		// 校验crc
		// 校验长度；
		// 获取命令头内容 ST=22;CN=2011;PW=123456;MN=120112TJTGGN13;CP=
		// 如果数据为长度小于2，就直接返回
		if (command.indexOf("##") < 0) {
			// 如果不是##开头的数据
			return result;
		}
		command = command.substring(command.indexOf("##"), command.length());
		
		if (command.length() <= 2) {
			return result;
		}
		// 校验文件头
		if (!command.substring(0, 2).equals("##")) {
			return result;
		}
		
		int hbeginIndex = command.indexOf("QN");
		if (hbeginIndex == -1) {
			hbeginIndex = command.indexOf("ST");
		}
		int hendIndex = command.indexOf("&&");

		String header = command.substring(hbeginIndex, hendIndex);

		// 获取数据内容
		// DataTime=20150811151200;PM10-Rtd=89.59,PM10-Flag=N;TSP-Rtd=133.71,TSP-Flag=N

		int dbeginIndex = command.indexOf("&&") + 2;
		int dendIndex = command.lastIndexOf("&&");

		String data = command.substring(dbeginIndex, dendIndex);

		String crckey = command.substring(dendIndex + 2, command.length()).trim();
		int icrckey = Integer.parseInt(crckey, 16);

		String crcdata = command.substring(hbeginIndex, dendIndex + 2);

		int icrccomputer = GetCRC(crcdata);

		// 校验文件长度
//		int keylength = Integer.parseInt(command.substring(2, hbeginIndex));
//		int datalength = command.substring(hbeginIndex, dendIndex + 2).length();
		// if(keylength!=datalength){
		// 文件长度不对
		// return;
		// }
		// 校验crc
		if (icrccomputer != icrckey) {
			return result;
		}
		printInfoLog("设备数据校验完成:");

		// 头信息
		HashMap<String, String> allHeaderData = new HashMap<String, String>();
		// 数据体内容信息
		HashMap<String, String> allDataData = new HashMap<String, String>();

		// 处理头数据
		String[] headers = header.split(";");
		for (int i = 0; i < headers.length; i++) {
			String[] kvs = headers[i].split("=");
			if (kvs.length >= 2) {
				allHeaderData.put(kvs[0], kvs[1]);
			} else {
				allHeaderData.put(kvs[0], "");
			}
		}

		String[] datas = data.split(";");
		for (int i = 0; i < datas.length; i++) {
			String[] values = datas[i].split(",");
			for (int j = 0; j < values.length; j++) {
				String[] kvs = values[j].split("=");
				if (kvs.length >= 2) {
					allDataData.put(kvs[0], kvs[1]);
				} else {
					allDataData.put(kvs[0], "");
				}
			}
		}
		printInfoLog("设备数据解析完成:");
		// 解析数据，判断是是否是主服务器，如果是主服务器就转发到从服务器上
		OlMonitorMinData olmonitormin = ConverData(allHeaderData, allDataData);
		if (olmonitormin != null) {
			result.add(olmonitormin);
		}

		// 返回
		return result;
	}

	/*
	 * 转换字典形式数据为实体数据
	 */
	private OlMonitorMinData ConverData(HashMap<String, String> allHeaderData, HashMap<String, String> allDataData) {
		OlMonitorMinData result = null;
		/*
		 * 字典对应关系，把的数据字段对应到监测数据库的数据项上
		 * ST=22;CN=2011;PW=123456;MN=120112TJTGGN13;CP=&&DataTime=
		 * 20150811151200;PM10-Rtd=89.59,PM10-Flag=N;TSP-Rtd=133.71,TSP-Flag=N&&
		 * 
		 * 接收数据字段和标准数据字段对应关系 前边是标准数据库，后边是接收的数据
		 * 
		 * 
		 * HFactor.put("PM25", "PM2.5"); HFactor.put("PM10", "PM10");
		 * HFactor.put("WD", "风向"); HFactor.put("PA", "气压"); HFactor.put("WS",
		 * "风速"); HFactor.put("TEM", "温度"); //HFactor.put("TSP", "扬尘");
		 * HFactor.put("RH", "湿度");
		 * 
		 */

		// 如果日期时间型，需要格式化
		try {
			OlMonitorMinData tmpdata = new OlMonitorMinData();
			tmpdata.setSourcepointId(allHeaderData.get("MN"));
			tmpdata.setDeviceId(allHeaderData.get("MN"));
			
			// CN=2011实时数据
            // CN=2051分钟数据
            if (allHeaderData.containsKey("CN")) {
                if(allHeaderData.get("CN").equals("2011") || allHeaderData.get("CN").equals("2051")) {
                    result = tmpdata;
                } else {
                    printErrorLog("CN 编码异常，不进行解析: CN="+allHeaderData.get("CN"));
                    return result;
                }
            }
			
			// 时间处理特殊判断，错误兼容
            if(allDataData.get("DataTime") != null && !allDataData.get("DataTime").isEmpty()) {
                try {
                    tmpdata.setMonitorTime(dataDateFormat.parse(allDataData.get("DataTime")));
                } catch (Exception e) {
                    // 时间转换失败
                    printErrorLog("时间转换失败:"+allDataData);
                    tmpdata.setMonitorTime(new Date());
                    e.printStackTrace();
                }
            } else {
                tmpdata.setMonitorTime(new Date());
            }

			if (allDataData.containsKey("a34002-Rtd") && !allDataData.get("a34002-Rtd").equals(null)) {
				tmpdata.setPm10(Double.parseDouble(allDataData.get("a34002-Rtd")));
			}
			/*
			 * if (allDataData.containsKey("PM10-Avg") &&
			 * !allDataData.get("PM10-Avg").equals(null)) {
			 * tmpdata.setPm10(Double.parseDouble(allDataData.get("PM10-Avg")));
			 * }
			 */
			if (allDataData.containsKey("PM10-Rtd") && !allDataData.get("PM10-Rtd").equals(null)) {
				tmpdata.setPm10(Double.parseDouble(allDataData.get("PM10-Rtd")));
			}
			if (allDataData.containsKey("107-Rtd") && !allDataData.get("107-Rtd").equals(null)) {
			    tmpdata.setPm10(Double.parseDouble(allDataData.get("107-Rtd")));
			}
			
//			if (allDataData.containsKey("a34004-Rtd") && !allDataData.get("a34004-Rtd").equals(null)) {
//				tmpdata.setPm25(Double.parseDouble(allDataData.get("a34004-Rtd")));
//			}
			if (allDataData.containsKey("SO2-Rtd") && !allDataData.get("SO2-Rtd").equals(null)) {
				tmpdata.setSo2(Double.parseDouble(allDataData.get("SO2-Rtd")));
			}
			if (allDataData.containsKey("CO-Rtd") && !allDataData.get("CO-Rtd").equals(null)) {
				tmpdata.setCo(Double.parseDouble(allDataData.get("CO-Rtd")));
			}
			if (allDataData.containsKey("NO2-Rtd") && !allDataData.get("NO2-Rtd").equals(null)) {
				tmpdata.setNo2(Double.parseDouble(allDataData.get("NO2-Rtd")));
			}
			if (allDataData.containsKey("O3-Rtd") && !allDataData.get("O3-Rtd").equals(null)) {
				tmpdata.setO3(Double.parseDouble(allDataData.get("O3-Rtd")));
			}
			/*
			 * if (allDataData.containsKey("PM25-Avg") &&
			 * !allDataData.get("PM25-Avg").equals(null)) {
			 * tmpdata.setPm25(Double.parseDouble(allDataData.get("PM25-Avg")));
			 * }
			 */
			if (allDataData.containsKey("PM25-Rtd") && !allDataData.get("PM25-Rtd").equals(null)) {
				tmpdata.setPm25(Double.parseDouble(allDataData.get("PM25-Rtd")));
			}
			if (allDataData.containsKey("925-Rtd") && !allDataData.get("925-Rtd").equals(null)) {
			    tmpdata.setPm25(Double.parseDouble(allDataData.get("925-Rtd")));
			}

//			if (allDataData.containsKey("a01008-Rtd") && !allDataData.get("a01008-Rtd").equals(null)) {
//				tmpdata.setWd(Double.parseDouble(allDataData.get("a01008-Rtd")));
//			}
			/*
			 * if (allDataData.containsKey("WD-Avg") &&
			 * !allDataData.get("WD-Avg").equals(null)) {
			 * tmpdata.setWd(Double.parseDouble(allDataData.get("WD-Avg"))); }
			 */
			if (allDataData.containsKey("WD-Rtd") && !allDataData.get("WD-Rtd").equals(null)) {
				tmpdata.setWd(Double.parseDouble(allDataData.get("WD-Rtd")));
			}
			if (allDataData.containsKey("130-Rtd") && !allDataData.get("130-Rtd").equals(null)) {
			    tmpdata.setWd(Double.parseDouble(allDataData.get("130-Rtd")));
			}

//			if (allDataData.containsKey("a01007-Rtd") && !allDataData.get("a01007-Rtd").equals(null)) {
//				tmpdata.setWs(Double.parseDouble(allDataData.get("a01007-Rtd")));
//			}
			/*
			 * if (allDataData.containsKey("WS-Avg") &&
			 * !allDataData.get("WS-Avg").equals(null)) {
			 * tmpdata.setWs(Double.parseDouble(allDataData.get("WS-Avg"))); }
			 */
			if (allDataData.containsKey("WS-Rtd") && !allDataData.get("WS-Rtd").equals(null)) {
				tmpdata.setWs(Double.parseDouble(allDataData.get("WS-Rtd")));
			}
			if (allDataData.containsKey("129-Rtd") && !allDataData.get("129-Rtd").equals(null)) {
			    tmpdata.setWs(Double.parseDouble(allDataData.get("129-Rtd")));
			}

			/*
			 * if (allDataData.containsKey("TSP-Avg") &&
			 * !allDataData.get("TSP-Avg").equals(null)) {
			 * tmpdata.setTsp(Double.parseDouble(allDataData.get("TSP-Avg"))); }
			 */
			if (allDataData.containsKey("TSP-Rtd") && !allDataData.get("TSP-Rtd").equals(null)) {
				tmpdata.setTsp(Double.parseDouble(allDataData.get("TSP-Rtd")));
			}
			//B03噪声协议编号
			if (allDataData.containsKey("B03-Rtd") && !allDataData.get("B03-Rtd").equals(null)) {
				tmpdata.setNoise(Double.parseDouble(allDataData.get("B03-Rtd")));
			}
			if (allDataData.containsKey("NOIS-Rtd") && !allDataData.get("NOIS-Rtd").equals(null)) {
				tmpdata.setNoise(Double.parseDouble(allDataData.get("NOIS-Rtd")));
			}
 			if (allDataData.containsKey("a01001-Rtd") && !allDataData.get("a01001-Rtd").equals(null)) {
				tmpdata.setTem(Double.parseDouble(allDataData.get("a01001-Rtd")));
			}
			if (allDataData.containsKey("TEM-Rtd") && !allDataData.get("TEM-Rtd").equals(null)) {
				tmpdata.setTem(Double.parseDouble(allDataData.get("TEM-Rtd")));
			}
			if (allDataData.containsKey("126-Rtd") && !allDataData.get("126-Rtd").equals(null)) {
			    tmpdata.setTem(Double.parseDouble(allDataData.get("126-Rtd")));
			}
			if (allDataData.containsKey("PA-Rtd") && !allDataData.get("PA-Rtd").equals(null)) {
				tmpdata.setPa(Double.parseDouble(allDataData.get("PA-Rtd")));
			}

			if (allDataData.containsKey("TVOC-Rtd") && !allDataData.get("TVOC-Rtd").equals(null)) {
				tmpdata.setTvocs(Double.parseDouble(allDataData.get("TVOC-Rtd")));
			}
			if (allDataData.containsKey("VOC-Rtd") && !allDataData.get("VOC-Rtd").equals(null)) {
			    tmpdata.setVocs(Double.parseDouble(allDataData.get("VOC-Rtd")));
			}
			

			/*
			 * if (allDataData.containsKey("RH-Avg") &&
			 * !allDataData.get("RH-Avg").equals(null)) {
			 * tmpdata.setRh(Double.parseDouble(allDataData.get("RH-Avg"))); }
			 */
			if (allDataData.containsKey("RH-Rtd") && !allDataData.get("RH-Rtd").equals(null)) {
				tmpdata.setRh(Double.parseDouble(allDataData.get("RH-Rtd")));
			}
			if (allDataData.containsKey("128-Rtd") && !allDataData.get("128-Rtd").equals(null)) {
			    tmpdata.setRh(Double.parseDouble(allDataData.get("128-Rtd")));
			}

			if (allDataData.containsKey("a01006-Rtd") && !allDataData.get("a01006-Rtd").equals(null)) {
				tmpdata.setPa(Double.parseDouble(allDataData.get("a01006-Rtd")));
			}
			//CH4
			//NMHC
			if (allDataData.containsKey("CH4-Rtd") && !allDataData.get("CH4-Rtd").equals(null)) {
				tmpdata.setCh4s(Double.parseDouble(allDataData.get("CH4-Rtd")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static void main(String[] args) {
        Process212 p = new Process212();
        long time1 = new Date().getTime();
        String command = "##0282ST=22;CN=2011;PW=123456;MN=202020202020;CP=&&DataTime=20170310182624;925-Rtd=978,925-Flag=N;107-Rtd=670,107-Flag=N;103-Rtd=228,103-Flag=N;B03-Rtd=30,B03-Flag=N;126-Rtd=33,126-Flag=N;128-Rtd=77,128-Flag=N;129-Rtd=190,129-Flag=N;130-Rtd=195,130-Flag=N;127-Rtd=638,127-Flag=N&&9481";
        if(p.CheckData(command)) {            
            List<OlMonitorMinData> minData = p.Process(command);
            System.out.println(minData.get(0).toString());
        }
        System.out.println("耗时： " + (new Date().getTime() - time1));
    }

	@Override
	public boolean IsComputerAQI() {
		return true;
	}

	@Override
	public boolean IsAnalyzeExceptionValue() {
		return false;
	}

	@Override
	public String GetProcessName() {
		return "大气简标212协议";
	}

	@Override
    public String GetProcessSysCode() {
        return AIR_MONITOR_MONITOR_212;
    }
}