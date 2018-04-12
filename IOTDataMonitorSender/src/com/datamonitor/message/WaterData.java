package com.datamonitor.message;

import com.alibaba.fastjson.JSONObject;
import com.datamonitor.utils.StringUtil;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class WaterData implements IDeviceData {
    private String deviceId;

    private String server;
    private String port;

    private String message;

    public WaterData(String server, String port) {
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
    //##299ST=21;CN=2011;PW=;MN=100000000AM1975;CP=&&DataTime=20171220040009;PM10-Rtd=69,PM10-Flag=N;PM25-Rtd=28,PM25-Flag=N;SO2-Rtd=23,SO2-Flag=N;CO-Rtd=1.3,CO-Flag=N;O3-Rtd=41,O3-Flag=N;NO2-Rtd=28,NO2-Flag=N;TEM-Rtd=18.54,TEM-Flag=N;RH-Rtd=13.58,RH-Flag=N;WD-Rtd=315.0,WD-Flag=N;WS-Rtd=2.45,WS-Flag=N&&d042
    public String getMessage() {
        StringBuffer stArea = new StringBuffer("ST=");
        stArea.append(getDeviceType()).append(";");
        stArea.append("CN=2011;PW=123456;MN="+deviceId+";");
        stArea.append("CP=&&");
        
        String Cod = "";
        String Bod = "";
        String NH3NH4 = "";
        String KMnO4 = "";
        String PH = "";
        String DO = "";
        String Conductivity = "";
        String FTU = "";
        String Temperature = "";
        String TP =  "";// 保证一级
        String TN = "";// 保证二级
        String CU = "";
        String Zn = "";
        String F = "";
        String As = "";
        String Hg = "";
        String Cd = "";
        String Pb = "";
        String Cr6 = "";
        String Fe = "";
        String Cn = "";// 保证一级
        String ArOH = "";// 保证一级
        String Oil = "";// 保证一级
        String anionics = "";// 保证一级
        String sulfide = "";// 保证一级
        String NO3N = "";

        if(factorStyle != null && factorStyle.containsKey("cod")) {
            float cod_ = factorStyle.getFloatValue("cod");
            int cod_err = factorStyle.containsKey("cod_err") ? factorStyle.getInteger("cod_err") : 0;
            Cod = getRateValue(random, cod_, cod_err == 1);
        } else if(fullFactor){
            Cod = String.valueOf(2.2f + getRandomFixedData() / 12.0f * 15);
        }
        if(factorStyle != null && factorStyle.containsKey("bod")) {
            float bod_ = factorStyle.getFloatValue("bod");
            int bod_err = factorStyle.containsKey("bod_err") ? factorStyle.getInteger("bod_err") : 0;
            Bod = getRateValue(random, bod_, bod_err == 1);
        } else if(fullFactor){
            Bod = String.format("%.2f", getRandomFixedData() / 3.1f);
        }
        if(factorStyle != null && factorStyle.containsKey("nh3nh4")) {
            float nh3nh4_ = factorStyle.getFloatValue("nh3nh4");
            int nh3nh4_err = factorStyle.containsKey("nh3nh4_err") ? factorStyle.getInteger("nh3nh4_err") : 0;
            NH3NH4 = getRateValue(random, nh3nh4_, nh3nh4_err == 1);
        } else if(fullFactor){
            NH3NH4 = String.valueOf(getRandomFixedData() / 10.0f);
        }
        if(factorStyle != null && factorStyle.containsKey("kmno4")) {
            float kmno4_ = factorStyle.getFloatValue("kmno4");
            int kmno4_err = factorStyle.containsKey("kmno4_err") ? factorStyle.getInteger("kmno4_err") : 0;
            KMnO4 = getRateValue(random, kmno4_, kmno4_err == 1);
        } else if(fullFactor){
            KMnO4 = String.valueOf(getRandomFixedData());
        }
        if(factorStyle != null && factorStyle.containsKey("ph")) {
            float ph_ = factorStyle.getFloatValue("ph");
            int ph_err = factorStyle.containsKey("ph_err") ? factorStyle.getInteger("ph_err") : 0;
            PH = getRateValue(random, ph_, ph_err == 1);
        } else if(fullFactor){
            PH = String.valueOf(6.8f + getRandomFixedData() / 10.0f);
        }
        if(factorStyle != null && factorStyle.containsKey("do")) {
            float do_ = factorStyle.getFloatValue("do");
            int do_err = factorStyle.containsKey("do_err") ? factorStyle.getInteger("do_err") : 0;
            DO = getRateValue(random, do_, do_err == 1);
        } else if(fullFactor){
            DO = String.valueOf(2.5f + getRandomFixedData());
        }
        if(factorStyle != null && factorStyle.containsKey("conductivity")) {
            float conductivity_ = factorStyle.getFloatValue("conductivity");
            int conductivity_err = factorStyle.containsKey("conductivity_err") ? factorStyle.getInteger("conductivity_err") : 0;
            Conductivity = getRateValue(random, conductivity_, conductivity_err == 1);
        } else if(fullFactor){
            Conductivity = String.valueOf(64f + getRandomFixedData());
        }
        if(factorStyle != null && factorStyle.containsKey("ftu")) {
            float ftu_ = factorStyle.getFloatValue("ftu");
            int ftu_err = factorStyle.containsKey("ftu_err") ? factorStyle.getInteger("ftu_err") : 0;
            FTU = getRateValue(random, ftu_, ftu_err == 1);
        } else if(fullFactor){
            FTU = String.valueOf(97f + getRandomFixedData());
        }
        if(factorStyle != null && factorStyle.containsKey("temperature")) {
            float temperature_ = factorStyle.getFloatValue("temperature");
            int temperature_err = factorStyle.containsKey("temperature_err") ? factorStyle.getInteger("temperature_err") : 0;
            Temperature = getRateValue(random, temperature_, temperature_err == 1);
        } else if(fullFactor){
            Temperature = String.format("%.2f", getRandomTemData());
        }
        if(factorStyle != null && factorStyle.containsKey("tp")) {
            float tp_ = factorStyle.getFloatValue("tp");
            int tp_err = factorStyle.containsKey("tp_err") ? factorStyle.getInteger("tp_err") : 0;
            TP = getRateValue(random, tp_, tp_err == 1);
        } else if(fullFactor){
            TP = String.format("%.2f", getRandomFixedData() / 121.0f);
        }
        if(factorStyle != null && factorStyle.containsKey("tn")) {
            float tn_ = factorStyle.getFloatValue("tn");
            int tn_err = factorStyle.containsKey("tn_err") ? factorStyle.getInteger("tn_err") : 0;
            TN = getRateValue(random, tn_, tn_err == 1);
        } else if(fullFactor){
            TN = String.format("%.2f", 0.2f + getRandomFixedData() / 240.0f);
        }
        if(factorStyle != null && factorStyle.containsKey("cu")) {
            float cu_ = factorStyle.getFloatValue("cu");
            int cu_err = factorStyle.containsKey("cu_err") ? factorStyle.getInteger("cu_err") : 0;
            CU = getRateValue(random, cu_, cu_err == 1);
        } else if(fullFactor){
            CU = String.format("%.5f", getRandomFixedData()/ 1200.0f + 0.001f);
        }
        if(factorStyle != null && factorStyle.containsKey("ws")) {
            float ws_ = factorStyle.getFloatValue("ws");
            int ws_err = factorStyle.containsKey("ws_err") ? factorStyle.getInteger("ws_err") : 0;
            Zn = getRateValue(random, ws_, ws_err == 1);
        } else if(fullFactor){
            Zn = String.format("%.2f", getRandomFixedData() / 12.0f);
        }
        if(factorStyle != null && factorStyle.containsKey("f")) {
            float f_ = factorStyle.getFloatValue("f");
            int f_err = factorStyle.containsKey("f_err") ? factorStyle.getInteger("f_err") : 0;
            F = getRateValue(random, f_, f_err == 1);
        } else if(fullFactor){
            F = String.format("%.2f", getRandomFixedData() / 12.0f);
        }
        if(factorStyle != null && factorStyle.containsKey("as")) {
            float as_ = factorStyle.getFloatValue("as");
            int as_err = factorStyle.containsKey("as_err") ? factorStyle.getInteger("as_err") : 0;
            As = getRateValue(random, as_, as_err == 1);
        } else if(fullFactor){
            As = String.format("%.2f", getRandomFixedData() / 240.0f);
        }
        if(factorStyle != null && factorStyle.containsKey("hg")) {
            float hg_ = factorStyle.getFloatValue("hg");
            int hg_err = factorStyle.containsKey("hg_err") ? factorStyle.getInteger("hg_err") : 0;
            Hg = getRateValue(random, hg_, hg_err == 1);
        } else if(fullFactor){
            Hg = String.format("%.5f", getRandomPaData()/ 24000.0f);
        }
        if(factorStyle != null && factorStyle.containsKey("cd")) {
            float cd_ = factorStyle.getFloatValue("cd");
            int cd_err = factorStyle.containsKey("cd_err") ? factorStyle.getInteger("cd_err") : 0;
            Cd = getRateValue(random, cd_, cd_err == 1);
        } else if(fullFactor){
            Cd = String.format("%.5f", getRandomFixedData() / 2400.0f);
        }
        if(factorStyle != null && factorStyle.containsKey("pb")) {
            float pb_ = factorStyle.getFloatValue("pb");
            int pb_err = factorStyle.containsKey("pb_err") ? factorStyle.getInteger("pb_err") : 0;
            Pb = getRateValue(random, pb_, pb_err == 1);
        } else if(fullFactor){
            Pb = String.format("%.2f", getRandomFixedData() / 240.0f);
        }
        if(factorStyle != null && factorStyle.containsKey("cr6")) {
            float cr6_ = factorStyle.getFloatValue("cr6");
            int cr6_err = factorStyle.containsKey("cr6_err") ? factorStyle.getInteger("cr6_err") : 0;
            Cr6 = getRateValue(random, cr6_, cr6_err == 1);
        } else if(fullFactor){
            Cr6 = String.format("%.2f", getRandomFixedData() / 240f);
        }
        if(factorStyle != null && factorStyle.containsKey("fe")) {
            float fe_ = factorStyle.getFloatValue("fe");
            int fe_err = factorStyle.containsKey("fe_err") ? factorStyle.getInteger("fe_err") : 0;
            Fe = getRateValue(random, fe_, fe_err == 1);
        } else if(fullFactor){
            Fe = String.valueOf(getRandomFixedData());
        }
        if(factorStyle != null && factorStyle.containsKey("cn")) {
            float cn_ = factorStyle.getFloatValue("cn");
            int cn_err = factorStyle.containsKey("cn_err") ? factorStyle.getInteger("cn_err") : 0;
            Cn = getRateValue(random, cn_, cn_err == 1);
        } else if(fullFactor){
            Cn = String.format("%.4f", getRandomFixedData() / 256.0f);
        }
        if(factorStyle != null && factorStyle.containsKey("aroh")) {
            float aroh_ = factorStyle.getFloatValue("aroh");
            int aroh_err = factorStyle.containsKey("aroh_err") ? factorStyle.getInteger("aroh_err") : 0;
            ArOH = getRateValue(random, aroh_, aroh_err == 1);
        } else if(fullFactor){
            ArOH = String.format("%.5f", getRandomFixedData()/ 1000.0f);
        }
        if(factorStyle != null && factorStyle.containsKey("oil")) {
            float oil_ = factorStyle.getFloatValue("oil");
            int oil_err = factorStyle.containsKey("oil_err") ? factorStyle.getInteger("oil_err") : 0;
            Oil = getRateValue(random, oil_, oil_err == 1);
        } else if(fullFactor){
            Oil = anionics = String.format("%.4f", 0.03f + getRandomFixedData() / 250.0f);
        }
        if(factorStyle != null && factorStyle.containsKey("anionics")) {
            float anionics_ = factorStyle.getFloatValue("anionics");
            int anionics_err = factorStyle.containsKey("anionics_err") ? factorStyle.getInteger("anionics_err") : 0;
            anionics = getRateValue(random, anionics_, anionics_err == 1);
        } else if(fullFactor){
            anionics = String.format("%.2f", getRandomFixedData()/ 80.0f);
        }
        if(factorStyle != null && factorStyle.containsKey("sulfide")) {
            float sulfide_ = factorStyle.getFloatValue("sulfide");
            int sulfide_err = factorStyle.containsKey("sulfide_err") ? factorStyle.getInteger("sulfide_err") : 0;
            sulfide = getRateValue(random, sulfide_, sulfide_err == 1);
        } else if(fullFactor){
            String.format("%.2f", getRandomFixedData()/ 240.0f);
        }
        if(factorStyle != null && factorStyle.containsKey("no3n")) {
            float no3n_ = factorStyle.getFloatValue("no3n");
            int no3n_err = factorStyle.containsKey("no3n_err") ? factorStyle.getInteger("no3n_err") : 0;
            NO3N = getRateValue(random, no3n_, no3n_err == 1);
        } else if(fullFactor){
            NO3N = String.valueOf(getRandomFixedData());
        }
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String dataTime = sdf.format(new Date());
        stArea.append("DataTime=" + dataTime + ";");
        if(!StringUtil.isEmptyString(Cod)) {            
            stArea.append("w01018-Rtd=").append(Cod).append(",w01018-Flag=N;");
        }
        if(!StringUtil.isEmptyString(Bod)) {            
            stArea.append("w01017-Rtd=").append(Bod).append(",w01017-Flag=N;");
        }
        if(!StringUtil.isEmptyString(NH3NH4)) {             
            stArea.append("w21003-Rtd=").append(NH3NH4).append(",w21003-Flag=N;");
        }
        if(!StringUtil.isEmptyString(KMnO4)) {            
            stArea.append("w01019-Rtd=").append(KMnO4).append(",w01019-Flag=N;");
        }
        if(!StringUtil.isEmptyString(PH)) {            
            stArea.append("w01001-Rtd=").append(PH).append(",w01001-Flag=N;");
        }
        if(!StringUtil.isEmptyString(DO)) {            
            stArea.append("w01009-Rtd=").append(DO).append(",w01009-Flag=N;");
        }
        if(!StringUtil.isEmptyString(Conductivity)) {
            stArea.append("w01014-Rtd=").append(Conductivity).append(",w01014-Flag=N;");
        }
        if(!StringUtil.isEmptyString(FTU)) {            
            stArea.append("a01021-Rtd=").append(FTU).append(",a01021-Flag=N;");
        }
        if(!StringUtil.isEmptyString(Temperature)) {            
            stArea.append("w01010-Rtd=").append(Temperature).append(",w01010-Flag=N;");
        }
        if(!StringUtil.isEmptyString(TP)) {
            stArea.append("w21011-Rtd=").append(TP).append(",w21011-Flag=N;");
        }
        if(!StringUtil.isEmptyString(TN)) {            
            stArea.append("w21001-Rtd=").append(TN).append(",w21001-Flag=N;");
        }
        if(!StringUtil.isEmptyString(CU)) {
            stArea.append("w20122-Rtd=").append(CU).append(",w20122-Flag=N;");
        }
        if(!StringUtil.isEmptyString(Zn)) {
            stArea.append("w20139-Rtd=").append(Zn).append(",w20139-Flag=N;");
        }
        if(!StringUtil.isEmptyString(F)) {
            stArea.append("w21017-Rtd=").append(F).append(",w21017-Flag=N;");
        }
        if(!StringUtil.isEmptyString(As)) {
            stArea.append("w20141-Rtd=").append(As).append(",w20141-Flag=N;");
        }
        if(!StringUtil.isEmptyString(Hg)) {
            stArea.append("w20142-Rtd=").append(Hg).append(",w20142-Flag=N;");
        }
        if(!StringUtil.isEmptyString(Cd)) {
            stArea.append("w20143-Rtd=").append(Cd).append(",w20143-Flag=N;");
        }
        if(!StringUtil.isEmptyString(Pb)) {
            stArea.append("w20144-Rtd=").append(Pb).append(",w20144-Flag=N;");
        }
        if(!StringUtil.isEmptyString(Cr6)) {
            stArea.append("w20117-Rtd=").append(Cr6).append(",w20117-Flag=N;");
        }
        if(!StringUtil.isEmptyString(Fe)) {
            stArea.append("w20125-Rtd=").append(Fe).append(",w20125-Flag=N;");
        }
        if(!StringUtil.isEmptyString(Cn)) {
            stArea.append("w21016-Rtd=").append(Cn).append(",w21016-Flag=N;");
        }
        if(!StringUtil.isEmptyString(ArOH)) {
            stArea.append("w23002-Rtd=").append(ArOH).append(",w23002-Flag=N;");
        }
        if(!StringUtil.isEmptyString(Oil)) {
            stArea.append("w22001-Rtd=").append(Oil).append(",w22001-Flag=N;");
        }
        if(!StringUtil.isEmptyString(anionics)) {
            stArea.append("w19002-Rtd=").append(anionics).append(",w19002-Flag=N;");
        }
        if(!StringUtil.isEmptyString(sulfide)) {
            stArea.append("w21019-Rtd=").append(sulfide).append(",w21019-Flag=N;");
        }
        if(!StringUtil.isEmptyString(NO3N)) {
            stArea.append("w2100721001-Rtd=").append(NO3N).append(",w2100721001-Flag=N");
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
        if(numStr.contains(".")) {
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
        Double value = Math.pow(10, 1);
        return 8 - (random.nextInt(value.intValue())) / 1.1f;
    }

    private Random random = new Random();
    private float getRandomFixedData() {
        return random.nextInt(12);
    }
    
    @Override
    public String getDeviceType() {
        return SURFACE_WATER_MONITOR_212;
    }
    
    @Override
    public int getDataType() {
        return TYPE_WATER_NA212;
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

