package com.example.finalproject.DatabaseAdapter;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.JsonObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpAdapter {
    public static  void getUrl(String path, Handler handler, int what) throws Exception {

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("running");
                try{
                    URL url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    // 设置连接超时为5秒
                    conn.setConnectTimeout(5000);
                    // 设置请求类型为Get类型
                    conn.setRequestMethod("GET");

                    conn.connect();

                    InputStream inStream = conn.getInputStream();


                    String bt = Streamtool.read(inStream);

                    inStream.close();

                    if (handler != null) {
//                        System.out.println(bt);
                        Message message = new Message();
//                        message.arg1 = response;
                        message.what = what;
                        message.obj = bt;
                        handler.sendMessage(message);
                        System.out.println("send!");
                    }else{
                        System.out.println("empty!");
                    }

                }   catch (Exception e) {
                    System.out.println(e);
                }

            }
        }).start();

    }
}
