package dao;
import model.Attachment;
import model.Post;
import util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
public class PostDAO {
	public void addPost(Post post) {
		try {
			Connection conn=DBConnection.getConnection();
			String sql="INSERT INTO posts (user_id,subject,content,created_at) VALUES(?,?,?,?)";
			String attachSql = "INSERT INTO post_attachments (post_id, file_name, file_path) VALUES (?, ?, ?)";
			PreparedStatement stmt=conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1,post.getUserId());
			stmt.setString(2, post.getSubject());
			stmt.setString(3,post.getContent());
			stmt.setTimestamp(4,java.sql.Timestamp.valueOf(post.getCreatedAt()));
			int rowsInserted=stmt.executeUpdate();
			if(rowsInserted>0) {
				ResultSet generatedKeys = stmt.getGeneratedKeys();
				if (generatedKeys.next()) {
                    int newPostId = generatedKeys.getInt(1);
                    try (PreparedStatement attachStmt = conn.prepareStatement(attachSql)) {
                        for (Attachment file : post.getAttachments()) {
                            attachStmt.setInt(1, newPostId);
                            attachStmt.setString(2, file.getFileName());
                            attachStmt.setString(3, file.getFilePath());
                            attachStmt.addBatch(); 
                        }
                        attachStmt.executeBatch(); 
                    }
				}
			}
			System.out.println("Post added Successfully!");
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void editPost(Post post) {
		try {
			Connection conn=DBConnection.getConnection();
			String sql = "UPDATE posts SET subject = ?, content = ? WHERE post_id = ?";
			PreparedStatement stmt=conn.prepareStatement(sql);
			stmt.setString(1, post.getSubject());
            stmt.setString(2, post.getContent());
            stmt.setInt(3, post.getPostId());
			int rowsUpdated=stmt.executeUpdate();
			if(rowsUpdated>0) {
				System.out.println("Post Edited Successfully!");
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deletePost(int postId) {
		try {
			Connection conn=DBConnection.getConnection();
			String sql="DELETE FROM posts WHERE post_id=?";
			PreparedStatement stmt=conn.prepareStatement(sql);
			stmt.setInt(1, postId);
			int rowsDeleted=stmt.executeUpdate();
			if(rowsDeleted>0) {
				System.out.println("Post Deleted Successfully!");
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public List<Post> getAllPost(){
		List<Post> posts=new ArrayList<>();
		try {
			Connection conn=DBConnection.getConnection();
			String sql="SELECT * FROM posts";
			Statement stmt= conn.createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next()) {
				Post post=new Post();
				post.setPostId(rs.getInt("post_id"));
				post.setUserId(rs.getInt("user_id"));
				post.setSubject(rs.getString("subject"));
				post.setContent(rs.getString("content"));
				post.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
				post.setAttachments(getAttachmentsByPostId(post.getPostId()));
				posts.add(post);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return posts;
	}
	public Post searchById(int postId) {
		Post post=null;
		try {
			Connection conn=DBConnection.getConnection();
			String sql="SELECT* FROM posts WHERE post_id=?";
			PreparedStatement stmt=conn.prepareStatement(sql);
			stmt.setInt(1, postId);
			ResultSet rs=stmt.executeQuery();
			if(rs.next()) {
				post=new Post();
				post.setPostId(rs.getInt("post_id"));
				post.setUserId(rs.getInt("user_id"));
				post.setSubject(rs.getString("subject"));
				post.setContent(rs.getString("content"));
				post.setCreatedAt((rs.getTimestamp("created_at")).toLocalDateTime());
				post.setAttachments(getAttachmentsByPostId(post.getPostId()));
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return post;
	}
	public List<Post>searchPosts(List<String>keywords){
		List<Post> posts=new ArrayList<>();
		try {
			Connection conn=DBConnection.getConnection();
			StringBuilder sql = new StringBuilder("SELECT * FROM posts WHERE ");
	        for (int i = 0; i < keywords.size(); i++) {
	            sql.append("(content LIKE ? OR subject LIKE ?)");
	            if (i < keywords.size() - 1) sql.append(" OR ");
	        }
	        PreparedStatement stmt=conn.prepareStatement(sql.toString());
	        for (int i = 0; i < keywords.size(); i++) {
                String pattern = "%" + keywords.get(i) + "%";
                stmt.setString(i * 2 + 1, pattern);
                stmt.setString(i * 2 + 2, pattern);
            }
			ResultSet rs=stmt.executeQuery();
			while(rs.next()) {
				Post post=new Post();
				post.setPostId(rs.getInt("post_id"));
				post.setUserId(rs.getInt("user_id"));
				post.setSubject(rs.getString("subject"));
				post.setContent(rs.getString("content"));
				post.setCreatedAt((rs.getTimestamp("created_at")).toLocalDateTime());
				post.setAttachments(getAttachmentsByPostId(post.getPostId()));
				posts.add(post);
			}
		}
		catch (SQLException e) {
	        e.printStackTrace();
	    }
		return posts;
	}
	public List<Post> postsByUser(int userId){
		List<Post> posts=new ArrayList<>();
		try {
			Connection conn=DBConnection.getConnection();
			String sql="SELECT* FROM posts WHERE user_id=?";
			PreparedStatement stmt=conn.prepareStatement(sql);
			stmt.setInt(1, userId);
			ResultSet rs=stmt.executeQuery();
			while(rs.next()) {
				Post post=new Post();
				post.setPostId(rs.getInt("post_id"));
				post.setUserId(rs.getInt("user_id"));
				post.setSubject(rs.getString("subject"));
				post.setContent(rs.getString("content"));
				post.setCreatedAt((rs.getTimestamp("created_at")).toLocalDateTime());
				post.setAttachments(getAttachmentsByPostId(post.getPostId()));
				posts.add(post);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return posts;
	}
	public List<Attachment> getAttachmentsByPostId(int postId) {
	    List<Attachment> attachments = new ArrayList<>();
	    String sql = "SELECT * FROM post_attachments WHERE post_id = ?";

	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        
	        stmt.setInt(1, postId);
	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            Attachment attach = new Attachment();
	            attach.setAttachmentId(rs.getInt("attachment_id"));
	            attach.setPostId(rs.getInt("post_id"));
	            attach.setFileName(rs.getString("file_name"));
	            attach.setFilePath(rs.getString("file_path")); 
	            
	            attachments.add(attach);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return attachments;
	}
}
