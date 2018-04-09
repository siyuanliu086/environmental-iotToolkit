package com.datamonitor.main;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.datamonitor.ConfigFile;
import com.datamonitor.message.WaterData;
import com.datamonitor.sender.SenderClient;

public class SenderWaterMain {

    public static void main(String[] args) {
                int index = 1;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                System.out.println("----"+sdf.format(new Date()));
                System.out.println("----START " + (index++) + " WAVE");
                //String[] deviceArr = new String[] {"NATIONAL212TEST001"};
                String[] deviceArr = new String[] {
                        "huantai_device_0001",
                        "huantai_device_0002",
                        "huantai_device_0003",
                        "huantai_device_0004",
                        "huantai_device_0005",
                        "huantai_device_0006",
                        "huantai_device_0007",
                        "huantai_device_0008",
                        "huantai_device_0009",
                        "huantai_device_0010",
                        "huantai_device_0011",
                        "huantai_device_0012",
                        "huantai_device_0013",
                        "huantai_device_0014",
                        "huantai_device_0015",
                        "huantai_device_0016",
                        "huantai_device_0017",
                        "huantai_device_0021"
                };

                int TIME_SLEEP = 5;//休眠5秒
                Timer timer = new Timer();
                timer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        WaterData data = new WaterData(ConfigFile.SOCKET_SERVER_IP, ConfigFile.SOCKET_SERVER_PORT);
                        for(String deviceId : deviceArr) {
                            data.setDeviceInfo(deviceId);
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
                }, 1000, 600 * 1000);
    }
   
}
