package com.datamonitor.receiver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.datamonitor.ConfigFile;
import com.datamonitor.message.MessageQueue;

public class ReceiverMain {
    // 缓冲线程池
    private ExecutorService pool;
    
    public void initReceiver() {
        pool = newFixedThreadPool(ConfigFile.THREAD_POOL_SIZE);
        //1、创建一个服务器端Socket，即ServerSocket，指定绑定的端口，并监听此端口
        try {
            ServerSocket serverSocket = new ServerSocket(Integer.valueOf(ConfigFile.SOCKET_SERVER_PORT));
            log("----------------------数据接收服务端启动------------------------");
            //2、调用accept()方法开始监听，等待客户端的连接
            Socket socket = null;
            while(true) {
                socket = serverSocket.accept(); 
                ReceiverThread receiverThread = new ReceiverThread(socket);
                pool.execute(receiverThread);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        ReceiverMain receiverMain = new ReceiverMain();
        receiverMain.initReceiver();
    }
    
    public static ExecutorService newFixedThreadPool(int corePoolSize) {  
        return new ThreadPoolExecutor(corePoolSize, Integer.MAX_VALUE, 2 * 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
    }
    
    private void log(String info) {
        MessageQueue.push(info);
        System.out.println(info);
    }
}
