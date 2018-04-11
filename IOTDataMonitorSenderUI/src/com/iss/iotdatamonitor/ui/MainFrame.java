package com.iss.iotdatamonitor.ui;

import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.filechooser.FileFilter;

import com.iss.iotcheck.MainWindow;

public class MainFrame {
    // 面板区显示的信息条数（超出部分回收）
    private static final int BUFFER_SIZE = 60;

    private JFrame frame;
    private JTextField configTextField;
    private JScrollPane scrollPane;
    private JList contentList;
    private JTextField serverTextField;
    private JTextField portTextField;
    
    private JButton stopButton;
    private JButton resetButton;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {

            public void run() {
                try {
                    MainFrame window = new MainFrame();
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
    public MainFrame() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame(Controller.TITLE);
        frame.setBackground(SystemColor.textHighlight);
        frame.getContentPane().setBackground(SystemColor.inactiveCaptionBorder);
        frame.setBounds(100, 100, 750, 492);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("/images/iot_icon.png")));
        frame.setResizable(false);
        
        JLabel typeLabel = new JLabel("选择类型：");
        typeLabel.setIcon(new ImageIcon(MainFrame.class.getResource("/images/icon_type.png")));
        typeLabel.setBounds(10, 10, 116, 21);
        frame.getContentPane().add(typeLabel);
        
        String[] item = new String[] {
                "大气-国标212",
                "大气-简标212",
                "大气-TVOC",
                "地表水-国标212",
                "位置-简标212"
        };
        JComboBox comboBox = new JComboBox(item);
        comboBox.setBounds(128, 10, 340, 21);
        frame.getContentPane().add(comboBox);
        
        JLabel configLabel = new JLabel("选择配置文件：");
        configLabel.setIcon(new ImageIcon(MainFrame.class.getResource("/images/icon_config.png")));
        configLabel.setBounds(10, 38, 116, 21);
        frame.getContentPane().add(configLabel);
        
        configTextField = new JTextField();
        configTextField.setText("D:\\as_git\\environmental-iotToolkit\\IOTDataMonitorSenderUI\\src\\com\\iss\\iotdatamonitor\\ui\\data_monitor.txt");
        configTextField.setBounds(128, 38, 340, 21);
        frame.getContentPane().add(configTextField);
        configTextField.setColumns(10);
        
        JButton selectConfigButton = new JButton("选择");
        selectConfigButton.setBounds(478, 37, 60, 23);
        frame.getContentPane().add(selectConfigButton);
        selectConfigButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clickChooseFile();
            }
        });

        scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(10, 126, 724, 328);
        frame.getContentPane().add(scrollPane);
        
        contentList = new JList();
        scrollPane.setViewportView(contentList);
        
        JLabel serverLabel = new JLabel("服务器地址：");
        serverLabel.setIcon(new ImageIcon(MainFrame.class.getResource("/images/icon_server.png")));
        serverLabel.setBounds(10, 66, 116, 21);
        frame.getContentPane().add(serverLabel);
        
        serverTextField = new JTextField();
        serverTextField.setText("49.4.6.49");
        serverTextField.setColumns(10);
        serverTextField.setBounds(128, 66, 340, 21);
        frame.getContentPane().add(serverTextField);
        
        JLabel portLabel = new JLabel("端口号：");
        portLabel.setIcon(new ImageIcon(MainFrame.class.getResource("/images/icon_port.png")));
        portLabel.setBounds(10, 94, 116, 21);
        frame.getContentPane().add(portLabel);
        
        portTextField = new JTextField();
        portTextField.setText("8095");
        portTextField.setColumns(10);
        portTextField.setBounds(128, 94, 340, 21);
        frame.getContentPane().add(portTextField);
        
        JButton startButton = new JButton("开始");
        startButton.setBounds(478, 93, 60, 23);
        frame.getContentPane().add(startButton);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取选择类型编号
                int selectIndex = 0;
                String selectedItem = comboBox.getSelectedItem().toString();
                for(; selectIndex < item.length; selectIndex ++) {
                    String name = item[selectIndex];
                    if(selectedItem.equals(name)) {
                        break;
                    }
                }
                
                // 获取选择配置文件
                String configFilePath = configTextField.getText().trim();
                String server = serverTextField.getText().trim();
                String port = portTextField.getText().trim();
                // 处理数据
                Controller mController = Controller.getInstance();
                mController.setControllerCallback(new Controller.IControllerCallback() {
                    
                    @Override
                    public void updateTitle(String title) {
                        frame.setTitle(Controller.TITLE + "-" + title);
                    }
                    
                    @Override
                    public void onMessage(String mess) {
                        setListMessage(mess);
                    }
                });
                mController.init(selectIndex, configFilePath, server, port);
                mController.start();
            }
        });
        
        stopButton = new JButton("停止");
        stopButton.setBounds(548, 93, 60, 23);
        frame.getContentPane().add(stopButton);
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.getInstance().stop();
            }
        });
        
        resetButton = new JButton("重置");
        resetButton.setBounds(674, 93, 60, 23);
        frame.getContentPane().add(resetButton);
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.getInstance().reset();
            }
        });
    }
    
    private Timer timer;
    
    /**
     * @description : 发送信息打印窗口
     * @business	: 发送信息打印，自动运动
     * @param 		: mess 信息，数据包
     * @return		:
     * @author 		: Liu Siyuan
     * @Date 		: 2018年4月10日 下午2:07:10
     * @version 1.0.0
     */
    public void setListMessage(String mess) {
        ListModel dlm = contentList.getModel();
        int index = 0, size = dlm.getSize();
        if(size >= BUFFER_SIZE) {
            index = size - BUFFER_SIZE;
        }
        DefaultListModel addDlm = new DefaultListModel();
        for(; index < dlm.getSize(); index ++) {
            addDlm.addElement(dlm.getElementAt(index));
        }
        addDlm.addElement(mess);
        contentList.setModel(addDlm);
        
        if(timer == null) {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                
                @Override
                public void run() {
                    // 自动滚动到底部
                    JScrollBar sBar = scrollPane.getVerticalScrollBar(); 
                    sBar.setValue(sBar.getMaximum()); 
                }
            }, 800);
        }
    }
    

    /**
     * @description : 文件选择器
     * @author 		: Liu Siyuan
     * @Date 		: 2018年4月10日 下午2:08:40
     * @version 1.0.0
     */
    private void clickChooseFile() {
        JFileChooser jfc = new JFileChooser(".");
        JSONFileFilter excelFilter = new JSONFileFilter(); //excel过滤器    
        jfc.addChoosableFileFilter(excelFilter);  
        jfc.setFileFilter(excelFilter);
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);//仅限文件  
        jfc.showDialog(new JLabel(), "选择");  
        File file = jfc.getSelectedFile();  
        if(file != null) {
//            if(file.isDirectory()){  
//                System.out.println("文件夹:"+file.getAbsolutePath());  
//            } else 
                if(file.isFile()){  
                System.out.println("文件:"+file.getAbsolutePath());  
                configTextField.setText(file.getAbsolutePath());
            }  
        }
    }
    
    /**
     * 文件过滤器
     * @author Liu Siyuan
     */
    class JSONFileFilter extends FileFilter {    
        public String getDescription() {    
            return "*.json;*.txt";    
        }    
        
        public boolean accept(File file) {    
            String name = file.getName();    
            return file.isDirectory() || name.toLowerCase().endsWith(".json") || name.toLowerCase().endsWith(".txt");  // 仅显示目录和json/txt文件  
        }    
    }
}
