package com.jpp.main.dao;

import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.jpp.main.vo.UserVO;

@Repository
public class MainDAO {
	
	@Resource
	private SqlSessionTemplate sqlSessionTemplate;
	
	private static final String MAPPER_NM = "com.jpp.main.mapper.MainMapper.";
	
	public List<UserVO> getUserList(){
		return sqlSessionTemplate.selectList(MAPPER_NM+"getUserList");
	}
}
