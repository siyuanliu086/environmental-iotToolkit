package com.iss.iotcheck.plugin;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.iss.iotcheck.model.OlMonitorWaterData;
import com.iss.iotcheck.tools.DateHelper;

/**
*@Author wanghao
*@Description: 水环境TVOC数据解析
*@Date:9:49 2018/3/16
*/
public class ProcessWater212 implements IProcessing {

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
	public List<OlMonitorWaterData> Process(String command) {

		List<OlMonitorWaterData> result = new ArrayList<OlMonitorWaterData>();

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
		 * 系统编号ST：212协议的系统编号，“21”代表 地表水质监测；
		 * 
		 * 命令编号CN：议212协的命令编号，“2011”代表 取污染物实时数据（分钟数据）；
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
		/**
		 * ##0494ST=21;CN=2011;PW=123456;MN=6C611136H;CP=&&DataTime=20180315164206;
		 * w01018-Rtd=1.31,w01018-Flag=N;w21003-Rtd=79,w21003-Flag=N;
		 * w01019-Rtd=31,w01019-Flag=N;w01001-Rtd=51.5,w01001-Flag=N;
		 * w01009-Rtd=8,w01009-Flag=N;w01014-Rtd=67,w01014-Flag=N;
		 * a01021-Rtd=99,a01021-Flag=N;w01010-Rtd=6.18,w01010-Flag=N;
		 * w21011-Rtd=14,w21011-Flag=N;w21001-Rtd=2,w21001-Flag=N;
		 * w20141-Rtd=8,w20141-Flag=N;w20142-Rtd=0.96,w20142-Flag=N;
		 * w20143-Rtd=1,w20143-Flag=Nw20144-Rtd=10,w20144-Flag=Nw20117-Rtd=5,w20117-Flag=N&&9901
		 */
		int dbeginIndex = command.indexOf("&&") + 2;
		int dendIndex = command.lastIndexOf("&&");

		String data = command.substring(dbeginIndex, dendIndex);
		String crckey = command.substring(dendIndex + 2, command.length());
		int icrckey = 0;
        try {
            icrckey = Integer.parseInt(crckey+"", 16);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

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
		OlMonitorWaterData olMonitorWaterData = ConverData(allHeaderData, allDataData);
		if (olMonitorWaterData != null) {
			//
			result.add(olMonitorWaterData);
		}

		// 返回
		return result;
	}

	/*
	 * 转换字典形式数据为实体数据
	 */
	private OlMonitorWaterData ConverData(HashMap<String, String> allHeaderData, HashMap<String, String> allDataData) {
		OlMonitorWaterData result = null;
		//字典对应关系，把国标的数据字段对应到监测数据库的数据项上
		// 如果日期时间型，需要格式化
		try {
			OlMonitorWaterData tmpdata = new OlMonitorWaterData();
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

            // Cod
			if (allDataData.containsKey("w01018-Rtd") && !allDataData.get("w01018-Rtd").equals(null)) {
				tmpdata.setCod(Double.parseDouble(allDataData.get("w01018-Rtd")));
			}
			// Bod
			if (allDataData.containsKey("w01017-Rtd") && !allDataData.get("w01017-Rtd").equals(null)) {
				tmpdata.setBod(Double.parseDouble(allDataData.get("w01017-Rtd")));
			}
			// 氨氮
			if (allDataData.containsKey("w21003-Rtd") && !allDataData.get("w21003-Rtd").equals(null)) {
				tmpdata.setNH3NH4(Double.parseDouble(allDataData.get("w21003-Rtd")));
			}
			// 高锰酸盐指数
			if (allDataData.containsKey("w01019-Rtd") && !allDataData.get("w01019-Rtd").equals(null)) {
				tmpdata.setKMnO4(Double.parseDouble(allDataData.get("w01019-Rtd")));
			}
			// PH
			if (allDataData.containsKey("w01001-Rtd") && !allDataData.get("w01001-Rtd").equals(null)) {
			    tmpdata.setPH(Double.parseDouble(allDataData.get("w01001-Rtd")));
			}
			// 溶解氧
			if (allDataData.containsKey("w01009-Rtd") && !allDataData.get("w01009-Rtd").equals(null)) {
				tmpdata.setDO(Double.parseDouble(allDataData.get("w01009-Rtd")));
			}
			// 电导率
			if (allDataData.containsKey("w01014-Rtd") && !allDataData.get("w01014-Rtd").equals(null)) {
			    tmpdata.setConductivity(Double.parseDouble(allDataData.get("w01014-Rtd")));
			}
			// 浊度
			if (allDataData.containsKey("a01021-Rtd") && !allDataData.get("a01021-Rtd").equals(null)) {
			    tmpdata.setFTU(Double.parseDouble(allDataData.get("a01021-Rtd")));
			}
			// 水温
			if (allDataData.containsKey("w01010-Rtd") && !allDataData.get("w01010-Rtd").equals(null)) {
				tmpdata.setTemperature(Double.parseDouble(allDataData.get("w01010-Rtd")));
			}
			// 总磷
			if (allDataData.containsKey("w21011-Rtd") && !allDataData.get("w21011-Rtd").equals(null)) {
				tmpdata.setTP(Double.parseDouble(allDataData.get("w21011-Rtd")));
			}
			// 总氮
			if (allDataData.containsKey("w21001-Rtd") && !allDataData.get("w21001-Rtd").equals(null)) {
			    tmpdata.setTN(Double.parseDouble(allDataData.get("w21001-Rtd")));
			}
			// 铜
			if (allDataData.containsKey("w20122-Rtd") && !allDataData.get("w20122-Rtd").equals(null)) {
			    tmpdata.setCu(Double.parseDouble(allDataData.get("w20122-Rtd")));
			}
			// 锌
			if (allDataData.containsKey("w20139-Rtd") && !allDataData.get("w20139-Rtd").equals(null)) {
			    tmpdata.setZn(Double.parseDouble(allDataData.get("w20139-Rtd")));
			}
			// 氟化物
			if (allDataData.containsKey("w21017-Rtd") && !allDataData.get("w21017-Rtd").equals(null)) {
			    tmpdata.setF(Double.parseDouble(allDataData.get("w21017-Rtd")));
			}
			// 砷
			if (allDataData.containsKey("w20141-Rtd") && !allDataData.get("w20141-Rtd").equals(null)) {
			    tmpdata.setAs(Double.parseDouble(allDataData.get("w20141-Rtd")));
			}
			// 汞
			if (allDataData.containsKey("w20142-Rtd") && !allDataData.get("w20142-Rtd").equals(null)) {
			    tmpdata.setHg(Double.parseDouble(allDataData.get("w20142-Rtd")));
			}
			// 镉
			if (allDataData.containsKey("w20143-Rtd") && !allDataData.get("w20143-Rtd").equals(null)) {
				tmpdata.setCd(Double.parseDouble(allDataData.get("w20143-Rtd")));
			}
			// 六价铬
			if (allDataData.containsKey("w20117-Rtd") && !allDataData.get("w20117-Rtd").equals(null)) {
				tmpdata.setCr6(Double.parseDouble(allDataData.get("w20117-Rtd")));
			}
			// 铅
			if (allDataData.containsKey("w20144-Rtd") && !allDataData.get("w20144-Rtd").equals(null)) {
				tmpdata.setPb(Double.parseDouble(allDataData.get("w20144-Rtd")));
			}
			// 总铁
			if (allDataData.containsKey("w20125-Rtd") && !allDataData.get("w20125-Rtd").equals(null)) {
				tmpdata.setFe(Double.parseDouble(allDataData.get("w20125-Rtd")));
			}
			// 氰化物
			if (allDataData.containsKey("w21016-Rtd") && !allDataData.get("w21016-Rtd").equals(null)) {
				tmpdata.setCN(Double.parseDouble(allDataData.get("w21016-Rtd")));
			}
			// 挥发酚
			if (allDataData.containsKey("w23002-Rtd") && !allDataData.get("w23002-Rtd").equals(null)) {
				tmpdata.setArOH(Double.parseDouble(allDataData.get("w23002-Rtd")));
			}
			// 石油类
			if (allDataData.containsKey("w22001-Rtd") && !allDataData.get("w22001-Rtd").equals(null)) {
				tmpdata.setOil(Double.parseDouble(allDataData.get("w22001-Rtd")));
			}
			// 阴离子表面活性剂
			if (allDataData.containsKey("w19002-Rtd") && !allDataData.get("w19002-Rtd").equals(null)) {
				tmpdata.setAnionics(Double.parseDouble(allDataData.get("w19002-Rtd")));
			}
			// 硫化物
			if (allDataData.containsKey("w21019-Rtd") && !allDataData.get("w21019-Rtd").equals(null)) {
				tmpdata.setSulfide(Double.parseDouble(allDataData.get("w21019-Rtd")));
			}
			// 硝酸盐氮
			if (allDataData.containsKey("w2100721001-Rtd") && !allDataData.get("w2100721001-Rtd").equals(null)) {
				tmpdata.setNO3N(Double.parseDouble(allDataData.get("w2100721001-Rtd")));
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
		return "地表水监测212解析";
	}
	
//	public static void main(String[] args) {
//	    String data = "##0823ST=21;CN=2011;PW=123456;MN=huantai_device_0001;CP=&&DataTime=20180319155633;w01018-Rtd=2.01,w01018-Flag=N;w01017-Rtd=2.11,w01017-Flag=N;w21003-Rtd=83,w21003-Flag=N;w01019-Rtd=34,w01019-Flag=N;w01001-Rtd=51.6,w01001-Flag=N;w01009-Rtd=11,w01009-Flag=N;w01014-Rtd=69,w01014-Flag=N;a01021-Rtd=101,a01021-Flag=N;w01010-Rtd=-0.18,w01010-Flag=N;w21011-Rtd=18,w21011-Flag=N;w21001-Rtd=10,w21001-Flag=N;w20122-Rtd=11,w20122-Flag=N;w20139-Rtd=3,w20139-Flag=N;w21017-Rtd=3,w21017-Flag=N;w20141-Rtd=1,w20141-Flag=N;w20142-Rtd=1.03,w20142-Flag=N;w20143-Rtd=11,w20143-Flag=N;w20144-Rtd=7,w20144-Flag=N;w20117-Rtd=4,w20117-Flag=N;w20125-Rtd=1,w20125-Flag=N;w21016-Rtd=7,w21016-Flag=N;w23002-Rtd=1,w23002-Flag=N;w22001-Rtd=11,w22001-Flag=N;w19002-Rtd=10,w19002-Flag=N;w21019-Rtd=8,w21019-Flag=N;w2100721001-Rtd=7,w2100721001-Flag=N&&4980";
//        ProcessWater212 standard212 = new ProcessWater212();
//        if(standard212.CheckData(data)) {
//            List<OlMonitorWaterData> min = standard212.Process(data);
//            System.out.println(min.size());
//        }
//    }

    @Override
    public String GetProcessSysCode() {
        return SURFACE_WATER_MONITOR_212;
    }
}