package app.iot.process.plugins;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;

import app.iot.process.database.entity.IotData;

/**
 * 解析器抽象父类
 * @author      : Liu Siyuan
 * @Date        : 2018年7月10日 下午5:29:49
 * @version 1.0.0
 */
public abstract class AbsProcessor implements IProcessor {
    /**协议指定数据时间格式*/
    protected SimpleDateFormat dataDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    protected SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    protected SimpleDateFormat hDateFormat = new SimpleDateFormat("yyyyMMddHH");
    
    /**
     * 定义统一日志标准
     * @author      : Liu Siyuan
     * @Date        : 2018年7月10日 下午5:29:49
     * @version 1.0.0
     */
    public AbsProcessor() {}
    
    private String logPath;
    private boolean isWriteLog;
    
    public void setWriteLog(boolean isWriteLog, String logPath) {
        this.isWriteLog = isWriteLog; 
        this.logPath = logPath;
    }
    
    // 普通日志，一般日志记录
    protected void writeDataLog(String data) {
        if(isWriteLog) {
            String filename = logPath + "/" + hDateFormat.format(new Date()) + ".txt";
            String filedata = simpleDateFormat.format(new Date()) + " : " + data + "\r\n";
            try {
                FileUtils.write(new File(filename), filedata, "UTF-8", true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    @Override
    public List<? extends IotData> process(String data) {
        return null;
    }
    
    @Override
    public List<String> process2Json(String data) {
        return null; 
    }
    
    protected String exchangeName;
    @Override
    public void setMQExchange(String exchangeName) {
        this.exchangeName = exchangeName;
    }
    
    
    /**
     * CRC 校验方法
     * @author      : Liu Siyuan
     * @Date        : 2018年7月10日 下午3:18:29
     * @version 1.0.0
     */
    protected static int GetCRC(String data212) {
        int CRC = 0xFFFF;
        int Num = 0xA001;
        int inum = 0;
        byte[] sb = data212.getBytes();
        for (int j = 0; j < sb.length; j++) {
            inum = sb[j];// data212[j];
            CRC = (CRC >> 8) & 0x00FF;
            CRC ^= inum;

            for (int k = 0; k < 8; k++) {
                int flag = CRC % 2;
                CRC = CRC >> 1;

                if (flag == 1) {
                    CRC = CRC ^ Num;
                }
            }
        }
        return CRC;
    }
}
