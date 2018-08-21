package com.iotdatamonitor.ui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.alibaba.fastjson.JSONObject;
import com.iotdatamonitor.message.DeviceData;
import com.iotdatamonitor.message.DeviceQiXiang;
import com.iotdatamonitor.message.ElectMeterDeviceData;
import com.iotdatamonitor.message.IDeviceData;
import com.iotdatamonitor.message.NationalDeviceData;
import com.iotdatamonitor.message.TVOCDeviceData;
import com.iotdatamonitor.message.WaterData;
import com.iotdatamonitor.sender.SenderClient;
import com.iotdatamonitor.utils.FileHelper;
import com.iotdatamonitor.utils.StringUtil;

public class Controller {
    public static final String TITLE = "软通智慧环保IOT数据模拟发送工具";
    
    private static final int TIME_SLEEP = 5;//休眠5秒
    
    private String title = TITLE;
    private int type;//发送类型
    private String[] deviceIdArr;// 设备编号
    private int sendCycle = 10;// 默认10分钟
    private int autoFullFactor = 1;//默认补全全部因子
    private JSONObject factorStyleJO;//因子规范 
    
    private static int sleepCount = 0;
    
    // 循环主线程
    private Timer mainTimer;
    
    // 设备发送器
    private List<IDeviceData> deviceList = new ArrayList<>();

    private boolean isRun;
    private Controller() {};
    public static Controller instance;
    
    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }
    
    public void init(int type, String configFilePath, String server, String port) {
        this.type = type;
        try {
            parseConfigFile(configFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        deviceList.clear();
        deviceList.add((IDeviceData) (new DeviceData(server, port)));
        deviceList.add((IDeviceData) (new NationalDeviceData(server, port)));
        deviceList.add((IDeviceData) (new TVOCDeviceData(server, port)));
        deviceList.add((IDeviceData) (new WaterData(server, port)));
        deviceList.add((IDeviceData) (new DeviceQiXiang(server, port)));
        deviceList.add((IDeviceData) (new ElectMeterDeviceData(server, port)));
    }
    
    /**
     * @description : 开始发送（启动线程）
     * @author      : Liu Siyuan
     * @Date        : 2018年4月10日 下午3:02:45
     * @version 1.0.0
     */
    public void start() {
        isRun = true;
        startSender();
    }
    
    /**
     * @description : 停止发送（各线程）
     * @author      : Liu Siyuan
     * @Date        : 2018年4月10日 下午3:02:45
     * @version 1.0.0
     */
    public void stop() {
        isRun = false;
        if(mainTimer != null) {
            mainTimer.cancel();
            mainTimer = null;
        }
    }
    

    public void reset() {
        deviceList.clear();
        deviceIdArr = null;
        stop();
    }
    
    /**
     * @description : 开始发送
     * @author 		: Liu Siyuan
     * @Date 		: 2018年4月10日 下午3:02:45
     * @version 1.0.0
     */
    private void startSender() {
        if(deviceIdArr.length < 20) {
            sleepCount = 1;
        } else if(deviceIdArr.length < 100) {
            sleepCount = 20;
        } else if(deviceIdArr.length < 1000) {
            sleepCount = 50;
        } else if(deviceIdArr.length < 2000) {
            sleepCount = 100;
        } else if(deviceIdArr.length < 3000) {
            sleepCount = 150;
        } else if(deviceIdArr.length < 5000) {
            sleepCount = 250;
        } else if(deviceIdArr.length < 7000) {
            sleepCount = 350;
        } else {
            sleepCount = 500;
        }
        
        mainTimer = new Timer();
        mainTimer.scheduleAtFixedRate(new TimerTask() {
            
            @Override
            public void run() {
                for(int i = 0; i < deviceList.size(); i ++) {
                    if(deviceList.size() > i) {// 重置时会处理deviceList
                        IDeviceData deviceData = deviceList.get(i);
                        if(deviceData.getDataType() == type) {
                            deviceSend(deviceData);
                        }
                    }
                    continue;
                }
            }
        }, 1000,  sendCycle * 60 * 1000);//发送周期，可配置
    }
    
    /**
     * @description : 所有设备号发送一次数据包
     * @author 		: Liu Siyuan
     * @Date 		: 2018年4月10日 下午3:01:38
     * @version 1.0.0
     */
    private void deviceSend(IDeviceData deviceData) {
        int i = 0;
        for(String deviceId : deviceIdArr) {//发送设备号（可配置）
            if(!isRun) {
                break;
            }
            deviceData.setDeviceId(deviceId);
            deviceData.setAutoFullFactor(autoFullFactor == 1);
            deviceData.setFactorStyle(factorStyleJO);
            try {
                String mess = SenderClient.startSocketClient(deviceData);
                callbackPrint(deviceId, mess);
            } catch (Exception e) {
                e.printStackTrace();
            }
            i ++;
            if(i % sleepCount == 0) {
                try {
                    Thread.sleep(200);// 每10条，休眠30ms
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    /**
     * @description : 解析配置文件
     * @author 		: Liu Siyuan
     * @Date 		: 2018年4月10日 下午2:12:24
     * @version 1.0.0
     */
    private void parseConfigFile(String path) throws IOException {
        File file = new File(path);
        if(file.isFile() && file.length() > 10) {
            String config = FileHelper.readTxt(path, "UTF-8");
            JSONObject configJO = JSONObject.parseObject(config);
            // 设置标题
            if(configJO.containsKey("title")) {
                title = configJO.getString("title");
            }
            callbackTitle(title);
            
            // 周期
            if(configJO.containsKey("cycle")) {
                try {
                    sendCycle = configJO.getIntValue("cycle");
                } catch (Exception e) {
                    e.printStackTrace();
                    sendCycle = 10;
                }
            }
            
            // 补全全部因子 auto_full_factor 
            if(configJO.containsKey("auto_full_factor")) {
                try {
                    autoFullFactor = configJO.getIntValue("auto_full_factor");
                } catch (Exception e) {
                    e.printStackTrace();
                    autoFullFactor = 1;
                }
            }
            
            // 发送因子，配置
            if(configJO.containsKey("factorList")) {                
                factorStyleJO = configJO.getJSONObject("factorList");
            }
            
            // 获取设备号
            String deviceIds = configJO.getString("deviceIds");
            if(StringUtil.isEmptyString(deviceIds)) {
                callbackPrint("ERROR!", "配置文件异常：未定义设备编号！");
                deviceIdArr = new String[] {};
            } else {                
                deviceIdArr = deviceIds.split(",");
            }
        } else {
            callbackPrint("ERROR!", "文件异常！");
        }
    }
    
    public String getTitle() {
        return title;
    }
    
    private IControllerCallback callback;
    public interface IControllerCallback {
        void onMessage(String deviceId, String mess);
        void updateTitle(String title);
    }
    public void setControllerCallback(IControllerCallback callback) {
        this.callback = callback;
    }
    private void callbackPrint(String deviceId, String mess) {
        if(callback != null) {
            callback.onMessage(deviceId, mess);
        }
    }
    private void callbackTitle(String title) {
        if(callback != null) {
            callback.updateTitle(title);
        }
    }

    public static void main(String[] args) {
        int sleepCount = 350;
        for(int i = 0; i < 10000; i++) {
            if(i % sleepCount == 0) {
                System.out.println(i);
            }
        }
    }
    
}
