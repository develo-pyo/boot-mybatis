package com.jpp.main.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jpp.main.dao.MainDAO;
import com.jpp.main.mapper.MainMapper;
import com.jpp.main.vo.UserVO;

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
		
	
	
	
}
