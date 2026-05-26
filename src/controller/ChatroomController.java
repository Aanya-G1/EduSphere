package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import dao.ChatDAO;
import dao.ChatRoomDAO;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import model.ChatMessage;
import model.ChatRoom;
import util.TestUser;

public class ChatroomController implements Initializable {
	@FXML
	Label roomName;
	@FXML
	Button createBtn;
	@FXML
	ListView roomlist;
	@FXML
	ScrollPane chatScrollPane;
	@FXML
	VBox messageContainer;
	@FXML
	TextField userInputField;
	
	private ChatRoom selectedRoom;
	private int lastMessageId = 0;
	private Timeline pollingTimeline;
	public void initialize(URL location, ResourceBundle resources) {
		userInputField.setDisable(true);
		loadChatroomList();
		RoomSelectionListener();
		startPolling();
	}
	@FXML
	public void createRoom(ActionEvent event) {
		TextInputDialog dialog=new TextInputDialog();
		dialog.setTitle("New Chatroom");
		dialog.setHeaderText("Create a study room");
		dialog.setContentText("Enter room name:");
		Optional<String> result=dialog.showAndWait();
		result.ifPresent(name->{
			ChatRoomDAO dao=new ChatRoomDAO();
			dao.addRoom(name);
			loadChatroomList();
		});
	}
	private void startPolling() {
		pollingTimeline=new Timeline(new KeyFrame(Duration.seconds(2),e-> fetchnewMessages()));
		pollingTimeline.setCycleCount(Animation.INDEFINITE);
		pollingTimeline.play();
	}
	private void RoomSelectionListener() {
		roomlist.getSelectionModel().selectedItemProperty().addListener((obs,oldVal,newVal)->{
			if(newVal!=null) {
				userInputField.setDisable(false);
				selectedRoom = (ChatRoom) newVal;
				roomName.setText(selectedRoom.getRoomName());
				messageContainer.getChildren().clear();
				lastMessageId=0;
				fetchnewMessages();
			}
		});
	}
	private void fetchnewMessages() {
		if (selectedRoom == null) return;
		ChatDAO dao=new ChatDAO();
		List<ChatMessage> messages=dao.getNewMessages(selectedRoom.getRoomId(), lastMessageId);
		for(ChatMessage message: messages) {
			if(message.getUserId()!=TestUser.getInstance().getUserId())
			addMessageToUI(message.getMessage(),false);
			else addMessageToUI(message.getMessage(),true);
			lastMessageId = message.getMessageId();
		}
		if(!(messages.isEmpty())) scrollToBottom();
	}
	@FXML
	public void handleSendMessage(ActionEvent event) {
		String userInput = userInputField.getText().trim();
        if (userInput.isEmpty()) return;
        ChatMessage message=new ChatMessage();
        message.setMessage(userInput);
        message.setRoomId(selectedRoom.getRoomId());
        message.setUserId(TestUser.getInstance().getUserId());
        ChatDAO dao=new ChatDAO();
        dao.addMessage(message);
        userInputField.clear();
	}
	private void addMessageToUI(String message,boolean isUser) {
		HBox hBox = new HBox();
        hBox.setAlignment(isUser ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);
        
        Label label = new Label(message);
        label.setWrapText(true);
        label.setMaxWidth(250); 
        
        String style = isUser 
            ? "-fx-background-color: #0078FF; -fx-text-fill: white; -fx-background-radius: 15 15 0 15; -fx-padding: 10;" 
            : "-fx-background-color: #E9E9EB; -fx-text-fill: black; -fx-background-radius: 15 15 15 0; -fx-padding: 10;";
        
        label.setStyle(style);
        hBox.getChildren().add(label);
        messageContainer.getChildren().add(hBox);
	}
	private void loadChatroomList() {
		List<ChatRoom> rooms=new ArrayList<>();
		ChatRoomDAO dao=new ChatRoomDAO();
		rooms=dao.getAllrooms();
		roomlist.getItems().setAll(rooms);
	}
	private void scrollToBottom() {
        chatScrollPane.setVvalue(1.0);
    }
}
