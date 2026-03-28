package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Post;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import dao.PostDAO;

public class FeedController implements Initializable{
	@FXML
	private VBox feedContainer;
	@FXML
	private TextField searchBar;
	@FXML
	private Button searchBtn;
	@FXML
	private MenuItem addPost;
	
	
	public void initialize(URL location, ResourceBundle resources) {
		loadPosts();
		addPost.setOnAction(this::openAddPost);
		searchBtn.setOnAction(this::searchPosts);
	}
	@FXML
	private void openAddPost(ActionEvent  event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/post.fxml"));
			Scene scene=new Scene(loader.load(),1000, 800);
			PostController controller = loader.getController();
			controller.setOnSaveCallback(() -> loadPosts());
			Stage stage=new Stage();
			stage.setScene(scene);
			stage.setTitle("Add Post");
			stage.show();
		}
		catch(Exception e) {
			System.out.println("Feed UI not working!");
			e.printStackTrace();
		}
	}
	public void loadPosts() {
		feedContainer.getChildren().clear();
		PostDAO dao = new PostDAO();
        List<Post> posts = dao.getAllPost();
        for (Post post : posts) {
        	try {
        		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/feedPost.fxml"));
        		VBox card=loader.load();
        		FeedPostController controller=loader.getController();
        		controller.loadData(post);
        		feedContainer.getChildren().add(card);
        	}
        	catch(Exception e) {
        		System.out.println("Couldn't load Posts!"+e.getMessage());
        	}
        }
		
	}
	private void displayPost(Post post) {
	    try {
	    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/feedPost.fxml"));
    		VBox card=loader.load();
    		FeedPostController controller=loader.getController();
    		controller.loadData(post);
    		feedContainer.getChildren().add(card);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	@FXML
	private void searchPosts(ActionEvent event) {
		String searchText=searchBar.getText().trim();
		String[] keywords=searchText.split("\\s+");
		List<String> keywordsList=Arrays.asList(keywords);
		feedContainer.getChildren().clear();
		PostDAO dao = new PostDAO();
	    List<Post> results;
	    results=dao.searchPosts(keywordsList);
	    for(Post post:results) {
	    	displayPost(post);
	    }
	}
}
