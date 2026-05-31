@SuppressWarnings("module")
module EduSphere {
    // Required Libraries
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires mysql.connector.j;
    requires java.net.http;

    requires com.opencsv;
    requires org.apache.commons.lang3;
    requires org.apache.commons.text;
	requires javafx.base;
    opens application to javafx.fxml;
    exports application;
    opens controller to javafx.fxml;
    
    exports controller;
}