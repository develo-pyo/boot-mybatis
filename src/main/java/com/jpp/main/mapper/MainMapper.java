package com.jpp.main.mapper;

import java.util.List;
import java.util.Map;

import com.jpp.main.vo.UserVO;

public interface MainMapper {
	
	public List<UserVO> getUserList() throws Exception;
	public int insertUser(Map<String, String> param) throws Exception;
}
