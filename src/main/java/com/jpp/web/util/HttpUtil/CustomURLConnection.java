package com.jpp.web.util.HttpUtil;

import java.util.Map;

@SuppressWarnings("unused")
public class CustomURLConnection {
   
   private final int tryCount;
   private final int connectionTimeOut;
   private final int readTimeOut;
   private final String httpMethod;
   private final String url;
   private final Map<String, Object> header;
   
   public static class Builder {
      private String httpMethod; //required
      private String url;        //required
      private int tryCount = 1;  //optional
      private int connectionTimeOut = 1000;  //optional
      private int readTimeOut = 1000;     //optional
      private Map<String, Object> header; //optional
      
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
      
      public CustomURLConnection build() {
         return new CustomURLConnection(this);
      }
   }
   
   public CustomURLConnection(Builder builder) {
      httpMethod = builder.httpMethod;
      url = builder.url;
      tryCount = builder.tryCount;
      connectionTimeOut = builder.connectionTimeOut;
      readTimeOut = builder.readTimeOut;
      header = builder.header;
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
   
}
