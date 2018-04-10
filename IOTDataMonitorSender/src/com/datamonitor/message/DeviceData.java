package com.datamonitor.message;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import com.alibaba.fastjson.JSONObject;
import com.datamonitor.ConfigFile;
import com.datamonitor.utils.StringUtil;

public class DeviceData implements IDeviceData{
    private String deviceId;
    
    private String server;
    private String port;
    
    private String message;
    
    private String dataTime;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    
    private int index;
    
    public DeviceData(String server, String port) {
        this.server = server;
        this.port = port;
        dataTime = sdf.format(new Date());
    }
    
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
    
    public void setServer(String server, String port) {
        this.server = server;
        this.port = port;
    }
    
    public String getServer() {
        return server;
    }
    
    public String getPort() {
        return port;
    }
    
    
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * 数据格式：##数据段长度ST=22(39);CN=2011;PW=123456;MN=781703664AM0001;CP=&&数据区&&CRC\r\n
     * @description : 获取数据包，组装数据包格式
     * @return		: 数据包字符串
     * @author 		: Liu Siyuan
     * @Date 		: 2017年8月23日 下午4:53:57
     * @version 1.0.0
     */
    //##299ST=22;CN=2011;PW=;MN=100000000AM1975;CP=&&DataTime=20171220040009;PM10-Rtd=69,PM10-Flag=N;PM25-Rtd=28,PM25-Flag=N;SO2-Rtd=23,SO2-Flag=N;CO-Rtd=1.3,CO-Flag=N;O3-Rtd=41,O3-Flag=N;NO2-Rtd=28,NO2-Flag=N;TEM-Rtd=18.54,TEM-Flag=N;RH-Rtd=13.58,RH-Flag=N;WD-Rtd=315.0,WD-Flag=N;WS-Rtd=2.45,WS-Flag=N&&d042
    public String getMessage() {
        StringBuffer stArea = new StringBuffer("ST=");
        stArea.append(getDeviceType()).append(";");
        stArea.append("CN=2011;PW=123456;MN="+deviceId+";");
        stArea.append("CP=&&");
        DecimalFormat decimalFormat =  new DecimalFormat("#.##");
        float CO = 0.71f + getRandomFixedData() / 18.0f;
        float NO2 = 36f + getRandomFixedData();
        float SO2 = 9f + getRandomFixedData();
        float O3 = 21f + getRandomFixedData() / 10.0f;
        float NOIS = 32f + getRandomFixedData();
        float PM25 = 17f + getRandomFixedData();
        float PM10 = 18f + getRandomFixedData();
        float TEM = getRandomTemData();
        float RH = 51f + getRandomFixedData();
        float WD = getRandomFixedData();
        float WS = 1 + getRandomFixedData() / 2.0f;
        float PA = getRandomPaData();
        float TSP = getRandomFixedData();
        
        // 故意制造
//        if(index == 5 || index == 7 || index == 9 || index == 11) {
//            PM25 += 30;
//            PM10 += 20;
//            SO2 += 10;
//            NO2 += 10;
//            CO += 2;
//        }
        
        
        stArea.append("DataTime=" + dataTime + ";");
        stArea.append("CO-Rtd=" + decimalFormat.format(CO)).append(",CO-Flag=N;");
        stArea.append("NO2-Rtd=" + decimalFormat.format(NO2)).append(",NO2-Flag=N;");
        stArea.append("SO2-Rtd=" + decimalFormat.format(SO2)).append(",SO2-Flag=N;");
        stArea.append("O3-Rtd=" + decimalFormat.format(O3)).append(",O3-Flag=N;");
        stArea.append("NOIS-Rtd=" + decimalFormat.format(NOIS)).append(",NOIS-Flag=N;");
        stArea.append("PM25-Rtd=" + decimalFormat.format(PM25)).append(",PM25-Flag=N;");
        stArea.append("PM10-Rtd=" + decimalFormat.format(PM10)).append(",PM10-Flag=N;");
        stArea.append("TEM-Rtd=" + decimalFormat.format(TEM)).append(",TEM-Flag=N;");
        stArea.append("RH-Rtd=" + decimalFormat.format(RH)).append(",RH-Flag=N;");
        stArea.append("WD-Rtd=" + decimalFormat.format(WD)).append(",WD-Flag=N;");
        stArea.append("WS-Rtd=" + decimalFormat.format(WS)).append(",WS-Flag=N;");
        stArea.append("PA-Rtd=" + decimalFormat.format(PA)).append(",PA-Flag=N;");
        stArea.append("TSP-Rtd=" + decimalFormat.format(TSP)).append(",TSP-Flag=N");
        stArea.append("&&");
        
        String CRC = Integer.toHexString(StringUtil.getCRC(stArea.toString())).toUpperCase();
        
        StringBuffer dataArea = new StringBuffer(stArea);
        dataArea.append(CRC);
        dataArea.append("\r\n");
        
        StringBuffer result = new StringBuffer("##");
        int length = dataArea.length();
        result.append(length < 1000 ? "0" + length : length);
        result.append(dataArea);
        message = result.toString();
        return message;
    }
    
    public static void main(String[] args) {
        //String s = "ST=22;CN=2011;PW=123456;MN=XH_70001;CP=&&DataTime=20150811151200;PM10-Rtd=89.59,PM10-Flag=N;TSP-Rtd=133.71,TSP-Flag=N&&";
        String s = "ST=22;CN=2011;PW=123456;MN=XH8010000000001;CP=&&DataTime=20180322152611;VOC-Rtd=70.11,VOC-Flag=N&&";
        String CRC = Integer.toHexString(StringUtil.getCRC(s.toString())).toUpperCase();
        System.out.println(CRC);
    }
    
    /**
     * 数据格式：##数据段长度ST=22(39);CN=2011;PW=123456;MN=781703664AM0001;CP=&&数据区&&CRC\r\n
     * @description : 获取数据包，组装数据包格式
     * @return      : 数据包字符串
     * @author      : Liu Siyuan
     * @Date        : 2017年8月23日 下午4:53:57
     * @version 1.0.0
     */
//    public String getMessage(String factor) {
//        StringBuffer stArea = new StringBuffer("ST=");
//        stArea.append(deviceType).append(";");
//        stArea.append("CN=2011;PW=123456;MN=781703664AM0001;");
//        stArea.append("CP=&&");
//        DecimalFormat decimalFormat =  new DecimalFormat("#.##");
//        float CO = getRandomFixedData(1);
//        float NO2 = getRandomFixedData(2);
//        float SO2 = getRandomFixedData(2);
//        float O3 = getRandomFixedData(2);
//        float NOIS = getRandomFixedData(2);
//        float PM25 = getRandomFixedData(2);
//        float PM10 = getRandomFixedData(2);
//        float TEM = getRandomTemData();
//        float RH = getRandomFixedData(2);
//        float WD = getRandomFixedData(1);
//        float WS = getRandomFixedData(2);
//        float PA = getRandomPaData();
//        float TSP = getRandomFixedData(2);
//        stArea.append("DataTime=" + dataTime + ";");
//        stArea.append("CO-Rtd=" + decimalFormat.format(CO)).append(",CO-Flag=N;");
//        stArea.append("NO2-Rtd=" + decimalFormat.format(NO2)).append(",NO2-Flag=N;");
//        stArea.append("SO2-Rtd=" + decimalFormat.format(SO2)).append(",SO2-Flag=N;");
//        stArea.append("O3-Rtd=" + decimalFormat.format(O3)).append(",O3-Flag=N;");
//        stArea.append("NOIS-Rtd=" + decimalFormat.format(NOIS)).append(",NOIS-Flag=N;");
//        stArea.append("PM25-Rtd=" + decimalFormat.format(PM25)).append(",PM25-Flag=N;");
//        stArea.append("PM10-Rtd=" + decimalFormat.format(PM10)).append(",PM10-Flag=N;");
//        stArea.append("TEM-Rtd=" + decimalFormat.format(TEM)).append(",TEM-Flag=N;");
//        stArea.append("RH-Rtd=" + decimalFormat.format(RH)).append(",RH-Flag=N;");
//        stArea.append("WD-Rtd=" + decimalFormat.format(WD)).append(",WD-Flag=N;");
//        stArea.append("WS-Rtd=" + decimalFormat.format(WS)).append(",WS-Flag=N;");
//        stArea.append("PA-Rtd=" + decimalFormat.format(PA)).append(",PA-Flag=N;");
//        stArea.append("TSP-Rtd=" + decimalFormat.format(TSP)).append(",TSP-Flag=N");
//        stArea.append("&&");
//        
//        String CRC = Integer.toHexString(StringUtil.getCRC(stArea.toString())).toUpperCase();
//        
//        StringBuffer dataArea = new StringBuffer(stArea);
//        dataArea.append(CRC);
//        dataArea.append("\r\n");
//        
//        StringBuffer result = new StringBuffer("##");
//        int length = dataArea.length();
//        result.append(length < 1000 ? "0" + length : length);
//        result.append(dataArea);
//        message = result.toString();
//        return message;
//    }
    
//    public void setMessage(String message) {
//        this.message = message;
//    }

    private float getRandomPaData() {
        Double value = Math.pow(10, 3);
        return 0.95f + (random.nextInt(value.intValue())) / 11111f;
    }

    private float getRandomTemData() {
        return 7 + random.nextInt(8);
    }

    private Random random = new Random();
    private float getRandomFixedData() {
        return random.nextInt(12);
    }
    
//    public static void main(String[] args) {
//        DeviceData d = new DeviceData("", "");
//        d.setDeviceInfo("ZB000000000001", "22");
//       System.out.println(d.getMessage());
//    }
    
//    public static void main(String[] args) {
//        DeviceData data = new DeviceData("127.0.0.1", "8080");
//        System.out.println(data.getMessage());
//    }
    
//    public static void main(String[] args) {
////        String info = "ST=22;CN=2011;PW=;MN=100000000AM1976;CP=&&DataTime=20180111092009;PM10-Rtd=68,PM10-Flag=N;PM25-Rtd=31,PM25-Flag=N;SO2-Rtd=21,SO2-Flag=N;CO-Rtd=0.9,CO-Flag=N;O3-Rtd=52,O3-Flag=N;NO2-Rtd=15,NO2-Flag=N;TEM-Rtd=14.28,TEM-Flag=N;RH-Rtd=8.06,RH-Flag=N;WD-Rtd=0.0,WD-Flag=N;WS-Rtd=4.4,WS-Flag=N&&";
//        //##0251ST=22;CN=2011;PW=123456;MN=JH0201712SD8763;CP=&&DataTime=20180115031850;PM25-Rtd=83.8,PM25-Flag=N;PM10-Rtd=138.7,PM10-Flag=N;TSP-Rtd=209.0,TSP-Flag=N;TEM-Rtd=10,TEM-Flag=N;RH-Rtd=0,RH-Flag=N;WS-Rtd=0,WS-Flag=N;WD-Rtd=0,WD-Flag=N;NOIS-Rtd=45.2,NOIS=N&&4037
//        //##0249ST=22;CN=2011;PW=123456;MN=JH0201712SD8763;CP=&&DataTime=20180114002828;PM25-Rtd=15.4,PM25-Flag=N;PM10-Rtd=31.7,PM10-Flag=N;TSP-Rtd=75.0,TSP-Flag=N;TEM-Rtd=10,TEM-Flag=N;RH-Rtd=0,RH-Flag=N;WS-Rtd=0,WS-Flag=N;WD-Rtd=0,WD-Flag=N;NOIS-Rtd=46.3,NOIS=N&&4045
//        // ##0420QN=20160801085000001;ST=22;CN=2011;PW=123456;MN=010000A8900016F000169DC0;Flag=7;CP=&&DataTime=20160801084000;a21005-Rtd=1.1,a21005-Flag=N;a21004-Rtd=112,a21004-Flag=N;a21026-Rtd=58,a21026-Flag=N;LA-td=50.1,LA-Flag=N;a34004-Rtd=207,a34004-Flag=N;a34002-Rtd=295,a34002-Flag=N;a01001-Rtd=12.6,a01001-Flag=N;a01002-Rtd=32,a01002-Flag=N;a01006-Rtd=101.02,a01006-Flag=N;a01007-Rtd=2.1,a01007-Flag=N;a01008-Rtd=120,a01008-Flag=N;a34001-Rtd=217,a34001-Flag=N;&&
//        //String info = "ST=22;CN=2011;PW=123456;MN=JH0201712SD8763;CP=&&DataTime=20180114002828;PM25-Rtd=15.4,PM25-Flag=N;PM10-Rtd=31.7,PM10-Flag=N;TSP-Rtd=75.0,TSP-Flag=N;TEM-Rtd=10,TEM-Flag=N;RH-Rtd=0,RH-Flag=N;WS-Rtd=0,WS-Flag=N;WD-Rtd=0,WD-Flag=N;NOIS-Rtd=46.3,NOIS=N&&";
//        String info = "QN=20160801085000001;ST=22;CN=2011;PW=123456;MN=010000A8900016F000169DC0;Flag=7;CP=&&DataTime=20160801084000;a21005-Rtd=1.1,a21005-Flag=N;a21004-Rtd=112,a21004-Flag=N;a21026-Rtd=58,a21026-Flag=N;a21030-Rtd=64,a21030-Flag=N;LA-Rtd=50.1,LA-Flag=N;a34004-Rtd=207,a34004-Flag=N;a34002-Rtd=295,a34002-Flag=N;a01001-Rtd=12.6,a01001-Flag=N;a01002-Rtd=32,a01002-Flag=N;a01006-Rtd=101.02,a01006-Flag=N;a01007-Rtd=2.1,a01007-Flag=N;a01008-Rtd=120,a01008-Flag=N;a34001-Rtd=217,a34001-Flag=N;&&";
//        System.out.println(info.length());
//        System.out.println(Integer.toHexString(StringUtil.getCRC(info)));
//    }
    
    public static int getCRC(String data212) {
        int CRC = 0xFFFF;
        int num = 0xA001;
        int inum = 0;
        byte[] sb = data212.getBytes();
        for(int j = 0; j < sb.length; j ++) {
            inum = sb[j];
            CRC = (CRC >> 8) & 0x00FF;
            CRC ^= inum;
            for(int k = 0; k < 8; k++) {
                int flag = CRC % 2;
                CRC = CRC >> 1;
            
                if(flag == 1) {
                    CRC = CRC ^ num;
                }
            }
        }
        return CRC;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String getDeviceType() {
        return AIR_MONITOR_MONITOR_212;
    }

    @Override
    public int getDataType() {
        return TYPE_AIR_SIM212;
    }

    private JSONObject factorStyle;
    @Override
    public void setFactorStyle(JSONObject jo) {
        factorStyle = jo;
    }
}

