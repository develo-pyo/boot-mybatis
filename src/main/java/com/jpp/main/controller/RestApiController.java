package com.jpp.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jpp.main.service.MainService;
import com.jpp.main.vo.UserVO;

@RestController
public class RestApiController {
   
   @Autowired
   private MainService mainService;
   
   
}
