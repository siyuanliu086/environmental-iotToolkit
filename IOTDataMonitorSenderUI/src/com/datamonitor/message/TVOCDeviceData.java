package com.datamonitor.message;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import com.alibaba.fastjson.JSONObject;
import com.datamonitor.utils.StringUtil;

public class TVOCDeviceData implements IDeviceData {
    private String deviceId;
    
    private String server;
    private String port;
    
    private String message;
    
    private String dataTime;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    
    public TVOCDeviceData(String server, String port) {
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

        String o2_content = "";
        String stack_gas_velocity = "";
        String gas_tem = "";
        String gas_rh = "";
        String gas_pa = "";
        String waste_gas = "";
        String soot = "";
        String soot_zs = "";
        String so2 = "";
        String so2_zs = "";
        String nox = "";
        String nox_zs = "";      
        if(factorStyle != null && factorStyle.containsKey("o2_content")) {
            float o2_content_ = factorStyle.getFloatValue("o2_content");
            int o2_content_err = factorStyle.getInteger("o2_content_err");
            o2_content = getRateValue(random, o2_content_, o2_content_err == 1);
        } else if(fullFactor){
            o2_content = String.valueOf(0.71f + getRandomFixedData() / 18.0f);
        }
        if(factorStyle != null && factorStyle.containsKey("stack_gas_velocity")) {
            float stack_gas_velocity_ = factorStyle.getFloatValue("stack_gas_velocity");
            int stack_gas_velocity_err = factorStyle.containsKey("stack_gas_velocity_err") ? factorStyle.getInteger("stack_gas_velocity_err") : 0;
            stack_gas_velocity = getRateValue(random, stack_gas_velocity_, stack_gas_velocity_err == 1);
        } else if(fullFactor){
            stack_gas_velocity = String.valueOf(6.38f + getRandomFixedData());
        }
        if(factorStyle != null && factorStyle.containsKey("gas_tem")) {
            float gas_tem_ = factorStyle.getFloatValue("gas_tem");
            int gas_tem_err = factorStyle.containsKey("gas_tem_err") ? factorStyle.getInteger("gas_tem_err") : 0;
            gas_tem = getRateValue(random, gas_tem_, gas_tem_err == 1);
        } else if(fullFactor){
            gas_tem = String.valueOf(147.12f + getRandomFixedData());
        }
        if(factorStyle != null && factorStyle.containsKey("gas_rh")) {
            float gas_rh_ = factorStyle.getFloatValue("gas_rh");
            int gas_rh_err = factorStyle.containsKey("gas_rh_err") ? factorStyle.getInteger("gas_rh_err") : 0;
            gas_rh = getRateValue(random, gas_rh_, gas_rh_err == 1);
        } else if(fullFactor){
            gas_rh = String.valueOf(8.000f + getRandomFixedData() / 10.0f);
        }
        if(factorStyle != null && factorStyle.containsKey("gas_pa")) {
            float gas_pa_ = factorStyle.getFloatValue("gas_pa");
            int gas_pa_err = factorStyle.containsKey("gas_pa_err") ? factorStyle.getInteger("gas_pa_err") : 0;
            gas_pa = getRateValue(random, gas_pa_, gas_pa_err == 1);
        } else if(fullFactor){
            gas_pa = String.valueOf(1.349f + getRandomFixedData());
        }
        if(factorStyle != null && factorStyle.containsKey("waste_gas")) {
            float waste_gas_ = factorStyle.getFloatValue("waste_gas");
            int waste_gas_err = factorStyle.containsKey("waste_gas_err") ? factorStyle.getInteger("waste_gas") : 0;
            waste_gas = getRateValue(random, waste_gas_, waste_gas_err == 1);
        } else if(fullFactor){
            waste_gas = String.valueOf(3600.0f + getRandomFixedData());
        }
        if(factorStyle != null && factorStyle.containsKey("soot")) {
            float soot_ = factorStyle.getFloatValue("soot");
            int soot_err = factorStyle.containsKey("soot_err") ? factorStyle.getInteger("soot") : 0;
            soot = getRateValue(random, soot_, soot_err == 1);
        } else if(fullFactor){
            soot = String.valueOf(25.2100f + getRandomFixedData());
        }
        if(factorStyle != null && factorStyle.containsKey("soot_zs")) {
            float soot_zs_ = factorStyle.getFloatValue("soot_zs");
            int soot_zs_err = factorStyle.containsKey("soot_zs_err") ? factorStyle.getInteger("soot_zs_err") : 0;
            soot_zs = getRateValue(random, soot_zs_, soot_zs_err == 1);
        } else if(fullFactor){
            soot_zs = String.valueOf(63.6616f + getRandomTemData());
        }
        if(factorStyle != null && factorStyle.containsKey("so2")) {
            float so2_ = factorStyle.getFloatValue("so2");
            int so2_err = factorStyle.containsKey("so2_err") ? factorStyle.getInteger("so2_err") : 0;
            so2 = getRateValue(random, so2_, so2_err == 1);
        } else if(fullFactor){
            so2 = String.valueOf(9f + getRandomFixedData());
        }
        if(factorStyle != null && factorStyle.containsKey("so2_zs")) {
            float so2_zs_ = factorStyle.getFloatValue("so2_zs");
            int so2_zs_err = factorStyle.containsKey("so2_zs_err") ? factorStyle.getInteger("so2_zs_err") : 0;
            so2_zs = getRateValue(random, so2_zs_, so2_zs_err == 1);
        } else if(fullFactor){
            so2_zs = String.valueOf(51.0317f + getRandomFixedData());
        }
        if(factorStyle != null && factorStyle.containsKey("nox")) {
            float nox_ = factorStyle.getFloatValue("nox");
            int nox_err = factorStyle.containsKey("nox_err") ? factorStyle.getInteger("nox_err") : 0;
            nox = getRateValue(random, nox_, nox_err == 1);
        } else if(fullFactor){
            nox = String.valueOf(50.5467f + getRandomFixedData());
        }
        if(factorStyle != null && factorStyle.containsKey("nox_zs")) {
            float nox_zs_ = factorStyle.getFloatValue("nox_zs");
            int nox_zs_err = factorStyle.containsKey("nox_zs_err") ? factorStyle.getInteger("nox_zs_err") : 0;
            nox_zs = getRateValue(random, nox_zs_, nox_zs_err == 1);
        } else if(fullFactor){
            nox_zs = String.valueOf(125.0510f + getRandomFixedData() / 2.0f);
        }
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String dataTime = sdf.format(new Date());
        stArea.append("DataTime=" + dataTime + ";");
        if(!StringUtil.isEmptyString(o2_content)) {            
            stArea.append("S01-Rtd=").append(o2_content).append(",S01-Flag=N;");
        }
        if(!StringUtil.isEmptyString(stack_gas_velocity)) {            
            stArea.append("S02-Rtd=").append(stack_gas_velocity).append(",S02-Flag=N;");
        }
        if(!StringUtil.isEmptyString(gas_tem)) {             
            stArea.append("S03-Rtd=").append(gas_tem).append(",S03-Flag=N;");
        }
        if(!StringUtil.isEmptyString(gas_rh)) {            
            stArea.append("S05-Rtd=").append(gas_rh).append(",S05-Flag=N;");
        }
        if(!StringUtil.isEmptyString(gas_pa)) {            
            stArea.append("S08-Rtd=").append(gas_pa).append(",S08-Flag=N;");
        }
        if(!StringUtil.isEmptyString(waste_gas)) {            
            stArea.append("B02-Rtd=").append(waste_gas).append(",B02-Flag=N;");
        }
        if(!StringUtil.isEmptyString(soot)) {
            stArea.append("01-Rtd=").append(soot);//1
        }
        if(!StringUtil.isEmptyString(soot_zs)) {            
            stArea.append(",01-ZsRtd=").append(soot_zs);//2
        }
        if(!StringUtil.isEmptyString(soot) || !StringUtil.isEmptyString(soot_zs)) {
            stArea.append(",01-Flag=N;");//3
        }
        if(!StringUtil.isEmptyString(so2)) {            
            stArea.append("02-Rtd=").append(so2);//1
        }
        if(!StringUtil.isEmptyString(so2_zs)) {
            stArea.append(",02-ZsRtd=").append(so2_zs);//2
        }
        if(!StringUtil.isEmptyString(so2) || !StringUtil.isEmptyString(so2_zs)) {            
            stArea.append(",02-Flag=N;");//3
        }
        if(!StringUtil.isEmptyString(nox)) {            
            stArea.append("03-Rtd=").append(nox);//1
        }
        if(!StringUtil.isEmptyString(nox_zs)) {
            stArea.append(",03-ZsRtd=").append(nox_zs);//2
        }
        if(!StringUtil.isEmptyString(nox) || !StringUtil.isEmptyString(nox_zs)) {  
            stArea.append(",03-Flag=N");//3
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
     * @author      : Liu Siyuan
     * @Date        : 2018年4月10日 下午4:03:49
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
        return 7 + random.nextInt(8);
    }

    private Random random = new Random();
    private float getRandomFixedData() {
        return random.nextInt(12);
    }

    @Override
    public String getDeviceType() {
        return AIR_POLLUTE_MONITOR_212;
    }
    
    @Override
    public int getDataType() {
        return TYPE_AIR_TVOC212;
    }
    
    private JSONObject factorStyle;
    @Override
    public void setFactorStyle(JSONObject jo) {
        factorStyle = jo;
    }
    private boolean fullFactor;
    @Override
    public void setAutoFullFactor(boolean b) {
        fullFactor = b;
    }
}