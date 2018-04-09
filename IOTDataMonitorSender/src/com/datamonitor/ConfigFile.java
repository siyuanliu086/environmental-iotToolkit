package com.datamonitor;

import java.util.ArrayList;
import java.util.List;

public class ConfigFile {
    public static final String SOCKET_SERVER_CONFIG = "";
//    public static final String SOCKET_SERVER_IP = "127.0.0.1";//测试服务器
    public static final String SOCKET_SERVER_IP = "117.78.42.36";
    public static final String SOCKET_SERVER_PORT = "8095";//1024-65535的某个端口
    
    // Thread Pool Size
    public static final int THREAD_POOL_SIZE = 12;
    
    public static final String[][] clientBean = new String[][] {
        {"127.0.0.1", "12888"}
    };
    
    public static List<String> messageQueue = new ArrayList<String>(64);
}
