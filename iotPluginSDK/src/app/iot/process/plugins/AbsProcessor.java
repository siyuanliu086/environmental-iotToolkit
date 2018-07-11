package app.iot.process.plugins;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;

/**
 * 解析器抽象父类，定义统一日志标准
 * @author      : Liu Siyuan
 * @Date        : 2018年7月10日 下午5:29:49
 * @version 1.0.0
 */
public abstract class AbsProcessor implements IProcessor {
    private java.util.logging.Logger logger = java.util.logging.Logger.getLogger(GetProcessName());
    protected String parseTime;
    
    /**
     * 定义统一日志标准
     * @author      : Liu Siyuan
     * @Date        : 2018年7月10日 下午5:29:49
     * @version 1.0.0
     */
    public AbsProcessor() {
        try {
            File logDir = new File("D:/run_pre/iot/process/plugin", GetProcessName() + "/");//创建文件对象参数：父目录,子目录
            logDir.mkdirs();//创建多级目录
            File logFile = new File(logDir.getAbsolutePath(), "process.log");//创建文件对象
            
            //写出日志文件,参数：路径,最大字节数,要使用的文件数,是否追加文件。
            //达到最大字节数后,会删掉原来所有记录,重新开始记录
            FileHandler fileHandler = new FileHandler(logFile.getAbsolutePath(), 1024 * 1024, 1, true);
            fileHandler.setLevel(Level.INFO); 
            fileHandler.setFormatter(new MyLogHander());
            logger.addHandler(fileHandler);
            
            parseTime = simpleDateFormat.format(new Date());
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
    
    class MyLogHander extends java.util.logging.Formatter { 
        @Override 
        public String format(java.util.logging.LogRecord record) { 
            return parseTime + "\t" + record.getLevel() + ":" + record.getMessage()+"\n"; 
        } 
    }
    
    protected void printInfoLog(String log) {
        logger.info(log); 
    }
    
    protected void printErrorLog(String log) {
        logger.warning(log); 
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
