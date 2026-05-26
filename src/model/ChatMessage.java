package model;
import java.time.LocalDateTime;

public class ChatMessage {
	private int message_id;
	private int room_id;
	private int user_id;
	private String message;
	private LocalDateTime sent_at;
	
	public ChatMessage() {}
	public ChatMessage(int message_id, int room_id, int user_id, String message) {
		this.message_id = message_id;
		this.room_id = room_id;
		this.user_id = user_id;
		this.message = message;
		this.sent_at = LocalDateTime.now();
	}
	
	public int getMessageId() {
		return message_id;
	}
	public void setMessageId(int message_id) {
		this.message_id = message_id;
	}
	public int getRoomId() {
		return room_id;
	}
	public void setRoomId(int room_id) {
		this.room_id = room_id;
	}
	public int getUserId() {
		return user_id;
	}
	public void setUserId(int user_id) {
		this.user_id = user_id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public LocalDateTime getSentAt() {
		return sent_at;
	}
	public void setSentAt(LocalDateTime sent_at) {
		this.sent_at =sent_at;
	}
	
	
}
