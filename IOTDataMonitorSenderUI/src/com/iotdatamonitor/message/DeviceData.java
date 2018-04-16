package com.iotdatamonitor.message;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import com.alibaba.fastjson.JSONObject;
import com.iotdatamonitor.utils.StringUtil;

public class DeviceData implements IDeviceData{
    private String deviceId;
    
    private String server;
    private String port;
    
    private String message;
    
    private boolean fullFactor;
    
    public DeviceData(String server, String port) {
        this.server = server;
        this.port = port;
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
        
        String CO = "";
        String NO2 = "";
        String SO2 = "";
        String O3 = "";
        String NOIS = "";
        String PM25 = "";
        String PM10 = "";
        String TEM = "";
        String RH = "";
        String WD = "";
        String WS = "";
        String PA = "";
        String TSP = "";
        if(factorStyle != null && factorStyle.containsKey("co")) {
            float co_ = factorStyle.getFloatValue("co");
            int co_err = factorStyle.containsKey("co_err") ? factorStyle.getInteger("co_err") : 0;
            CO = getRateValue(random, co_, co_err == 1);
        } else if(fullFactor){
            CO = String.valueOf(0.71f + getRandomFixedData() / 18.0f);
        }
        if(factorStyle != null && factorStyle.containsKey("no2")) {
            float no2_ = factorStyle.getFloatValue("no2");
            int no2_err = factorStyle.containsKey("no2_err") ? factorStyle.getInteger("no2_err") : 0;
            NO2 = getRateValue(random, no2_, no2_err == 1);
        } else if(fullFactor){
            NO2 = String.valueOf(36f + getRandomFixedData());
        }
        if(factorStyle != null && factorStyle.containsKey("so2")) {
            float so2_ = factorStyle.getFloatValue("so2");
            int so2_err = factorStyle.containsKey("so2_err") ? factorStyle.getInteger("so2_err") : 0;
            SO2 = getRateValue(random, so2_, so2_err == 1);
        } else if(fullFactor){
            SO2 = String.valueOf(9f + getRandomFixedData());
        }
        if(factorStyle != null && factorStyle.containsKey("o3")) {
            float o3_ = factorStyle.getFloatValue("o3");
            int o3_err = factorStyle.containsKey("o3_err") ? factorStyle.getInteger("o3_err") : 0;
            O3 = getRateValue(random, o3_, o3_err == 1);
        } else if(fullFactor){
            O3 = String.valueOf(21f + getRandomFixedData() / 10.0f);
        }
        if(factorStyle != null && factorStyle.containsKey("nois")) {
            float nois_ = factorStyle.getFloatValue("nois");
            int nois_err = factorStyle.containsKey("nois_err") ? factorStyle.getInteger("nois_err") : 0;
            NOIS = getRateValue(random, nois_, nois_err == 1);
        } else if(fullFactor){
            NOIS = String.valueOf(32f + getRandomFixedData());
        }
        if(factorStyle != null && factorStyle.containsKey("pm25")) {
            float pm25_ = factorStyle.getFloatValue("pm25");
            int pm25_err = factorStyle.containsKey("pm25_err") ? factorStyle.getInteger("pm25_err") : 0;
            PM25 = getRateValue(random, pm25_, pm25_err == 1);
        } else if(fullFactor){
            PM25 = String.valueOf(17f + getRandomFixedData());
        }
        if(factorStyle != null && factorStyle.containsKey("pm10")) {
            float pm10_ = factorStyle.getFloatValue("pm10");
            int pm10_err = factorStyle.containsKey("pm10_err") ? factorStyle.getInteger("pm10_err") : 0;
            PM10 = getRateValue(random, pm10_, pm10_err == 1);
        } else if(fullFactor){
            PM10 = String.valueOf(18f + getRandomFixedData());
        }
        if(factorStyle != null && factorStyle.containsKey("tem")) {
            float tem_ = factorStyle.getFloatValue("tem");
            int tem_err = factorStyle.containsKey("tem_err") ? factorStyle.getInteger("tem_err") : 0;
            TEM = getRateValue(random, tem_, tem_err == 1);
        } else if(fullFactor){
            TEM = String.valueOf(getRandomTemData());
        }
        if(factorStyle != null && factorStyle.containsKey("rh")) {
            float rh_ = factorStyle.getFloatValue("rh");
            int rh_err = factorStyle.containsKey("rh_err") ? factorStyle.getInteger("rh_err") : 0;
            RH = getRateValue(random, rh_, rh_err == 1);
        } else if(fullFactor){
            RH = String.valueOf(51f + getRandomFixedData());
        }
        if(factorStyle != null && factorStyle.containsKey("wd")) {
            float wd_ = factorStyle.getFloatValue("wd");
            int wd_err = factorStyle.containsKey("wd_err") ? factorStyle.getInteger("wd_err") : 0;
            WD = getRateValue(random, wd_, wd_err == 1);
        } else if(fullFactor){
            WD = String.valueOf(getRandomFixedData());
        }
        if(factorStyle != null && factorStyle.containsKey("ws")) {
            float ws_ = factorStyle.getFloatValue("ws");
            int ws_err = factorStyle.containsKey("ws_err") ? factorStyle.getInteger("ws_err") : 0;
            WS = getRateValue(random, ws_, ws_err == 1);
        } else if(fullFactor){
            WS = String.valueOf(1 + getRandomFixedData() / 2.0f);
        }
        if(factorStyle != null && factorStyle.containsKey("pa")) {
            float pa_ = factorStyle.getFloatValue("pa");
            int pa_err = factorStyle.containsKey("pa_err") ? factorStyle.getInteger("pa_err") : 0;
            PA = getRateValue(random, pa_, pa_err == 1);
        } else if(fullFactor){
            PA = String.valueOf(getRandomPaData());
        }
        if(factorStyle != null && factorStyle.containsKey("tsp")) {
            float tsp_ = factorStyle.getFloatValue("tsp");
            int tsp_err = factorStyle.containsKey("tsp_err") ? factorStyle.getInteger("tsp_err") : 0;
            TSP = getRateValue(random, tsp_, tsp_err == 1);
        } else if(fullFactor){
            TSP = String.valueOf(getRandomFixedData());
        }
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String dataTime = sdf.format(new Date());
        
        stArea.append("DataTime=" + dataTime + ";");
        if(!StringUtil.isEmptyString(CO)) {            
            stArea.append("CO-Rtd=").append(CO).append(",CO-Flag=N;");
        }
        if(!StringUtil.isEmptyString(NO2)) {            
            stArea.append("NO2-Rtd=").append(NO2).append(",NO2-Flag=N;");
        }
        if(!StringUtil.isEmptyString(SO2)) {             
            stArea.append("SO2-Rtd=").append(SO2).append(",SO2-Flag=N;");
        }
        if(!StringUtil.isEmptyString(O3)) {            
            stArea.append("O3-Rtd=").append(O3).append(",O3-Flag=N;");
        }
        if(!StringUtil.isEmptyString(NOIS)) {            
            stArea.append("NOIS-Rtd=").append(NOIS).append(",NOIS-Flag=N;");
        }
        if(!StringUtil.isEmptyString(PM25)) {            
            stArea.append("PM25-Rtd=").append(PM25).append(",PM25-Flag=N;");
        }
        if(!StringUtil.isEmptyString(PM10)) {
            stArea.append("PM10-Rtd=").append(PM10).append(",PM10-Flag=N;");
        }
        if(!StringUtil.isEmptyString(TEM)) {            
            stArea.append("TEM-Rtd=").append(TEM).append(",TEM-Flag=N;");
        }
        if(!StringUtil.isEmptyString(RH)) {            
            stArea.append("RH-Rtd=").append(RH).append(",RH-Flag=N;");
        }
        if(!StringUtil.isEmptyString(WD)) {
            stArea.append("WD-Rtd=").append(WD).append(",WD-Flag=N;");
        }
        if(!StringUtil.isEmptyString(WS)) {            
            stArea.append("WS-Rtd=").append(WS).append(",WS-Flag=N;");
        }
        if(!StringUtil.isEmptyString(PA)) {
            stArea.append("PA-Rtd=").append(PA).append(",PA-Flag=N;");
        }
        if(!StringUtil.isEmptyString(TSP)) {
            stArea.append("TSP-Rtd=").append(TSP).append(",TSP-Flag=N");
        }
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
    
    /**
     * @description : 将数值d在90.0%-110.0%之间波动(保持小数位数)
     * @author 		: Liu Siyuan
     * @Date 		: 2018年4月10日 下午4:03:49
     * @version 1.0.0
     * @param isError 
     */
    public static String getRateValue(Random random, float d, boolean isError) {
        float rate = (random.nextInt(200) + 900) / 1000.0f;
        
        // isError == true 随机制造一些异常情况（0值，负值，超大值）
        if(isError) {
            if(rate < 0.96) {
                //0值
                rate = 0;//变0值
            } else if(rate < 1.03) {
                rate *= -1;//变负值
            } else {
                rate *= 123;//常规值得123倍
            }
        }
        
        int decimal = getDecimal(d);
        return String.format("%."+decimal+"f", d * rate);
    }
    
    /**
     * @description : 获取数值的小数位（等于整数时取整）
     * @author      : Liu Siyuan
     * @Date        : 2018年4月10日 下午4:03:49
     * @version 1.0.0
     */
    public static int getDecimal(float num) {
        String numStr = String.valueOf(num);
        if(numStr.contains(".") && !numStr.endsWith(".0")) {
            numStr = numStr.replace('.', ';');
            String[] ss = numStr.split(";");
            return ss[1].length();
        }
        return 0;
    }

    private float getRandomPaData() {
        Double value = Math.pow(10, 3);
        return 0.95f + (random.nextInt(value.intValue())) / 11111f;
    }

    private float getRandomTemData() {
        return 16 + random.nextInt(6);
    }

    private Random random = new Random();
    private float getRandomFixedData() {
        return random.nextInt(12);
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

    @Override
    public void setAutoFullFactor(boolean b) {
        fullFactor = b;
    }
}

