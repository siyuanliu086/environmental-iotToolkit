package com.datamonitor.main;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.datamonitor.ConfigFile;
import com.datamonitor.message.TVOCDeviceData;
import com.datamonitor.sender.SenderClient;

public class TVOCSenderMain {

    public static void main(String[] args) {
                int index = 1;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                System.out.println("----"+sdf.format(new Date()));
                System.out.println("----START " + (index++) + " WAVE");
                //String[] deviceArr = new String[] {"NATIONAL212TEST001"};
                String[] deviceArr = new String[] {
                        "NATIONAL212TEST001",
//                        "JSNT0000002",
//                        "JSNT0000003",
//                        "JSNT0000004",
//                        "JSNT0000005",
//                        "JSNT0000006",
//                        "JSNT0000007",
//                        "JSNT0000008",
//                        "JSNT0000009",
//                        "JSNT0000010",
//                        "JSNT0000011",
//                        "JSNT0000012",
//                        "JSNT0000013",
//                        "JSNT0000014",
//                        "JSNT0000015",
//                        "JSNT0000016",
//                        "JSNT0000017",
//                        "JSNT0000018"
                };

                int TIME_SLEEP = 5;//休眠5秒
                Timer timer = new Timer();
                timer.scheduleAtFixedRate(new TimerTask() {
                    
                    @Override
                    public void run() {
                        TVOCDeviceData data = new TVOCDeviceData(ConfigFile.SOCKET_SERVER_IP, ConfigFile.SOCKET_SERVER_PORT);
                        for(String deviceId : deviceArr) {
                            data.setDeviceId(deviceId);
                            try {
                                SenderClient.startSocketClient(data);
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
                }, 1000,  60 * 1000);
    }
}
