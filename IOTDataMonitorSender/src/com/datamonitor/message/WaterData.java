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

    private String dataTime;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

    public WaterData(String server, String port) {
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
    //##299ST=21;CN=2011;PW=;MN=100000000AM1975;CP=&&DataTime=20171220040009;PM10-Rtd=69,PM10-Flag=N;PM25-Rtd=28,PM25-Flag=N;SO2-Rtd=23,SO2-Flag=N;CO-Rtd=1.3,CO-Flag=N;O3-Rtd=41,O3-Flag=N;NO2-Rtd=28,NO2-Flag=N;TEM-Rtd=18.54,TEM-Flag=N;RH-Rtd=13.58,RH-Flag=N;WD-Rtd=315.0,WD-Flag=N;WS-Rtd=2.45,WS-Flag=N&&d042
    public String getMessage() {
        StringBuffer stArea = new StringBuffer("ST=");
        stArea.append(getDeviceType()).append(";");
        stArea.append("CN=2011;PW=123456;MN="+deviceId+";");
        stArea.append("CP=&&");
        DecimalFormat decimalFormat =  new DecimalFormat("#.##");
        DecimalFormat df5 =  new DecimalFormat("#.#####");
        float Cod = 2.2f + getRandomFixedData() / 12.0f * 15;
        float Bod = getRandomFixedData() / 3.1f;

        float NH3NH4 = getRandomFixedData() / 10.0f;
        float KMnO4 = getRandomFixedData();
        float PH = 6.8f + getRandomFixedData() / 10.0f;
        float DO = 2.5f + getRandomFixedData();
        float Conductivity = 64f + getRandomFixedData();
        float FTU = 97f + getRandomFixedData();
        float Temperature = getRandomTemData();
        float TP =  getRandomFixedData() / 121.0f;// 保证一级
        float TN = 0.2f + getRandomFixedData() / 240.0f;// 保证二级
        float CU = getRandomFixedData()/ 1200.0f - 0.001f;
        float Zn = getRandomFixedData() / 12.0f;
        float F = getRandomFixedData() / 12.0f;
        float As = getRandomFixedData() / 240.0f;
        float Hg = getRandomPaData()/ 24000.0f;
        float Cd = getRandomFixedData() / 2400.0f;
        float Pb = getRandomFixedData() / 240.0f;
        float Cr6 = getRandomFixedData() / 240f;
        float Fe = getRandomFixedData();
        float Cn = getRandomFixedData() / 250.0f;// 保证一级
        float ArOH = getRandomFixedData()/ 1000.0f;// 保证一级
        float Oil = 0.03f + getRandomFixedData() / 250.0f;// 保证一级
        float anionics = getRandomFixedData()/ 80.0f ;// 保证一级
        float sulfide = getRandomFixedData()/ 240.0f;// 保证一级
        float NO3N = getRandomFixedData();

        //为了制造超标
        int index = random.nextInt(10);
        if(index < 4) {
            Cod = Cod * 100;
            As = As + 100;
            Cr6 = -Cr6;
            KMnO4 = -KMnO4;
            NO3N = NO3N * index;
        }
        
        
        stArea.append("DataTime=" + dataTime + ";");
        stArea.append("w01018-Rtd=" + decimalFormat.format(Cod)).append(",w01018-Flag=N;");
        stArea.append("w01017-Rtd=" + decimalFormat.format(Bod)).append(",w01017-Flag=N;");
        stArea.append("w21003-Rtd=" + decimalFormat.format(NH3NH4)).append(",w21003-Flag=N;");
        stArea.append("w01019-Rtd=" + decimalFormat.format(KMnO4)).append(",w01019-Flag=N;");
        stArea.append("w01001-Rtd=" + decimalFormat.format(PH)).append(",w01001-Flag=N;");
        stArea.append("w01009-Rtd=" + decimalFormat.format(DO)).append(",w01009-Flag=N;");
        stArea.append("w01014-Rtd=" + decimalFormat.format(Conductivity)).append(",w01014-Flag=N;");
        stArea.append("a01021-Rtd=" + decimalFormat.format(FTU)).append(",a01021-Flag=N;");
        stArea.append("w01010-Rtd=" + decimalFormat.format(Temperature)).append(",w01010-Flag=N;");
        stArea.append("w21011-Rtd=" + df5.format(TP)).append(",w21011-Flag=N;");
        stArea.append("w21001-Rtd=" + df5.format(TN)).append(",w21001-Flag=N;");
        stArea.append("w20122-Rtd=" + df5.format(CU)).append(",w20122-Flag=N;");
        stArea.append("w20139-Rtd=" + decimalFormat.format(Zn)).append(",w20139-Flag=N;");
        stArea.append("w21017-Rtd=" + decimalFormat.format(F)).append(",w21017-Flag=N;");
        stArea.append("w20141-Rtd=" + decimalFormat.format(As)).append(",w20141-Flag=N;");
        stArea.append("w20142-Rtd=" + df5.format(Hg)).append(",w20142-Flag=N;");
        stArea.append("w20143-Rtd=" + df5.format(Cd)).append(",w20143-Flag=N;");
        stArea.append("w20144-Rtd=" + df5.format(Pb)).append(",w20144-Flag=N;");
        stArea.append("w20117-Rtd=" + decimalFormat.format(Cr6)).append(",w20117-Flag=N;");
        stArea.append("w20125-Rtd=" + decimalFormat.format(Fe)).append(",w20125-Flag=N;");
        stArea.append("w21016-Rtd=" + df5.format(Cn)).append(",w21016-Flag=N;");
        stArea.append("w23002-Rtd=" + df5.format(ArOH)).append(",w23002-Flag=N;");
        stArea.append("w22001-Rtd=" + decimalFormat.format(Oil)).append(",w22001-Flag=N;");
        stArea.append("w19002-Rtd=" + decimalFormat.format(anionics)).append(",w19002-Flag=N;");
        stArea.append("w21019-Rtd=" + decimalFormat.format(sulfide)).append(",w21019-Flag=N;");
        stArea.append("w2100721001-Rtd=" + decimalFormat.format(NO3N)).append(",w2100721001-Flag=N");
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
        Double value = Math.pow(10, 1);
        return 8 - (random.nextInt(value.intValue())) / 1.1f;
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
}

