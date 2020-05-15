package cn.citms.dataaccessserver.util;

import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密
 */
@Slf4j
public class MD5Utils {

    /**
     * 对字符串进行MD5加密
     * @param key
     * @return
     */
    public static String encrypt(String key){
//        String value = "";
//        try{
//            MessageDigest md5=MessageDigest.getInstance("MD5");
//            BASE64Encoder base64en = new BASE64Encoder();
//            //加密后的字符串
//            value = base64en.encode(md5.digest(key.getBytes("utf-8")));
//        } catch (NoSuchAlgorithmException ne) {
//            log.error("加密失败",ne.toString());
//        } catch(UnsupportedEncodingException ue) {
//            log.error("加密失败",ue.toString());
//        }
//        return value;
        byte[] secretBytes = null;
        try {
            secretBytes = MessageDigest.getInstance("md5").digest(
                    key.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有这个md5算法！");
        }
        String md5code = new BigInteger(1, secretBytes).toString(16);
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }
        return md5code;
    }
}
