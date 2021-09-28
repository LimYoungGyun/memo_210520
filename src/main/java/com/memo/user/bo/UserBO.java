package com.memo.user.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memo.user.dao.UserDAO;

@Service
public class UserBO {
	
	@Autowired
	private UserDAO userDAO;

	// 아이디 중복 확인
	public boolean existLoginId(String loginId) {
		return userDAO.existLoginId(loginId);
	}
	
	// 회원가입
	public int addNewUser(String loginId, String password, String name, String email) {
		return userDAO.insertNewUser(loginId, password, name, email);
	};
}
