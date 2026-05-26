package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.ChatMessage;
import util.DBConnection;

public class ChatDAO {
	public void addMessage(ChatMessage message) {
		try {
			Connection conn=DBConnection.getConnection();
			String sql="INSERT INTO chat_messages (room_id,user_id,message) VALUES(?,?,?)";
			PreparedStatement stmt=conn.prepareStatement(sql);
			stmt.setInt(1,message.getRoomId());
			stmt.setInt(2,message.getUserId());
			stmt.setString(3, message.getMessage());
			int rowsInserted=stmt.executeUpdate();
			if(rowsInserted>0) {
				System.out.println("Message Added Successfully!");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public List<ChatMessage> getChatByRoomId(int roomid) {
		List<ChatMessage> messages=new ArrayList<>();
		try {
			Connection conn=DBConnection.getConnection();
			String sql="SELECT* FROM chat_messages WHERE room_id=?";
			PreparedStatement stmt=conn.prepareStatement(sql);
			stmt.setInt(1, roomid);
			ResultSet rs=stmt.executeQuery();
			while(rs.next()) {
				ChatMessage message=new ChatMessage();
				message.setMessageId(rs.getInt("message_id"));
				message.setRoomId(rs.getInt("room_id"));
				message.setUserId(rs.getInt("user_id"));
				message.setMessage(rs.getString("message"));
				message.setSentAt(rs.getTimestamp("sent_at").toLocalDateTime());
				messages.add(message);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return messages;
	}
	public List<ChatMessage> getNewMessages(int roomId, int lastMessageId) {
	    List<ChatMessage> messages = new ArrayList<>();
	    String sql = "SELECT * FROM chat_messages WHERE room_id = ? AND message_id > ? ORDER BY sent_at ASC";
	    try {
			Connection conn=DBConnection.getConnection();
		    PreparedStatement stmt=conn.prepareStatement(sql);
			stmt.setInt(1, roomId);
			stmt.setInt(2,lastMessageId);
			ResultSet rs=stmt.executeQuery();
			while(rs.next()) {
				ChatMessage message=new ChatMessage();
				message.setMessageId(rs.getInt("message_id"));
				message.setRoomId(rs.getInt("room_id"));
				message.setUserId(rs.getInt("user_id"));
				message.setMessage(rs.getString("message"));
				message.setSentAt(rs.getTimestamp("sent_at").toLocalDateTime());
				messages.add(message);
			}
		}
	    catch(Exception e) {
			e.printStackTrace();
		}
		return messages;
	}
}
