package com.iss.iotdatamonitor.ui;

import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

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

    private JFrame frame;
    private JTextField configTextField;
    private JScrollPane scrollPane;
    private JList contentList;
    private JTextField serverTextField;
    private JTextField portTextField;

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
        frame = new JFrame("软通智慧环保IOT数据模拟发送工具");
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
        configTextField.setBounds(128, 38, 340, 21);
        frame.getContentPane().add(configTextField);
        configTextField.setColumns(10);
        
        JButton selectConfigButton = new JButton("选择");
        selectConfigButton.setBounds(478, 37, 98, 23);
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
        serverTextField.setColumns(10);
        serverTextField.setBounds(128, 66, 340, 21);
        frame.getContentPane().add(serverTextField);
        
        JLabel label_2 = new JLabel("端口号：");
        label_2.setIcon(new ImageIcon(MainFrame.class.getResource("/images/icon_port.png")));
        label_2.setBounds(10, 94, 116, 21);
        frame.getContentPane().add(label_2);
        
        portTextField = new JTextField();
        portTextField.setColumns(10);
        portTextField.setBounds(128, 94, 340, 21);
        frame.getContentPane().add(portTextField);
        
        JButton startButton = new JButton("开始");
        startButton.setBounds(478, 93, 98, 23);
        frame.getContentPane().add(startButton);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String type = "0";
                String configFilePath = configTextField.getText().trim();
                String server = serverTextField.getText().trim();
                String port = portTextField.getText().trim();
                // 处理数据
                Controller.getInstance(type, configFilePath, server, port).setListMessage(contentList);
                
                // 界面调整
                JScrollBar sBar = scrollPane.getVerticalScrollBar(); 
                sBar.setValue(sBar.getMaximum() + 10); 
            }
        });
    }
    

    /**文件选择器*/
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
     * 文件选择器
     * @author Liu Siyuan
     *
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
