package com.iotdatamonitor.message;

import java.util.LinkedList;

public class MessageQueue {
    private static LinkedList<String> storage = new LinkedList<String>();

    public static synchronized void push(String message) {// 需要加上同步
        storage.add(message);
    }

    public static String pick() {
        String message = new String(storage.getFirst());
        storage.removeFirst();
        return message;
    }

    public static boolean empty() {
        return storage.isEmpty();
    }
}
