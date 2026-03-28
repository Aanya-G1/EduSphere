package model;
import java.time.LocalDateTime;

public class Comment {
	private int comment_id;
	private int post_id;
	private int user_id;
	private String content;
	private LocalDateTime created_at;
	
	public Comment() {}
	public Comment(int comment_id,int post_id,int user_id,String content) {
		this.comment_id=comment_id;
		this.post_id=post_id;
		this.user_id=user_id;
		this.content=content;
		this.created_at=LocalDateTime.now();
	}
	public int getCommentId() {
		return comment_id;
	}
	public void setCommentId(int comment_id) {
		this.comment_id = comment_id;
	}
	
	public int getPostId() {
		return post_id;
	}
	public void setPostId(int post_id) {
		this.post_id = post_id;
	}
	
	public int getUserId() {
		return user_id;
	}
	public void setUserId(int user_id) {
		this.user_id = user_id;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public LocalDateTime getCreatedAt() {
		return created_at;
	}
	public void setCreatedAt(LocalDateTime created_at) {
		this.created_at =created_at;
	}
}
