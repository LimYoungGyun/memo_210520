package com.memo.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.memo.common.EncryptUtils;
import com.memo.user.bo.UserBO;

@RestController
@RequestMapping("/user")
public class UserRestController {
	
	@Autowired
	private UserBO userBO;

	/**
	 * 아이디 중복 확인
	 * @param loginId
	 * @return
	 */
	@RequestMapping("/is_duplicated_id")
	public Map<String, Boolean> isDuplicatedId(
			@RequestParam("loginId") String loginId) {
		
		// 로그인 아이디 중복 여부 디비 조회
		// 중복 여부에 대한 결과 Map 생성
		Map<String, Boolean> result = new HashMap<>();
		result.put("result", userBO.existLoginId(loginId));
		
		// return map
		return result;
	}
	
	/**
	 * 회원가입
	 * @param loginId
	 * @param password
	 * @param name
	 * @param email
	 * @return
	 */
	@PostMapping("/sign_up")
	public Map<String, Object> signUp(
			@RequestParam("loginId") String loginId
			, @RequestParam("password") String password
			, @RequestParam("name") String name
			, @RequestParam("email") String email) {
		
		// 비밀번호 해싱
		String encryptPassword = EncryptUtils.md5(password);
		
		// DB user insert
		int cnt = userBO.addNewUser(loginId, encryptPassword, name, email);
		
		// 응답값 생성 후 리턴
		Map<String, Object> result = new HashMap<>();
		if (cnt >= 1) {
			result.put("result", "success");
		} else {
			result.put("result", "false");
		}
		return result;
	}
}
