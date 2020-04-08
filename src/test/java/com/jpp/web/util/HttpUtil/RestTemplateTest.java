package com.jpp.web.util.HttpUtil;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RestTemplateTest {
   
   private static final Logger logger = LoggerFactory.getLogger(RestTemplateTest.class);
   
   
   private RestTemplate restTemplate = new RestTemplate();
   
   @Test
   public void test() {
      
      String url = "http://localhost:18082/bopr/sim/ReExecSchdul.do";
      
      Map<String, String> param = new HashMap<>();
      param.put("seq", "132");
      ResponseEntity rs =  restTemplate.postForEntity(url, param, Map.class);
      
      logger.info("rs code : "+rs.getStatusCode());
      logger.info("rs body : " + rs.getBody().toString());
   }
   
   
}
