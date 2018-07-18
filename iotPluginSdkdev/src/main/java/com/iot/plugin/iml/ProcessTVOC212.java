package com.iot.plugin.iml;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import app.iot.process.database.entity.OlMonitorMinData;
import app.iot.process.plugins.AbsProcessor;
import app.tools.JsonHelper;

/**
 * 大气环境污染源TVOC软通212
 * 
 * @author Liu Siyuan
 */
public class ProcessTVOC212 extends AbsProcessor {

    /*
     * 检查数据是否是本协议的数据
     */
    public boolean checkData(String data) {
        boolean result = false;
        String[] tempArr = data.split("ST=");
        if (tempArr.length == 2) {
            String systemCode = tempArr[1].substring(0, 2);
            if (getProcessSysCode().equals(systemCode)) {// 系统编码31,大气环境污染源
                result = true;
                writeDataLog("设备数据校验完成");
            }
        }
        return result;
    }

    /*
     * 处理本数据，正常情况应该是返回一条或者多条， 如果返回数据少于一条，那就是解析失败，数据需要保存到失败数据库
     */
    @Override
    public List<OlMonitorMinData> process(String command) {
        writeDataLog("收到预处理数据:" + command);
        List<OlMonitorMinData> result = new ArrayList<OlMonitorMinData>();
        // 接受处理的的数据
        // 注意，由于是字符串数据，只有在收到数据知道后使用使用回车换行的，才会触发这个信息：环保部212和天津国标的设备就是这个模式。
        // 首先验证数据crc，然后验证数据长度，然后获取数据值

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
        // int keylength = Integer.parseInt(command.substring(2, hbeginIndex));
        // int datalength = command.substring(hbeginIndex, dendIndex + 2).length();
        // if(keylength!=datalength){
        // 文件长度不对
        // return;
        // }
        // 校验crc
        if (icrccomputer != icrckey) {
            writeDataLog("CRC 校验异常！" + icrccomputer);
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
            result.add(olmonitormin);
        }
        writeDataLog("数据解析完成:");
        return result;
    }

    @Override
    public List<String> process2Json(String data) {
        List<String> result = new ArrayList<String>();
        List<OlMonitorMinData> dataList = process(data);
        if (dataList == null || dataList.size() == 0) {
            return result;
        }
        for (OlMonitorMinData minData : dataList) {
            try {
                result.add(JsonHelper.beanToJsonStr(minData));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /*
     * 转换字典形式数据为实体数据
     */
    private OlMonitorMinData ConverData(HashMap<String, String> allHeaderData, HashMap<String, String> allDataData) {
        OlMonitorMinData result = null;
        // 字典对应关系，把国标的数据字段对应到监测数据库的数据项上
        // 如果日期时间型，需要格式化
        try {
            OlMonitorMinData tmpdata = new OlMonitorMinData();
            tmpdata.setSourcepointId(allHeaderData.get("MN"));
            tmpdata.setDeviceId(allHeaderData.get("MN"));

            // 时间处理特殊判断，错误兼容
            if (allDataData.get("DataTime") != null && !allDataData.get("DataTime").isEmpty()) {
                try {
                    tmpdata.setMonitorTime(dataDateFormat.parse(allDataData.get("DataTime")));
                } catch (Exception e) {
                    // 时间转换失败
                    writeDataLog("时间转换失败:" + allDataData);
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
            // if (allDataData.containsKey("S04-Rtd") && !allDataData.get("S04-Rtd").equals(null)) {
            // tmpdata.setSo2(Double.parseDouble(allDataData.get("S04-Rtd")));
            // }
            // 烟气湿度
            if (allDataData.containsKey("S05-Rtd") && !allDataData.get("S05-Rtd").equals(null)) {
                tmpdata.setGas_rh(Double.parseDouble(allDataData.get("S05-Rtd")));
            }
            // 制冷温度
            // if (allDataData.containsKey("S06-Rtd") && !allDataData.get("S06-Rtd").equals(null)) {
            // tmpdata.setCo(Double.parseDouble(allDataData.get("S06-Rtd")));
            // }
            // 烟道截面积
            // if (allDataData.containsKey("S07-Rtd") && !allDataData.get("S07-Rtd").equals(null)) {
            // tmpdata.setCo(Double.parseDouble(allDataData.get("S07-Rtd")));
            // }
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
            // 【恶臭浓度 v00000 】 ，甲硫醇 28 ，【氨 10】 ，TVOC v01001 ， 硫化氢 05
            if (allDataData.containsKey("05-Rtd") && !allDataData.get("05-Rtd").equals(null)) {
                tmpdata.setH2s(Double.parseDouble(allDataData.get("05-Rtd")));
            }
            // if (allDataData.containsKey("10-Rtd") && !allDataData.get("10-Rtd").equals(null)) {
            // tmpdata.setNox_zs(Double.parseDouble(allDataData.get("10-Rtd")));
            // }
            if (allDataData.containsKey("28-Rtd") && !allDataData.get("28-Rtd").equals(null)) {
                tmpdata.setCh4s(Double.parseDouble(allDataData.get("28-Rtd")));
            }
            if (allDataData.containsKey("v01001-Rtd") && !allDataData.get("v01001-Rtd").equals(null)) {
                tmpdata.setTvocs(Double.parseDouble(allDataData.get("v01001-Rtd")));
            }
            if (allDataData.containsKey("TVOC-Rtd") && !allDataData.get("TVOC-Rtd").equals(null)) {
                tmpdata.setTvocs(Double.parseDouble(allDataData.get("TVOC-Rtd")));
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
                if (allHeaderData.get("CN").equals("2011") || allHeaderData.get("CN").equals("2051")) {
                    result = tmpdata;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public String getProcessName() {
        return "TVOC旧版标准";
    }

    public static void main(String[] args) {
        String data = "##0240QN=20180703105956001;ST=31;CN=2011;PW=123456;MN=12345678901235;Flag=5;CP=&&DataTime=20180703105900;TEM-Rtd=34.3,TEM-Flag=N;RH-Rtd=43.5,RH-Flag=N;PA-Rtd=21.000,PA-Flag=N;WS-Rtd=1.5,WS-Flag=N;WD-Rtd=245,WD-Flag=N;TVOC-Rtd=0.0000,TVOC-Flag=N&&5500";
        ProcessTVOC212 standard212 = new ProcessTVOC212();
        if (standard212.checkData(data)) {
            List<OlMonitorMinData> min = standard212.process(data);
            System.out.println(min.size());
        }
    }

    public String getProcessSysCode() {
        return AIR_POLLUTE_MONITOR_212;
    }
}
