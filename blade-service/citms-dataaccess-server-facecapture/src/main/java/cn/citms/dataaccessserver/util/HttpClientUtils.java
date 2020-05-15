package cn.citms.dataaccessserver.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * http请求的工具类
 */
public class HttpClientUtils {

    public static final int TIME_OUT = 5000;

    public static String sendPost(String url,String tokenKey,String token,Part[] parts) throws HttpException, IOException {
        // 创建httpClient实例对象
        HttpClient httpClient = new HttpClient();
        // 设置httpClient连接主机服务器超时时间：5000毫秒
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(TIME_OUT);
        // 创建post请求方法实例对象
        PostMethod postMethod = new PostMethod(url);
        // 设置post请求超时时间
        postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, TIME_OUT);
        postMethod.addRequestHeader("Content-Type", "application/json; charset=UTF-8");
        if(StringUtils.isNotBlank(token)) {
            postMethod.addRequestHeader(tokenKey, token);
        }

        RequestEntity entity = new MultipartRequestEntity(parts,postMethod.getParams());
        postMethod.setRequestEntity(entity);

        httpClient.executeMethod(postMethod);

        String result = postMethod.getResponseBodyAsString();
        postMethod.releaseConnection();
        return result;
    }

    /**
     * @throws IOException
     * @throws IllegalStateException
     *
     * @Title: postForString
     * @Description: post请求
     * @param url soa地址
     * @param jsonParam 请求参数，json字符串
     * @param contractInfo 契约名
     * @return
     * @return String
     * @throws
     */
    public static String postForString(String url,
                                       String jsonParam,
                                       String contractInfo) throws IllegalStateException,
            IOException {
        String result = "";
        if (StringUtils.isEmpty(url)) {
            return result;
        }
        InputStream input = null;//输入流
        InputStreamReader isr = null;
        BufferedReader buffer = null;
        StringBuffer sb = null;
        String line = null;
        try {
            HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
            //HttpClient
            CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
            /*post向服务器请求数据*/
            HttpPost request = new HttpPost(url);
            @SuppressWarnings("deprecation")
            StringEntity se = new StringEntity(jsonParam, HTTP.UTF_8);
            request.setEntity(se);
            if (StringUtils.isNotEmpty(contractInfo)) {
                request.setHeader("ContractInfo", contractInfo);
            }
            request.setHeader("content-Type", "application/json; charset=UTF-8");
            HttpResponse response = closeableHttpClient.execute(request);
            int code = response.getStatusLine().getStatusCode();
            //System.out.println("postCode= " + code);
            // 若状态值为200，则ok
            if (code == HttpStatus.SC_OK) {
                //从服务器获得输入流
                input = response.getEntity().getContent();
                isr = new InputStreamReader(input,"UTF-8");
                buffer = new BufferedReader(isr, 10 * 1024);

                sb = new StringBuffer();
                while ((line = buffer.readLine()) != null) {
                    sb.append(line);
                }
                result = sb.toString();
            }
        } finally {
            try {
                if (buffer != null) {
                    buffer.close();
                    buffer = null;
                }
                if (isr != null) {
                    isr.close();
                    isr = null;
                }
                if (input != null) {
                    input.close();
                    input = null;
                }
            } catch (Exception e) {
            }
        }
        return result;
    }
}
