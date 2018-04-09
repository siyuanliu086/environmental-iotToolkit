package com.iss.iotcheck;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.iss.iotcheck.plugin.IProcessing;

/**
 * 启动页面，页面主框
 * @author Liu Siyuan
 *
 */
public class MainWindow {
    private JFrame frame;
    private JTextArea infoTextArea;
    private JRadioButton waterRadioButton, airRadioButton, gasButton, tvocBtnTvoc;
    private JLabel checkResult;
    private List<JRadioButton> radioButtonList;
    private JScrollPane resultPanel;
    private JLabel label_1;
    
    private IOTCheck iotCheck;
    
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {

            public void run() {
                try {
                    MainWindow window = new MainWindow();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public MainWindow() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        iotCheck = new IOTCheckImpl();
        radioButtonList = new ArrayList<>();
        
        frame = new JFrame("软通IOT数据检测工具");
        frame.setBounds(100, 100, 750, 492);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("/images/iot_icon.png")));
        frame.setResizable(false);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(220, 220, 220));
        mainPanel.setBounds(0, 0, 744, 464);
        frame.getContentPane().add(mainPanel);
        mainPanel.setLayout(null);
        
        Color transparent = new Color(00, 0, 0, 0);
        // =======设备类型：水、气选择按钮=======
        label_1 = new JLabel("设备类型：");
        label_1.setBackground(Color.DARK_GRAY);
        label_1.setBounds(10, 9, 97, 15);
        mainPanel.add(label_1);
        
        airRadioButton = new JRadioButton("大气");
        airRadioButton.setBackground(transparent);
        airRadioButton.setBounds(110, 5, 100, 23);
        mainPanel.add(airRadioButton);
        airRadioButton.setSelected(true);
        radioButtonList.add(airRadioButton);
        airRadioButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                // 手动互斥
                for(JRadioButton radio : radioButtonList) {
                    if(!"大气".equals(radio.getText())) {                        
                        radio.setSelected(false);
                    }
                }
            }
        });
        
        waterRadioButton = new JRadioButton("地表水");
        waterRadioButton.setBackground(transparent);
        waterRadioButton.setBounds(220, 5, 100, 23);
        mainPanel.add(waterRadioButton);
        radioButtonList.add(waterRadioButton);
        waterRadioButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                // 手动互斥
                for(JRadioButton radio : radioButtonList) {
                    if(!"地表水".equals(radio.getText())) {                        
                        radio.setSelected(false);
                    }
                }
            }
        });
        
        gasButton = new JRadioButton("废气");
        gasButton.setBackground(transparent);
        gasButton.setBounds(320, 5, 100, 23);
        mainPanel.add(gasButton);
        gasButton.setEnabled(false);
        radioButtonList.add(gasButton);
        gasButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                // 手动互斥
                for(JRadioButton radio : radioButtonList) {
                    if(!"废气".equals(radio.getText())) {                        
                        radio.setSelected(false);
                    }
                }
            }
        });
        
        tvocBtnTvoc = new JRadioButton("TVOC");
        tvocBtnTvoc.setBackground(transparent);
        tvocBtnTvoc.setBounds(420, 5, 100, 23);
        mainPanel.add(tvocBtnTvoc);
        radioButtonList.add(tvocBtnTvoc);
        tvocBtnTvoc.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                // 手动互斥
                for(JRadioButton radio : radioButtonList) {
                    if(!"TVOC".equals(radio.getText())) {                        
                        radio.setSelected(false);
                    }
                }
            }
        });
        
        JLabel label = new JLabel("检测数据包：");
        label.setBackground(Color.DARK_GRAY);
        label.setBounds(10, 34, 97, 15);
        mainPanel.add(label);
        
        JButton button = new JButton("检测");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String result = IOTCheck.RE_CODE_OK;
                int type = 0;
                String mess = infoTextArea.getText().toString().trim();
                if(mess == null || mess.length() < 4) {
                    result = IOTCheck.RE_MESS_EMP;
                    setCheckResult(result);
                    return;
                } 
                
                if(waterRadioButton.isSelected()) {//水
                    type = Integer.valueOf(IProcessing.SURFACE_WATER_MONITOR_212);
                } else if(airRadioButton.isSelected()) {//气
                    type = Integer.valueOf(IProcessing.AIR_MONITOR_MONITOR_212);
                } else if(tvocBtnTvoc.isSelected()) {//tvoc
                    type = Integer.valueOf(IProcessing.AIR_POLLUTE_MONITOR_212);
                } else {
                    result = IOTCheck.RE_MN_EMP;
                    setCheckResult(result);
                    return;
                }
                result = iotCheck.checkMessage(mess, type);
                setCheckResult(result);
            }
        });
        button.setBounds(606, 167, 93, 23);
        mainPanel.add(button);
        
        infoTextArea = new JTextArea();
        infoTextArea.setBackground(Color.WHITE);
        infoTextArea.setBounds(110, 34, 589, 122);
        infoTextArea.setLineWrap(true);
        // ##0223ST=22;CN=2011;PW=123456;MN=C037800AM2011;CP=&&DataTime=20180328235000;SO2-Rtd=19.0,SO2-Flag=N;NO2-Rtd=25.0,NO2-Flag=N;CO-Rtd=0.687,CO-Flag=N;O3-Rtd=76.0,O3-Flag=N;PM25-Rtd=75.0,PM25-Flag=N;PM10-Rtd=374.0,PM10-Flag=N&&4380
        infoTextArea.setText("");
        mainPanel.add(infoTextArea);
        
        JLabel tipLabel = new JLabel("检测结果：");
        tipLabel.setBackground(Color.DARK_GRAY);
        tipLabel.setBounds(10, 202, 97, 15);
        mainPanel.add(tipLabel);
        
        checkResult = new JLabel("结果");
        checkResult.setBounds(114, 0, 588, 252);
        
        resultPanel = new JScrollPane(checkResult);
        resultPanel.setBounds(110, 200, 590, 254);

        mainPanel.add(resultPanel);
    }
    
    private void setCheckResult(String result) {
        checkResult.setText(result);
    }
}
