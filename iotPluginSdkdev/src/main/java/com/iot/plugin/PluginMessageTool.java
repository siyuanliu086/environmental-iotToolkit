package com.iot.plugin;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

/**
 * 消息中心工具
 * @author Liu Siyuan
 *
 */
public class PluginMessageTool {
    private static Logger logger = LoggerFactory.getLogger(PluginMessageTool.class);
    
    private final static String MQ_ADDRESS = "114.115.158.187";
    private final static int MQ_PORT = 5672;
    private final static String MQ_USER = "admin";
    private final static String MQ_PWD = "admin123";
    
    private static Connection connection;
    
    private static PluginMessageTool instance;
    private PluginMessageTool() {}
    
    public static final PluginMessageTool getInstance() {
        if(instance == null) {
            instance = new PluginMessageTool();
            getConnection();
        }
        return instance;
    }
    
    private static void getConnection() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(MQ_ADDRESS);// MQ的IP
        factory.setPort(MQ_PORT);// MQ端口
        factory.setUsername(MQ_USER);// MQ用户名
        factory.setPassword(MQ_PWD);// MQ密码
        try {
            logger.info("----Create RabbitMQ Connection ----");
            connection = factory.newConnection();
            //Channel channel = connection.createChannel(); //从连接中创建通道
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 发送消息
     * @author      : Liu Siyuan
     * @Date        : 2018年7月18日 上午11:48:58
     * @version 1.0.0
     */
    public void send(String exchangeName, String message) throws IOException {
        if(connection != null && connection.isOpen()) {
            String QUEUE = exchangeName + "_QUEUE";
            Channel channel = connection.createChannel(); //从连接中创建通道
            channel.exchangeDeclare(exchangeName, "fanout");//声明Exchange，指定交换机的类型为topic
            Map<String, Object> argument = new HashMap<>();
            argument.put("x-message-ttl", 7 * 24 * 60 * 60 * 1000);//消息一周过期
            channel.queueDeclare(QUEUE, false, false, true, argument);
            channel.basicPublish(exchangeName, "", null, message.getBytes());
        } else {
            // 重新创建
            getConnection();
            send(exchangeName, message);
        }
    }
    
    /**
     * 接收消息
     * @author 		: Liu Siyuan
     * @Date 		: 2018年7月18日 上午11:48:58
     * @version 1.0.0
     */
    public void getCenterMessage(String exchangeName) throws Exception {
        String QUEUE = exchangeName + "_QUEUE";
        if(connection != null && connection.isOpen()) {
            /* 声明要连接的队列 */
            Channel channel = connection.createChannel(); //从连接中创建通道
            Map<String, Object> argument = new HashMap<>();
            argument.put("x-message-ttl", 7 * 24 * 60 * 60 * 1000);//消息一周过期
            channel.queueDeclare(QUEUE, false, false, true, argument);
            channel.exchangeDeclare(exchangeName, "fanout");//声明Exchange，指定交换机的类型为topic
            System.out.println("等待消息产生：");
            channel.queueBind(QUEUE, exchangeName, "");
            channel.basicConsume(QUEUE, false, "", new DefaultConsumer(channel) {
                        @Override
                        public void handleDelivery(String consumerTag, Envelope envelope, 
                                AMQP.BasicProperties properties, byte[] body) throws IOException {
                            String routingKey = envelope.getRoutingKey();
                            long deliveryTag = envelope.getDeliveryTag();
                            channel.basicAck(deliveryTag, false);
                            // (process the message components here ...)
                            String message = new String(body);
                            handlerMessage(routingKey, message);
                        }
               });
        } else {
            // 重新创建
            getConnection();
            // 构建后再监听
            getCenterMessage(exchangeName);
        }
    }
    
    private void handlerMessage(String routingKey, String message) {
        System.out.println("routingKey:" + routingKey + " message:" + message);
        if(callback != null) {
            callback.onMessage(routingKey, message);
        }
    }
    
    private MessageCallback callback;
    public interface MessageCallback {
        void onMessage(String routingKey, String message);
    }
    public void setMessageListener(MessageCallback callback) {
        this.callback = callback;
    }
    
    public static void main(String[] args) {
        PluginMessageTool messageTool = new PluginMessageTool();
        try {
//            messageTool.getCenterMessage("IOTSDK_EXCHNAGE_AIR");
            Thread.sleep(1000);
            for(int i = 0; i < 10; i ++) {
                messageTool.send("IOTSDK_EXCHNAGE_AIR", "this is test pluginQueue " + i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
