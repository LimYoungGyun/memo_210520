package com.memo.post;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.memo.post.bo.PostBO;
import com.memo.post.model.Post;

@Controller
@RequestMapping("/post")
public class PostController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private PostBO postBO;

	/**
	 * 글 목록 화면
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/post_list_view")
	public String postListView(Model model, HttpServletRequest request) {
		
		// 글 목록들을 가져온다.
		HttpSession session = request.getSession();
		Integer userId = (Integer) session.getAttribute("userId"); // userId가 null일수도 있으니 Integer를 사용
		if (userId == null) {
			logger.info("[post_list_view] userId is null. " + userId);
			return "redirect:/user/sign_in_view";
		}
		
		List<Post> postList = postBO.getPostList(userId);
		
		// 모델에 담는다.
		model.addAttribute("postList", postList);
		model.addAttribute("viewName", "post/post_list");
		
		return "template/layout";
	}
	
	/**
	 * 글쓰기 화면
	 * @param model
	 * @return
	 */
	@RequestMapping("/post_create_view")
	public String postCreateView(Model model, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		Integer userId = (Integer) session.getAttribute("userId");
		if (userId == null) {
			// 세션에 userId가 없으면 로그인 하는 페이지로 이동(redirect)
			return "redirect:/user/sign_in_view";
		}
		
		model.addAttribute("viewName", "post/post_create");
		
		return "template/layout";
	}
	
	@RequestMapping("/post_detail_view")
	public String postDetailView(
			@RequestParam("postId") int postId
			, Model model
			, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		Integer userId = (Integer) session.getAttribute("userId");
		if (userId == null) {
			// 세션에 userId가 없으면 로그인 하는 페이지로 이동(redirect)
			return "redirect:/user/sign_in_view";
		}
		
		// 아직 구현하지 않은것은 TODO 를 사용하면 된다.
		// postId에 해당하는 게시물을 가져와서 model에 담는다.
		Post post = postBO.getPost(postId);
		model.addAttribute("post", post);
		
		model.addAttribute("viewName", "post/post_detail");
		
		return "template/layout";
	}
}
