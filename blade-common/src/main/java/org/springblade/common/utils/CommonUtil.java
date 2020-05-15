/*
 *      Copyright (c) 2018-2028, Chill Zhuang All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the dreamlu.net developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: Chill 庄骞 (smallchill@163.com)
 */
package org.springblade.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springblade.common.constant.CitmsAppConstant;
import org.springblade.common.constant.CommonConstant;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 通用工具类
 *
 * @author Chill
 */
@Slf4j
public class CommonUtil {

    /**
     * 双精度转字符串，小数点保留指定位数
     *
     * @param number 双精度数据
     * @param decimalSize 小数点保留位数
     * @return
     */
    public static String fomatDoubleToString(double number, int decimalSize){
        BigDecimal bg = new BigDecimal(number).setScale(decimalSize, RoundingMode.DOWN);
        String scaleStr = bg.doubleValue() + "";
        return scaleStr.replaceAll("\\.0", "");
    }

    /**
     * 图片地址转Base64字符串
     *
     * @param url 图片URL地址
     * @param isBase64Prefix 是否需要前缀
     * @return
     */
    public static String imgUrlToBase64(String url, boolean isBase64Prefix){
        if(StringUtils.isBlank(url) || (url.indexOf("http://") < 0 && url.indexOf("https://") < 0)){
            return null;
        }
        // url地址处理，对文件名urlEncode编码
        String urlForepart = url.substring(0, url.lastIndexOf("/") + 1);
        String urlFileName = url.substring(url.lastIndexOf("/") + 1);
        try {
            if(urlFileName.indexOf("@") != -1){
                url = urlForepart + URLEncoder.encode(urlFileName, "UTF-8");
            }
        } catch (UnsupportedEncodingException e) {
            log.error("图片地址转Base64字符串，URL地址中文件名UrlEncode编码异常", e);
            return null;
        }

        String imgBase64 = null;

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        InputStream inputStream = null;
        OutputStream outputStream = null;
        byte[] data = null;
        try {
            List list = new ArrayList<>();
            list.add(MediaType.IMAGE_JPEG);
            headers.setAccept(list);
            URI uri = new URI(url);
            ResponseEntity<byte[]> response = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    new HttpEntity<byte[]>(headers),
                    byte[].class);

            byte[] result = response.getBody();

            inputStream = new ByteArrayInputStream(result);
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
            // 加密
            BASE64Encoder encoder = new BASE64Encoder();
            String base64 = encoder.encode(data);
            if(isBase64Prefix){
                String base64Prefix = getBase64PrefixByImgUrl(url);
                imgBase64 = base64Prefix + base64;
            }else{
                imgBase64 = base64;
            }
        } catch (Exception e) {
            log.error("图片URL转Base64字符串异常，URL地址：" + url, e);
        }finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error("图片URL转Base64字符串，关闭输入流异常", e);
                }
            }
            if(outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    log.error("图片URL转Base64字符串，关闭输出流异常", e);
                }
            }
        }
        if(StringUtils.isNotBlank(imgBase64)){
            imgBase64 = imgBase64.replaceAll("\r|\n","");
        }
        return imgBase64;
    }

    public static byte[] imgUrlToByte(String url){
        if(StringUtils.isBlank(url) || (url.indexOf("http://") < 0 && url.indexOf("https://") < 0)){
            return null;
        }
        // url地址处理，对文件名urlEncode编码
        String urlForepart = url.substring(0, url.lastIndexOf("/") + 1);
        String urlFileName = url.substring(url.lastIndexOf("/") + 1);
        try {
            if(urlFileName.indexOf("@") != -1){
                url = urlForepart + URLEncoder.encode(urlFileName, "UTF-8");
            }
        } catch (UnsupportedEncodingException e) {
            log.error("图片地址转Base64字符串，URL地址中文件名UrlEncode编码异常", e);
            return null;
        }
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(3000);
        requestFactory.setReadTimeout(3000);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        HttpHeaders headers = new HttpHeaders();
        InputStream inputStream = null;
        OutputStream outputStream = null;
        byte[] result = null;
        try {
            List list = new ArrayList<>();
            list.add(MediaType.IMAGE_JPEG);
            headers.setAccept(list);
            URI uri = new URI(url);
            ResponseEntity<byte[]> response = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    new HttpEntity<byte[]>(headers),
                    byte[].class);
            result = response.getBody();
        } catch (Exception e) {
            log.error("图片URL转Base64字符串异常，URL地址：" + url, e);
        }finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error("图片URL转Base64字符串，关闭输入流异常", e);
                }
            }
            if(outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    log.error("图片URL转Base64字符串，关闭输出流异常", e);
                }
            }
        }
        return result;
    }


    /**
     * 年龄转年龄段
     *
     * @param age 年龄
     * @return
     */
    public static String ageToAgeRegion(Integer age){
        if(age == null || age <= 0){
            // 未知
            return null;
        }
        if(age > 0 && age <= 6){
            // 孩童
            return "1";
        }else if(age >= 7 && age <= 14){
            // 少年
            return "5";
        }else if(age >= 15 && age <= 35){
            // 青年
            return "2";
        }else if(age >= 36 && age <= 60){
            // 中年
            return "3";
        }else if(age >= 61){
            // 老年
            return "4";
        }else{
            return null;
        }
    }

    /**
     * 生成随机数
     *
     * @param startNumber 开始数字
     * @param endNumber 结束数字
     * @return 开始数字>=生成的随机数<结束数字
     */
    public static int getRandomNumber(int startNumber, int endNumber){
        int random = 0;
        do{
            random = (int)(Math.random() * endNumber);
        }while (random < startNumber);
        return  random;
    }

    /**
     * 根据图片地址获取Base64前缀
     *
     * @param url 图片URL地址或磁盘路径
     * @return
     */
    private static String getBase64PrefixByImgUrl(String url){
        String fileFormat = "jpeg";
        if(StringUtils.isBlank(url)){
            return "data:image/" + fileFormat + ";base64,";
        }
        String suffix = url.substring(url.lastIndexOf("."));
        switch (suffix){
            case ".png":
                fileFormat = "png";
                break;
            case ".gif":
                fileFormat = "gif";
                break;
            case ".bmp":
                fileFormat = "bmp";
                break;
            case ".ico":
                fileFormat = "ico";
                break;
        }
        return "data:image/" + fileFormat + ";base64,";
    }



}
