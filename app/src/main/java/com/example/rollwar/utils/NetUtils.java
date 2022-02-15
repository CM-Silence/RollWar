package com.example.rollwar.utils;

import android.os.Message;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

/**
 * description ： 用于进行网络请求
 * author : Silence
 */

public class NetUtils {

    private static String StreamToString(InputStream in){
        StringBuilder sb = new StringBuilder();
        String oneLine;
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        try {
            while ((oneLine = reader.readLine()) != null) { //readLine方法将读取一行
                sb.append(oneLine).append('\n'); //拼接字符串并且增加换行，提高可读性
                }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();//关闭InputStream
                } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();//将拼接好的字符串返回出去
    }


    public static void sendNetRequest(String mUrl,String aim){
        new Thread( () -> {
            try {
                URL url = new URL(mUrl);
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(8000);//设置最大连接时间，单位为ms
                connection.setReadTimeout(8000);//设置最大的读取时间，单位为ms
                connection.setRequestProperty("Accept-Language","zh-CN,zh;q=0.9");
                connection.setRequestProperty("Accept-Encoding","gzip,deflate");
                connection.connect();
                InputStream in = connection.getInputStream();//从接口处获取
                String responseData = StreamToString(in);
                Message message = new Message();//新建一个Message
                message.obj = jsonDecodeTest(responseData,aim);;//将数据赋值给message的obj属性
                //MainActivity.mHandler.sendMessage(message);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }).start();
    }

    public static void sendNetRequest(String mUrl, String aim, @Nullable HashMap<String,String> params){
        new Thread( () -> {
            try {
                URL url = new URL(mUrl);
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("POST");
                connection.setConnectTimeout(8000);//设置最大连接时间，单位为ms
                connection.setReadTimeout(8000);//设置最大的读取时间，单位为ms
                connection.setDoOutput(true);//允许输入流
                connection.setDoInput(true);//允许输出流
                StringBuilder dataToWrite = new StringBuilder(); //构建参数值
                connection.connect();
                if(params != null) {
                    for (String key : params.keySet()) {
                        dataToWrite.append(key).append("=").append(params.get(key)).append("&");
                    }
                    OutputStream outputStream = connection.getOutputStream();
                    outputStream.write(dataToWrite.substring(0, dataToWrite.length() - 1).getBytes());//去除最后一个&
                }
                InputStream in = connection.getInputStream();//从接口处获取数据
                String responseData = StreamToString(in);
                Message message = new Message();//新建一个Message
                //message.obj = jsonDecodeTest(responseData,aim);;//将数据赋值给message的obj属性
                message.obj = responseData;
                //MainActivity.mHandler.sendMessage(message);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }).start();
    }

    public static String jsonDecodeTest(String json,String aim){
        StringBuilder information = new StringBuilder();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for(int i = 0; i < jsonArray.length(); i++){
                information.append(jsonArray.getJSONObject(i).getString(aim)).append(",");
            }
            information.append(aim);
        }catch (Exception e){
            e.printStackTrace();
        }
        return information.toString();
    }

}
