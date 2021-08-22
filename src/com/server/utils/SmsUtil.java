package com.server.utils;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;


import java.io.IOException;

/**
 * @Author:zdc
 * @Date 2021/8/17 10:30
 * @Version 1.0
 */

public class SmsUtil {

    public static String sendSms(String phone,String number) throws IOException {
        HttpClient client = new HttpClient();
        PostMethod post = new PostMethod("http://gbk.sms.webchinese.cn");
        post.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=gbk");//在头文件中设置转码
        NameValuePair[] data = {
                new NameValuePair("Uid", "ZhangdctTop"),//用户名
                new NameValuePair("Key", "d41d8cd98f00b204e980"),//密码
                new NameValuePair("smsMob", phone),//电话号码
                new NameValuePair("smsText", "您的验证码为："+number)};//短信内容
        post.setRequestBody(data);

        client.executeMethod(post);
        Header[] headers = post.getResponseHeaders();
        int statusCode = post.getStatusCode();
        System.out.println("statusCode:" + statusCode);
        for (Header h : headers) {
            System.out.println(h.toString());
        }
        String result = new String(post.getResponseBodyAsString().getBytes("gbk"));
        System.out.println(result); //打印返回消息状态
        post.releaseConnection();
        return result;
    }
}
