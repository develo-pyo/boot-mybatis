package com.jpp.web.util.HttpUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.jpp.web.comm.CustomException;
import com.jpp.web.constants.Constants;
import com.jpp.web.constants.ConstantsEnum;

public class HttpUtil {
    
   private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);
   
   public String requestApi(URLConnection uc, Map<String, Object> params, int expectedHttpStatus, String requestCharset, String responseCharset) {
      
      Long start = System.currentTimeMillis();
      
      String httpMethod = uc.getHttpMethod();
      int tryCnt = uc.getTryCount();
      int readTimeOut = uc.getReadTimeOut();
      int connectionTimeOut = uc.getConnectionTimeOut();
      String surl = uc.getUrl();
      Map<String, Object> header = uc.getHeader();

      logger.info("HTTPCONNECTION.REQUEST | URL = {} | IN_PARAMS = {}", surl, params);
      
      String reqCharset = requestCharset==null||requestCharset.isEmpty()?"UTF-8":requestCharset;
      String resCharset = responseCharset==null||responseCharset.isEmpty()?"UTF-8":responseCharset;
             
      URL url = null;
      HttpURLConnection conn = null;
      BufferedReader br = null;
      JSONObject jobj = null;
      String postParams = "";
      String errMsg = "";
      String returnText = "";
      int repCode = 0;
      
       
      for(int i=0; i < (tryCnt<1?1:tryCnt); i++){
          
         try {
             
            if(httpMethod.equalsIgnoreCase(Constants.POST) || httpMethod.equalsIgnoreCase(Constants.DELETE)){
               url = new URL(surl);
            } else if(httpMethod.equalsIgnoreCase(Constants.GET)){
               url = new URL(surl + ((params!=null)?"?"+makeUrlEncodedParams(params, reqCharset):""));
            }
            
            conn = (HttpURLConnection) url.openConnection();
            
            if(header != null){
                conn.setRequestProperty(header.get("headerKey").toString(), header.get("headerValue").toString());
            }
             
            conn.setRequestMethod(httpMethod);
            conn.setConnectTimeout(connectionTimeOut);
            conn.setReadTimeout(readTimeOut);
            conn.setDoOutput(true);
            
            if(httpMethod.equalsIgnoreCase(Constants.POST) || httpMethod.equalsIgnoreCase(Constants.DELETE)){
               if(params != null){
                  postParams = makeUrlEncodedParams(params, reqCharset);
                  conn.getOutputStream().write(postParams.getBytes("UTF-8"));
                  conn.getOutputStream().flush();
               }
            }
            
            repCode = conn.getResponseCode();
            
            if(expectedHttpStatus > 0){
               if(expectedHttpStatus!=conn.getResponseCode()){
                  throw new CustomException("successCode : {" + expectedHttpStatus + "}" + " , responseCode : {" + repCode + "}", ConstantsEnum.API_RESULT.E_NETWORK.getCode());
               }
            }
            
            br = new BufferedReader(new InputStreamReader(conn.getInputStream(),resCharset));
            
            StringBuffer sb = null;
            sb = new StringBuffer();
            
            String jsonData = "";
            while((jsonData = br.readLine()) != null){
               sb.append(jsonData);
            }
            returnText = sb.toString();
            
            try{
               jobj = new JSONObject(returnText);
               if( ! jobj.has("responseCode") ){
                  jobj.put("responseCode", conn.getResponseCode());
               }
            } catch (JSONException e){
               jobj = new JSONObject();
               jobj.put("responseCode", conn.getResponseCode());
            }
            
            break;
            
         } catch (SocketTimeoutException se){
            logger.error("connection fail : " + se);
             errMsg = se.getMessage();
         } catch (CustomException e){
            logger.error("response fail : " + e);
             errMsg = e.getMessage();
         } catch (Exception e){
            throw new CustomException(e.getMessage().toString(), ConstantsEnum.API_RESULT.E_NETWORK.getCode());
         } finally {
            try {
               if (br != null) br.close();
            } catch(Exception e){
               logger.warn("finally..br.close()", e);
            }
            br = null;
            try {
               if(conn!=null) {
                  conn.disconnect();
               }
            } catch(Exception e){
               logger.warn("finally..conn.disconnect()", e);
            }
            conn = null;
         }
      }
      
      logger.info("HTTPCONNECTION.RESPONSE | URL = {} | TIME = {} | STATUS CODE = {} | IN_PARAMS = {} | OUT_PARAMS = {}", surl, System.currentTimeMillis() - start, repCode==-1?"":repCode, params, returnText);
      
      if(jobj!=null){
         return jobj.toString();
      } else {
         throw new CustomException(errMsg, ConstantsEnum.API_RESULT.E_NETWORK.getCode());
      }
   }
   
   
   public String requestApiWithJsonForm(URLConnection uc, Map<String, Object> params, int expectedHttpStatus, String responseCharset) {
      
      Long start = System.currentTimeMillis();
      
      String httpMethod = uc.getHttpMethod();
      int tryCnt = uc.getTryCount();
      int readTimeOut = uc.getReadTimeOut();
      int connectionTimeOut = uc.getConnectionTimeOut();
      String surl = uc.getUrl();
      Map<String, Object> header = uc.getHeader();

      logger.info("HTTPCONNECTION.REQUEST | URL = {} | IN_PARAMS = {}", surl, params);
      
      String resCharset = responseCharset==null||responseCharset.isEmpty()?"UTF-8":responseCharset;
      
      URL url = null;
      HttpURLConnection conn = null;
      BufferedReader br = null;
      JSONObject jobj = null;
      String postParams = "";
      String errMsg = "";
      String returnText = "";
      int repCode = 0;
      
       
      for(int i=0; i < (tryCnt<1?1:tryCnt); i++){
          
         try {
             
            url = new URL(surl);
            
            conn = (HttpURLConnection) url.openConnection();
            
            if(header != null){
                conn.setRequestProperty(header.get("headerKey").toString(), header.get("headerValue").toString());
            }
             
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod(httpMethod);
            conn.setConnectTimeout(connectionTimeOut);
            conn.setReadTimeout(readTimeOut);
            conn.setDoOutput(true);
            
            if(params != null){
                postParams = makeJsonParams(params);
                conn.getOutputStream().write(postParams.getBytes("UTF-8"));
                conn.getOutputStream().flush();
            }
            
            repCode = conn.getResponseCode();
            
            if(expectedHttpStatus != 0){
               if(expectedHttpStatus!=conn.getResponseCode()){
                  throw new CustomException("successCode : {" + expectedHttpStatus + "}" + " , responseCode : {" + repCode + "}", ConstantsEnum.API_RESULT.E_NETWORK.getCode());
               }
            }
            
            br = new BufferedReader(new InputStreamReader(conn.getInputStream(), resCharset));
            
            StringBuffer sb = null;
            sb = new StringBuffer();
            
            String jsonData = "";
            while((jsonData = br.readLine()) != null){
               sb.append(jsonData);
            }
            returnText = sb.toString();
            
            try{
               jobj = new JSONObject(returnText);
               if( ! jobj.has("responseCode") ){
                  jobj.put("responseCode", conn.getResponseCode());
               }
            } catch (JSONException e){
               jobj = new JSONObject();
               jobj.put("responseCode", conn.getResponseCode());
            }
            
            break;
            
         } catch (SocketTimeoutException se){
            logger.error("connection fail : " + se);
             errMsg = se.getMessage();
         } catch (CustomException e){
            logger.error("response fail : " + e);
             errMsg = e.getMessage();
         } catch (Exception e){
            throw new CustomException(e.getMessage().toString(), ConstantsEnum.API_RESULT.E_NETWORK.getCode());
         } finally {
            try {
               if (br != null) br.close();
            } catch(Exception e){
               logger.warn("finally..br.close()", e);
            }
            br = null;
            try {
               if(conn!=null) {
                  conn.disconnect();
               }
            } catch(Exception e){
               logger.warn("finally..conn.disconnect()", e);
            }
            conn = null;
         }
      }
      
      logger.info("HTTPCONNECTION.RESPONSE | URL = {} | TIME = {} | STATUS CODE = {} | IN_PARAMS = {} | OUT_PARAMS = {}", surl, System.currentTimeMillis() - start, repCode==-1?"":repCode, params, returnText);
      
      if(jobj!=null){
         return jobj.toString();
      } else {
         throw new CustomException(errMsg, ConstantsEnum.API_RESULT.E_NETWORK.getCode());
      }
   }
    
   
   private String makeUrlEncodedParams(Map<String, Object> params, String charset) throws Exception{
      String param = "";
      StringBuffer sb = new StringBuffer();
      
      if(params != null){
         for ( String key : params.keySet() ){
             try {
                sb.append(key).append("=").append((params.get(key)==null?"":URLEncoder.encode(params.get(key).toString(), charset)).toString().trim()).append("&");
             } catch (UnsupportedEncodingException e) {
                logger.error("ex while encoding : {}", e.getMessage());
                throw e;
             }
         }
         param = sb.toString().substring(0, sb.toString().length()-1);
      }
      
      return param;
   }
  
   
   private String makeJsonParams(Map<String, Object> params){
      String json = "";
      if(params != null){
          json = new Gson().toJson(params);
      }
      return json;
   }
   
}
