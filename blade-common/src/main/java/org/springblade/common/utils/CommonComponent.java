package org.springblade.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xiaoleilu.hutool.setting.dialect.Props;
import com.xiaoleilu.hutool.util.ObjectUtil;
import com.xiaoleilu.hutool.util.StrUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springblade.common.constant.CitmsAppConstant;
import org.springblade.common.constant.CommonConstant;
import org.springblade.common.enums.DictEnum;
import org.springblade.common.vo.DeviceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

/**
 * 服务配置信息工具类
 */
@Data
@Component
@Slf4j
public class CommonComponent implements ApplicationListener<WebServerInitializedEvent> {

    /**
     * 服务端口号
     */
    private int serverPort;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OkHttpCli okHttpCli;

    /**
     * 获取本服务完整服务地址
     *
     * @return
     */
    public String getServerAddress() {
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return "http://" + address.getHostAddress() + ":" + this.serverPort;
    }

    /**
     * 获取本服务Host
     *
     * @return
     */
    public String getServerHost() {
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return address.getHostAddress();
    }

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        serverPort = event.getWebServer().getPort();
    }

    /**
     * 通过key取对应的redis数据
     *
     * @param key
     * @param toke
     * @return
     */
    public String getRedisData(String key, String toke) {
        String str = redisTemplate.opsForValue().get(key + toke);
        if (!"".equals(str) && str != null) {
            return str;
        }
        return "";
    }

    /**
     * 获取默认token
     */
    public String getRedisDefaultToken() {
        Set<String> keySet = redisTemplate.keys(CitmsAppConstant.DEFAULTTOKEN + "*");
        if (CollectionUtils.isNotEmpty(keySet)) {
            for (String key : keySet) {
                return key.replaceAll(CitmsAppConstant.DEFAULTTOKEN, "");
            }
        }
        return "";
    }

    /**
     * 通过key获取字典项（车）value数据
     *
     * @return
     */
    public String getDictValue(JSONObject respJsonObj, String kind, String key) {
        String dictionaryValue = "";
        if (ObjectUtil.isNotNull(respJsonObj)) {
            JSONObject resultJson = respJsonObj.getJSONObject("result");
            if (ObjectUtil.isNotNull(resultJson)) {
                JSONArray jsonArray = resultJson.getJSONArray(kind);
                if (ObjectUtil.isNotNull(jsonArray)) {
                    for (int k = 0; k < jsonArray.size(); k++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(k);
                        if (ObjectUtil.isNotNull(jsonObject)) {
                            if (key.equals(jsonObject.getString("dictionaryNo"))) {
                                dictionaryValue = jsonObject.getString("dictionaryValue");
                            }
                        }
                    }
                }
            }
        }
        return dictionaryValue;
    }

    /**
     * 通过value获取字典项（车）key数据
     *
     * @return
     */
    public String getDictKey(JSONObject respJsonObj, String kind, String value) {
        String dictionaryNo = "";
        if (ObjectUtil.isNotNull(respJsonObj)) {
            JSONObject resultJson = respJsonObj.getJSONObject("result");
            if (ObjectUtil.isNotNull(resultJson)) {
                JSONArray jsonArray = resultJson.getJSONArray(kind);
                for (int k = 0; k < jsonArray.size(); k++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(k);
                    if (ObjectUtil.isNotNull(jsonObject)) {
                        if (value.equals(jsonObject.getString("dictionaryValue"))) {
                            dictionaryNo = jsonObject.getString("dictionaryNo");
                        }
                    }
                }
            }
        }
        return dictionaryNo;
    }

    /**
     * 获取数据字典车辆相关基础信息
     *
     * @return
     */
    public JSONObject getDictRequestData() {
        String url = this.getServerAddress() + "/smw/Dict/GetSomeDicts";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Basic " + this.getRedisDefaultToken());
        Integer[] data = new Integer[DictEnum.values().length];
        int i = 0;
        JSONObject respJsonObj = null;
        if (data.length > 0) {
            for (DictEnum dictEnum : DictEnum.values()) {
                i++;
                String s = dictEnum.getIndex();
                data[i - 1] = Integer.valueOf(s);
            }
            // 将请求头和请求参数设置到HttpEntity中
            HttpEntity httpEntity = new HttpEntity(data, headers);
            // 发送请求
            respJsonObj = restTemplate.postForObject(url, httpEntity, JSONObject.class);
            //log.info("查询字典项中结果：" + respJsonObj.toJSONString());

        }
        return respJsonObj;
    }

    /**
     * 获取指定字典类型指定字典编号的字典详情数据
     *
     * @param kind         字典类型编号
     * @param dictionaryNo 字典项编号
     * @return
     */
    public String getDictByKindAndNo(int kind, String dictionaryNo) {
        String dictionaryName = "";
        Map<String, Object> params = new HashMap<>();
        params.put("kind", kind);
        params.put("dictionaryNo", dictionaryNo);
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Basic " + this.getRedisDefaultToken());
        String resp = okHttpCli.doGet(this.getServerAddress() + "/smw/Dict/GetDictByKindAndNo", params, headers);
        if (StringUtils.isBlank(resp)) {
            return dictionaryName;
        }
        JSONObject respJson = JSON.parseObject(resp);
        if (respJson == null) {
            return dictionaryName;
        }
        int code = respJson.getInteger("code");
        if (code == 0) {
            JSONObject result = respJson.getJSONObject("result");
            if (result != null && StringUtils.isNotBlank(result.getString("dictionaryValue"))) {
                dictionaryName = result.getString("dictionaryValue");
            }
        }
        return dictionaryName;
    }


    /**
     * 获取多个字典类型对应字典值
     *
     * @param listKind 字典类型编号
     * @return
     */
    public JSONObject getSomeDicts(List<Object> listKind) {
        if (CollectionUtils.isEmpty(listKind)) {
            return null;
        }
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Basic " + this.getRedisDefaultToken());
        String resp = okHttpCli.doPostJson(this.getServerAddress() + "/smw/Dict/GetSomeDicts", JSON.toJSONString(listKind), headers);
        if (StringUtils.isBlank(resp)) {
            return null;
        }
        JSONObject respJson = JSON.parseObject(resp);
        if (respJson == null) {
            return null;
        }
        int code = respJson.getInteger("code");
        JSONObject resultJson = null;
        if (code == 0) {
            resultJson = respJson.getJSONObject("result");
        }
        return resultJson;
    }

    /**
     * 获取大公安接口设备数量及总数及离在线情况
     */
    public String getDeviceOnlineData() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Basic " + this.getRedisDefaultToken());
        String resp = okHttpCli.doGet(this.getServerAddress() + "/bdm/Device/GetDeviceOnlineSTAsync", null, headers);
        if (StringUtils.isBlank(resp)) {
            return null;
        }
        JSONObject respJson = JSON.parseObject(resp);
        if (respJson == null) {
            return null;
        }
        int code = respJson.getInteger("code");
        String resultJson = "";
        if (code == 0) {
            resultJson = respJson.getString("result");
        }
        return resultJson;
    }

    /**
     * 获取相关编号设备信息
     */
    public Map<String, DeviceModel> getSomeDeviceByNo(List<String> deviceNos) {
        String url = this.getServerAddress() + "/bdm/Device/GetSomeDeviceByNo";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Basic " + this.getRedisDefaultToken());
        // 将请求头和请求参数设置到HttpEntity中
        HttpEntity httpEntity = new HttpEntity(deviceNos, headers);
        // 发送请求
        JSONObject respJsonObj = restTemplate.postForObject(url, httpEntity, JSONObject.class);
        Map<String, DeviceModel> map = new HashMap<>(5);
        DeviceModel deviceModel = null;
        BigDecimal b1 = null;
        BigDecimal b2 = null;
        if (ObjectUtil.isNotNull(respJsonObj)) {
            JSONArray resultJson = respJsonObj.getJSONArray("result");
            if (ObjectUtil.isNotNull(resultJson)) {
                for (int i = 0; i < resultJson.size(); i++) {
                    JSONObject jsonObject = resultJson.getJSONObject(i);
                    String deviceNo = jsonObject.getString("deviceNo");
                    String deviceName = jsonObject.getString("deviceName");
                    if (jsonObject.getString("longitude") != null) {
                        String longitude = jsonObject.getString("longitude");
                        b2 = new BigDecimal(longitude);

                    }
                    if (jsonObject.getString("latitude") != null) {
                        String latitude = jsonObject.getString("latitude");
                        b1 = new BigDecimal(latitude);

                    }
                    deviceModel = new DeviceModel();
                    deviceModel.setDeviceName(deviceName);
                    deviceModel.setDeviceNo(deviceNo);
                    deviceModel.setLatitude(b1);
                    deviceModel.setLongitude(b2);
                    map.put(deviceNo, deviceModel);
                }
            }
        }
        return map;
    }

    /**
     * 获取中台字典转换后的数据
     */
/*    public String getConvertDictData(JSONObject jsonObject, String fieldCode, String dictValue) {
        if (ObjectUtil.isNotNull(jsonObject)) {
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int g = 0; g < jsonArray.size(); g++) {
                JSONObject JSONObject = jsonArray.getJSONObject(g);
                JSONArray jsonArray3 = JSONObject.getJSONArray("fields");
                for (int j = 0; j < jsonArray3.size(); j++) {
                    JSONObject jsonObject3 = jsonArray3.getJSONObject(j);
                    String fieldCodeValue = jsonObject3.getString("fieldCode");
                    if (fieldCode.equals(fieldCodeValue)) {
                        JSONArray dictArray = jsonObject3.getJSONArray("dict");
                        if (ObjectUtil.isNotNull(dictArray)) {
                            for (int k = 0; k < dictArray.size(); k++) {
                                JSONObject jsonObject4 = dictArray.getJSONObject(k);
                                String dictKey = jsonObject4.getString("dictKey");
                                String value = jsonObject4.getString("dictValue");
                                if (dictValue.equals(value)) {
                                    return dictKey;
                                }
                            }
                        }
                    }
                }
            }
        }
        return "";
    }*/

    /**
     * 获取中台厂商数据
     */
    public JSONArray searchManufacturerData(String serverAddress) {
        String resp = okHttpCli.doGet(serverAddress + CommonConstant.MANUFACTURER_URL);
        if (StringUtils.isBlank(resp)) {
            return null;
        }
        JSONObject respJson = JSON.parseObject(resp);
        Integer code = respJson.getInteger("code");
        Boolean success = respJson.getBoolean("success");

        if (!code.equals(200) || !success) {
            return null;
        }

        JSONArray dataJson = respJson.getJSONArray("data");
        return dataJson;
    }


    /**
     * 通用查询接口（中台）
     *
     * @param serverAddress 中台服务地址
     * @param paramsJsonStr json字符串参数
     * @return
     */
    public JSONObject commonSearch(String serverAddress, String paramsJsonStr) {
        log.info("通用查询，中台地址：{}；\n参数：{}", serverAddress, paramsJsonStr);
        String resp = okHttpCli.doPostJson(serverAddress + CommonConstant.COMMON_SEARCH_URL, paramsJsonStr);
        if (StringUtils.isBlank(resp)) {
            return null;
        }

        JSONObject respJson = JSON.parseObject(resp);
        Integer code = respJson.getInteger("code");
        Boolean success = respJson.getBoolean("success");

        if (!code.equals(200) || !success) {
            return null;
        }

        JSONObject dataJson = respJson.getJSONObject("data");
        return dataJson;
    }

}



