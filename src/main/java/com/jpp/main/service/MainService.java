package com.jpp.main.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jpp.main.mapper.MainMapper;
import com.jpp.main.vo.UserVO;
import com.jpp.web.util.HttpUtil.HttpUtil;
import com.jpp.web.util.HttpUtil.CustomURLConnection;

@Service
public class MainService {

	@Autowired
	private MainMapper mainMapper;
	
//	@Autowired
//	private MainDAO mainDao;
	
	public List<UserVO> getUserList() throws Exception {
		
		return mainMapper.getUserList();
//		return mainDao.getUserList();
	}
		
	public List<Map<String, String>> getDataFromOtherServer() throws Exception {
	   
	   CustomURLConnection uc = new CustomURLConnection.Builder("GET", "127.0.0.1/test.do")
	                                       .setTryCount(3)
	                                       .setConnectionTimeOut(5000)
	                                       .setReadTimeOut(5000)
	                                       .build();
	   
	   
	   
	   HttpUtil http = new HttpUtil();
	   
	   return null;
	}
	
}
