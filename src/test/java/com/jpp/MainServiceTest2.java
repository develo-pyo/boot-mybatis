package com.jpp;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.jpp.main.controller.MainController;
import com.jpp.main.service.MainService;

@RunWith(SpringRunner.class)
@WebMvcTest(MainController.class)
public class MainServiceTest2 {
	
//	private MainDAO mainDao;
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private MainService mainService;
	
//	@Autowired
//	private ApplicationContext context;
	
	@Test
	public void api() throws Exception {
		
		
		ResultActions actions = 
				mvc.perform(post("/api2")
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.content("{\"test\":\"val\"}")
						).andDo(print());
		
		actions.andExpect(status().isOk());
		
		//MainDAO dao = context.getBean("mainDAO", MainDAO.class);
		
//		for(UserVO vo : mainMapper.getUserList()) {
//			System.out.println(vo.getName());
//		}
	}
	
	
}
