package com.jpp.main.mapper;

import java.util.List;

import com.jpp.main.vo.UserVO;

public interface MainMapper {
	
	public List<UserVO> getUserList() throws Exception;
}
