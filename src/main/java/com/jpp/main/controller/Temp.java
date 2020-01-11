package com.jpp.main.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Temp {

	@GetMapping("/testapi")
	public String testapi() {
		
		return "goodd";
	}
}
