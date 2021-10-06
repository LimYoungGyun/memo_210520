package com.memo.post.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.memo.post.model.Post;

@Repository
public interface PostDAO {
	
	public List<Post> selectPostList(int userId);

	public int insertPost(
			@Param("userId") int userId
			, @Param("subject") String subject
			, @Param("content") String content
			, @Param("imagePath") String imagePath);
	
	
	// "public List<Post> selectPostList(int userId);"에서 검색된 결과중 하나를 가져오기 때문에 "userId"를 제외해 주어도 된다.
	public Post selectPost(int id);
	
	public void updatePost(
			@Param("id") int id
			, @Param("subject") String subject
			, @Param("content") String content
			, @Param("imagePath") String imagePath);
	
	public void deletePost(int id);
}
