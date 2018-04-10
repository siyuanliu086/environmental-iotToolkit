package com.datamonitor.main;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.datamonitor.ConfigFile;
import com.datamonitor.message.DeviceData;
import com.datamonitor.sender.SenderClient;

public class SenderMain {

    public static void main(String[] args) {
        SenderMain sm = new SenderMain();
        sm.send();
    }
    
    public void send() {
        int index = 1;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        System.out.println("----"+sdf.format(new Date()));
        System.out.println("----START " + (index++) + " WAVE");
        //String[] deviceArr = new String[] {"NATIONAL212TEST001"};
        String[] deviceArr = new String[] {
                "DOHA20001",
                "DOHA20002",
                "DOHA20003",
                "DOHA20004",
                "DOHA20005",
                "DOHA20006",
        };
        //SenderMain senderMain = new SenderMain();

        int TIME_SLEEP = 5;//休眠5秒
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            
            @Override
            public void run() {
                DeviceData data = new DeviceData(ConfigFile.SOCKET_SERVER_IP, ConfigFile.SOCKET_SERVER_PORT);
                int i = 0;
                for(String deviceId : deviceArr) {
                    i ++;
                    data.setDeviceId(deviceId);
                    try {
                        SenderClient.startSocketClient(data);
                        //send(data);//这里发送了两次
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(TIME_SLEEP * 1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }, 1000,  600 * 1000);
    }
}
