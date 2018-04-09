package com.iss.iotdatamonitor.ui;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListModel;

public class Controller {
    // 面板区显示的信息条数（超出部门回收）
    private static final int BUFFER_SIZE = 60;
    
    public static final int TYPE_AIR_NA212 = 0;
    public static final int TYPE_AIR_SIM212 = 1;
    public static final int TYPE_AIR_TVOC212 = 2;
    public static final int TYPE_WATER_NA212 = 3;
    public static final int TYPE_POS_NA212 = 4;
    
    private String type, configFilePath, server, port;
    public static Controller instance;

    private Controller(String type, String configFilePath, String server, String port) {
        this.type = type.trim();
        this.configFilePath = configFilePath.trim();
        this.server = server.trim();
        this.port = port.trim();
    }
    private Controller() {};
    
    public static Controller getInstance(String type, String configFilePath, String server, String port) {
        if (instance == null) {
            instance = new Controller(type, configFilePath, server, port);
        }
        return instance;
    }
    
    public void setListMessage(JList contentList) {
        ListModel dlm = contentList.getModel();
        int index = 0, size = dlm.getSize();
        if(size >= BUFFER_SIZE) {
            index = size - BUFFER_SIZE;
        }
        DefaultListModel addDlm = new DefaultListModel();
        for(; index < dlm.getSize(); index ++) {
            addDlm.addElement(dlm.getElementAt(index));
        }
        addDlm.addElement(server);
        contentList.setModel(addDlm);
    }
}
