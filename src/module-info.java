@SuppressWarnings("module")
module EduSphere {
    // Required Libraries
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires mysql.connector.j;
    requires java.net.http;


    opens application to javafx.fxml;
    exports application;
    opens controller to javafx.fxml;
    
    exports controller;
}