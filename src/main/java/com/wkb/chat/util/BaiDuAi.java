package com.wkb.chat.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.baidu.aip.speech.AipSpeech;
import com.baidu.aip.speech.TtsResponse;
import com.baidu.aip.util.Util;

public class BaiDuAi {

    public static final String APP_ID = "11577815";
    public static final String API_KEY = "0rVp3Q9SUv5Wijx0M47la44C";
    public static final String SECRET_KEY = "ZbrjCqGEXdals83uI7lRKMEKFvhDlyQh";
    public static final String CLASSPATH = FileUtil.getUplodFilePath();

    /**
     * 语音识别
     *
     * @param args
     * @throws JSONException
     */

    public static JSONObject soundDistinguish(String filePath) throws JSONException {

        // 初始化一个FaceClient
        AipSpeech client = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        byte[] b = getBytes(filePath);

        // 调用API
        JSONObject res = client.asr(b, "wav", 8000, null);
        System.out.println("result:" + res);
        return res;
    }


    public static byte[] getBytes(String filePath) {
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }
}
