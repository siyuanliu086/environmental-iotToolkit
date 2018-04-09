package com.iss.iotcheck.plugin;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.iss.iotcheck.model.OlMonitorMinData;
import com.iss.iotcheck.tools.DateHelper;

/**
 * 大气环境污染源TVOC软通212
 * @author Liu Siyuan
 *
 */
public class ProcessTVOC212 implements IProcessing {

	/*
	 * 检查数据是否是本协议的数据
	 */
	@Override
	public boolean CheckData(String data) {
		boolean result = false;
		String[] tempArr = data.split("ST=");
		if(tempArr.length == 2) {
		    String systemCode = tempArr[1].substring(0, 2);
		    if (GetProcessSysCode().equals(systemCode)) {//系统编码31,大气环境污染源
	            result = true;
	        }
		}
		return result;
	}

	/*
	 * 处理本数据，正常情况应该是返回一条或者多条， 如果返回数据少于一条，那就是解析失败，数据需要保存到失败数据库
	 */
	@Override
	public List<OlMonitorMinData> Process(String command) {

		List<OlMonitorMinData> result = new ArrayList<OlMonitorMinData>();

		// 接受处理的的数据
		// 注意，由于是字符串数据，只有在收到数据知道后使用使用回车换行的，才会触发这个信息：环保部212和天津国标的设备就是这个模式。
		// 首先验证数据crc，然后验证数据长度，然后获取数据值

		/*
		 * 分钟数据包 示例：
		 * ##0131ST=22;CN=2011;PW=123456;MN=120112TJTGGN13;CP=&&DataTime=
		 * 20150811151200;PM10-Rtd=89.59,PM10-Flag=N;TSP-Rtd=133.71,TSP-Flag=N&&
		 * C241
		 * 
		 * 说明： 包头：固定为##； 数据段长度：数据段的 ASCII 字符数，例如：长 255，则写为“
		 * 0255”。数据包长度从第一个系统编号“ST=” 开始计算，到最后的换行符“\r\n”为止的数据长度；
		 * 
		 * 系统编号ST：212协议的系统编号，“22”代表 空气质量监测；
		 * 
		 * 命令编号CN：212协议的命令编号，“2011”代表 取污染物实时数据（分钟数据）；
		 * 
		 * 访问密码PW：现无实际用途，可固定为“123456”；
		 * 
		 * 设备唯一标识MN：用作设备识别，共14位，前 7 位是设备制造商组织机构代码的后 7 位， 后 7
		 * 位是设备制造商自行确定的此类设备的唯一编码（测试阶段可自行编写所有14位编码）；
		 * 
		 * 指令参数CP：（CP=&&数据区&&）请参照环保212协议所定义的数据区说明配置，最终格式为以上示例所示。
		 * 说明：现有扬尘各参数监测编码请参照此示例标准： PM10参数为PM10； PM2.5参数为PM25； TSP参数为TSP； 噪声 参数为
		 * Voice； 风向 参数为 WD; 风速 参数为 WS； 温度 参数为 TEM； 气压 参数为 PA； 湿度 参数为 RH； 降雨量
		 * 参数为 RI；
		 * 
		 * xxx-Flag：监测污染物实时数据标记： N：正常； T：超测上限； D：故障； 正常发送数据可标记为N；
		 * 
		 * 包尾CRC校验：请参照环保212协议 - 附录 A：循环冗余校验（ CRC）算法说明计算，以下给出算法示例： 
		 * public int GetCRC(string data212) { 
		 * int CRC = 0xFFFF; int Num = 0xA001; int inum
		 * = 0; for (int j = 0; j < data212.Length; j++) { inum = data212[j];
		 * CRC = (CRC >> 8) & 0x00FF; CRC ^= inum;
		 * 
		 * for (int k = 0; k < 8; k++) { int flag = CRC % 2; CRC = CRC >> 1;
		 * 
		 * if (flag == 1) { CRC = CRC ^ Num; } } } return CRC; } 参数 “data212”
		 * 需要传入的字符串为： ST=22;CN=2011;PW=123456;MN=120112TJTGGN13;CP=&&DataTime=
		 * 20150811151200;PM10-Rtd=89.59,PM10-Flag=N;TSP-Rtd=133.71,TSP-Flag=N&&
		 * 将返回的CRC转换为16进制字符串，添加到包尾。 注意事项 每条数据包的末尾需添加换行符 “\r\n” 以示和上一条数据包的区分。
		 * 将符合以上示例格式的数据包，以每分钟一次的频率发送至指定服务器IP端口。
		 * 
		 */

		// 首先风格; , =

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

		String crckey = command.substring(dendIndex + 2, command.length());
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
		// 如果日期时间型，需要格式化
		try {
			OlMonitorMinData tmpdata = new OlMonitorMinData();
			tmpdata.setSourcepointId(allHeaderData.get("MN"));
			tmpdata.setDeviceId(allHeaderData.get("MN"));
			
			// 时间处理特殊判断，错误兼容
            if(allDataData.get("DataTime") != null && !allDataData.get("DataTime").isEmpty()) {
                try {
                    tmpdata.setMonitorTime(DateHelper.parse(allDataData.get("DataTime"), "yyyyMMddHHmmss"));
                } catch (Exception e) {
                    // 时间转换失败
                    tmpdata.setMonitorTime(new Date());
                    e.printStackTrace();
                }
            } else {
                tmpdata.setMonitorTime(new Date());
            }

            // 氧气含量
			if (allDataData.containsKey("S01-Rtd") && !allDataData.get("S01-Rtd").equals(null)) {
				tmpdata.setO2_content(Double.parseDouble(allDataData.get("S01-Rtd")));
			}
			// 烟气流速
			if (allDataData.containsKey("S02-Rtd") && !allDataData.get("S02-Rtd").equals(null)) {
				tmpdata.setStack_gas_velocity(Double.parseDouble(allDataData.get("S02-Rtd")));
			}
			// 烟气温度
			if (allDataData.containsKey("S03-Rtd") && !allDataData.get("S03-Rtd").equals(null)) {
				tmpdata.setGas_tem(Double.parseDouble(allDataData.get("S03-Rtd")));
			}
			// 烟气动压
//			if (allDataData.containsKey("S04-Rtd") && !allDataData.get("S04-Rtd").equals(null)) {
//			    tmpdata.setSo2(Double.parseDouble(allDataData.get("S04-Rtd")));
//			}
			// 烟气湿度
			if (allDataData.containsKey("S05-Rtd") && !allDataData.get("S05-Rtd").equals(null)) {
				tmpdata.setGas_rh(Double.parseDouble(allDataData.get("S05-Rtd")));
			}
			// 制冷温度
//			if (allDataData.containsKey("S06-Rtd") && !allDataData.get("S06-Rtd").equals(null)) {
//			    tmpdata.setCo(Double.parseDouble(allDataData.get("S06-Rtd")));
//			}
			// 烟道截面积 
//			if (allDataData.containsKey("S07-Rtd") && !allDataData.get("S07-Rtd").equals(null)) {
//			    tmpdata.setCo(Double.parseDouble(allDataData.get("S07-Rtd")));
//			}
			// 烟气压力
			if (allDataData.containsKey("S08-Rtd") && !allDataData.get("S08-Rtd").equals(null)) {
				tmpdata.setGas_pa(Double.parseDouble(allDataData.get("S08-Rtd")));
			}
			// 废气(立方米/秒)
			if (allDataData.containsKey("B02-Rtd") && !allDataData.get("B02-Rtd").equals(null)) {
				tmpdata.setWaste_gas(Double.parseDouble(allDataData.get("B02-Rtd")));
			}
			// 烟尘实时采样数据
			if (allDataData.containsKey("01-Rtd") && !allDataData.get("01-Rtd").equals(null)) {
			    tmpdata.setSoot(Double.parseDouble(allDataData.get("01-Rtd")));
			}
			// 烟尘实时采样折算数据
			if (allDataData.containsKey("01-ZsRtd") && !allDataData.get("01-ZsRtd").equals(null)) {
			    tmpdata.setSoot_zs(Double.parseDouble(allDataData.get("01-ZsRtd")));
			}
			// 二氧化硫实时采样数据
			if (allDataData.containsKey("02-Rtd") && !allDataData.get("02-Rtd").equals(null)) {
			    tmpdata.setSo2(Double.parseDouble(allDataData.get("02-Rtd")));
			}
			// 二氧化硫实时采样折算数据
			if (allDataData.containsKey("02-ZsRtd") && !allDataData.get("02-ZsRtd").equals(null)) {
			    tmpdata.setSo2_zs(Double.parseDouble(allDataData.get("02-ZsRtd")));
			}
			// 氮氧化物实时采样数据
			if (allDataData.containsKey("03-Rtd") && !allDataData.get("03-Rtd").equals(null)) {
			    tmpdata.setNox(Double.parseDouble(allDataData.get("03-Rtd")));
			}
			// 氮氧化物实时采样折算数据
			if (allDataData.containsKey("03-ZsRtd") && !allDataData.get("03-ZsRtd").equals(null)) {
			    tmpdata.setNox_zs(Double.parseDouble(allDataData.get("03-ZsRtd")));
			}
			
			// 附带大气监测(温度、湿度、气压、风速、风向)
			if (allDataData.containsKey("TEM-Rtd") && !allDataData.get("TEM-Rtd").equals(null)) {
                tmpdata.setTem(Double.parseDouble(allDataData.get("TEM-Rtd")));
            }
			if (allDataData.containsKey("RH-Rtd") && !allDataData.get("RH-Rtd").equals(null)) {
                tmpdata.setRh(Double.parseDouble(allDataData.get("RH-Rtd")));
            }
            if (allDataData.containsKey("PA-Rtd") && !allDataData.get("PA-Rtd").equals(null)) {
                tmpdata.setPa(Double.parseDouble(allDataData.get("PA-Rtd")));
            }
            if (allDataData.containsKey("WS-Rtd") && !allDataData.get("WS-Rtd").equals(null)) {
                tmpdata.setWs(Double.parseDouble(allDataData.get("WS-Rtd")));
            }
            if (allDataData.containsKey("WD-Rtd") && !allDataData.get("WD-Rtd").equals(null)) {
                tmpdata.setWd(Double.parseDouble(allDataData.get("WD-Rtd")));
            }
		
			// CN=2011实时数据
			// CN=2051分钟数据
			if (allHeaderData.containsKey("CN")) {
				if(allHeaderData.get("CN").equals("2011") || allHeaderData.get("CN").equals("2051")) {
					result = tmpdata;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private int GetCRC(String data212) {
		int CRC = 0xFFFF;
		int Num = 0xA001;
		int inum = 0;
		byte[] sb = data212.getBytes();
		for (int j = 0; j < sb.length; j++) {
			inum = sb[j];// data212[j];
			CRC = (CRC >> 8) & 0x00FF;
			CRC ^= inum;

			for (int k = 0; k < 8; k++) {
				int flag = CRC % 2;
				CRC = CRC >> 1;

				if (flag == 1) {
					CRC = CRC ^ Num;
				}
			}
		}
		return CRC;
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
		return "TVOC旧版标准";
	}
	
//	public static void main(String[] args) {
//	    String data = "##0372QN=20180315134456001;ST=31;CN=2011;PW=123456;MN=TY0102183V0001;CP=&&DataTime=20180315134400;S03-Rtd=147.12,S03-Flag=N;S08-Rtd=1.349,S08-Flag=N;S05-Rtd=8.000,S05-Flag=N;S01-Rtd=14.913,S01-Flag=N;S02-Rtd=6.38,S02-Flag=N;B02-Rtd=3600.0,B02-Flag=N;01-Rtd=25.2100,01-ZsRtd=63.6616,01-Flag=N;02-Rtd=20.3400,02-ZsRtd=51.0317,02-Flag=N;03-Rtd=50.5467,03-ZsRtd=125.0510,03-Flag=N&&A781";
//        ProcessTVOC212 standard212 = new ProcessTVOC212();
//        if(standard212.CheckData(data)) {
//            List<OlMonitorMinData> min = standard212.Process(data);
//            System.out.println(min.size());
//        }
//    }

    @Override
    public String GetProcessSysCode() {
        return AIR_POLLUTE_MONITOR_212;
    }
}