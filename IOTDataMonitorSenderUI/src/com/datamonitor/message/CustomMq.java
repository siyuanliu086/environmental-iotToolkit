package com.datamonitor.message;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

public class CustomMq {

    private static ConcurrentHashMap<String, LinkedList<String>> storage = new ConcurrentHashMap<String, LinkedList<String>>();

    public static synchronized void push(String key, String value) {// 需要加上同步
        if (storage.containsKey(key)) {
            storage.get(key).addFirst(value);
        } else {
            LinkedList<String> list = new LinkedList<String>();
            list.addFirst(value);
            storage.put(key, list);
        }
    }

    public static String peek(String key) {
        String message = storage.get(key).getFirst();
        CustomMq.pop(key);
        return message;
    }

    public static void pop(String key) {
        if (!storage.isEmpty()) {
            storage.get(key).removeFirst();
        }
    }

    public static boolean empty(String key) {
        if (storage.isEmpty() || storage.get(key) == null) {
            return true;
        }
        return storage.get(key).isEmpty();
    }
}
