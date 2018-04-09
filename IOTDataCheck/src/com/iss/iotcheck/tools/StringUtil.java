package com.iss.iotcheck.tools;

public class StringUtil {
    /**
     * @className   :字符串转换数组
     * @author      :Wang Dandan
     */
    public static String[] convertStrToArray(String str){  
        String[] strArray = null;  
        //拆分字符为"," ,然后把结果交给数组strArray
        strArray = str.split(","); 
        return strArray;
    }   
    
    /**
     * 判断字符串非空
     * @return		: true 空串， false 非空
     * @author 		: Liu Siyuan
     * @Date 		: 2017年7月31日 下午2:18:49
     * @version 1.0.0
     */
    public static final boolean isEmptyString(String str) {
        return null == str || "null".equals(str) || "".equals(str);
    }
    
    public static void main(String[] args) {
        System.out.println(Math.pow(10, 2));
    }
    
    /**
     * CRC校验（来源：生态物联网监测云平台数据传输协议（大气））
     * @author      : Liu Siyuan
     * @Date        : 2017年8月23日 下午3:47:02
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
}
