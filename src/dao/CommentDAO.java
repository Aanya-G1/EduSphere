package dao;
import model.Comment;
import util.DBConnection;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CommentDAO {
	public void addComment(Comment comment) {
		try {
			Connection conn=DBConnection.getConnection();
			String sql="INSERT INTO comments (post_id,user_id,content,created_at) VALUES(?,?,?,?)";
			PreparedStatement stmt=conn.prepareStatement(sql);
			stmt.setInt(1,comment.getPostId());
			stmt.setInt(2,comment.getUserId());
			stmt.setString(3,comment.getContent());
			stmt.setTimestamp(4,java.sql.Timestamp.valueOf(comment.getCreatedAt()));
			int rowsInserted=stmt.executeUpdate();
			if(rowsInserted>0) {
				System.out.println("Comment Added Successfully!");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public List<Comment> viewComments(int postId) {
		List<Comment> comments=new ArrayList<>();
		try(Connection conn=DBConnection.getConnection()) {
			String sql="SELECT* FROM comments WHERE post_id=? ORDER BY created_at ASC;";
			PreparedStatement stmt=conn.prepareStatement(sql);
			stmt.setInt(1, postId);
			ResultSet rs=stmt.executeQuery();
			while(rs.next()) {
				Comment comment=new Comment();
				comment.setCommentId(rs.getInt("comment_id"));
				comment.setPostId(rs.getInt("post_id"));
				comment.setUserId(rs.getInt("user_id"));
				comment.setContent(rs.getString("content"));
				Timestamp ts = rs.getTimestamp("created_at");
				LocalDateTime createdAt = ts.toLocalDateTime();
				comment.setCreatedAt(createdAt);
				comments.add(comment);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return comments;
	}
	public void editComment(Comment comment) {
		String sql = "UPDATE comments SET content = ? WHERE comment_id = ?";
		try (Connection conn = DBConnection.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			stmt.setString(1, comment.getContent());
			stmt.setInt(2, comment.getCommentId());
			
			int rowsUpdated = stmt.executeUpdate();
			if (rowsUpdated > 0) {
				System.out.println("Comment Edited Successfully!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void deleteComment(int commentId) {
		String sql = "DELETE FROM comments WHERE comment_id = ?";
		try (Connection conn = DBConnection.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			stmt.setInt(1, commentId);
			
			int rowsDeleted = stmt.executeUpdate();
			if (rowsDeleted > 0) {
				System.out.println("Comment Deleted Successfully!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
