package com.wkb.chat.controller;


import com.wkb.chat.util.BaiDuAi;
import com.wkb.chat.util.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javazoom.jl.decoder.JavaLayerException;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;


@RestController
@RequestMapping(value = "SoundCtrl")
@Api(value = "音频后台", tags = "SoundCtrl")
public class SoundCtrl {

    public static final String CLASSPATH = FileUtil.getUplodFilePath();

    @ApiOperation(value = "音频流上传")
    @RequestMapping(value = "armUpload", method = RequestMethod.POST)
    public void armUpload(@RequestParam("audioData") MultipartFile audioData, HttpServletResponse response) throws IOException, JavaLayerException, JSONException {

        long fileName = new Date().getTime();

        /**
         * 将文件上传到指定文件夹
         */
        File temp = new File(CLASSPATH + "upload/");
        if (!temp.exists()) {
            temp.mkdir();
        }
        File onlineAudioFile = new File(CLASSPATH + "upload/" + fileName + ".wav");
        try {
            FileCopyUtils.copy(audioData.getInputStream(), new FileOutputStream(onlineAudioFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String text;
        String resultText;
        /**
         * 将录音转为文字
         */
        JSONObject textJson = BaiDuAi.soundDistinguish(CLASSPATH + "upload/" + fileName + ".wav");
        if (textJson.get("err_no").equals(0)) {
            text = textJson.get("result").toString().substring(2, textJson.get("result").toString().length() - 2);
            if (text != null) {
                demo(text);
            }
        }
    }

    public void demo(String text) {
        OutputStream os = null;
        try {
            os = new FileOutputStream(new File(CLASSPATH + "txt/txt.txt"));
            //向文件中写入数据
            String content = text;
            byte[] data = content.getBytes();
            os.write(data);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                os.close();//关闭输出流
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
