package com.jpp.web.util.HttpUtil;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtil {
    
   private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);
   
   private RequestForm reqForm;
   private Map<String, Object> reqParams;
   private CustomHttpUrlConnection httpUrlConnection;
   
   public HttpUtil(RequestForm reqForm, Map<String, Object> reqParams) {
      this.reqForm = reqForm;
      this.reqParams = reqParams;
      httpUrlConnection = new CustomHttpUrlConnection(reqForm, reqParams);
   }
   
   public String requestApi() {
      printBeforeLog();
      String resParams = httpUrlConnection.requestApi();
      printAfterLog(resParams);
      return resParams;
   }
   
   public String requestApiWithJsonForm() {
      printBeforeLog();
      String resParams = httpUrlConnection.requestApiWithJsonForm();
      printAfterLog(resParams);
      return resParams;
   }
   
   private void printBeforeLog() {
      logger.info("HTTPCONNECTION.REQUEST|URL:{}|IN_PARAMS:{}"
            , reqForm.getUrl(), reqParams);
   }
   
   private void printAfterLog(String resParams) {
      logger.info("HTTPCONNECTION.RESPONSE|URL:{}|TIME:{}|STATUS_CODE:{}|IN_PARAMS:{}|OUT_PARAMS:{}"
            , reqForm.getUrl()
            , System.currentTimeMillis() - httpUrlConnection.getStartTime()
            , httpUrlConnection.getResponseCode()<1?"":httpUrlConnection.getResponseCode()
            , reqParams
            , resParams);
   }
   
}
