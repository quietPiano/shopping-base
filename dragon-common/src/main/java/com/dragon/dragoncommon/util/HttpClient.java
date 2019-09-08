package com.dragon.dragoncommon.util;
/*
 * Author: liuyb@weilaicheng.com
 * Created: 2019-04-07 23:21
 * Copyright (c) 2019, weilaicheng.com All Rights Reserved
 */

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.ThreadContext;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.Map.Entry;

@Slf4j
public class HttpClient {

    // 编码格式。发送编码格式统一用UTF-8
    private final String ENCODING = "UTF-8";

    private int connectTimeout = 5000;

    private int excuteTimeout = 10000;

    public HttpClient(int connectTimeout, int executeTimeout) {
        this.connectTimeout = connectTimeout;
        this.excuteTimeout = executeTimeout;
    }

    /**
     * 构造请求日志
     *
     * @param url
     * @param headers
     * @param params
     * @param result
     * @return
     */
    private String BuildRequestLog(String url, Map<String, String> headers,
                                   Map<String, String> params, String result,
                                   String jsonbody, long cost) {

        StringBuilder builder = new StringBuilder();

        builder.append(" url:").append(url);

        String h = "";
        if (null != headers) {
            h = JSON.toJSONString(headers);
        }
        builder.append(" header:").append(h);

        String p = "";
        if (null != params) {
            p = JSON.toJSONString(params);
        } else if (null != jsonbody) {
            p = jsonbody;
        }

        builder.append(" params:").append(p);

        if (null != result && result.length() > 200) {
            result = result.substring(0, 200);
        }
        builder.append(" result:").append(result);

        if (cost > 0) {
            builder.append(" cost:").append(cost).append("ms");
        }

        return builder.toString();
    }

    /**
     * 发送get请求；带请求头和请求参数
     *
     * @param url     请求地址
     * @param headers 请求头集合
     * @param params  请求参数集合
     * @return
     */
    public String DoGet(String url, Map<String, String> headers, Map<String, String> params) {
        headers = this.packageLogId(headers);
        // 创建httpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        
        // 创建访问的地址
        URIBuilder uriBuilder = null;
        // 创建httpResponse对象
        CloseableHttpResponse httpResponse = null;

        HttpGet httpGet = null;

        try {
            uriBuilder = new URIBuilder(url);
            if (params != null) {
                Set<Entry<String, String>> entrySet = params.entrySet();
                for (Entry<String, String> entry : entrySet) {
                    uriBuilder.setParameter(entry.getKey(), entry.getValue());
                }
            }
            httpGet = new HttpGet(uriBuilder.build());
        } catch (Exception e) {
            log.error("HttpClient DoGet error", e);
            release(httpResponse, httpClient);
            return null;
        }

        /**
         * setConnectTimeout：设置连接超时时间，单位毫秒。
         * setConnectionRequestTimeout：设置从connect Manager(连接池)获取Connection
         * 超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。
         * setSocketTimeout：请求获取数据的超时时间(即响应时间)，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
         */
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(connectTimeout).setSocketTimeout(excuteTimeout).build();
        httpGet.setConfig(requestConfig);

        // 设置请求头
        packageHeader(headers, httpGet);

        String result = null;
        try {
            // 执行请求并获得响应结果
            long start_ts = System.currentTimeMillis();
            result = getString(httpResponse, httpClient, httpGet);
            long cost = System.currentTimeMillis() - start_ts;
            log.info("httprequest success " + BuildRequestLog(url, headers, params, result, null, cost));
        } catch (Exception e) {
            log.error("httprequest error " + BuildRequestLog(url, headers, params, e.getMessage(), null, 0));
        } finally {
            // 释放资源
            release(httpResponse, httpClient);
        }

        return result;
    }

    /**
     * 发送delete请求；带请求头和请求参数
     *
     * @param url     请求地址
     * @param headers 请求头集合
     * @param params  请求参数集合
     * @return
     */
    public String DoDelete(String url, Map<String, String> headers, Map<String, String> params) {
        headers = this.packageLogId(headers);
        // 创建httpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // 创建访问的地址
        URIBuilder uriBuilder = null;
        // 创建httpResponse对象
        CloseableHttpResponse httpResponse = null;

        HttpDelete httpGet = null;

        try {
            uriBuilder = new URIBuilder(url);
            if (params != null) {
                Set<Entry<String, String>> entrySet = params.entrySet();
                for (Entry<String, String> entry : entrySet) {
                    uriBuilder.setParameter(entry.getKey(), entry.getValue());
                }
            }
            httpGet = new HttpDelete(uriBuilder.build());
        } catch (Exception e) {
            log.error("HttpClient DoGet error", e);
            release(httpResponse, httpClient);
            return null;
        }

        /**
         * setConnectTimeout：设置连接超时时间，单位毫秒。
         * setConnectionRequestTimeout：设置从connect Manager(连接池)获取Connection
         * 超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。
         * setSocketTimeout：请求获取数据的超时时间(即响应时间)，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
         */
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(connectTimeout).setSocketTimeout(excuteTimeout).build();
        httpGet.setConfig(requestConfig);

        // 设置请求头
        packageHeader(headers, httpGet);

        String result = null;
        try {
            // 执行请求并获得响应结果
            long start_ts = System.currentTimeMillis();
            result = getString(httpResponse, httpClient, httpGet);
            long cost = System.currentTimeMillis() - start_ts;
            log.info("httprequest success " + BuildRequestLog(url, headers, params, result, null, cost));
        } catch (Exception e) {
            log.error("httprequest error " + BuildRequestLog(url, headers, params, e.getMessage(), null, 0));
        } finally {
            // 释放资源
            release(httpResponse, httpClient);
        }

        return result;
    }


    /**
     * 发送post请求；带请求头和请求参数
     *
     * @param url     请求地址
     * @param headers 请求头集合
     * @param params  请求参数集合
     * @return
     */
    public String DoPostWithForm(String url, Map<String, String> headers, Map<String, String> params) {
        headers = this.packageLogId(headers);
        // 创建httpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // 创建http对象
        HttpPost httpPost = new HttpPost(url);
        /**
         * setConnectTimeout：设置连接超时时间，单位毫秒。
         * setConnectionRequestTimeout：设置从connect Manager(连接池)获取Connection
         * 超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。
         * setSocketTimeout：请求获取数据的超时时间(即响应时间)，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
         */
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(connectTimeout).setSocketTimeout(excuteTimeout).build();
        httpPost.setConfig(requestConfig);

        packageHeader(headers, httpPost);

        // 创建httpResponse对象
        CloseableHttpResponse httpResponse = null;

        // 封装请求参数
        try {
            packageParam(params, httpPost);
        } catch (Exception e) {
            log.error("HttpClient DoPostWithForm error", e);
            release(httpResponse, httpClient);
            return null;
        }


        String result = null;
        try {
            // 执行请求并获得响应结果
            long start_ts = System.currentTimeMillis();
            result = getString(httpResponse, httpClient, httpPost);
            long cost = System.currentTimeMillis() - start_ts;
            log.info("httprequest success " + BuildRequestLog(url, headers, params, result, null, cost));

        } catch (Exception e) {
            log.error("httprequest error " + BuildRequestLog(url, headers, params, e.getMessage(), null, 0));
        } finally {
            // 释放资源
            release(httpResponse, httpClient);
        }
        return result;
    }


    /**
     * 发送put请求；body是form表单 带请求头和请求参数
     *
     * @param url     请求地址
     * @param headers 请求头集合
     * @param params  请求参数集合
     * @return
     */
    public String DoPutWithForm(String url, Map<String, String> headers, Map<String, String> params) {
        headers = this.packageLogId(headers);
        // 创建httpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // 创建http对象
        HttpPut httpPost = new HttpPut(url);
        /**
         * setConnectTimeout：设置连接超时时间，单位毫秒。
         * setConnectionRequestTimeout：设置从connect Manager(连接池)获取Connection
         * 超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。
         * setSocketTimeout：请求获取数据的超时时间(即响应时间)，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
         */
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(connectTimeout).setSocketTimeout(excuteTimeout).build();
        httpPost.setConfig(requestConfig);

        packageHeader(headers, httpPost);

        // 创建httpResponse对象
        CloseableHttpResponse httpResponse = null;

        // 封装请求参数
        try {
            packageParam(params, httpPost);
        } catch (Exception e) {
            log.error("HttpClient DoPostWithForm error", e);
            release(httpResponse, httpClient);
            return null;
        }


        String result = null;
        try {
            // 执行请求并获得响应结果
            long start_ts = System.currentTimeMillis();
            result = getString(httpResponse, httpClient, httpPost);
            long cost = System.currentTimeMillis() - start_ts;
            log.info("httprequest success " + BuildRequestLog(url, headers, params, result, null, cost));

        } catch (Exception e) {
            log.error("httprequest error " + BuildRequestLog(url, headers, params, e.getMessage(), null, 0));
        } finally {
            // 释放资源
            release(httpResponse, httpClient);
        }
        return result;
    }

    /**
     * @param url
     * @param headers
     * @param jsonBody
     * @return
     * @throws Exception
     */
    public String DoPostWithJson(String url, Map<String, String> headers, String jsonBody) {
        headers = this.packageLogId(headers);
        // 创建httpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 创建httpResponse对象
        CloseableHttpResponse httpResponse = null;
        // 创建http对象
        HttpPost httpPost = new HttpPost(url);
        /**
         * setConnectTimeout：设置连接超时时间，单位毫秒。
         * setConnectionRequestTimeout：设置从connect Manager(连接池)获取Connection
         * 超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。
         * setSocketTimeout：请求获取数据的超时时间(即响应时间)，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
         */
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(connectTimeout).setSocketTimeout(excuteTimeout).build();
        httpPost.setConfig(requestConfig);

        if (null == headers) {
            headers = new HashMap<>();
        }

        headers.put("Content-Type", "application/json;charset=utf-8");

        packageHeader(headers, httpPost);

        StringEntity entity = null;
        entity = new StringEntity(jsonBody, ENCODING);

        entity.setContentEncoding(ENCODING);
        httpPost.setEntity(entity);

        String result = null;
        try {
            // 执行请求并获得响应结果
            long start_ts = System.currentTimeMillis();
            result = getString(httpResponse, httpClient, httpPost);
            long cost = System.currentTimeMillis() - start_ts;

            log.info("httprequest success " + BuildRequestLog(url, headers, null, result, jsonBody, cost));

        } catch (Exception e) {
            log.error("httprequest error " + BuildRequestLog(url, headers, null, e.getMessage(), jsonBody, 0));
        } finally {
            // 释放资源
            release(httpResponse, httpClient);
        }
        return result;
    }

    /**
     * @param url
     * @param headers
     * @param jsonBody
     * @return
     * @throws Exception
     */
    public String DoPutWithJson(String url, Map<String, String> headers, String jsonBody) {
        headers = this.packageLogId(headers);
        // 创建httpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 创建httpResponse对象
        CloseableHttpResponse httpResponse = null;
        // 创建http对象
        HttpPut httpPost = new HttpPut(url);
        /**
         * setConnectTimeout：设置连接超时时间，单位毫秒。
         * setConnectionRequestTimeout：设置从connect Manager(连接池)获取Connection
         * 超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。
         * setSocketTimeout：请求获取数据的超时时间(即响应时间)，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
         */
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(connectTimeout).setSocketTimeout(excuteTimeout).build();
        httpPost.setConfig(requestConfig);

        if (null == headers) {
            headers = new HashMap<>();
        }

        headers.put("Content-Type", "application/json;charset=utf-8");

        packageHeader(headers, httpPost);

        StringEntity entity = null;
        entity = new StringEntity(jsonBody, ENCODING);

        entity.setContentEncoding(ENCODING);
        httpPost.setEntity(entity);

        String result = null;
        try {
            // 执行请求并获得响应结果
            long start_ts = System.currentTimeMillis();
            result = getString(httpResponse, httpClient, httpPost);
            long cost = System.currentTimeMillis() - start_ts;

            log.info("httprequest success " + BuildRequestLog(url, headers, null, result, jsonBody, cost));

        } catch (Exception e) {
            log.error("httprequest error " + BuildRequestLog(url, headers, null, e.getMessage(), jsonBody, 0));
        } finally {
            // 释放资源
            release(httpResponse, httpClient);
        }
        return result;
    }

    /**
     * Description: 封装请求头
     *
     * @param params
     * @param httpMethod
     */
    private void packageHeader(Map<String, String> params, HttpRequestBase httpMethod) {
        // 封装请求头
        if (params != null) {
            Set<Entry<String, String>> entrySet = params.entrySet();
            for (Entry<String, String> entry : entrySet) {
                // 设置到请求头到HttpRequestBase对象中
                httpMethod.setHeader(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * Description: 封装请求参数
     *
     * @param params
     * @param httpMethod
     * @throws UnsupportedEncodingException
     */
    private void packageParam(Map<String, String> params, HttpEntityEnclosingRequestBase httpMethod)
            throws UnsupportedEncodingException {
        // 封装请求参数
        if (params != null) {
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            Set<Entry<String, String>> entrySet = params.entrySet();
            for (Entry<String, String> entry : entrySet) {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }

            // 设置到请求的http对象中
            httpMethod.setEntity(new UrlEncodedFormEntity(nvps, ENCODING));
        }
    }

    /**
     * Description: 获得响应结果
     *
     * @param httpResponse
     * @param httpClient
     * @param httpMethod
     * @return
     * @throws Exception
     */
    private String getString(CloseableHttpResponse httpResponse,
                             CloseableHttpClient httpClient,
                             HttpRequestBase httpMethod) throws Exception {
        // 执行请求
        httpResponse = httpClient.execute(httpMethod);

        // 获取返回结果
        if (httpResponse != null && httpResponse.getStatusLine() != null) {
            String content = "";
            if (httpResponse.getEntity() != null) {
                content = EntityUtils.toString(httpResponse.getEntity(), ENCODING);
            }
            return content;
        }
        return "";
    }

    /**
     * Description: 释放资源
     *
     * @param httpResponse
     * @param httpClient
     * @throws IOException
     */
    private void release(CloseableHttpResponse httpResponse, CloseableHttpClient httpClient) {
        // 释放资源
        try {
            if (httpResponse != null) {
                httpResponse.close();
            }
            if (httpClient != null) {
                httpClient.close();
            }
        } catch (Exception e) {
            log.error("HttpClient release error", e);
        }

    }

    /**
     * 将 logid 封装进 header
     *
     * @param header
     * @return
     */
    private Map<String, String> packageLogId(Map<String, String> header) {
        log.info("HttpClient-->packageLogId");
        if (header == null) {
            HashMap<String, String> headers = new HashMap<>();
            headers.put("logid", ThreadContext.get("logId"));
            return headers;
        }
        header.put("logid", ThreadContext.get("logId"));
        return header;
    }
}

