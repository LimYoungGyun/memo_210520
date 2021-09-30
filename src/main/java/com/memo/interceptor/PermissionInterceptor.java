package com.memo.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class PermissionInterceptor implements HandlerInterceptor {
	
	// System.out.println() 사용을 하지말고 하단의 있는 Logger를 사용한다.
	// private Logger logger = LoggerFactory.getLogger(PermissionInterceptor.class);
	// import할때 package명을 주의해야한다.
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
		// System.out.println("[###preHandle] : " + request.getRequestURI());
		logger.info("[###preHandle] : " + request.getRequestURI());
		
		// session을 가져온다.
		HttpSession session = request.getSession();
		Integer userId = (Integer) session.getAttribute("userId");
		
		// URL path를 가져온다.
		String uri = request.getRequestURI();
		
		if (userId != null && uri.startsWith("/user")) {
			// 만약 로그인이 되어 있으면 + /user => post 쪽으로 보낸다.
			response.sendRedirect("/post/post_list_view");
			return false;
		} else if (userId == null && uri.startsWith("/post")) {
			// 만약 로그인이 안되어 있으면 + /post => user 쪽으로 보낸다.
			response.sendRedirect("/user/sign_in_view");
			return false;
		}
		
		
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
		// System.out.println("[###postHandle] : " + request.getRequestURI());
		logger.warn("[###postHandle] : " + request.getRequestURI());
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) {
		// System.out.println("[###afterCompletion] : " + request.getRequestURI());
		logger.error("[###afterCompletion] : " + request.getRequestURI());
	}
}
