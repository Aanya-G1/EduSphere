package model;
import java.time.LocalDateTime;
import java.util.List;

public class Post {
	private int post_id;
	private int user_id;
	private String subject;
	private String content;
	private List<Attachment> attachments;
	private LocalDateTime created_at;
	
	public Post() {}
	public Post(int post_id,int user_id,String subject,String content,List<Attachment> attachments) {
		this.post_id=post_id;
		this.user_id=user_id;
		this.subject=subject;
		this.content=content;
		this.attachments=attachments;
		this.created_at=LocalDateTime.now();
	}
	
	public void setPostId(int post_id) {
		this.post_id=post_id;
	}
	public int getPostId() {
		return post_id;
	}
	

	public int getUserId() {
		return user_id;
	}
	public void setUserId(int user_id) {
		this.user_id = user_id;
	}
	
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public List<Attachment> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}
	public LocalDateTime getCreatedAt() {
		return created_at;
	}
	public void setCreatedAt(LocalDateTime created_at) {
		this.created_at =created_at;
	}
}
