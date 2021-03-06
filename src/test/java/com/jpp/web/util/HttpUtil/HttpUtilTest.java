package com.jpp.web.util.HttpUtil;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HttpUtilTest {
   
   private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);
   
   @Test
   public void test() {
      RequestForm reqForm = new RequestForm.Builder("GET", "127.0.0.1:8082/mobile/device")
            .setConnectionTimeOut(1000)
            .setReadTimeOut(1000)
            .setTryCount(3)
            .build();
      
      Map<String, Object> reqParams = new HashMap<String, Object>();
      reqParams.put("deviceType", "1");
      reqParams.put("osVersion", "10");
      
      String apiResult = new HttpUtil(reqForm, reqParams).requestApiWithJsonForm();
      logger.info(apiResult);
   }

   @Test
   public void test2() {
      RequestForm reqForm = new RequestForm.Builder("GET", "127.0.0.1:8082/mobile/version")
            .setConnectionTimeOut(1000)
            .setReadTimeOut(1000)
            .setTryCount(3)
            .build();
      
      Map<String, Object> reqParams = new HashMap<String, Object>();
      
      String rs = new HttpUtil(reqForm, reqParams).requestApi();
      logger.info(rs);
   }
}
