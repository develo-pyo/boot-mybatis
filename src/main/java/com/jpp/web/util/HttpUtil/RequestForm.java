package com.jpp.web.util.HttpUtil;

import java.util.Map;

public class RequestForm {
   
   private final int tryCount;
   private final int connectionTimeOut;
   private final int readTimeOut;
   private final String httpMethod;
   private final String url;
   private final Map<String, Object> header;
   private final int httpStatus;
   private final String requestCharset;
   private final String responseCharset;
   
   public static class Builder {
      private String httpMethod; //required
      private String url;        //required
      private int tryCount = 1;  //optional
      private int connectionTimeOut = 1000;  //optional
      private int readTimeOut = 1000;     //optional
      private Map<String, Object> header; //optional
      private int httpStatus = 0;    //optional
      private String requestCharset = "UTF-8";
      private String responseCharset = "UTF-8";
      
      public Builder(String httpMethod, String url) {
         this.httpMethod = httpMethod;
         this.url = url;
      }
      
      public Builder setTryCount(int tryCount) {
         this.tryCount = tryCount<1?1:tryCount;
         return this;
      }
      
      public Builder setConnectionTimeOut(int connectionTimeOut) {
         this.connectionTimeOut = connectionTimeOut<100?1000:connectionTimeOut;
         return this;
      }
      
      public Builder setReadTimeOut(int readTimeOut) {
         this.readTimeOut =  readTimeOut<100?1000:readTimeOut;
         return this;
      }
      
      public Builder setHeader(Map<String, Object> header) {
         this.header = header;
         return this;
      }
      
      public Builder setExpectedHttpStatus(int httpStatus) {
         this.httpStatus = httpStatus;
         return this;
      }
      
      public Builder setRequestCharset(String requestCharset) {
         this.requestCharset = requestCharset;
         return this;
      }
      
      public Builder setResponseCharset(String responseCharset) {
         this.responseCharset = responseCharset;
         return this;
      }
      
      public RequestForm build() {
         return new RequestForm(this);
      }
   }
   
   public RequestForm(Builder builder) {
      this.httpMethod = builder.httpMethod;
      this.url = builder.url;
      this.tryCount = builder.tryCount;
      this.connectionTimeOut = builder.connectionTimeOut;
      this.readTimeOut = builder.readTimeOut;
      this.header = builder.header;
      this.httpStatus = builder.httpStatus;
      this.requestCharset = builder.requestCharset;
      this.responseCharset = builder.responseCharset;
   }
   
   public int getTryCount() {
      return tryCount;
   }

   public int getConnectionTimeOut() {
      return connectionTimeOut;
   }

   public int getReadTimeOut() {
      return readTimeOut;
   }

   public String getHttpMethod() {
      return httpMethod;
   }

   public String getUrl() {
      return url;
   }
   
   public Map<String, Object> getHeader(){
      return header;
   }
   
   public int getHttpStatus() {
      return httpStatus;
   }

   public String getRequestCharset() {
      return requestCharset;
   }

   public String getResponseCharset() {
      return responseCharset;
   }
   
}
