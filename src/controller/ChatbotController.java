package controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import util.DBConnection;

public class ChatbotController implements Initializable {
	
	@FXML
	VBox chatRoot;
	@FXML
	ScrollPane chatScrollPane;
	@FXML
	VBox messageContainer;
	@FXML
	TextField userInputField;
	
	private int searchLevel=0;
	private String keywords="";
	private String course="";
	public void initialize(URL location, ResourceBundle resources) {
		
	}

	@FXML
	public void handleSendMessage(ActionEvent event) {
		String userInput = userInputField.getText().trim();
        if (userInput.isEmpty()) return;
        addMessageToUI(userInput, true);
        switch(searchLevel) {
        case 0:
        	InitialSearch(userInput);
        	break;
        
        case 1:
        	CourseSearch(userInput);
        	break;
        case 2:
        	TopicSearch(userInput);
        	break;
        }
        userInputField.clear();
        chatScrollPane.setVvalue(1.0);
        	
	}
	private void InitialSearch(String userInput) {
		keywords=generateKeywords(userInput);
		String response = searchDatabase(keywords, "", "","");
		if (response != null) {
            addMessageToUI(response,false);
        } else {
            addMessageToUI("Sorry I couldn't find an exact match. Which course is this for?",false);
            searchLevel = 1;
        }
	}
	private void CourseSearch(String course) {
		String response=searchDatabase(keywords,course,"","");
		if (response != null) {
            addMessageToUI(response,false);
        } else {
            addMessageToUI("Sorry I couldn't find an exact match. Could you please specify the subject and the topic ?",false);
            searchLevel =2;
        }
		
	}
	private void TopicSearch(String userInput) {
		String[] input=userInput.split("[,\\s]+");
		String sub = input.length > 0 ? input[0] : "";
        String topic = input.length > 1 ? input[1] : "";
		String response=searchDatabase(keywords,course,sub,topic);
		if (response != null) {
            addMessageToUI(response,false);
        } else {
            addMessageToUI("Sorry my database is a bit limited right now I couldn't answer your question.",false);
            resetChat();
        }
	}
	private void resetChat() {
        searchLevel = 0;
        keywords = "";
        course = "";
    }
	private String generateKeywords(String text) {
        if (text == null || text.isEmpty()) return "";
        Set<String> stopWords = new HashSet<>(Arrays.asList(
            "what", "is", "are", "the", "define", "explain", "how", "to", "of", "in", 
            "an", "a", "can", "you", "tell", "me", "about", "briefly", "discuss"
        ));

        return Arrays.stream(text.toLowerCase().replaceAll("[^a-zA-Z0-9 ]", "").split("\\s+"))
                .filter(word -> !stopWords.contains(word))
                .filter(word -> word.length() > 1)
                .distinct()
                .collect(Collectors.joining(", "));
    }
	private String searchDatabase(String keywords,String course,String subject,String topic) {
		String response=null;
		String sql="SELECT response FROM chatbot_rules WHERE keywords LIKE ?";
		if(!(course.isEmpty())) sql+=" AND course=?";
		if(!(subject.isEmpty())) sql+=" AND subject=?";
		if(!(topic.isEmpty())) sql+=" AND category=?";
		try(Connection conn=DBConnection.getConnection()){
			PreparedStatement stmt=conn.prepareStatement(sql);
			stmt.setString(1, "%" + keywords + "%");
			int param=2;
			if(!(course.isEmpty())) stmt.setString(param++,course);
			if(!(subject.isEmpty())) stmt.setString(param++,subject);
			if(!(topic.isEmpty())) stmt.setString(param++,topic);
			ResultSet rs=stmt.executeQuery();
			if(rs.next()) response=rs.getString("response");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	private void addMessageToUI(String response,boolean isUser) {
		HBox hBox = new HBox();
        hBox.setAlignment(isUser ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);
        
        Label label = new Label(response);
        label.setWrapText(true);
        label.setMaxWidth(250); 
        
        String style = isUser 
            ? "-fx-background-color: #0078FF; -fx-text-fill: white; -fx-background-radius: 15 15 0 15; -fx-padding: 10;" 
            : "-fx-background-color: #E9E9EB; -fx-text-fill: black; -fx-background-radius: 15 15 15 0; -fx-padding: 10;";
        
        label.setStyle(style);
        hBox.getChildren().add(label);
        messageContainer.getChildren().add(hBox);
	}
	@FXML
	public void handleClose(ActionEvent event) {
		chatRoot.setVisible(false);
	}
}
