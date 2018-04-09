package com.datamonitor.sender;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.datamonitor.message.CustomMq;
import com.datamonitor.message.MessageQueue;

/**
 * 发送长连接
 * 
 * @author : Liu Siyuan
 * @Date : 2017年8月23日 上午11:33:56
 * @version 1.0.0
 */
public class SenderSocketLongLink implements Runnable {
    private String addr;
    private int port;
    private String deviceId;
    private String messageKey;
    private SimpleDateFormat sft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    private Socket socket = null;

    public SenderSocketLongLink(String addr, String port, String deviceId) {
        this.addr = addr;
        this.port = Integer.valueOf(port);
        this.deviceId = deviceId;
        this.messageKey = addr +  "_" + deviceId;
    }

    @Override
    public void run() {
        BufferedWriter out = null;
        InputStream inStr = null;
        try {
            socket = new Socket(this.addr, this.port);
            log("(" + addr + ")PORT:" + port + "\tSocket LINK SUCCESS！\t " + messageKey + " Sender-Socket");
//            while (true) {
                if (!CustomMq.empty(messageKey)) {
                    String time = sft.format(new Date());
                    log("------------------------" + time + "------------------------");
                    log("----START :" + messageKey + " Sender-Socket");
                    String message = CustomMq.peek(messageKey);
                    out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    log("----QUEUE :\n" + message);
                    out.write(message + "\r\n");
                    out.flush();
                    
                    //inStr = socket.getInputStream();
                    //BufferedReader in = new BufferedReader(new InputStreamReader(inStr));
                    log("----END :\t" + messageKey + " Sender-Socket");
                    
                    Thread.sleep(100);// 休眠
//                    readServerMessage(inStr, in);
                }
//                Thread.sleep(100);// 休眠
//            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                SenderClient.socketManages.remove(messageKey);
                socket.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void readServerMessage(InputStream inStr, BufferedReader br) {
        try {
            log("---- BACK ：" + messageKey + " Sender-Socket");
            String returnMessage = null;
            long startTime = (new Date()).getTime();
            
            while (true) {
                String result = null;
                if (inStr.available() > 0) {
                    result = br.readLine();
                    if (result != null && !result.equals("")) {
                        returnMessage = result;
                    }
                    break;
                }
                long nowTime = (new Date()).getTime();
                if (nowTime - startTime > 300) {
                    break;
                }
            }

            log("==Form Server:" + returnMessage + "\t ON " + addr + "_" + deviceId + " Sender-Socket\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void log(String info) {
        MessageQueue.push(info);
        System.out.println(info);
    }
}
