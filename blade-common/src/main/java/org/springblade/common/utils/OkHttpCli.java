package org.springblade.common.utils;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springblade.common.enums.ApiEnum;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class OkHttpCli {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType XML = MediaType.parse("application/xml; charset=utf-8");

    private OkHttpClient okHttpClient;

    public OkHttpCli() {
        okHttpClient = new OkHttpClient().newBuilder()
                .sslSocketFactory(SSLSocketClient.getSSLSocketFactory())
                .hostnameVerifier(SSLSocketClient.getHostnameVerifier())
                .build();
    }

    /**
     * get 请求
     *
     * @param url 请求url地址
     * @return string
     */
    public String doGet(String url) {
        return doGet(url, null, null);
    }

    /**
     * get 请求
     *
     * @param url    请求url地址
     * @param params 请求参数 map
     * @return string
     */
    public String doGet(String url, Map<String, Object> params) {
        return doGet(url, params, null);
    }

    /**
     * get 请求
     *
     * @param url     请求url地址
     * @param params  请求参数 map
     * @param headers 请求头集合
     * @return string
     */
    public String doGet(String url, Map<String, Object> params, Map<String, String> headers) {
        Request request = getUrlParamRequest(url, params, headers, "get");
        return execute(request);
    }

    /**
     * delete 请求
     *
     * @param url     请求url地址
     * @param params  请求参数 map
     * @param headers 请求头集合
     * @return string
     */
    public String doDelete(String url, Map<String, Object> params, Map<String, String> headers) {
        Request request = getUrlParamRequest(url, params, headers, "delete");
        return execute(request);
    }

    /**
     * 获取URL传参请求体对象
     *
     * @param url         请求地址
     * @param params      请求参数集合
     * @param headers     请求头集合
     * @param requestType 请求类型：Get Delete
     * @return
     */
    private Request getUrlParamRequest(String url, Map<String, Object> params, Map<String, String> headers, String requestType) {
        StringBuilder sb = new StringBuilder(url);
        if (params != null && params.keySet().size() > 0) {
            boolean firstFlag = true;
            for (String key : params.keySet()) {
                if (params.get(key) == null) {
                    continue;
                }
                String param = String.valueOf(params.get(key));
                if (firstFlag) {
                    sb.append("?").append(key).append("=").append(param);
                    firstFlag = false;
                } else {
                    sb.append("&").append(key).append("=").append(param);
                }
            }
        }

        Request.Builder builder = new Request.Builder();
        if (headers != null && headers.size() > 0) {
            for (String key : headers.keySet()) {
                builder.addHeader(key, headers.get(key));
            }
        }
        log.info("do get request and url[{}]", sb.toString());
        if ("delete".equals(requestType.toLowerCase())) {
            return builder.url(sb.toString()).delete().build();
        } else {
            return builder.url(sb.toString()).get().build();
        }
    }

    /**
     * post 请求
     *
     * @param url    请求url地址
     * @param params 请求参数 map
     * @return string
     */
    public String doPost(String url, Map<String, String> params) {
        FormBody.Builder builder = new FormBody.Builder();

        if (params != null && params.keySet().size() > 0) {
            for (String key : params.keySet()) {
                builder.add(key, params.get(key));
            }
        }
        Request request = new Request.Builder().url(url).post(builder.build()).build();
        log.info("do post request and url[{}]", url);

        return execute(request);
    }

    /**
     * post 请求
     *
     * @param url     请求url地址
     * @param params  请求参数 map
     * @param headers 请求头集合
     * @return string
     */
    public String doPost(String url, Map<String, String> params, Map<String, String> headers) {
        FormBody.Builder builder = new FormBody.Builder();

        if (params != null && params.keySet().size() > 0) {
            for (String key : params.keySet()) {
                builder.add(key, params.get(key));
            }
        }

        Request.Builder requestBuilder = new Request.Builder();
        if (headers != null && headers.size() > 0) {
            for (String key : headers.keySet()) {
                requestBuilder.addHeader(key, headers.get(key));
            }
        }

        Request request = requestBuilder.url(url).post(builder.build()).build();
        log.info("do post request and url[{}]", url);

        return execute(request);
    }

    /**
     * post 请求（返回Response）
     *
     * @param url     请求url地址
     * @param params  请求参数 map
     * @param headers 请求头集合
     * @return string
     */
    public Response doPostResp(String url, Map<String, String> params, Map<String, String> headers) {
        FormBody.Builder builder = new FormBody.Builder();

        if (params != null && params.keySet().size() > 0) {
            for (String key : params.keySet()) {
                builder.add(key, params.get(key));
            }
        }

        if (headers != null && headers.size() > 0) {
            for (String key : headers.keySet()) {
                builder.add(key, headers.get(key));
            }
        }

        Request request = new Request.Builder().url(url).post(builder.build()).build();
        log.info("do post request and url[{}]", url);

        Response response = null;
        try {

            response = okHttpClient.newCall(request).execute();
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
        }
        return response;
    }

    /**
     * get 请求（返回Response）
     *
     * @param url     请求url地址
     * @param params  请求参数 map
     * @param headers 请求头集合
     * @return string
     */
    public Response doGetResp(String url, Map<String, Object> params, Map<String, String> headers) {
        Request request = getUrlParamRequest(url, params, headers, "get");
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
        }
        return response;
    }

    /**
     * 上传文件
     *
     * @param url     请求url地址
     * @param params  上传文件参数
     * @param params  普通请求参数
     * @param headers 请求头
     * @return
     */
    public String doMultipartPost(String url, Map<String, byte[]> files, Map<String, String> params, Map<String, String> headers) {
        MultipartBody.Builder paramBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (files != null && files.keySet().size() > 0) {
            for (String key : files.keySet()) {
                paramBuilder.addFormDataPart(key, key + ".jpg",
                        RequestBody.create(MediaType.parse("multipart/form-data"), params.get(key)));
            }
        }
        if (params != null && params.keySet().size() > 0) {
            for (String key : params.keySet()) {
                paramBuilder.addFormDataPart(key, params.get(key));
            }
        }
        Request.Builder builder = new Request.Builder();
        if (headers != null && headers.size() > 0) {
            for (String key : headers.keySet()) {
                builder.addHeader(key, headers.get(key));
            }
        }
        Request request = builder.url(url).post(paramBuilder.build()).build();
        return execute(request);
    }

    /**
     * post 请求, 请求数据为 json 的字符串
     *
     * @param url  请求url地址
     * @param json 请求数据, json 字符串
     * @return string
     */
    public String doPostJson(String url, String json) {
        log.info("do post request and url[{}]", url);
        return executePost(url, json, JSON, null);
    }

    /**
     * post 请求, 请求数据为 json 的字符串
     *
     * @param url     请求url地址
     * @param json    请求数据, json 字符串
     * @param headers 请求头集合
     * @return string
     */
    public String doPostJson(String url, String json, Map<String, String> headers) {
        log.info("do post request and url[{}]", url);
        return executePost(url, json, JSON, headers);
    }

    /**
     * put 请求, 请求数据为 json 的字符串
     *
     * @param url     请求url地址
     * @param json    请求数据, json 字符串
     * @param headers 请求头集合
     * @return string
     */
    public String doPutJson(String url, String json, Map<String, String> headers) {
        log.info("do post request and url[{}]", url);
        return executePut(url, json, JSON, headers);
    }

    /**
     * post 请求, 请求数据为 xml 的字符串
     *
     * @param url 请求url地址
     * @param xml 请求数据, xml 字符串
     * @return string
     */
    public String doPostXml(String url, String xml) {
        log.info("do post request and url[{}]", url);
        return executePost(url, xml, XML, null);
    }

    /**
     * post 请求, 请求数据为 xml 的字符串
     *
     * @param url     请求url地址
     * @param xml     请求数据, xml 字符串
     * @param headers 请求头集合
     * @return string
     */
    public String doPostXml(String url, String xml, Map<String, String> headers) {
        log.info("do post request and url[{}]", url);
        return executePost(url, xml, XML, headers);
    }

    private String executePost(String url, String data, MediaType contentType, Map<String, String> headers) {
        RequestBody requestBody = RequestBody.create(contentType, data);
        Request.Builder buider = new Request.Builder();
        if (headers != null && headers.size() > 0) {
            for (String key : headers.keySet()) {
                buider.addHeader(key, headers.get(key));
            }
        }
        Request request = buider.url(url).post(requestBody).build();
        return execute(request);
    }

    private String executePut(String url, String data, MediaType contentType, Map<String, String> headers) {
        RequestBody requestBody = RequestBody.create(contentType, data);
        Request.Builder buider = new Request.Builder();
        if (headers != null && headers.size() > 0) {
            for (String key : headers.keySet()) {
                buider.addHeader(key, headers.get(key));
            }
        }
        Request request = buider.url(url).put(requestBody).build();
        return execute(request);
    }

    private String execute(Request request) {
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return null;
    }


    /**
     * get 请求（返回Response）
     *
     * @param url     请求url地址
     * @param params  请求参数 map
     * @param headers 请求头集合
     * @return string
     */
    public Response doGetRespData(String url, String manufacturer, Map<String, Object> params, Map<String, String> headers) {
        Request request = getUrlParamRequest(url, params, headers, "get");
        Response response = null;
        try {
            if (ApiEnum.VCM.getIndex().equals(manufacturer)) {
                // 华为的使用忽略SSL验证
                OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                        .sslSocketFactory(SSLSocketClient.getSSLSocketFactory())
                        .hostnameVerifier(SSLSocketClient.getHostnameVerifier())
                        .build();
                response = okHttpClient.newCall(request).execute();
            } else {
                response = okHttpClient.newCall(request).execute();
            }
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
        }
        return response;
    }
}
