package com.jpp.web.util.HttpUtil;

import java.util.Map;

@SuppressWarnings("unused")
public class URLConnection {
   
   private final int tryCount;
   private final int connectionTimeOut;
   private final int readTimeOut;
   private final String httpMethod;
   private final String url;
   private final Map<String, Object> header;
   
   public static class Builder {
      private String httpMethod;
      private String url;
      private int tryCount = 1;
      private int connectionTimeOut = 1000;
      private int readTimeOut = 1000;
      private Map<String, Object> header;
      
      public Builder(String httpMethod, String url) {
         this.httpMethod = httpMethod;
         this.url = url;
      }
      
      public Builder setTryCount(int tryCount) {
         this.tryCount = tryCount;
         return this;
      }
      
      public Builder setConnectionTimeOut(int connectionTimeOut) {
         this.connectionTimeOut = connectionTimeOut;
         return this;
      }
      
      public Builder setReadTimeOut(int readTimeOut) {
         this.readTimeOut = readTimeOut;
         return this;
      }
      
      public Builder setHeader(Map<String, Object> header) {
         this.header = header;
         return this;
      }
      
      public URLConnection build() {
         return new URLConnection(this);
      }
   }
   
   public URLConnection(Builder builder) {
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
