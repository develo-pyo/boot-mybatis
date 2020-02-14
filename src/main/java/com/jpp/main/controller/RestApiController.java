package com.jpp.main.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jpp.main.service.MainService;

//https://howtodoinjava.com/spring5/webmvc/controller-getmapping-postmapping/

@RestController
public class RestApiController {
   
   @Autowired
   private MainService mainService;
   
   private static final Logger logger = LoggerFactory.getLogger(RestApiController.class);
   
   @PostMapping("/user")
   public ResponseEntity<Object> userPost(@RequestBody Map<String, String> param) throws Exception {
      logger.info("userPost");

      Map<String, String> rs = new HashMap<>();
      
      int i = 0;
      try {
         i = mainService.insertUser(param);
      } catch(DuplicateKeyException dk) {
         return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      } catch(Exception e) {
         return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
      logger.info("rs : " + i);
      
      return new ResponseEntity<>(HttpStatus.OK);
   }
   
   @GetMapping("/user/{id}")
   public ResponseEntity<List<Map<String, String>>> userGet(Model model) throws Exception {
      logger.info("userGet");

      List<Map<String, String>> rs = new ArrayList<Map<String, String>>();
      
      
      return new ResponseEntity<List<Map<String,String>>>(rs, HttpStatus.OK);
   }
   
   @PutMapping("/user/{id}")
   public ResponseEntity<Map<String, String>> userPut(@RequestBody Map<String, String> param) throws Exception {
      logger.info("userPut");

      Map<String, String> rs = new HashMap<>();
      
      
      return new ResponseEntity<Map<String,String>>(rs, HttpStatus.OK);
   }
   
   @DeleteMapping("/user/{id}")
   public ResponseEntity<Map<String, String>> userDelete(Model model) throws Exception {
      logger.info("userDelete");

      Map<String, String> rs = new HashMap<>();
      
      
      return new ResponseEntity<Map<String,String>>(rs, HttpStatus.OK);
   }
   
}
