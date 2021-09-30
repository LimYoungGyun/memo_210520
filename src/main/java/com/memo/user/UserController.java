package com.memo.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user")
@Controller
public class UserController {

	/**
	 * 회원가입 페이지로 이동
	 * @param model
	 * @return
	 */
	@RequestMapping("/sign_up_view")
	public String signUpView(Model model) {
		
		model.addAttribute("viewName", "user/sign_up");
		
		return "template/layout";
	}
	
	/**
	 * 로그인 페이지로 이동
	 * @param model
	 * @return
	 */
	@RequestMapping("/sign_in_view")
	public String signInView(Model model) {
		
		model.addAttribute("viewName", "user/sign_in");
		
		return "template/layout";
	}
	
	/**
	 * 로그아웃
	 * @param reuqest
	 * @return
	 */
	@RequestMapping("/sign_out")
	public String signOut(HttpServletRequest reuqest) {
		HttpSession session = reuqest.getSession();
		session.removeAttribute("userId");
		session.removeAttribute("userName");
		session.removeAttribute("userLoginId");
		return "redirect:/user/sign_in_view";
	}
}
