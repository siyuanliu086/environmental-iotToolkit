package iot.plugin;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import app.iot.process.database.entity.OlMonitorMinData;
import app.iot.process.plugins.AbsProcessing;

/**
 * 国标212
 * @author Liu Siyuan
 *
 */
public class NationalStandard212 extends AbsProcessing {

	/*
	 * 检查数据是否是本协议的数据
	 */
	@Override
	public boolean CheckData(String data) {
		boolean result = false;
		// ## + 长度 + QN 区别于软通简版212协议
		String[] tempArr = data.split("ST=");
        if(tempArr.length == 2) {
            String systemCode = tempArr[1].substring(0, 2);
            if (GetProcessSysCode().equals(systemCode) || DUST_POLLUTE_MONITOR_212.equals(systemCode)) {//系统编码22,大气环境监测
                if (data.length() >= 2 && data.indexOf("##") >= 0 && data.indexOf("QN=") <= 7 && (data.contains("a34004") || data.contains("a01001"))) {//包含PM2.5 或者温度的编码
                    result = true;
                    printInfoLog("国标设备数据校验完成");
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
	    printInfoLog("收到国标设备预处理数据:" + command);
		List<OlMonitorMinData> result = new ArrayList<OlMonitorMinData>();
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

		String crckey = command.substring(dendIndex + 2, command.length());
		int icrckey = Integer.parseInt(crckey, 16);

		String crcdata = command.substring(hbeginIndex, dendIndex + 2);
		int icrccomputer = GetCRC(crcdata);
		// 校验crc
		if (icrccomputer != icrckey) {
		    printErrorLog("CRC 校验异常！" + icrccomputer);
			return result;
		}
		printInfoLog("校验完成");

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
		printInfoLog("解析完成");
		// 解析数据，判断是是否是主服务器，如果是主服务器就转发到从服务器上
		OlMonitorMinData olmonitormin = ConverData(allHeaderData, allDataData);
		if (olmonitormin != null) {
			//
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
		//字典对应关系，把国标的数据字段对应到监测数据库的数据项上
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
			if (allDataData.containsKey("a34004-Rtd") && !allDataData.get("a34004-Rtd").equals(null)) {
				tmpdata.setPm25(Double.parseDouble(allDataData.get("a34004-Rtd")));
			}
			if (allDataData.containsKey("a21026-Rtd") && !allDataData.get("a21026-Rtd").equals(null)) {
				tmpdata.setSo2(Double.parseDouble(allDataData.get("a21026-Rtd")));
			}
			if (allDataData.containsKey("a21005-Rtd") && !allDataData.get("a21005-Rtd").equals(null)) {
				tmpdata.setCo(Double.parseDouble(allDataData.get("a21005-Rtd")));
			}
			if (allDataData.containsKey("a21004-Rtd") && !allDataData.get("a21004-Rtd").equals(null)) {
				tmpdata.setNo2(Double.parseDouble(allDataData.get("a21004-Rtd")));
			}
			if (allDataData.containsKey("a21030-Rtd") && !allDataData.get("a21030-Rtd").equals(null)) {
				tmpdata.setO3(Double.parseDouble(allDataData.get("a21030-Rtd")));
			}
			if (allDataData.containsKey("a05024-Rtd") && !allDataData.get("a05024-Rtd").equals(null)) {
			    tmpdata.setO3(Double.parseDouble(allDataData.get("a05024-Rtd")));
			}
			if (allDataData.containsKey("a01008-Rtd") && !allDataData.get("a01008-Rtd").equals(null)) {
				tmpdata.setWd(Double.parseDouble(allDataData.get("a01008-Rtd")));
			}
			if (allDataData.containsKey("a01007-Rtd") && !allDataData.get("a01007-Rtd").equals(null)) {
				tmpdata.setWs(Double.parseDouble(allDataData.get("a01007-Rtd")));
			}
			if (allDataData.containsKey("a34001-Rtd") && !allDataData.get("a34001-Rtd").equals(null)) {
				tmpdata.setTsp(Double.parseDouble(allDataData.get("a34001-Rtd")));
			}
			//国标B03噪声协议编号
			if (allDataData.containsKey("B03-Rtd") && !allDataData.get("B03-Rtd").equals(null)) {
				tmpdata.setNoise(Double.parseDouble(allDataData.get("B03-Rtd")));
			}
			if (allDataData.containsKey("LA-Rtd") && !allDataData.get("LA-Rtd").equals(null)) {
				tmpdata.setNoise(Double.parseDouble(allDataData.get("LA-Rtd")));
			}
 			if (allDataData.containsKey("a01001-Rtd") && !allDataData.get("a01001-Rtd").equals(null)) {
				tmpdata.setTem(Double.parseDouble(allDataData.get("a01001-Rtd")));
			}
			if (allDataData.containsKey("a01006-Rtd") && !allDataData.get("a01006-Rtd").equals(null)) {
				tmpdata.setPa(Double.parseDouble(allDataData.get("a01006-Rtd")));
			}
			if (allDataData.containsKey("a01002-Rtd") && !allDataData.get("a01002-Rtd").equals(null)) {
				tmpdata.setRh(Double.parseDouble(allDataData.get("a01002-Rtd")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
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
		return "212国标解析器";
	}
	
	public static void main(String[] args) {
	    long time1 = new Date().getTime();
	    String data = "##0249QN=20180507000558;ST=39;CN=2011;PW=123456;MN=010000A00000000000A00654;CP=&&DataTime=20180507000558;a01001-Rtd=26.0,a01001-Flag=N;a01002-Rtd=30.4,a01002-Flag=N;a34001-Rtd=0.0,a34001-Flag=N;a34002-Rtd=24.0,a34002-Flag=N;a34004-Rtd=23.0,a34004-Flag=N&&D801";
        //String data = "##0481QN=20160801085000001;ST=22;CN=2011;PW=123456;MN=010000A8900016F000169DC0;Flag=7;CP=&&DataTime=20160801084000;a21005-Rtd=1.1,a21005-Flag=N;a21004-Rtd=112,a21004-Flag=N;a21026-Rtd=58,a21026-Flag=N;a21030-Rtd=64,a21030-Flag=N;LA-Rtd=50.1,LA-Flag=N;a34004-Rtd=207,a34004-Flag=N;a34002-Rtd=295,a34002-Flag=N;a01001-Rtd=12.6,a01001-Flag=N;a01002-Rtd=32,a01002-Flag=N;a01006-Rtd=101.02,a01006-Flag=N;a01007-Rtd=2.1,a01007-Flag=N;a01008-Rtd=120,a01008-Flag=N;a34001-Rtd=217,a34001-Flag=N;&&4f40";
        NationalStandard212 standard212 = new NationalStandard212();
        if(standard212.CheckData(data)) {
            //List<OlMonitorMinData> min = standard212.Process(data);
        }
        System.out.println("耗时： " + (new Date().getTime() - time1));
    }

    @Override
    public String GetProcessSysCode() {
        return AIR_MONITOR_MONITOR_212;
    }
}