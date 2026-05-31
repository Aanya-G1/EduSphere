package model;

public class ChatRoom {
	private int room_id;
	private String room_name;
	
	public ChatRoom() {}
	public ChatRoom(int room_id, String room_name) {
		this.room_id = room_id;
		this.room_name = room_name;
	}
	
	public int getRoomId() {
		return room_id;
	}
	public void setRoomId(int room_id) {
		this.room_id = room_id;
	}
	
	public String getRoomName() {
		return room_name;
	}
	public void setRoomName(String room_name) {
		this.room_name = room_name;
	}
	@Override
	public String toString() {
	    return this.room_name; 
	}
}
