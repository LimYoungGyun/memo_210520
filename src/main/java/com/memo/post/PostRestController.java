package com.memo.post;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.memo.post.bo.PostBO;

@RestController
@RequestMapping("/post")
public class PostRestController {
	
	@Autowired
	private PostBO postBO;

	/**
	 * 글 쓰기
	 * @param subject
	 * @param content
	 * @param file
	 * @param request
	 * @return
	 */
	@PostMapping("/create")
	public Map<String, Object> create(
			@RequestParam("subject") String subject
			, @RequestParam("content") String content
			, @RequestParam(value = "file", required = false) MultipartFile file
			, HttpServletRequest request) {
		
		// session에서 userId를 가져온다.
		HttpSession session = request.getSession();
		Integer userId = (Integer) session.getAttribute("userId");
		String userLoginId = (String) session.getAttribute("userLoginId");
		
		Map<String, Object> result = new HashMap<>();
		result.put("result", "error");
		
		if (userId == null) {
			return result;
		}
		
		// DB에 insert BO -> userId, userLoginId, subject, content, file
		int row = postBO.createPost(userId, userLoginId, subject, content, file);
		if (row > 0) {
			result.put("result", "success");
		}
		
		
		// 결과값 response
		
		
		return result;
	}
	
	/**
	 * 게시글 수정
	 * @param postId
	 * @param subject
	 * @param content
	 * @param file
	 * @param request
	 * @return
	 */
	@PutMapping("/update")
	public Map<String, Object> update(
			@RequestParam("postId") int postId
			, @RequestParam("subject") String subject
			, @RequestParam("content") String content
			, @RequestParam(value = "file", required = false) MultipartFile file
			, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		String loginId = (String) session.getAttribute("userLoginId");
		
		// db update
		postBO.updatePost(postId, loginId, subject, content, file);
		
		// 응답값 세팅
		Map<String, Object> result = new HashMap<>();
		result.put("result", "success");
		
		return result;
	}
	
	@DeleteMapping("/delete")
	public Map<String, Object> delete(
			@RequestParam("postId") int postId) {
		
		// db postId 데이터 삭제
		postBO.deletePost(postId);
		
		// 결과 리턴
		Map<String, Object> result = new HashMap<>();
		result.put("result", "success");
		
		return result;
	}
}
