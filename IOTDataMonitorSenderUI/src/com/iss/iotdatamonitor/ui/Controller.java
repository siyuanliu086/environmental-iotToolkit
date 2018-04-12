package com.iss.iotdatamonitor.ui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.alibaba.fastjson.JSONObject;
import com.datamonitor.message.DeviceData;
import com.datamonitor.message.IDeviceData;
import com.datamonitor.message.NationalDeviceData;
import com.datamonitor.message.TVOCDeviceData;
import com.datamonitor.message.WaterData;
import com.datamonitor.sender.SenderClient;
import com.iss.iotdatamonitor.tools.FileHelper;

public class Controller {
    public static final String TITLE = "软通智慧环保IOT数据模拟发送工具";
    
    private static final int TIME_SLEEP = 5;//休眠5秒
    
    private String title;
    private int type;//发送类型
    private String[] deviceIdArr;// 设备编号
    private int sendCycle = 10;// 默认10分钟
    private int autoFullFactor = 0;//默认补全全部因子
    private JSONObject factorStyleJO;//因子规范 
    
    
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
        deviceList.add((IDeviceData) (new DeviceData(server, port)));
        deviceList.add((IDeviceData) (new NationalDeviceData(server, port)));
        deviceList.add((IDeviceData) (new TVOCDeviceData(server, port)));
        deviceList.add((IDeviceData) (new WaterData(server, port)));
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
            try {
                Thread.sleep(TIME_SLEEP * 1000);
            } catch (Exception e) {
                e.printStackTrace();
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
            title = configJO.getString("title");
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
                }
            }
            
            // 发送因子，配置
            factorStyleJO = configJO.getJSONObject("factorList");
            
            // 获取设备号
            String deviceIds = configJO.getString("deviceIds");
            deviceIdArr = deviceIds.split(",");
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

    
}
