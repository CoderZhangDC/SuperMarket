package com.server.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author:zdc
 * @Date 2021/8/11 17:08
 * @Version 1.0
 */
public class SystemStart {
    public static void main(String[] args) throws IOException {
        ExecutorService es = Executors.newFixedThreadPool(5);
        ServerSocket ss = new ServerSocket(6666);
        while (true){
            es.submit(new ServerRunnable(ss.accept()));
        }
    }
}
