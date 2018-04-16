package com.iotdatamonitor.utils;


public class CRC {
    /**
     * CRC校验（来源：生态物联网监测云平台数据传输协议（大气））
     * @author 		: Liu Siyuan
     * @Date 		: 2017年8月23日 下午3:47:02
     * @version 1.0.0
     */
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
    
    /**
     * 整形转16进制
     * @author      : Liu Siyuan
     * @Date        : 2017年8月23日 下午3:47:02
     * @version 1.0.0
     */
    public static final String getInteger2Hex(int value) {
        return Integer.toHexString(value).toUpperCase();
    }
    
    public static void main(String[] args) {
        System.out.println(getInteger2Hex(getCRC("ST=22;CN=2011;PW=123456;MN=781703664AM0001;CP=&&DataTime=20170823170249;CO-Rtd=2.1367521,CO-Flag=N;NO2-Rtd=0.85470086,NO2-Flag=N;SO2-Rtd=13.675214,SO2-Flag=N;O3-Rtd=21.794872,O3-Flag=N;NOIS-Rtd=14.529915,NOIS-Flag=N;PM25-Rtd=41.025642,PM25-Flag=N;PM10-Rtd=29.05983,PM10-Flag=N;TEM-Rtd=8.119658,TEM-Flag=N;RH-Rtd=31.623932,RH-Flag=N;WD-Rtd=2.1367521,WD-Flag=N;WS-Rtd=36.752136,WS-Flag=N;PA-Rtd=36.752136,PA-Flag=N;TSP-Rtd=38.46154,TSP-Flag=N&&")));
    }
}
