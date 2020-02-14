package com.jpp.main.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.jpp.main.service.MainService;
import com.jpp.main.vo.UserVO;

@Controller
public class MainController {
	
	@Autowired
	private MainService mainService;
	
	
	@RequestMapping("/")
	public String test() throws Exception {
		
		return "main";
	}
	
	@RequestMapping("/main")
	public ModelAndView main() throws Exception {
		ModelAndView mav = new ModelAndView("main");
		List<UserVO> list = mainService.getUserList();
		
		mav.addObject("list", list);
		return mav;
	}
	
	@RequestMapping("/api")
	public ModelAndView api() throws Exception {
		String rs = "good";
		
		System.out.println("api called !");
		
		ModelAndView mav = new ModelAndView("api");
		mav.addObject("testg", "tgood!!");
		return mav;
	}
	
	@PostMapping("/api2")
	public ResponseEntity<Map<String, String>> api(@RequestBody String test) throws Exception {
		Map<String, String> rs = new HashMap<>();
		
		System.out.println("api2 called !");
		rs.put("rs", "good");
		
		return new ResponseEntity<Map<String,String>>(rs, HttpStatus.OK);
	}
	
}
