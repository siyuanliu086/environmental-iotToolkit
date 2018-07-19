package com.iot.plugin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iot.plugin.PluginMessageTool.MessageCallback;
import com.iot.plugin.sdkdev.database.dao.DataDao;
import com.springboot.base.util.StringUtil;

import app.iot.process.database.entity.OlMonitorMinData;
import app.tools.JsonHelper;

@Component("mainProcess")
public class MainProcess extends Thread implements MessageCallback {
    
    @Autowired
    private DataDao dataDao;

	@Override
	public void run() {
		String exchangeName = "IOTSDK_TVOC";
        try {
            PluginMessageTool messageTool = PluginMessageTool.getInstance();
            messageTool.getCenterMessage(exchangeName);
            messageTool.setMessageListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

    /**
     * 一般性能要求：这里直接入库。
     * 高性能要求：这里可以实现一个缓冲队列或者开启多个线程并发执行。
     * @param       : routingKey 路由键, message 消息
     * @author      : Liu Siyuan
     * @Date        : 2018年7月18日 下午5:01:35
     * @version 1.0.0
     */
    @Override
    public void onMessage(String routingKey, String message) {
        if(StringUtil.isEmptyString(message)) {
            return;
        }
        try {
            OlMonitorMinData minData = JsonHelper.jsonStrToBean(message, OlMonitorMinData.class);
            //TODO 获取解析后的数据进行保存
            // 这里可以根据实际业务，添加二次计算数据
            // 例如：AQI计算、空气质量级别计算、补充站点信息等
            dataDao.insert(minData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
