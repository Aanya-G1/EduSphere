package controller;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import dao.PostDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Attachment;
import model.Post;
import util.CloudStorage;
import util.TestUser;
public class PostController implements Initializable {
	private String[] subjects= {"Data Structures","Microprocessors","Machine Learning"};
	@FXML
	private ChoiceBox<String> subjectChoice;
	@FXML
	private Label selSub;
	@FXML
	private TextArea postContent;
	@FXML
	private Button submitBtn;
	@FXML
	private Button uploadBtn;
	@FXML
	private ListView filesList;
	
	private List<File> selectedFiles = new ArrayList<>();
	
	private Runnable onSaveCallback; 

    public void setOnSaveCallback(Runnable callback) {
        this.onSaveCallback = callback;
    }
    
	private void getSub(ActionEvent event) {
		String sub=subjectChoice.getValue();
		selSub.setText(sub);
	}
	public void initialize(URL location, ResourceBundle resources) {
		subjectChoice.getItems().addAll(subjects);
		subjectChoice.setValue("Select Subject");
		subjectChoice.setOnAction(this::getSub);
		uploadBtn.setOnAction(this::AttachFile);
	}
	public void AttachFile(ActionEvent event) {
		FileChooser filechooser=new FileChooser();
		filechooser.setTitle("Select Files to Upload");
		List<File> files=filechooser.showOpenMultipleDialog(null);
		if(files!=null) {
			selectedFiles.addAll(files);
			for(File file:selectedFiles) {
				filesList.getItems().add(file.getName());
			}
		}
	}
	public void submitPost(ActionEvent event) {
		Post post = new Post();
        post.setUserId(TestUser.getInstance().getUserId());
        post.setSubject(subjectChoice.getValue());
        post.setContent(postContent.getText());
        post.setCreatedAt(java.time.LocalDateTime.now());
        List<Attachment> attachments = new ArrayList<>();
        for(File file:selectedFiles) {
			String fileUrl=null;
			try {
				fileUrl = CloudStorage.uploadFile(file);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Attachment attach = new Attachment();
            attach.setFileName(file.getName());
            attach.setFilePath(fileUrl);
            attachments.add(attach);
		}
        post.setAttachments(attachments);
        PostDAO dao = new PostDAO();
        dao.addPost(post);
        if (onSaveCallback != null) {
            onSaveCallback.run(); 
        }
        Stage stage = (Stage) submitBtn.getScene().getWindow();
        stage.close();
	}
}
