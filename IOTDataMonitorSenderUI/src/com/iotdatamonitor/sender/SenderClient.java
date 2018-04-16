package com.iotdatamonitor.sender;

import java.util.concurrent.ConcurrentHashMap;

import com.iotdatamonitor.message.CustomMq;
import com.iotdatamonitor.message.IDeviceData;
import com.iotdatamonitor.utils.StringUtil;

public class SenderClient {
    // socket连接池
    public static ConcurrentHashMap<String, String> socketManages = new ConcurrentHashMap<String, String>();

    /**
     * 根据对象启动并发送消息
     * 
     * @param data
     * @throws Exception
     */
    public static String startSocketClient(IDeviceData data) throws Exception {
        // 构建数据协议
        String dataStr = data.getMessage();
        if (!StringUtil.isEmptyString(dataStr)) {
            String messageKey = data.getServer() +  "_" + data.getDeviceId();
            if (socketManages == null || !socketManages.containsKey(messageKey)) {
                socketManages.put(messageKey, "true");
                Thread zteTr = new Thread(new SenderSocketLongLink(data.getServer(), data.getPort(), data.getDeviceId()));
                zteTr.start();
            }
            CustomMq.push(messageKey, dataStr);
        }
        return dataStr;
    }

}
