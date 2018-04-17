package com.iotdatamonitor.message;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import com.alibaba.fastjson.JSONObject;
import com.iotdatamonitor.utils.StringUtil;

public class PositionData implements IDeviceData{
    private String mn;
    
    private String server;
    private String port;
    
    private String message;
    
    private boolean fullFactor;
    
    public PositionData(String server, String port) {
        this.server = server;
        this.port = port;
    }
    
    public void setDeviceId(String mn) {
        this.mn = mn;
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
        return mn;
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
        stArea.append("CN=2011;PW=123456;MN="+mn+";");
        stArea.append("CP=&&");
        
        String TYPE = "";
        String REGION = "";
        String LONGITUDE = "";
        String LATITUDE = "";
        String ADDRESS = "";
        String SPEED = "";
        String DIRECTION = "";
        String HEIGHT = "";
        String SATELLITE = "";
        if(factorStyle != null && factorStyle.containsKey("type")) {
            TYPE = String.valueOf(factorStyle.getIntValue("type"));
        } else if(fullFactor){
            TYPE = "1";
        }
        if(factorStyle != null && factorStyle.containsKey("region")) {
            REGION = factorStyle.getString("region");
        } else if(fullFactor) {
            REGION = "110000";
        }
        if(factorStyle != null && factorStyle.containsKey("longitude")) {
            float longitude_ = factorStyle.getFloatValue("longitude");
            int longitude_err = factorStyle.containsKey("longitude_err") ? factorStyle.getInteger("longitude_err") : 0;
            float seendLon = longitude_;
            LONGITUDE = getRateLonLatValue(random, seendLon, longitude_err == 1);// 北京
        } else if(fullFactor){
            float seendLon = 116.291765f;
            LONGITUDE = getRateLonLatValue(random, seendLon, false);// 北京
        }
        if(factorStyle != null && factorStyle.containsKey("latitude")) {
            float latitude_ = factorStyle.getFloatValue("latitude");
            int latitude_err = factorStyle.containsKey("latitude_err") ? factorStyle.getInteger("latitude_err") : 0;
            LATITUDE = getRateLonLatValue(random, latitude_, latitude_err == 1);
        } else if(fullFactor) {
            float seed = 40.051179f;
            LATITUDE = getRateLonLatValue(random, seed, false);
        }
        if(factorStyle != null && factorStyle.containsKey("address")) {
            float address_ = factorStyle.getFloatValue("address");
            int address_err = factorStyle.containsKey("address_err") ? factorStyle.getInteger("address_err") : 0;
            ADDRESS = String.valueOf(address_);//getRateValue(random, address_, );
            if(address_err == 1) {
                ADDRESS = null;
            }
        } else if(fullFactor){
            ADDRESS = "北京市海淀区";
        }
        // 中文URL编码
        try {
            ADDRESS = URLEncoder.encode(ADDRESS, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if(factorStyle != null && factorStyle.containsKey("speed")) {
            float speed_ = factorStyle.getFloatValue("speed");
            int speed_err = factorStyle.containsKey("speed_err") ? factorStyle.getInteger("speed_err") : 0;
            SPEED = getRateValue(random, speed_, speed_err == 1);
        } else if(fullFactor){
            SPEED = String.valueOf(4f + getRandomFixedData() / 4);
        }
        if(factorStyle != null && factorStyle.containsKey("direction")) {
            float direction_ = factorStyle.getFloatValue("direction");
            int direction_err = factorStyle.containsKey("direction_err") ? factorStyle.getInteger("direction_err") : 0;
            DIRECTION = getRateValue(random, direction_, direction_err == 1);
        } else if(fullFactor){
            DIRECTION = String.valueOf(180f + getRandomFixedData() * 2);
        }
        if(factorStyle != null && factorStyle.containsKey("height")) {
            float height_ = factorStyle.getFloatValue("height");
            int height_err = factorStyle.containsKey("height_err") ? factorStyle.getInteger("height_err") : 0;
            HEIGHT = getRateValue(random, height_, height_err == 1);
        } else if(fullFactor){
            HEIGHT = String.valueOf(getRandomHeightData());
        }
        if(factorStyle != null && factorStyle.containsKey("satellite")) {
            float satellite_ = factorStyle.getFloatValue("satellite");
            int satellite_err = factorStyle.containsKey("satellite_err") ? factorStyle.getInteger("satellite_err") : 0;
            SATELLITE = String.valueOf(satellite_ + random.nextInt(3));
            if(satellite_err == 1) {
                SATELLITE = "-" + SATELLITE;
            }
        } else if(fullFactor){
            SATELLITE = String.valueOf(4);
        }
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String dataTime = sdf.format(new Date());
        stArea.append("DataTime=" + dataTime + ";");
        if(!StringUtil.isEmptyString(TYPE)) {            
            stArea.append("TYPE-Rtd=").append(TYPE).append(",TYPE-Flag=N;");
        }
        if(!StringUtil.isEmptyString(REGION)) {
            stArea.append("REGION-Rtd=").append(REGION);
        }
        if(!StringUtil.isEmptyString(ADDRESS)) {            
            stArea.append(",ADDRESS-Rtd=").append(ADDRESS);
        }
        if("1".equals(TYPE)) {
            stArea.append(",Modile-Flag=N;");// 以上是手机收集数据
        } else {
            stArea.append(",Modile-Flag=F;");
        }
        if(!StringUtil.isEmptyString(LONGITUDE)) {
            stArea.append("LONGITUDE-Rtd=").append(LONGITUDE);
        }
        if(!StringUtil.isEmptyString(LATITUDE)) {
            stArea.append(",LATITUDE-Rtd=").append(LATITUDE);
        }
        if(!StringUtil.isEmptyString(SPEED)) {
            stArea.append(",SPEED-Rtd=").append(SPEED);
        }
        if(!StringUtil.isEmptyString(DIRECTION)) {
            stArea.append(",DIRECTION-Rtd=").append(DIRECTION);
        }
        if(!StringUtil.isEmptyString(HEIGHT)) {
            stArea.append(",HEIGHT-Rtd=").append(HEIGHT);
        }
        if(!StringUtil.isEmptyString(SATELLITE)) {
            stArea.append(",SATELLITE-Rtd=").append(SATELLITE);
        }
        stArea.append(",GPS-Flag=N");// 以上是盒子GPS收集数据
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
     * @description : 将数值d在90.0%-110.0%之间波动(保持小数位数)
     * @author      : Liu Siyuan
     * @Date        : 2018年4月10日 下午4:03:49
     * @version 1.0.0
     * @param isError 
     */
    public static String getRateLonLatValue(Random random, float lonlat, boolean isError) {
        float rate = (random.nextInt(30) + 985) / 1000.0f;
        
        // isError == true 随机制造一些异常情况（0值，负值，超大值）
        if(isError) {
            if(rate < 0.995) {
                //0值
                rate = 0;//变0值
            } else if(rate < 1.05) {
                rate *= -1;//变负值
            } else {
                rate *= 123;//常规值得123倍
            }
        }
        
        int decimal = getDecimal(lonlat);
        return String.format("%."+decimal+"f", lonlat * rate);
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

    private float getRandomHeightData() {
        return 160 + random.nextInt(60);
    }

    private Random random = new Random();
    private float getRandomFixedData() {
        return random.nextInt(12);
    }

    @Override
    public String getDeviceType() {
        return POSITION_MONITOR_212;
    }

    @Override
    public int getDataType() {
        return TYPE_POS_NA212;
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

