package controller;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

import dao.CommentDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Attachment;
import model.Comment;
import model.Post;
import util.Date;
import util.TestUser;

public class FeedPostController {
	
	@FXML
	private Label postSub;
	@FXML
	private Label userId;
	@FXML
	private Label uploadTime;
	@FXML
	private TextArea postContent;
	@FXML
	private VBox commentBox;
	@FXML
	private TextField comment;
	@FXML
	private Button commentBtn;
	@FXML
	private Button downloadBtn;
	@FXML
	private ListView filesList;
	
	private Post currentPost;
	public void initialize(URL location, ResourceBundle resources) {
		downloadBtn.setOnAction(this::downloadFiles);
		commentBtn.setOnAction(this::addComment);
	}
	
	private void loadComments() {
		CommentDAO commentobj=new CommentDAO();
		List<Comment> comments= commentobj.viewComments(currentPost.getPostId());
		commentBox.getChildren().clear();
		for(Comment c:comments) {
			HBox row=new HBox(10);
			Label user=new Label("user id: "+c.getUserId());
			Label cont=new Label(c.getContent());
			cont.setWrapText(true);
			Label time=new Label(Date.relativeTime(c.getCreatedAt()));
			row.getChildren().addAll(user,cont,time);
			commentBox.getChildren().add(row);
		}
	}
	
	@FXML
	private void downloadFiles(ActionEvent event) {
		Attachment selectedFile = (Attachment) filesList.getSelectionModel().getSelectedItem();
		if (selectedFile == null) {
	        System.out.println("No file selected!");
	        return; 
	    }
		String fileUrl = selectedFile.getFilePath();
	    String fileName = selectedFile.getFileName();
	    String home = System.getProperty("user.home");
	    File downloadFolder = new File(home, "Downloads");
	    File destFile = new File(downloadFolder, fileName);
	    File finalDest = destFile;
	    new Thread(() -> {
	        try (InputStream in = new java.net.URL(fileUrl).openStream()) {
	            
	            java.nio.file.Files.copy(in, finalDest.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
	            
	        } catch (Exception e) {
	            System.err.println("Download failed: " + e.getMessage());
	        }
	    }).start();
	}
	
	public void loadData(Post post) {
		this.currentPost = post;
		postContent.setText(post.getContent());
		userId.setText("User: "+post.getUserId());
		uploadTime.setText(Date.relativeTime(post.getCreatedAt()));
		postSub.setText(post.getSubject());
		List<Attachment> attachments = post.getAttachments();
	    if (attachments != null) {
	        filesList.getItems().clear(); 
	        filesList.getItems().addAll(attachments);
	    }
	    loadComments();
	}
	@FXML
	private void addComment(ActionEvent event) {
		Comment commentobj=new Comment();
		commentobj.setContent(comment.getText());
		commentobj.setCreatedAt(LocalDateTime.now());
		commentobj.setUserId(TestUser.getInstance().getUserId());
		commentobj.setPostId(currentPost.getPostId());
		CommentDAO dao=new CommentDAO();
		dao.addComment(commentobj);
		comment.clear();
		loadComments();
	}
}
