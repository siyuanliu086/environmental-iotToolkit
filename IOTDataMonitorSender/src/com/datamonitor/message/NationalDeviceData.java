package com.datamonitor.message;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import com.datamonitor.utils.StringUtil;

/**
 * 国标数据模拟器
 * @author Liu Siyuan
 *
    |编码 |    国标212|   中文名称 
    |CO  |    a21005 |   一氧化碳
    |NO2 |    a21004 |   二氧化氮
    |SO2 |    a21026 |   二氧化硫 
    |O3  |    a21030 |   臭氧
    |NOIS|    LA     |   噪声
    |PM25|    a34004 |   PM2.5
    |PM10|    a34002 |   PM10 
    |TEM |    a01001 |   温度
    |RH  |    a01002 |   湿度
    |WD  |    a01008 |   风向
    |WS  |    a01007 |   风速
    |PA  |    a01006 |   气压
    |TSP |    a34001 |   扬尘
 *
 */
public class NationalDeviceData implements IDeviceData {
    private String deviceId;
    
    private String server;
    private String port;
    
    private String message;
    
    private String timeQN;
    private String dataTime;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    
    public NationalDeviceData(String server, String port) {
        this.server = server;
        this.port = port;
        timeQN = sdf.format(new Date());
        dataTime = timeQN.substring(0, timeQN.length() - 3);
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
    //##0481QN=20160801085000001;ST=22;CN=2011;PW=123456;MN=010000A8900016F000169DC0;Flag=7;CP=&&DataTime=20160801084000;a21005-Rtd=1.1,a21005-Flag=N;a21004-Rtd=112,a21004-Flag=N;a21026-Rtd=58,a21026-Flag=N;a21030-Rtd=64,a21030-Flag=N;LA-td=50.1,LA-Flag=N;a34004-Rtd=207,a34004-Flag=N;a34002-Rtd=295,a34002-Flag=N;a01001-Rtd=12.6,a01001-Flag=N;a01002-Rtd=32,a01002-Flag=N;a01006-Rtd=101.02,a01006-Flag=N;a01007-Rtd=2.1,a01007-Flag=N;a01008-Rtd=120,a01008-Flag=N;a34001-Rtd=217,a34001-Flag=N;&&7580
    public String getMessage() {
        StringBuffer stArea = new StringBuffer("");
        stArea.append("QN=").append(timeQN).append(";");
        stArea.append("ST=").append(getDeviceType()).append(";");
        stArea.append("CN=2011;PW=123456;MN="+deviceId+";");
        stArea.append("CP=&&");
        DecimalFormat decimalFormat =  new DecimalFormat("#.##");
        float CO = 2.21f + getRandomFixedData() / 10.0f;
        float NO2 = 64f + getRandomFixedData();
        float SO2 = 34f + getRandomFixedData();
        float O3 = 7f + getRandomFixedData() / 10.0f;
        float NOIS = getRandomFixedData();
        float PM25 = 161f + getRandomFixedData();
        float PM10 = 251f + getRandomFixedData();
        float TEM = getRandomTemData();
        float RH = getRandomFixedData();
        float WD = getRandomFixedData();
        float WS = getRandomFixedData();
        float PA = getRandomPaData();
        float TSP = getRandomFixedData();
        stArea.append("DataTime=" + dataTime + ";");
        stArea.append("a21005-Rtd=" + decimalFormat.format(CO)).append(",a21005-Flag=N;");
        stArea.append("a21004-Rtd=" + decimalFormat.format(NO2)).append(",a21004-Flag=N;");
        stArea.append("a21026-Rtd=" + decimalFormat.format(SO2)).append(",a21026-Flag=N;");
        stArea.append("a21030-Rtd=" + decimalFormat.format(O3)).append(",a21030-Flag=N;");
        stArea.append("LA-Rtd=" + decimalFormat.format(NOIS)).append(",LA-Flag=N;");
        stArea.append("a34004-Rtd=" + decimalFormat.format(PM25)).append(",a34004-Flag=N;");
        stArea.append("a34002-Rtd=" + decimalFormat.format(PM10)).append(",a34002-Flag=N;");
        stArea.append("a01001-Rtd=" + decimalFormat.format(TEM)).append(",a01001-Flag=N;");
        stArea.append("a01002-Rtd=" + decimalFormat.format(RH)).append(",a01002-Flag=N;");
        stArea.append("a01008-Rtd=" + decimalFormat.format(WD)).append(",a01008-Flag=N;");
        stArea.append("a01007-Rtd=" + decimalFormat.format(WS)).append(",a01007-Flag=N;");
        stArea.append("a01006-Rtd=" + decimalFormat.format(PA)).append(",a01006-Flag=N;");
        stArea.append("a34001-Rtd=" + decimalFormat.format(TSP)).append(",a34001-Flag=N");
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
        return 10 - (random.nextInt(value.intValue())) / 1.1f;
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
    
    public static void main(String[] args) {
//        String info = "ST=22;CN=2011;PW=;MN=100000000AM1976;CP=&&DataTime=20180111092009;PM10-Rtd=68,PM10-Flag=N;PM25-Rtd=31,PM25-Flag=N;SO2-Rtd=21,SO2-Flag=N;CO-Rtd=0.9,CO-Flag=N;O3-Rtd=52,O3-Flag=N;NO2-Rtd=15,NO2-Flag=N;TEM-Rtd=14.28,TEM-Flag=N;RH-Rtd=8.06,RH-Flag=N;WD-Rtd=0.0,WD-Flag=N;WS-Rtd=4.4,WS-Flag=N&&";
        //##0251ST=22;CN=2011;PW=123456;MN=JH0201712SD8763;CP=&&DataTime=20180115031850;PM25-Rtd=83.8,PM25-Flag=N;PM10-Rtd=138.7,PM10-Flag=N;TSP-Rtd=209.0,TSP-Flag=N;TEM-Rtd=10,TEM-Flag=N;RH-Rtd=0,RH-Flag=N;WS-Rtd=0,WS-Flag=N;WD-Rtd=0,WD-Flag=N;NOIS-Rtd=45.2,NOIS=N&&4037
        //##0249ST=22;CN=2011;PW=123456;MN=JH0201712SD8763;CP=&&DataTime=20180114002828;PM25-Rtd=15.4,PM25-Flag=N;PM10-Rtd=31.7,PM10-Flag=N;TSP-Rtd=75.0,TSP-Flag=N;TEM-Rtd=10,TEM-Flag=N;RH-Rtd=0,RH-Flag=N;WS-Rtd=0,WS-Flag=N;WD-Rtd=0,WD-Flag=N;NOIS-Rtd=46.3,NOIS=N&&4045
        // ##0420QN=20160801085000001;ST=22;CN=2011;PW=123456;MN=010000A8900016F000169DC0;Flag=7;CP=&&DataTime=20160801084000;a21005-Rtd=1.1,a21005-Flag=N;a21004-Rtd=112,a21004-Flag=N;a21026-Rtd=58,a21026-Flag=N;LA-td=50.1,LA-Flag=N;a34004-Rtd=207,a34004-Flag=N;a34002-Rtd=295,a34002-Flag=N;a01001-Rtd=12.6,a01001-Flag=N;a01002-Rtd=32,a01002-Flag=N;a01006-Rtd=101.02,a01006-Flag=N;a01007-Rtd=2.1,a01007-Flag=N;a01008-Rtd=120,a01008-Flag=N;a34001-Rtd=217,a34001-Flag=N;&&
        //String info = "ST=22;CN=2011;PW=123456;MN=JH0201712SD8763;CP=&&DataTime=20180114002828;PM25-Rtd=15.4,PM25-Flag=N;PM10-Rtd=31.7,PM10-Flag=N;TSP-Rtd=75.0,TSP-Flag=N;TEM-Rtd=10,TEM-Flag=N;RH-Rtd=0,RH-Flag=N;WS-Rtd=0,WS-Flag=N;WD-Rtd=0,WD-Flag=N;NOIS-Rtd=46.3,NOIS=N&&";
//        String info = "QN=20160801085000001;ST=22;CN=2011;PW=123456;MN=010000A8900016F000169DC0;Flag=7;CP=&&DataTime=20160801084000;a21005-Rtd=1.1,a21005-Flag=N;a21004-Rtd=112,a21004-Flag=N;a21026-Rtd=58,a21026-Flag=N;a21030-Rtd=64,a21030-Flag=N;LA-Rtd=50.1,LA-Flag=N;a34004-Rtd=207,a34004-Flag=N;a34002-Rtd=295,a34002-Flag=N;a01001-Rtd=12.6,a01001-Flag=N;a01002-Rtd=32,a01002-Flag=N;a01006-Rtd=101.02,a01006-Flag=N;a01007-Rtd=2.1,a01007-Flag=N;a01008-Rtd=120,a01008-Flag=N;a34001-Rtd=217,a34001-Flag=N;&&";
//        System.out.println(info.length());
//        System.out.println(Integer.toHexString(StringUtil.getCRC(info)));
      
        NationalDeviceData dv = new NationalDeviceData("", "");
        System.out.println(dv.getMessage());
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
        return AIR_MONITOR_MONITOR_212;
    }
}


































