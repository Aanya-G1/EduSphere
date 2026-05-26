package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.ChatRoom;
import util.DBConnection;

public class ChatRoomDAO {
	public boolean addRoom(String roomName) {
	    String sql = "INSERT INTO chat_rooms (room_name) VALUES (?)";
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        
	        stmt.setString(1, roomName);
	        return stmt.executeUpdate() > 0;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	public ChatRoom searchById(int id) {
		ChatRoom room=null;
		try {
			Connection conn=DBConnection.getConnection();
			String sql="SELECT* FROM chat_rooms WHERE room_id=?";
			PreparedStatement stmt=conn.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs=stmt.executeQuery();
			if(rs.next()) {
				room=new ChatRoom();
				room.setRoomId(rs.getInt("room_id"));
				room.setRoomName(rs.getString("room_name"));
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return room;
	}
	public List<ChatRoom> getAllrooms(){
		List<ChatRoom> rooms=new ArrayList<>();
		try {
			Connection conn=DBConnection.getConnection();
			String sql="SELECT * FROM chat_rooms";
			Statement stmt= conn.createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next()) {
				ChatRoom room=new ChatRoom();
				room.setRoomId(rs.getInt("room_id"));
				room.setRoomName(rs.getString("room_name"));
				rooms.add(room);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return rooms;
	}
}
