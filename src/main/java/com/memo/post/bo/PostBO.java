package com.memo.post.bo;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.memo.common.FileManagerService;
import com.memo.post.dao.PostDAO;
import com.memo.post.model.Post;

@Service
public class PostBO {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private PostDAO postDAO;
	
	@Autowired
	private FileManagerService fileManagerService;
	
	public List<Post> getPostList(int userId) {
		return postDAO.selectPostList(userId);
	}
	
	public int createPost(int userId, String userLoginId, String subject, String content, MultipartFile file) {
		
		String imagePath = null;
		if (file != null) {
			try {
				imagePath = fileManagerService.saveFile(userLoginId, file);
			} catch (IOException e) {
				imagePath = null;
			}
		}
		
		return postDAO.insertPost(userId, subject, content, imagePath);
	}
	
	// 너무 당연하게 넘기는 경우 ByPostId를 제외해 줘도 된다.
	public Post getPost(int postId) {
		return postDAO.selectPost(postId);
	}
	
	public void updatePost(int postId, String loginId, String subject, String content, MultipartFile file) {
		
		// postId로 게시물이 있는지 확인
		// 많은 사용자가 사용할시에 내가 수정하려고할때 삭제할수도 있으니 해당 코드를 입력해준다.
		Post post = getPost(postId);
		if (post == null) {
			logger.error("[글 수정] post is null postId:{}", postId);
			return;
		}
		
		// file이 있으면 업로드 후 imagePath 얻어온다.
		String imagePath = null;
		if (file != null) {
			// 파일 업로드
			try {
				imagePath = fileManagerService.saveFile(loginId, file);
				
				// 기존에 있던 폴더에 파일 제거 - imagePath가 존재(업로드 성공) && 기존에 파일이 있으면
				if (imagePath != null && post.getImagePath() != null) {
					// 업로드가 실패할 수 있으므로 업로드 성공 후 제거
					// 매개변수로 들어가는 imagePath를 주의해서 넣기 잘못넣으면 수정한 파일과 폴더를 삭제하게됨.
					// fileManagerService.deleteFile(imagePath);
					fileManagerService.deleteFile(post.getImagePath());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		// db update
		postDAO.updatePost(postId, subject, content, imagePath);
	}
	
	public void deletePost(int postId) {
		// postId로 post를 가져온다.
		Post post = getPost(postId);
		if (post == null) {
			logger.error("[delete post] 삭제할 게시물이 없습니다. postId:{}", postId);
			return;
		}
		
		// 그림이 있으면 삭제한다.
		String imagePath = post.getImagePath();
		if (imagePath != null) {
			try {
				fileManagerService.deleteFile(imagePath);
			} catch (IOException e) {
				logger.error("[delete post] 그림 삭제 실패. postId:{}, path:{}", postId, imagePath);
			}
		}
		
		// post를 삭제한다.
		postDAO.deletePost(postId);
	}
}
