package com.server.utils;

import java.io.Closeable;
/**
 * Closeable接口又继承了另外的父接口AutoCloseable
 *
 * 其中close()方法是关闭流并且释放与其相关的任何方法，如果流已被关闭，那么调用此方法没有效果。
 *
 * 只要实现了AutoCloseable或Closeable接口的类或接口，都可以使用该代码块来实现异常处理和资源关闭异常抛出顺序
 *
 * InputStream和OutputStream类都实现了该接口。
 * @author user
 *
 */
public class CloseUtil {
    /**
     ** 释放资源
     */
    public static void close(Closeable... targets) {//可变参数
        for (Closeable target : targets) {
            try {
                if (target != null) {
                    target.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
