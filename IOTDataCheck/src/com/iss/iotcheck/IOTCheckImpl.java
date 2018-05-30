package com.iss.iotcheck;

import java.util.List;

import com.iss.iotcheck.model.OlMonitorMinData;
import com.iss.iotcheck.model.OlMonitorPositionData;
import com.iss.iotcheck.model.OlMonitorWaterData;
import com.iss.iotcheck.plugin.IProcessing;
import com.iss.iotcheck.plugin.NationalStandard212;
import com.iss.iotcheck.plugin.Process212;
import com.iss.iotcheck.plugin.ProcessPosition;
import com.iss.iotcheck.plugin.ProcessTVOC212;
import com.iss.iotcheck.plugin.ProcessWater212;
import com.iss.iotcheck.tools.DateHelper;
import com.iss.iotcheck.tools.StringUtil;

/**
 * 检验出的异常：
 * 1.212协议检测异常
 * 2.包含空格错误
 * 3.设备号不合规范 > 字母加数字
 * 4.数据包头错误
 * @author Liu Siyuan
 *
 */
public class IOTCheckImpl implements IOTCheck{
    
    @Override
    public String checkMessage(String mess, int type) {
        if(Integer.valueOf(IProcessing.AIR_MONITOR_MONITOR_212) == type
                || Integer.valueOf(IProcessing.AIR_POLLUTE_MONITOR_212) == type) {
            if(!"##".equals(mess.substring(0, 2))) {
                return RE_START_CHAR_ERR;
            }
            int start = mess.lastIndexOf("&&");
            if(!isHexNumber(mess.substring(start + 2, mess.length()))) {
                return RE_CRC_STRING_ERR;
            }
            // 大气
            IProcessing p1 = new Process212();
            IProcessing p2 = new NationalStandard212();
            IProcessing p3 = new ProcessTVOC212();
            List<OlMonitorMinData> minData = null;
            if(p1.CheckData(mess)) {
                minData = (List<OlMonitorMinData>) p1.Process(mess);
            } else if(p2.CheckData(mess)) {
                minData = (List<OlMonitorMinData>) p2.Process(mess);
            } else if(p3.CheckData(mess)) {
                minData = (List<OlMonitorMinData>) p3.Process(mess);
            } else {
                return RE_HEADER_ERR;
            }
            if(minData.size() == 0) {
                if(!checkCRC(mess)) {
                    // CRC校验错误
                    return RE_CRC_ERR;
                } else {                    
                    // 不知道什么情况
                    return RE_PROTOCOL212_OTHER_ERR;
                }
            }
            OlMonitorMinData monitorMinData = minData.get(0);
            String deviceId = monitorMinData.getDeviceId();
            // 检查设备号
            if(!checkDevieId(deviceId)) {
                return RE_MN_ERR;
            }
            if(Integer.valueOf(IProcessing.AIR_MONITOR_MONITOR_212) == type) {
                // 输出大气监测因子+气象五参
                return "<html><body><b>检测成功>>></b><br/>设备: " + deviceId + "<br/>时间: " + DateHelper.format(monitorMinData.getMonitorTime())
                + "<hr>PM2.5: " + monitorMinData.getPm25() + "<br/>PM10: " + monitorMinData.getPm10()
                + "<br/>SO2: " + monitorMinData.getSo2() + "<br/>CO: " + monitorMinData.getCo()
                + "<br/>O3: " + monitorMinData.getO3() + "<br/>NO2: " + monitorMinData.getNo2()
                + "<br/>WS: " + monitorMinData.getWs() + "<br/>WD: " + monitorMinData.getWd()
                + "<br/>TEM: " + monitorMinData.getTem() + "<br/>RH: " + monitorMinData.getRh()
                + "<br/>PA: " + monitorMinData.getWs() + "<br/>TSP: " + monitorMinData.getTsp()
                + "<br/>噪声: " + monitorMinData.getNoise() 
                + "</body>"; 
            } else {
                // 输出TVOC监测因子+气象五参
                return "<html><body><b>检测成功>>></b><br/>设备: " + deviceId + "<br/>时间: " + DateHelper.format(monitorMinData.getMonitorTime())
                + "<br/>甲硫醇: " + monitorMinData.getCh4s() + "<br/>硫化氢:" + + monitorMinData.getH2s()
                + "<br/>TVOC: " + monitorMinData.getTvocs() 
                + "<br/>温度: " + monitorMinData.getTem() + "<br/>湿度: " + monitorMinData.getRh()
                + "<br/>含氧量: " + monitorMinData.getO2_content() + "<br/>烟气流速: " + monitorMinData.getStack_gas_velocity()
                + "<br/>烟气温度: " + monitorMinData.getGas_tem() + "<br/>烟气湿度: " + monitorMinData.getGas_rh()
                + "<br/>烟气压力: " + monitorMinData.getGas_pa() + "<br/>废气: " + monitorMinData.getWaste_gas()
                + "<br/>烟尘: " + monitorMinData.getSoot() + "<br/>烟尘采样折算值: " + monitorMinData.getSoot_zs()
                + "<br/>SO2: " + monitorMinData.getSo2() + "<br/>SO2采样折算值: " + monitorMinData.getSo2_zs()
                + "<br/>氮氧化物: " + monitorMinData.getNox() + "<br/>氮氧化物采样折算值: " + monitorMinData.getNox_zs() 
                + "</body>"; 
            }
        } else if(Integer.valueOf(IProcessing.SURFACE_WATER_MONITOR_212) == type) {
            // 水
            if(!"##".equals(mess.substring(0, 2))) {
                return RE_START_CHAR_ERR;
            }
            int start = mess.lastIndexOf("&&");
            if(!isHexNumber(mess.substring(start + 2, mess.length()))) {
                return RE_CRC_STRING_ERR;
            }
            IProcessing p = new ProcessWater212();
            List<OlMonitorWaterData> minData = null;
            if(p.CheckData(mess)) {
                if(p.CheckData(mess)) {
                    minData = (List<OlMonitorWaterData>) p.Process(mess);
                } else {
                    return RE_HEADER_ERR;
                }
            } else {
                return RE_HEADER_ERR;
            }
            if(minData.size() == 0) {
                if(!checkCRC(mess)) {
                    // CRC检测错误
                    return RE_CRC_ERR;
                } else {                    
                    // 不知道什么情况
                    return RE_PROTOCOL212_OTHER_ERR;
                }
            }
            OlMonitorWaterData monitorMinData = minData.get(0);
            String deviceId = monitorMinData.getDeviceId();
            // 检查设备号
            if(!checkDevieId(deviceId)) {
                return RE_PROTOCOL212_OTHER_ERR;
            }
            
            // 结果
            return "<html><body><b>检测成功>>></b><br/>设备: " + deviceId + "<br/>时间: " + DateHelper.format(monitorMinData.getMonitorTime())
            + "<br/>COD: " + monitorMinData.getCod() + "<br/>Bod: " + monitorMinData.getBod()
            + "<br/>NH3NH4: " + monitorMinData.getNH3NH4() + "<br/>KMNO4: " + monitorMinData.getKMnO4()
            + "<br/>PH: " + monitorMinData.getPH() + "<br/>DO: " + monitorMinData.getDO()
            + "<br/>Conductivity: " + monitorMinData.getConductivity() + "<br/>FTU: " + monitorMinData.getFTU()
            + "<br/>Temperature: " + monitorMinData.getTemperature() + "<br/>TP: " + monitorMinData.getTP()
            + "<br/>TN: " + monitorMinData.getTN() + "<br/>Cu: " + monitorMinData.getCu()
            + "<br/>Zn: " + monitorMinData.getZn() + "<br/>F: " + monitorMinData.getF()
            + "<br/>As: " + monitorMinData.getAs() + "<br/>Hg: " + monitorMinData.getHg()
            + "<br/>Cd: " + monitorMinData.getCd() + "<br/>Cr6: " + monitorMinData.getCr6()
            + "<br/>Pb: " + monitorMinData.getPb() + "<br/>Fe: " + monitorMinData.getFe()
            + "<br/>CN: " + monitorMinData.getCN() + "<br/>ArOH: " + monitorMinData.getArOH()
            + "<br/>Oil: " + monitorMinData.getOil() + "<br/>anionics: " + monitorMinData.getAnionics()
            + "<br/>sulfide: " + monitorMinData.getSulfide() + "<br/>NO3N: " + monitorMinData.getNO3N()
            + "<br/>biotoxicity: " + monitorMinData.getBiotoxicity() + "<br/>chlorophyl_a: " + monitorMinData.getChlorophyla()
            + "<br/>algae: " + monitorMinData.getAlgae()
            + "</body>";
        } else if(Integer.valueOf(IProcessing.POSITION_MONITOR_212) == type) {
            IProcessing p = new ProcessPosition();
            List<OlMonitorPositionData> minData = null;
            if(p.CheckData(mess)) {
                minData = (List<OlMonitorPositionData>) p.Process(mess);
            } else {
                return RE_HEADER_ERR;
            }
            
            if(minData.size() == 0) {
                if(!checkCRC(mess)) {
                    // CRC检测错误
                    return RE_CRC_ERR;
                } else {                    
                    // 不知道什么情况
                    return RE_PROTOCOL212_OTHER_ERR;
                }
            }
            OlMonitorPositionData monitorMinData = minData.get(0);
            String deviceId = monitorMinData.getDeviceId();
            // 检查设备号
            if(!checkDevieId(deviceId)) {
                return RE_PROTOCOL212_OTHER_ERR;
            }
            
            // 结果
            return "<html><body><b>检测成功>>></b><br/>设备: " + deviceId + "<br/>时间: " + DateHelper.format(monitorMinData.getMonitorTime())
            + "<br/>Type: " + monitorMinData.getType() + (monitorMinData.getType().equals("1") ? "-手机" : "-GPS") + "<br/>Region: " + monitorMinData.getRegionCode()
            + "<br/>address: " + monitorMinData.getAddress() + "<br/>longitude: " + monitorMinData.getLongitude()
            + "<br/>latitude: " + monitorMinData.getLatitude() + "<br/>speed: " + monitorMinData.getSpeed()
            + "<br/>direction: " + monitorMinData.getDirection() + "<br/>height: " + monitorMinData.getHeight()
            + "<br/>satellite: " + monitorMinData.getSatellite()
            + "</body>";
        }
        return RE_CODE_OK;
    }

    private static boolean checkCRC(String mess) {
        int start = mess.indexOf("ST=");
        int end = mess.lastIndexOf("&&");
        String target = mess.substring(start, end + 2);
        String CRC = Integer.toHexString(StringUtil.getCRC(target)).toUpperCase();
        return CRC.equals(mess.substring(end + 2, mess.length()));
    }

//    public static void main(String[] args) {
////        String[] dev = new String[] {
////                "123",
////                "abc",
////                "123abc",
////                "123456"
////        };
////        Arrays.asList(dev).forEach(s -> System.out.println(checkDevieId(s)));
//        System.out.println(checkCRC("##0223ST=22;CN=2011;PW=123456;MN=C037800AM2011;CP=&&DataTime=20180328235000;SO2-Rtd=19.0,SO2-Flag=N;NO2-Rtd=25.0,NO2-Flag=N;CO-Rtd=0.687,CO-Flag=N;O3-Rtd=76.0,O3-Flag=N;PM25-Rtd=75.0,PM25-Flag=N;PM10-Rtd=374.0,PM10-Flag=N&&4380"));
//    }
    
    private static boolean checkDevieId(String str) {
        if(str == null || str.length() ==0 || str.length() < 10) {
            return false;
        }
        return containNum(str) && containNum(str);
    }
    
    public static boolean containNum(String str){
        char ch[] = str.toCharArray();
        boolean contain=false;
        for(int i=0;i<ch.length;i++){
            if(Character.isDigit(ch[i])){
                contain=true;
            }
        }
        return contain;
    }

    public static boolean containLetter(String str){
        char ch[] = str.toCharArray();
        boolean contain=false;
        for(int i=0;i<ch.length;i++){
            if(Character.isLetter(ch[i])){
                contain=true;
            }
        }
        return contain;
    }
    
    //十六进制  
    private static boolean isHexNumber(String str){  
        boolean flag = true;  
        for(int i=0;i<str.length();i++){  
            char cc = str.charAt(i);  
            if(!(cc=='0'||cc=='1'||cc=='2'||cc=='3'||cc=='4'||cc=='5'||cc=='6'||cc=='7'||cc=='8'||cc=='9'||cc=='A'||cc=='B'||cc=='C'||  
                    cc=='D'||cc=='E'||cc=='F'||cc=='a'||cc=='b'||cc=='c'||cc=='c'||cc=='d'||cc=='e'||cc=='f')){  
                flag = false;  
                break;
            }  
        }  
        return flag;  
    }  
}
