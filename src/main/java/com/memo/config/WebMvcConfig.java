package com.memo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.memo.interceptor.PermissionInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
	@Autowired
	private PermissionInterceptor interceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(interceptor)
		.addPathPatterns("/**") // "/**" 손주를 포함한 모든 디렉토리 확인
		.excludePathPatterns("/user/sign_out", "/static/**", "/error"); // 인터셉터를 안태울 path 설정
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/images/**") // http://localhost/images/admin_1633347406654/다운로드.jpg
		.addResourceLocations("file:///D:\\marondal\\6_spring_project\\ex\\memo_workspace\\Memo\\images/"); // 실제 파일 저장 위치, 프로토콜 필요("file:///") - 윈도우는 /가 3개여야 한다.
	}
}
