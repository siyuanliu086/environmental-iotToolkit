package com.iotdatamonitor.message;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import com.alibaba.fastjson.JSONObject;
import com.iotdatamonitor.utils.StringUtil;

public class ElectMeterDeviceData implements IDeviceData{
    private String deviceId;
    
    private String server;
    private String port;
    
    private String message;
    
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    
    public ElectMeterDeviceData(String server, String port) {
        this.server = server;
        this.port = port;
    }
    
    public void setDeviceInfo(String deviceId) {
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
        float KW = getRandomTemData();
//        float TEM = getRandomTemData();
//        float RH = 51f + getRandomFixedData();
//        float WD = getRandomFixedData();
//        float WS = 1 + getRandomFixedData() / 2.0f;
//        float PA = getRandomPaData();
        
        stArea.append("DataTime=" + sdf.format(new Date()) + ";");
        stArea.append("KW-Rtd=" + decimalFormat.format(KW)).append(",KW-Flag=N;");
//        stArea.append("TEM-Rtd=" + decimalFormat.format(TEM)).append(",TEM-Flag=N;");
//        stArea.append("RH-Rtd=" + decimalFormat.format(RH)).append(",RH-Flag=N;");
//        stArea.append("WD-Rtd=" + decimalFormat.format(WD)).append(",WD-Flag=N;");
//        stArea.append("WS-Rtd=" + decimalFormat.format(WS)).append(",WS-Flag=N;");
//        stArea.append("PA-Rtd=" + decimalFormat.format(PA)).append(",PA-Flag=N;");
//        stArea.append("TSP-Rtd=" + decimalFormat.format(TSP)).append(",TSP-Flag=N");
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
        String s = "ST=ZHDB;CN=2011;PW=123456;MN=XH8010000000001;CP=&&DataTime=20180322152611;VOC-Rtd=70.11,VOC-Flag=N&&";
        String CRC = Integer.toHexString(StringUtil.getCRC(s.toString())).toUpperCase();
        System.out.println(CRC);
    }

    private Random random = new Random();
    
    private float getRandomPaData() {
        Double value = Math.pow(10, 3);
        return 0.95f + (random.nextInt(value.intValue())) / 11111f;
    }

    private float getRandomTemData() {
        return 80 + random.nextInt(30) + getRandomPaData();
    }

    @Override
    public String getDeviceType() {
        return "ZHDB";
    }

    @Override
    public int getDataType() {
        return 5;
    }

    @Override
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public JSONObject factorStyle;
    @Override
    public void setFactorStyle(JSONObject jo) {
        factorStyle = jo;
    }

    @Override
    public void setAutoFullFactor(boolean b) {
    }
}