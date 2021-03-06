package com.jpp;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jpp.main.dao.MainDAO;
import com.jpp.main.service.MainService;
import com.jpp.main.vo.UserVO;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class, TestAOPConfig.class, MainService.class})
//@SpringBootTest
public class MainServiceTest {
	
	@Mock
	private MainDAO mainDao;
	
	@Autowired
	private MainService mainService;
	
	@Test
	public void getUserList() throws Exception {
		//test
		List<UserVO> list = mainService.getUserList();
		for(UserVO vo : list) {
			System.out.println(vo.getName());
		}
		
	}
	
	
}
