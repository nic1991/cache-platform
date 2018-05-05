package com.newegg.ec.cache.app.util;

import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.DefaultHttpClient;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by gl49 on 2017/8/16.
 */
public class HttpUtil {
    public static final Log logger = LogFactory.getLog(HttpUtil.class);
    private HttpUtil(){
        //ignore
    }

    public static JSONObject jsonGet(String url) throws Exception {
        String strRes = get( url, false );
        JSONObject result = JSONObject.fromObject( strRes );
        return result;
    }

    public static JSONObject jsonPost(String url, JSONObject reqObj) throws IOException {
        String res = sendPost(url, reqObj);
        JSONObject result = new JSONObject();
        if( StringUtils.isBlank( res ) ){
            return result;
        }
        result = JSONObject.fromObject( res );
        return result;
    }

    /**
     * 向指定URL发送GET方法的请求
     */
    public static String get(String url, boolean flag) throws Exception {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            // 建立实际的连接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                if( flag ){
                    result += line + "\n";
                }else{
                    result += line;
                }
            }
        } catch (Exception e){
            logger.error( e );
            throw e;
        }finally {
            if (in != null) {
                in.close();
            }
        }
        return result;
    }

    public static String delete(String url) throws IOException {
        if (url != null && url.length() != 0) {
            HttpDelete delete = new HttpDelete(url);
            delete.setHeader("Accept", "*/*");
            new DefaultHttpClient().execute(delete);
        }
        return "";
    }

    public static String post(String url) throws IOException {
        return strPost(url, null);
    }

    public static String strPost(String url, String param) throws IOException {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "application/json");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            if( param != null ){
                out.print( param );
            }
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        }catch (Exception e){
            logger.error( e );
            throw e;
        }finally{
            if(out!=null){
                out.close();
            }
            if(in!=null){
                in.close();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     */
    public static String sendPost(String url, JSONObject reqObj) throws IOException {
        return strPost(url, reqObj.toString());
    }
}
