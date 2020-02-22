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

public class CustomHttpUrlConnection {
   
   private static final Logger logger = LoggerFactory.getLogger(CustomHttpUrlConnection.class);
   private static final String UTF_8 = "UTF-8";

   private int responseCode = 0;
   private long startTime = 0;

   private RequestForm reqForm;
   private Map<String, Object> reqParams;
   
   public CustomHttpUrlConnection(RequestForm reqForm, Map<String, Object> reqParams){
      this.reqForm = reqForm;
      this.reqParams = reqParams;
   }
   
   public int getResponseCode() {
      return responseCode;
   }
   
   public long getStartTime() {
      return startTime;
   }
   
   public String requestApi() {
      
      this.startTime = System.currentTimeMillis();
      
      String httpMethod = reqForm.getHttpMethod();  
      String surl = reqForm.getUrl();               
      int tryCnt = reqForm.getTryCount();   
      int readTimeOut = reqForm.getReadTimeOut(); 
      int connectionTimeOut = reqForm.getConnectionTimeOut();
      Map<String, Object> header = reqForm.getHeader();
      int expectedHttpStatus = reqForm.getHttpStatus();
      String requestCharset = reqForm.getRequestCharset();
      String responseCharset = reqForm.getResponseCharset();
      
      String reqCharset = requestCharset==null||requestCharset.isEmpty()?UTF_8:requestCharset;
      String resCharset = responseCharset==null||responseCharset.isEmpty()?UTF_8:responseCharset;
             
      URL url = null;
      HttpURLConnection conn = null;
      BufferedReader br = null;
      JSONObject jobj = null;
      String postParams = "";
      String errMsg = "";
      String returnText = "";

      
      for(int i=0; i < tryCnt; i++){
          
         try {
             
            if(httpMethod.equalsIgnoreCase(Constants.POST) || httpMethod.equalsIgnoreCase(Constants.DELETE)){
               url = new URL(surl);
            } else if(httpMethod.equalsIgnoreCase(Constants.GET)){
               url = new URL(surl + ((reqParams!=null)?"?"+makeUrlEncodedParams(reqParams, reqCharset):""));
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
               if(reqParams != null){
                  postParams = makeUrlEncodedParams(reqParams, reqCharset);
                  conn.getOutputStream().write(postParams.getBytes(UTF_8));
                  conn.getOutputStream().flush();
               }
            }
            
            this.responseCode = conn.getResponseCode();
            
            if(expectedHttpStatus > 0){
               if(expectedHttpStatus!=conn.getResponseCode()){
                  throw new CustomException("successCode : {" + expectedHttpStatus + "}" + " , responseCode : {" + this.responseCode + "}", ConstantsEnum.API_RESULT.E_NETWORK.getCode());
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
            } catch (JSONException e){
               throw new CustomException();
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
      
      if(jobj!=null){
         return jobj.toString();
      } else {
         throw new CustomException(errMsg, ConstantsEnum.API_RESULT.E_NETWORK.getCode());
      }
   }
   
   
   public String requestApiWithJsonForm() {
      
      this.startTime = System.currentTimeMillis();
      
      String httpMethod = reqForm.getHttpMethod();
      int tryCnt = reqForm.getTryCount();
      int readTimeOut = reqForm.getReadTimeOut();
      int connectionTimeOut = reqForm.getConnectionTimeOut();
      String surl = reqForm.getUrl();
      Map<String, Object> header = reqForm.getHeader();
      int expectedHttpStatus = reqForm.getHttpStatus();
      String responseCharset = reqForm.getResponseCharset();
      
      String resCharset = responseCharset==null||responseCharset.isEmpty()?UTF_8:responseCharset;
      
      URL url = null;
      HttpURLConnection conn = null;
      BufferedReader br = null;
      JSONObject jobj = null;
      String postParams = "";
      String errMsg = "";
      String returnText = "";
       
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
            
            if(reqParams != null){
                postParams = makeJsonParams(reqParams);
                conn.getOutputStream().write(postParams.getBytes(UTF_8));
                conn.getOutputStream().flush();
            }
            
            this.responseCode = conn.getResponseCode();
            
            if(expectedHttpStatus != 0){
               if(expectedHttpStatus!=conn.getResponseCode()){
                  throw new CustomException("successCode : {" + expectedHttpStatus + "}" + " , responseCode : {" + this.responseCode + "}", ConstantsEnum.API_RESULT.E_NETWORK.getCode());
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
            } catch (JSONException e){
               throw new CustomException();
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
