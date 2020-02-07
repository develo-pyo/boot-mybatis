package com.jpp.web.util.HttpUtil;

import java.util.HashMap;
import java.util.Map;

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
      
      CustomURLConnection uc = new CustomURLConnection.Builder("GET", "127.0.0.1/mobile/checkdeviceinfo")
            .setConnectionTimeOut(1000)
            .setReadTimeOut(1000)
            .setTryCount(3)
            .build();
      
      Map<String, Object> params = new HashMap<String, Object>();
      params.put("deviceType", "1");
      params.put("osVersion", "10");
      String apiResult = new HttpUtil().requestApiWithJsonForm(uc, params);
      
      logger.info(apiResult);
   }

}
