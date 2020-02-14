package com.jpp.main.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.jpp.main.service.MainService;

//https://javaengine.tistory.com/entry/%EC%8A%A4%ED%94%84%EB%A7%81-%EB%B6%80%ED%8A%B8-%ED%85%8C%EC%8A%A4%ED%8A%B8-%EC%9E%91%EC%84%B1-%EA%B0%80%EC%9D%B4%EB%93%9C

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
public class RestApiControllerTest {
   
   @Autowired
   private MockMvc mvc;
   
   @MockBean
   private MainService mainService;
   
   @Test
   public void userPost() throws Exception {
      ResultActions actions = 
            mvc.perform(post("/user")
                  .contentType(MediaType.APPLICATION_JSON_UTF8)
                  .content("{\"id\":\"testid\", \"mobileNum\":\"01012341234\"}")
                  ).andDo(print());
      
      
      actions.andExpect(status().isOk());
   }

}
