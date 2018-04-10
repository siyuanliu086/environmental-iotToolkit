package com.iss.iotdatamonitor.ui;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.alibaba.fastjson.JSONObject;
import com.datamonitor.ConfigFile;
import com.datamonitor.message.DeviceData;
import com.datamonitor.message.IDeviceData;
import com.datamonitor.message.NationalDeviceData;
import com.datamonitor.message.TVOCDeviceData;
import com.datamonitor.message.WaterData;
import com.datamonitor.sender.SenderClient;
import com.datamonitor.utils.StringUtil;
import com.iss.iotdatamonitor.tools.FileHelper;

public class Controller {
    public static final String TITLE = "软通智慧环保IOT数据模拟发送工具";
    
    private static final int TIME_SLEEP = 5;//休眠5秒
    
    private int type;
    private String configFilePath, server, port;
    private String[] deviceIdArr;
    private int sendCycle = 10;//默认10分钟
    private int autoFullFactor = 0;//默认补全全部因子
    private JSONObject styleJO;
    
    public static Controller instance;
    
    private List<IDeviceData> deviceList = new ArrayList<>();

    private Controller(int type, String configFilePath, String server, String port) {
        this.type = type;
        this.configFilePath = configFilePath;
        this.server = server;
        this.port = port;
    }
    private Controller() {};
    
    public static Controller getInstance(int type, String configFilePath, String server, String port) {
        if (instance == null) {
            instance = new Controller(type, configFilePath, server, port);
        }
        return instance;
    }
    
    public void init() {
        try {
            parseConfigFile(configFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        deviceList.add((IDeviceData) (new DeviceData(server, port)));
        deviceList.add((IDeviceData) (new NationalDeviceData(server, port)));
        deviceList.add((IDeviceData) (new TVOCDeviceData(server, port)));
        deviceList.add((IDeviceData) (new WaterData(server, port)));
        startSender();
    }
    
    /**
     * @description : 开始发送
     * @author 		: Liu Siyuan
     * @Date 		: 2018年4月10日 下午3:02:45
     * @version 1.0.0
     */
    private void startSender() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            
            @Override
            public void run() {
                for(IDeviceData deviceData : deviceList) {
                    if(deviceData.getDataType() == type) {
                        deviceSend(deviceData);
                    }
                    continue;
                }
            }
        }, 1000,  600 * 1000);
    }
    
    /**
     * @description : 所有设备号发送一次数据包
     * @author 		: Liu Siyuan
     * @Date 		: 2018年4月10日 下午3:01:38
     * @version 1.0.0
     */
    private void deviceSend(IDeviceData deviceData) {
        for(String deviceId : deviceIdArr) {
            deviceData.setDeviceId(deviceId);
            deviceData.setAutoFullFactor(autoFullFactor == 1);
            deviceData.setFactorStyle(styleJO);
            try {
                String mess = SenderClient.startSocketClient(deviceData);
                callbackPrint(mess);
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
            String title = configJO.getString("title");
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
                autoFullFactor = 0;
            }
            
            // 因子
            styleJO = configJO.getJSONObject("factorList");
            
            // 获取设备号
            String deviceIds = configJO.getString("deviceIds");
            deviceIdArr = deviceIds.split(",");
        } else {
            callbackPrint("文件异常！");
        }
    }
    
    private IControllerCallback callback;
    public interface IControllerCallback {
        void onMessage(String mess);
        void updateTitle(String title);
    }
    public void setControllerCallback(IControllerCallback callback) {
        this.callback = callback;
    }
    private void callbackPrint(String mess) {
        if(callback != null) {
            callback.onMessage(mess);
        }
    }
    private void callbackTitle(String title) {
        if(callback != null) {
            callback.updateTitle(title);
        }
    }
}
