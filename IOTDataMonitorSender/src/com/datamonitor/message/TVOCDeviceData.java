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
        DecimalFormat decimalFormat =  new DecimalFormat("#.##");
        float o2_content = 0.71f + getRandomFixedData() / 18.0f;
        float stack_gas_velocity = 6.38f + getRandomFixedData();
        float gas_tem = 147.12f + getRandomFixedData();
        float gas_rh = 8.000f + getRandomFixedData() / 10.0f;
        float gas_pa = 1.349f + getRandomFixedData();
        float waste_gas = 3600.0f + getRandomFixedData();
        float soot = 25.2100f + getRandomFixedData();
        float soot_zs = 63.6616f + getRandomTemData();
        float so2 = 9f + getRandomFixedData();
        float so2_zs = 51.0317f + getRandomFixedData();
        float nox = 50.5467f + getRandomFixedData();
        float nox_zs = 125.0510f + getRandomFixedData() / 2.0f;
        
        stArea.append("DataTime=" + dataTime + ";");
        stArea.append("S01-Rtd=").append(o2_content).append(",S01-Flag=N;");
        stArea.append("S02-Rtd=").append(stack_gas_velocity).append(",S02-Flag=N;");
        stArea.append("S03-Rtd=").append(gas_tem).append(",S03-Flag=N;");
        stArea.append("S05-Rtd=").append(gas_rh).append(",S05-Flag=N;");
        stArea.append("S08-Rtd=").append(gas_pa).append(",S08-Flag=N;");
        stArea.append("B02-Rtd=").append(waste_gas).append(",B02-Flag=N;");
        stArea.append("01-Rtd=").append(soot).append(",01-ZsRtd=").append(soot_zs).append(",01-Flag=N;");
        stArea.append("02-Rtd=").append(so2).append(",02-ZsRtd=").append(so2_zs).append(",02-Flag=N;");
        stArea.append("03-Rtd=").append(nox).append(",03-ZsRtd=").append(nox_zs).append(",03-Flag=N");
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
}