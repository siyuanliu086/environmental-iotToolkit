package com.datamonitor.receiver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

import com.datamonitor.ConfigFile;
import com.datamonitor.message.MessageQueue;

public class ReceiverThread extends Thread {

    private Socket socket;

    public ReceiverThread(Socket socket) {
        this.socket = socket;
    }

    // @Override
    public void run2() {
        try {
            // 服务器端，实现基于UDP的用户登录
            // 1、创建服务器端DatagramSocket，指定端口
            DatagramSocket socket = new DatagramSocket(Integer.valueOf(ConfigFile.SOCKET_SERVER_PORT));
            // 2、创建数据报，用于接受客户端发送的数据
            byte[] data = new byte[10 * 1024];//
            DatagramPacket packet = new DatagramPacket(data, data.length);
            // 3、接受客户端发送的数据
            socket.receive(packet);// 此方法在接受数据报之前会一致阻塞
            // 4、读取数据
            String info = new String(data, 0, data.length);
            log("我是服务器，客户端告诉我" + info);

            // =========================================================
            // 向客户端响应数据
            // 1、定义客户端的地址、端口号、数据
            InetAddress address = packet.getAddress();
            int port = packet.getPort();
            byte[] data2 = "欢迎您！".getBytes();
            // 2、创建数据报，包含响应的数据信息
            DatagramPacket packet2 = new DatagramPacket(data2, data2.length, address, port);
            // 3、响应客户端
            socket.send(packet2);
            // 4、关闭资源
            socket.close();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        // 3、获取输入流，并读取客户端信息
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            is = socket.getInputStream();
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            String info = null;
            while ((info = br.readLine()) != null) {
                log("服务器收到：" + info);
            }

            socket.shutdownInput();// 关闭输入流
            // 4、获取输出流，响应客户端的请求
            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter out = new BufferedWriter(osw);
            out.write("服务端已收到");
            out.flush();

            // 5、关闭资源
            out.close();
            osw.close();
            os.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
                isr.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void log(String info) {
        MessageQueue.push(info);
        System.out.println(info);
    }
}
